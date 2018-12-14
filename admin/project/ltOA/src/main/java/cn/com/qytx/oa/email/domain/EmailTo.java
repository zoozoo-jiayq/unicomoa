package cn.com.qytx.oa.email.domain;

import java.sql.Timestamp;

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

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:收件箱信息Domain类
 * 版本:1.0
 * 创建日期: 2014-06-30
 * 修改日期: 2014-06-30
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Entity
@Table(name="tb_op_email_to")
public class EmailTo extends BaseDomain{
	
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	/**
	 * 收件人id
	 */
	@Column(name="to_id",nullable=false)
	private Integer toId;
	
	/**
	 * 收件人类型：收件人、抄送人、密送人
	 */
	@Column(name="to_type",nullable=false)
	private Integer toType;
	
	/**
	 * 收件人读取状态：0未读，1已读
	 */
	@Column(name="read_status",nullable=false)
	private Integer readStatus = 0;
	
	/**
	 * 阅读时间
	 */
	@Column(name="read_time")
	private Timestamp readTime;
	
	/**
	 * 所属邮件箱id
	 */
	@Transient
	private Integer emailBoxId;
	
    /**
     * 关联的邮件体
     */
	@JoinColumn(name="email_body_id",nullable=false)
	@ManyToOne(cascade=CascadeType.REFRESH)
    private EmailBody emailBody;

    /**
     * 0未接受 1已接收 2 已取消发送（在发起人删除发送邮件而收件人未读的情况下）
     */
	@Column(name="email_to_status",nullable=false)
    private Integer emailToStatus = 1;
    
	 /**
     * 邮件星级:
     * 0：无
     * 1：灰色星
     * 2：绿色星
     * 3：黄色星
     * 4：红色星
     */
	@Column(name="mark_level",nullable=false)
    private Integer markLevel = 0;
    
	@Column(name="create_time",nullable=false,length=23)
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());

	@JoinColumn(name="create_user_id",nullable=false)
	@ManyToOne(cascade=CascadeType.REFRESH)
	private UserInfo createUserInfo;
	
	@Transient
	private Integer createUserId;
	
	@Column(name="last_update_time",nullable=false,length=23)
	private Timestamp lastUpdateTime;
	
	@Column(name="last_update_user_id",nullable=false)
	private Integer lastUpdateUserId;
	
	@Transient
	private UserInfo lastUpdateUserInfo;
	
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete = 0;
	

    /**
     * 所属邮件箱
     */
	@JoinColumn(name="email_box_id")
	@ManyToOne(cascade=CascadeType.REFRESH)
    private EmailBox emailBox;
	
	/**
	 * 曾经所属邮件箱
	 */
	@JoinColumn(name="old_email_box_id")
	@ManyToOne(cascade=CascadeType.REFRESH)
	private EmailBox oldEmailBox;

    /**
     * 关联邮件体id
     */
	@Transient
    private Integer emailBodyId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	public Integer getToType() {
		return toType;
	}

	public void setToType(Integer toType) {
		this.toType = toType;
	}

	public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}

	public Timestamp getReadTime() {
		return readTime;
	}

	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

	public Integer getEmailBoxId() {
		return emailBoxId;
	}

	public void setEmailBoxId(Integer emailBoxId) {
		this.emailBoxId = emailBoxId;
	}

	public EmailBody getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(EmailBody emailBody) {
		this.emailBody = emailBody;
	}

	public Integer getEmailToStatus() {
		return emailToStatus;
	}

	public void setEmailToStatus(Integer emailToStatus) {
		this.emailToStatus = emailToStatus;
	}

	public Integer getMarkLevel() {
		return markLevel;
	}

	public void setMarkLevel(Integer markLevel) {
		this.markLevel = markLevel;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public UserInfo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserInfo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Integer lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}


	public EmailBox getEmailBox() {
		return emailBox;
	}

	public void setEmailBox(EmailBox emailBox) {
		this.emailBox = emailBox;
	}

	public Integer getEmailBodyId() {
		return emailBodyId;
	}

	public void setEmailBodyId(Integer emailBodyId) {
		this.emailBodyId = emailBodyId;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public UserInfo getLastUpdateUserInfo() {
		return lastUpdateUserInfo;
	}

	public void setLastUpdateUserInfo(UserInfo lastUpdateUserInfo) {
		this.lastUpdateUserInfo = lastUpdateUserInfo;
	}

	public EmailBox getOldEmailBox() {
		return oldEmailBox;
	}

	public void setOldEmailBox(EmailBox oldEmailBox) {
		this.oldEmailBox = oldEmailBox;
	}

	
}
