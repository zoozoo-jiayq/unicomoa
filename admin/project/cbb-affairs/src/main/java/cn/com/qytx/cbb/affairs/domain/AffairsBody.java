package cn.com.qytx.cbb.affairs.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:事务提醒主体实体类
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
@Entity
@Table(name="tb_om_affairs_body")
public class AffairsBody extends BaseDomain
{
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@JoinColumn(name="create_user_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo createUserInfo;
	
	@Column(name="create_time",length=23)
	private Timestamp createTime;
	
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete;
	
	@Column(name="last_update_time",length=23)
	private Timestamp lastUpdateTime;
	
	@Column(name="last_update_user_id")
	private Integer lastUpdateUserId;
	
    /**
     * 发送人
     */
	@JoinColumn(name="from_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo fromUserInfo;
	
    /**
     * 模块标示字段
     */
	@Column(name="sms_type")
	private String smsType;

    /**
     * 提醒内容
     */
	@Column(name="content_info")
	private String contentInfo;

    /**
     * 提醒时间
     */
	@Column(name="send_time",length=23)
	private Timestamp sendTime;

	/**
     * 提醒对应URL
     */
	@Column(name="remind_url")
	private String remindUrl;
	
	/**
	 * 记录ID
	 */
	@Column(name="record_id")
	private String recordId;
	
	
	
    /**
     * 序列号
     */
	@Transient
    private static final long serialVersionUID = -1465481972612080985L;

    /**
     * 发送人Id
     */
	@Transient
    private Integer fromId;




    /**
     * 接收人Id集合 以,分割
     */
	@Transient
    public String toIds;

    public Integer getFromId()
    {
        return this.fromId;
    }

    public void setFromId(Integer fromId)
    {
        this.fromId = fromId;
    }

    public String getSmsType()
    {
        return this.smsType;
    }

    public void setSmsType(String smsType)
    {
        this.smsType = smsType;
    }

    public String getContentInfo()
    {
        return this.contentInfo;
    }

    public void setContentInfo(String contentInfo)
    {
        this.contentInfo = contentInfo;
    }

    public Timestamp getSendTime()
    {
        return this.sendTime;
    }

    public void setSendTime(Timestamp sendTime)
    {
        this.sendTime = sendTime;
    }

    public String getRemindUrl()
    {
        return this.remindUrl;
    }

    public void setRemindUrl(String remindUrl)
    {
        this.remindUrl = remindUrl;
    }

    public UserInfo getFromUserInfo()
    {
        return fromUserInfo;
    }

    public void setFromUserInfo(UserInfo fromUserInfo)
    {
        this.fromUserInfo = fromUserInfo;
    }

    public String getToIds()
    {
        return toIds;
    }

    public void setToIds(String toIds)
    {
        this.toIds = toIds;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserInfo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserInfo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Integer lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	
	
}