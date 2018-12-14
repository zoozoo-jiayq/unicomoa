package cn.com.qytx.oa.email.domain;

/** 
 * 发送邮件需要使用的基本信息 
 */
import java.io.Serializable;
import java.util.Properties;



public class MailSenderInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5711939847283320389L;
	// 发送邮件的服务器的IP和端口
	private String mailServerHost;
	private String mailServerPort = "25";
	// 邮件发送者的地址
	private String fromAddress;
	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	// 是否需要身份验证
	private boolean validate = false;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;


	//接收者
	private String ToAddress;
	// 抄送者
    private String[] ccs;
    private String[] ccsName;
    //密送者
    private String[] bccs;
    private String[] bccsName;
    // 0表示发送,1表示草稿箱
    private String isSend;
    
    /**
     * 邮件主体的Id
     */
    private Integer emailId;
    
    public String[] getCcs() {
        return ccs;
    }
    public void setCcs(String[] ccs) {
        this.ccs = ccs;
    }

	// 邮件的接收者，可以有多个
	private String[] receivers;
	private String[] receiversName;
	public String[] getReceivers() {
		return receivers;
	}

	public void setReceivers(String[] receivers) {
		this.receivers = receivers;
	}

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		
		
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
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

	public String getToAddress() {
		return ToAddress;
	}

	public void setToAddress(String toAddress) {
		ToAddress = toAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    public String getIsSend()
    {
        return isSend;
    }
    public void setIsSend(String isSend)
    {
        this.isSend = isSend;
    }
    public String[] getBccs()
    {
        return bccs;
    }
    public void setBccs(String[] bccs)
    {
        this.bccs = bccs;
    }
    public String[] getCcsName()
    {
        return ccsName;
    }
    public void setCcsName(String[] ccsName)
    {
        this.ccsName = ccsName;
    }
    public String[] getBccsName()
    {
        return bccsName;
    }
    public void setBccsName(String[] bccsName)
    {
        this.bccsName = bccsName;
    }
    public String[] getReceiversName()
    {
        return receiversName;
    }
    public void setReceiversName(String[] receiversName)
    {
        this.receiversName = receiversName;
    }
    public Integer getEmailId()
    {
        return emailId;
    }
    public void setEmailId(Integer emailId)
    {
        this.emailId = emailId;
    }
	
	


}