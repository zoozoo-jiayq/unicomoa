package cn.com.qytx.cbb.jbpmApp.service.impl.command;

import org.hibernate.Session;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.task.TaskImpl;

/**
 * 功能 更新任务状态  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月8日
 * <br/>修改日期  2016年1月8日
 * <br/>修改列表
 */
public class TaskSetStateCommand implements Command<Boolean>{

	private TaskImpl task;
	private String state;
	public TaskSetStateCommand(TaskImpl task,String state)
	{
		this.task=task;
		this.state=state;
	}
	@Override
	public Boolean execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		if(task!=null)
		{
			Session session = environment.get(Session.class);
			task.setState(state);
			session.update(task);
			session.flush();
			return Boolean.TRUE;
		}
		else
			return Boolean.FALSE;
	}

}
