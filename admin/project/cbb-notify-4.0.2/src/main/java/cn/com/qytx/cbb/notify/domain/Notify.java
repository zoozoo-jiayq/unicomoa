package cn.com.qytx.cbb.notify.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.platform.base.domain.CompanyId;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;

@Entity
@Table(name="tb_cbb_notify")
public class Notify implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; //公告Id
	@JoinColumn(name="create_user_id",updatable=false)
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo createUser; //创建人
	
	@Column(name="create_date",updatable=false)
	private Date createDate = new Date(); //创建时间
	
	@JoinColumn(name="last_update_user_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo updateUser; //最后一次修改人
	
	@Column(name="update_date")
	private Date updateDate = new Date();//最后一次更新时间
	
	@DeleteState
	@Column(name="is_delete",nullable=false)
	private Integer isDelete = 0; //删除状态
	
	@Column(name="subject",length=500)
	private String subject; //标题
	
	@Column(name="content",columnDefinition="text")
	private String content; //内容
	
	@Column(name="summary",length=60)
	private String summary; //内容简介
	
	@Column(name="begin_date")
	private Timestamp beginDate; //生效日期
	
	@Column(name="end_date")
	private Timestamp endDate; //终止日期
	
	@Column(name="notify_type")
	private Integer notifyType; //公告类型id
	
	@Column(name="is_top")
	private Integer isTop;  //是否置顶
	
	@Column(name="status")
	private Integer status; //状态  0草稿，1:审批中2:通过（已发布）  3:未通过  4:终止  
	
	@Column(name="auditer")
	private Integer auditer; //审批人id
	
	@Column(name="reason",length=1000)
	private String reason; //审批原因
	
	@Column(name="approve_date")
	private Timestamp approveDate; //审批时间
	
	@Column(name="attment",length=500)
	private String attment; //附件
	
	@Column(name="publish_user_ids",columnDefinition="text")
	private String publishScopeUserIds; //发布范围ids
	
	@Column(name="publish_user_names",columnDefinition="text")
	private String publishScopeUserNames; //发布人员名称
	
	@Column(name="view_count")
	private Integer viewCount;//浏览次数
	
	@Column(name="columnid")
	private int columnId;  //栏目类型
	
	@Column(name="images",length=1000)
	private String images;//手机图片
	
	@Column(name="sendtype")
	private String sendType;
	
	@Column(name="publish_group_id",updatable=false)
	private Integer publishGroupId;
	
	@Column(name="is_fork_group")
	private Integer isForkGroup;
	
	@Transient
	private List<Attachment> imagesList = new ArrayList<Attachment>();
	@Transient
	private List<Attachment> attachmentList = new ArrayList<Attachment>();
	
	@OneToMany(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY,mappedBy="notify")
	private List<NotifyView> viewList= new ArrayList<NotifyView>();
	
	@OneToMany(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY,mappedBy="notify")
	private List<NotifyComment> commentList = new ArrayList<NotifyComment>();
	@Transient
	private String differenceTime;
	@Transient
	private Integer readCount;//已读人数
	@Transient
	private Integer unreadCount;//未读人数
	@CompanyId
	@Column(name="company_id")
	private Integer companyId;
	public Integer getId() {
		return id;
	}

	public Integer getIsForkGroup() {
		return isForkGroup;
	}

	public void setIsForkGroup(Integer isForkGroup) {
		this.isForkGroup = isForkGroup;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserInfo getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public UserInfo getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(UserInfo updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Timestamp getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAuditer() {
		return auditer;
	}

	public void setAuditer(Integer auditer) {
		this.auditer = auditer;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Timestamp getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Timestamp approveDate) {
		this.approveDate = approveDate;
	}

	public String getAttment() {
		return attment;
	}

	public void setAttment(String attment) {
		this.attment = attment;
	}

	public String getPublishScopeUserIds() {
		return publishScopeUserIds;
	}

	public void setPublishScopeUserIds(String publishScopeUserIds) {
		this.publishScopeUserIds = publishScopeUserIds;
	}

	public String getPublishScopeUserNames() {
		return publishScopeUserNames;
	}

	public void setPublishScopeUserNames(String publishScopeUserNames) {
		this.publishScopeUserNames = publishScopeUserNames;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	@Transient
	public List<Attachment> getImagesList() {
		return imagesList;
	}

	public void setImagesList(List<Attachment> imagesList) {
		this.imagesList = imagesList;
	}
	@Transient
	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<NotifyView> getViewList() {
		return viewList;
	}

	public void setViewList(List<NotifyView> viewList) {
		this.viewList = viewList;
	}
	
	@OrderBy("createDate desc")
	public List<NotifyComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<NotifyComment> commentList) {
		this.commentList = commentList;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public Integer getPublishGroupId() {
		return publishGroupId;
	}

	public void setPublishGroupId(Integer publishGroupId) {
		this.publishGroupId = publishGroupId;
	}

	public String getDifferenceTime() {
		return differenceTime;
	}

	public void setDifferenceTime(String differenceTime) {
		this.differenceTime = differenceTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
	}
	
}
