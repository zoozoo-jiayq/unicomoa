package cn.com.qytx.cbb.publicDom.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.job.JobImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customForm.dao.FormPropertiesDao;
import cn.com.qytx.cbb.customForm.dao.FormPropertyValueDao;
import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customForm.service.IFormPropertyValue;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.customJpdl.service.impl.template.SubTaskManager;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.cbb.jbpmApp.domain.AdviceOrder;
import cn.com.qytx.cbb.jbpmApp.service.IJbpmApp;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.publicDom.dao.GongwenVarDao;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.domain.DocumentType;
import cn.com.qytx.cbb.publicDom.domain.GongwenVar;
import cn.com.qytx.cbb.publicDom.service.DispatchSend;
import cn.com.qytx.cbb.publicDom.service.DocumentTypeService;
import cn.com.qytx.cbb.publicDom.service.GatherRegisterService;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant.GATHER_SOURCE;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant.SearchType;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.cbb.publicDom.service.impl.command.SearchCompletedTaskList;
import cn.com.qytx.cbb.publicDom.service.impl.command.SearchProcessingTaskList;
import cn.com.qytx.cbb.publicDom.util.AffairUtils;
import cn.com.qytx.cbb.publicDom.util.DocumentExtUtil;
import cn.com.qytx.cbb.publicDom.util.DomContentUtil;
import cn.com.qytx.cbb.publicDom.util.OfficeUtils;
import cn.com.qytx.cbb.publicDom.util.PublicDomAdviceUtil;
import cn.com.qytx.cbb.publicDom.vo.PublicDomView;
import cn.com.qytx.cbb.publicDom.vo.PublicDomVo;
import cn.com.qytx.cbb.publicDom.vo.ReadStateView;
import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.utils.spring.SpringUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 功能：公文工作流接口的实现类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-16 上午11:26:35 
 * 修改日期：2013-4-16 上午11:26:35 
 * 修改列表：
 */
@Service("publicDomService")
@Transactional
public class PublicDomServiceImpl implements PublicDomService {
	@Resource(name="documentExtService")
	private IDocumentExtService documentExtService;
	@Resource
	private ProcessEngine processEngine;
	private ExecutionService executionService;
	private TaskService taskService;
	private HistoryService historyService;
	@Resource
	private DocumentTypeService documentTypeService;
	@Resource
	private IFormAttribute formAttributeService;
	@Resource
	private IFormPropertyValue formPropertyValueService;
	@Resource
	private IAttachment attachmentService;
	@Resource(name="formCategoryService")
	private IFormCategory formCategory;
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	@Resource(name="gongwenVarDao")
	GongwenVarDao gongwenVarDao;
	@Resource(name="groupUserService")
	IGroupUser groupUserService;
	@Resource(name="groupService")
	IGroup groupService;
	@Resource(name = "formPropertyValueDao")
    FormPropertyValueDao formPropertyValueDao;
	/**表单控件属性 */
	@Resource(name = "formPropertiesDao")
    FormPropertiesDao formPropertiesDao;
	@Resource(name="jbpmAppService")
	IJbpmApp jbpmAppService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private IWorkFlowService workflowService;
	@Resource
	private ProcessAttributeService processAttributeService;
	@Resource
	private IDocTemplateService docTemplateService;
	@Resource
	private IRole roleService;
	@Resource
	private SubTaskManager subTaskManager;
    @Resource
    private FilePathConfig filePathConfig;

	/**
	 * 功能：初始化JBPM的各项服务
	 * @param
	 * @return
	 * @throws   
	 */
	public void init(){
		executionService = processEngine.getExecutionService();
		taskService = processEngine.getTaskService();
		historyService = processEngine.getHistoryService();
	}

