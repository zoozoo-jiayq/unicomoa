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

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:邮件体domain类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Entity
@Table(name="tb_op_email_body")
public class EmailBody extends BaseDomain{

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	/**
	 * 收件人id，使用英文逗号分割
	 */
	@Column(name="to_id")
	private String toId;
	
    /**
     * 收件人姓名，使用英文逗号分割
     */
	@Column(name="to_name")
	private String toName;
	
	/**
	 * 抄送人，使用英文逗号分割
	 */
	@Column(name="copy_to_id")
	private String copyToId;
	
    /**
     * 抄送人姓名，使用英文逗号分割
     */
	@Column(name="copy_to_name")
	private String copyToName;
	
	/**
	 * 密送人，使用英文逗号分割
	 */
	@Column(name="secret_to_id")
	private String secretToId;
	
    /**
     * 密送人姓名，使用英文逗号分割
     */
	@Column(name="secret_to_name")
	private String secretToName;

	/**
	 * 邮件主题
	 */
	@Column(name="subject",length=200)
	private String subject;
	
	/**
	 * 邮件内容
	 */
	@Column(name="content_info",columnDefinition="TEXT")
	private String contentInfo;

	/**
	 * 邮件发送时间
	 */
	@Column(name="send_time",length=23)
	private Timestamp sendTime;
	
	/**
	 * 发送状态，0未发送(草稿) 1发送
	 */
	@Column(name="send_status",nullable=false)
	private Integer sendStatus;
	
	/**
	 * 附件，以JSON形式存储附件信息,url,name,size
	 */
	@Column(name="attachment")
	private String attachment;
	
	/**
	 * 是否发送事务提醒消息 0不发送 1发送
	 */
	@Column(name="sms_remind",nullable=false)
	private Integer smsRemind;
	
	@Column(name="send_type")
	private String sendType;
	
	/**
	 * 请求收条，0：不需要收条，1：需要收条
	 */
	@Column(name="need_receipt",nullable=false)
	private Integer needReceipt;

	/**
	 * 重要级别 0:一般邮件 1：重要邮件 2：非常重要
	 */
	@Column(name="important_level",nullable=false)
	private Integer importantLevel;
	
    /**
     * 附件总大小 单位：字节
     */
	@Column(name="attachment_size",nullable=false)
	private Long attachmentSize;
	
	@Column(name="create_time",nullable=false,length=23)
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());
	
	@JoinColumn(name="create_user_id",nullable=false)
	@ManyToOne(cascade=CascadeType.REFRESH)
	private UserInfo createUserInfo;
	
	@Column(name="last_update_time",nullable=false,length=23)
	private Timestamp lastUpdateTime;
	
	@Column(name="last_update_user_id",nullable=false)
	private Integer lastUpdateUserId;
	
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete = 0;
	

    public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getToId() {
		return toId;
	}



	public void setToId(String toId) {
		this.toId = toId;
	}



	public String getToName() {
		return toName;
	}



	public void setToName(String toName) {
		this.toName = toName;
	}



	public String getCopyToId() {
		return copyToId;
	}



	public void setCopyToId(String copyToId) {
		this.copyToId = copyToId;
	}



	public String getCopyToName() {
		return copyToName;
	}



	public void setCopyToName(String copyToName) {
		this.copyToName = copyToName;
	}



	public String getSecretToId() {
		return secretToId;
	}



	public void setSecretToId(String secretToId) {
		this.secretToId = secretToId;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getContentInfo() {
		return contentInfo;
	}



	public void setContentInfo(String contentInfo) {
		this.contentInfo = contentInfo;
	}



	public Timestamp getSendTime() {
		return sendTime;
	}



	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}



	public Integer getSendStatus() {
		return sendStatus;
	}



	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}



	public String getAttachment() {
		return attachment;
	}



	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}



	public Integer getSmsRemind() {
		return smsRemind;
	}



	public void setSmsRemind(Integer smsRemind) {
		this.smsRemind = smsRemind;
	}



	public Integer getNeedReceipt() {
		return needReceipt;
	}



	public void setNeedReceipt(Integer needReceipt) {
		this.needReceipt = needReceipt;
	}



	public Integer getImportantLevel() {
		return importantLevel;
	}



	public void setImportantLevel(Integer importantLevel) {
		this.importantLevel = importantLevel;
	}



	public Long getAttachmentSize() {
		return attachmentSize;
	}



	public void setAttachmentSize(Long attachmentSize) {
		this.attachmentSize = attachmentSize;
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

	public String getSecretToName() {
		return secretToName;
	}



	public void setSecretToName(String secretToName) {
        this.secretToName = secretToName;
    }



	public String getSendType() {
		return sendType;
	}



	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	
	
}
