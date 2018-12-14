package cn.com.qytx.oa.email.domain;

import java.io.Serializable;

public class MailsInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4283329089511825847L;
	private int MessageID;
	private int UserId;
	private String[] From;//address和Personal
	private String cc;//抄送
	private String ccId;//抄送
	private String UserName;
	private String Pwd;
	private String SentDate;
	private String Subject;//主题
	private String Content;
	private int Attachment; //0有附件1没附件
	private String FileName;
	private int isNew;//0已读1未读
    private String attachmentJson; // 附件json字符串
    
    private String receive;//接收
    private String receiveId;//接收
    private String sc;//密送
    private String scId;//密送
    
    private Integer readStatus;
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int UserId) {
		this.UserId = UserId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPwd() {
		return Pwd;
	}
	public void setPwd(String pwd) {
		Pwd = pwd;
	}
	public int getMessageID() {
		return MessageID;
	}
	public void setMessageID(int messageID) {
		MessageID = messageID;
	}
	public String[] getFrom() {
		return From;
	}
	public void setFrom(String[] from) {
		From = from;
	}
	public String getSentDate() {
		return SentDate;
	}
	public void setSentDate(String sentDate) {
		SentDate = sentDate;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public int getAttachment() {
		return Attachment;
	}
	public void setAttachment(int attachment) {
		Attachment = attachment;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
    public String getAttachmentJson()
    {
        return attachmentJson;
    }
    public void setAttachmentJson(String attachmentJson)
    {
        this.attachmentJson = attachmentJson;
    }
    public String getCc()
    {
        return cc;
    }
    public void setCc(String cc)
    {
        this.cc = cc;
    }
    public String getCcId()
    {
        return ccId;
    }
    public void setCcId(String ccId)
    {
        this.ccId = ccId;
    }
    public String getReceive()
    {
        return receive;
    }
    public void setReceive(String receive)
    {
        this.receive = receive;
    }
    public String getReceiveId()
    {
        return receiveId;
    }
    public void setReceiveId(String receiveId)
    {
        this.receiveId = receiveId;
    }
    public String getSc()
    {
        return sc;
    }
    public void setSc(String sc)
    {
        this.sc = sc;
    }
    public String getScId()
    {
        return scId;
    }
    public void setScId(String scId)
    {
        this.scId = scId;
    }
    public Integer getReadStatus()
    {
        return readStatus;
    }
    public void setReadStatus(Integer readStatus)
    {
        this.readStatus = readStatus;
    }
}
