package cn.com.qytx.cbb.publicDom.service.mobile.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customForm.dao.FormPropertiesDao;
import cn.com.qytx.cbb.customForm.dao.FormPropertyValueDao;
import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.jbpmApp.service.mobile.JbpmMobileService;
import cn.com.qytx.cbb.jbpmApp.service.mobile.htmlElement.HtmlElement;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.cbb.publicDom.mobile.vo.AdviceObj;
import cn.com.qytx.cbb.publicDom.mobile.vo.AttachObj;
import cn.com.qytx.cbb.publicDom.mobile.vo.MobilePublicDomView;
import cn.com.qytx.cbb.publicDom.mobile.vo.MobileReadState;
import cn.com.qytx.cbb.publicDom.mobile.vo.MobleViewProcessDetail;
import cn.com.qytx.cbb.publicDom.service.GongwenVarService;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.cbb.publicDom.service.IFormAuthority;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.service.mobile.MobilePublicDomService;
import cn.com.qytx.cbb.publicDom.util.AttachUtil;
import cn.com.qytx.cbb.publicDom.vo.PublicDomView;
import cn.com.qytx.cbb.publicDom.vo.ReadStateView;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：手机服务端接口实现
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-5-6 上午9:35:48 
 * 修改日期：2013-5-6 上午9:35:48 
 * 修改列表：
 */
@Service("mobilePublicDomService")
@Transactional
public class MobilePublicDomServiceImpl implements MobilePublicDomService {
	
	//未结束的流程标志
	private final static String NOT_COMPLETED = "notCompleted";
	//已结束的流程标志
	private final static String COMPLETED = "completed";
	
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	
	@Resource(name="processEngine")
	private ProcessEngine processEngine;
	
	@Resource(name="userService")
	private IUser userService;
	
	@Resource(name = "formPropertyValueDao")
    FormPropertyValueDao formPropertyValueDao;

	@Resource(name="documentExtService")
	private IDocumentExtService documentExtService;
	
	@Resource(name = "formPropertiesDao")
    FormPropertiesDao formPropertiesDao;
	
	@Resource(name="formAuthorityService")
	IFormAuthority formAuthorityService;
	
	@Resource(name="gongwenVarService")
	private GongwenVarService gongwenVarService;
	
	@Resource
	private JbpmMobileService jbpmMobileService;
    @Resource(name = "groupService")
    private IGroup groupService;
	@Override
	public List<MobilePublicDomView> findNotCompletedDom(UserInfo user,
			String menu, Pageable page) {
		return findDom(NOT_COMPLETED, user, menu, null,page);
	}
	
	@Override
	public List<MobilePublicDomView> findCompletedDom(UserInfo user,
			String menu, String title,Pageable page) {
		// TODO Auto-generated method stub
		return findDom(COMPLETED, user, menu, title,page);
	}
	
