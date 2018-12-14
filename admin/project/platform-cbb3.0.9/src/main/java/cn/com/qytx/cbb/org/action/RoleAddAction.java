package cn.com.qytx.cbb.org.action;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.service.IRole;


/**

 * @Description:  [增加角色信息ACTION]   
 * @Author:       [REN]   
 * @CreateDate:   [2012-10-12 上午09:31:32]   
 * @UpdateUser:   [REN]   
 * @UpdateDate:   [2012-10-12 上午09:31:32]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */
public class RoleAddAction  extends BaseActionSupport{
	/**角色信息*/
	@Resource(name = "roleService")
	IRole roleService;
	
    private RoleInfo roleInfo; 


	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

    /**
     * 设置角色（黄普友）
     * @param roleInfo 角色信息
     */
	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}


	/**
	 * 
	 * @Title: addRole
	 * @Description: TODO(添加角色信息) 
	 * @param
	 * @return String    返回类型
	 */
	public String addRole(){
		//保存角色信息
		if(roleInfo!=null){
		    String roleName=roleInfo.getRoleName();
			if(roleName!=null){
                if(roleService.isExistRoleName(roleName)){
                    ajax(3);
                    return null;
                }
            }
			
			// 随机生成rolecode
			roleInfo.setRoleCode(roleName+(int)(100 *Math.random()*1D));
			
			String roleCode=roleInfo.getRoleCode();
			if(roleCode!=null){
				if(roleService.isExistRoleCode(roleCode)){
					ajax(2);
					return null;
				}
			}
			roleInfo.setOrderIndex(0);
			roleInfo.setRoleType(1);//0,系统默认1,非系统默认
			roleInfo.setIsDelete(0);
			roleInfo.setCompanyId(getLoginUser().getCompanyId());
			roleInfo.setIsForkGroup(getLoginUser().getIsForkGroup());
			roleService.saveOrUpdate(roleInfo);
			ajax(1);
		}
		return null;
	}
}
