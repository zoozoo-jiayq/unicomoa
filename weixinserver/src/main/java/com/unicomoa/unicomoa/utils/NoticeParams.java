package com.unicomoa.unicomoa.utils;


public class  NoticeParams{
	//通知类型，100用户分类通知；200任务结束通知
	private int sendType;
	//任务ID
	private String taskId;
	//主叫号码
	private String caller;
	//被叫号码
	private Integer taskUserId;
	
	public NoticeParams(int sendType, String taskId) {
		super();
		this.sendType = sendType;
		this.taskId = taskId;
	}
	public NoticeParams(int sendType, String taskId, String caller, Integer taskUserId) {
		super();
		this.sendType = sendType;
		this.taskId = taskId;
		this.caller = caller;
		this.taskUserId = taskUserId;
	}
	public int getSendType() {
		return sendType;
	}
	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getCaller() {
		return caller;
	}
	public void setCaller(String caller) {
		this.caller = caller;
	}
	public Integer getTaskUserId() {
		return taskUserId;
	}
	public void setTaskUserId(Integer taskUserId) {
		this.taskUserId = taskUserId;
	}
	
}