	/**
	 * 功能：查询公文列表：未完成的/已完成的
	 * @param
	 * @return
	 * @throws   
	 */
	private List<MobilePublicDomView> findDom(String type,UserInfo user,String menu,String title,Pageable page){
		String userId = user.getUserId().toString();
		List<PublicDomView> viewlist = getPublicDomViews(type,user, menu,title, page).getContent();
		List<MobilePublicDomView> mobileViewList = new ArrayList<MobilePublicDomView>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(viewlist!=null){
				for(int i=0; i<viewlist.size(); i++){
					PublicDomView temp = viewlist.get(i);
					MobilePublicDomView mobileView = new MobilePublicDomView();
					mobileView.setInstanceId(temp.getInstanceId());
					mobileView.setTitle(temp.getTitle());
					mobileView.setGongwenTypeId(temp.getGongwenTypeName());
					mobileView.setIsSystem("out");
					if(temp.getInstanceId().startsWith("gather")){
						if(temp.getGatherSource().equals("dispatch")){
							mobileView.setIsSystem("inner");
						}
					}
//					mobileView.setState(temp.getState());
					String state = PublicDocumentConstant.stateMap.get(temp.getState());
					if(state == null){
						mobileView.setState("已归档");
					}else{
						mobileView.setState(state);
					}
					mobileViewList.add(mobileView);
			}
			List<UserInfo> users = userService.findAll();
			//收文阅读，显示收文分发人和分发时间
			if(menu.equals("swyd")){
				for(int i=0; i<mobileViewList.size(); i++){
					MobilePublicDomView temp = mobileViewList.get(i);
					HistoryTask ht = processEngine.getHistoryService().createHistoryTaskQuery()
							.executionId(temp.getInstanceId()).outcome("转阅读").uniqueResult();
					UserInfo ui = getUserById(users, ht.getAssignee());
					if(ui!=null){
						temp.setFrom(ui.getUserName());
					}else{
						temp.setFrom("");
					}
					temp.setReceiveTime(sdf.format(ht.getEndTime()));
				}
				//收文记录，显示发文单位和发文时间
			}else if(menu.equals("swjl")){
				for(int i =0; i<mobileViewList.size(); i++){
					MobilePublicDomView temp = mobileViewList.get(i);
					String instanceId = temp.getInstanceId();
					ProcessInstance processInstance = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
					//提取发文单位和发文时间
					HistoryActivityInstance hai = processEngine.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(instanceId).activityName("收文登记").uniqueResult();
					String fromGroup = "";
					if(processInstance==null){
						fromGroup = (String) processEngine.getHistoryService().getVariable(instanceId, PublicDocumentConstant.SENDER_DEPT);
					}else{
						fromGroup = (String) processEngine.getExecutionService().getVariable(instanceId, PublicDocumentConstant.SENDER_DEPT);
					}
					temp.setFrom(fromGroup);
					if(hai.getEndTime()==null){
						temp.setReceiveTime(sdf.format(hai.getStartTime()));
					}else{
						temp.setReceiveTime(sdf.format(hai.getEndTime()));
					}
				}
			}	
			//领导批阅
			else if(menu.equals("ldpy")){
				for(int i =0; i<mobileViewList.size(); i++){
					MobilePublicDomView temp = mobileViewList.get(i);
					String instanceId = temp.getInstanceId();
					
					//查找上一步任务
					List<HistoryTask> historyTaskList = processEngine.getHistoryService().createHistoryTaskQuery().executionId(instanceId).outcome("转领导批阅").orderDesc("endTime").list();
					List<HistoryActivityInstance> historyActivityList = processEngine.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(instanceId).orderDesc("endTime").list();
					HistoryTask historyTask = historyTaskList.get(0);
					HistoryActivityInstance historyActivityInstance = null;
					for(Iterator it = historyActivityList.iterator();it.hasNext(); ){
						HistoryActivityInstance hai = (HistoryActivityInstance) it.next();
						if(hai.getActivityName().equals("收文登记") || hai.getActivityName().equals("领导批阅")){
							historyActivityInstance = hai;
							break;
						}
					}
					if(historyActivityInstance!=null){
						if(historyActivityInstance.getActivityName().equals("收文登记")){
							temp.setFromTag("登记");
						}else if(historyActivityInstance.getActivityName().equals("领导批阅")){
							temp.setFromTag("批阅");
						}
					}
					UserInfo ui = getUserById(users, historyTask.getAssignee());
					temp.setFrom(ui.getUserName());
					
					List<HistoryActivityInstance> hailist = processEngine.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(instanceId).activityName("领导批阅").orderDesc("endTime").list();
					if(type.equals(NOT_COMPLETED)){
						temp.setReceiveTime(sdf.format(hailist.get(0).getStartTime()));
					}else{
						for(Iterator<HistoryActivityInstance> it = hailist.iterator(); it.hasNext();){
							HistoryActivityInstance h = it.next();
							if(h.getEndTime()!=null){
								temp.setReceiveTime(sdf.format(h.getEndTime()));
								break;
							}
						}
					}
					
				}
			}
			//收文分发
			else if(menu.equals("swff")){
				for(int i =0; i<mobileViewList.size(); i++){
					MobilePublicDomView temp = mobileViewList.get(i);
					String instanceId = temp.getInstanceId();
					
					//查找上一步任务
					List<HistoryTask> historyTaskList = processEngine.getHistoryService().createHistoryTaskQuery().executionId(instanceId).outcome("转收文分发").orderDesc("endTime").list();
					HistoryTask historyTask = historyTaskList.get(0);
				
					UserInfo ui = getUserById(users, historyTask.getAssignee());
					temp.setFrom(ui.getUserName());
					
					HistoryActivityInstance hai = processEngine.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(instanceId).activityName("收文分发").uniqueResult();
					if(hai.getEndTime() == null){
						temp.setReceiveTime(sdf.format(hai.getStartTime()));
					}else{
						temp.setReceiveTime(sdf.format(hai.getEndTime()));
						long count = processEngine.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(instanceId).activityName("收文阅读").count();
						temp.setReaders(count);
					}
					
				}
			}
			//发文拟稿
			else if(menu.equals("ngjl")){
				for(int i =0; i<mobileViewList.size(); i++){
					MobilePublicDomView temp = mobileViewList.get(i);
					String instanceId = temp.getInstanceId();
					HistoryActivityInstance hai = processEngine.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(instanceId).activityName("发文拟稿").uniqueResult();
					if(hai.getEndTime() == null){
						temp.setReceiveTime(sdf.format(hai.getStartTime()));
					}else{
						temp.setReceiveTime(sdf.format(hai.getEndTime()));
					}
					
				}
			}
			//发文核稿
			else if(menu.equals("fwhg")){
				for(int i =0; i<mobileViewList.size(); i++){
					MobilePublicDomView temp = mobileViewList.get(i);
					String instanceId = temp.getInstanceId();
					temp.setFromTag("拟稿");
					//查找上一步任务
					List<HistoryTask> historyTaskList = processEngine.getHistoryService().createHistoryTaskQuery().executionId(instanceId).outcome("转核稿").orderDesc("endTime").list();
					HistoryTask historyTask = historyTaskList.get(0);
				
					UserInfo ui = getUserById(users, historyTask.getAssignee());
					temp.setFrom(ui.getUserName());
					
					List<HistoryActivityInstance> hailist = processEngine.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(instanceId).activityName("发文核稿").orderDesc("endTime").list();
					if(type.equals(NOT_COMPLETED)){
						temp.setReceiveTime(sdf.format(hailist.get(0).getStartTime()));
					}else{
						for(Iterator<HistoryActivityInstance> it = hailist.iterator(); it.hasNext();){
							HistoryActivityInstance h = it.next();
							if(h.getEndTime()!=null){
								temp.setReceiveTime(sdf.format(h.getEndTime()));
								break;
							}
						}
					}
				}
			}
			//发文分发
			else if(menu.equals("fwff")){
				for(int i =0; i<mobileViewList.size(); i++){
					MobilePublicDomView temp = mobileViewList.get(i);
					String instanceId = temp.getInstanceId();
					//查找上一步任务
					List<HistoryTask> historyTaskList = processEngine.getHistoryService().createHistoryTaskQuery().executionId(instanceId).outcome("转发文分发").orderDesc("endTime").list();
					HistoryTask historyTask = historyTaskList.get(0);
				
					UserInfo ui = getUserById(users, historyTask.getAssignee());
					temp.setFrom(ui.getUserName());
					
					HistoryActivityInstance hai = processEngine.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(instanceId).activityName("发文分发").uniqueResult();
					if(hai.getEndTime() == null){
						temp.setReceiveTime(sdf.format(hai.getStartTime()));
					}else{
						temp.setReceiveTime(sdf.format(hai.getEndTime()));
					}
					
					//add by jiayq
					if(type.equals(COMPLETED)){
						ProcessInstance pi = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
						Object obj = null;
						if(pi!=null){
							obj = processEngine.getExecutionService().getVariable(instanceId, "receiver_count");
						}else{
							obj = processEngine.getHistoryService().getVariable(instanceId, "receiver_count");
						}
						if(obj!=null){
							int readers = Integer.parseInt(obj.toString());
							temp.setReaders(readers);
						}
					}
				}
			}
			//归档
			else if(menu.equals("gd") || menu.equals("swgd") || menu.equals("fwgd")){
				for(int i =0; i<mobileViewList.size(); i++){
					MobilePublicDomView temp = mobileViewList.get(i);
					String instanceId = temp.getInstanceId();
					if(instanceId.startsWith("gather")){
						temp.setFromTag("登记");
					}else{
						temp.setFromTag("拟稿");
					}
					GongwenVar gv = gongwenVarService.findByInstanceId(instanceId);
					temp.setFrom(gv.getCreater());
					temp.setReceiveTime(sdf.format(gv.getCreateTime()));
					if(gv.getEndTime()!=null){
						temp.setReceiveTime(sdf.format(gv.getEndTime()));
					}
				}
			}
			
			
		}
		return mobileViewList;
	}
	
