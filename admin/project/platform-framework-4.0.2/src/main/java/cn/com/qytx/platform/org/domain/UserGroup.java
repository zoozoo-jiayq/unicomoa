package cn.com.qytx.platform.org.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 功能:人员管理范围表
 * 开发人员: zhangjingjing
 * 创建日期: 2015年1月8日
 */
@Entity
@Table(name="tb_user_group")
public class UserGroup extends BaseDomain{
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	@JoinColumn(name="user_id",updatable=false)
	@OneToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	private UserInfo user;
    @Column(name="group_power")
    private String groupPower;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroupPower() {
		return groupPower;
	}
	public void setGroupPower(String groupPower) {
		this.groupPower = groupPower;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}

    
}
