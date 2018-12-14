package cn.com.qytx.platform.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.RoleUserDao;
import cn.com.qytx.platform.org.domain.RoleUser;
import cn.com.qytx.platform.org.service.IRoleUser;

/**   
 * 项目名称：wzerp   
 * 类名称：RoleUserImpl   
 * 类描述：   
 * 创建人：CQL   
 * 创建时间：2012-10-8 下午08:32:47   
 * @version      
 */
@Transactional
@Service("roleUserService")
public class RoleUserImpl extends BaseServiceImpl<RoleUser> implements IRoleUser {

	@Resource(name="roleUserDao")
	private RoleUserDao<RoleUser> roleUserDao;

	
	/**
	 * @Title: isExistRoleUser 
	 * @Description: TODO(是否存在角色用户) 
	 * @param roleId 角色id
	 * @param userId 用户id
	 * @return Boolean    返回类型
	 */
	public Boolean isExistRoleUser(Integer roleId,Integer userId){
		return roleUserDao.isExistRoleUser(roleId, userId);
	}
	/**
	 * 
	 * 功能：保存角色成员
	 * @param roleId 角色
	 * @param userIdArr 用户数组 
	 * @param companyId 公司id
	 * @param type 类型1主角色 ,类型0辅助角色
	 * @param isDelete 等于true删除以前的角色对应成员
	 */
	public void saveRoleUsersByUserIds(Integer roleId,Integer[] userIdArr, Integer companyId,Integer type,Boolean isDelete){
		    if(isDelete){
				//对应角色成员全部删除
				roleUserDao.deleteUsersByRoleId(roleId,type);
		    }
		    List<RoleUser> list = new ArrayList<RoleUser>();
			for (int i = 0; i < userIdArr.length; i++) {
					RoleUser roleUser = new RoleUser();
					roleUser.setRoleId(roleId);
					roleUser.setUserId(userIdArr[i]);
					roleUser.setCompanyId(companyId);
					roleUser.setType(type);
					list.add(roleUser);
			}
			roleUserDao.saveOrUpdate(list);
	}
	/**
	 * 
	 * 功能：保存角色成员
	 * @param roleIdArr 角色数组
	 * @param userId 用户id
	 * @param companyId 公司id
	 * @param type 类型1主角色 ,类型0辅助角色
	 * @param isDelete
	 */
	public void saveRoleUsersByRoleIds(Integer[] roleIdArr,Integer userId, Integer companyId,Integer type,Boolean isDelete){
			if(isDelete){
				//对应角色成员全部删除
				roleUserDao.deleteRolesByUserId(userId,type);
			}
			if (null != roleIdArr){
			    for (int i = 0; i < roleIdArr.length; i++) {
					RoleUser roleUser = new RoleUser();
					roleUser.setRoleId(roleIdArr[i]);
					roleUser.setUserId(userId);
					roleUser.setCompanyId(companyId);
					roleUser.setType(type);
					roleUserDao.saveOrUpdate(roleUser);
                }
			}
	}
	@Override
	public void deleteRoleUserByUserIds(String userIds) {
		// TODO Auto-generated method stub
		roleUserDao.deleteRoleUsersByUserId(userIds);
	}
	
	/**
	 * 功能：判断该角色下是否存在用户
	 * @param roleId 角色ID集合
	 */
	public boolean isExistRoleUserByRoleId(Integer roleId[]){
		return roleUserDao.isExistRoleUserByRoleId(roleId);
	}
	@Override
	public void deleteRoleUserByRoleCode(Integer userId,Integer companyId, String roleCode) {
		roleUserDao.deleteRoleUserByRoleCode(userId,companyId,roleCode);
		
	}
}