	/**
	 * 功能：获取PC端公文列表
	 * @param
	 * @return
	 * @throws   
	 */
	private Page<PublicDomView> getPublicDomViews(String type,UserInfo user,String menu,String title,Pageable page){
		String userId = user.getUserId().toString();
		String processName = "";
		List<String> nodeNames = new ArrayList<String>();
		GroupInfo forkGroup = null;
		if(menu!=null){
			if(menu.equals("swjl")){
				nodeNames.add("收文登记");
				processName = "gather";
			}else if(menu.equals("ldpy")){
				nodeNames.add("领导批阅");
				processName = "gather";
			}else if(menu.equals("swff")){
				nodeNames.add("收文分发");
				processName = "gather";
			}else if(menu.equals("swyd")){
				nodeNames.add("收文阅读");
				processName = "gather";
			}else if(menu.equals("ngjl")){
				nodeNames.add("发文拟稿");
				processName = "dispatch";
			}else if(menu.equals("fwhg")){
				nodeNames.add("发文核稿");
				processName = "dispatch";
			}else if(menu.equals("thgz")){
				nodeNames.add("套红盖章");
				processName = "dispatch";
			}else if(menu.equals("fwff")){
				nodeNames.add("发文分发");
				processName = "dispatch";
			}else if(menu.equals("gd")){
				if(type.equals(NOT_COMPLETED)){
					nodeNames.add("收文阅读");
				}
				nodeNames.add("归档");
				forkGroup = groupService.getForkGroup(user.getCompanyId(),user.getUserId());
				userId = null;
			}else if(menu.equals("swgd")){
				processName = "gather";
				if(type.equals(NOT_COMPLETED)){
					nodeNames.add("收文阅读");
				}
				nodeNames.add("归档");
				forkGroup = groupService.getForkGroup(user.getCompanyId(),user.getUserId());
				userId = null;
				
			}else if(menu.equals("fwgd")){
				processName = "dispatch";
				nodeNames.add("归档");
				forkGroup = groupService.getForkGroup(user.getCompanyId(),user.getUserId());
				userId = null;
			}
		}
		Page<PublicDomView> viewlist = null;
		if(type.equals(NOT_COMPLETED)){
			viewlist = publicDomService.searchWaittingProcessTask(processName, nodeNames, null, userId, forkGroup, page);
		}else if(type.equals(COMPLETED)){
			viewlist = publicDomService.searchMyCompletedProcessTask(processName, nodeNames, title, userId, forkGroup, page);
		}
		return viewlist;
	}
	
