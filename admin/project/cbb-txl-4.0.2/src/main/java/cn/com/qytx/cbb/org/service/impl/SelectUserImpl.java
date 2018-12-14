package cn.com.qytx.cbb.org.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.org.service.ISelectUser;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.CompanyDao;
import cn.com.qytx.platform.org.dao.GroupDao;
import cn.com.qytx.platform.org.dao.RoleUserDao;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.RoleUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.tree.TreeNode;

@Service
@Transactional
public class SelectUserImpl extends BaseServiceImpl<UserInfo> implements ISelectUser,Serializable {

	@Autowired
	CompanyDao<CompanyInfo> companyDao;
	
	@Autowired 
	GroupDao<GroupInfo> groupDao;
	
	@Autowired
	RoleUserDao<RoleUser> roleUserDao;
 	
    @Resource
    IGroup groupService;
    
    @Resource
	IUser userService;
    
    @Resource
    IRole roleService;
    
    @Resource
    IGroupUser groupUserService;
    
	/**
     * 功能：搜索一级部门结构
     * 作者：张永峰
     * 参数：
     * 输出：
     */
    public List<TreeNode> selectDefaultOrg(UserInfo adminUser,GroupInfo forkGroup,int showType,int key,String path,int groupType){
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
      	CompanyInfo companyInfo = companyDao.findOne(adminUser.getCompanyId());
        List<GroupInfo> groupList = new ArrayList<GroupInfo>();
        if(companyInfo!=null)
        {
            if(forkGroup==null){
            	TreeNode treeHead = new TreeNode();
            	treeHead.setId("gid_0");//部门ID前加gid表示类型为部门
            	treeHead.setName(companyInfo.getShortName());
            	treeHead.setTitle(companyInfo.getShortName());
            	treeHead.setPId("gid_-1");
            	treeHead.setIcon(path + "/images/company.png");
            	treeHead.setOpen(true);
            	treeNodes.add(treeHead);
            	groupList = groupDao.getGroupInfoByParentId(0,groupType,adminUser.getCompanyId());
            }else{
            	//add by jiayq
            	TreeNode treeHead = new TreeNode();
            	treeHead.setId("gid_"+forkGroup.getGroupId());
            	treeHead.setName(forkGroup.getGroupName());
            	treeHead.setTitle(forkGroup.getGroupName());
            	treeHead.setPId("gid_-1");
            	treeHead.setIcon(path + "/images/group.png");
            	treeHead.setOpen(true);
            	treeNodes.add(treeHead);
            	groupList = groupDao.getGroupInfoByParentId(forkGroup.getGroupId(),groupType,adminUser.getCompanyId());
            }
        }
       
        if (groupList != null)
        {
            // 遍历部门
            for (GroupInfo group : groupList)
            {
               // String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
                TreeNode treeNode = new TreeNode();
                treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
                treeNode.setName(group.getGroupName());
                treeNode.setParent(true);
                treeNode.setTitle(group.getGroupName());
                treeNode.setPId("gid_" + group.getParentId().toString());
                treeNode.setIcon(path + "/images/group.png");
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }
    
    
    /**
     * 功能：搜索一级群组
     * 作者：张永峰
     * 参数：
     * 输出：
     */
    public List<TreeNode> selectDefaultQunzu(int companyId, int qunzuType,String contextPath){
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
		
		//查找群组
		List<GroupInfo> grouplist = groupService.findGroupTree(companyId, qunzuType);
		for(int i=0; i<grouplist.size(); i++){
			GroupInfo gi = grouplist.get(i);
			if(gi.getParentId() !=null && gi.getParentId() == 0){
				TreeNode treeNode = new TreeNode();
				treeNode.setId("gid_"+gi.getGroupId());
				treeNode.setName(gi.getGroupName());
				treeNode.setParent(true);
				treeNode.setPId("gid_"+(gi.getParentId()==null?0:gi.getParentId().toString()));
				treeNode.setIcon( contextPath+ "/images/group.png");
				nodelist.add(treeNode);
			}
		}
		return nodelist;
    }
    
    /**
     * 功能：搜索一级角色
     * @return
     */
    public List<TreeNode> selectDefaultRole(){
		 List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	     List<RoleInfo> roleList = roleService.unDeleted().findAll();
	     if (roleList != null){
	            // 遍历部门
	            for (RoleInfo role : roleList)
	            {
	                TreeNode treeNode = new TreeNode();
	                treeNode.setTitle(role.getRoleName());
	                treeNode.setId("rid_" + role.getRoleId().toString());// rid表示类型为角色
	                treeNode.setName(role.getRoleName());
	                treeNode.setParent(true);
	                treeNode.setPId("0");
	                treeNodes.add(treeNode);
	            }
	        }
		return treeNodes;
    }
    
    
    /**
     * 功能：获得节点下面的信息
     * @param nodeId
     * @param companyId
     * @param contextPath
     * @param showType
     * @param type
     * @return
     */
    public List<TreeNode> getChildrenByNodeId(UserInfo userInfo,int nodeId,String contextPath,int showType,int type){
    	List<TreeNode> nodelist = new ArrayList<TreeNode>();
    	List<GroupUser> groupUsers = groupUserService.findAll();
    	if(type == 1 || type == 4){//部门、群组人员
    		List<GroupInfo> groupList = groupDao.getGroupInfoByParentId(nodeId, type, userInfo.getCompanyId());
    		if (groupList != null && groupList.size()>0){
                // 遍历部门
                for (GroupInfo group : groupList)
                {
                   // String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
                    TreeNode treeNode = new TreeNode();
                    treeNode.setId("gid_" + group.getGroupId().toString());// 部门ID前加gid表示类型为部门
                    treeNode.setName(group.getGroupName());
                    treeNode.setTitle(group.getGroupName());
                    treeNode.setParent(true);
                    treeNode.setPId("gid_" + group.getParentId().toString());
                    treeNode.setIcon(contextPath + "/images/group.png");
                    nodelist.add(treeNode);
                }
            }
    		if(showType == 3){
    			List<UserInfo> userList = userService.findUsersByGroupId(nodeId+"");
    			if(userList != null && userList.size() > 0){
    				// 遍历人员
    				for(UserInfo user:userList){
    					TreeNode treeNode = new TreeNode();
                        treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
                        treeNode.setName(user.getUserName());
                        treeNode.setObj(user.getPhone()); // 把号码存放到node里面，js里面调用
                        treeNode.setParent(false);
                        treeNode.setPId("gid_"
                                + getGroupIdByUserId(groupUsers, user.getUserId()));
                        if (null != user.getSex() && 0 == user.getSex())
                            {
                                treeNode.setIcon(contextPath + "/images/woman.png");
                            }
                            else
                            {
                                treeNode.setIcon(contextPath + "/images/man.png");
                            }
                        nodelist.add(treeNode);
    				}
    			}
    		}
    	}else if(type == 2){
    		//角色人员
    		if(showType == 3){
    			//遍历人员
    			List<RoleUser> roleusers = roleUserDao.getRoleUserByRoleIdCompanyId(userInfo.getCompanyId(), nodeId);
    			List<UserInfo> userList = new ArrayList<UserInfo>();
    			List<GroupInfo> groupList = new ArrayList<GroupInfo>();
    			String ids="";
    			GroupInfo forkGroup = groupService.getForkGroup(userInfo.getCompanyId(),userInfo.getUserId());
    			if(forkGroup==null){
    				userList = userService.findUsersByRoleId(nodeId+"");
    			}else{
    				groupList = groupService.getSubGroupList(forkGroup.getGroupId());
    				for (GroupInfo group : groupList)
    				{
    					ids += group.getGroupId() + ",";
    				}
    				
    				if (ids.endsWith(","))
    				{
    					// 移除后面的,
    					ids = ids.substring(0, ids.length() - 1);
    				}
    				
    				//add by jiayq，把二级局单位下面的人员也添加上
    				if(forkGroup!=null){
    					ids+=","+forkGroup.getGroupId();
    				}
    				
    				// 遍历人员
    				userList = userService.findUsersByGroupId(ids);
    			}
    			
    			if (userList != null){
                    for (UserInfo user : userList)
                    {
                        TreeNode treeNode = new TreeNode();
                        treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
                        treeNode.setName(user.getUserName());
                        treeNode.setObj(user.getPhone()); // 把号码存放到node里面，js里面调用
                        treeNode.setParent(false);
                        treeNode.setPId("rid_"
                                + getRoleByUserId(roleusers, user.getUserId()));
                        if (null != user.getSex() && 0 == user.getSex())
                        {
                            treeNode.setIcon(contextPath + "images/woman.png");
                        }
                        else
                        {
                            treeNode.setIcon(contextPath + "images/man.png");
                        }

                        nodelist.add(treeNode);
                    }
                }
    		}
    	}
    	return nodelist;
    }
    
    /**
     * 功能： 获得用户的groupId
     * @param groupUsers
     * @param userId
     * @return
     */
    private int getGroupIdByUserId(List<GroupUser> groupUsers,Integer userId){
    	for(int i=0; i<groupUsers.size(); i++){
    		if(groupUsers.get(i).getUserId()!=null && groupUsers.get(i).getUserId().intValue() == userId.intValue()){
    			return groupUsers.get(i).getGroupId();
    		}
    	}
    	return 0;
    }
    
    
    
	 private int getRoleByUserId(List<RoleUser> roles,int userId){
	    	for(int i=0; i<roles.size(); i++){
	    		RoleUser ru = roles.get(i);
	    		if(ru.getUserId().intValue() == userId){
	    			return ru.getRoleId();
	    		}
	    	}
	    	return 0;
    }

}
