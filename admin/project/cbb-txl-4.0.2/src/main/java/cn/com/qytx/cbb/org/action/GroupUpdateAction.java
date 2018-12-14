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
 * 
 * <br/>功能:更新部门
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-4-8
 * <br/>修改日期: 2013-4-8
 * <br/>修改列表:
 */
public class GroupUpdateAction extends BaseActionSupport {
	
	/** 系统日志接口 */
	@Resource
    private ILog logService;

	/**
     * 部门/群组管理接口
     */
    @Resource
    private IGroup groupService;
    @Resource(name = "groupUserService")
    cn.com.qytx.platform.org.service.IGroupUser groupUserService;
    
	private GroupInfo group;

	public GroupInfo getGroup() {
		return group;
	}

	public void setGroup(GroupInfo group) {
		this.group = group;
	}

	/**
     * 更新部门,群组
     * @return
     */
    public String updateGroup(){
		GroupInfo groupOld=groupService.findOne(group.getGroupId());
		if(groupOld!=null){
			String oldGroupName=groupOld.getGroupName();
			
	    	groupOld.setOrderIndex(group.getOrderIndex());
	    	groupOld.setGroupName(group.getGroupName());
	    	groupOld.setPhone(group.getPhone());
	    	groupOld.setParentId(group.getParentId());
	    	groupOld.setBranch(group.getBranch());
	    	groupOld.setDirectorId(group.getDirectorId());
	    	groupOld.setAssistantId(group.getAssistantId());
	    	groupOld.setTopDirectorId(group.getTopDirectorId());
	    	groupOld.setTopChangeId(group.getTopChangeId());
	    	groupOld.setFunctions(group.getFunctions());
	    	groupOld.setLastUpdateTime(new Date());
	    	
	    	if(group.getParentId()==null){
	    		groupOld.setParentId(0);
	    	}
	    	
	    	Integer parentId=group.getParentId();
	    	//判断部门名称是否重复
	    	if(!oldGroupName.equals(group.getGroupName())){
	    		boolean  isSame=groupService.isHasSameGroupName(group.getParentId(), group.getGroupName(),group.getGroupType(),getLoginUser().getCompanyId());
	        	if(isSame){
	                ajax(2);
	                return null;
	        	}
	    	}
	
	    	if(parentId==0){
	    		groupOld.setPath(String.valueOf(group.getGroupId()));
	    		groupOld.setGrade(0);
	    	}else{
	        	//得到父对象
	    		GroupInfo groupParent=groupService.findOne(parentId);
	    		if(groupParent!=null){
	    			//设置路径
	    			String pathParent = groupParent.getPath();
	    			if(pathParent!=null){
	    				groupOld.setPath(pathParent+group.getGroupId());
	    			}
	            	//设置级别
	    			Integer gradeParent=groupParent.getGrade();
	    			if(gradeParent!=null){
	    				groupOld.setGrade(gradeParent+1);
	    			}
	    		}
	    	}
	    	groupService.addOrUpdateGroup(groupOld);
	    	//系统日志
	    	UserInfo userInfo = (UserInfo)getSession().getAttribute("adminUser");
			Log log = new Log();
			log.setCompanyId(userInfo.getCompanyId());
			log.setInsertTime(new Timestamp(new Date().getTime()));
			log.setIp(this.getRequest().getRemoteAddr());
			log.setIsDelete(0);
			log.setLogType(LogType.LOG_GROUP_UPDATE);//对照LogType类中的常量修改
			log.setRefId(userInfo.getUserId());
			log.setRemark("部门修改成功");
			log.setUserId(userInfo.getUserId());
			log.setUserName(userInfo.getUserName());
			log.setType(0);//手动添加系统日志
			logService.saveOrUpdate(log);
	    	
	    	ajax(1);
		}
		return null;
    }
}