	/**
	 * 功能：根据用户ID查找用户信息
	 * @param
	 * @return
	 * @throws   
	 */
	private UserInfo getUserById(List<UserInfo> users,String id){
		for(int i=0; i<users.size(); i++){
			UserInfo ui = users.get(i);
			if(ui.getUserId().toString().equals(id)){
				return ui;
			}
		}
		return null;
	}

						
	@Override
	public Map<String, Long> findNotCompletedTaskCount(String searchType,
			UserInfo userInfo,Pageable page) {
		// TODO Auto-generated method stub
		Map<String,Long> map = new HashMap<String, Long>();
		Page<PublicDomView> viewlist = null;
		if(searchType.equals("gather")){
			//收文记录
			viewlist =  getPublicDomViews(NOT_COMPLETED, userInfo, "swjl", null,page);
			map.put("swjl", viewlist.getTotalElements());
			
			//领导批阅
			viewlist = getPublicDomViews(NOT_COMPLETED, userInfo, "ldpy", null,page);
			map.put("ldpy", viewlist.getTotalElements());
			
			//收文分发
			viewlist = getPublicDomViews(NOT_COMPLETED, userInfo,"swff",null, page);
			map.put("swff", viewlist.getTotalElements());
			
			//收文阅读
			viewlist = getPublicDomViews(NOT_COMPLETED, userInfo, "swyd", null,page);
			map.put("swyd", viewlist.getTotalElements());
		}else if(searchType.equals("dispatch")){
			//发文拟稿
			viewlist = getPublicDomViews(NOT_COMPLETED, userInfo,"ngjl", null,page);
			map.put("ngjl", viewlist.getTotalElements());
			
			//发文核稿
			viewlist = getPublicDomViews(NOT_COMPLETED, userInfo, "fwhg",null, page);
			map.put("fwhg", viewlist.getTotalElements());
			
			//发文
			viewlist = getPublicDomViews(NOT_COMPLETED, userInfo, "fwff", null,page);
			map.put("fwff", viewlist.getTotalElements());
		}else if(searchType.equals("zip")){
			//归档
			viewlist = getPublicDomViews(NOT_COMPLETED, userInfo, "gd",null, page);
			map.put("gd", viewlist.getTotalElements());
		}
		return map;
	}

