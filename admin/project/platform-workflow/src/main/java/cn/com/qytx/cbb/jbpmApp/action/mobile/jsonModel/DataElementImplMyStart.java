package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

public class DataElementImplMyStart {

	private String flowName;
	private String time;
	private String curentTaskName;
	private String currentTaskAssigner; 
	private String state;
	private String instanceId;
	private Integer attachFileSize;
	
	//false不能删除；true能删除
	private boolean canDelete;
	private String groupName;
	private String roleName;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCurrentTaskAssigner() {
		return currentTaskAssigner;
	}
	public void setCurrentTaskAssigner(String currentTaskAssigner) {
		this.currentTaskAssigner = currentTaskAssigner;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCurentTaskName() {
		return curentTaskName;
	}
	public void setCurentTaskName(String curentTaskName) {
		this.curentTaskName = curentTaskName;
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
	public boolean isCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	
	
}
