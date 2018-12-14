package cn.com.qytx.cbb.comment.domain;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.org.domain.UserInfo;

@Entity	
@Table(name="tb_cbb_comment")
public class Comment extends BaseDomain implements Serializable{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 创建人
	 */
	@JoinColumn(name="create_user_id",updatable=false)
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo createUser;
	
	@Column(name="create_date",updatable=false)
	private Timestamp createDate = new Timestamp(new Date().getTime());
	/**
	 * 评论内容
	 */
	@Column(name="content")
	private String content;
	
	/**
	 * 审批人
	 */
	@JoinColumn(name="approve_user_id",updatable=false)
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo approveUser;
	
	/**
	 * 审批日期
	 */
	@Column(name="approve_date")
	private Timestamp approveDate;
	
	/**
	 * 业务ID
	 */
	@Column(name="instance_id")
	private String instanceId;
	
	/**
	 * 模块分类
	 */
	@Column(name="type")
	private String type;
	
	/**
	 * 状态 0未审核  ，1 审核通过  2 审核不通过
	 */
	@Column(name="statue")
	private Integer statue; 
	
	/**
	 * 附件
	 */
	@Column(name="attach_ids")
	private String attachIds;
	/**
	 * 匿名  0实名  1 匿名
	 */
	@Column(name="is_anonymity")
	private Integer isAnonymity = 0 ;
	/**
	 * 父节点
	 */
	@JoinColumn(name="parent_id",updatable=false)
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private Comment parent;
	
	/**
	 * 子评论
	 */
	@OneToMany(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY,mappedBy="parent")
	private List<Comment> children = new ArrayList<Comment>();
	
	/**
	 * 附件
	 */
	@Transient
	private List<Attachment> attachList = new ArrayList<Attachment>();
	
	public Integer getId() {
		return id;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public UserInfo getApproveUser() {
		return approveUser;
	}
	public void setApproveUser(UserInfo approveUser) {
		this.approveUser = approveUser;
	}
	public Timestamp getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Timestamp approveDate) {
		this.approveDate = approveDate;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Comment getParent() {
		return parent;
	}
	public void setParent(Comment parent) {
		this.parent = parent;
	}
	public List<Comment> getChildren() {
		return children;
	}
	public void setChildren(List<Comment> children) {
		this.children = children;
	}
	public Integer getStatue() {
		return statue;
	}
	public void setStatue(Integer statue) {
		this.statue = statue;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getAttachIds() {
		return attachIds;
	}
	public void setAttachIds(String attachIds) {
		this.attachIds = attachIds;
	}
	public Integer getIsAnonymity() {
		return isAnonymity;
	}
	public void setIsAnonymity(Integer isAnonymity) {
		this.isAnonymity = isAnonymity;
	}
	@Transient
	public List<Attachment> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<Attachment> attachList) {
		this.attachList = attachList;
	}
}
