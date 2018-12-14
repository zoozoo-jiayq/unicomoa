package cn.com.qytx.cbb.module.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.BaseDomain;

/**
 * 手机用户角色表
 * @author liyanlei
 *
 */
@Entity
@Table(name="tb_role_module_mobile")
public class RoleModuleMobile extends BaseDomain implements Serializable{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * 拥有模块id
	 */
	@Column(name="module_id")
	private Integer moduleId;
	
	/**
	 * 角色id
	 */
	@Column(name="role_id")
	private Integer roleId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
}
