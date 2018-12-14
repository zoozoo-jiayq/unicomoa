package cn.com.qytx.cbb.groupExt.service;

import java.util.List;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.UserInfo;

public interface IGroupExt extends BaseService<UserInfo>{
	/**
	 * 根据部门id和是否展示状态获得部门下面的所有的用户
	 * @param page
	 * @param groupId 
	 * @param mobileViewState 0 展示，1不展示，-1展示全部
	 * @return
	 */
	public Page<UserInfo> getPageUserListByGroupId(Integer companyId,Pageable page,int groupId,String searchName,Integer sex);
	
	
	 /**
	  * 将人员移动到群组
	  * @return
	  */
	public void moveUserToGroup(String userIds,int groupId);
	
	 /**
	  * 将人员从群组中删除
	  * @return
	  */
	public void moveOutUserFromGroup(String ids,int groupId);
	
	/**
	 * 删除群组
	 */
	public void deleteGroupExt(int groupId);
	
	/**
	 * 根据groupId获得群组列表
	 */
	public List<GroupUser> getGroupUserByGoupId(int groupId);
}
