/**
 * 
 */
package cn.com.qytx.oa.record.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:工作
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月19日
 * 修改日期: 2016年12月19日
 * 修改列表: 
 */
@Entity
@Table(name="tb_ohr_work")
public class RecordWork extends BaseDomain {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 5375374477734622664L;

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	/**
     * 用户 对象
     */
    @JoinColumn(name="user_id",nullable=false)
   	@ManyToOne(cascade=CascadeType.REFRESH)
    private UserInfo userInfo;
    /**
     * 职务
     */
    @Column(name="position")
    private String position;
    
    /**
     * 所在部门
     */
    @Column(name="department")
    private String department;
    
    /**
     * 证明人
     */
    @Column(name="reterence")
    private String reterence;
    
    /**
     * 开始时间
     */
    @Column(name="start_date")
    private Timestamp startDate;
    /**
     * 结束时间
     */
    @Column(name="end_date")
    private Timestamp endDate;
    /**
     * 行业类别
     */
    @Column(name="industry_category")
    private String industryCategory;
    /**
     * 工作单位
     */
    @Column(name="work_unit")
    private String workUnit;
     /**
      * 工作内容
      */
    @Column(name="job_content")
    private String jobContent;
    /**
     * 离职原因
     */
    @Column(name="leaving_reasons")
    private String leavingReasons;
    /**
     * 附件
     */
    @Column(name="attment")
    private String attment;
    /**
     * 备注
     */
    @Column(name="remark")
    private String remark;
    /**
     * 主要业绩
     */
    @Column(name="achievement")
    private String achievement;
  
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Timestamp createTime;
	/**
	 * 修改时间
	 */
	@Column(name="update_time")
	private Timestamp updateTime;
	/**
	 * 修改人Id
	 */
	@Column(name="update_user_id")
	private Integer updateUserId;
	
	/**
	 * 创建者Id
	 */
	@Column(name="create_user_id")
	private Integer createUserId;
	@DeleteState
	@Column(name="is_delete")
	private Integer isDelete;
  
	@Transient
	private Integer userId;
	
	@Transient
	private List<Attachment> attachmentList = new ArrayList<Attachment>();
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the reterence
	 */
	public String getReterence() {
		return reterence;
	}

	/**
	 * @param reterence the reterence to set
	 */
	public void setReterence(String reterence) {
		this.reterence = reterence;
	}

	/**
	 * @return the startDate
	 */
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Timestamp getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the industryCategory
	 */
	public String getIndustryCategory() {
		return industryCategory;
	}

	/**
	 * @param industryCategory the industryCategory to set
	 */
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	/**
	 * @return the workUnit
	 */
	public String getWorkUnit() {
		return workUnit;
	}

	/**
	 * @param workUnit the workUnit to set
	 */
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	/**
	 * @return the jobContent
	 */
	public String getJobContent() {
		return jobContent;
	}

	/**
	 * @param jobContent the jobContent to set
	 */
	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}

	/**
	 * @return the leavingReasons
	 */
	public String getLeavingReasons() {
		return leavingReasons;
	}

	/**
	 * @param leavingReasons the leavingReasons to set
	 */
	public void setLeavingReasons(String leavingReasons) {
		this.leavingReasons = leavingReasons;
	}

	/**
	 * @return the attment
	 */
	public String getAttment() {
		return attment;
	}

	/**
	 * @param attment the attment to set
	 */
	public void setAttment(String attment) {
		this.attment = attment;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the achievement
	 */
	public String getAchievement() {
		return achievement;
	}

	/**
	 * @param achievement the achievement to set
	 */
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the updateUserId
	 */
	public Integer getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId the updateUserId to set
	 */
	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * @return the createUserId
	 */
	public Integer getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the attachmentList
	 */
	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	/**
	 * @param attachmentList the attachmentList to set
	 */
	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	
}
