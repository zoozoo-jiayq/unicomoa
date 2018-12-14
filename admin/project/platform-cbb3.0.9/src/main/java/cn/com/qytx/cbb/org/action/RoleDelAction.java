package cn.com.qytx.cbb.org.action;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
/**
 * Simple to Introduction   
 * @Description:  [删除角色ACTION]   
 * @Author:       [REN]   
 * @CreateDate:   [2012-10-16 上午11:01:06]   
 * @UpdateUser:   [REN]   
 * @UpdateDate:   [2012-10-16 上午11:01:06]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */

public class RoleDelAction  extends BaseActionSupport{
	
	/**角色信息*/
	@Resource(name = "roleService")
	IRole roleService;
	/**用户角色信息*/
	@Resource(name = "roleUserService")
	IRoleUser roleUserService;
	
	/**角色id列表*/
	private Integer[] roleIds;

	public Integer[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}


	/**
	 * 
	 * @Title: deleteRole 
	 * @Description: TODO(删除角色) 
	 * @return String    返回类型
	 */
	public String deleteRole(){
		if(roleIds!=null&&roleIds.length>0){
			boolean isExistUser = roleUserService.isExistRoleUserByRoleId(roleIds);
			if(isExistUser){
				ajax(2);
				return null;
			}
			for (int i = 0; i < roleIds.length; i++) {
				roleService.delete(roleIds[i],false);
			}
		}
		ajax(1);
		return null;
	}
	
}
