package cn.com.qytx.cbb.publicDom.service.impl;

import java.util.List;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.customJpdl.service.impl.template.SubTaskManager;
import cn.com.qytx.cbb.publicDom.service.PublicDocumentConstant;
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
	SubTaskManager subTaskManager = (SubTaskManager) SpringUtil.getBean("subTaskManager");
	/* (non-Javadoc)
	 * @see org.jbpm.api.task.AssignmentHandler#assign(org.jbpm.api.task.Assignable, org.jbpm.api.model.OpenExecution)
	 */
	@Override
	@Transactional
	public void assign(Assignable arg0, OpenExecution execution) throws Exception {
		String pid=execution.getProcessInstance().getId();
		List<Task> tasks=taskService.createTaskQuery().processInstanceId(pid).activityName(execution.getName()).list();
		Task parentTask = null;
		for(Task temp:tasks){
			TaskImpl impl = (TaskImpl)temp;
			if(impl.getSuperTask()==null){
				parentTask = temp;
				break;
			}
		}
		String multisign = (String) execution.getVariable(PublicDocumentConstant.ASSIGNER);
		String[] participants = multisign.split(",");
		for(String participant:participants){  
			Task subTask = subTaskManager.createSubTask(parentTask, participant);
		}  
	}
	
}

