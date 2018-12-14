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
 * 功能:调动
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月19日
 * 修改日期: 2016年12月19日
 * 修改列表: 
 */
@Entity
@Table(name="tb_ohr_transfer")
public class RecordTransfer extends BaseDomain {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 2138922711255500986L;

	
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
     * 调动类型  1 晋升  2 平调 3降级 4其他
     */
    @Column(name="type")
    private Integer type;
    /**
     * 调动日期
     */
    @Column(name="transfer_date")
    private Timestamp transferDate;
    /**
     * 调动生效日期
     */
    @Column(name="effective_date")
    private Timestamp effectiveDate;
    /**
     * 调动前职务
     */
    @Column(name="before_position")
    private String beforePosition;
    /**
     * 调动后职务
     */
    @Column(name="post_position")
    private String postPosition;
    /**
     * 调动前部门
     */
    @Column(name="before_group_id")
    private Integer beforeGroupId;
    /**
     * 调动后部门
     */
    @Column(name="post_group_id")
    private Integer postGroupId;
    /**
     * 调动后角色
     */
    @Column(name="post_role_id")
    private Integer postRoleId;
    /**
     * 调动手续
     */
    @Column(name="transfer_procedure")
    private String transferProcedure;
    
    /**
     * 调动原因
     */
    @Column(name="transfer_reason")
    private String transferReason;
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
     * 提醒
     */
    @Column(name="reminded")
    private String reminded;
    
    /**
     * 是否是最后一条记录 0 否 1 是
     */
    @Column(name="is_last")
    private Integer isLast;
    
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
	
	@Transient
	private String beforeGroupName;
	@Transient
	private String postGroupName;
	@Transient
	private String typeStr;
	

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
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	
    

	/**
	 * @return the transferDate
	 */
	public Timestamp getTransferDate() {
		return transferDate;
	}

	/**
	 * @param transferDate the transferDate to set
	 */
	public void setTransferDate(Timestamp transferDate) {
		this.transferDate = transferDate;
	}

	/**
	 * @return the effectiveDate
	 */
	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the beforePosition
	 */
	public String getBeforePosition() {
		return beforePosition;
	}

	/**
	 * @param beforePosition the beforePosition to set
	 */
	public void setBeforePosition(String beforePosition) {
		this.beforePosition = beforePosition;
	}

	/**
	 * @return the postPosition
	 */
	public String getPostPosition() {
		return postPosition;
	}

	/**
	 * @param postPosition the postPosition to set
	 */
	public void setPostPosition(String postPosition) {
		this.postPosition = postPosition;
	}

	/**
	 * @return the beforeGroupId
	 */
	public Integer getBeforeGroupId() {
		return beforeGroupId;
	}

	/**
	 * @param beforeGroupId the beforeGroupId to set
	 */
	public void setBeforeGroupId(Integer beforeGroupId) {
		this.beforeGroupId = beforeGroupId;
	}

	/**
	 * @return the postGroupId
	 */
	public Integer getPostGroupId() {
		return postGroupId;
	}

	/**
	 * @param postGroupId the postGroupId to set
	 */
	public void setPostGroupId(Integer postGroupId) {
		this.postGroupId = postGroupId;
	}

	/**
	 * @return the postRoleId
	 */
	public Integer getPostRoleId() {
		return postRoleId;
	}

	/**
	 * @param postRoleId the postRoleId to set
	 */
	public void setPostRoleId(Integer postRoleId) {
		this.postRoleId = postRoleId;
	}

	/**
	 * @return the transferProcedure
	 */
	public String getTransferProcedure() {
		return transferProcedure;
	}

	/**
	 * @param transferProcedure the transferProcedure to set
	 */
	public void setTransferProcedure(String transferProcedure) {
		this.transferProcedure = transferProcedure;
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
	 * @return the reminded
	 */
	public String getReminded() {
		return reminded;
	}

	/**
	 * @param reminded the reminded to set
	 */
	public void setReminded(String reminded) {
		this.reminded = reminded;
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

	public String getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	public Integer getIsLast() {
		return isLast;
	}

	public void setIsLast(Integer isLast) {
		this.isLast = isLast;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public String getBeforeGroupName() {
		return beforeGroupName;
	}

	public void setBeforeGroupName(String beforeGroupName) {
		this.beforeGroupName = beforeGroupName;
	}

	public String getPostGroupName() {
		return postGroupName;
	}

	public void setPostGroupName(String postGroupName) {
		this.postGroupName = postGroupName;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	
	
}
