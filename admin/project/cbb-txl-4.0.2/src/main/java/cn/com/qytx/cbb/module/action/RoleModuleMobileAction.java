package cn.com.qytx.cbb.module.action;

import java.util.List;

import javax.annotation.Resource;

import cn.com.qytx.cbb.module.domain.RoleModuleMobile;
import cn.com.qytx.cbb.module.service.IRoleModuleMobile;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.utils.JsonUtils;
/**
 * 手机用户角色action
 * @author liyanlei
 *
 */
public class RoleModuleMobileAction extends BaseActionSupport{
	
	@Resource(name="roleModuleMobileImpl")
	private IRoleModuleMobile roleModuleMobileService;
	
	private Integer roleId;
	private Integer[] moduleIds;
	
	/**
	 * 用户权限
	 * @return
	 */
	public String getModuleList(){
		List<RoleModuleMobile> list = roleModuleMobileService.getModuleList(roleId);
		ajax(JsonUtils.generJsonString(list));
		return null;
	}
	/**
	 * 更新用户权限
	 * @return
	 */
	public String update(){
		roleModuleMobileService.updateModuleList(moduleIds,roleId);
		return null;
	}
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
	
	
}
