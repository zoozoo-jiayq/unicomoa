package cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel;

//待审批/已挂起
public class DataElementImplWaitApprove {


	private String flowName;
	private String starter;
	private String time;
	private String lastTaskName;
	private String lastTaskAssigner;
	private String instanceId;
	private Integer attachFileSize;
	private boolean canDelete;
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getStarter() {
		return starter;
	}
	public void setStarter(String starter) {
		this.starter = starter;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLastTaskName() {
		return lastTaskName;
	}
	public void setLastTaskName(String lastTaskName) {
		this.lastTaskName = lastTaskName;
	}
	public String getLastTaskAssigner() {
		return lastTaskAssigner;
	}
	public void setLastTaskAssigner(String lastTaskAssigner) {
		this.lastTaskAssigner = lastTaskAssigner;
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
	public boolean getCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	
}
