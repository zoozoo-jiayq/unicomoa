package cn.com.qytx.platform.org.service;

import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.RoleUser;

/**   
 * 项目名称：wzerp   
 * 类名称：IRoleUser   
 * 类描述：   
 * 创建人：CQL   
 * 创建时间：2012-10-8 下午07:10:45   
 * @version      
 */
public interface IRoleUser extends BaseService<RoleUser>{


	/**
	 * @Title: isExistRoleUser 
	 * @Description: TODO(是否存在角色用户) 
	 * @param roleId 角色id
	 * @param userId 用户id
	 * @return Boolean    返回类型
	 */
	public Boolean isExistRoleUser(Integer roleId,Integer userId);
	
	/**
	 * 
	 * 功能：保存角色成员
	 * @param roleId 角色
	 * @param userIdArr 用户数组 
	 * @param companyId 公司id
	 * @param type 类型1主角色 ,类型0辅助角色
	 * @param isDelete 等于true删除以前的角色对应成员
	 */
	public void saveRoleUsersByUserIds(Integer roleId,Integer[] userIdArr, Integer companyId,Integer type,Boolean isDelete);
	
	/**
	 * 
	 * 功能：保存角色成员
	 * @param roleIdArr 角色数组
	 * @param userId 用户id
	 * @param companyId 公司id
	 * @param type 类型1主角色 ,类型0辅助角色
	 * @param isDelete
	 */
	public void saveRoleUsersByRoleIds(Integer[] roleIdArr,Integer userId, Integer companyId,Integer type,Boolean isDelete);
	
	/**
	 * 根据用户ID删除角色用户的权限
	 * @param userIds 用户ID，以","隔开
	 */
	public void deleteRoleUserByUserIds(String userIds);
	
	/**
	 * 功能：判断该角色下是否存在用户
	 * @param roleId 角色ID集合
	 */
	public boolean isExistRoleUserByRoleId(Integer roleId[]);
	/**
	 * 删除人员对应的角色
	 * @param userId
	 * @param roleCode
	 */
	public void deleteRoleUserByRoleCode(Integer userId,Integer companyId,String roleCode);
}
