package cn.com.qytx.cbb.jbpmApp.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairSetting.service.IAffairSetting;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.domain.ProcessAttribute;
import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.customJpdl.service.impl.template.SubTaskManager;
import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;
import cn.com.qytx.cbb.jbpmApp.dao.MuliSignRollbackDao;
import cn.com.qytx.cbb.jbpmApp.dao.WorkflowVarDao;
import cn.com.qytx.cbb.jbpmApp.domain.TaskTraceVar;
import cn.com.qytx.cbb.jbpmApp.domain.WorkFlowView;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowVar;
import cn.com.qytx.cbb.jbpmApp.service.IWorkFlowService;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.GetSuperTaskCommand;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.SearchAllStartTaskList;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.SearchEndTaskList;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.SearchListenerTaskList;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.SearchMyApprovedTaskList;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.SearchMyStartTaskList;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.SearchWaitProcessTaskList;
import cn.com.qytx.cbb.jbpmApp.service.impl.command.TaskSetStateCommand;
import cn.com.qytx.cbb.myapply.service.MyApplyService;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

/**
 * 工作流接口实现
 * @author anxing
 *
 */
@Service("workFlowService")
@Transactional
public class WorkFlowServiceImpl implements IWorkFlowService {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER=LoggerFactory.getLogger(WorkFlowServiceImpl.class);
	@Resource(name = "processEngine")
	private ProcessEngine processEngine;
	@Resource(name = "nodeFormAttributeService")
	NodeFormAttributeService nodeFormAttributeService;
	@Resource(name="muliSignRollbackDao")
	private MuliSignRollbackDao muliSignRollbackDao;
	
	@Resource(name="workflowVarDao")
	WorkflowVarDao workflowVarDao;
	
	@Resource(name="userService")
	IUser  userService;
	
	@Resource(name="processAttributeService")
	private ProcessAttributeService processAttributeService;
	
	@Resource
	private SubTaskManager subTaskManager;
	@Resource
	IAffairSetting affairSetting;
	@Resource
	IAffairsBody affairsBody;
	@Resource
	MyApplyService myApplyService;
	
