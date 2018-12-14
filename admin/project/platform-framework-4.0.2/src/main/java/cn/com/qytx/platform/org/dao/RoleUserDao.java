package cn.com.qytx.platform.org.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.org.domain.RoleUser;

/**   
 * 项目名称：wzerp   
 * 类名称：RoleUserDao   
 * 类描述：  人员角色关系操作Dao 
 * 创建人：CQL   
 * 创建时间：2012-10-8 下午07:06:34   
 * @version      
 */
@Repository("roleUserDao")
public class RoleUserDao <T extends RoleUser> extends BaseDao<RoleUser, Integer> implements Serializable{
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 判断人员是否具有指定的角色
	 * @param roleId 角色id
	 * @param userId 用户id
	 * @return Boolean ，true有此角色,false无此角色
	 */
	public Boolean isExistRoleUser(Integer roleId,Integer userId){
		String hql="from  "+this.getEntityClass().getName()+" where roleId=? and userId=?";
		Object roleUserObj=super.findUnique(hql, roleId,userId);
		if(roleUserObj!=null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 删除指定的角色的所有关系 
	 * @param  roleId  角色id
	 * @param  type  类型
	 * @return void    返回类型
	 */
	public void deleteUsersByRoleId(Integer roleId,Integer type){
		String hql="delete from  "+this.getEntityClass().getSimpleName()+" where roleId=? and type=?";
		super.bulkDelete(hql, roleId,type);
	}
	/**
	 * 删除指定人员的角色关系 
	 * @param  userId  用户id
	 * @param  type  类型
	 * @return void    返回类型
	 */
	public void deleteRolesByUserId(Integer userId,Integer type){
		String hql="delete from  "+this.getEntityClass().getName()+" where userId=? and type=?";
		super.bulkDelete(hql, userId,type);
	}
	
	/**
	 * 根据用户获取用户关联的所有角色
	 * @param userid 用户ID
	 * @return List<RoleUser> 角色列表
	 */
	public List<RoleUser> getRoleUser(String userid)
	{
		String hql="from "+this.getEntityClass().getName()+" where userId="+userid+"and type=1";
		return super.findAll(hql);
	}
	
	/**
	 * 功能：根据用户ID删除用户角色关系
	 * @param userIds 用户ID集合
	 */
	public void deleteRoleUsersByUserId(String userIds){
		String hql = "delete from "+this.getEntityClass().getSimpleName()+" where userId in ("+userIds+")";
		super.bulkDelete(hql);
	}
	
	/**
	 * 根据公司id和角色id获得角色人员关联信息
	 */
	public List<RoleUser> getRoleUserByRoleIdCompanyId(int companyId,int roleId){
		return this.findAll(" roleId = ? and companyId = ? ",roleId,companyId);
	}
	
	/**
	 * 功能：判断该角色下是否存在用户
	 * @param roleId 角色ID集合
	 */
	public boolean isExistRoleUserByRoleId(Integer roleId[]){
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < roleId.length;i++){
			if(i != roleId.length-1){
				sb.append(roleId[i]+",");
			}else{
				sb.append(roleId[i]);
			}
		}
		String hql = "roleId in ("+sb.toString()+")";
		List list = this.findAll(hql);
		boolean isExistRoleUser = false;
		if(list != null && list.size() > 0){
			isExistRoleUser = true;
		}
		return isExistRoleUser;	
	}
	/**
	 * 删除人员对应的角色
	 * @param userId
	 * @param roleCode
	 */
	public void deleteRoleUserByRoleCode(Integer userId,Integer companyId, String roleCode) {
		String sql="delete from tb_role_user where user_Id=? and company_id=? and role_id in "+
				"(select role_id from tb_role_info where role_code=? and company_id=?)";
		Query query=this.entityManager.createNativeQuery(sql);
		query.setParameter(1, userId);
		query.setParameter(2, companyId);
		query.setParameter(3, roleCode);
		query.setParameter(4, companyId);
		query.executeUpdate();
	}
}
