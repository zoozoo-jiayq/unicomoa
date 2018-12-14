package cn.com.qytx.platform.org.domain.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.CompanyId;
import cn.com.qytx.platform.base.domain.DeleteState;


/**
 * 功能：角色基本信息表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午3:59:35 
 * 修改日期：下午3:59:35 
 * 修改列表：
 */@MappedSuperclass

public class BaseRoleInfo extends BaseDomain implements Serializable {

	// Fields
	@Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer roleId;
	
	@Column(name="role_name",length=50)
	private String roleName; //角色名称	
	
	@Column(name="Memo",length=100)
	private String memo;//备注
	
	@Column(name="order_index")
	private Integer orderIndex;
	
	@DeleteState
	@Column(name="is_delete")
	private Integer isDelete;//是否删除 1：已经删除0未删除

	@Column(name="role_type")
	private Integer roleType ;// 0,系统默认不可删除1,非系统默认
	 
	@Column(name="role_code",length=50)
	private String roleCode; // 角色编码 seat:座席 seatleader：座席班长
	

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	
	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}