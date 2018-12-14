package cn.com.qytx.cbb.jpush.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 功能:推送人员表
 * 版本: 1.0
 * 开发人员: zhangjingjing
 * 创建日期: 2014-6-24
 * 修改日期: 2014-6-24
 * 修改列表:
 */
@Entity
@Table(name="tb_cbb_jpush_user")
public class PushUser {
	@Id
	@Column(name="send_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sendNo;
    @Column(name="Push_Id",nullable=false)
	private Integer pushId;
    @Column(name="User_type",nullable=false)
	private Integer userType;
	@Column(name="User_Id",nullable=false)
	private Integer userId;
	@Column(name="User_name",nullable=true)
    private String userName;
	@Column(name="is_read")
    private Integer isRead; //0:未读；1：已读

	public Integer getSendNo() {
		return sendNo;
	}

	public void setSendNo(Integer sendNo) {
		this.sendNo = sendNo;
	}

	public Integer getPushId() {
		return pushId;
	}

	public void setPushId(Integer pushId) {
		this.pushId = pushId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

}