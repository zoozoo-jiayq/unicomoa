package com.unicomoa.unicomoa.workplan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.unicomoa.unicomoa.base.BaseModel;

@Entity
@Table(name="tb_oa_visitor_myd")
public class VisitorMyd extends BaseModel{

	@Column(name="work_plan_id")
	private int workPlanId;
	@Column(name="progress_id")
	private int progressId;
	@Column(name="manyidu")
	private int manyidu;
	@Column(name="remark")
	private String remark;
	
	public int getWorkPlanId() {
		return workPlanId;
	}
	public void setWorkPlanId(int workPlanId) {
		this.workPlanId = workPlanId;
	}
	public int getProgressId() {
		return progressId;
	}
	public void setProgressId(int progressId) {
		this.progressId = progressId;
	}
	public int getManyidu() {
		return manyidu;
	}
	public void setManyidu(int manyidu) {
		this.manyidu = manyidu;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
