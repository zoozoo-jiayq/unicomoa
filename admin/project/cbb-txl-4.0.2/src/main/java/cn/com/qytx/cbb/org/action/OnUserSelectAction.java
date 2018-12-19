package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
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
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
		try{
			List<TreeNode> treeNodes = new ArrayList<TreeNode>(); 
	        // 根据部门选择
	        GroupInfo forkGroup = null;
	        int key = 0 ;
	        if(forkGroup!=null){ 
	            key =  forkGroup.getGroupId(); 
	        }
	        treeNodes = userService.selectUserByGroup(getLoginUser(), forkGroup, "userTree", 1, key,getRequest().getContextPath(),GroupInfo.DEPT);
	        //Gson gson = new Gson();
	        Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	        String jsons = json.toJson(treeNodes);
	        ajax(jsons);
		}catch(Exception e){
			e.printStackTrace();
		}
		
        return null;
	}

	/**
	 * 根据部门选择人员
	 */
	private void selectUserByGroup(Collection<TreeNod> treeNodes) {

		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
			List onlineUserList = null;
			String haveP = "";
			List<GroupInfo> groupList = groupService.getGroupList(getLoginUser().getCompanyId(), 1);
			if (groupList != null) {
				String ids = "";
				// 遍历部门
				for (GroupInfo group : groupList) {

					List<UserInfo> list = userService.findUsersByGroupId(group.getGroupId()+"");
					// 组里面的所有的人员
					String userId = "";
					StringBuffer onUserId=new StringBuffer();
					 
					Map allUserMap = new HashMap();
					// 组里面是否有人员
					if (list.size() != 0) {
						for (int i = 0; i < list.size(); i++) {
							UserInfo userInfo = (UserInfo) list.get(i);
							userId += userInfo.getUserId() + ",";
							allUserMap.put(userInfo.getUserId()+"", userInfo.getUserId());
						}
						ServletContext servletContext = ServletActionContext
								.getServletContext();
//							onlineUserList = OnlineUserListener.onlineUserIdList;
						if (null != onlineUserList) {
							for (int i = 0; i < onlineUserList.size(); i++) {
								Object object = onlineUserList.get(i);
								String id = object.toString();
								onUserId.append(id).append(",");
								if(allUserMap.get(id)!=null){
									haveP="1";
								}
				
								
							}
						}
						
						if(type == 2){
								 haveP="1";
						}

					}
					if (list.size() != 0) {
						ids += group.getGroupId() + ",";

					}

					String havePerson = "1";
					String groupName = StringUtils.substring(group
							.getGroupName(), 0, 8);
					TreeNod treeNode = new TreeNod();
					if ("1".equals(haveP)) {
						treeNode.setObj("1");
					} else {
						treeNode.setObj("");
					}
					if(type==1)
					{
						treeNode.setTreeType("1");
					}
					if(type==2)
					{
						treeNode.setTreeType("2");
					}
				
					treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
					treeNode.setName(groupName);
					treeNode
							.setPId("gid_" + group.getParentId().toString());
					//treeNode.setIcon(basePath + "images/group.jpg");
					treeNodes.add(treeNode);

				}

				if (ids.endsWith(",")) {
					// 移除后面的,
					ids = ids.substring(0, ids.length() - 1);
				}

				// 遍历人员
				List<UserInfo> userList = userService
						.findUsersByGroupId(ids);

				if (type == 2) {
					if (userList != null) {
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
							/*
							 * treeNode.setObj(user.getPhone()); //
							 * 把号码存放到node里面，js里面调用 treeNode.setPId("gid_" +
							 * user.getExtendData().get("groupId")
							 * .toString());
							 */
								treeNode.setPId("gid_" + user.getGroupId());
								GroupInfo gi = groupService.getGroupByUserId(user.getCompanyId(), user.getUserId());
								treeNode.setObj(gi.getGroupName());
								treeNodes.add(treeNode);
						}
					}

				} else {
					if (onlineUserList != null) {
						for (int k = 0; k < onlineUserList.size(); k++) {
							Object object = onlineUserList.get(k);
							String nv = basePath + "images/woman.png";
							String na = basePath + "images/man.png";
							String id = object.toString();
							int finalId = Integer.parseInt(id);

							UserInfo user = userService.findOne( finalId);
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

							// 把号码存放到node里面，js里面调用
							treeNode.setPId("gid_" + user.getGroupId());
							GroupInfo g = groupService.getGroupByUserId(user.getCompanyId(), user.getUserId());
							treeNode.setObj(g.getGroupName());
							treeNodes.add(treeNode);
						}
					}
				}
			}
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