	/**
	 * 功能：新建公文
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String createInstance(PublicDomVo publicDom,UserInfo userInfo,GATHER_SOURCE source) {
		init();
		Map<String,String> map = new HashMap<String,String>();
		map.put(PublicDocumentConstant.WENHAO, publicDom.getWenhao());
		map.put(PublicDocumentConstant.GONGWENTYPE, publicDom.getDomTypeName());
		map.put(PublicDocumentConstant.GONGWENTYPE_ID, publicDom.getDomTypeId()+"");
		map.put(PublicDocumentConstant.TITLE, publicDom.getTitle());
		map.put(PublicDocumentConstant.GATHER_SOURCE, source.getSource());
		map.put(PublicDocumentConstant.SECRET_LEVEL, publicDom.getSecretLevel());
		map.put(PublicDocumentConstant.HUANJI, publicDom.getHuanji());
		map.put(PublicDocumentConstant.SENDER_DEPT, publicDom.getSourceDept());
		map.put(PublicDocumentConstant.CREATER, userInfo.getUserId().toString());
		map.put(PublicDocumentConstant.FIRST_LEVEL_NAME, publicDom.getFirstLevel());
		if(publicDom.getDomName().equals(DomType.GATHER.getProcessName())){
			GatherRegisterService gatherRegisterService = (GatherRegisterService) SpringUtil.getBean("gatherRegisterService");
			String groupNameString = gatherRegisterService.findGroupNameByUserId(userInfo.getUserId().toString());
			map.put(PublicDocumentConstant.GATHER_DEPT, groupNameString);
		}
		
		String historyOptionName = "创建收文单";
		//如果是发文，则把发文的流程ID保存在流程变量中
		if(publicDom.getDomName().equals(DomType.DISPATCHER.getProcessName())){
			historyOptionName = "创建发文单";
		}
		String advice = PublicDomAdviceUtil.createPublicDomAdvice( userInfo, historyOptionName);
		map.put(PublicDocumentConstant.APPROVE_HIST_RECORD, advice);
		ProcessInstance processInstance = executionService.startProcessInstanceByKey(publicDom.getDomName(),map);
		
		if(processInstance!=null){
			try {
	            DocumentExtUtil.generateDocumentExt(processInstance.getId(), "dom");
            } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
		}
		
		//保存公文流程变量
		GongwenVar var = new GongwenVar();
		if(processInstance!=null){
		var.setInstanceId(processInstance.getId());
		}
		var.setWenhao(publicDom.getWenhao());
		var.setGongwenTypeName(publicDom.getDomTypeName());
		var.setTitle(publicDom.getTitle());
		var.setGatherSource(source.getSource());
		var.setMiji(publicDom.getSecretLevel());
		var.setHuanji(publicDom.getHuanji());
		var.setFromGroup(publicDom.getSourceDept());
		var.setCreater(userInfo.getUserName());
		GroupInfo forkGroup = groupService.getForkGroup(userInfo.getCompanyId(),userInfo.getUserId());
		int forkGroupId = 0;
		if(forkGroup != null){
			forkGroupId = forkGroup.getGroupId();
		}
		var.setForkGroupId(forkGroupId);
		var.setReceiverGroup(map.get(PublicDocumentConstant.GATHER_DEPT));
		if(processInstance!=null){
		Set<String> set = processInstance.findActiveActivityNames();
		var.setState(set.iterator().next());
		var.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		}
		var.setCreaterId(userInfo.getUserId());
		var.setCompanyId(userInfo.getCompanyId());
		gongwenVarDao.saveOrUpdate(var);
		if(processInstance!=null){
			Task task = processEngine.getTaskService().createTaskQuery().executionId(processInstance.getId()).uniqueResult();
			return task.getId();
		}
		return null;
	}
	

	/**
	 * 功能：完成任务，isSign:true当前节点是会签,false当前节点不是会签
	 * @param
	 * @return
	 * @throws   
	 */
	private void completeTask(UserInfo userInfo,Task task, String nextTaskAssigner,
			String action, boolean isSign) {
	    String outcome = action;
		String taskId = task.getId();
		TaskImpl impl = (TaskImpl)task;
		String instanceId = impl.getProcessInstance().getId();
		Map<String,String> map = new HashMap<String,String>();
		if(nextTaskAssigner!=null){
			map.put(PublicDocumentConstant.ASSIGNER, nextTaskAssigner);
		}
		GongwenVar gongwenVar = gongwenVarDao.findByInstanceId(instanceId);
		if(outcome==null){
			outcome = taskService.getOutcomes(taskId).iterator().next();
		}
		/*
		 * 如果是会签(收文阅读),分两种情况处理：1,阅读(强制签阅)；2归档(非强制签阅)
		 */
		if(isSign){
			if(outcome.equals("归档")){//非强制签阅，可以在没有阅读完毕的情况下做归档处理,连续走两步
				taskService.completeTask(taskId);
				Task theTask = taskService.createTaskQuery().processInstanceId(instanceId).activityName("归档").uniqueResult();
				if(theTask!=null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Set<String> vars = executionService.getVariableNames(instanceId);
					Map<String,Object> varMap = executionService.getVariables(instanceId, vars);
					//varMap.put(PublicDocumentConstant.ZIP_TIME, sdf.format(new Timestamp(Calendar.getInstance().getTimeInMillis())));
					executionService.createVariables(instanceId, varMap, true);
					gongwenVar.setEndTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					taskService.completeTask(theTask.getId());
				}
			}else if(outcome.equals("已阅")){
				TaskImpl joinTask = (TaskImpl) taskService.getTask(taskId);
				subTaskManager.completeSubTask(joinTask, outcome);
			}
		}else{
			//发文发送
			if(outcome.equals("转分发")){
				DispatchSend send = new DispatchSend(task,nextTaskAssigner, impl.getProcessInstance().getId(),userInfo);
				send.execute(userInfo);
			}
			//如果是发文核稿,并且文号为空，才生成文号
			//update  by jiayq,发文拟稿提交的时候就生成文号
			if(task.getActivityName()!=null && task.getActivityName().equals("发文拟稿")){
				String tempwenhao = (String) executionService.getVariable(task.getExecutionId(), PublicDocumentConstant.WENHAO);
				if(tempwenhao==null || tempwenhao.equals("")){
					String gongwenTypeId = processEngine.getExecutionService().getVariable(impl.getProcessInstance().getId(), PublicDocumentConstant.GONGWENTYPE_ID).toString();
					DocumentType documentType = documentTypeService.findOne(Integer.parseInt(gongwenTypeId));
					int categoryid = documentType.getCategoryId();
					FormCategory temp   = formCategory.findById(categoryid);
					String categoryName = "";
					if(temp!=null){
						categoryName = temp.getCategoryName();
					}
					String wenhao = documentType.getWenhao(userInfo.getLoginName(),userInfo.getGroupName(),categoryName);
					map.put(PublicDocumentConstant.WENHAO, wenhao);
					gongwenVar.setWenhao(wenhao);
				}
			}
			
			
			//归档时候，把变量都放进历史表中
			if(task.getActivityName().equals("归档")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Set<String> vars = executionService.getVariableNames(instanceId);
				Map<String,Object> varMap = executionService.getVariables(instanceId, vars);
				//varMap.put(PublicDocumentConstant.ZIP_TIME, sdf.format(new Timestamp(Calendar.getInstance().getTimeInMillis())));
				executionService.createVariables(instanceId, varMap, true);
				gongwenVar.setEndTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			}
			try{
				taskService.completeTask(taskId, outcome, map);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		ProcessInstance processInstance = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
		if(processInstance!=null){
			Set<String> set = processInstance.findActiveActivityNames();
			gongwenVar.setState(set.iterator().next());
			//如果存在定时任务，则执行自动归档操作
			JobImpl job = (JobImpl) processEngine.getManagementService().createJobQuery().timers().processInstanceId(processInstance.getId()).uniqueResult();
			if(job!=null){
				processEngine.getManagementService().executeJob(job.getId()); 
				autoZip(instanceId);
			}	
		}else{
			gongwenVar.setState(null);
		}
		gongwenVarDao.saveOrUpdate(gongwenVar);
	}
	
	/**
	 * 功能：自动归档
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	private void autoZip(String instanceId){
		Set<String> vars = processEngine.getExecutionService().getVariableNames(instanceId);
		Map<String,String> map = sysConfigService.getSysConfig();
		Map<String,Object> varMap =  processEngine.getExecutionService().getVariables(instanceId, vars);
			int zipBehavior = 1 ;
			if(instanceId.startsWith("gather")){
				zipBehavior = Integer.parseInt(map.get(SysConfig.DOM_GATHER_ZIP));
			}else if(instanceId.startsWith("dispatch")){
				zipBehavior = Integer.parseInt(map.get(SysConfig.DOM_DISPATCH_ZIP));
			}
			if(zipBehavior!=1){//自动归档
				GongwenVar gongwenVar = gongwenVarDao.findByInstanceId(instanceId);
				Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).activityName("归档").uniqueResult();
				if(task!=null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					varMap.put(PublicDocumentConstant.ZIP_TIME, sdf.format(new Timestamp(Calendar.getInstance().getTimeInMillis())));
					processEngine.getExecutionService().createVariables(instanceId, varMap, true);
					gongwenVar.setEndTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					gongwenVar.setState(null);
					gongwenVarDao.saveOrUpdate(gongwenVar);
					processEngine.getTaskService().completeTask(task.getId());
				}
			}
	}


	/**
	 * 功能：完成任务,保存审批记录，更新GongwenVar变量对象，发送事物提醒,最后调用API完成任务
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public void completeTask(UserInfo userInfo,String taskId, String nextTaskAssigner,
			String outcome) {
		//标记当前任务是否是会签节点
		boolean sign = false;
		init();
		TaskImpl task = (TaskImpl) taskService.getTask(taskId);
		String activityName = task.getName();
		
		//先保存审批历史记录
		PublicDomAdviceUtil.addPublicDomAdvice(task.getProcessInstance().getId(), userInfo, activityName);
		
		//如果是发文分发，则不不在这里发送通知，避免重复发送
		if(!outcome.equals("转分发")){
			if(nextTaskAssigner!=null && !nextTaskAssigner.equals("")){
			    String title = workflowService.getVariablebyInstance(task.getProcessInstance().getId(), PublicDocumentConstant.TITLE);
			    String nextAction = PublicDocumentConstant.OPERATION_NODE_MAP.get(outcome);
				AffairUtils.insertAffair(userInfo, nextTaskAssigner, task.getActivityName(),title,task.getProcessInstance().getId(),nextAction);
			}
		}
		
		//调用API完成任务
		if(activityName.contains(MenuType.GA_READ.getNodeName())){
			sign = true;
		}
		completeTask(userInfo,task, nextTaskAssigner, outcome,sign);
		
	}

	/**
	 * 功能：查询发文流程定义ID
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String getDispatcherDomProcessDefineId() {
		ProcessDefinition define = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionName(DomType.DISPATCHER.getProcessName()).uniqueResult();
		return define.getId();
	}

	/**
	 * 功能：查询收文流程定义ID
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String getGatherDomProcessDefineId() {
		ProcessDefinition define = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionName(DomType.GATHER.getProcessName()).uniqueResult();
		return define.getId();
	}

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public void deleteInstance(String taskIds) {
		init();
		if(taskIds!=null){
			String ids[] = taskIds.split(",");
			for(int i=0;i<ids.length;i++){
				String id = ids[i];
				if(id!=null && !id.equals("")){
					TaskImpl task = (TaskImpl) taskService.getTask(id);
					String eid = task.getProcessInstance().getId();
					String instanceId = executionService.findExecutionById(eid).getProcessInstance().getId();
					executionService.deleteProcessInstanceCascade(instanceId);
					formPropertyValueService.deleteFormDataByBeanId(instanceId);
					gongwenVarDao.deleteGongwenByInstanceId(instanceId);
				}
			}
		}
		
	}

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public FormAttribute getFormSource(String taskId) {
		init();
		TaskImpl task = (TaskImpl) taskService.getTask(taskId);
		String eid = task.getProcessInstance().getId();
		String typeId = (String) executionService.getVariable(eid, PublicDocumentConstant.GONGWENTYPE_ID);
		DocumentType documentType = documentTypeService.findOne(Integer.parseInt(typeId));
		if(documentType==null){
			return null;
		}
		int formId = documentType.getFormId();
		return  formAttributeService.findById(formId);
	}


	/**
	 * 功能：保存表单数据和附件信息
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public void saveFormDataAndCommants(String jsonData,String instanceId,Integer formID) {
		init();
		if(formID!=null && jsonData!=null && !jsonData.equals("")&& formID!=null){
			formPropertyValueService.saveOrUpdate(jsonData, instanceId, formID);
		}
	}

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public String getInstanceIdByTaskId(String taskId) {
		init();
		TaskImpl task = (TaskImpl) taskService.getTask(taskId);
		String eid = task.getProcessInstance().getId();
		return eid;
	}

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public List<ReadStateView> getReadStateViewList(String InstanceId) {
		return processEngine.execute(new SubTaskStateSearchCommand(InstanceId));
	}


	/**
	 * 功能：查询某个公文菜单下面的所有任务数量
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public int getPersonTaskCount(DomType domType, MenuType menuType, String userId) {
		// TODO Auto-generated method stub
		init();
		int count = 0;
		if(menuType!=null){
			count = (int) taskService.createTaskQuery().activityName(menuType.getNodeName())
					.assignee(userId+"").count();
		}else{
			count = (int) taskService.createTaskQuery().assignee(userId+"").count();
		}
		return count;
	}

	/**
	 * 功能：根据任务ID获取公文类型
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public DocumentType getDocumentTypeByTaskId(String taskId) {
		// TODO Auto-generated method stub
		init();
		String gongwenTypeId = (String) taskService.getVariable(taskId, PublicDocumentConstant.GONGWENTYPE_ID);
		DocumentType type = documentTypeService.findOne(Integer.parseInt(gongwenTypeId));
		return type;
	}

	@Override
	public FormAttribute getFormSourceByInstanceId(String instanceId) {
		if(instanceId==null || instanceId.equals("")){
			return null;
		}
		init();
		HistoryProcessInstance processInstance = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
		String typeId = null;
		if(processInstance!=null){
			if(processInstance.getState().equals(HistoryProcessInstance.STATE_ENDED)){
				typeId = (String) processEngine.getHistoryService().getVariable(instanceId, PublicDocumentConstant.GONGWENTYPE_ID);
			}else{
				typeId = (String) processEngine.getExecutionService().getVariable(instanceId, PublicDocumentConstant.GONGWENTYPE_ID);
			}
		}
		if(typeId!=null){
			DocumentType documentType = documentTypeService.findOne(Integer.parseInt(typeId));
			if(documentType!=null){
				int formId = documentType.getFormId();
				return  formAttributeService.findById(formId);
			}else{
				return null;
			}
		}
		return null;
	}


	@Override
	public boolean titleIsRepeat(String title) {
		// TODO Auto-generated method stub
		List<GongwenVar> list = gongwenVarDao.findByTitle(title);
		if(list == null || list.size()==0){
			return false;
		}
		return true;
	}

	@Override
	public List<ReadStateView> getReadStateViewCount(String instanceId) {
		// TODO Auto-generated method stub
		return processEngine.execute(new SubTaskStateCountSearchCommand(instanceId));
	}

	@Override
	public Page<PublicDomView> searchWaittingProcessTask(String processName,
			List<String> nodeNames, String title, String userId,GroupInfo forkGroup,
			Pageable page) {
		// TODO Auto-generated method stub
		return searchByType(SearchType.MY_PROCESSING, processName, nodeNames, title, userId,forkGroup, page);
	}

	@Override
	public Page<PublicDomView> searchMyCompletedProcessTask(
			String processName, List<String> nodeNames,
			String title, String userId, GroupInfo forkGroup,Pageable page) {
		return searchByType(SearchType.MY_COMPLETE, processName, nodeNames, title, userId,forkGroup, page);
	}
	
	private Page<PublicDomView> searchByType(SearchType searchType,String processName,List<String> nodes,String title,String userId,GroupInfo forkGroup,Pageable page){
		if(searchType.equals(SearchType.MY_PROCESSING)){
			return processEngine.execute(new SearchProcessingTaskList(userId, nodes, processName, title,forkGroup, page));
		}else if(searchType.equals(SearchType.MY_COMPLETE)){
			return processEngine.execute(new SearchCompletedTaskList(userId, nodes, processName, title, forkGroup,page));
		}else{
			return null;
		}
	}
	
	private UserInfo findByUserId(Integer uid,List<UserInfo> uis){
		for(int i=0; i<uis.size(); i++){
			UserInfo ui = uis.get(i);
			if(ui.getUserId().intValue() == uid){
				return ui;
			}
		}
		return null;
	}
	
	@Override
	public int getPersonTaskCount(DomType domType, MenuType menuType,
			int userId) {
		init();
		int count = 0;
		 count = (int) taskService.createTaskQuery().activityName(menuType.getNodeName())
					.assignee(userId+"").count();
		return count;
	}




	/**
	 * 功能：压缩归档
	 * @param 
	 * @return
	 * @throws   
	 */
	@Override
	public File zipForDownload(String instanceId)throws Exception {
		init();
		GongwenVar var = gongwenVarDao.findByInstanceId(instanceId);
		String title = var.getTitle();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDD");
		String d = sdf.format(new Date());
		title += d;
		File zipFile = File.createTempFile(title, ".zip");
		FileOutputStream fos = new FileOutputStream(zipFile);
		
		//获取自定义表单文件
		File customFile = getCustomerForm(instanceId,var.getTitle());
		
		//获取版式文件
		File pdfFile = getPdfFile(instanceId);
		
		//获取附件
		File[] attachs = getAttachs(instanceId);
		
		//原始发文单
		File orialCustomFile = getOrialCustomFile(instanceId);
		
		ZipOutputStream zous = new ZipOutputStream(fos);
		zous.setEncoding("GBK");
		
		if(customFile!=null && customFile.exists()){
			String tempName = "";
			if(var.getInstanceId().startsWith("gather")){
				tempName = "收文单";
			}else{
				tempName = "发文单";
			}
			zipFile(zous, customFile,"("+tempName+")"+customFile.getName());
			customFile.delete();
		}
		if(pdfFile!=null && pdfFile.exists()){
			zipFile(zous,pdfFile,"(版式文件)"+title+".pdf");
		}
		if(attachs!=null){
			for(int i=0; i<attachs.length; i++){
				File temp = attachs[i];
				if(temp!=null && temp.exists()){
					zipFile(zous, temp,"(附件)"+temp.getName());
				}
			}
		}
		
		//压缩原始发文档
		if(orialCustomFile!=null){
			zipFile(zous,orialCustomFile,"(原发文单)"+orialCustomFile.getName());
		}
		zous.close();
		return zipFile;
	}
	
	/**
	 * 功能：压缩一个指定的文件FILE到zos输入流中
	 * @param zos:压缩文件流；f:待压缩的文件；fileName:待压缩的文件名
	 * @return
	 * @throws   
	 */
	private void zipFile(ZipOutputStream zos,File f,String fileName) throws Exception{
		if(f == null){
			return;
		}
		int maxSize = 1024;
		FileInputStream fis = new FileInputStream(f);
		zos.putNextEntry(new ZipEntry(fileName));
		byte[] buff = new byte[maxSize];
		while(fis.read(buff)!=-1){
			zos.write(buff);
			zos.flush();
			buff = new byte[maxSize];
		}
		zos.closeEntry();
		fis.close();
	}
	
	private void zipFile(ZipOutputStream zos,File f) throws Exception{
		zipFile(zos, f, f.getName());
	}
	
	/** 
	 * 功能：获取自定义表单文件
	 * @param
	 * @return
	 * @throws   
	 */
	public File getCustomerForm(String instanceId,String title){
		int formId = 0 ;
		FormAttribute fa = this.getFormSourceByInstanceId(instanceId);
		if(fa!=null){
			formId = fa.getFormId();
		}
		GongwenVar var = gongwenVarDao.findByInstanceId(instanceId);
		return DomContentUtil.getCustomform(formId, instanceId, var.getSignImg(), title);
	}
	
	/**
	 * 功能：获取原始
	 * @param
	 * @return
	 * @throws   
	 */
	public File getOrialCustomFile(String instanceId){
		if(instanceId.startsWith("gather")){
			ProcessInstance processInstance = executionService.createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
			String source_executionid = "";
			if(processInstance == null){
				source_executionid = (String) historyService.getVariable(instanceId, "source_executionid");
			}else{
				source_executionid = (String) executionService.getVariable(instanceId, "source_executionid");
			}
			if(source_executionid!=null && !source_executionid.equals("")){
				String title = workflowService.getVariablebyInstance(source_executionid, PublicDocumentConstant.TITLE);
				return getCustomerForm(source_executionid,title);
			}
		}
		return null;
	}
	

	
	/**
	 * 功能：获取版式文件
	 * @param
	 * @return
	 * @throws   
	 */
	public File getPdfFile(String instanceId){
		return DomContentUtil.getPdfFile(instanceId);
	}
	
	/**
	 * 功能：获取附件列表
	 * @param
	 * @return
	 * @throws   
	 */
	public File[] getAttachs(String instanceId){
		return DomContentUtil.getAttachs(instanceId);
	}
	
	public String getTitleByInstanceId(String instanceId){
		GongwenVar var = gongwenVarDao.findByInstanceId(instanceId);
		return var.getTitle();
	}

	@Override
	public void batchCompleteTask(UserInfo userInfo,String[] taskIds) {
		// TODO Auto-generated method stub
		init();
		for(int i=0; i<taskIds.length; i++){
			String taskId = taskIds[i];
//			taskService.completeTask(taskId);
			Task task = taskService.getTask(taskId);
			completeTask(userInfo,task, null, null,false);
		}
	}

	@Override
	public File batchZipForDowanload(String[] instanceIds) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dat = sdf.format(new Date());
		String fileName = dat+"批量下载";
		File zipFile = File.createTempFile(fileName+"", ".zip");
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zous = new ZipOutputStream(fos);
		zous.setEncoding("GBK");
		for(int i=0; i<instanceIds.length; i++){
			String instanceId = instanceIds[i];
			File temp = zipForDownload(instanceId);
			zipFile(zous, temp);
			temp.delete();
		}
		zous.flush();
		zous.close();
		fos.flush();
		fos.close();
		return zipFile;
	}

	@Override
	public String saveAdvice(String instanceId, JbpmAdvice jbpmAdvice) {
		// TODO Auto-generated method stub
		init();
		Gson gson = new Gson();
		Object advicelist = executionService.getVariable(instanceId,"tdAdvice");
		List<JbpmAdvice> list = null;
    	if(advicelist!=null){
    		list = gson.fromJson(advicelist.toString(), new TypeToken<List<JbpmAdvice>>(){}.getType());
    	}
    	if(list==null){
    		list = new ArrayList<JbpmAdvice>();
    	}
    	boolean flag = false;
    	for(int i=0; i<list.size(); i++){
    		JbpmAdvice temp = list.get(i);
    		if(temp.getEditorName().equals(jbpmAdvice.getEditorName()) && temp.getTaskId().equals(jbpmAdvice.getTaskId())){
    			temp.setContent(jbpmAdvice.getContent());
    			temp.setAdviceTime(jbpmAdvice.getAdviceTime());
    			temp.setSignType(jbpmAdvice.getSignType());
    			temp.setSignUrl(jbpmAdvice.getSignUrl());
    			flag = true;
    		}
    	}
    	if(!flag){
    		list.add(jbpmAdvice);
    	}
    	
    	//如果审批意见为空，则删除该审批意见
    	for(Iterator it = list.iterator(); it.hasNext();){
    		JbpmAdvice temp = (JbpmAdvice) it.next();
    		if(temp.getContent()==null || temp.getContent().equals("")){
    			
    			Map<String,String> map = sysConfigService.getSysConfig();
    			String str = map.get(SysConfig.SYS_APPROVE_WIDGET);
    			if(!(str == null || str.equals("1"))){
    				it.remove();
    			}
    		}
    	}
    	Collections.sort(list, new AdviceOrder());
    	String result = gson.toJson(list);  
    	processEngine.getExecutionService().createVariable(instanceId, "tdAdvice", result, false);
    	return result;
	}

	@Override
	public String getAdvice(String instanceId) {
		// TODO Auto-generated method stub
		if((instanceId!=null)&&(!instanceId.equals(""))){
			init();
			Object obj = null;
			ProcessInstance pi = 
					executionService.createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
			if(pi == null){
					obj = historyService.getVariable(instanceId, "tdAdvice");
			}else{
				obj = executionService.getVariable(instanceId, "tdAdvice");
			}
			if(obj != null){
				return obj.toString();
			}
		}
		return null;
	}


	@Override
	public String saveAdvice(String instanceId, List<JbpmAdvice> adlist) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
			init();
			Gson gson = new Gson();
			Object advicelist = executionService.getVariable(instanceId,"tdAdvice");
			List<JbpmAdvice> list = null;
	    	if(advicelist!=null){
	    		list = gson.fromJson(advicelist.toString(), new TypeToken<List<JbpmAdvice>>(){}.getType());
	    	}
	    	if(list==null){
	    		list = new ArrayList<JbpmAdvice>();
	    	}
	    	list.addAll(adlist);
	    	Collections.sort(list, new AdviceOrder());
	    	String result = gson.toJson(list);  
	    	processEngine.getExecutionService().createVariable(instanceId, "tdAdvice", result, false);
	    	return result;
	}

