package cn.com.qytx.oa.publicaddress.domain;

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
 * 
 * 功能:联系人群组表
 * 版本: 1.0
 * 修改列表:
 */
@Entity
@Table(name="tb_oab_group")
public class AddressGroup extends BaseDomain implements java.io.Serializable {
	/**
	 * 主键ID
	 */
	@Id
	@Column(name="vid")
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
	
	/**
	 * 排序号
	 */
	@Column(name="order_no")
	private Integer orderNo;
	
	@Column(name="last_update_time",length=23)
	private Timestamp lastUpdateTime;
	
	@Column(name="last_update_user_id")
	private Integer lastUpdateUserId;
	
	/**
	 * 组名
	 */
	@Column(name="name",length=100,nullable=false)
	private String name;
	
	/**
	 * 备注
	 */
	@Column(name="remark",length=500)
	private String remark;
	
	/**
	 * 群组类型 
	 * 1 表示个人通讯簿 
	 * 2 表示公共通讯簿
	 */
	@Column(name="grouptype")
	private Integer groupType;
	
    /**
     * 维护部门集合
     */
	@Column(name="maintaingroupids")
	private String maintainGroupIds;
	
    /**
     * 维护部门名称集合
     */
	@Column(name="maintaingroupnames")
	private String maintainGroupNames;
	
    /**
     * 维护人员Id集合
     */
	@Column(name="maintainuserids")
	private String maintainUserIds;
	
    /**
     * 维护人员名称集合
     */
	@Column(name="maintainusernames")
	private String maintainUserNames;
	
	@Transient
	private static final long serialVersionUID = 2532819515772269035L;
	
    /**
     * 公布范围 人员
     */
	@Transient
    private String userIds;
    
    /**
     * 公布范围 部门
     */
	@Transient
    private String groupIds;
    
    /**
     * 公布范围 角色
     */
	@Transient
    private String roleIds;
	
    /**
     * 角色名
     */
	@Transient
    private String roleNames;
	
    /**
     * 部门名
     */
	@Transient
    private String groupNames;
	
    /**
     * 用户名
     */
	@Transient
    private String userNames;
    
    /**
     * 包含的人员数
     */
	@Transient
    private Integer containAddress;
    
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public Integer getGroupType()
    {
        return groupType;
    }

    public void setGroupType(Integer groupType)
    {
        this.groupType = groupType;
    }

    public String getRoleNames()
    {
        return roleNames;
    }

    public void setRoleNames(String roleNames)
    {
        this.roleNames = roleNames;
    }

    public String getGroupNames()
    {
        return groupNames;
    }

    public void setGroupNames(String groupNames)
    {
        this.groupNames = groupNames;
    }

    public String getUserNames()
    {
        return userNames;
    }

    public void setUserNames(String userNames)
    {
        this.userNames = userNames;
    }

    public String getUserIds()
    {
        return userIds;
    }

    public void setUserIds(String userIds)
    {
        this.userIds = userIds;
    }

    public String getGroupIds()
    {
        return groupIds;
    }

    public void setGroupIds(String groupIds)
    {
        this.groupIds = groupIds;
    }

    public String getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(String roleIds)
    {
        this.roleIds = roleIds;
    }

    public String getMaintainGroupIds()
    {
        return maintainGroupIds;
    }

    public void setMaintainGroupIds(String maintainGroupIds)
    {
        this.maintainGroupIds = maintainGroupIds;
    }

    public String getMaintainGroupNames()
    {
        return maintainGroupNames;
    }

    public void setMaintainGroupNames(String maintainGroupNames)
    {
        this.maintainGroupNames = maintainGroupNames;
    }

    public String getMaintainUserIds()
    {
        return maintainUserIds;
    }

    public void setMaintainUserIds(String maintainUserIds)
    {
        this.maintainUserIds = maintainUserIds;
    }

    public String getMaintainUserNames()
    {
        return maintainUserNames;
    }

    public void setMaintainUserNames(String maintainUserNames)
    {
        this.maintainUserNames = maintainUserNames;
    }

    public Integer getContainAddress()
    {
        return containAddress;
    }

    public void setContainAddress(Integer containAddress)
    {
        this.containAddress = containAddress;
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