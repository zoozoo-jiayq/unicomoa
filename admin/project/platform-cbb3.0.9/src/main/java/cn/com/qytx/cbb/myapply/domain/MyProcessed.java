package cn.com.qytx.cbb.myapply.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

@Entity
@Table(name="tb_cbb_my_processed")
public class MyProcessed extends BaseDomain{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="my_started_id")
	private Integer myStartedId;
	
	/**
	 * 分类(流程类型/栏目)
	 */
	@Column(name="category_name",length=50)
    private String categoryName;
	
	/**
	 * 标题
	 */
	@Column(name="title",length=50)
    private String title;
	
	/**
	 * 业务ID
	 */
	@Column(name="instance_id",length=50)
    private String instanceId;
	
	/**
	 * 处理人ID
	 */
	@Column(name="processer_id")
    private Integer processerId;
	
	/**
	 * 处理人姓名
	 */
	@Column(name="processer_name",length=50)
    private String processerName;
	
	/**
	 * 任务处理时间
	 */
	@Column(name="end_time")
    private Timestamp endTime;
	
	/**
	 * 所属模块
	 */
	@Column(name="module_code",length=50)
    private String moduleCode;
    
	/**
	 * 发起人
	 */
	@Column(name="creater_name",length=50)
    private String createrName;
	
	/**
	 * 审批意见
	 */
	@Column(name="advice",length=200)
    private String advice;
	
	@Column(name="approveResult")
	private String approveResult;
	
	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public Integer getMyStartedId() {
		return myStartedId;
	}

	public void setMyStartedId(Integer myStartedId) {
		this.myStartedId = myStartedId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Integer getProcesserId() {
		return processerId;
	}

	public void setProcesserId(Integer processerId) {
		this.processerId = processerId;
	}

	public String getProcesserName() {
		return processerName;
	}

	public void setProcesserName(String processerName) {
		this.processerName = processerName;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
}
