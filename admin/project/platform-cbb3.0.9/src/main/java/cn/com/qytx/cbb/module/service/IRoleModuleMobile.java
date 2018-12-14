package cn.com.qytx.cbb.module.service;

import java.util.List;

import cn.com.qytx.cbb.module.domain.RoleModuleMobile;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 手机用户角色接口定义
 * @author liyanlei
 *
 */
public interface IRoleModuleMobile extends BaseService<RoleModuleMobile>{
	
	/**
	 * 得到模块
	 * @param roleId
	 * @return
	 */
	public List<RoleModuleMobile> getModuleList(Integer roleId);
	
	/**
	 * 更新模块
	 * @param moduleIds
	 * @param roleId
	 */
	public void updateModuleList(Integer[] moduleIds, Integer roleId);

}
