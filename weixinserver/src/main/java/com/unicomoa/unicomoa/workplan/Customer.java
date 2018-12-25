package com.unicomoa.unicomoa.workplan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.unicomoa.unicomoa.base.BaseModel;

@Entity
@Table(name="tb_oa_customer")
public class Customer extends BaseModel {

	@Column(name="sex")
	private int sex;
	
	@Column(name="username")
	private String username;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="work_plan_id")
	private int workPlanId;

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getWorkPlanId() {
		return workPlanId;
	}

	public void setWorkPlanId(int workPlanId) {
		this.workPlanId = workPlanId;
	}
	
}
