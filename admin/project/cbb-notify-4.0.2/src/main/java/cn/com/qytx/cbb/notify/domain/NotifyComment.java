package cn.com.qytx.cbb.notify.domain;
import java.io.Serializable;
import java.util.Date;

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

import cn.com.qytx.platform.base.domain.CompanyId;
import cn.com.qytx.platform.org.domain.UserInfo;

@Entity
@Table(name="tb_cbb_notify_comment")
public class NotifyComment implements Serializable{

	@Column(name="id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; //主键
	
	@Column(name="create_date",updatable=false,nullable=false)
	private Date createDate = new Date(); //创建时间

	@JoinColumn(name="create_user_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo createUserInfo;  //评论人

	@Column(name="comment",length=1000)
	private String comment;  //评论

	@JoinColumn(name="notify_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private Notify notify;  //通知公告
	
	@CompanyId
	@Column(name="company_id")
	private Integer companyId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public UserInfo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserInfo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Notify getNotify() {
		return notify;
	}

	public void setNotify(Notify notify) {
		this.notify = notify;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	
	
}
