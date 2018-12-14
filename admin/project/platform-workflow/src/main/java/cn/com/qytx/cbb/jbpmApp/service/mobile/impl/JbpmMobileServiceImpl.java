package cn.com.qytx.cbb.jbpmApp.service.mobile.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.hibernate.DbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customForm.domain.FormProperties;
import cn.com.qytx.cbb.customForm.domain.FormPropertyValue;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customForm.service.IFormProperties;
import cn.com.qytx.cbb.customForm.service.IFormPropertyValue;
import cn.com.qytx.cbb.customJpdl.dao.NodeFormAttributeDao;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.ApproveHistory;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplDetail;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplMyApproved;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplMyStart;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplNextTaskInfo;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplNotApproved;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplWaitApprove;
import cn.com.qytx.cbb.jbpmApp.dao.WorkflowVarDao;
import cn.com.qytx.cbb.jbpmApp.domain.WorkFlowView;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowVar;
import cn.com.qytx.cbb.jbpmApp.service.IJbpmApp;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.jbpmApp.service.impl.JbpmNodeState;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.SearchWaitProcessTaskList;
import cn.com.qytx.cbb.jbpmApp.service.mobile.JbpmMobileService;
import cn.com.qytx.cbb.jbpmApp.service.mobile.htmlElement.HtmlElement;
import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.cbb.myapply.service.IMyProcessed;
import cn.com.qytx.cbb.publicDom.service.IFormAuthority;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.security.SystemContextHolder;
//import cn.com.qytx.platform.utils.StringUtil;
import cn.com.qytx.platform.utils.spring.SpringUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：工作流手机接口实现
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午2:02:16 
 * 修改日期：下午2:02:16 
 * 修改列表：
 */
@Service("jbpmMobileService")
@Transactional
public class JbpmMobileServiceImpl implements JbpmMobileService {
	
	private Logger log = LoggerFactory.getLogger(JbpmMobileServiceImpl.class);
	
    private final int FILE_SIZE_B = 1024;
    private final int FILE_SIZE_K =1048576;
    private final int FILE_SIZE_M =1073741824;

	@Resource(name="processEngine")
	private ProcessEngine processEngine;
	@Resource(name="formPropertyValueService")
	private IFormPropertyValue formPropertyValueService;
	@Resource(name="formPropertiesService")
	private IFormProperties formPropertyService;
	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	@Resource(name="jbpmAppService")
	private IJbpmApp jbpmAppService;
	@Resource(name = "workFlowService")
	IWorkFlowService workFlowService; 
	@Resource(name="formAttributeService")
	IFormAttribute formAttributeService;
	@Resource(name="nodeFormAttributeService")
	NodeFormAttributeService nodeFormAttributeService;
	@Resource(name="userService")
	IUser userService;
	@Resource(name="workflowVarDao")
	WorkflowVarDao workflowVarDao;
	@Resource(name="groupUserService")
	IGroupUser  groupUserService;
	@Resource(name="formCategoryService")
	private IFormCategory formCategoryService;
	@Resource(name="groupDao")
	private GroupDao<GroupInfo> groupDao;
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	@Resource(name="formAuthorityService")
	IFormAuthority formAuthorityService;
	@Resource
	private NodeFormAttributeDao nodeFormAttributeDao;
	@Resource
	private IMyProcessed myProcessedService;
	
	private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public List<DataElementImplMyStart> findByStart(String loginName, Pageable page) {
		// TODO Auto-generated method stub
		List<DataElementImplMyStart> result = new ArrayList<DataElementImplMyStart>();
		List<WorkFlowView> viewlist = workFlowService.findByStartList(loginName,"", page).getContent();
		List<UserInfo> users = userService.findAll();
		if(viewlist!=null){
			for(int i=0; i<viewlist.size(); i++){
				WorkFlowView wfv = viewlist.get(i);
				DataElementImplMyStart myStart = new DataElementImplMyStart();
				myStart.setFlowName(wfv.getJobName());
				myStart.setTime(SDF.format(wfv.getProcessStart()));
				myStart.setCurentTaskName(wfv.getCurrentTaskName());
				if(wfv.getCurrentUser()!=null){
					myStart.setCurrentTaskAssigner(wfv.getCurrentUser());
				}else{
					List<Task> tasklist = processEngine.getTaskService().createTaskQuery().processInstanceId(wfv.getInstanceId()).list();
					if(tasklist!=null){
						String loginNames = "";
						String names = "";
						for (Iterator it = tasklist.iterator(); it.hasNext();) {
							Task temp1 = (Task) it.next();
							if(temp1.getAssignee()!=null){
								names+=userService.findByLoginName(temp1.getAssignee());
							}
							if(it.hasNext()){
								names+=",";
							}
						}
						myStart.setCurrentTaskAssigner(names);
					}
					
				}
				myStart.setState(getCurrentState(wfv.getCurrentState()));
				myStart.setInstanceId(wfv.getInstanceId());
				int attachSize = 0 ;
				List<Attachment> map = jbpmAppService.getAttachList(wfv.getInstanceId());
				if(map != null){
					attachSize = map.size();
				}
				myStart.setAttachFileSize(attachSize);
				//驳回可以删除
				if(wfv.getCurrentState().equals("endnoagree")){
					myStart.setCanDelete(true);
					
				//如果是审批中，且任务还没有被除发起人外第二个人执行的话可以删除
				}else if(wfv.getCurrentState().equals("approve")){
					boolean canDelete = jbpmAppService.canDeleteInstance(wfv.getInstanceId());
					myStart.setCanDelete(canDelete);
				
				//其它状态不能删除
				}else{
					myStart.setCanDelete(false);
				}
				result.add(myStart);
			}
		}
		return result;
	}
	
