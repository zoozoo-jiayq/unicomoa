package cn.com.qytx.cbb.affairs.domain;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 保存事务提醒的发送信息
 * @author TANG
 *
 */
public class ReminderInfo {
	/**  发送人  */
	private UserInfo fromUserInfo;
	
	/** 在线提醒内容  */
	private String affairContent;
	
	/** 短信内容  */
	private String smsContent;
	
	/** 推送内容  */
	private String pushContent ;
	
	/** 推送标题  */
	private String pushSubjcet ;
	
	/** 推送链接  */
	private String pushUrl ;
	
	/** 事务提醒人的id集合，以“，”分隔  */
	private String toids ;
	
	/** 在线提醒链接  */
	private String affairUrl ;
	
	/** 模块名称  */
	private String moduleName ;
	
	/** 记录ID  */
	private String recordId  ;
	
	/** 事务提醒类型(1_0_1表示要在线提醒，不发送短信，发送手机推送)  */
	private String sendType  ;
	
	/**  **/
	private String pushType;
    
	/**
	 * 附件id
	 */
	private String attmentIds;
	
	/**
	 * 模块id
	 */
	private String moduleId;
	
	public UserInfo getFromUserInfo() {
		return fromUserInfo;
	}

	public void setFromUserInfo(UserInfo fromUserInfo) {
		this.fromUserInfo = fromUserInfo;
	}

	public String getAffairContent() {
		return affairContent;
	}

	public void setAffairContent(String affairContent) {
		this.affairContent = affairContent;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getPushContent() {
		return pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

	public String getPushSubjcet() {
		return pushSubjcet;
	}

	public void setPushSubjcet(String pushSubjcet) {
		this.pushSubjcet = pushSubjcet;
	}

	public String getPushUrl() {
		return pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public String getToids() {
		return toids;
	}

	public void setToids(String toids) {
		this.toids = toids;
	}

	public String getAffairUrl() {
		return affairUrl;
	}

	public void setAffairUrl(String affairUrl) {
		this.affairUrl = affairUrl;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getAttmentIds() {
		return attmentIds;
	}

	public void setAttmentIds(String attmentIds) {
		this.attmentIds = attmentIds;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	
	
}
