package cn.com.qytx.cbb.org.action;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.service.IRole;

/**
 * @Description: [编辑角色信息ACTION]
 * @Author: [REN]
 * @CreateDate: [2012-10-12 上午09:31:32]
 * @UpdateUser: [REN]
 * @UpdateDate: [2012-10-12 上午09:31:32]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 */
public class RoleUpdateAction extends BaseActionSupport
{
    /** 角色信息 */
    @Resource(name = "roleService")
    IRole roleService;

    private RoleInfo role;

    public RoleInfo getRole()
    {
        return role;
    }

    public void setRole(RoleInfo role)
    {
        this.role = role;
    }

    /**
     * @Title: updateRole
     * @Description: TODO(编辑角色信息)
     * @param
     * @return String 返回类型
     */
    public String updateRole()
    {
        // 编辑角色信息
        if (role != null && role.getRoleId() != null)
        {
            // 得到老的角色代码
            RoleInfo updateRole = roleService.findOne(role.getRoleId());
            
            // 角色名唯一
            String roleName = role.getRoleName();
            String oldRoleName = updateRole.getRoleName();
            if (oldRoleName != null && roleName != null
                    && !oldRoleName.equalsIgnoreCase(roleName))
            {
                if (roleService.isExistRoleName(roleName))
                {
                    ajax(3);
                    return null;
                }
            }

            String oldRoleCode = updateRole.getRoleCode();
            // 角色代码唯一
            String roleCode = role.getRoleCode();
            if (oldRoleCode != null && roleCode != null
                    && !oldRoleCode.equalsIgnoreCase(roleCode))
            {
                if (roleService.isExistRoleCode(roleCode))
                {
                    ajax(2);
                    return null;
                }
            }
            updateRole.setRoleName(role.getRoleName());
            updateRole.setRoleCode(role.getRoleCode());
            updateRole.setMemo(role.getMemo());
            updateRole.setIsForkGroup(getLoginUser().getIsForkGroup());
            roleService.saveOrUpdate(updateRole);
            ajax(1);
        }
        return null;
    }
}
