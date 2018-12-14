package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 左边人员树Action
 * 版本: 1.0
 * 开发人员：黄普友
 * 创建日期: 2013-3-20
 * 修改日期：2013-3-21
 * 修改列表：
 */
public class TreeUserAction extends BaseActionSupport {

    /**用户信息*/
    @Resource(name = "userService")
    IUser userService;
    
    @Resource
    ICompany companyService;
    /**
     * 部门/群组管理接口
     */
    @Resource(name = "groupService")
    private IGroup groupService;
    /**
     * 角色管理接口
     */
    @Resource(name = "roleService")
    private IRole roleService;
    
    @Resource(name="groupUserService")
    private IGroupUser groupUserService;
	//组类型GroupType
    private Integer type;
    private String searchName;
    //显示类型 1 显示部门 2显示角色 3显示人员
    private Integer showType;
    
    private String moduleName;
    
    /**
     * 根据类型选择人员
     * @return
     */
    public String selectUserByType()
    {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        // 根据部门选择
        GroupInfo forkGroup = null;
        //如果是发文分发，则不区分二级局
        if(moduleName!=null&&moduleName.equals("dispatch")){
            forkGroup = null;
        }
        String isSchoolManager = (String)this.getSession().getAttribute("isSchoolManager");
        UserInfo userInfo = this.getLoginUser();
		if("0".equals(isSchoolManager)){
			forkGroup = groupService.findOne(userInfo.getGroupId());
		}
        int key = 0 ;
        if(forkGroup!=null){
            key =  forkGroup.getGroupId();
        }
        if(moduleName == null){
            this.moduleName = "userTree";
        }
        if(type==null){
            type = GroupInfo.DEPT;
        }
    	treeNodes = userService.selectUserByGroup(getLoginUser(), forkGroup, moduleName, showType, key,getRequest().getContextPath(),type);
        if(StringUtils.isEmpty(moduleName)){
            //this.modulePrivService.removeNoPriv(treeNodes,adminUser.getUserId(),adminUser.getCompanyId(),null);
        }
        //Gson gson = new Gson();
        Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String jsons = json.toJson(treeNodes);
        ajax(jsons);
        return null;
    }
    
    
    /**
     * 获取单位列表树
     * @return
     */
    public String selectGroupByType()
    {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        // 根据部门选择
        GroupInfo forkGroup = null;
        //如果是发文分发，则不区分二级局
        if(moduleName!=null&&moduleName.equals("dispatch")){
            forkGroup = null;
        }
        int key = 0 ;
        if(forkGroup!=null){
            key =  forkGroup.getGroupId();
        }
        if(moduleName == null){
            this.moduleName = "userTree";
        }
        if(type==null){
            type = GroupInfo.DEPT;
        }
        treeNodes = userService.selectUserByGroup(getLoginUser(), forkGroup, moduleName, showType, key,getRequest().getContextPath(),type);
        if(StringUtils.isEmpty(moduleName)){
            //this.modulePrivService.removeNoPriv(treeNodes,adminUser.getUserId(),adminUser.getCompanyId(),null);
        }
        //Gson gson = new Gson();
        Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String jsons = json.toJson(treeNodes);
        ajax(jsons);
        return null;
    }

    /**
     * 根据类型选择加权限控制
     * @return
     */
    public String selectUserPriv()
    {
        ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();
            //根据部门选择
        selectUserByGroupNoIcon(treeNodes);
        if(StringUtils.isEmpty(moduleName)){
            // this.modulePrivService.removeNoPriv(treeNodes,adminUser.getUserId(),adminUser.getCompanyId(),null);
        }
        Gson gson = new Gson();
        String jsons = gson.toJson(treeNodes);
        ajax(jsons);
        return null;
    }

    
    /**
     * 根据部门选择人员
     */
    private void selectUserByGroupNoIcon(Collection<TreeNode> treeNodes)
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + path + "/";
    
    	if(type==null){
    		type=1;
    	}
    	if(showType==null){
    		showType=1;
    	}
        CompanyInfo companyInfo;
        
        //add by jiayq，获取二级局单位
        GroupInfo forkGroup = groupService.getForkGroup(getLoginUser().getCompanyId(),getLoginUser().getUserId());
        
        //如果二级局未空，显示全公司的部门
        if(forkGroup == null){
        	companyInfo  = companyService.findOne(getLoginUser().getCompanyId());
            TreeNode treeNode = new TreeNode();
            treeNode.setId("gid_0");//部门ID前加gid表示类型为部门
            treeNode.setName(companyInfo.getShortName());
            treeNode.setPId("gid_-1");
            treeNode.setIcon(basePath + "/images/company.png");
            treeNodes.add(treeNode);
        }
        List<GroupInfo> groupList = null;
        if(forkGroup == null){
        	groupList=groupService.getGroupList(getLoginUser().getCompanyId(),type);
        }else{
        	groupList = groupService.getSubGroupList(forkGroup.getGroupId());
        	groupList.add(forkGroup);
        }
        if(groupList!=null)
        {
            String ids="";
            //遍历部门
            for(GroupInfo group:groupList)
            {
                ids+=group.getGroupId()+",";
                String groupName = group.getGroupName();
                TreeNode treeNode = new TreeNode();
                treeNode.setId("gid_" + group.getGroupId().toString());//部门ID前加gid表示类型为部门
                treeNode.setName(groupName);
                treeNode.setPId("gid_" + group.getParentId().toString());
                //treeNode.setIcon(basePath + "images/group.jpg");
                treeNodes.add(treeNode);
            }
                if(ids.endsWith(","))
                {
                    //移除后面的,
                    ids=ids.substring(0,ids.length()-1);
                }
                if(showType==3){
                    //遍历人员
                    List<UserInfo> userList=userService.findUsersByGroupId(ids);
                    List<GroupUser> groupUsers = groupUserService.findByType(getLoginUser().getCompanyId(), 1);
                    if(userList!=null)
                    {
                        for (UserInfo user : userList)
                        {
                            TreeNode treeNode = new TreeNode();
                            treeNode.setId("uid_" + user.getUserId());//部门ID前加gid表示类型为部门
                            treeNode.setName(user.getUserName());
                            treeNode.setObj(user.getPhone()); //把号码存放到node里面，js里面调用
                            int gid = getGroupIdByUser(user.getUserId(), groupUsers);
                            treeNode.setPId("gid_"+gid);
                            if (null != user.getSex() && 0 == user.getSex())
                            {
                               treeNode.setIcon(basePath + "images/woman.png");
                            }
                            else
                            {
                                treeNode.setIcon(basePath + "images/man.png");
                            }
                            treeNodes.add(treeNode);
                        }
                    }
                }
            }
    }
    
    private int getGroupIdByUser(int userId,List<GroupUser> groupUsers){
    	for(int i=0; i<groupUsers.size(); i++){
    		if(groupUsers.get(i).getUserId().intValue() == userId){
    			return groupUsers.get(i).getGroupId();
    		}
    	}
    	return 0;
    }

    public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getSearchName() {
		return searchName;
	}


	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}


	public Integer getShowType() {
		return showType;
	}


	public void setShowType(Integer showType) {
		this.showType = showType;
	}


    public String getModuleName()
    {
        return moduleName;
    }


    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
}
