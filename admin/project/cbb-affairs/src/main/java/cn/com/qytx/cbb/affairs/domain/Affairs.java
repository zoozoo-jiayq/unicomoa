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
 * 功能:事务通知子表实体类
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
@Entity
@Table(name="tb_om_affairs")
public class Affairs extends BaseDomain
{

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete;
	
	@JoinColumn(name="create_user_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo createUserInfo;
	
	@Column(name="create_time",length=23)
	private Timestamp createTime;
	
	@Column(name="last_update_time",length=23)
	private Timestamp lastUpdateTime;
	
	@Column(name="last_update_user_id")
	private Integer lastUpdateUserId;
	
    /**
     * 接收人
     */
	@JoinColumn(name="to_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo toUserInfo;
	
    /**
     * 消息提醒
     * 0已发送 1已接受 2已阅
     */
	@Column(name="remind_flag",nullable=false)
	private Integer remindFlag;
	
    /**
     * 消息体
     */
	@JoinColumn(name="body_id")
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private AffairsBody affairsBody;
	
    /**
     * 提醒时间
     */
	@Column(name="remind_time",length=23)
	private Timestamp remindTime;
	
	
    /**
     * 序列号
     */
	@Transient
    private static final long serialVersionUID = -727311897903453623L;

    /**
     * 接收人ID
     */
	@Transient
    private Integer toId;



    /**
     * 消息体Id
     */
	@Transient
    private Integer bodyId;


    
    /**
     * 距离当前时间
     */
	@Transient
    private String limitTime;

    public Integer getToId()
    {
        return this.toId;
    }

    public void setToId(Integer toId)
    {
        this.toId = toId;
    }

    public Integer getRemindFlag()
    {
        return this.remindFlag;
    }

    public void setRemindFlag(Integer remindFlag)
    {
        this.remindFlag = remindFlag;
    }

    public Integer getBodyId()
    {
        return this.bodyId;
    }

    public void setBodyId(Integer bodyId)
    {
        this.bodyId = bodyId;
    }

    public Timestamp getRemindTime()
    {
        return this.remindTime;
    }

    public void setRemindTime(Timestamp remindTime)
    {
        this.remindTime = remindTime;
    }

    public UserInfo getToUserInfo()
    {
        return toUserInfo;
    }

    public void setToUserInfo(UserInfo toUserInfo)
    {
        this.toUserInfo = toUserInfo;
    }

    public AffairsBody getAffairsBody()
    {
        return affairsBody;
    }

    public void setAffairsBody(AffairsBody affairsBody)
    {
        this.affairsBody = affairsBody;
    }

    public String getLimitTime()
    {
        return limitTime;
    }

    public void setLimitTime(String limitTime)
    {
        this.limitTime = limitTime;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

}