package cn.com.qytx.cbb.jpush.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.DeleteState;
/**
 * 功能:推送表
 * 版本: 1.0
 * 开发人员: zhangjingjing
 * 创建日期: 2014-6-24
 * 修改日期: 2014-6-24
 * 修改列表:
 */
@Entity
@Table(name="tb_cbb_jpush")
public class PushInfo{
	@Id
	@Column(name="push_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pushId;
	@Column(name="Subject",length=200,nullable=false)
    private String subject;
	@Column(name="User_Id")
    private Integer userId;
	@Column(name="User_Name",length=50)
    private String userName;
	@Column(name="Push_Content",length=5000)
	private String pushContent;
	@Column(name="Show_Content",length=5000)
    private String showContent;
	@Column(name="Push_Time")
    private Timestamp pushTime;
	@Column(name="Insert_Time")
    private Timestamp insertTime;
	@DeleteState
	@Column(name = "Is_Delete")
    private Integer isDelete;
	@Column(name = "push_type")
    private Integer pushType;
    //手机端显示
	@Transient
    private String pushTimeStr;
	@Transient
    private String insertTimeStr;
	@Transient
    private int pushCount;//推荐数量
	@Transient
    private int pushSuccessCount;//推荐成功数量
    @Transient
    private Integer isRead; //0:未读；1：已读
    
	public Integer getPushId() {
		return pushId;
	}

	public void setPushId(Integer pushId) {
		this.pushId = pushId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getShowContent() {
		return showContent;
	}

	public void setShowContent(String showContent) {
		this.showContent = showContent;
	}
	
	public String getPushContent() {
		return pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

	public Timestamp getPushTime() {
		return pushTime;
	}

	public void setPushTime(Timestamp pushTime) {
		this.pushTime = pushTime;
	}

	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public String getPushTimeStr() {
		return pushTimeStr;
	}

	public void setPushTimeStr(String pushTimeStr) {
		this.pushTimeStr = pushTimeStr;
	}

	public String getInsertTimeStr() {
		return insertTimeStr;
	}

	public void setInsertTimeStr(String insertTimeStr) {
		this.insertTimeStr = insertTimeStr;
	}

	public int getPushCount() {
		return pushCount;
	}

	public void setPushCount(int pushCount) {
		this.pushCount = pushCount;
	}

	public int getPushSuccessCount() {
		return pushSuccessCount;
	}

	public void setPushSuccessCount(int pushSuccessCount) {
		this.pushSuccessCount = pushSuccessCount;
	}

	public Integer getIsDelete() {
		return isDelete;
	}
	
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}
	
}