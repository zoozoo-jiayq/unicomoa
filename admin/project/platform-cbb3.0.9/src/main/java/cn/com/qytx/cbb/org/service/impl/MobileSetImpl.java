package cn.com.qytx.cbb.org.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.groupExt.impl.EventForAddUserToGroup;
import cn.com.qytx.cbb.groupExt.impl.EventForDeleteUserToGroup;
import cn.com.qytx.cbb.org.service.MobileSetService;
import cn.com.qytx.platform.event.PublishEventUtil;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * <br/>功能: 手机端通讯录群组人员管理接口 
 * <br/>版本: 1.0
 * <br/>开发人员: zyf
 * <br/>创建日期: 2015年5月18日
 * <br/>修改日期: 2015年5月18日
 * <br/>修改列表:
 */
@Service("mobileSetService")
@Transactional
public class MobileSetImpl implements MobileSetService {
	
	/**
	 * 群组人员信息实现类
	 */
	@Resource(name = "groupUserService")
    IGroupUser groupUserService; 
	
	/**
	 * 部门/群组管理接口
	 */
	@Resource(name = "groupService")
    private IGroup groupService; 
	
	/**
	 * 功能：新增群组人员
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param groupId 群组ID
	 * @param userIds 人员ID列表，多个人员ID之间,隔开
	 * @return
	 */
	public String addSetUsers(Integer companyId, Integer userId, String userName, Integer groupId, String userIds) throws Exception {
        //首先删除对应关系
        groupUserService.deleteGroupUserByUserIds(companyId,groupId,userIds);
        //把操作人添加到部门/群组里面
        GroupUser masterUser=groupUserService.findOne("companyId=?1 and groupId=?2 and userId=?3",companyId,groupId,userId);
        if(masterUser==null)
        {
            GroupUser groupUser=new GroupUser();
            groupUser.setCompanyId(companyId);
            groupUser.setGroupId(groupId);
            groupUser.setUserId(userId);
            groupUserService.saveOrUpdate(groupUser);
        }
        String[] arr=userIds.split(",");
        for(int i=0;i<arr.length;i++)
        {
        	Integer uId=Integer.parseInt(arr[i]);
        	//操作人员不重复添加
        	if(userId==uId.intValue()){
        		continue;
        	}
            GroupUser groupUser=new GroupUser();
            groupUser.setCompanyId(companyId);
            groupUser.setGroupId(groupId);
            groupUser.setUserId(uId);
            groupUserService.saveOrUpdate(groupUser);
        }
        //更新部门变更时间
        GroupInfo groupInfo=groupService.findOne(groupId);
        if(groupInfo!=null)
        {
            groupInfo.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(),"yyyy-MM-dd HH:mm:ss"));
            groupService.saveOrUpdate(groupInfo);
        }
        String temuserIds="";
        List<GroupUser> list=groupUserService.findAll("groupId=?1 and companyId=?2",groupId,companyId);
        if(list!=null)
        {
             for(GroupUser user:list)
             {
            	 if(temuserIds.indexOf(","+user.getUserId()+",") <0 && temuserIds.indexOf(user.getUserId()+",")!=0)
            		 temuserIds+=user.getUserId()+",";
             }
        }
        if(StringUtils.isNotBlank(temuserIds))
        {
            if(temuserIds.endsWith(","))
            {
            	temuserIds=temuserIds.substring(0,temuserIds.length()-1);
            }
        }
        
        //发布群组新增人员广播
        PublishEventUtil.publishEvent(new EventForAddUserToGroup(companyId));
        return temuserIds;
    }
	
	/**
	 * 功能：删除群组人员
	 * @param companyId 单位ID
	 * @param userId 操作人ID
	 * @param userName 操作人姓名
	 * @param groupId 群组ID
	 * @param userIds 人员ID列表，多个人员ID之间,隔开
	 * @return
	 */
	public String deleteSetUsers(Integer companyId, Integer userId, String userName, Integer groupId, String userIds) throws Exception {
        //更新部门变更时间
        GroupInfo groupInfo=groupService.findOne(groupId);
        if(groupInfo!=null && groupInfo.getGroupType().intValue()==5){//如果是个人群组，则需判断不能删除创建人
        	String createUserId = groupInfo.getUserId().toString();
        	
        	if(!userIds.endsWith(",")){
        		userIds+=",";
        	}
        	if(!userIds.startsWith(",")){
        		userIds = ","+userIds;
        	}
        	
        	userIds = userIds.replace(","+createUserId+",", ",");
        	
        	if(userIds.equals(",")){
        		userIds = "";
        	}
        }
        
        //首先删除对应关系
        groupUserService.deleteGroupUserByUserIds(companyId,groupId,userIds);

        if(groupInfo!=null)
        {
            groupInfo.setLastUpdateTime(DateTimeUtil.stringToDate(DateTimeUtil.getCurrentTime(),"yyyy-MM-dd HH:mm:ss"));
            groupService.saveOrUpdate(groupInfo);
        }

        String temuserIds="";
        List<GroupUser> list=groupUserService.findAll("groupId=?1 and companyId=?2",groupId,companyId);
        if(list!=null)
        {
            for(GroupUser user:list)
            {
            	if(temuserIds.indexOf(","+user.getUserId()+",") <0 && temuserIds.indexOf(user.getUserId()+",")!=0)
            	temuserIds+=user.getUserId()+",";
            }
        }
        if(StringUtils.isNotBlank(temuserIds))
        {
            if(temuserIds.endsWith(","))
            {
            	temuserIds=temuserIds.substring(0,temuserIds.length()-1);
            }
        }
        //发布群组删除人员广播
        PublishEventUtil.publishEvent(new EventForDeleteUserToGroup(companyId));
        
        return temuserIds;
    }
}
