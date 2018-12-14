package cn.com.qytx.platform.org.dao;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.ModuleInfo;

/**
 * 模块数据库操作类
 * User: 黄普友
 * Date: 13-2-19
 * Time: 下午4:24
 */
@Repository("moduleDao")
public class ModuleDao <T extends ModuleInfo>  extends BaseDao<ModuleInfo,Integer> implements Serializable{
    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 获取所有功能模块(未删除的)
     * @return 
     */
	public List<ModuleInfo> getAllModule()
    {
        return super.unDeleted().findAll();
    }
    /**
     * 根据角色ID数组获取模块列表
     * @param roleArr 角色ID，多个ID直接用，隔开
     * @return 返回角色对应的菜单
     */
	public List<ModuleInfo> getModuleByRole(String roleArr){
        if (StringUtils.isEmpty(roleArr)){
            return  new ArrayList<ModuleInfo>();
        }
        String hql = "";
        if(roleArr!=null && !roleArr.equals(""))
        {
            hql+="isDelete = 0 and moduleId in (select moduleId from RoleModule where roleId in ("+roleArr+"))";
        }
        Order o = new Order(Direction.ASC,"orderIndex");
        Sort s = new Sort(o);
        return this.findAll(hql,s);
    }
    /**
     * 根据角色ID数组获取没有权限模块列表
     * @param roleArr 角色ID，多个ID直接用，隔开
     * @return 没有权限的菜单列表
     */
	public List<ModuleInfo> getNotModuleByRole(String roleArr){
        if (StringUtils.isEmpty(roleArr)){
            return  new ArrayList<ModuleInfo>();
        }
        String hql = "";
        if(roleArr!=null && !roleArr.equals(""))
        {
            hql+="  moduleId not in (select moduleId from RoleModule where roleId in ("+roleArr+"))";
        }
        Order o = new Order(Direction.ASC,"orderIndex");
        Sort s = new Sort(o);
        return this.unDeleted().findAll(hql,s);
    }
	/**
	 * @Description: TODO(得到指定级别Module) 
	 * @param  level 指定的级别
	 * @param  moduleMap 数据源
	 * @return Map<Integer,Module> 按级别过滤后的菜单
	 */
	public Map<Integer,ModuleInfo> findModuleLevelMap(Map<Integer,ModuleInfo> moduleMap,Integer level){	
		Map<Integer, ModuleInfo> revModuleMap = new TreeMap<Integer, ModuleInfo>();
		if(moduleMap!=null){
			//根据级别过滤moduleMap
			Set<Entry<Integer, ModuleInfo>> moduleMapSet= moduleMap.entrySet();	
			for (Entry<Integer, ModuleInfo> entry : moduleMapSet) {
				Integer moduleId=entry.getKey();
				ModuleInfo module=entry.getValue();
				if(level.intValue() == getModuleLevel(module,1,moduleMap)){
					revModuleMap.put(moduleId, module);
				}
			}
		}
		return revModuleMap;
	}
	
	/**
	 * @Title: getModuleLevel 
	 * @Description: TODO(得到Module级别)
	 * @param module 模版
	 * @return Integer    返回-1不在级别树中
	 */
	private Integer getModuleLevel(ModuleInfo module,Integer initLevel,Map<Integer,ModuleInfo> moduleMap){

		Integer parentId=module.getParentId();
		if(parentId!=null){
			ModuleInfo obj=moduleMap.get(parentId);
			if(obj!=null){
				ModuleInfo revModule=obj;
				return getModuleLevel(revModule,initLevel+1,moduleMap);
			}
		}else{
			initLevel=-1;
		}
	    return initLevel;
	}
	
	/**
	 * 
	 * @Title: findModuleMap 
	 * @Description: TODO(得到指定角色的菜单Id和菜单对象的映射) 
	 * @param sysName 系统名称
	 * @param roleId 角色ID
	 * @return Map<Integer,Module>  key值是角色ID,value是菜单对象
	 */
	public Map<Integer,ModuleInfo> findModuleMap(String sysName,Integer roleId){
		Order o = new Order(Direction.ASC,"orderIndex");
        Sort s = new Sort(o);
		//得到角色模版
		List<ModuleInfo> moduleOwnList=this.getModuleByRole(roleId.toString());
		//得到模版
		List<ModuleInfo> moduleList=super.unDeleted().findAll("",s);

		Map<Integer,ModuleInfo> moduleOwnMap=new TreeMap<Integer, ModuleInfo>();
		if(moduleOwnList!=null){
			for (ModuleInfo module : moduleOwnList) {
				Integer moduleId=module.getModuleId();
				moduleOwnMap.put(moduleId,module);
			}
		}
		Map<Integer,ModuleInfo> moduleMap=new TreeMap<Integer, ModuleInfo>();
		if(moduleOwnMap!=null){
			if(moduleList!=null){
				for (ModuleInfo module : moduleList) {
					Integer moduleId=module.getModuleId();
					if(moduleOwnMap.get(moduleId)!=null){
						module.setIsSelected(true);
					}else{
						module.setIsSelected(false);
					}
					moduleMap.put(moduleId,module);
				}
			}
		}
		return moduleMap;
	}
	/* 
	 * 查询所有未删除的菜单
	 * @see cn.com.qytx.platform.base.dao.BaseDao#findAll()
	 */
	@Override
	public List<ModuleInfo> findAll() {
		/*Order order = new Order(Direction.ASC,"orderIndex");
		Sort sort = new Sort(order);
		return super.unDeleted().findAll("",sort);*/
		return entityManager.createQuery("from ModuleInfo where isDelete = 0 order by orderIndex asc").getResultList();
	}
	
	/**
     * 根据模块类型id模块列表
     * @param sysName
     * @return 菜单列表
     */
	public List<ModuleInfo> getModuleBySysName(String sysName){
        String hql = "";
        if(StringUtils.isNotBlank(sysName))
        {
            hql+="  sysName='"+sysName+"'";
        }
        Order o = new Order(Direction.ASC,"orderIndex");
        Sort s = new Sort(o);
        return this.unDeleted().findAll(hql,s);
    }
	
}
