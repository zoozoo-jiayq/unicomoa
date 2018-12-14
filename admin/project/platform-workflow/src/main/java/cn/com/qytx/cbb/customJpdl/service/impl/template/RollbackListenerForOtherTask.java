package cn.com.qytx.cbb.customJpdl.service.impl.template;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;

/**
 * 功能：记录其它任务节点的处理人和任务节点名称
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:42:33 
 * 修改日期：上午10:42:33 
 * 修改列表：
 */
public class RollbackListenerForOtherTask implements EventListener {

	@Override
	public void notify(EventListenerExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String user     = (String) execution.getVariable(JpdlInterface.VAR_CANDIDATE_USERS);
		String taskName = execution.getActivity().getName();
		String instanceId = execution.getProcessInstance().getId();
		RecordApproveUserUtil.record(instanceId, user, taskName);
	}

}
