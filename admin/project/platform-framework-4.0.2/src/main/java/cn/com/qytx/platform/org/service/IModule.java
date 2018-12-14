package cn.com.qytx.platform.org.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.ModuleInfo;

/**
 * 模块操作类
 * User: 黄普友
 * Date: 13-2-19
 * Time: 下午4:20
 */
public interface IModule   extends BaseService<ModuleInfo>, Serializable{
    /**
     * 获取所有功能模块
     * @return
     */
    public List<ModuleInfo> getAllModule();

    /**
     * 根据角色ID数组获取模块列表
     * @param roleArr 角色ID，多个ID直接用，隔开
     * @return
     */
    public List<ModuleInfo> getModuleByRole(String roleArr);
    /**
     * 根据角色ID数组获取没有权限模块列表
     * @param roleArr 角色ID，多个ID直接用，隔开
     * @return
     */
	public List<ModuleInfo> getNotModuleByRole(String roleArr);
    
	/**
	 * @Description: TODO(得到级别Module) 
	 * @param  level
	 * @param  moduleMap
	 * @return Map<Integer,Module>    返回类型
	 */
	public Map<Integer,ModuleInfo> findModuleLevelMap(Map<Integer,ModuleInfo> moduleMap,Integer level);
	
	/**
	 * @Title: findModuleMap 
	 * @Description: TODO(得到Module) 
	 * @param sysName 系统名称
	 * @return Map<Integer,T> 返回类型
	 */
	public Map<Integer,ModuleInfo> findModuleMap(String sysName,Integer roleId);
	
	/**
	 * 根据用户Id获取用户有权限的模块列表
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public List<ModuleInfo> getModuleListByUser(int companyId,int userId);
	
	/**
     * 根据模块类型id模块列表
     * @param sysName
     * @return 菜单列表
     */
	public List<ModuleInfo> getModuleBySysName(String sysName);
	
}
