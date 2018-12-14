package cn.com.qytx.cbb.jbpmApp.domain;

import cn.com.qytx.platform.utils.datetime.DateTimeUtil;


public class TaskTraceVar implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskName;
	private String taskEndTime;
	public TaskTraceVar(String taskName)
	{
		this.taskName=taskName;
		taskEndTime=DateTimeUtil.getCurrentTime();
	}
	public String getTaskName() {
		return taskName;
	}
	public String getTaskEndTime() {
		return taskEndTime;
	}
}
