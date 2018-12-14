package cn.com.qytx.cbb.jbpmApp.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowVar;
import cn.com.qytx.cbb.jbpmApp.service.IJbpmApp;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.jbpmApp.service.IWorkflowVar;
import cn.com.qytx.cbb.myapply.service.MyApplyService;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.cbb.publicDom.service.IDocumentExtService;
import cn.com.qytx.cbb.publicDom.service.PublicDomService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能：工作流操作接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:28:57 
 * 修改日期：下午4:28:57 
 * 修改列表：
 */
public class WorkflowOperationAction extends BaseActionSupport {
	
	private String title;
	private String formDataJSON;
	private String attachJSON;
	private String advice;
	private int processId;
	private String outComeName;
	private String holdStr;
	private String processInstanceId;
	private String taskId;
	private Integer documentExtId;
	private Integer newAssigner;
	@Resource(name="jbpmAppService")
	private IJbpmApp jbpmAppService;
	@Resource(name="publicDomService")
	private PublicDomService publicDomService;
	@Resource(name = "workFlowService")
	private IWorkFlowService workFlowService;  //工作流服务
	@Resource
	private IDocumentExtService documentExtService;
	@Resource(name="myApplyService")
	private MyApplyService myApplyService;
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private IWorkflowVar workflowVarService;
	@Resource
	private IUser userService;
	
	/**
	 * 功能：发起流程
	 * @param
	 * @return
	 * @throws   
	 */
	public String startProcess() throws Exception{
		UserInfo ui =getLoginUser();
		String instanceId = jbpmAppService.startProcess(ui, title, formDataJSON, attachJSON, "", processId, outComeName, holdStr);
		//从session中取出审批意见保存到数据库中
		Object jbpmAdvice = getSession().getAttribute("tdAdvice");
		if(jbpmAdvice!=null){
			List<JbpmAdvice> ja = (List<JbpmAdvice>)jbpmAdvice;
			publicDomService.saveAdvice(instanceId, ja);
		}
		//设置公文正文表(DocumentExt)的instanceId
		if(documentExtId!=null){
			DocumentExt ext = documentExtService.findOne(documentExtId);
			ext.setProcessInstanceId(instanceId);
			ext.setCompanyId(ui.getCompanyId());
			documentExtService.saveOrUpdate(ext);
		}
		ajax("success");
		return null;
	}
	
	
	/**
	 * 功能：删除任务
	 * @return
	 */
	public String deleteInstance(){
		try {
			workFlowService.deleteProcessInstance(processInstanceId);
			ajax(1);
		} catch (Exception ex) {
			ajax(-1);
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 功能：完成任务，并提交下一步
	 * @param
	 * @return
	 * @throws   
	 */
	public String completdTask() throws Exception{
		UserInfo ui = getLoginUser();
		jbpmAppService.completeTask(ui, taskId, attachJSON, formDataJSON, advice, outComeName, holdStr);
		ajax("success");
		return null;
	}	
	
	
	/**
	 * 功能：驳回，回退任务
	 * @return
	 * @throws Exception 
	 */
	public String rollbackTask() throws Exception{
		UserInfo userinfo =getLoginUser();
		processInstanceId = URLDecoder.decode(processInstanceId, "UTF-8");
		jbpmAppService.rollbackTask(processInstanceId, null, null, userinfo, advice,JpdlInterface.PROCESS_STATE_ROLLBACK);
		ajax("1");
		return null;
	}
	
	/**
	 * 功能：撤销任务
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String repealTask(){
		UserInfo userinfo =getLoginUser();
		jbpmAppService.rollbackTask(processInstanceId, null, null, userinfo, advice,JpdlInterface.PROCESS_STATE_REPEAL);
		//myApplyService.delMyStarted(processInstanceId, ModuleCode.WORKFLOW);
		ajax("1");
		return null;
	}
	
	/**催办提醒
	 * @return
	 * @throws Exception
	 */
	public String notifyNextUser() throws Exception{
		List<Task> tasklist = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
		//收集接收人信息
		String nextUsers = "";
		for(int i=0; i<tasklist.size(); i++){
			Task temp = tasklist.get(i);
			String assignee = temp.getAssignee();
			if(assignee !=null && assignee.split("_").length>1){
				nextUsers+=assignee.split("_")[1];
			}else{
				nextUsers+=assignee;
			}
		}
		jbpmAppService.setAffairsSign(nextUsers, getLoginUser(), processInstanceId);
		ajax("success");
		return null;
	}
	
	/**********************委托设置 开始*****************************/
	/**跳转到委托设置界面
	 * @return
	 * @throws Exception
	 */
	public String assigneeOther() throws Exception{
		return "assigneeOther";
	}
	
	public Map<String,String> getAssigneeSet(){
		Map<String,String> result = new HashMap<String, String>();
		Task task = processEngine.getTaskService().getTask(taskId);
		String assign = task.getAssignee();
		UserInfo u = userService.findByLoginName(assign);
		String instanceId = task.getExecutionId();
		WorkflowVar var = workflowVarService.findByInstanceId(instanceId);
		result.put("title", var.getTitle());
		result.put("taskName", task.getName());
		result.put("oldAssignee", u==null?"":u.getUserName());
		return result;
	}
	
	/**设置新的任务班里人
	 * @return
	 */
	public String doAssigneeOther(){
		workFlowService.assigneeNewUser(taskId, newAssigner);
		ajax("success");
		return null;
	}
	
	/**********************委托设置 结束******************************/
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFormDataJSON() {
		return formDataJSON;
	}
	public void setFormDataJSON(String formDataJSON) {
		this.formDataJSON = formDataJSON;
	}
	public String getAttachJSON() {
		return attachJSON;
	}
	public void setAttachJSON(String attachJSON) {
		this.attachJSON = attachJSON;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public String getOutComeName() {
		return outComeName;
	}
	public void setOutComeName(String outComeName) {
		this.outComeName = outComeName;
	}
	public String getHoldStr() {
		return holdStr;
	}
	public void setHoldStr(String holdStr) {
		this.holdStr = holdStr;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public Integer getDocumentExtId() {
		return documentExtId;
	}


	public void setDocumentExtId(Integer documentExtId) {
		this.documentExtId = documentExtId;
	}


	public Integer getNewAssigner() {
		return newAssigner;
	}


	public void setNewAssigner(Integer newAssigner) {
		this.newAssigner = newAssigner;
	}
	
}
