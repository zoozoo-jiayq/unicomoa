package cn.com.qytx.cbb.org.action;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;

/**
 * 
 * <br/>功能:添加部门
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-4-8
 * <br/>修改日期: 2013-4-8
 * <br/>修改列表:
 */
public class GroupAddAction  extends BaseActionSupport {
	
	/** 系统日志接口 */
	@Resource
    private ILog logService;
	
    /**
     * 部门,群组管理接口
     */
    @Resource
    private IGroup groupService;
    
    @Autowired
    IGroupUser groupUserService;
    
	private GroupInfo group;

	public GroupInfo getGroup() {
		return group;
	}

	public void setGroup(GroupInfo group) {
		this.group = group;
	}

	/**
     * 添加部门,群组
     * @return
     */
    public String addGroup()
    {
    	Integer userId=getLoginUser().getUserId();
    	Integer companyId=getLoginUser().getCompanyId();
    	group.setUserId(userId);
    	group.setCompanyId(companyId);
    	group.setIsDelete(0);
    	group.setLastUpdateTime(new Date());
    	if(group.getParentId()==null){
    		group.setParentId(0);
    	}
    	boolean  isSame=false;
    	if(group!=null && group.getGroupType()!=null && group.getGroupType().intValue() == 5){//个人群组
    		isSame = groupService.isHasSameGroupNameForSet(group.getParentId(), group.getGroupName(),group.getGroupType(),companyId,userId);
    	}else{
    		isSame = groupService.isHasSameGroupName(group.getParentId(), group.getGroupName(),group.getGroupType(),companyId);
    	}
    	if(isSame){
            ajax(2);
            return null;
    	}
    	//groupService.addOrUpdateGroup(group);
    	Integer groupId=group.getParentId();
    	if(groupId==0){
//    		group.setPath(String.valueOf(group.getGroupId()));
    		group.setGrade(0);
    	}else{
        	//得到父对象
    		GroupInfo groupParent=groupService.findOne(groupId);
    		if(groupParent!=null){
            	//设置级别
    			Integer gradeParent=groupParent.getGrade();
    			if(gradeParent!=null){
    				group.setGrade(gradeParent+1);
    			}
    		}
    	}
    	if(group.getOrderIndex()==null){
    		int maxOrder = groupService.getMaxOrderIndex(companyId, group.getParentId(),GroupInfo.DEPT);
    		group.setOrderIndex(maxOrder+1);
    	}
    	groupService.addGroup(group);
    	
    	if(group.getGroupType() == 5){//新增个人群组的时候需要添加个人进入个人群组
    		GroupUser groupUser = new GroupUser();
    		groupUser.setUserId(userId);
    		groupUser.setCompanyId(companyId);
    		groupUser.setGroupId(group.getGroupId());
    		groupUserService.saveOrUpdate(groupUser);
    	}
    	
    	//系统日志
    	UserInfo userInfo = (UserInfo)getSession().getAttribute("adminUser");
		Log log = new Log();
		log.setCompanyId(userInfo.getCompanyId());
		log.setInsertTime(new Timestamp(new Date().getTime()));
		log.setIp(this.getRequest().getRemoteAddr());
		log.setIsDelete(0);
		log.setLogType(LogType.LOG_GROUP_ADD);//对照LogType类中的常量修改
		log.setRefId(userInfo.getUserId());
		log.setRemark("部门添加成功");
		log.setUserId(userInfo.getUserId());
		log.setUserName(userInfo.getUserName());
		log.setType(0);//手动添加系统日志
		logService.saveOrUpdate(log);
    	
        ajax(1);
        return null;
    }
}
