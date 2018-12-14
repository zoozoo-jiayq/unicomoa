package cn.com.qytx.cbb.notify.utils;

import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;

public class ColumnUtils {
	public static Integer getGroupColumnIdByUser(int userId){
		IGroup groupService = (IGroup) SpringUtil.getBean("groupService");
		IUser userService = (IUser) SpringUtil.getBean("userService");
		SysConfigService sysConfigService = (SysConfigService) SpringUtil.getBean("sysConfigService");
		UserInfo user = userService.findOne(userId);
		if(user.getGroupId()==null){
			return 0;
		}
		GroupInfo groupInfo = groupService.findOne(user.getGroupId());
		String groupIds = sysConfigService.getSysConfig().get(SysConfig.BUMENZHUANLAN);
		String[] groupArray = null;
		if(groupIds!=null&&groupIds.length()>0){
			groupArray = groupIds.split(",");
		}
		return getGroupId(groupInfo,groupArray,groupService);
	}
	
	private static Integer getGroupId(GroupInfo groupInfo,String[] groupArray,IGroup groupService){
		if(groupInfo!=null&&groupArray!=null&&groupArray.length>0){
			for(int i=0;i<groupArray.length;i++){
				if(groupArray[i].equals(groupInfo.getGroupId()+"")){
					return groupInfo.getGroupId();
				}else{
					continue;
				}
			}
			
			if(groupInfo.getParentId()==0){
				return groupInfo.getGroupId();
			}else{
				GroupInfo parentGroupInfo = groupService.findOne(groupInfo.getParentId());
				return getGroupId(parentGroupInfo,groupArray,groupService);
			}
			
		}else{
			return 0;
		}
	}
	
}
