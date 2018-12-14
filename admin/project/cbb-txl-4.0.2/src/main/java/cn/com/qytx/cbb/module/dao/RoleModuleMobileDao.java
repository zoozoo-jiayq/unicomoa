package cn.com.qytx.cbb.module.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.module.domain.RoleModuleMobile;
import cn.com.qytx.platform.base.dao.BaseDao;
/**
 * 角色模块
 * @author liyanlei
 *
 */
@Repository("roleModuleMobileDao")
public class RoleModuleMobileDao extends BaseDao<RoleModuleMobile,Integer>{
	
	/**
	 * 通过用户角色id得到所有手机模块
	 * @param roleId
	 * @return
	 */
	public List<RoleModuleMobile> getModuleList(Integer roleId) {
		String hql ="roleId = ?1";
		return super.findAll(hql,roleId);
	}
	/**
	 * 删除角色id下的所有模块权限
	 * @param roleId
	 */
	public void delByRoleId(Integer roleId) {
		String hql ="delete from RoleModuleMobile where roleId = ?1";
		super.executeQuery(hql,roleId);
	}

}
