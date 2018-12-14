package cn.com.qytx.platform.org.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.ModuleDao;
import cn.com.qytx.platform.org.domain.ModuleInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.service.IModule;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.serviceAdapter.core.Constant;

/**
 * 模块操作实现类
 * User: 黄普友
 * Date: 13-2-19
 * Time: 下午4:25
 */
@Transactional
@Service("moduleService")
public class ModuleImpl  extends BaseServiceImpl<ModuleInfo> implements IModule {
    
	@Resource(name="moduleDao")
	private ModuleDao<ModuleInfo> moduleDao;
    @Resource(name = "roleService")
    private IRole roleService;
    
	@Override
    public List<ModuleInfo> getAllModule() {
        return moduleDao.getAllModule();
    }

    @Override
    public List<ModuleInfo> getModuleByRole(String roleArr) {
        return moduleDao.getModuleByRole(roleArr);
    }
    /**
     * 根据角色ID数组获取没有权限模块列表
     * @param roleArr 角色ID，多个ID直接用，隔开
     * @return
     */
	public List<ModuleInfo> getNotModuleByRole(String roleArr){
		 return moduleDao.getNotModuleByRole(roleArr);
	}

	/**
	 * @Description: TODO(得到级别Module) 
	 * @param  level
	 * @param  moduleMap
	 * @return Map<Integer,Module>    返回类型
	 */
	public Map<Integer,ModuleInfo> findModuleLevelMap(Map<Integer,ModuleInfo> moduleMap,Integer level){
		return moduleDao.findModuleLevelMap(moduleMap, level);
	}
	
	/**
	 * 
	 * @Title: findModuleMap 
	 * @Description: TODO(得到Module) 
	 * @param sysName 系统名称
	 * @return Map<Integer,Module>    返回类型
	 */
	public Map<Integer,ModuleInfo> findModuleMap(String sysName,Integer roleId){
		return moduleDao.findModuleMap(sysName, roleId);
	}

	@Override
	public List<ModuleInfo> findAll() {
		return moduleDao.findAll();
	}

	@Override
	public List<ModuleInfo> getModuleListByUser(int companyId, int userId) {
		//每个用户默认都有【普通用户角色】
		RoleInfo r = roleService.findByRoleCode(companyId, Constant.ROLE_INFO_ROLE_CODE_STAFF);
		
		// //人员功能权限
		String roleIdArr = "";
		// 根据人员Id获取角色列表
		List<RoleInfo> roleList = roleService.getRoleByUser(userId); 
		if(roleList!=null&&roleList.size()>0){
			roleList.add(r);
			Collection<Integer> roles = Collections2.transform(roleList, new Function<RoleInfo,Integer>(){
			@Override
			public Integer apply(RoleInfo input) {
				return input.getRoleId();
			}
			});
			//roles.add(r.getRoleId());
			roleIdArr = StringUtils.join(roles, ",");
		}
		List<ModuleInfo> moduleList = this.getModuleByRole(roleIdArr);
		return moduleList;
	}
	
	/**
     * 根据模块类型id模块列表
     * @param sysName
     * @return 菜单列表
     */
	public List<ModuleInfo> getModuleBySysName(String sysName){
		return moduleDao.getModuleBySysName(sysName);
	}
	

}
