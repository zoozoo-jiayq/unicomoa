package com.unicomoa.unicomoa.workplan;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;
import com.unicomoa.unicomoa.base.BaseModel;

@Entity
@Table(name="tb_oa_work_plan_progress")
public class WorkPlanProgress extends BaseModel {

	@Column(name = "work_plan_id")
	private int workPlanId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="content")
	private String content;
	
	@Column(name="imgs")
	private String imgs;
	
	@Column(name="address")
	private String address;
	
	@Column(name="complete_state")
	private int completeState;
	
	@Transient
	private int myd;
	
	@Transient
	private String remark;
	
	public int getMyd() {
		return myd;
	}

	public void setMyd(int myd) {
		this.myd = myd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCompleteState() {
		return completeState;
	}

	public void setCompleteState(int completeState) {
		this.completeState = completeState;
	}

	public void setImgUrls(List<String> imgUrls) {
		this.imgUrls = imgUrls;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Transient
	private List<String> imgUrls;

	public List<String> getImgUrls() {
		List<String> strs = new Gson().fromJson(this.imgs, List.class);
		return strs;
	}

	public int getWorkPlanId() {
		return workPlanId;
	}

	public void setWorkPlanId(int workPlanId) {
		this.workPlanId = workPlanId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
