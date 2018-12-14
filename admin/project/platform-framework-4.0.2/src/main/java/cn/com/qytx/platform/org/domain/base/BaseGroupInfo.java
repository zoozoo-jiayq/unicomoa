package cn.com.qytx.platform.org.domain.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;


/**
 * 功能：部门基本信息表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午3:36:05 
 * 修改日期：下午3:36:05 
 * 修改列表：
 */
@MappedSuperclass
public class BaseGroupInfo  extends BaseDomain{
	// Fields
	@Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer groupId;
	
	//部门名称
	@Column(name="group_name",length=50)
	private String groupName;

    //部门类型 1 企业通讯录 ;2 公共通讯录 ;3 私人通讯录  ; 5 个人群组;4公共群组(原为4)
	@Column(name="group_type")
	private Integer groupType;
	
	//父ID
	@Column(name="parent_id")
	private Integer parentId;
	
	//排序
	@Column(name="order_index")
	private Integer orderIndex;
	
	//部门编码  在移动通讯助理中 作为部门分机号码使用
	@Column(name="group_code",length=50)
	private String groupCode;
	
	@Column(name="Memo",length=300)
	private String memo;
	

	@DeleteState
	@Column(name="is_delete")
	private Integer isDelete;

    //部门电话 在移动通讯助理中使用
	@Column(name="Phone",length=50)
	private String phone;
	
	//主要只能
	@Column(name="Functions",length=500)
	private String functions;
	
	@Column(name="Path",length=50)
	private String path;
    @Column(name="last_update_time",length=23)
    private Date lastUpdateTime;
	//创建人ID
	@Column(name="user_id")
	private Integer userId;
	
    public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}


	public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseGroupInfo other = (BaseGroupInfo) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}
    
}