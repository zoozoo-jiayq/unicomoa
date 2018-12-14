package cn.com.qytx.oa.publicaddress.vo;

import java.sql.Timestamp;

import cn.com.qytx.oa.publicaddress.domain.Address;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能:联系人通讯簿VO
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public class AddressVo extends Address
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 23827594517603429L;

    /**
     * 生日起始查询日期
     */
    private String startDate;

    /**
     * 生日结束查询日期
     */
    private String endDate;
    
    /**
     * 共享起始时间
     */
    private String shareStartTime;

    /**
     * 共享结束时间
     */
    private String shareEndTime;
    
    /**
     * 生日起始查询日期,日期类型
     */
    private Timestamp startTimestamp;

    /**
     * 生日结束查询日期,日期类型
     */
    private Timestamp endTimestamp;
    
    /**
     * 排序字段
     */
    private String sortFiled;

    /**
     * 排序方式 升序asc 降序 desc
     */
    private String sortType;
    
    /**
     * type=share 表示获取共享中的数据
     */
    private String type;
    
    /**
     * 标记是否为公共通讯簿 1表示为公共
     */
    public Integer publicSign;
    
    /**
     * 公共通讯簿权限字段
     */
    private String dataPrivHql;
    
    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        if (!StringUtil.isEmpty(startDate))
        {
            setStartTimestamp(DateTimeUtil.stringToTimestamp(startDate, CbbConst.DATE_FORMAT_STR));
        }
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        if (!StringUtil.isEmpty(endDate))
        {
            setEndTimestamp(DateTimeUtil.stringToTimestamp(endDate, CbbConst.DATE_FORMAT_STR));
        }
        this.endDate = endDate;
    }

    public Timestamp getStartTimestamp()
    {
        return startTimestamp;
    }

    public void setStartTimestamp(Timestamp startTimestamp)
    {
        this.startTimestamp = startTimestamp;
    }

    public Timestamp getEndTimestamp()
    {
        return endTimestamp;
    }

    public void setEndTimestamp(Timestamp endTimestamp)
    {
        this.endTimestamp = endTimestamp;
    }

    public String getShareStartTime()
    {
        return shareStartTime;
    }

    public void setShareStartTime(String shareStartTime)
    {
        this.shareStartTime = shareStartTime;
    }

    public String getShareEndTime()
    {
        return shareEndTime;
    }

    public void setShareEndTime(String shareEndTime)
    {
        this.shareEndTime = shareEndTime;
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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Integer getPublicSign()
    {
        return publicSign;
    }

    public void setPublicSign(Integer publicSign)
    {
        this.publicSign = publicSign;
    }

    public String getDataPrivHql()
    {
        return dataPrivHql;
    }

    public void setDataPrivHql(String dataPrivHql)
    {
        this.dataPrivHql = dataPrivHql;
    }
}