	@Override
	public DataElementImplNotApproved findMyNotApproved(String loginName) {
		// TODO Auto-generated method stub
		DataElementImplNotApproved result = new DataElementImplNotApproved();
	/*	Page wait = new Page();
		Page susp = new Page();*/
		Pageable wait =new PageRequest(0,2);
		Pageable susp =new PageRequest(0,2);
		Page<WorkFlowView> w =workFlowService.findWaitProcessViewlist(loginName, SearchWaitProcessTaskList.PROCESS_STATE_ACTIVE,"", wait);
		Page<WorkFlowView> s =workFlowService.findWaitProcessViewlist(loginName, SearchWaitProcessTaskList.PROCESS_STATE_SUSPEND,"", susp);
		/*result.setNotApprove(wait.getTotalCount());
		result.setSuspend(susp.getTotalCount());*/
		result.setNotApprove((int) w.getTotalElements());
		result.setSuspend((int)s.getTotalElements());
		return result;
	}

	//待审批列表
	@Override
	public List<DataElementImplWaitApprove> findMyWaitApprove(int type,String loginName,
			Pageable page) {
		List<UserInfo> users = userService.findAll();
		List<DataElementImplWaitApprove> result = new ArrayList<DataElementImplWaitApprove>();
		List<WorkFlowView> viewlist = null;
		if(type == 0){
			viewlist = workFlowService.findWaitProcessViewlist(loginName, SearchWaitProcessTaskList.PROCESS_STATE_ACTIVE,"",page).getContent();
		}else if(type == 1){
			viewlist = workFlowService.findWaitProcessViewlist(loginName, SearchWaitProcessTaskList.PROCESS_STATE_SUSPEND,"", page).getContent();
		}
		if(viewlist!=null){
			for(int i=0; i<viewlist.size(); i++){
				WorkFlowView view = viewlist.get(i);
				DataElementImplWaitApprove temp = new DataElementImplWaitApprove();
				temp.setFlowName(view.getJobName());
				UserInfo ui = getUserInfoByLoginName(users, view.getCreater());
				temp.setStarter(ui.getUserName());
				temp.setTime(SDF.format(view.getTaskStart()));
				temp.setLastTaskName(view.getBeforeTaskName());
				temp.setLastTaskAssigner(view.getBeforeUser());
				temp.setInstanceId(view.getInstanceId());
				int attachSize = 0 ;
				List<Attachment> map = jbpmAppService.getAttachList(view.getInstanceId());
				if(map != null){
					attachSize = map.size();
				}
				temp.setAttachFileSize(attachSize);
				boolean canDelete = jbpmAppService.canDeleteInstance(view.getInstanceId());
				temp.setCanDelete(canDelete);
				result.add(temp);
			}
		}
		return result;
	}
	
	private UserInfo getUserInfoByLoginName(List<UserInfo> list,String loginName){
		for(int i=0; i<list.size(); i++){
			UserInfo ui = list.get(i);
			if(ui.getLoginName().equals(loginName)){
				return ui;
			}
		}
		return null;
	}
	
	

	@Override
	public List<DataElementImplMyApproved> findMyApproved(String loginName,
			Pageable page) {
		// TODO Auto-generated method stub
		List<DataElementImplMyApproved> result = new ArrayList<DataElementImplMyApproved>();
		List<WorkFlowView> list = workFlowService.findByApprovedList(loginName,"", page).getContent();
		if(list!=null){
			for(int i=0; i<list.size(); i++){
				WorkFlowView view = list.get(i);
				DataElementImplMyApproved temp = new DataElementImplMyApproved();
				temp.setFlowName(view.getJobName());
				temp.setStartTime(SDF.format(view.getProcessStart()));
				temp.setStarter(view.getCreater());
				temp.setApproveTime(SDF.format(view.getTaskEnd()));
				temp.setCurentTaskName(view.getCurrentTaskName());
				if(view.getCurrentUser()!=null){
					temp.setCurrentTaskAssigner(view.getCurrentUser());
				}else{
					List<Task> tasklist = processEngine.getTaskService().createTaskQuery().processInstanceId(view.getInstanceId()).list();
					if(tasklist!=null){
						String loginNames = "";
						String names = "";
						for (Iterator it = tasklist.iterator(); it.hasNext();) {
							Task temp1 = (Task) it.next();
							if(temp1.getAssignee()!=null){
								names+=userService.findByLoginName(temp1.getAssignee());
							}
							if(it.hasNext()){
								names+=",";
							}
						}
						/*for(Iterator it = tasklist.iterator(); it.hasNext();){
							Task temp1 = (Task) it.next();
							if(temp1.getAssignee()!=null){
								loginNames+=temp1.getAssignee();
							}
							if(it.hasNext()){
								loginNames+=",";
							}
						}
						List<UserInfo> us = userService.findByLoginNames(loginNames);
						for(Iterator it = us.iterator(); it.hasNext();){
							UserInfo ui = (UserInfo) it.next();
							names+=ui.getUserName();
							if(it.hasNext()){
								names+=",";
							}
						}*/
						temp.setCurrentTaskAssigner(names);
					}
					
				}
				
				temp.setCurrentTaskAssigner(view.getCurrentUser());
				temp.setState(getCurrentState(view.getCurrentState()));
				temp.setInstanceId(view.getInstanceId());
				temp.setCurrentTask(view.getCurrentTaskName());
				int attachSize = 0 ;
				List<Attachment> map = jbpmAppService.getAttachList(view.getInstanceId());
				if(map != null){
					attachSize = map.size();
				}
				temp.setAttachFileSize(attachSize);
				result.add(temp);
			}
			
		}
		return result;
	}
	
	/**
	 * 获取流程实例下的附件信息
	 */
	public Map getAttachMap(Map varMap) {
		Map<Integer,String> res =null;
		if(varMap!=null){
			 String str=varMap.get("attach")!=null?varMap.get("attach").toString():""; 
			 Gson gson = new Gson();
			 res =gson.fromJson(str, Map.class);
		}
		return res;
	}


	private String getCurrentState(String currentState) {
		String res = "";
		if("start".equals(currentState)){
			res="未审批";
		}
		if("approve".equals(currentState)){
			res="审批中";
		}
		if("suspend".equals(currentState) ){
			res="已挂起";
		}
		if("end".equals(currentState) ){
			res="完结";
		}
		if("endnoagree".equals(currentState) ){
			res="完结（不同意）";
		}
		if("stop".equals(currentState) ){
			res="已中止";
		}
		if(JpdlInterface.PROCESS_STATE_REPEAL.equals(currentState)){
			res = "已撤销";
		}
		return res;
	}

