package cn.com.qytx.platform.org.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.qytx.platform.org.domain.base.BaseRoleInfo;


/**
 * 功能：角色信息表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午3:59:35 
 * 修改日期：下午3:59:35 
 * 修改列表：
 */
@Entity
@Table(name="tb_role_info")
public class RoleInfo extends BaseRoleInfo{

	/*
	 * 通用角色
	 */
	public final static String COMMON_ROLE = "common_role";
	
	@Column(name="is_fork_group")
	public Integer isForkGroup;

	public Integer getIsForkGroup() {
		return isForkGroup;
	}

	public void setIsForkGroup(Integer isForkGroup) {
		this.isForkGroup = isForkGroup;
	}
	
	
	
}