	@Override
	public void saveReaderName(String instanceId, UserInfo ui) {
		// TODO Auto-generated method stub
		init();
		String userName = ui.getUserName();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String date = sdf.format(new Date());
		String obj = (String) executionService.getVariable(instanceId, "readerName");
		if(obj==null){
			obj = "";
		}
		Gson gson = new Gson();
		List<ReaderName> readerlist = null;
		try{
				readerlist = gson.fromJson(obj, new TypeToken<List<ReaderName>>(){}.getType());
		}catch(Exception e){
			e.printStackTrace();
		}
		if(readerlist == null){
			readerlist = new ArrayList<ReaderName>();
		}
		boolean b = false;
		for(int i=0; i<readerlist.size(); i++){
			ReaderName rn = readerlist.get(i);
			if(rn.getUserId().equals(ui.getUserId()+"")){
				b = true;
			}
		}
		if(!b){
			ReaderName rn = new ReaderName();
			rn.setDate(date);
			rn.setUserId(ui.getUserId()+"");
			rn.setUserName(userName);
			rn.setOrderIndex(ui.getOrderIndex());
			rn.setReadTime(Calendar.getInstance().getTimeInMillis());
			readerlist.add(rn);
			String r = gson.toJson(readerlist);
			executionService.createVariable(instanceId, "readerName", r, false);
		}
	}
	/**
	 * 功能：阅读对象
	 * 版本: 1.0
	 * 开发人员：贾永强
	 * 创建日期: 上午11:37:07 
	 * 修改日期：上午11:37:07 
	 * 修改列表：
	 */
	public class ReaderName{
		private String userId;
		private String userName;
		private String date;
		private int orderIndex;
		private long readTime = 0;
		
