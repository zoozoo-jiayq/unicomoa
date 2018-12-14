package cn.com.qytx.cbb.org.action;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.service.IRoleModule;
/**
 * Simple to Introduction   
 * @Description:  [角色模版ACTION]   
 * @Author:       [REN]   
 * @CreateDate:   [2012-10-16 上午11:01:06]   
 * @UpdateUser:   [REN]   
 * @UpdateDate:   [2012-10-16 上午11:01:06]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */

public class RoleModuleAddAction  extends BaseActionSupport{
	
	/**角色模版信息*/
	@Resource(name = "roleModuleService")
	IRoleModule roleModuleService;
	
	/**角色id*/
	private Integer roleId;

	/**模版id列表*/
	private Integer[] moduleIds;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer[] getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(Integer[] moduleIds) {
		this.moduleIds = moduleIds;
	}


	/**
	 * 
	 * @Title: addRoleModule
	 * @Description: TODO(增加角色模版) 
	 * @return String    返回类型
	 */
	public String addRoleModule(){
		if(roleId!=null){
			roleModuleService.saveRoleModules(roleId,moduleIds);
		}
		ajax(1);
		return null;
	}
	
}
