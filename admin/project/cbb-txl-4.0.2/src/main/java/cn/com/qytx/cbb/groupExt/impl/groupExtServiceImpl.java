package cn.com.qytx.cbb.groupExt.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.groupExt.dao.GroupExtDao;
import cn.com.qytx.cbb.groupExt.service.IGroupExt;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.event.PublishEventUtil;
import cn.com.qytx.platform.org.dao.GroupUserDao;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;

@Service
@Transactional
public class groupExtServiceImpl extends BaseServiceImpl<UserInfo> implements IGroupExt{

	@Autowired
	IGroup groupService;
	
	@Autowired
	UserDao<UserInfo> userDao;
	
	@Autowired
	GroupUserDao<GroupUser> groupUserDao;
	
	@Autowired
	GroupExtDao groupExtDao;
	/**
	 * 根据部门id和是否展示状态获得部门下面的所有的用户
	 * @param page
	 * @param groupId 
	 * @return
	 */
	public Page<UserInfo> getPageUserListByGroupId(Integer companyId,Pageable page,int groupId,String searchName,Integer sex){
		return groupExtDao.getPageUserListByGroupId(companyId, page, groupId, searchName, sex);
	}
	
	 /**
	  * 将人员移动到群组
	  * @return
	  */
	public void moveUserToGroup(String userIds,int groupId){
		 String[] arr = userIds.split(",");
		 for(String userId:arr){
			 if(!userId.equals("")){
				 GroupUser groupUser = new GroupUser();
				 groupUser.setCompanyId(userDao.findOne(Integer.valueOf(userId)).getCompanyId());
				 groupUser.setGroupId(groupId);
				 groupUser.setUserId(Integer.valueOf(userId));
				 List<Integer> list = groupUserDao.getUserIdsBySetId(groupUser.getCompanyId(),groupUser.getGroupId());
				 if(list==null || list.size()<=0 || !list.contains(groupUser.getUserId())){
					 groupUserDao.saveOrUpdate(groupUser);
				 }
			 }
		 }
	}
	
	 /**
	  * 将人员从群组中删除
	  * @return
	  * @param ids 需要删除的groupUser的ids
	  */
	public void moveOutUserFromGroup(String ids,int groupId){
		groupExtDao.moveOutUserFromGroup(ids, groupId);
		groupService.updateGroupTime(groupId);
		GroupInfo group = groupService.findOne(groupId);
		//发布群组删除人员广播
		PublishEventUtil.publishEvent(new EventForDeleteUserToGroup(group.getCompanyId()));
	}
	
	/**
	 * 删除群组
	 */
	public void deleteGroupExt(int groupId){
		groupService.delete(groupId, false);
		groupUserDao.deleteGroupUserByGroupId(groupId);
	}
	
	/**
	 * 根据groupId获得群组列表
	 */
	public List<GroupUser> getGroupUserByGoupId(int groupId){
		return groupUserDao.getSetByGoupIdUndeleteUser(groupId);
	}
	
}
