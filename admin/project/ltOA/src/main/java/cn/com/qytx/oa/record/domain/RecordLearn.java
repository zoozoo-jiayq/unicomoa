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
 * 功能：用户档案-学习
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2016年12月19日
 * 修改日期：2016年12月19日	
 */
@Entity
@Table(name="tb_ohr_learn")
public class RecordLearn extends BaseDomain implements java.io.Serializable {
	private static final long serialVersionUID = 6003502541882035434L;

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id", unique=true,nullable=false)
	private Integer id;
	/**
	 * 专业
	 */
	private String major;
	
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
	 * 所获学历
	 */
	private Integer education;
	
	/**
	 * 学位
	 */
	private Integer degree;
	
	/**
     * 用户 对象
     */
    @JoinColumn(name="user_id",nullable=false)
   	@ManyToOne(cascade=CascadeType.REFRESH)
	private UserInfo userInfo;
    
    /**
	 * 班干部
	 */
    @Column(name="class_cadre")
    private String classCadre;
    
    /**
     * 证明人
     */
    @Column(name="reterence")
    private String reterence;
    
    /**
	 * 所在学校
	 */
    private String school;
    
    /**
	 * 学校地址
	 */
    @Column(name="school_address")
    private String schoolAddress;
    
    /**
	 * 获奖情况
	 */
    private String award;
    
    /**
	 * 所获证书
	 */
    private String certificates;
    
    /**
	 * 附件
	 */
    private String attment;
    
    /**
	 * 备注
	 */
    private String remark;
    
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
     * 创建人
     */
    @Column(name="create_user_id")
    private Integer createUserId;
    /**
     * 修改人
     */
    @Column(name="update_user_id")
    private Integer updateUserId;
    
    @DeleteState
    @Column(name="is_delete")
    private Integer isDelete;

	@Transient
	private List<Attachment> attachmentList = new ArrayList<Attachment>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getClassCadre() {
		return classCadre;
	}

	public void setClassCadre(String classCadre) {
		this.classCadre = classCadre;
	}

	public String getReterence() {
		return reterence;
	}

	public void setReterence(String reterence) {
		this.reterence = reterence;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getCertificates() {
		return certificates;
	}

	public void setCertificates(String certificates) {
		this.certificates = certificates;
	}

	public String getAttment() {
		return attment;
	}

	public void setAttment(String attment) {
		this.attment = attment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
    
    
	
	
}
