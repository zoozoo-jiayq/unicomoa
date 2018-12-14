package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.event.PublishEventUtil;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserGroup;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.EventForAddUserGroup;
import cn.com.qytx.platform.org.service.EventForDeleteUserGroup;
import cn.com.qytx.platform.org.service.EventForUpdateUserGroup;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.org.service.IUserGroup;

/**
 * 功能:管理授权
 */
public class UserGroupAction  extends BaseActionSupport{
	private static final long serialVersionUID = 1L;
	@Resource(name = "userGroupService")
	private IUserGroup userGroupService;
	@Resource(name="userService")
	private IUser userService;
	@Resource(name="groupService")
	private IGroup groupService;
	
	private UserGroup userGroup;
	private String ids;//ids
	private String userIds;
	private String groupIds;
	private String searchkey;
	private Integer groupId;
	/**
	 * 功能：分页列表
	 * @return
	 */
	public String findUserList(){
		Page<Map<String, Object>> pageInfo = userGroupService.findByCompany(getPageable(), getLoginUser().getCompanyId(), searchkey);
        List<Map<String, Object>> userGroupList = pageInfo.getContent();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        List<GroupInfo> groupList = new ArrayList<GroupInfo>();
        groupList=groupService.getGroupList(getLoginUser().getCompanyId(), GroupInfo.DEPT);
        if (userGroupList != null) {
        	Integer companyId = getLoginUser().getCompanyId();
        	int uSize=userGroupList.size();
            for(int i=0; i<uSize; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                String groupPower=(String) userGroupList.get(i).get("groupPower");
                if (StringUtils.isNotBlank(groupPower)) {
                	  StringBuilder groupPowerName= new StringBuilder();
                	  List<GroupInfo> groupList1=groupService.getGroupListByIds(companyId, groupPower);
                      for (GroupInfo groupInfo2 : groupList1) {
                      	groupPowerName.append(groupInfo2.getGroupName()).append(",");
      				  }
                      map.put("groupPowerName", groupPowerName.toString());
                      map.put("groupPower",userGroupList.get(i).get("groupPower").toString());
				}else {
					map.put("groupPowerName", "-");
                    map.put("groupPower","");
				}
                String groupName="-";
                for (int j = 0; j < groupList.size(); j++) {
                	Integer groupId=(Integer) userGroupList.get(i).get("groupId");
					if (null!=groupId&&groupId.equals(groupList.get(j).getGroupId())) {
						groupName=groupList.get(j).getGroupName();
						break;
					}
				}
                map.put("no", getPageable().getOffset()+i+1);
                map.put("userName",userGroupList.get(i).get("userName"));
                map.put("phone",userGroupList.get(i).get("phone"));
                map.put("id", userGroupList.get(i).get("id"));
                map.put("userId", userGroupList.get(i).get("userId"));
                map.put("groupName",groupName);
                mapList.add(map);
            }
        }
        ajaxPage(pageInfo, mapList);
		return null;
	}
	
	/**
	 * 功能：新增数据
	 * @return
	 */
	public String addData(){
	    String users[] =userIds.split(",");
	    List<UserGroup> ugList = new ArrayList<UserGroup>();
		for (int i = 0; i < users.length; i++) {
			//如果存在用户 则更新
			UserGroup u=userGroupService.findByUserCompany(Integer.parseInt(users[i]),getLoginUser().getCompanyId());
			if (null!=u) {
				u.setGroupPower(groupIds);
				ugList.add(u);
			}else {
				UserGroup uGroup = new UserGroup();
				uGroup.setCompanyId(getLoginUser().getCompanyId());
				UserInfo user = new UserInfo();
				user.setUserId(Integer.parseInt(users[i]));
				uGroup.setUser(user);
				uGroup.setGroupPower(groupIds);
				ugList.add(uGroup);
			}
		}
		userGroupService.saveOrUpdate(ugList);
		//发布添加管理范围的事件
        PublishEventUtil.publishEvent(new EventForAddUserGroup(ugList.get(0)));
		ajax("1");
		return null;
	}
	/**
	 * 功能：删除数据
	 * @return
	 */
	public String removeByIds(){
		List<Integer> list = new ArrayList<Integer>();
		String idsTem[] = ids.split(",");
		for (int i = 0; i < idsTem.length; i++) {
			list.add(Integer.parseInt(idsTem[i]));
		}
		userGroupService.deleteByIds(list,true);
		//发布添加管理范围的事件
        PublishEventUtil.publishEvent(new EventForDeleteUserGroup(ids));
		ajax("1");
		return null;
	}

	/**
	 * 功能：更新数据
	 * @return
	 */
	public String updateById(){
		userGroup.setCompanyId(getLoginUser().getCompanyId());
		userGroupService.saveOrUpdate(userGroup);
		//发布添加管理范围的事件
        PublishEventUtil.publishEvent(new EventForUpdateUserGroup(userGroup));
		ajax("1");
		return null;
	}
    public String checkPower() {
    	boolean flag=userGroupService.checkIsExistPower(getLoginUser().getUserId(), getLoginUser().getCompanyId(), groupId);
    	if (!flag) {
    		List<GroupInfo> gls =groupService.findGroupTree( getLoginUser().getCompanyId(), 1);
			UserGroup  ug=userGroupService.findByUserCompany(getLoginUser().getUserId(), getLoginUser().getCompanyId());
			if(gls!=null && ug!=null && (gls.size()==ug.getGroupPower().substring(1).split(",").length)){
				flag=true;
			}
		}
    	ajax(flag==true?1:0);
    	return null;
    }
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
}