	/*
	 * update by 贾永强，审批意见控件和阅读控件的内容需要从流程变量中获取
	 */
	@Override
	public DataElementImplDetail findByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		DataElementImplDetail result = new DataElementImplDetail();
	
		//获取表单信息
		List<HtmlElement> htmlElements = getHtmlElementsForFlow(instanceId) ;
		result.setDetail(htmlElements);
		
		//附件信息
		getAttachs(instanceId, result);
		
		//获取审批历史
		List<ApproveHistory> historylist = getApproveHistory(instanceId);
		result.addAllHistory(historylist);
		
		String totalState = "";
		WorkflowVar wfv = workflowVarDao.findByInstanceId(instanceId);
		result.setProcessId(String.valueOf(wfv.getProcessAttributeId()));
		String loginName = wfv.getCreater();
		log.info(">>>>>>>>>>>>>loginName:"+loginName);
		log.info(">>>>>>>>>>>>>单位ID："+SystemContextHolder.getSessionContext().getUser().getCompanyId());
		UserInfo u = userService.getUserInfoByLoginName(SystemContextHolder.getSessionContext().getUser().getCompanyId(), loginName);
		if(u!=null){
			result.setCreateUserPhoto(u.getPhoto());
			result.setCreateUserName(u.getUserName());
			log.info(">>>>>>>>>>用户头像地址:"+u.getPhoto());
			log.info(">>>>>>>>>>用户姓名:"+u.getUserName());
		}else{
			log.info(">>>>>>>>用户为空");
		}
		