	private RepositoryService repositoryService;
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	public RepositoryService getRepositoryService() {
		return repositoryService;
	}
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	public ExecutionService getExecutionService() {
		return executionService;
	}
	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
	}
	public TaskService getTaskService() {
		return taskService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private ExecutionService executionService;
	private TaskService taskService;
	private HistoryService historyService;
    public WorkFlowServiceImpl()
    {
    	
    }
    
    /**
     * 初始化主要JBPM服务
     */
    public void initialize()
    {
    	if(repositoryService==null)
    	{
	    	repositoryService=processEngine.getRepositoryService();
	        executionService=processEngine.getExecutionService();
	        taskService=processEngine.getTaskService();
	        historyService=processEngine.getHistoryService();
    	}
    }

    /**
     * 功能：开始任务
     * @param processDefinitionId
     * @param userId
     * @return
     */
	@Override
	public ProcessInstance startProcessInstance(String processDefinitionId,
			String userId) {
		// TODO Auto-generated method stub
		Map<String, String> map=new HashMap<String, String>();
		map.put(WorkFlowConstants.VAR_CONSTANTS.CREATER, userId);
		ProcessInstance instance=processEngine.getExecutionService().startProcessInstanceById(processDefinitionId,map);	
		return instance;
	}
	
	/**
	 * 功能：结束任务
	 * @param tas
	 * @param userinfo
	 * @param outComeName
	 * @param nextTaskUserId
	 * @param instanceId
	 * @return
	 */
	@Override
	public boolean completeTask(Task tas,UserInfo userinfo,String outComeName,String nextTaskUserId,String instanceId) {
		// TODO Auto-generated method stub
		initialize();
		WorkflowVar wfv = workflowVarDao.findByInstanceId(instanceId);
		Task task = tas;
		//设置流程变量
		Map<String,String> vars = new HashMap<String,String>();
		vars.put(WorkFlowConstants.VAR_CONSTANTS.CANDIDATE_USERS,nextTaskUserId);
		
		//更新工作流变量对象
		processEngine.getExecutionService().createVariables(wfv.getInstanceId(), vars, false);
		
		//更新办理人信息
		wfv.setBeforeUser(userinfo.getUserName());
		wfv.setBeforeTaskName(wfv.getCurrentTaskName());
		wfv.setCurrentUser("");
		LOGGER.info(instanceId+"[下一步任务处理人][登陆名]:"+nextTaskUserId);
		if(nextTaskUserId!=null && !nextTaskUserId.equals("") && !nextTaskUserId.contains(",")){
			UserInfo ui = userService.companyId(userinfo.getCompanyId()).findOne("loginName = ? and isDelete = 0", nextTaskUserId);
			LOGGER.info(instanceId+"[下一步任务处理人][单位ID]:"+userinfo.getCompanyId() );
			if(ui!=null){
				LOGGER.info(instanceId+"[下一步任务处理人][用户ID]:"+ui.getUserId());
				wfv.setCurrentUser(ui.getUserName());
			}else{
				LOGGER.info(instanceId+"[下一步任务处理人][用户不存在]");
				return false;
			}
		}
		
		//判断当前节点是否是会签
		boolean signFlag = false;
		long tasksize = processEngine.getTaskService().createTaskQuery().processInstanceId(wfv.getInstanceId()).count();
		if(tasksize>1){
			signFlag = true;
		}
		
		//判断下一步是否是结束节点
		boolean endFlag = false;
		String nextActionName = null;
		if(tasksize<3){
			if(outComeName!=null && !outComeName.equals("")){
				String[] ss = outComeName.split(" ");
				if(ss.length>1){
					nextActionName = ss[1];
					if(ss[1].equals(JpdlInterface.END_NODE_NAME)){
						endFlag = true;
					}
				}
			}
		}
		if(nextActionName!=null){
			wfv.setCurrentTaskName(nextActionName);
		}
		updateInstanceState(wfv, endFlag);
		//非会签
		if(!signFlag) {
			taskService.completeTask(tas.getId(),outComeName,vars);
		//会签
		}else{
			subTaskManager.completeSubTask(task, outComeName);
		}
		workflowVarDao.updateWorkflowVar(wfv);
		
		return true;
	}
	
	/**
	 * 功能：更新流程状态
	 * @param wfv
	 * @param endFlag
	 */
	private void updateInstanceState(WorkflowVar wfv,boolean endFlag){
		if(endFlag){
			wfv.setCurrentState(JpdlInterface.PROCESS_STATE_END);
			Set<String> set = processEngine.getExecutionService().getVariableNames(wfv.getInstanceId());
			Map<String,Object> map = processEngine.getExecutionService().getVariables(wfv.getInstanceId(), set);
			processEngine.getExecutionService().createVariables(wfv.getInstanceId(), map, true);
		}else{
			wfv.setCurrentState(JpdlInterface.PROCESS_STATE_APPROVE);
		}
	}
	
	/**
	 * 功能：获得回滚的任务名称
	 * @param taskId
	 * @return
	 */
	private String getRollbackOutcomeName(String taskId)
	{
		Task task=taskService.getTask(taskId);
		@SuppressWarnings("unchecked")
		LinkedList<TaskTraceVar> list=(LinkedList<TaskTraceVar>)
				taskService.getVariable(taskId,WorkFlowConstants.VAR_CONSTANTS.TASKTRACE);
		if(list==null)  //这里可能是第一个节点
			return null;
		else
		{
			TaskTraceVar var=list.getLast();
			String back=task.getName()+" rollbackto "+var.getTaskName();
			return back;
		}
	}
	/**
	 * 功能：update by 贾永强
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public boolean rollbackTask(String instanceId,UserInfo currentUser,String state) {
		ProcessInstance processInstance = processEngine.getExecutionService().findProcessInstanceById(instanceId);
		String processDefinitionId=processInstance.getProcessDefinitionId();
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(instanceId).list();
		int size =  tasks.size();
		String taskId = "";
		if(tasks.size()==1){//非会签
			taskId = tasks.get(0).getId();
		}else if(tasks.size()>1){//会签,获取父任务
			for(int i=0; i<tasks.size(); i++){
				Task t = tasks.get(i);
				if(t.getAssignee() == null || t.getAssignee().equals("")){
					taskId = t.getId();
					break;
				}
			}
		}
		//创建动态路径
		String backOutcome = createRollbackPath(taskId);
		processEngine.getTaskService().completeTask(taskId,backOutcome);
		//考虑会签打回的情况，删除子任务，更新历史子任务为已结束
		if(size>1){
			muliSignRollbackDao.deleteSubTask(taskId);
		}
		WorkflowVar wfv = workflowVarDao.findByInstanceId(instanceId);
		wfv.setCurrentUser("");
		wfv.setCurrentTaskName("结束");
		wfv.setCurrentState(state);
		workflowVarDao.saveOrUpdate(wfv);
		
		//移除动态路径
		removeRollbackPath(processDefinitionId,tasks.get(0).getActivityName(),backOutcome);
		return true;
	}
	
	/**
	 * 功能：移除创建的虚拟路径
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	private void removeRollbackPath(String processDefineId,String taskName,String backOutcome){
		ProcessDefinitionImpl processDefinition=(ProcessDefinitionImpl)processEngine.getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(processDefineId)
				.uniqueResult();
		
		//获取起始节点和结束节点的节点对象和节点名称
		ActivityImpl from = processDefinition.findActivity(taskName);
		ActivityImpl to = processDefinition.findActivity("结束");
		
		TransitionImpl ti = from.getOutgoingTransition(backOutcome);
		from.removeOutgoingTransition(ti);
	}
	
	
	
	
	/*
	 * 功能：创建回退路径,回退到结束节点
	 * @param
	 * @return
	 * @throws 
	 * add  by 贾永强  
	 */
	private String createRollbackPath(String taskId){
		Task task = processEngine.getTaskService().getTask(taskId);
		ProcessInstance processInstance = processEngine.getExecutionService().findProcessInstanceById(task.getExecutionId());
		String processDefinitionId=processInstance.getProcessDefinitionId();
		ProcessDefinitionImpl processDefinition=(ProcessDefinitionImpl)processEngine.getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
				.uniqueResult();
		
		//获取起始节点和结束节点的节点对象和节点名称
		String fromactiveName = processEngine.getTaskService().getTask(taskId).getActivityName();
		ActivityImpl from = processDefinition.findActivity(fromactiveName);
		ActivityImpl to = processDefinition.findActivity("结束");
		String toName = to.getName();
		String rollbackPaht = fromactiveName+"rollbackto"+toName;
		
		TransitionImpl transition=from.createOutgoingTransition();
		transition.setName(rollbackPaht);
		transition.setDestination(to);
		return rollbackPaht;
	}

	/**
	 * 功能：挂起任务
	 * @param userId
	 * @param taskId
	 * @return
	 */
	@Override
	public boolean suspendTask(String userId, String taskId) {
		// TODO Auto-generated method stub
		initialize();
		Task task=taskService.getTask(taskId);
		TaskImpl taskImpl=(TaskImpl)task;
		TaskSetStateCommand cmd=new TaskSetStateCommand(taskImpl,Task.STATE_SUSPENDED);
		processEngine.execute(cmd);
		
		return true;
	}
	
	/**
	 * 设置流程全局变量
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	@Override
	public boolean setVariablebyInstance(String processInstanceId, Map<String, ?> var) {
		// TODO Auto-generated method stub
		//更新流程变量到表tb_workflow_var中
		WorkflowVar wfv = workflowVarDao.findByInstanceId(processInstanceId);
		if(wfv == null){
			wfv = new WorkflowVar();
		}
		wfv.setInstanceId(processInstanceId);
		if(var!=null){
			if(var.get("currentState")!=null){
				wfv.setCurrentState((String)var.get("currentState"));
			}
			if(var.get("currentTaskName")!=null){
				wfv.setCurrentTaskName((String)var.get("currentTaskName"));
			}
			if(var.get("breforeTaskName")!=null){
				wfv.setBeforeTaskName((String)var.get("breforeTaskName"));
			}
			if(var.get("beforeUser")!=null){
				wfv.setBeforeUser((String)var.get("beforeUser"));
			}
			if(var.get("currentUser")!=null){
				wfv.setCurrentUser((String)var.get("currentUser"));
			}
			
			if(var.get("suspendTime")!=null){
				wfv.setSuspendTime((Timestamp)var.get("suspendTime"));
			}
		}
		workflowVarDao.updateWorkflowVar(wfv);
		HistoryProcessInstance instance = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(processInstanceId).uniqueResult();
		if(!instance.getState().equals(HistoryProcessInstance.STATE_ENDED)){
			if(var !=null){
				executionService.createVariables(processInstanceId, var,false);
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * 获得某流程的变量
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	@Override
	public String getVariablebyInstance(String processInstanceId, String key) {
		// TODO Auto-generated method stub
		HistoryProcessInstance pi = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(processInstanceId).uniqueResult();
		if(pi.getState().equals(HistoryProcessInstance.STATE_ACTIVE)){
			return (String) processEngine.getExecutionService().getVariable(processInstanceId, key);
		}else{
			return (String) processEngine.getHistoryService().getVariable(processInstanceId, key);
		}
	}

	/**
	 * 功能：删除历史流程实例
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public boolean deleteProcessInstance(String processInstanceId) {
		// TODO Auto-generated method stub
		HistoryProcessInstance pi = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(processInstanceId).uniqueResult();
		if(pi.getState().equals(HistoryProcessInstance.STATE_ACTIVE)){
			processEngine.getExecutionService().deleteProcessInstanceCascade(processInstanceId);
		}else{
			processEngine.execute(new DeleteInstanceCmd(processInstanceId));
		}
		workflowVarDao.deleteWorkflowVar(processInstanceId);
		//domflowVarDao.deleteByInstanceId(processInstanceId);
		myApplyService.delMyStarted(processInstanceId, ModuleCode.WORKFLOW);
		return true;
	}
	
	/**
	 * 功能：获得我的审批数量
	 * @param userIdOrLoginName
	 * @return
	 */
	@Override
	public int getApproveCount(String userIdOrLoginName) {
	    long l =  processEngine.getTaskService().createTaskQuery().assignee(userIdOrLoginName).count();
	    String str = String.valueOf(l);
	    return Integer.parseInt(str);
	}
	
	/**
	 * 功能：查询待我处理的任务
	 * state:审批中/已挂起
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public Page<WorkFlowView> findWaitProcessViewlist(String userId,String state,String searchkey,Pageable page) {
		// TODO Auto-generated method stub
		return processEngine.execute(new SearchWaitProcessTaskList(userId,page,state,searchkey,""));
	}
	/**
	 * 功能：查询我发起的任务
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public Page<WorkFlowView> findByStartList(String userId,String searchkey, Pageable page) {
		// TODO Auto-generated method stub
		//List<WorkFlowView> plist = processEngine.execute(new SearchMyStartTaskList(userId, page));
		return processEngine.execute(new SearchMyStartTaskList(userId,searchkey, page));
	}
	
	/**
	 * 功能：查询发起的任务
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public Page<WorkFlowView> findByStartAllList(String userId,String searchkey, Pageable page,Integer processattributeid) {
		// TODO Auto-generated method stub
		//List<WorkFlowView> plist = processEngine.execute(new SearchMyStartTaskList(userId, page));
		return processEngine.execute(new SearchAllStartTaskList(userId, searchkey, page, processattributeid));
	}
	
	/**
	 * 功能：查询经我处理的任务
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public Page<WorkFlowView> findByApprovedList(String userId,String searchkey,Pageable  page) {
		// TODO Auto-generated method stub
	//	List<WorkFlowView> plist = processEngine.execute(new SearchMyApprovedTaskList(userId, page));
		return  processEngine.execute(new SearchMyApprovedTaskList(userId,searchkey, page));
	}
	
	/**
	 * 功能：查询办结的任务
	 * @param searchkey
	 * @param page
	 * @return
	 */
	public Page<WorkFlowView> findByendList(Integer processId,String searchkey,String beginDate,String endDate,Pageable page){
		return  processEngine.execute(new SearchEndTaskList(processId,searchkey,beginDate,endDate, page));
	}
	/**
	 * 功能：获取发起任务的时候需要选择的下一步流程 节点信息 ,开始节点固定为“发起流程”
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public List<NodeFormAttribute> findStartSelectNode(int processId) {
		// TODO Auto-generated method stub
		initialize();
		List<NodeFormAttribute> list = new ArrayList<NodeFormAttribute>();
		ProcessAttribute pa = processAttributeService.getProcessById(processId);
		ProcessDefinitionImpl definition = (ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().processDefinitionId(pa.getProcessDefineId()).uniqueResult();
		ActivityImpl starter = definition.getActivity("发起流程");
		List<TransitionImpl> outs = (List<TransitionImpl>) starter.getOutgoingTransitions();
		for(int i=0;i<outs.size(); i++){
			TransitionImpl temp = outs.get(i);
			ActivityImpl dest = temp.getDestination();
			NodeFormAttribute nfa = nodeFormAttributeService.findByProcessIdAndTaskName(processId, dest.getName());
			list.add(nfa);
		}
		return list;
	}
	
	/**
	 * add by 贾永强
	 * 功能：获取下一步流程信息
	 * @param
	 * @return{name:"",type:"",nodeId:""}
	 * @throws   
	 */
	@Override
	public List<Map<String, String>> findApplyNextNodes(String taskId,int processId) {
		// TODO Auto-generated method stub
		initialize();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Task task = taskService.getTask(taskId);
		ProcessAttribute pa = processAttributeService.getProcessById(processId);
		ProcessDefinitionImpl definition = (ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().processDefinitionId(pa.getProcessDefineId()).uniqueResult();
		ActivityImpl ai = definition.getActivity(task.getActivityName());
		List<TransitionImpl> outs = (List<TransitionImpl>) ai.getOutgoingTransitions();
		for(int i=0; i<outs.size(); i++){
			TransitionImpl temp = outs.get(i);
			ActivityImpl dest = temp.getDestination();
			Map<String,String> map = new HashMap<String, String>(); 
			if(dest.getName().equals("结束")){
				map.put("name", "结束");
				map.put("type", "end");
				map.put("nodeId", "0");
			}else{
				NodeFormAttribute nfa = nodeFormAttributeService.findByProcessIdAndTaskName(processId, dest.getName());
				if(nfa!=null){
					map.put("name", nfa.getNodeName());
					map.put("type", nfa.getNodeType());
					map.put("nodeId", nfa.getId().toString());
				//默认是task节点
				}else{
					map.put("name", dest.getName());
					map.put("type", "task");
				}
			}
			
			boolean f = true;
			for(Iterator it = list.iterator(); it.hasNext();){
				Map<String,String> mn = (Map<String, String>) it.next();
				if(mn.get("name").equals(map.get("name"))){
					f = false;
					break;
				}
			}
			if(f){
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 功能：显示流程节点图
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public List<JbpmNodeState> findJbpmNodesState(String instanceId,
			int processId) {
		List<JbpmNodeState> list = new ArrayList<JbpmNodeState>();
		initialize();
		//第一步，查询审批历史，将审批历史放进查询列表中
		String advice = "";
		ProcessInstance processInstance = 
				executionService.createProcessInstanceQuery().processInstanceId(instanceId).uniqueResult();

		advice = getVariablebyInstance(instanceId, JpdlInterface.VAR_ADVICE);
		
		Gson gson = new Gson();
		List<JbpmAdvice> map = gson.fromJson(advice,new com.google.common.reflect.TypeToken<List<JbpmAdvice>>() {
		}.getType());
		for(Iterator<JbpmAdvice> it = map.iterator(); it.hasNext();){
			JbpmAdvice temp = it.next();
			JbpmNodeState node = new JbpmNodeState();
			node.setApproveTime(temp.getCreateTime());
			node.setCompleted(true);
			node.setNodeName(temp.getTaskName());
			node.setResult(temp.getResult());
			node.setUserName(temp.getUserName());
			node.setState("before");
			if(node.getNodeName()!=null && node.getNodeName().equals("发起流程")){
				node.setState("start");
			}
			node.setContent(temp.getContent());
			list.add(node);
		}
		LOGGER.info(">>>>>>已审批列表:"+gson.toJson(list));
		//如果流程结束了，不用判断isShowNotCompletedNode 的true、false
		if(processInstance == null){
			JbpmNodeState node = new JbpmNodeState();
			node.setApproveTime("");
			node.setCompleted(true);
			node.setNodeName("结束");
			node.setResult("");
			node.setUserName("");
			node.setState("endOk");
			list.add(node);
			LOGGER.info(">>>>>>>>>>>流程已结束:"+gson.toJson(list));
		}else{//流程未结束
			LOGGER.info(">>>>>>>>>>流程未结束");
			ProcessAttribute pa = processAttributeService.getProcessById(processId);
			ProcessDefinitionImpl definition = (ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().processDefinitionId(pa.getProcessDefineId()).uniqueResult();
			
			//获取当前节点
			List<Task> tasklist = taskService.createTaskQuery().processInstanceId(instanceId).list();
			Task currentTask = tasklist.get(0);
			ActivityImpl currentNode  = definition.findActivity(currentTask.getName());
			
			//判断是否显示完整的流程
//			boolean show = isShowNotCompletedNode(instanceId, processId);
			boolean show = true;
			if(show){//迭代显示所有的节点
				iteratorActivity(list, currentNode,"now");
				LOGGER.info(">>>>>>>>>>>>>显示所有节点:"+gson.toJson(list));
			}else{//只显示到当前节点
				JbpmNodeState node = new JbpmNodeState();
				node.setApproveTime("");
				node.setCompleted(false);
				if(currentNode!=null){
				    node.setNodeName(currentNode.getName());
				}
				node.setResult("");
				node.setUserName("");
				node.setState("now");
				list.add(node);
			}
			JbpmNodeState endNode = list.get(list.size()-1);
			
			//如果最后一个节点是结束，则标记为endNo,否则就是当前节点
			if(endNode.getNodeName().equals("结束")){
				endNode.setState("endNo");
			}else{
				endNode.setState("now");
			}
			LOGGER.info(">>>>>>>>>>>>>>>>>最终节点列表:"+gson.toJson(list));
		}
		
		return list;
	}
	
	/**
	 * 功能：迭代获取未审批的节点
	 * @param
	 * @return
	 * @throws   
	 */
	private void iteratorActivity(List<JbpmNodeState> nodelist,ActivityImpl beginNode,String state){
		JbpmNodeState node = new JbpmNodeState();
		node.setApproveTime("");
		node.setCompleted(false);
		node.setNodeName(beginNode.getName());
		node.setResult("");
		node.setUserName("");
		node.setState(state);
		nodelist.add(node);
		TransitionImpl next = beginNode.getDefaultOutgoingTransition();
		if(next!=null){
			ActivityImpl nextNode = next.getDestination();
			iteratorActivity(nodelist, nextNode,"after");
		}
	}
	
	/**是否显示完整流程：true显示；false不显示
	 * @param instanceId
	 * @param processId
	 * @return
	 */
	private boolean isShowNotCompletedNode(String instanceId,int processId) {
		// TODO Auto-generated method stub
		boolean result = true;
		initialize();
		ProcessAttribute pa = processAttributeService.getProcessById(processId);
		ProcessDefinitionImpl definition = (ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().processDefinitionId(pa.getProcessDefineId()).uniqueResult();
		List<ActivityImpl> activelist = (List<ActivityImpl>) definition.getActivities();
		for(int i=0; i<activelist.size();i++){
			ActivityImpl activity = activelist.get(i);
			List<TransitionImpl> outs = (List<TransitionImpl>) activity.getOutgoingTransitions();
			//分支大于1,并且该分支节点没有结束的的时候有分支，不显示未完成的节点
			if(outs.size()>1){
				String nodeName = activity.getName();
				List<HistoryActivityInstance> temp = historyService.createHistoryActivityInstanceQuery().processInstanceId(instanceId).activityName(nodeName).list();
				if(temp==null || temp.size()==0){
					result = false;
					break;
				}else{
					for(int j=0; j<temp.size(); j++){
						if(temp.get(j).getEndTime()==null){
							result = false;
							break;
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 功能：获取下一步的操作，
	 * 1，考虑了会签节点的实现
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
    public Set<String> getNextOperations(String taskId) {
	    // TODO Auto-generated method stub
		String theTaskId = taskId;
		
		//如果是会签，且当前只有一个活动子任务，则返回该父任务的下一步操作节点
		String superTask = processEngine.execute(new GetSuperTaskCommand(taskId));
		if(superTask!=null){
			TaskImpl impl = (TaskImpl) processEngine.getTaskService().getTask(superTask);
			if(impl.getSubTasks().size()>1){
				return new HashSet<String>();
			}else{
				theTaskId = superTask;
			}
		}
		
		return processEngine.getTaskService().getOutcomes(theTaskId);
    }
	
	/**
	 * 功能：根据TaskId获取流程实例ID
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
    public String getInstanceIdByTask(String taskId) {
	    // TODO Auto-generated method stub
		TaskImpl impl = (TaskImpl) processEngine.getTaskService().getTask(taskId);
		return impl.getProcessInstance().getId();
    }
	
	/**查询监控任务列表
	 * @param searchkey
	 * @param beginDate
	 * @param endDate
	 * @param page
	 * @return
	 */
	@Override
	public Page<WorkFlowView> findListenerlist(String searchkey,
			String beginDate, String endDate, Pageable page) {
		// TODO Auto-generated method stub
		return processEngine.execute(new SearchListenerTaskList(page, SearchWaitProcessTaskList.PROCESS_STATE_ACTIVE, searchkey, beginDate, endDate));
	}
	
	/**指派新的办理人
	 * @param taskId 任务Id 
	 * @param newUserId  新的办理人
	 */
	@Override
	public void assigneeNewUser(String taskId, int newUserId) {
		// TODO Auto-generated method stub
		UserInfo user = userService.findOne(newUserId);
		Task task = processEngine.getTaskService().getTask(taskId);
		
		EnvironmentImpl env = ((EnvironmentFactory)processEngine).openEnvironment();
		try{
		   // TODO;
			 task.setAssignee(user.getLoginName());
		} finally{
		    env.close();
		}
		
		WorkflowVar var = workflowVarDao.findByInstanceId(task.getExecutionId());
		var.setCurrentUser(user.getUserName());
		workflowVarDao.updateWorkflowVar(var);
	}
}
