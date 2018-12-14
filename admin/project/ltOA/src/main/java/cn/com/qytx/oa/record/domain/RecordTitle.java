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
 * 功能:职称
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月19日
 * 修改日期: 2016年12月19日
 * 修改列表: 
 */
@Entity
@Table(name="tb_ohr_title")
public class RecordTitle extends BaseDomain{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 452808139648047991L;
   
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
	 * 批准人ID
	 */
	@JoinColumn(name="approved_person")
   	@ManyToOne(cascade=CascadeType.REFRESH)
	private UserInfo approveUser;
	
	/**
	 * 获得的职称
	 */
	@Column(name="title")
	private String title;
	/**
	 * 获得方式
	 */
	@Column(name="access_type")
	private Integer accessType;
	/**
	 * 申请时间
	 */
	@Column(name="apply_date")
	private Timestamp applyDate;
	
	/**
	 * 获得时间
	 */
	@Column(name="give_date")
	private Timestamp giveDate;
	/**
	 * 下次申报的职称
	 */
	@Column(name="next_title")
	private String nextTitle;
	/**
	 * 下次申报的时间
	 */
	@Column(name="next_apply_date")
	private Timestamp nextApplyDate;
	/**
	 * 聘用职务
	 */
	@Column(name="hiring_position")
	private String hiringPosition;
	/**
	 * 聘用单位
	 */
	@Column(name="hiring_units")
	private String hiringUnits;
	/**
	 * 聘用开始的时间
	 */
	@Column(name="hiring_begin_date")
	private Timestamp hiringBeginDate;
	/**
	 * 聘用结束的时间
	 */
	@Column(name="hiring_end_date")
	private Timestamp hiringEndDate;
	/**
	 * 评定详情
	 */
	@Column(name="remark")
	private String remark;
	
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
	@Transient
	private String accessTypeStr;

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the nextTitle
	 */
	public String getNextTitle() {
		return nextTitle;
	}

	/**
	 * @param nextTitle the nextTitle to set
	 */
	public void setNextTitle(String nextTitle) {
		this.nextTitle = nextTitle;
	}


	/**
	 * @return the hiringPosition
	 */
	public String getHiringPosition() {
		return hiringPosition;
	}

	/**
	 * @param hiringPosition the hiringPosition to set
	 */
	public void setHiringPosition(String hiringPosition) {
		this.hiringPosition = hiringPosition;
	}

	/**
	 * @return the hiringUnits
	 */
	public String getHiringUnits() {
		return hiringUnits;
	}

	/**
	 * @param hiringUnits the hiringUnits to set
	 */
	public void setHiringUnits(String hiringUnits) {
		this.hiringUnits = hiringUnits;
	}

	

	public Timestamp getHiringBeginDate() {
		return hiringBeginDate;
	}

	public void setHiringBeginDate(Timestamp hiringBeginDate) {
		this.hiringBeginDate = hiringBeginDate;
	}

	public Timestamp getHiringEndDate() {
		return hiringEndDate;
	}

	public void setHiringEndDate(Timestamp hiringEndDate) {
		this.hiringEndDate = hiringEndDate;
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

	public Integer getAccessType() {
		return accessType;
	}

	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}

	public Timestamp getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public Timestamp getGiveDate() {
		return giveDate;
	}

	public void setGiveDate(Timestamp giveDate) {
		this.giveDate = giveDate;
	}

	public Timestamp getNextApplyDate() {
		return nextApplyDate;
	}

	public void setNextApplyDate(Timestamp nextApplyDate) {
		this.nextApplyDate = nextApplyDate;
	}

	public String getAttment() {
		return attment;
	}

	public void setAttment(String attment) {
		this.attment = attment;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public String getAccessTypeStr() {
		return accessTypeStr;
	}

	public void setAccessTypeStr(String accessTypeStr) {
		this.accessTypeStr = accessTypeStr;
	}

	public UserInfo getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(UserInfo approveUser) {
		this.approveUser = approveUser;
	}
	
	
	
}
