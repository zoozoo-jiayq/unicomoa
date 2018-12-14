package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.OnlineUserListener;

import com.google.gson.Gson;

/**
 * 人员选择action 版本: 1.0 开发人员：黄普友 创建日期: 2013-3-20 修改日期：2013-3-21 修改列表：
 */
public class OnUserSelectAction extends BaseActionSupport {
	private int type;// 选择类型1 部门 2 角色 3 分组 4 在线
	/** 用户信息 */
	@Resource(name = "userService")
	IUser userService;
	/**
	 * 部门/群组管理接口
	 */
	@Resource(name = "groupService")
	private IGroup groupService;
	@Resource(name = "groupUserService")
	private IGroupUser groupUserService;
	/**
	 * 角色管理接口
	 */
	@Resource(name = "roleService")
	private IRole roleService;

	private String searchName;
	private int showType;// 选择类型 1只显示部门 2 显示角色 3 显示人员

	/**
	 * 根据类型选择人员
	 * 
	 * @return
	 */

	public String selectUserByType() {
		ArrayList<TreeNod> treeNodes = new ArrayList<TreeNod>();
		if(type == 2){
			selectUser(treeNodes);
		}else{
			selectOnlineUser(treeNodes);
		}
		Gson json = new Gson();
		String jsons = json.toJson(treeNodes);
		ajax(jsons);
		return null;
	}
	
	private void selectOnlineUser(Collection<TreeNod> treeNodes){
		ArrayList<Integer> onlines = OnlineUserListener.getOnlineUser();
		List<UserInfo> onlineUsers = new ArrayList<UserInfo>();
		List<GroupInfo> onlineGroups = new ArrayList<GroupInfo>();
		
		List<GroupInfo> groupList = groupService.getGroupList(getLoginUser().getCompanyId(), 1);
		List<UserInfo> userList = userService.findAll();
		for(int i=0; i<onlines.size(); i++){
			Integer userId = onlines.get(i);
			for(int j=0; j<userList.size(); j++){
				if(userList.get(j).getUserId().intValue() == userId.intValue()){
					onlineUsers.add(userList.get(j));
					break;
				}
			}
		}
		List<Integer> gl = new ArrayList<Integer>();
		for(int i=0; i<onlineUsers.size(); i++){
			UserInfo u = onlineUsers.get(i);
			int groupId = u.getGroupId();
			for(int j=0; j<groupList.size(); j++){
				if(groupList.get(j).getGroupId().intValue() == groupId && !gl.contains(groupList.get(j).getGroupId().intValue())){
					gl.add(groupList.get(j).getGroupId().intValue());
					onlineGroups.add(groupList.get(j));
				}
			}
		}
		
		treeNodes.addAll(generateGroup(onlineGroups));
		treeNodes.addAll(generateUser(onlineUsers));
	}

	/**
	 * 根据部门选择人员
	 */
	private void selectUser(Collection<TreeNod> treeNodes) {

		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
			List onlineUserList = null;
			String haveP = "";
			List<GroupInfo> groupList = groupService.getGroupList(getLoginUser().getCompanyId(), 1);
			List<UserInfo> userList = userService.unDeleted().findAll();
			if (groupList != null) {
				String ids = "";
				
				treeNodes.addAll(generateGroup(groupList));

				if (userList != null) {
					treeNodes.addAll(generateUser(userList));
				}
			}
	}
	
	private List<TreeNod> generateGroup(List<GroupInfo> groupList){
		List<TreeNod> r = new ArrayList<TreeNod>();
		// 遍历部门
		for (GroupInfo group : groupList) {

			String groupName = StringUtils.substring(group
					.getGroupName(), 0, 8);
			TreeNod treeNode = new TreeNod();
		
			treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
			treeNode.setName(groupName);
			treeNode
					.setPId("gid_" + group.getParentId().toString());
			//treeNode.setIcon(basePath + "images/group.jpg");
			r.add(treeNode);

		}
		return r;
	}
	
	private List<TreeNod> generateUser(List<UserInfo> userList){
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		List<TreeNod> r = new ArrayList<TreeNod>();
		for (UserInfo user : userList) {
			String nvpath = basePath + "images/woman.png";
			String nanpath = basePath + "images/man.png";
			TreeNod treeNode = new TreeNod();
			treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
			treeNode.setName(user.getUserName());
			if (null != user.getSex() && 0 == user.getSex()) {
				treeNode.setIcon(basePath
						+ "images/woman.png");
			} else {
				treeNode.setIcon(basePath
						+ "images/man.png");
			}
			treeNode.setPId("gid_" + (user.getGroupId()==null?"":user.getGroupId()));
			r.add(treeNode);
		}
		return r;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}
}