		public long getReadTime() {
			return readTime;
		}
		public void setReadTime(long readTime) {
			this.readTime = readTime;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public int getOrderIndex() {
			return orderIndex;
		}
		public void setOrderIndex(int orderIndex) {
			this.orderIndex = orderIndex;
		}
		
	}

	@Override
	public String getReaderNamelist(String instanceId) {
		// TODO Auto-generated method stub
		init();
		Object obj = null;
		ProcessInstance pi = 
				executionService.createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();
		if(pi == null){
			obj = historyService.getVariable(instanceId, "readerName");
		}else{
			obj = executionService.getVariable(instanceId, "readerName");
		}
		if(obj != null){
			
			Gson gson = new Gson();
			List<ReaderName> list = new ArrayList<ReaderName>();
			try{
				list = gson.fromJson(obj.toString(), new TypeToken<List<ReaderName>>(){}.getType());
			}catch(Exception e){
				e.printStackTrace();
			}
			Map<String,String> map = sysConfigService.getSysConfig();
			String rw = map.get(SysConfig.SYS_READER_WIDGET);
			
			//按照人员排序
			if(rw == null || rw.equals("") || rw.equals("1")){
				Collections.sort(list, new ComparatorByUserOrder());
			}else{
				//按照阅读时间排序
				Collections.sort(list, new ComparatorByDate());
			}
			return gson.toJson(list);
		}else{
			return "";
		}
	}