	@Override
    public MobleViewProcessDetail findDomDetail(String instanceId,String userId) {
		MobleViewProcessDetail detail = new MobleViewProcessDetail();
		
		DocumentExt ext = documentExtService.findByProcessInstanceId(instanceId);
		int attachId = ext.getAttachId();
		detail.setDocAttachId(attachId);
		
		ProcessInstance instance = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
		
		List<HtmlElement> propertylist = jbpmMobileService.getHtmlElementsForDoc(instanceId,userId);
		detail.setForm(propertylist);
		
		List<AttachObj> attachlist = getAttachsByInstanceId(instanceId);
		detail.setAttach(attachlist);
		
		//判断是系统内还是系统外,默认系统外
		detail.setIsSystem("out");
		detail.setReaded(true);
		if(instanceId.startsWith("gather")){
			String source = "";
			if(instance == null){
				source = (String) processEngine.getHistoryService().getVariable(instanceId, "source");
				detail.setReaded(true);
			}else{
				source = (String) processEngine.getExecutionService().getVariable(instanceId, "source");
				boolean result = processEngine.getExecutionService().findExecutionById(instanceId).isActive("收文阅读");
				if(result){
					detail.setReaded(false);
				}
			}
			if(source.equals("dispatch")){
				detail.setIsSystem("inner");
			}
			
		}
	    return detail;
    }

	
	/**
	 * 功能：根据流程实例ID获取附件列表`
	 * @param
	 * @return
	 * @throws   
	 */
	private List<AttachObj> getAttachsByInstanceId(String instanceId){
		List<AttachObj> attachlist = new ArrayList<AttachObj>();
		List<Attachment> list = AttachUtil.getAttachmentList(instanceId);
		if(list!=null){
			for(int i=0; i<list.size(); i++){
				Attachment av = list.get(i);
				AttachObj ao = new AttachObj();
				ao.setName(av.getAttachName());
				ao.setSize(FileUtils.byteCountToDisplaySize(av.getAttachSize()));
				ao.setId(av.getId());
				ao.setAttachmentName(av.getAttachName());
				ao.setSuffix("");
				if(av.getAttachName()!=null && av.getAttachName().indexOf(".")>=0){
					ao.setSuffix(av.getAttachName().substring(av.getAttachName().lastIndexOf(".")+1));
				}
				ao.setFileSize(formetFileSize(av.getAttachSize()));
				attachlist.add(ao);
			}
		}
		return attachlist;
	}
	
