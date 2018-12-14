package cn.com.qytx.cbb.baseworkflow.service;

/**
 * 我已审批过的
 * @author jiayongqiang
 *
 */
public class BMyProcessed {
	
	private String photoUrl;//创建人头像
	
	private String title;//标题
	
	private String state;//流程状态 例：审批中、草稿、通过驳回:分支2(超级管理员)、结束...
	
	private String approveTime;//审批日期
	
	private String instanceId;//流程id
	
	private String createrName;//创建人姓名
	
	private String moduleCode;//模块代码
	
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
	public String getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	
}