	@Override
    public List<String> getEnableOperations(String taskId,Integer menu) {
	    // TODO Auto-generated method stub
		TaskImpl task = (TaskImpl) processEngine.getTaskService().getTask(taskId);
		String taskName = task.getName();
		Map<String,String> map = sysConfigService.getSysConfig();
		List<String> list = new ArrayList<String>();
		String config = null;
		if(taskName.equals("收文登记")){
			config = map.get(SysConfig.DOM_GATHER_REGISTER);
			list.add("保存");
		}else if(taskName.equals("领导批阅")){
			config = map.get(SysConfig.DOM_GATHER_APPROVE);
		}else if(taskName.equals("收文分发")){
			config = "转阅读";
		}else if(taskName.equals("收文阅读")){
			config="已阅";
			if(menu!=null && menu == 9){//收文归档需要做特殊处理
				//如果非强制签阅，则可以归档，如果强制签阅，则不能归档
				if(map.get(SysConfig.FORCE_READ).equals("1")){//强制签名阅读，不能归档
					config = "";
				}else{
					config = "归档";
				}
			}
		}else if(taskName.equals("发文拟稿")){
			config = map.get(SysConfig.DOM_DISPATCH_NIGAO);
			list.add("保存");
		}else if(taskName.equals("发文核稿")){
			config = map.get(SysConfig.DOM_DISPATCH_HEGAO);
		}else if(taskName.equals("套红盖章")){
			config = map.get(SysConfig.DOM_DISPATCH_RED);
		}else if(taskName.equals("发文分发")){
			config = "转分发";
		}else if(taskName.equals("归档")){
			config = "归档";
			list.remove("保存");
		}
		if(config!=null){
			String[] strs = config.split(",");
			for(String str:strs){
			    if(!StringUtils.isEmpty(str)){
			        list.add(str);
			    }
			}
		}
		return list;
    }

