package cn.com.qytx.cbb.customJpdl.service.impl.template;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.api.task.Task;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;
import cn.com.qytx.platform.utils.spring.SpringUtil;


/**
 * 功能：会签任务实现类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:24:29 
 * 修改日期：2013-3-22 上午11:24:29 
 * 修改列表：
 */
public class MultiAssignTask implements AssignmentHandler {

	ProcessEngine processEngine=(ProcessEngine) SpringUtil.getBean("processEngine");
	TaskService taskService=processEngine.getTaskService();
	/* (non-Javadoc)
	 * @see org.jbpm.api.task.AssignmentHandler#assign(org.jbpm.api.task.Assignable, org.jbpm.api.model.OpenExecution)
	 */
	@Override
	@Transactional
	public void assign(Assignable arg0, OpenExecution execution) throws Exception {
		String pid=execution.getProcessInstance().getId();
		String activeName = execution.getActivity().getName();
		Task task=taskService.createTaskQuery().processInstanceId(pid).activityName(execution.getName()).uniqueResult();
		String users = (String) execution.getVariable(JpdlInterface.VAR_CANDIDATE_USERS);
		SubTaskManager manager = (SubTaskManager) SpringUtil.getBean("subTaskManager");				
		if(users!=null){
			String userIds[] = users.split(",");
			if(userIds!=null && userIds.length>0){
				for(int i=0;i<userIds.length; i++){
					if(userIds[i]!=null && !userIds[i].equals("")){
						manager.createSubTask(task, userIds[i]);
						RecordApproveUserUtil.record(pid, userIds[i], activeName);
					}
				}
			}
		}
		
		
	}
}
