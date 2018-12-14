package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

public class DataElementImplMyApproved {
	
	private String flowName;
	private String startTime;
	private String starter;
	private String approveTime;
	private String currentTask;
	private String currentTaskAssigner;
	private String state;
	private String instanceId;
	private String curentTaskName;
	private Integer attachFileSize;
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
	
	public String getCurentTaskName() {
		return curentTaskName;
	}
	public void setCurentTaskName(String curentTaskName) {
		this.curentTaskName = curentTaskName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStarter() {
		return starter;
	}
	public void setStarter(String starter) {
		this.starter = starter;
	}
	public String getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	public String getCurrentTask() {
		return currentTask;
	}
	public void setCurrentTask(String currentTask) {
		this.currentTask = currentTask;
	}
	public String getCurrentTaskAssigner() {
		return currentTaskAssigner;
	}
	public void setCurrentTaskAssigner(String currentTaskAssigner) {
		this.currentTaskAssigner = currentTaskAssigner;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public Integer getAttachFileSize() {
		return attachFileSize;
	}
	public void setAttachFileSize(Integer attachFileSize) {
		this.attachFileSize = attachFileSize;
	}
	
	
}
