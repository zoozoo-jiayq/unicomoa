package cn.com.qytx.cbb.org.action;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;


/**
 * <br/>功能: 得到用户详情
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-4-10
 * <br/>修改日期: 2013-4-10
 * <br/>修改列表:
 */
public class UserViewAction extends BaseActionSupport {

    /**
     * 用户信息
     */
    @Resource(name = "userService")
    IUser userService;
    /**
     * 部门，群组管理接口
     */
    @Resource(name = "groupService")
    IGroup groupService;

    /**
     * 角色人员信息
     */
    @Resource(name = "roleUserService")
    IRoleUser roleUserService;
    
    @Resource(name="roleService")
    IRole roleService;

    private Integer userId;
   
    /**
     * 访问类型
     */
    private String type;
   
    /**
     * 访问来源
     */
    private String source;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取人员信息
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getUserById() {
        UserInfo user = userService.findOne(userId);
        if(user.getIsVirtual() != null && user.getIsVirtual() == 1 && (StringUtils.isEmpty(type) || !"view".equals(type))){
        	user = userService.findOne(user.getLinkId());
		}
        GroupInfo group = groupService.getGroupByUserId(getLoginUser().getCompanyId(), userId);   //获取部门信息
        int groupId = 0;
        if (group != null) {
            groupId = group.getGroupId();
        }
        this.getRequest().setAttribute("user", user);
        //得到部门
        if(group!=null){
        this.getRequest().setAttribute("groupName", group.getGroupName());
        }
        this.getRequest().setAttribute("groupId", groupId);
        //得到主角色
        List<RoleInfo> roleList = roleService.findRolesByUserId(user.getUserId(), 1);
        StringBuffer roleIds = new StringBuffer();
        StringBuffer roleNames = new StringBuffer();
        String rIds = "";
        String rNames="";
        for (RoleInfo roleInfo : roleList) {
            Integer roleId = roleInfo.getRoleId();
            String roleName = roleInfo.getRoleName();
            roleIds.append(roleId + ",");
            roleNames.append(roleName + ",");
        }
        if (StringUtils.isNotEmpty(roleIds.toString())) {
            rIds = StringUtils.substring(roleIds.toString(), 0, roleIds.toString().length() - 1);
        }
        if (StringUtils.isNotEmpty(roleNames.toString())) {
            rNames = StringUtils.substring(roleNames.toString(), 0, roleNames.toString().length() - 1);
        }
        this.getRequest().setAttribute("roleNames", rNames);
        this.getRequest().setAttribute("roleIds", rIds);
        //得到辅助角色
        List<RoleInfo> assistList = roleService.findRolesByUserId(user.getUserId(), 0);
        StringBuffer assistIds = new StringBuffer();
        StringBuffer assistNames = new StringBuffer();
        String aIds = "";
        String aNames = "";
        for (RoleInfo roleInfo : assistList) {
            Integer roleId = roleInfo.getRoleId();
            String roleName = roleInfo.getRoleName();
            assistIds.append(roleId + ",");
            assistNames.append(roleName + ",");
        }
        if (StringUtils.isNotEmpty(assistIds.toString())) {
            aIds = StringUtils.substring(assistIds.toString(), 0, assistIds.toString().length() - 1);
        }
        if (StringUtils.isNotEmpty(assistNames.toString())) {
            aNames = StringUtils.substring(assistNames.toString(), 0, assistNames.toString().length() - 1);
        }
        this.getRequest().setAttribute("assistNames", aNames);
        this.getRequest().setAttribute("assistIds", aIds);

		// 返回查询页面
		if (!StringUtils.isEmpty(type) && "view".equals(type)){
		    return "view";
		}

		//当编辑虚拟联系人时返回虚拟页面
//		if(user.getIsVirtual() != null && user.getIsVirtual() == 1){
//			return "virtual";
//		}
		
		// 返回修改页面
		return SUCCESS;
    }

