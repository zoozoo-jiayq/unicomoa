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
@Table(name="tb_cbb_notify_view")
public class NotifyView implements Serializable{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; //主键
	
	@Column(name="create_time",updatable=false)
	private Date createTime = new Date(); //创建时间
	
	@JoinColumn(name="create_user_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo createUser; //创建人
	
	@JoinColumn(name="notify_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private Notify notify; //通知公告
	
	@CompanyId
	@Column(name="company_id")
	private Integer companyId;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UserInfo getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
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
