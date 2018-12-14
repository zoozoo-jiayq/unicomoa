package cn.com.qytx.cbb.org.action;



import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.service.IRole;

/**
 * @Description:  [角色详细ACTION]   
 * @Author:       [REN]   
 * @CreateDate:   [2012-10-12 上午09:31:32]   
 * @UpdateUser:   [REN]   
 * @UpdateDate:   [2012-10-12 上午09:31:32]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */
public class RoleViewAction  extends BaseActionSupport{
	/**角色信息*/
	@Resource(name = "roleService")
	IRole roleService;
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 
	 * @Title: loadRoleView 
	 * @Description: TODO(得到角色详细) 
	 * @param
	 * @return String    返回类型
	 */
	public String loadRoleView(){
		RoleInfo role=roleService.findOne(id);
		this.getRequest().setAttribute("role",role);
		return SUCCESS;
	}
	/**
	 * 
	 * @Title: loadRoleUpdateView 
	 * @Description: TODO(得到更新角色详细) 
	 * @param
	 * @return String    返回类型
	 */
	public String loadRoleUpdateView(){
		RoleInfo role=roleService.findOne(id);
		this.getRequest().setAttribute("role",role);
		return SUCCESS;
	}
}
