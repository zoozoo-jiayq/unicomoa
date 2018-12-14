package cn.com.qytx.oa.dataPriv.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;

/**
 * 功能:数据权限表
 * 版本: 1.0
 * 开发人员: zhangjingjing
 * 创建日期: 2014-6-26
 * 修改日期: 2014-6-26
 * 修改列表: 
 */
@Entity
@Table(name="tb_cbb_data_priv")
public class DataPriv extends BaseDomain{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", unique=true,nullable=false)
	private int id;
    @Column(name = "create_user_id")
	private int createUserId;
    @Column(name="create_time")
	private Timestamp createTime;
    @Column(name="last_update_time")
	private Timestamp lastUpdateTime;
    @Column(name="last_update_user_id")
	private int lastUpdateUserId;
	@Column(name="module_name",length=200)
	private String moduleName;//模块名称
	@Column(name="sub_module_name",length=100)
	private String subModuleName;//子模块名称
	@Column(name="ref_id")
	private Integer refId;//数据ID
	@Column(name="user_ids",length=5000)
	private String userIds;//人员ID集合
	@Column(name="group_ids",length=5000)
	private String groupIds;//群组ID集合
	@Column(name="role_ids",length=5000)
	private String roleIds;//角色ID集合
	@Column(name="group_names",length=5000)
	private String groupNames;//部门名
	@Column(name="role_names",length=5000)
	private String roleNames;//角色名
	@Column(name="user_names",length=5000)
	private String userNames;// 用户名
	@Column(name="ref_name",length=50)
	private String refName;//数据名称
	@DeleteState
	@Column(name = "is_delete")
	private int isDelete;
	@Transient
	private String param1;
	
	
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
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
	
	public int getLastUpdateUserId() {
		return lastUpdateUserId;
	}
	public void setLastUpdateUserId(int lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getSubModuleName() {
		return subModuleName;
	}
	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}
	public Integer getRefId() {
		return refId;
	}
	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getGroupNames() {
		return groupNames;
	}
	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	

}