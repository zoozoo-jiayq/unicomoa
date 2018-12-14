package cn.com.qytx.cbb.org.action;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;

/**
 * 功能:删除部门 
 * <br/>版本: 1.0 
 * <br/>开发人员: 任 
 * <br/>创建日期: 2013-4-8
 * <br/>修改日期: 2013-4-8
 * <br/>修改列表:
 */
public class GroupDeleteAction extends BaseActionSupport
{

	/** 系统日志接口 */
	@Resource
    private ILog logService;
	
    /**
     * 部门/群组管理接口
     */
    @Resource
    private IGroup groupService;

    private Integer groupId;

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
    }

    /**
     * 删除部门/群组
     * @return
     */
    public String deleteGroup()
    {
        // 得到是否有子组
        boolean isHasChild = groupService.isHasChild(groupId);
        GroupInfo group = groupService.findOne(groupId);
        if (isHasChild)
        {
            ajax(2);
            return null;
        }
        //如果是部门则判断下面是否含有成员
        if(group.getGroupType().intValue() == GroupInfo.DEPT){
        	// 判断部门下是否含有成员
        	boolean isHasGroupUser = groupService.isHasUsers(getLoginUser().getCompanyId(),groupId);
        	
        	if (isHasGroupUser)
        	{
        		ajax(3);
        		return null;
        	}
        }
        groupService.deleteGroup(groupId);
        
      //系统日志
    	UserInfo userInfo = (UserInfo)getSession().getAttribute("adminUser");
		Log log = new Log();
		log.setCompanyId(userInfo.getCompanyId());
		log.setInsertTime(new Timestamp(new Date().getTime()));
		log.setIp(this.getRequest().getRemoteAddr());
		log.setIsDelete(0);
		log.setLogType(LogType.LOG_GROUP_DELETE);//对照LogType类中的常量修改
		log.setRefId(userInfo.getUserId());
		log.setRemark("部门删除成功");
		log.setUserId(userInfo.getUserId());
		log.setUserName(userInfo.getUserName());
		log.setType(0);//手动添加系统日志
		logService.saveOrUpdate(log);
		
        ajax(1);
        return null;
    }
}
