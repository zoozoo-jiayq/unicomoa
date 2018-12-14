package cn.com.qytx.platform.org.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.RoleDao;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.service.IRole;

/**
 * 用户管理实现类
 * User: 黄普友
 * Date: 13-2-16
 * Time: 下午3:00
 */
@Service("roleService")
@Transactional
public class RoleImpl extends BaseServiceImpl<RoleInfo> implements IRole {

	@Resource(name="roleDao")
    private RoleDao<RoleInfo> roleDao;
	
    @Override
    public List<RoleInfo> getRoleByUser(int userId) {
        return roleDao.findRolesByUserId(userId, 1);
    }
    /**
     * 获取角色列表
     * @param companyId 企业ID
     * @return
     */
    public List<RoleInfo> getRoleList()
    {
        return roleDao.getRoleList();
    }
	/**
	 * @Title: isExistRoleCode 
	 * @Description: TODO(是否存在角色代码) 
	 * @param roleCode 角色代码
	 * @return Boolean    返回类型
	 */
	public Boolean isExistRoleCode(String roleCode){
		return roleDao.isExistRoleCode(roleCode);
	}
	
    /**
   * @Title: isExistRoleCode 
   * @Description: (是否存在角色名称) 
   * @param roleCode 角色名称
   * @return Boolean    返回类型
   */
  public Boolean isExistRoleName(String roleName){
      return roleDao.isExistRoleName(roleName);
  }
  
	/**
	 * @Title: findByPage 
	 * @Description: TODO(角色分页列表) 
	 * @param page
	 * @return Page    返回类型
	 */
	public Page<RoleInfo> findByPage(Pageable page){
		return roleDao.findByPage(page);
	}
	@Override
	public List<RoleInfo> findRolesByUserId(Integer userId, Integer type) {
		// TODO Auto-generated method stub
		return roleDao.findRolesByUserId(userId, type);
	}
	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public RoleInfo saveOrUpdate(RoleInfo entity) {
		// TODO Auto-generated method stub
		return super.saveOrUpdate(entity);
	}
	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public List<RoleInfo> saveOrUpdate(Iterable<RoleInfo> entities) {
		// TODO Auto-generated method stub
		return super.saveOrUpdate(entities);
	}
	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public int delete(RoleInfo entity, boolean fromDB) {
		// TODO Auto-generated method stub
		return super.delete(entity, fromDB);
	}
	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public int delete(Integer id, boolean fromDB) {
		// TODO Auto-generated method stub
		return super.delete(id, fromDB);
	}
	@Override
	 @CacheEvict(value="userTreeCache",allEntries=true)
	public int deleteByIds(Iterable<Integer> ids, boolean fromDB) {
		// TODO Auto-generated method stub
		return super.deleteByIds(ids, fromDB);
	}
	@Override
	public RoleInfo findByRoleCode(String roleCode) {
		// TODO Auto-generated method stub
		return roleDao.findByRoleCode(roleCode);
	}
	@Override
	public RoleInfo findByRoleCode(Integer companyId, String roleCode) {
		// TODO Auto-generated method stub
		return roleDao.findByRoleCode(companyId,roleCode);
	}
	

}
