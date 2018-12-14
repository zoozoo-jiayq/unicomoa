package cn.com.qytx.cbb.baseworkflow.model;

import java.util.ArrayList;
import java.util.List;

import cn.com.qytx.cbb.jbpmApp.action.mobile.jsonModel.ApproveHistory;

/**
 * 固定流程的数据模型
 * @author jiayongqiang
 *
 */
public class ViewModule {
	//审批历史记录
	private List<ApproveHistory> history = new ArrayList<ApproveHistory>();
	//流程实例ID
	private String instanceId;
	//表单数据
	private String formData;
	//申请人
	private String createrName;
	//申请人头像
	private String createrPhoto;
	
	//流程状态 approving/over
	private String totalState = "approving";
	//当前审批人Id
	private String approverId;
	
	public String getTotalState() {
		return totalState;
	}

	public void setTotalState(String totalState) {
		this.totalState = totalState;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public void addHistory(ApproveHistory history){
		this.history.add(history);
	}
	
	public List<ApproveHistory> getHistory() {
		return history;
	}
	public void setHistory(List<ApproveHistory> history) {
		this.history = history;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getFormData() {
		return formData;
	}
	public void setFormData(String formData) {
		this.formData = formData;
	}
	
	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getCreaterPhoto() {
		return createrPhoto;
	}

	public void setCreaterPhoto(String createrPhoto) {
		this.createrPhoto = createrPhoto;
	}

	//更新状态:审批通过/驳回/撤销
	public void approve(String approveResult){
		int index = 0;
		for(int i=0; i<history.size(); i++){
			if(history.get(i).getState().equals(ApproveHistory.ARRIVED)){
				index = i;
			}
		}
		history.get(index).setApproveResult(approveResult);
	}
	
}

