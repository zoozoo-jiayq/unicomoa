package cn.com.qytx.cbb.org.action;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.service.IRoleUser;
/**
 * Simple to Introduction   
 * @Description:  [角色人员ACTION]   
 * @Author:       [REN]   
 * @CreateDate:   [2012-10-16 上午11:01:06]   
 * @UpdateUser:   [REN]   
 * @UpdateDate:   [2012-10-16 上午11:01:06]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */

public class RoleUserAddAction  extends BaseActionSupport{
	
	/**角色人员信息*/
	@Resource(name = "roleUserService")
	IRoleUser roleUserService;
	
	/**角色id*/
	private Integer roleId;
	/**人员id列表*/
	private Integer[] userIds;
	/**角色类型*/
	private Integer type;


	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Integer[] userIds) {
		this.userIds = userIds;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 
	 * @Title: addRoleUser
	 * @Description: TODO(增加角色人员) 
	 * @return String    返回类型
	 */
	public String addRoleUser(){
		if (null == userIds){
		    userIds = new Integer[]{};
		}
		roleUserService.saveRoleUsersByUserIds(roleId, userIds, getLoginUser().getCompanyId(), type, true);
		ajax(1);
		return null;
	}
	
}
