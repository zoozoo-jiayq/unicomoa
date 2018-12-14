package cn.com.qytx.cbb.jbpmApp.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;

import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.jbpmApp.domain.WorkFlowView;
import cn.com.qytx.cbb.jbpmApp.service.impl.JbpmNodeState;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 流程实例接口
 * @author anxing
 *
 */
public interface IWorkFlowService extends Serializable{
	
	/**
	 * 启动流程实例
	 * @param processDefinitionId 流程定义ID
	 * @param userId 流程创建用户Id
	 * @param processInstanceKey 流程实例KEY，例如流程文号
	 * @return NOT NULL：流程实例；NULL：流程创建失败，可能流程已经存在
	 */
	ProcessInstance startProcessInstance(String processDefinitionId,
			String userId);
	
	/**
	 * 功能：删除历史流程实例
	 * @param
	 * @return
	 * @throws   
	 */
	boolean deleteProcessInstance(String processInstanceId);
	

	/**
	 * 任务完成，转入下个任务，【同意】的走向
	 * @param taskId 任务ID
	 * @param userId 完成任务的用户ID
	 * @param comment 会签意见
	 * @param outComeName 流出名称
	 * @param nextTaskMainUserId 办理人员ID，多个用户用","隔开
	 * @return
	 * update by 贾永强 2013-7-16
	 */
	boolean completeTask(Task task,UserInfo ui,String outComeName
			,String nextTaskUserId,String instanceId);


	/**
	 * 任务驳回或者撤销，根据state字段区分，endnoagree:驳回；repeal：撤销
	 * @param taskId 任务ID
	 * @return
	*/
	boolean rollbackTask(String taskId,UserInfo currentUser,String state);
	
	/**
	 * 挂起任务
	 * @param userId 用户ID
	 * @param TaskId
	 * @return
	 */
	boolean suspendTask(String userId,String taskId);

	/**
	 * 设置流程全局变量
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	boolean setVariablebyInstance(String processInstanceId,Map<String,?> var);

	/**
	 * 获得某流程的变量
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	String getVariablebyInstance(String processInstanceId,String key);

	/**
	 * 功能：获得我的审批数量
	 * @param userIdOrLoginName
	 * @return
	 */
	int getApproveCount(String userIdOrLoginName);
	
	/**
	 * add by jiayq
	 * 功能：查询待我审批的任务列表
	 * state:审批中/已挂起
	 * @param
	 * @return
	 * @throws   
	 */
	Page<WorkFlowView> findWaitProcessViewlist(String userId,String state,String searchkey,Pageable page);
	
	/**
	 * add by jiayq
	 * 功能：查询我发起的任务列表
	 * @param
	 * @return
	 * @throws   
	 */
	Page<WorkFlowView> findByStartList(String userId,String searchkey,Pageable page);
	
	/**
	 * 功能：查询经我处理的任务
	 * @param
	 * @return
	 * @throws   
	 */
	Page<WorkFlowView> findByApprovedList(String userId,String searchkey,Pageable page);
	/**
	 * 功能：查询办结的任务
	 * @param searchkey
	 * @param page
	 * @return
	 */
	Page<WorkFlowView> findByendList(Integer processId,String searchkey,String beginDate,String endDate,Pageable page);
	/**
	 * add by 贾永强
	 * 功能：获取发起任务的时候需要选择的下一步流程 节点信息
	 * @param
	 * @return
	 * @throws   
	 */
	List<NodeFormAttribute> findStartSelectNode(int processId);
	
	/**
	 * add by 贾永强
	 * 功能：获取下一步流程信息
	 * @param
	 * @return{name:"",type:"",nodeId:""}
	 * @throws   
	 */
	List<Map<String,String>> findApplyNextNodes(String taskId,int processId);
	
	/**
	 * 功能：查询发起的任务
	 * @param
	 * @return
	 * @throws   
	 */
	
	public Page<WorkFlowView> findByStartAllList(String userId,String searchkey, Pageable page,Integer instanceId);
	
	
	/**
	 * 功能：显示流程节点图
	 * @param
	 * @return
	 * @throws   
	 */
	List<JbpmNodeState> findJbpmNodesState(String instanceId,int processId);
	
	/**
	 * 功能：获取下一步的操作，
	 * 1，考虑了会签节点的实现
	 * @param
	 * @return
	 * @throws   
	 */
	public Set<String> getNextOperations(String taskId);
	
	/**
	 * 功能：根据TaskId获取流程实例ID
	 * @param
	 * @return
	 * @throws   
	 */
	public String getInstanceIdByTask(String taskId);
	
	/**查询监控任务列表
	 * @param searchkey
	 * @param beginDate
	 * @param endDate
	 * @param page
	 * @return
	 */
	public Page<WorkFlowView> findListenerlist(String searchkey,String beginDate,String endDate,Pageable page);
	
	/**指派新的办理人
	 * @param taskId 任务Id 
	 * @param newUserId  新的办理人
	 */
	public void assigneeNewUser(String taskId,int newUserId);
}
