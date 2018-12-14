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
 * 功能:培训
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月19日
 * 修改日期: 2016年12月19日
 * 修改列表: 
 */
@Entity
@Table(name="tb_ohr_training")
public class RecordTraining extends BaseDomain {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	
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
     * 培训计划名称
     */
	@Column(name="train_plan_name")
	private String trainPlanName;
   /**
    * 培训机构名称（第一个）
    */
	@Column(name="train_mechanism")
	private String trainMechanism;
	/**
	 * 培训费（第一个）
	 */
	@Column(name="train_money")
	private Float trainMoney;
	
	/**
	 * start date
	 */
	@Column(name="train_date")
	private Timestamp trainDate;
	
	@Column(name="train_end_date")
	private Timestamp trainEndDate;
	/**
	 * 培训机构名称（第二个）
	 */
	@Column(name="train_mechanism_name_two")
	private String trainMechanismNameTwo;
	/**
	 * 培训费（第二个）
	 */
	@Column(name="train_money_two")
	private Float trainMoneyTwo;
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
	 * @return the trainPlanName
	 */
	public String getTrainPlanName() {
		return trainPlanName;
	}
	/**
	 * @param trainPlanName the trainPlanName to set
	 */
	public void setTrainPlanName(String trainPlanName) {
		this.trainPlanName = trainPlanName;
	}
	
	/**
	 * @return the trainMechanismNameTwo
	 */
	public String getTrainMechanismNameTwo() {
		return trainMechanismNameTwo;
	}
	/**
	 * @param trainMechanismNameTwo the trainMechanismNameTwo to set
	 */
	public void setTrainMechanismNameTwo(String trainMechanismNameTwo) {
		this.trainMechanismNameTwo = trainMechanismNameTwo;
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
	public String getTrainMechanism() {
		return trainMechanism;
	}
	public void setTrainMechanism(String trainMechanism) {
		this.trainMechanism = trainMechanism;
	}
	
	public Float getTrainMoney() {
		return trainMoney;
	}
	public void setTrainMoney(Float trainMoney) {
		this.trainMoney = trainMoney;
	}
	public void setTrainMoneyTwo(Float trainMoneyTwo) {
		this.trainMoneyTwo = trainMoneyTwo;
	}
	public Timestamp getTrainDate() {
		return trainDate;
	}
	public void setTrainDate(Timestamp trainDate) {
		this.trainDate = trainDate;
	}
	public Timestamp getTrainEndDate() {
		return trainEndDate;
	}
	public void setTrainEndDate(Timestamp trainEndDate) {
		this.trainEndDate = trainEndDate;
	}
	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
	
	
}
