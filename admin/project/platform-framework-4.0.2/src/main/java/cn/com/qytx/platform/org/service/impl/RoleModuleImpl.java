package cn.com.qytx.platform.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.RoleModuleDao;
import cn.com.qytx.platform.org.domain.RoleModule;
import cn.com.qytx.platform.org.service.IRoleModule;

/**
 * 项目名称：wzerp 类名称：RoleModuleImpl 类描述： 创建人：CQL 创建时间：2012-10-8 下午08:29:08 修改人：CQL
 * 修改时间：2012-10-8 下午08:29:08 修改备注：
 * 
 * @version
 */
@Transactional
@Service("roleModuleService")
public class RoleModuleImpl extends BaseServiceImpl<RoleModule> implements IRoleModule {

	@Resource(name="roleModuleDao")
	private RoleModuleDao<RoleModule> roleModuleDao;

	/**
	 * @Title: saveRoleModules
	 * @Description: TODO(保存角色模版)
	 * @param roleId
	 * @param moduleIds
	 * @return void 返回类型
	 */
	public void saveRoleModules(Integer roleId,
			Integer[] moduleIds) {
		// 对应角色模版全部删除
		roleModuleDao.deleteModulesByRoleId(roleId);
		// 保存人员
		List<RoleModule> list = new ArrayList<RoleModule>();
		if(moduleIds!=null){
		for (int i = 0; i < moduleIds.length; i++) {
			RoleModule roleModule = new RoleModule();
			roleModule.setRoleId(roleId);
			roleModule.setModuleId(moduleIds[i]);
			roleModule.setCompanyId(TransportUser.get().getCompanyId());
			list.add(roleModule);
			}
		}
		roleModuleDao.saveOrUpdate(list);
	}
}
