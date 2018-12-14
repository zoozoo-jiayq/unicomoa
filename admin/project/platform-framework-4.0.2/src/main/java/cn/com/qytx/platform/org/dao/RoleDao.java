package cn.com.qytx.platform.org.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.base.BaseRoleUser;

/**
 * 模块数据库操作类
 * User: 黄普友
 * Date: 13-2-19
 * Time: 下午4:24
 */
@Repository("roleDao")
public class RoleDao <T extends RoleInfo> extends BaseDao<RoleInfo,Integer> implements Serializable{

    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;


	/**
     * 获取角色列表
     * @param companyId 企业ID
     * @return List<RoleInfo> 角色列表
     */
    public List<RoleInfo> getRoleList()
    {
        String hql="isDelete=0 ";
        Order o = new Order(Direction.ASC,"orderIndex");
        Sort s = new Sort(o);
        return this.findAll(hql,s);
    }
    /**
     * 根据人员ID获取该人员角色列表
     * @param userId 人员ID
     * @return List<T>
     */
    @SuppressWarnings("unchecked")
	public List<RoleInfo> getRoleByUser(int userId,Class<BaseRoleUser> roleUser)
    {
        String hql = "isDelete = 0 and roleId in (select roleId from "+roleUser.getName()+" where userId = ?)";
        return this.findAll(hql,userId);
    }
    
	/**
	 * @Title: isExistRoleCode 
	 * @Description: (是否存在角色代码) 
	 * @param roleCode 角色代码
	 * @return Boolean  true存在该角色，false不存在该角色
	 */
	public Boolean isExistRoleCode(String roleCode){
		String hql="from RoleInfo where roleCode=? and isDelete=0";
		List list = super.find(hql, roleCode);
		if(list!=null && list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
    /**
   * @Title: isExistRoleCode 
   * @Description: (是否存在角色名称) 
   * @param roleCode 角色名称
   * @return Boolean true存在该角色名称,false不存在该角色名称
   */
  public Boolean isExistRoleName(String roleName){
      String hql="roleName=? and isDelete=0";
      List roleObj=super.findAll(hql, roleName);
      if(roleObj!=null && roleObj.size()>0){
          return true;
      }else{
          return false;
      }
  }
	
	/**
	 * @Title: findByPage 
	 * @Description: TODO(角色分页列表) 
	 * @param page 分页查询对象
	 * @return Page 返回类型
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page findByPage(Pageable page){
        String hql=" isDelete=0";
        return super.companyId().findAll(hql, page);
	}
	
	/**
	 * 功能：根据用户ID获取角色列表
	 * @param userId 角色Id
	 * @param type 角色类型
	 * @return List<RoleInfo> 角色列表
	 */
	public List<RoleInfo> findRolesByUserId(Integer userId,Integer type){
		String hql = "roleId in (select roleId from RoleUser where userId = ? and type = ?) ";
		return super.findAll(hql, userId,type);
	}
	
	/**
	 * 根据角色代码查找角色
	 * @param roleCode 角色代码
	 * @return RoleInfo 角色对象
	 */
	public RoleInfo findByRoleCode(String roleCode){
		String hql = "from RoleInfo where roleCode = ? and isDelete = 0";
		return super.findUnique(hql, roleCode);
	}
	/**
	 * 根据角色代码查找角色
	 * @param companyId 公司ID
	 * @param roleCode 角色代码
	 * @return RoleInfo 角色对象
	 */
	public RoleInfo findByRoleCode(Integer companyId, String roleCode) {
		String hql = "from RoleInfo where companyId=? and roleCode = ? and isDelete = 0";
		return super.findUnique(hql, companyId,roleCode);
	}
   
}
