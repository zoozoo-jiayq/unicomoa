package cn.com.qytx.cbb.baseworkflow.service;

/**
 * 待我审批的
 * @author jiayongqiang
 *
 */
public class BMyWaitApprove {

	//头像URL
	private String photoUrl;
	
	//标题
	private String title;
	
	//状态
	private String state = "待审批";
	
	//任务到达时间
	private String arriveTime;
	
	//流程实例ID
	private String instanceId;
	
	//当前审批人ID
	private String currentUserId;
	
	//创建人姓名
	private String createrName;
	
	private String moduleCode;

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	
	
}