		if(wfv.getCurrentState().equals("end")){
			totalState = ApproveHistory.APPROVED;
		}else if(wfv.getCurrentState().equals("approve")){
			totalState = ApproveHistory.APPROVE;
			Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).assignee(SystemContextHolder.getSessionContext().getUser().getLoginName()).uniqueResult();
			if(task!=null){
				result.setTaskId(task.getId());
			}
		}else if(wfv.getCurrentState().equals("endnoagree")){
			totalState = ApproveHistory.ROOLBACK;
		}else if(wfv.getCurrentState().equals("repeal")){
			totalState = ApproveHistory.REPEAL;
		}
		result.setTotalState(totalState);
		
		
		return result;
	}
	
	/**
	 * 功能：获取自定义表单的内容
	 * @param
	 * @return
	 * @throws   
	 */
	public List<HtmlElement> getHtmlElementsForFlow(String instanceId){
		ProcessInstance pi = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
		int processId = 0;
		int formId = getFormIdByInstanceId(instanceId);
		if(pi == null){
			String processAttributeId = processEngine.getHistoryService().getVariable(instanceId, "processAttributeId").toString();
			processId = Integer.parseInt(processAttributeId);
		}else{
			String processAttributeId =  processEngine.getExecutionService().getVariable(instanceId, "processAttributeId").toString();
			processId = Integer.parseInt(processAttributeId);
		}
		//如果流程已结束则只做第一步查询，否则第2,3,4都要做
		NodeFormAttribute nodeForm  = null;
		if(pi!=null){
			String taskName = pi.findActiveActivityNames().iterator().next();
			nodeForm = nodeFormAttributeService.findByProcessIdAndTaskName(processId, taskName);
		}
		//第一步先获取所有的表单属性和值，默认为不可编辑
		List<HtmlElement> htmlElements = getDefaultHtmlElement(instanceId, formId);
		//if(pi!=null && nodeForm!=null){
			//过滤表单属性
			filterHtmlElements(htmlElements, formId);
		//}
		
		//处理审批控件
		FormAttribute fa = formAttributeService.findById(formId);
		if(fa!=null){
    		Document doc = Jsoup.parse(formAttributeService.findById(formId).getFormSource());
    		filterApproveAttribute(htmlElements, doc,instanceId);
		}
		
		return htmlElements;
	}
	
	/**
	 * 功能：过滤表单属性，先去处理藏属性，然后处理不可编辑属性，最后处理各种可编辑的表单属性
	 * @param
	 * @return
	 * @throws   
	 */
	private void filterHtmlElements(List<HtmlElement>htmlElements,int formId ){
		
		//第二步去掉需要隐藏的属性
//		String secrets = nodeForm.getSecretProperties();
//		if(secrets!=null && !secrets.equals("")){
//			String[] strs = secrets.split(",");
//			if(strs!=null){
//				for(int i=0; i<strs.length; i++){
//					String temp = strs[i];
//					if(temp!=null){
//						int id = Integer.parseInt(temp);
//						for(Iterator<HtmlElement> it = htmlElements.iterator(); it.hasNext();){
//							HtmlElement he = it.next();
//							if(he.getPropertyId() == id){
//								it.remove();
//							}
//						}
//					}
//				}
//			}
//		}
	
		//第三步判断哪些属性可编辑哪些不可编辑
//		String editProperties = nodeForm.getWriteableProperties();
//		if(editProperties!=null && !editProperties.equals("")){
//			String[] strs = editProperties.split(",");
//			if(strs!=null){
//				for(int i=0; i<strs.length; i++){
//					String temp = strs[i];
//					if(temp!=null){
//						int id = Integer.parseInt(temp);
//						for(Iterator<HtmlElement> it = htmlElements.iterator(); it.hasNext();){
//							HtmlElement he = it.next();
//							if(he.getPropertyId() == id){
//								he.setCanEdit(true);
//							}
//						}
//					}
//				}
//			}
//		}
	
		//第四部遍历可编辑字段，用JSOUP解析html,生成HTMLELEMENT对象
		FormAttribute formAttribute = formAttributeService.findById(formId);
		if(formAttribute!=null && formAttribute.getFormSource()!=null){
			Document doc = Jsoup.parse(formAttribute.getFormSource());
			for(int i=0; i<htmlElements.size(); i++){
				HtmlElement defaultElement = htmlElements.get(i);
				String name = defaultElement.getName();
				parseHtmlElement(doc,defaultElement, name);
			}
		}
		//重构明细控件
		recodeDetailWidget(htmlElements);
	}
	
	/*
	 * 重构明细控件
	 */
	private void recodeDetailWidget(List<HtmlElement> htmlElements){
		//处理明细控件
		Map<String,List<HtmlElement>> childrens = new HashMap<String, List<HtmlElement>>();//子控件
		Map<String,HtmlElement> details = new HashMap<String, HtmlElement>();//明细控件
		for(Iterator it = htmlElements.iterator(); it.hasNext();){
			HtmlElement he = (HtmlElement) it.next();
			if(he.getHtmlType()==null){
				continue;
			}
			if(he.getHtmlType().equals(HtmlElement.ELEMENT_TYPE_DETAIL)){
				details.put(he.getName(), he);
			}else{
				if(he.getParentName()!=null && he.getParentName().length()>0){
					List<HtmlElement> ls = childrens.get(he.getParentName());
					if(ls==null){
						ls = new ArrayList<HtmlElement>();
					}
					ls.add(he);
					childrens.put(he.getParentName(), ls);
					it.remove();
				}
			}
		}
		Set<Map.Entry<String, List<HtmlElement>>> childrenSet = childrens.entrySet();
		Set<Map.Entry<String, HtmlElement>> detailSet = details.entrySet();
		for(Iterator<Map.Entry<String, HtmlElement>> it = detailSet.iterator(); it.hasNext();){
			Map.Entry<String, HtmlElement> temp = it.next();
			String name = temp.getKey();
			HtmlElement d = temp.getValue();
			for(Iterator<Map.Entry<String, List<HtmlElement>>> iter = childrenSet.iterator(); iter.hasNext();){
				Map.Entry<String, List<HtmlElement>> temp1 = iter.next();
				String parent = temp1.getKey();
				List<HtmlElement> cs = temp1.getValue();
				if(name.equals("_"+parent+"_")){
					d.setDetails(cs);
				}
			}
		}
	}
	
	/**
	 * 功能：从HTML中解析出指定name属性的元素
	 * @param
	 * @return
	 * @throws   
	 */
	private void parseHtmlElement(Document doc,HtmlElement defaultElement,String elementName){
		
		//先判断INPUT标签
		Elements es = doc.select("input[name="+elementName+"]");
		if(es!=null){
			if(es.size()==1){
				Element temp = es.get(0);
				String type = temp.attr("type");
				if(type!=null){
					//update by jiayq,必填标识
					String isRequired = temp.attr("required");
					if(isRequired != null && isRequired.equals("required")){
						defaultElement.setRequired(true);
					}
					
					String calendarRange = temp.attr("calendarrange_expr");
					if(!StringUtil.isBlank(calendarRange)){
						defaultElement.setCalendarRangeExpr(calendarRange);
					}
					
					//单行输入框
					if(type.equals("text") || type == null || type.equals("")){
						//update by jiayq,更新数据类型，number/string
						String contentType = temp.attr("contentType");
						defaultElement.setContentType(contentType);
						//日历控件
						if(temp.hasAttr("dateWidget")){
							defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_CALENDAR);
							
							String dateFormat = temp.attr("date_format");
							defaultElement.setDateFormat(dateFormat);
							
							String calendarRangeFlag = temp.attr("calendarRangeFlag");
							defaultElement.setCalendarRangeFlag(calendarRangeFlag);
							
							String othername = temp.attr("other");
							defaultElement.setOther(othername);
							
							//选择人员控件
						}else if(temp.hasAttr("selectuser") ){
							defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_USERS);
							//选择部门控件
						}else if(temp.hasAttr("selectgroup")){
							defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_GROUP);
							//计算控件
						}else if(temp.hasAttr("calu") && temp.attr("calu").equals("1")){
							defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_CACU);
							defaultElement.setExpr(temp.attr("expr"));
							//阅读控件
						}else if(temp.hasAttr("readflag") && temp.attr("readflag").equals("readflag")){
							defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_READERS);
						}else{
							defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_INPUT);
							defaultElement.setDefaltValue(temp.attr("value"));
						}
					}else if(type.equals("radio")){
						String val = temp.attr("value");
						defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_RADIO);
						defaultElement.addRadios(val);
					}else if(type.equals("checkbox")){
						String val = temp.attr("value");
						defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_CHECKBOX);
						defaultElement.addRadios(val);
					}else if(type.equals("button")){
						String isDetail  = temp.attr("detail");
						if(isDetail.equals("detail")){
							defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_DETAIL);
						}
					}else {
						defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_INPUT);
					}
				}
			}else{//可能是radio
				if(es.first()!=null && es.first().attr("type").equals("radio")){
					defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_RADIO);
					String isRequired = es.first().attr("required");
					if(isRequired != null && isRequired.equals("required")){
						defaultElement.setRequired(true);
					}
					for(int i=0; i<es.size(); i++){
						String val = es.get(i).attr("value");
						defaultElement.addRadios(val);
						if(es.get(i).hasAttr("checked")){
							defaultElement.setDefaltValue(val);
						}
					}
				}
				//可能是checkbox
				if(es.first()!=null && es.first().attr("type").equals("checkbox")){
					defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_CHECKBOX);
					String isRequired = es.first().attr("required");
					if(isRequired != null && isRequired.equals("required")){
						defaultElement.setRequired(true);
					}
					for(int i=0; i<es.size(); i++){
						String val = es.get(i).attr("value");
						defaultElement.addRadios(val);
						if(es.get(i).hasAttr("checked")){
							String dv = defaultElement.getDefaltValue()==null?"":defaultElement.getDefaltValue();
							defaultElement.setDefaltValue(dv+","+val);
						}
					}
				}
			}
		}
		
		//判断textarea标签
		Elements textareaEles =  doc.select("textarea[name="+elementName+"]");
		if(textareaEles!=null && textareaEles.first()!=null){
			Element temp = textareaEles.first();
			//审批意见控件
			if(temp.hasAttr("adviceflag") && temp.attr("adviceflag").equals("advice")){
				defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_APPROVE);
			}else{
				defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_TEXTAREA);
			}
		
			//update by jiayq,必填标识
			String isRequired = temp.attr("required");
			if(isRequired != null && isRequired.equals("required")){
				defaultElement.setRequired(true);
			}
			defaultElement.setDefaltValue(temp.text());
		}
		
		//判断下拉菜单
		Elements selectEle = doc.select("select[name="+elementName+"]");
		if(selectEle!=null){
			Element select  = selectEle.first();
			if(select!=null){
				Elements ops = select.select("option");
				defaultElement.setHtmlType(HtmlElement.ELEMENT_TYPE_SELECT);
				if(ops!=null){
					for(int i=0; i<ops.size(); i++){
						Element op = ops.get(i);
						String text = op.text();
						defaultElement.addOptions(text);
						if(op.hasAttr("selected")){
							defaultElement.setDefaltValue(text);
						}
					}
				}
				
				//update by jiayq,必填标识
				String isRequired = select.attr("required");
				if(isRequired != null && isRequired.equals("required")){
					defaultElement.setRequired(true);
				}
			}
		}
		if(defaultElement.getHtmlType()==null){
//			System.out.println(defaultElement.getName()+"===========控件名称，控件类型为空");
		}
	}
	
	/**
	 * 功能：获取表单元素的name和值属性
	 * @param
	 * @return
	 * @throws   
	 */
	private List<HtmlElement> getDefaultHtmlElement(String instanceId,int formId){
		List<HtmlElement> htmlElements = new ArrayList<HtmlElement>();
		List<FormPropertyValue> propertyValues = null;
		if(instanceId!=null){
			propertyValues = formPropertyValueService.findByInstanceId(instanceId);
		}
		List<FormProperties> formProperties = formPropertyService.findByFormId(formId);
		if(formProperties!=null){
			for(int i=0; i<formProperties.size(); i++){
				FormProperties fp = formProperties.get(i);
				HtmlElement dhe = new HtmlElement();
				dhe.setCanEdit(true);
				dhe.setParentName(fp.getParentName());
				dhe.setCnName(fp.getPropertyNameCh());
				dhe.setName(fp.getPropertyName());
				dhe.setPropertyId(fp.getPropertyId());
				if(propertyValues!=null){
					for(int j=0; j<propertyValues.size(); j++){
						FormPropertyValue fpv = propertyValues.get(j);
						if(fp.getPropertyId().intValue() == fpv.getBeanPropertyId().intValue()){
							dhe.setValue(fpv.getBeanValue());
						}
					}
				}
				htmlElements.add(dhe);
			}
		}
		return getClientShowHtmlElement(htmlElements, formId);
	}
	
	/**
	 * 功能：获取附件列表
	 * @param
	 * @return
	 * @throws   
	 */
	public void getAttachs(String instanceId,DataElementImplDetail result){
		List<Attachment> attachs=jbpmAppService.getAttachList(instanceId);
		if(attachs!=null){
			for(Iterator it = (Iterator) attachs.iterator(); it.hasNext();){
				Attachment temp = (Attachment) it.next();
				String id = temp.getId()+"";
				String name = temp.getAttachName();
				String fileSize=	formetFileSize(temp.getAttachSize());
		        result.addAttach(name, id, fileSize);
			}
		}
	}
	
	/**
	 * 功能：获取审批历史
	 * @param
	 * @return
	 * @throws   
	 */
	private List<ApproveHistory> getApproveHistory(String instanceId){
		List<ApproveHistory> historylist = new ArrayList<ApproveHistory>();
		ProcessInstance pi = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
		int processId = 0;
		if(pi == null){
			String processAttributeId = processEngine.getHistoryService().getVariable(instanceId, "processAttributeId").toString();
			processId = Integer.parseInt(processAttributeId);
		}else{
			String processAttributeId =processEngine.getExecutionService().getVariable(instanceId, "processAttributeId").toString();
			processId = Integer.parseInt(processAttributeId);
		}
		List<JbpmNodeState> nodesState = workFlowService.findJbpmNodesState(instanceId, processId);
		log.info("<<<<<<<<<<<<<<<<<<<<<<<流程节点列表:"+new Gson().toJson(nodesState));
//		List<MyProcessed> processedlist = myProcessedService.findByInstanceId(instanceId);
		WorkflowVar workflowVar = workflowVarDao.findByInstanceId(instanceId);
		for(int i=0; i<nodesState.size(); i++){
			JbpmNodeState jns = nodesState.get(i);
			ApproveHistory ah = new ApproveHistory();
			ah.setIndex(i);
			ah.setTaskName(jns.getNodeName());
			ah.setApprover(jns.getUserName());
			ah.setState("0");
			if(jns.isCompleted()){
				ah.setState("1");
			}
			ah.setTime(jns.getApproveTime());
			ah.setAdvice(jns.getResult());
			ah.setContent(jns.getContent());
			
			//add by jiayq,添加当前步骤的处理人。
			if(workflowVar.getCurrentTaskName().equals(ah.getTaskName())){
				String uname = workflowVar.getCurrentUser();
				ah.setApprover(uname);
				ah.setApproveResult(ApproveHistory.APPROVE);
			}
			
			//转换历史节点的审批状态
			if(i==0){
				ah.setApproveResult(ApproveHistory.APPLY);
			}else{
				if(jns.getResult().equals(JpdlInterface.APPROVE_RESULT_AGREE)){
					ah.setApproveResult(ApproveHistory.APPROVED);
				}else if(jns.getResult().equals(JpdlInterface.APPROVE_RESULT_ROLLBACK)){
					ah.setApproveResult(ApproveHistory.ROOLBACK);
				}else if(jns.getResult().equals(JpdlInterface.APPROVE_RESULT_REPEAL)){
					ah.setApproveResult(ApproveHistory.REPEAL);
				}
			}
			
			
			historylist.add(ah);
		}
		return historylist;
	}
	
	
	
	   /**
  * 
  * 功能：根据大小得到b k  m  g
  * @param fileS
  * @return
  */
 public String formetFileSize(long fileS) {//转换文件大小
  //   DecimalFormat df = new DecimalFormat("#.00");
     DecimalFormat df = new DecimalFormat("0.00");
     String fileSizeString = "";
     if (fileS < FILE_SIZE_B) {
         fileSizeString = df.format((double) fileS) + "B";
     } else if (fileS < FILE_SIZE_K) {
         fileSizeString = df.format((double) fileS / FILE_SIZE_B) + "K";
     } else if (fileS < FILE_SIZE_M) {
         fileSizeString = df.format((double) fileS / FILE_SIZE_K) + "M";
     } else {
         fileSizeString = df.format((double) fileS / FILE_SIZE_M) + "G";
     }
     return fileSizeString;
 }
	
	private int getFormIdByInstanceId(String instanceId){
		HistoryProcessInstance instance = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
		if(instance==null){
			return 0;
		}
		String processDefineId = instance.getProcessDefinitionId();
		ProcessAttribute pa = processAttributeService.findByDefineId(processDefineId);
		int formId = pa.getFormId();
		return formId;
	}

	@Override
	public void deleteInstance(String instance) {
		// TODO Auto-generated method stub
		workFlowService.deleteProcessInstance(instance);
		workflowVarDao.deleteWorkflowVar(instance);
	}

	@Override
	public int viewForm(String instanceId) {
		// TODO Auto-generated method stub
		int formId = getFormIdByInstanceId(instanceId);
		return formId;
	}

	//获取下一步信息
	/**
	 * 功能：先获取当前任务，判断当前任务是否是会签，如果是会签，则判断子任务的数量，如果只有一个子任务
	 * 则允许选择下一步处理人，否则不允许选人;如果不是会签，则允许选人。如果是最后一个任务也不允许选人。
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public DataElementImplNextTaskInfo getNextTaskInfo(String instanceId,String userId) {
		// TODO Auto-generated method stub
		TaskImpl currentTask = (TaskImpl) processEngine.getTaskService().createTaskQuery()
				.processInstanceId(instanceId).assignee(userId).uniqueResult();
		DataElementImplNextTaskInfo info = new DataElementImplNextTaskInfo();
		info.setCurrentTaskId(currentTask.getId());
		
		String nextTaskName = "";//下一步任务名称
		boolean assignerIsShow = true;
		boolean onlySelectOne = true;//默认是只选中一个人
		String defaultAssignerName = "";
		String defaultAssignerId = "";
		
		boolean isSign = isSign(instanceId, currentTask.getActivityName());
		
		//如果不是会签
		if(!isSign){
			Set<String> set = processEngine.getTaskService().getOutcomes(currentTask.getId());
			if(set!=null && set.size()>0){
				String temp = set.iterator().next();
				if(temp!=null){
					if(temp.endsWith("结束")){
						assignerIsShow  = false;
					}else{
						assignerIsShow = true;
					}
					String[] strs = temp.split(" ");
					if(strs!=null && strs.length>1){
						nextTaskName = strs[1];
					}
					getCandidate(info, instanceId, nextTaskName);
					//判断下一步是否是会签
					boolean nextTaskIsSign = isSign(instanceId,nextTaskName);
					//如果是会签，选多人
					if(nextTaskIsSign){
						onlySelectOne = false;
					}else{//非会签，设置默认人
						if(info.getCandidate().size() == 1){
							defaultAssignerName = info.getCandidate().get(0).getName();
							defaultAssignerId = info.getCandidate().get(0).getId();
						}
					}
				}
			}
			
		}else{//如果是会签
			TaskImpl parentTask = currentTask.getSuperTask();
			Set<String> outs = processEngine.getTaskService().getOutcomes(parentTask.getId());
			nextTaskName = outs.iterator().next();
			getCandidate(info, instanceId, nextTaskName);
			Set<Task> joinTasks = parentTask.getSubTasks();
			if(joinTasks.size()>1){
				assignerIsShow = false;
			}
			boolean nextIsSign = isSign(instanceId,nextTaskName);
			if(nextIsSign){
				onlySelectOne = false;
			}else{
				if(info.getCandidate().size() == 1){
					defaultAssignerName = info.getCandidate().get(0).getName();
					defaultAssignerId = info.getCandidate().get(0).getId();
				}
			}
		}
		info.setNextTaskName(nextTaskName);
		info.setOnlySelectOne(onlySelectOne);
		return info;
	}
	
	private boolean isSign(String instanceId,String taskName){
		boolean result = false;//默认不是会签
		ProcessInstance instance = processEngine.getExecutionService().createProcessInstanceQuery()
				.processInstanceId(instanceId).uniqueResult();
		String defineId = instance.getProcessDefinitionId();
		ProcessAttribute pa = processAttributeService.findByDefineId(defineId);
		if(pa!=null){
			int pid = pa.getId();
			NodeFormAttribute nfa = nodeFormAttributeService.findByProcessIdAndTaskName(pid, taskName);
			if(nfa!=null){
				String type = nfa.getNodeType();
				if(type.equals("mutilSign")){
					result = true;
				}
			}
		}
		
		return result;
	}
	
	private void getCandidate(DataElementImplNextTaskInfo info,String instanceId,String toTaskName){
	    String taskName = toTaskName;
		ProcessInstance instance = processEngine.getExecutionService().createProcessInstanceQuery()
				.processInstanceId(instanceId).uniqueResult();
		String defineId = instance.getProcessDefinitionId();
		ProcessAttribute pa = processAttributeService.findByDefineId(defineId);
		if(pa!=null){
			int pid = pa.getId();
			if(taskName!=null&&taskName.startsWith("TO ")){
			    String[] strs = taskName.split(" ");
				taskName = strs[1];
			}
			NodeFormAttribute nfa = nodeFormAttributeService.findByProcessIdAndTaskName(pid, taskName);
			if(nfa != null){
				List<UserInfo> list = userService.findUsersByIds(nfa.getCandidate(),nfa.getDepts(), nfa.getRoles());
				if(list!=null){
					for(int i=0; i<list.size(); i++){
						info.addUser(list.get(i).getLoginName(),list.get(i).getUserName(),list.get(i).getPhoto());
					}
				}
			}
		}
	}
	
	private UserInfo getUserInfoByLoginName(String loginName){
		IUser userService = (IUser) SpringUtil.getBean("userService");
		return userService.findByLoginName(loginName);
	}

	@Override
	public int getApproveCount(String string) {
		return jbpmAppService.getApproveCount(string);
	}


	//如果下一步是结束节点或者当前节点是会签节点且并不是最后一个会签人，则不显示下一步信息。
	@Override
    public List<DataElementImplNextTaskInfo> getNextTaskInfos(
            String instanceId, String userId) {
	    // TODO Auto-generated method stub
		 int currentTaskCount = jbpmAppService.getCurrentTaskCount(instanceId);
		 
		 //会签，直接返回;不是会签,继续向下走
		 if(currentTaskCount>2){
			 return null;
		 }
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).assignee(userId).uniqueResult();
		String processIdStr = (String) processEngine.getExecutionService().getVariable(instanceId, "processAttributeId");
		int processId = Integer.parseInt(processIdStr); 
		
		List<Map<String,String>> list = workFlowService.findApplyNextNodes(task.getId(), processId);
		if(list.size()==1){
			Map<String,String> map = list.get(0);
			if(map.get("name").equals("结束")){
				return null;
			}
		}
		List<DataElementImplNextTaskInfo> nextTaskInfo = new ArrayList<DataElementImplNextTaskInfo>();
		for(int i=0; i<list.size(); i++){
			DataElementImplNextTaskInfo temp = new DataElementImplNextTaskInfo();
			Map map = list.get(i);
			temp.setCurrentTaskId(task.getId());
			temp.setNextTaskName((String)map.get("name"));
			//默认只许选一个人
			temp.setOnlySelectOne(true);
			if(map.get("type").equals("mutilSign")){
				temp.setOnlySelectOne(false);
			}
			getCandidate(temp, instanceId, temp.getNextTaskName());
			nextTaskInfo.add(temp);
		}
		return nextTaskInfo;
    }


	/**
	 * 功能：发起流程的时候，获取表单属性
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
    public List<HtmlElement> getStartFormProperties(int processId) {
	    // TODO Auto-generated method stub
		ProcessAttribute pa = processAttributeService.getProcessById(processId);
		
		//获取所有表单的基本属性
		List<HtmlElement> defaultHtmlElement = getDefaultHtmlElement(null, pa.getFormId());
		
		NodeFormAttribute nodeForm = nodeFormAttributeService.findByProcessIdAndTaskName(processId, "发起流程");
		filterHtmlElements(defaultHtmlElement, pa.getFormId());
		
		FormAttribute formAttribute = formAttributeService.findById(pa.getFormId());
		Document doc = Jsoup.parse(formAttribute.getFormSource());
		filterApproveAttribute(defaultHtmlElement, doc,null);
		
	    return defaultHtmlElement;
    }

	/**
	 * 功能：发起流程的时候获取下一步的任务信息
	 * @param processId:流程定义ID
	 * @return
	 * @throws   
	 */
	@Override
    public List<DataElementImplNextTaskInfo> getStartNextTaskInfos(int processId) {
	    // TODO Auto-generated method stub
		List<NodeFormAttribute> nodes = workFlowService.findStartSelectNode(processId);
		List<DataElementImplNextTaskInfo> result = new ArrayList<DataElementImplNextTaskInfo>();
		for(int i=0; i<nodes.size(); i++){
			NodeFormAttribute nfa = nodes.get(i);
			DataElementImplNextTaskInfo temp = new DataElementImplNextTaskInfo();
			temp.setNextTaskName(nfa.getNodeName());
			//默认只许选一个人
			temp.setOnlySelectOne(true);
			if(nfa.getNodeType().equals("mutilSign")){
				temp.setOnlySelectOne(false);
			}
			
			List<UserInfo> list = userService.findUsersByIds( nfa.getCandidate(),nfa.getDepts(), nfa.getRoles());
			
			if(list!=null){
				for(int j=0; j<list.size(); j++){
					temp.addUser(list.get(j).getLoginName(),list.get(j).getUserName(),list.get(j).getPhoto());
				}
			}
			
			result.add(temp);
		}
	    return result;
    }


	/**
	 * 功能：发起流程
	 * @param
	 * processId:流程定义Id
	 * formData:表单数据
	 * attachData:附件数据
	 * advice:审批意见
	 * nextAction:下一步流向
	 * nextUser:下一步任务处理人
	 * @return
	 * @throws   
	 */
	@Override
    public String startProcess(UserInfo ui,int processId, String formData,
            String attachData, String advice, String nextAction, String nextUser) {
	    // TODO Auto-generated method stub
		ProcessAttribute pa = processAttributeService.getProcessById(processId);
		String title = getStartProcessTitle(ui, processId);
		return jbpmAppService.startProcess(ui, title, formData, null, advice, processId, nextAction, nextUser);
    }
	
	/**
	 * 功能：获取流程标题
	 * @param
	 * @return
	 * @throws   
	 */
	private  String getStartProcessTitle(UserInfo userInfo,int processId){
		String userName="";
		String groupName="";
		if(userInfo!=null){
			userName = userInfo.getUserName();
			groupName = getGroupNameByUserInfo(userInfo);
		}
		ProcessAttribute pa = processAttributeService.getProcessById(processId);
		int categoryId = pa.getCategoryId();
		FormCategory fc = formCategoryService.findById(categoryId);
		String res = pa.generateProcessInstanceName(userName, groupName, fc.getCategoryName());
		return res;
	}
	private String getGroupNameByUserInfo(UserInfo userInfo) {
		String res="";
		GroupInfo g = groupDao.findOne(userInfo.getGroupId());
		if(g!=null){
			res = g.getGroupName();
		}
		return res;
	}

	@Override
    public List<HtmlElement> getHtmlElementsForDoc(String instanceId,String userId) {
		//ProcessInstance pi = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
		int formId = 0;
		FormAttribute fa = publicDomService.getFormSourceByInstanceId(instanceId);
		if(fa!=null){
		    formId = fa.getFormId();
		}else{
		    return new ArrayList<HtmlElement>();
		}
		//第一步先获取所有的表单属性和值，默认为不可编辑
		List<HtmlElement> htmlElements = getDefaultHtmlElement(instanceId, formId);
		//过滤表单属性，设置表单能否编辑
		//update by jiayq,采用新的表单权限
		//List<String> auList = formAuthorityService.findHasAuthorityProperties(formId+"", userId);
		String str = workFlowService.getVariablebyInstance(instanceId, "gongwenType_id");;
		int gongwenTypeId = (StringUtils.isEmpty(str)?0:Integer.parseInt(str));
		List<Task> tasklist = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
		NodeFormAttribute nfa = null;
		if(tasklist!=null && tasklist.size()>0){
			nfa = nodeFormAttributeDao.findByProcessIdAndName(gongwenTypeId*-1, tasklist.get(0).getName());
		}
		if(nfa!=null){
			//过滤可编辑字段
			filterWriterables(htmlElements, nfa);
			//过滤隐藏字段
			filterSecrets(htmlElements, nfa);
		}
		
		//如果能编辑，需要获取html类型，审批控件特殊，即使不能编辑，也需要返回类型
		FormAttribute formAttribute = formAttributeService.findById(formId);
		if(formAttribute!=null && formAttribute.getFormSource()!=null){
			Document doc = Jsoup.parse(formAttribute.getFormSource());
			for(int i=0; i<htmlElements.size(); i++){
				HtmlElement defaultElement = htmlElements.get(i);
				String name = defaultElement.getName();
				//if(defaultElement.isCanEdit() == true){
					parseHtmlElement(doc,defaultElement, name);
				//}
			}
			
			filterApproveAttribute(htmlElements, doc,instanceId);
		}
		return htmlElements;
    }
	
	/**过滤可写字段
	 * @param htmlElements
	 * @param nfa
	 */
	private void filterWriterables(List<HtmlElement> htmlElements,NodeFormAttribute nfa){
		String writers = nfa.getWriteableProperties();
		List<FormProperties> properties = nodeFormAttributeService.findFormPropertiesByIds(writers);
		if(properties!=null){
			for(int i=0; i<properties.size(); i++){
				FormProperties fp = properties.get(i);
				for(int j=0; j<htmlElements.size(); j++){
					HtmlElement he = htmlElements.get(j);
					if(he.getName().equals(fp.getPropertyName())){
						he.setCanEdit(true);
						break;
					}
				}
			}
		}
	}
	
	/**过滤隐藏字段
	 * @param htmlElements
	 * @param nfa
	 */
	private void filterSecrets(List<HtmlElement> htmlElements,NodeFormAttribute nfa){
		String secrets = nfa.getSecretProperties();
		List<FormProperties> ss = nodeFormAttributeService.findFormPropertiesByIds(secrets);
		if(ss != null){
			for(int i=0; i<ss.size(); i++){
				FormProperties fp = ss.get(i);
				for(Iterator it = htmlElements.iterator(); it.hasNext();){
					HtmlElement he = (HtmlElement) it.next();
					if(he.getName().equals(fp.getPropertyName())){
						it.remove();
						break;
					}
				}
			}
		}
	}
	
	//设置审批控件的HTMLTYPE
	private void filterApproveAttribute(List<HtmlElement> htmlElements,Document doc,String instanceId){
		List<String> rs = getAdviceNameList(doc);
		for(int i=0; i<rs.size(); i++){
			String tname = rs.get(i);
			for(int j=0; j<htmlElements.size(); j++){
				if(htmlElements.get(j).getName().equals(tname)){
					htmlElements.get(j).setHtmlType(HtmlElement.ELEMENT_TYPE_APPROVE);
					int count = getApproveWidgetCount(instanceId,htmlElements.get(j).getCnName());
					htmlElements.get(j).setValue(count+"个");
				}
			}
		}
	}
	
	//获取表单中的审批控件类型
	private List<String> getAdviceNameList(Document doc){
		List<String> list = new ArrayList<String>();
		Elements es = doc.select("textarea[adviceflag=advice]");
		for(int i=0; i<es.size(); i++){
			Element e = es.get(i);
			String name = e.attr("name");
			list.add(name);
		}
		return list;
	}
	
	/**
	 * 功能：手机客户端不显示的控件过滤掉
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	private List<HtmlElement> getClientShowHtmlElement(List<HtmlElement> list,int formId){
		List<HtmlElement> htmlElements = new ArrayList<HtmlElement>();
		FormAttribute fa = formAttributeService.findOne(formId);
		if(fa == null){
		    return new ArrayList<HtmlElement>();
		}
		Document doc = Jsoup.parse(fa.getFormSource());
		for(int i=0; i<list.size();i++){
			HtmlElement he = list.get(i);
			String name = he.getName();
			Elements es = doc.select("[clientShow=no][name="+name+"]");
			if(es==null || es.size()==0){
				htmlElements.add(he);
			}
			
		}
		return htmlElements;
	}
	
	/**
	 * 功能：获取指定控件的审批意见数量
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	private int getApproveWidgetCount(String instanceId,String editorName){
	    int count = 0 ;
	    if(instanceId!=null){
    	    String advicelist = workFlowService.getVariablebyInstance(instanceId, "tdAdvice");
    	    List<JbpmAdvice> list = null;
    	    Gson gson = new Gson();
            if(advicelist!=null){
                list = gson.fromJson(advicelist.toString(), new TypeToken<List<JbpmAdvice>>(){}.getType());
            }
            if(list!=null){
                for(int i=0; i<list.size(); i++){
                    if(list.get(i).getEditorName().equals(editorName)){
                        count++;
                    }
                }
            }
	    }
        return count;
	}
}

class Attach{
	private String attachName;

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
}

class SearchHistory implements org.jbpm.api.cmd.Command<List>{

	private String instanceId;
	
	public SearchHistory(String instanceId) {
		super();
		this.instanceId = instanceId;
	}

	@Override
	public List execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select JHT.ASSIGNEE_,JHA.ACTIVITY_NAME_,JHT.STATE_,JHT.DBID_,(convert(datetime,JHT.END_,120))   from JBPM4_HIST_TASK JHT,JBPM4_HIST_ACTINST JHA WHERE JHT.DBID_ = JHA.HTASK_ AND JHT.EXECUTION_ = ?";
		DbSessionImpl impl = (DbSessionImpl) environment.get(DbSession.class);
		Session session = impl.getSession();
		return session.createSQLQuery(sql).setString(0, instanceId).list();
	}
}
