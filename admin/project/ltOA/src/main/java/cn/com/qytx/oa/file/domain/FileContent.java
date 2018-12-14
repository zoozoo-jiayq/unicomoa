package cn.com.qytx.oa.file.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.CompanyId;
import cn.com.qytx.platform.base.domain.DeleteState;


/**
 * 
 * 功能:文件的类
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Entity
@Table(name="tb_on_file")
public class FileContent extends BaseDomain{
	/** 关联(many-to-one)  */
	@JoinColumn(name="file_sort")
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private FileSort fileSort;
	/**自增id */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="content_id")
	private Integer contentId;
	
	
	/** 排序号*/
	@Column(name="sort_no")
	private String sortNo;
	/**文件名   */
	@Column(name="subject")
	private String subject;
	/**文件内容*/
	@Column(name="content")
	private String content;
	/** 附件描述  */
	@Column(name="attachment_desc")
	private String attachmentDesc;
	/**文件内容序号 */
	@Column(name="content_no")
	private String contentNo;
	/**关键字 */
	@Column(name="keyword")
	private String keyWord;
	/**创建时间 */
	@Column(name="create_time")
	private Date createTime;
	/** 创建人 */
	@Column(name="create_user_id")
	private int createUserId;
	/**附件对象 */
	@Column(name="attachment")
	private String attachment;
	/** 附件名字 */
	@Column(name="attachment_name")
	private String attachmentName;
	/**0：表示未删除,1：表示删除  */
	@DeleteState
	@Column(name="is_delete")
	private Integer  isDelete;
	/**最后修改时间   */
	@Column(name="last_update_time")
    private Date lastUpdateTime;
    /** 最后修改人 */
	@Column(name="last_update_user_id")
    private int lastUpdateUserId;
	/**版本号 */
    @Column(name="version")
    private int version;
    /**创建人/发送人*/
    @Column(name="create_user")
    private String createUser;
    /**
     * 类型 1全部文件 2共享文件
     */
    @Column(name="type")
    private Integer type;
    
    /**
     * 发布范围
     */
    @Column(name="user_ids")
    private String userIds;
    
    /**
     * 发布范围
     */
    @Column(name="user_names")
    private String userNames;
    
	/**
	 * 用于显示的附件list
	 */
    @Transient
	private List<Attachment> attachList;
	
	public List<Attachment> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<Attachment> attachList) {
		this.attachList = attachList;
	}
	
	
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getAttachmentDesc() {
		return attachmentDesc;
	}
	public void setAttachmentDesc(String attachmentDesc) {
		this.attachmentDesc = attachmentDesc;
	}
	
	public String getContentNo() {
		return contentNo;
	}
	public void setContentNo(String contentNo) {
		this.contentNo = contentNo;
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public FileSort getFileSort() {
		return fileSort;
	}
	public void setFileSort(FileSort fileSort) {
		this.fileSort = fileSort;
	}
	
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
	public int getLastUpdateUserId() {
		return lastUpdateUserId;
	}
	public void setLastUpdateUserId(int lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

}
