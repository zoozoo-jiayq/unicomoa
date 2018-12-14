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
 * 功能:成员关系
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月19日
 * 修改日期: 2016年12月19日
 * 修改列表: 
 */
@Entity
@Table(name="tb_ohr_relation")
public class RecordRelation extends BaseDomain{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 8725255581157345199L;
	
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
     * 成员姓名
     */
    @Column(name="member_name")
    private String memberName;
    /**
     * 与本人关系
     */
    @Column(name="relation")
    private Integer relation;
    /**
     * 出生日期
     */
    @Column(name="birth_date")
    private Timestamp birthDate;
    /**
     * 政治面貌
     */
    @Column(name="political_status")
    private Integer politicalStatus;
    /**
     * 职业
     */
    @Column(name="occupation")
    private String occupation;
    /**
     * 担任职务
     */
    @Column(name="duties")
    private String duties;
    /**
     * 个人电话
     */
    @Column(name="personal_phone")
    private String personalPhone;
    /**
     * 家庭电话
     */
    @Column(name="home_phone")
    private String homePhone;
    /**
     * 联系电话（工作）
     */
    @Column(name="work_telephone")
    private String workTelephone;
    /**
     * 工作单位
     */
    @Column(name="work_unit")
    private String workUnit;
    /**
     * 单位地址
     */
    @Column(name="unit_address")
    private String unitAddress;
    /**
     * 家庭地址
     */
    @Column(name="home_address")
    private String homeAddress;
    /**
     * 备注
     */
    @Column(name="remark")
    private String remark;
    /**
     * 附件
     */
    @Column(name="attment")
    private String attment;
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
	@Transient
	private String relationStr;
	
	@Transient
	private String politicalStatusStr;
	
	@DeleteState
	@Column(name="is_delete")
	private Integer isDelete;

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
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the relation
	 */
	public Integer getRelation() {
		return relation;
	}

	/**
	 * @param relation the relation to set
	 */
	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	/**
	 * @return the birthDate
	 */
	public Timestamp getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Timestamp birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the politicalStatus
	 */
	public Integer getPoliticalStatus() {
		return politicalStatus;
	}

	/**
	 * @param politicalStatus the politicalStatus to set
	 */
	public void setPoliticalStatus(Integer politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * @return the duties
	 */
	public String getDuties() {
		return duties;
	}

	/**
	 * @param duties the duties to set
	 */
	public void setDuties(String duties) {
		this.duties = duties;
	}

	

	/**
	 * @return the personalPhone
	 */
	public String getPersonalPhone() {
		return personalPhone;
	}

	/**
	 * @param personalPhone the personalPhone to set
	 */
	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	/**
	 * @return the homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @param homePhone the homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * @return the workTelephone
	 */
	public String getWorkTelephone() {
		return workTelephone;
	}

	/**
	 * @param workTelephone the workTelephone to set
	 */
	public void setWorkTelephone(String workTelephone) {
		this.workTelephone = workTelephone;
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
	 * @return the unitAddress
	 */
	public String getUnitAddress() {
		return unitAddress;
	}

	/**
	 * @param unitAddress the unitAddress to set
	 */
	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	/**
	 * @return the homeAddress
	 */
	public String getHomeAddress() {
		return homeAddress;
	}

	/**
	 * @param homeAddress the homeAddress to set
	 */
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
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

	/**
	 * @return the relationStr
	 */
	public String getRelationStr() {
		return relationStr;
	}

	/**
	 * @param relationStr the relationStr to set
	 */
	public void setRelationStr(String relationStr) {
		this.relationStr = relationStr;
	}

	/**
	 * @return the politicalStatusStr
	 */
	public String getPoliticalStatusStr() {
		return politicalStatusStr;
	}

	/**
	 * @param politicalStatusStr the politicalStatusStr to set
	 */
	public void setPoliticalStatusStr(String politicalStatusStr) {
		this.politicalStatusStr = politicalStatusStr;
	}
	
	

}
