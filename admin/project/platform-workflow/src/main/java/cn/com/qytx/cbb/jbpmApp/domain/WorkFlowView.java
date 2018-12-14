package cn.com.qytx.cbb.jbpmApp.domain;

import java.sql.Timestamp;

/**
 * 功能：待我审批的任务视图
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午5:30:29 
 * 修改日期：下午5:30:29 
 * 修改列表：
 */
public class WorkFlowView {

	private String taskId;
	private String instanceId;
	private Timestamp taskStart;
	private String jobName;
	private String processType;
	private String creater;
	private Timestamp processStart;
	private String beforeTaskName;
	private String beforeUser;
	private String currentTaskName;
	private String processId;
	private String currentState;
	private String currentUser;
	private Timestamp suspendTime;
	private Timestamp taskEnd;
	private String categoryName;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Timestamp getTaskEnd() {
		return taskEnd;
	}
	public void setTaskEnd(Timestamp taskEnd) {
		this.taskEnd = taskEnd;
	}
	public Timestamp getSuspendTime() {
		return suspendTime;
	}
	public void setSuspendTime(Timestamp suspendTime) {
		this.suspendTime = suspendTime;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public Timestamp getTaskStart() {
		return taskStart;
	}
	public void setTaskStart(Timestamp taskStart) {
		this.taskStart = taskStart;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Timestamp getProcessStart() {
		return processStart;
	}
	public void setProcessStart(Timestamp processStart) {
		this.processStart = processStart;
	}
	public String getBeforeTaskName() {
		return beforeTaskName;
	}
	public void setBeforeTaskName(String beforeTaskName) {
		this.beforeTaskName = beforeTaskName;
	}
	public String getBeforeUser() {
		return beforeUser;
	}
	public void setBeforeUser(String beforeUser) {
		this.beforeUser = beforeUser;
	}
	public String getCurrentTaskName() {
		return currentTaskName;
	}
	public void setCurrentTaskName(String currentTaskName) {
		this.currentTaskName = currentTaskName;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
}
