package cn.com.qytx.cbb.customJpdl.service.impl.template;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;

import cn.com.qytx.cbb.customJpdl.service.JpdlInterface;

import com.google.gson.Gson;

/**
 * 功能：回退监听事件，监听start事件,监听发起任务节点，记录该任务的处理人和该任务的名称
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:27:56 
 * 修改日期：上午10:27:56 
 * 修改列表：
 */
public class RollbackListenerForFirstTask implements EventListener {

	@Override
	public void notify(EventListenerExecution execution) throws Exception {

		//记录发起任务节点的信息
		String creater  =  (String) execution.getVariable(JpdlInterface.VAR_CREATER);
		String taskName = execution.getActivity().getName();
		List<RollbackTaskInfo> list = new ArrayList<RollbackTaskInfo>();
		list.add(new RollbackTaskInfo(creater, taskName,1));
		Gson gson = new Gson();
		String result = gson.toJson(list);
		execution.setVariable(JpdlInterface.ROLL_BACK_INFO, result);
	}

}
