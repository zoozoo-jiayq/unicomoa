package cn.com.qytx.oa.message.domain;

import java.sql.Timestamp;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 
 * 功能: 微讯视图信息
 * 创建日期: 2013-5-8
 * 修改日期: 2013-5-8
 * 修改列表:
 */
public class MessageView
{
    
	/**
	 * 主键ID
	 */
	private Integer id;
	
    /**
     * 发送时间
     */
    private Timestamp sendTime;

    
    /**
     * 微讯内容
     */
    private String contentInfo;
	
    /**
     * 未读信息数量
     */
    private Integer unReadNum;

	private UserInfo createUserInfo;
	
    
    /**
     * 接收人信息
     */
	private UserInfo toUserInfo;
	
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 发送人Id
     */
    private Integer toUid;


    /**
     * 发送人信息
     */
    private String fromUserName;

    

    public Integer getToUid()
    {
        return toUid;
    }

    public void setToUid(Integer toUid)
    {
        this.toUid = toUid;
    }

    public UserInfo getToUserInfo()
    {
        return toUserInfo;
    }

    public void setToUserInfo(UserInfo toUserInfo)
    {
        this.toUserInfo = toUserInfo;
    }

    public String getFromUserName()
    {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName)
    {
        this.fromUserName = fromUserName;
    }

    public Timestamp getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime)
    {
        this.sendTime = sendTime;
    }

    public String getContentInfo()
    {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo)
    {
        this.contentInfo = contentInfo;
    }

    public Integer getUnReadNum()
    {
        return unReadNum;
    }

    public void setUnReadNum(Integer unReadNum)
    {
        this.unReadNum = unReadNum;
    }

	public UserInfo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserInfo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    

}
