package cn.com.qytx.cbb.jbpmApp.action.mobile;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.ApproveHistory;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.DataElementImplNextTaskInfo;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.IJson;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.JsonImplServerException;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.JsonImplSuccess;
import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.ProcessTypeModel;
import cn.com.qytx.cbb.jbpmApp.service.IJbpmApp;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.jbpmApp.service.mobile.JbpmMobileService;
import cn.com.qytx.cbb.jbpmApp.service.mobile.htmlElement.HtmlElement;
import cn.com.qytx.cbb.myapply.domain.MyWaitProcess;
import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OptionAction extends MyBaseAction {
	private static final long serialVersionUID = 1L;
	private static final Logger logger=LoggerFactory.getLogger(OptionAction.class);
	@Resource(name="jbpmMobileService")
	private JbpmMobileService jbpmMobileService;
	@Resource(name="jbpmAppService")
	private IJbpmApp jbpmAppService;
	@Resource(name = "workFlowService")
	IWorkFlowService workFlowService;
	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	@Resource(name="formCategoryService")
	private IFormCategory formCategoryService;
	@Resource
	private IMyWaitProcess myWaitProcessService;
	@Resource(name="groupDao")
	private GroupDao<GroupInfo> groupDao;
	@Autowired
	private IUser userService;
	private String instanceId;
	private String taskId;
	private String advice;
	private String assigner;
	private String approveResult;
	private String nextAction;
	private String formData;
	private String nextUsers;
	//工作流分类ID
	private int categoryId;
	//工作流定义ID
	private int processId;
	public String getNextUsers() {
		return nextUsers;
	}

	public void setNextUsers(String nextUsers) {
		this.nextUsers = nextUsers;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getAssigner() {
		return assigner;
	}

	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	
	/**
	 * 判断流程是否可以删除
	 * @return
	 */
	public String canDelete(){
		IJson json = null;
		try{
			instanceId = URLDecoder.decode(instanceId, "UTF-8");
			boolean result = jbpmAppService.canDeleteInstance(instanceId);
			json = new JsonImplSuccess<String>();
			json.setDatas(result);
		}catch(Exception e){
			json = new JsonImplServerException();
			e.printStackTrace();
		}finally{
			try {
				ajax(json.getMobileClientResponse());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 功能：删除
	 * @param
	 * @return
	 * @throws   
	 */
	public String delete(){
		IJson json = null;
		try{
//			instanceId = "gather.20125";
			instanceId = URLDecoder.decode(instanceId, "UTF-8");
			jbpmMobileService.deleteInstance(instanceId);
			json = new JsonImplSuccess<String>();
			json.setDatas("success");
		}catch(Exception e){
			json = new JsonImplServerException();
			e.printStackTrace();
		}finally{
			try {
				ajax(json.getMobileClientResponse());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 功能：获取下一步信息
	 * @param
	 * @return
	 * @throws   
	 */
	public String getNextTaskInfo(){
		IJson json = null;
		try{
			UserInfo user = userService.findOne(Integer.parseInt(userId));
			instanceId = URLDecoder.decode(instanceId, "UTF-8");
			Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).assignee(user.getLoginName()).uniqueResult();
			if(task == null){
				json  = new JsonImplServerException();
				json.setDatas("该任务已经办理完毕");
			}else{
				List<DataElementImplNextTaskInfo> nextActions  = jbpmMobileService.getNextTaskInfos(instanceId, user.getLoginName());
				json = new JsonImplSuccess<List<DataElementImplNextTaskInfo>>();
				json.setDatas(nextActions);
			}
		}catch(Exception e){
			json = new JsonImplServerException();
			e.printStackTrace();
		}finally{
			try {
				ajax(json.getMobileClientResponse());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 功能：审批
	 * @param
	 * @return
	 * @throws   
	 */
	public String approve(){
		IJson json = null;
		try{
			instanceId = URLDecoder.decode(instanceId, "UTF-8");
			UserInfo ui = userService.findOne(Integer.parseInt(userId));
			if(!approveResult.equals(ApproveHistory.REPEAL)){
				Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).assignee(ui.getLoginName()).uniqueResult();
				if(task == null){
					json  = new JsonImplServerException();
					ajax("101||该任务已经办理完毕！");
					return null;
				}
				if(nextAction==null || nextAction.equals("")){
					nextAction = processEngine.getTaskService().getOutcomes(task.getId()).iterator().next();
				}
				if(taskId==null || taskId.equals("")){
					taskId = task.getId();
				}
			}
			//同意
			if(approveResult.equals(ApproveHistory.APPROVED)){//同意
				jbpmAppService.completeTask(ui, taskId, null,formData, advice, nextAction, assigner);
			}else if(approveResult.equals(ApproveHistory.ROOLBACK)){//驳回
				jbpmAppService.updateFormData(taskId, formData);
				jbpmAppService.rollbackTask(instanceId, null, null, ui, advice,JpdlInterface.PROCESS_STATE_ROLLBACK);
			}else if(approveResult.equals(ApproveHistory.REPEAL)){//撤销
				jbpmAppService.rollbackTask(instanceId, null, null, ui, advice,JpdlInterface.PROCESS_STATE_REPEAL);
			}
			json = new JsonImplSuccess<String>();
			json.setDatas("success");
		}catch(Exception e){
			json = new JsonImplServerException();
			e.printStackTrace();
		}finally{
			try {
				ajax(json.getMobileClientResponse());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取流程分类列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String getProcessCategoryList() throws Exception{
		UserInfo user = userService.findOne(Integer.parseInt(userId));
		List<FormCategory> formCategorys = formCategoryService.findByTypeCompanyId(user.getCompanyId(), 2);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String rs = gson.toJson(formCategorys);
		ajax("100||"+rs);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取指定分类下面的流程定义列表
	 * @param
	 * @return
	 * @throws   
	 */
	public String getProcessAttributeList() throws Exception{
		long t1=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"开始获取流程分类列表：获取人员userId="+userId+",开始时间t1="+t1);
		UserInfo u = userService.findOne(Integer.parseInt(userId));
		long t2=System.currentTimeMillis();
		logger.info(Thread.currentThread().getName()+"开始获取流程分类列表：获取人员userName="+u.getUserName()+",当前时间t2="+t2+"间隔时间t2-t1="+(t2-t1));
		List<ProcessAttribute> processAttributes = processAttributeService.findProcessAttributesByPermissions(u,categoryId,"");
		List<ProcessAttribute> result = new ArrayList<ProcessAttribute>();
		if(processAttributes!=null && categoryId>0){
			for(int i=0; i<processAttributes.size(); i++){
				if(processAttributes.get(i).getCategoryId().intValue() == categoryId){
					result.add(processAttributes.get(i));
				}
			}
		}else{
			result = processAttributes;
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String rs = gson.toJson(result);
		long t3=System.currentTimeMillis(); 
		logger.info(Thread.currentThread().getName()+"开始获取流程分类列表结果：result="+rs+",当前时间t3="+t3+"间隔时间t3-t1="+(t3-t1));
		ajax("100||"+rs);
		return null;
	}
	
	/**
	 * 功能：获取发起流程的时候的表单属性
	 * @param
	 * @return
	 * @throws   
	 */
	public String getStartFormProperties() throws Exception{
		List<HtmlElement> hes = jbpmMobileService.getStartFormProperties(processId);
		Gson gson = new Gson();
		String result = gson.toJson(hes);
		ajax("100||"+result);
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：获取发起流程的时候，下一步任务信息
	 * @param
	 * @return
	 * @throws   
	 */
	public String getStartNextTaskInfo() throws Exception{
		List<DataElementImplNextTaskInfo> result = jbpmMobileService.getStartNextTaskInfos(processId);
		Gson gson = new Gson();
		String str = gson.toJson(result);
		ajax("100||"+str);
		return null;
	}
	
	/*
	 * 功能：发起流程的时候，获取申请名称
	 */
	public String getStartProcessTitle() throws Exception{
		UserInfo u = userService.findOne(Integer.parseInt(userId));
		ProcessAttribute processAttribute= processAttributeService.getProcessById(processId);
		String str = jbpmAppService.generateProcessInstanceName(u, processAttribute);
		ajax("100||"+str);
		return null;
	}
	
	
	/**
	 * @throws Exception 
	 * 功能：发起流程
	 * @param
	 * @return
	 * @throws   
	 */
	public String startProcess() throws Exception{
		UserInfo ui = userService.findOne(Integer.parseInt(userId));
		String instanceId = jbpmMobileService.startProcess(ui,processId, formData, null, "", nextAction, nextUsers);
		//获取审批人员信息
		List<Map<String,String>> processerUser=new ArrayList<Map<String,String>>();
		if(StringUtils.isNotBlank(instanceId)){
			List<MyWaitProcess> waitList=myWaitProcessService.findByInstanceId(instanceId);
			if(waitList!=null&&waitList.size()>0){
				for(MyWaitProcess myWaitProcess:waitList){
					Map<String,String> map=new HashMap<String,String>();
					map.put("userName", myWaitProcess.getProcesserName());
					map.put("userId", myWaitProcess.getProcesserId().toString());
					processerUser.add(map);
				}
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("instanceId", instanceId);
		map.put("processerUser", processerUser);
		Gson gson=new Gson();
		ajax("100||"+gson.toJson(map));
		return null;
	}
	
	
	
	/**
	 * 功能：更新表单数据
	 * @return
	 */
	public String updateFormData(){
		try{
			jbpmAppService.updateFormData(taskId, formData);
			ajax("100||0");
		}catch(Exception e){
			ajax("102||1");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 功能：判断任务是否已完成
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String taskIsEnd() throws Exception{
		instanceId = URLDecoder.decode(instanceId, "UTF-8");
		UserInfo ui = userService.findOne(Integer.parseInt(userId));
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).assignee(ui.getLoginName()).uniqueResult();
		String str = "";
		if(task == null){
			str = "end";
		}else{
			str = "wait";
		}
		ajax("100||"+str);
		return null;
	}
	
	/**获取流程分类和流程定义列表(显示流程分类下的流程数量)
	 * @return
	 */
	public void getProcessList(){
		List<ProcessTypeModel> list = new ArrayList<ProcessTypeModel>();
		UserInfo user = userService.findOne(Integer.parseInt(userId));
		
		//查询所有的流程分类
		List<FormCategory> formCategorys = formCategoryService.findByTypeCompanyId(user.getCompanyId(), 2);
		//查询所有的流程定义
		List<ProcessAttribute> processAttributes = processAttributeService.findProcessAttributesByPermissions(user,0,"");
		for(int i=0; i<formCategorys.size(); i++){
			ProcessTypeModel ptm = new ProcessTypeModel();
			FormCategory temp = formCategorys.get(i);
			ptm.setCategoryId(temp.getCategoryId());
			ptm.setCategoryName(temp.getCategoryName());
			if(processAttributes!=null){
				for(Iterator<ProcessAttribute> it = processAttributes.iterator(); it.hasNext();){
					ProcessAttribute pa = it.next();
					if(pa.getCategoryId().intValue() == temp.getCategoryId().intValue()){
						ptm.setNum(ptm.getNum()+1);
						ptm.addDefine(pa.getId(),pa.getProcessName());
					}
				}
			}
			list.add(ptm);
		}
		ajax("100||"+new Gson().toJson(list));;
	}
}
