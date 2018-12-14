package cn.com.qytx.platform.org.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.org.domain.RoleModule;


/**
 * @Description:  [角色菜单权限表]   
 * @Author:       [Administrator]   
 * @CreateDate:   [2012-11-27 下午01:56:54]   
 * @UpdateUser:   [Administrator]   
 * @UpdateDate:   [2012-11-27 下午01:56:54]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */
@Repository("roleModuleDao")
public class RoleModuleDao <T extends RoleModule> extends BaseDao<RoleModule,Integer>  implements Serializable{
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Title: deleteModulesByRoleId 
	 * @Description: 删除指定角色的所有权限
	 * @param  roleId  角色id
	 * @return void    返回类型
	 */
	public void deleteModulesByRoleId(Integer roleId){
		String hql="delete from "+super.getEntityClass().getName()+" a where a.roleId=? ";
		super.bulkDelete(hql, roleId);
	}
	
}