	@Override
    public int getTaohongTemplate(String taskId, String domflow) {
	    // TODO Auto-generated method stub
		String inst = workflowService.getInstanceIdByTask(taskId);
		String gongwenTypeId = workflowService.getVariablebyInstance(inst, "gongwenType_id");
		DocumentType dt = documentTypeService.findOne(Integer.parseInt(gongwenTypeId));
		int templateId = dt.getDocTemplateId();
		return templateId;
    }

	@Override
	public String getOfficeHtmlContent(String instanceId, String filePath,
			String imgPath) {
		// TODO Auto-generated method stub
		DocumentExt de = documentExtService.findByProcessInstanceId(instanceId);
		int attachId = de.getAttachId();
		String docFile = getInputFileByAttachId(attachId);
		File doc = new File(docFile);
		try{
			OfficeUtils utils = new OfficeUtils();
			String str = utils.convertToHtml(doc, filePath+"/temp",imgPath+"/temp");
			return str;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	private String getInputFileByAttachId(int attachId){
		Attachment ach = attachmentService.getAttachment(attachId);
		if(ach!=null){
			String refPath = ach.getAttachFile();
			
			String catalinaHome = filePathConfig.getFileUploadPath();
	        String inputFile = catalinaHome+"/upload/"+refPath;
	        return inputFile;
		}
		return "";
	}
	
	
	@Override
	public boolean isCanZip(String instanceId) {
		// TODO Auto-generated method stub
		//判断是否可以归档，优先根据公文类型的收文设置
		Map<String,String> map = sysConfigService.getSysConfig();
		//1：强制签阅，必须都阅读完才能签约；2，其它非强制签阅，可以随时归档
		Integer iv = Integer.parseInt(map.get(SysConfig.FORCE_READ));
		if(iv == 2){
			return true;
		}else{
			boolean result = processEngine.getExecutionService().findExecutionById(instanceId).isActive("归档");
			if(result){
				return true;
			}else{
				return false;
			}
		}
	}
}


