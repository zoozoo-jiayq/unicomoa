package cn.com.qytx.cbb.org.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;

/**
 * <br/>
 * 功能: 删除人员 <br/>
 * 版本: 1.0 <br/>
 * 开发人员: 任 <br/>
 * 创建日期: 2013-4-9 <br/>
 * 修改日期: 2013-4-9 <br/>
 * 修改列表:
 */
public class UserDeleteAction extends BaseActionSupport
{
	
	 /** 系统日志接口 */
	@Resource
    private ILog logService;
		
    /** 用户信息 */
    @Resource(name = "userService")
    IUser userService;

    @Resource(name = "groupUserService")
    IGroupUser groupUserService;

    /**
     * 角色人员信息
     */
    @Resource(name = "roleUserService")
    IRoleUser roleUserService;

    private String userIds = "";
    private int groupId;
    private boolean deleteFlag = false;
    
    /**
     * 删除人员
     * @return
     */
    public String deleteUser()
    {
        if (StringUtils.isNotEmpty(userIds))
        {
            if (userIds.endsWith(","))
            {
                userIds = userIds.substring(0, userIds.length() - 1);
            }
            if (userIds.startsWith(","))
            {
                userIds = userIds.substring(1, userIds.length());
            }
            userService.deleteUserByIds(userIds, deleteFlag,this.getLoginUser().getCompanyId());
            ajax("success");
            return null;
        }
        else
        {
            ajax("请选择要删除的人员！");
            return null;
        }
    }

    /**
     * 功能：删除登录用户信息,主要是清空登录名,密码,同时设置用户状态为2
     */
    public void deleteLoginUser(){
	    if (StringUtils.isNotEmpty(userIds))
	    {
	    	if (userIds.endsWith(","))
	        {
	            userIds = userIds.substring(0, userIds.length() - 1);
	        }
	        if (userIds.startsWith(","))
	        {
	            userIds = userIds.substring(1, userIds.length());
	        }
	        UserInfo userOld = userService.findOne(
	                Integer.parseInt(userIds));
	        userOld.setUserState(UserInfo.USERSTATE_UNLOGIN);
	
	        // 更新用户名和密码信息,保证用户不能再登陆
	        userOld.setLoginPass("");
	        userOld.setLoginName(UUID.randomUUID().toString());
	        userService.saveOrUpdate(userOld);
	
	        // 同时删除该用户配置的角色信息
	        roleUserService.saveRoleUsersByRoleIds(null, userOld.getUserId(),
	                userOld.getCompanyId(), 1, true);
	        
	        //系统日志添加
            UserInfo userInfo = (UserInfo)getSession().getAttribute("adminUser");
    		Log log = new Log();
    		log.setCompanyId(userInfo.getCompanyId());
    		log.setInsertTime(new Timestamp(new Date().getTime()));
    		log.setIp(this.getRequest().getRemoteAddr());
    		log.setIsDelete(0);
    		log.setLogType(LogType.LOG_USER_DELETE);//对照LogType类中的常量修改
    		log.setRefId(userInfo.getUserId());
    		log.setRemark("删除用户成功");
    		log.setUserId(userInfo.getUserId());
    		log.setUserName(userInfo.getUserName());
    		log.setType(0);//手动添加系统日志
    		logService.saveOrUpdate(log);
    		
	        ajax("success");
	    }
	    else
	    {
	        ajax("请选择要删除的人员！");
	    }
	}

    
    public String getUserIds()
    {
        return userIds;
    }

    public void setUserIds(String userIds)
    {
        this.userIds = userIds;
    }

	
	public int getGroupId() {
		return groupId;
	}

	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
}
