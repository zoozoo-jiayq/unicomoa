package cn.com.qytx.oa.message.vo;

import cn.com.qytx.oa.message.domain.Message;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:微讯Vo
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public class MessageVo extends Message
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 消息类型：
     * -1全部类型 1微讯网页消息 2即时通讯离线消息 3微讯移动版
     * 4微讯移动版(iPhone) 5微讯移动版(Android)
     */
    private Integer msgType;

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 内容
     */
    private String contentInfo;

    /**
     * 排序字段
     */
    private String sortFiled;

    /**
     * 排序方式 升序asc 降序 desc
     */
    private String sortType;

    /**
     * 用户
     */
    private UserInfo userinfo;
    
    private String ids;

    public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getMsgType()
    {
        return msgType;
    }

    public void setMsgType(Integer msgType)
    {
        this.msgType = msgType;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getContentInfo()
    {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo)
    {
        this.contentInfo = contentInfo;
    }

    public String getSortFiled()
    {
        return sortFiled;
    }

    public void setSortFiled(String sortFiled)
    {
        this.sortFiled = sortFiled;
    }

    public String getSortType()
    {
        return sortType;
    }

    public void setSortType(String sortType)
    {
        this.sortType = sortType;
    }

    public UserInfo getUserinfo()
    {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo)
    {
        this.userinfo = userinfo;
    }
}
