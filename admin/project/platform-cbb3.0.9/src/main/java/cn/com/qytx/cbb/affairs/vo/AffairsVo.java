package cn.com.qytx.cbb.affairs.vo;

import java.io.Serializable;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:事务提醒Vo
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public class AffairsVo implements Serializable
{
    /**
     * 描述含义
     */
    private static final long serialVersionUID = -3439922663278722676L;

    /**
     * 查询用户类型 1发送人 2接收人
     */
    private Integer userType;

    /**
     * 发送人或接收人Id集合
     */
    private String userIds;

    /**
     * 事务提醒类型
     */
    private String smsType;

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束
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
     * 操作
     */
    private Integer operationType;

    /**
     * 用户
     */
    private UserInfo userinfo;

    public Integer getUserType()
    {
        return userType;
    }

    public void setUserType(Integer userType)
    {
        this.userType = userType;
    }

    public String getUserIds()
    {
        return userIds;
    }

    public void setUserIds(String userIds)
    {
        this.userIds = userIds;
    }

    public String getSmsType()
    {
        return smsType;
    }

    public void setSmsType(String smsType)
    {
        this.smsType = smsType;
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

    public Integer getOperationType()
    {
        return operationType;
    }

    public void setOperationType(Integer operationType)
    {
        this.operationType = operationType;
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