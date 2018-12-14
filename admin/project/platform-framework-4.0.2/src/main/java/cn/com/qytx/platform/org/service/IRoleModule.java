package cn.com.qytx.platform.org.service;


import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.RoleModule;

/**   
 * 项目名称：wzerp   
 * 类名称：IRoleModule   
 * 类描述：   
 * 创建人：CQL   
 * 创建时间：2012-10-8 下午07:10:13   
 * 修改人：CQL   
 * 修改时间：2012-10-8 下午07:10:13   
 * 修改备注：   
 * @version      
 */
public interface IRoleModule extends BaseService<RoleModule>{
	/**
	 * @Title: saveRoleModules 
	 * @Description: TODO(保存角色模版) 
	 * @param  roleId
	 * @param  moduleIds
	 * @return void    返回类型
	 */
	public void saveRoleModules(Integer roleId, Integer[] moduleIds);
}
