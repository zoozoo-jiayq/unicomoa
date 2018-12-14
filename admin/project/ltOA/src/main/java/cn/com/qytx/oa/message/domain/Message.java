package cn.com.qytx.oa.message.domain;

import java.io.Serializable;
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
 * 功能:微讯实体类
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
@Entity
@Table(name="tb_om_message")
public class Message extends BaseDomain implements Serializable
{

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	/**
	 * 创建人
	 */
	@JoinColumn(name="create_user_id")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo createUserInfo;
	 
	 /**
	  * 创建时间
	  */
	@Column(name="create_time",length=23)
	private Timestamp createTime;
	
	/**
	 * 删除状态
	 */
	@DeleteState
    @Column(name = "is_delete")
    private Integer isDelete = 0;
		
	/**
	 * last_update_time
	 */
	@Column(name="last_update_time",length=23)
	private Timestamp lastUpdateTime;
	
	@Column(name="last_update_user_id")
	private Integer lastUpdateUserId;
	
	/**
     * 接收人信息
     */
	@JoinColumn(name="to_uid")
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	private UserInfo toUserInfo;
	
    /**
     * 提醒标志
     */
	@Column(name="remind_Flag")
    private Integer remindFlag = 0;
	
    /**
     * 发送时间
     */
	@Column(name="send_time",length=23,nullable=false)
    private Timestamp sendTime = new Timestamp(System.currentTimeMillis());
    
    /**
     * 微讯来源类型
     * 1.网页2手机终端等
     */
	@Column(name="msg_type")
    private Integer msgType;
    
    /**
     * 微讯内容
     */
	@Column(name="content_info")
    private String contentInfo;
	
	 
    /**
     * 序列号
     */
	@Transient
    private static final long serialVersionUID = -3521892800382381539L;

    /**
     * 发送人Id
     */
	@Transient
    private Integer toUid;


    /**
     * 发送人信息
     */
	@Transient
    private String fromUserName;
    
    /**
     * 接收人信息
     */
	@Transient
    private String toUserName;
    
    /**
     * 接收人信息集合
     */
	@Transient
    private String toUids;
    
    /**
     * 接收人姓名集合
     */
	@Transient
    private String toUserNames;


    /**
     * 发送时间
     */
	@Transient
    private String sendTimeStr;
    
    /**
     * 微讯内容 box 表示页面发送的信息
     */
	@Transient
    private String srcType;
	
	@Transient
	private int createUserId;
	

    public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getToUid()
    {
        return this.toUid;
    }

    public void setToUid(Integer toUid)
    {
        this.toUid = toUid;
    }

    public Integer getRemindFlag()
    {
        return remindFlag;
    }

    public void setRemindFlag(Integer remindFlag)
    {
        this.remindFlag = remindFlag;
    }

    public Timestamp getSendTime()
    {
        return this.sendTime;
    }

    public void setSendTime(Timestamp sendTime)
    {
        this.sendTime = sendTime;
    }

    public Integer getMsgType()
    {
        return this.msgType;
    }

    public void setMsgType(Integer msgType)
    {
        this.msgType = msgType;
    }

    public String getContentInfo()
    {
        return this.contentInfo;
    }

    public void setContentInfo(String contentInfo)
    {
        this.contentInfo = contentInfo;
    }

    public String getSendTimeStr()
    {
        return sendTimeStr;
    }

    public void setSendTimeStr(String sendTimeStr)
    {
        this.sendTimeStr = sendTimeStr;
    }

    public UserInfo getToUserInfo()
    {
        return toUserInfo;
    }

    public void setToUserInfo(UserInfo toUserInfo)
    {
        this.toUserInfo = toUserInfo;
    }

    public String getToUids()
    {
        return toUids;
    }

    public void setToUids(String toUids)
    {
        this.toUids = toUids;
    }

    public String getFromUserName()
    {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName)
    {
        this.fromUserName = fromUserName;
    }

    public String getToUserName()
    {
        return toUserName;
    }

    public void setToUserName(String toUserName)
    {
        this.toUserName = toUserName;
    }

    public String getSrcType()
    {
        return srcType;
    }

    public void setSrcType(String srcType)
    {
        this.srcType = srcType;
    }

    public String getToUserNames()
    {
        return toUserNames;
    }

    public void setToUserNames(String toUserNames)
    {
        this.toUserNames = toUserNames;
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

    
}