	/**
	 * 
	 * 功能：根据大小得到b k m g
	 * 
	 * @param fileS
	 * @return
	 */
	private String formetFileSize(long fileS) {// 转换文件大小
		// DecimalFormat df = new DecimalFormat("#.00");
		DecimalFormat df = new DecimalFormat("0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	@Override
    public MobileReadState findReadStateByInstanceId(String instanceId) {
	    // TODO Auto-generated method stub
		MobileReadState mrs = new MobileReadState();
		List<ReadStateView> viewlist = publicDomService.getReadStateViewList(instanceId);
		mrs.setCount(viewlist.size());
		int readed = 0 ;
		for(int i=0; i<viewlist.size(); i++){
			ReadStateView rsv = viewlist.get(i);
			if(rsv.getState().contains("已阅读")){
				readed++;
				mrs.addReadlist(rsv.getUserName(), rsv.getGroupName(), rsv.getRole(), rsv.getReadTime());
			}else{
				mrs.addNotRead(rsv.getUserName(), rsv.getGroupName(), rsv.getRole());
			}
		}
		mrs.setReaded(readed);
		mrs.setNotRead(mrs.getCount()-mrs.getReaded());
	    return mrs;
    }

	@Override
    public Map<String, Object> getVarsMap(String instanceId, Set<String> set) {
	    // TODO Auto-generated method stub
		return processEngine.getExecutionService().getVariables(instanceId, set);
    }

	@Override
    public List<AdviceObj> getAdviceObjlist(String instanceId, String editorId) {
	    // TODO Auto-generated method stub
		List<AdviceObj> list = new ArrayList<AdviceObj>();
		String adviceStr = publicDomService.getAdvice(instanceId);
		if(adviceStr!=null){
			Gson gson = new Gson();
			List<JbpmAdvice> advicelist = gson.fromJson(adviceStr, new TypeToken<List<JbpmAdvice>>(){}.getType());
			for(int i=0; i<advicelist.size(); i++){
				JbpmAdvice ja = advicelist.get(i);
				if(ja.getEditorName().equals(editorId)){
					AdviceObj ao = new AdviceObj();
					ao.setContent(ja.getContent());
					ao.setTime(ja.getAdviceTime());
					ao.setUserName(ja.getUserName());
					ao.setTaskId(ja.getTaskId());
					ao.setHeadPhoto(ja.getHeadPhoto());
					ao.setCreateTime(ja.getCreateTimeOrial());
					ao.setUserId(ja.getUserId());
					list.add(ao);
				}
			}
		}
	    return list;
    }

	@Override
    public void saveDom(String instanceId, UserInfo userInfo, String formData) {
	    // TODO Auto-generated method stub
		FormAttribute fa = publicDomService.getFormSourceByInstanceId(instanceId);
		List<Task> tasklist = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
		if(tasklist!=null && tasklist.size()>0){
			String taskName = tasklist.get(0).getName();
			publicDomService.saveFormDataAndCommants(formData, instanceId, fa==null?null:fa.getFormId());
			GongwenVar gongwenVar = gongwenVarService.findByInstanceId(instanceId);
			gongwenVarService.saveOrUpdate(gongwenVar);
		}
    }

}