    private GroupInfo getGroupInfo(final List<GroupInfo> groupList) {
        GroupInfo group = null;
        for (GroupInfo info : groupList) {
            //类型为部门
            if (info.getGroupType() == 1) {
                group = info;
                break;
            }
        }
        return group;
    }
    
    
    /**
     * 获取人员信息
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public void ajaxUserById() {
        UserInfo user = userService.findOne(userId);
        GroupInfo group = groupService.getGroupByUserId(getLoginUser().getCompanyId(), userId);   //获取部门信息
        int groupId = 0;
        if (group != null) {
            groupId = group.getGroupId();
        }
        this.getRequest().setAttribute("user", user);
        //得到部门
        if(group!=null){
        this.getRequest().setAttribute("groupName", group.getGroupName());
        }
        this.getRequest().setAttribute("groupId", groupId);
        //得到主角色
        List<RoleInfo> roleList = roleService.findRolesByUserId(user.getUserId(), 1);
        StringBuffer roleIds = new StringBuffer();
        String rs="";
        StringBuffer roleNames = new StringBuffer();
        String rn="";
        for (RoleInfo roleInfo : roleList) {
            Integer roleId = roleInfo.getRoleId();
            String roleName = roleInfo.getRoleName();
            roleIds.append(roleId + ",");
            roleNames.append(roleName + ",");
        }
        if (StringUtils.isNotEmpty(roleIds.toString())) {
            rs = StringUtils.substring(roleIds.toString(), 0, roleIds.toString().length() - 1);
        }
        if (StringUtils.isNotEmpty(roleNames.toString())) {
            rn= StringUtils.substring(roleNames.toString(), 0, roleNames.toString().length() - 1);
        }
        this.getRequest().setAttribute("roleNames", rs);
        this.getRequest().setAttribute("roleIds", rn);
        //得到辅助角色
        List<RoleInfo> assistList = roleService.findRolesByUserId(user.getUserId(), 0);
        StringBuffer assistIds = new StringBuffer();
        StringBuffer assistNames = new StringBuffer();
        String aIds="";
        String aNames="";
        for (RoleInfo roleInfo : assistList) {
            Integer roleId = roleInfo.getRoleId();
            String roleName = roleInfo.getRoleName();
            assistIds.append(roleId + ",");
            assistNames.append(roleName + ",");
        }
        if (StringUtils.isNotEmpty(assistIds.toString())) {
            aIds = StringUtils.substring(assistIds.toString(), 0, assistIds.toString().length() - 1);
        }
        if (StringUtils.isNotEmpty(assistNames.toString())) {
            aNames = StringUtils.substring(assistNames.toString(), 0, assistNames.toString().length() - 1);
        }
        this.getRequest().setAttribute("assistNames", aNames);
        this.getRequest().setAttribute("assistIds", aIds);

//                ModulePriv modulePriv = this.modulePrivService.findByUserIdAndModuleName(this.userId, null);
//                String managementRange;
//                String appointGroupIds;
//                String appointGroupNames;

//                if (modulePriv != null) {
//                		managementRange=String.valueOf(modulePriv.getRangeType());
//                   if(ModulePrivConst.RANGE_TYPE_APPOINT_GROUP.equals(modulePriv.getRangeType())){
//                        appointGroupIds = modulePriv.getGroupIds();
//                        appointGroupNames = modulePriv.getGroupNames();
//                   }
//                }

        Map<String, Object> dataMap = new HashMap<String, Object>();
        // 登录名
        dataMap.put("loginName", user.getLoginName());
        // 用户状态
        dataMap.put("userState", user.getUserState());
        // 用户ID
        dataMap.put("id", user.getUserId());
        // 部门名称
        if(group!=null){
        dataMap.put("groupName", group.getGroupName());
        }
        // 部门Id
        dataMap.put("groupId", groupId);
        // 角色
        dataMap.put("roleNames", roleNames);
        // 工号
        dataMap.put("workNo", user.getWorkNo());
        // 姓名
        dataMap.put("userName", user.getUserName());
        // 性别
        dataMap.put("sex", user.getSex());
        // 生日
        String birthDay = "";
        if(user.getBirthDay()!=null){
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	birthDay = format.format(user.getBirthDay());
        }
        dataMap.put("birthDay", birthDay);
        // 职务
        dataMap.put("job", user.getJob());
        // 头像
        dataMap.put("photo", user.getPhoto());
        // 联系电话phone2
        dataMap.put("phone2", user.getPhone2());
        // 手机号码
        dataMap.put("phone", user.getPhone());
        // 电子邮件
        dataMap.put("email", user.getEmail());
        // 部门/单位 显示级联信息
        String groupName = groupService.getGroupNamePathByUserId(user.getCompanyId(), user.getUserId());
        dataMap.put("groupPath", groupName);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(dataMap);
        ajax(jsonStr);
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

}
