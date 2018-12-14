package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.com.qytx.cbb.util.StringUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 人员选择action
 * <br/>版本: 1.0
 * <br/>开发人员：黄普友
 * <br/>创建日期: 2013-3-20
 * <br/>修改日期：2013-3-21
 * <br/>修改列表：
 */
public class UserSelectAction extends BaseActionSupport{
    
	private int type;
    
	/*
	 * 1:根据部门选择;2根据角色选择;5查找人员;3根据群组选择;4根据群组选择;6带数据权限的部门树;7查找人员 过滤隐藏人员和管理范围;8查询分支机构
	 */
	public final static int SELECT_TYPE_BYGROUP = 1;
	public final static int SELECT_TYPE_BYROLE = 2;
	public final static int SELECT_TYPE_SEARCH = 5;
    public final static int SELECT_TYPE_QUNZU = 3;
    public final static int SELECT_TYPE_QUNZU4 = 4;
    public final static int SELECT_TYPE_BYGROUP_AUTHO = 6;
    public final static int SELECT_TYPE_SEARCH_FILTER = 7 ;
    public final static int SELECT_TYPE_SEARCH_FORKGROUP = 8 ;
	
    /** 用户信息 */
    @Resource(name = "userService")
    IUser userService;
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

	@Resource
	private ICompany companyService;
	
	@Resource
	private IRoleUser roleUserService;
	
	@Resource
	private IGroupUser groupUserService;
	
    private String searchName;
    private int showType;// 选择类型 1只显示部门 2 显示角色 3 显示人员
    
    private Integer userId;
    private Integer companyId;
    /**
     * 模块名称
     */
    private String moduleName;
    
    private Integer qunzuType;
    
    public Integer getQunzuType() {
		return qunzuType;
	}
	public void setQunzuType(Integer qunzuType) {
		this.qunzuType = qunzuType;
	}

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 根据类型选择人员
     * @return
     */
    
    public String getUserInfo(){
    	try {
			String userIdStr = getRequest().getParameter("userId");
			if(userIdStr!=null && !"".equals(userIdStr)){
				Integer userId = Integer.parseInt(userIdStr);
				UserInfo userInfo = userService.findOne(userId);
				if(userInfo!=null){
					Integer groupId = userInfo.getGroupId();
					GroupInfo g = groupService.findOne(groupId);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("userId", userInfo.getUserId());
					map.put("userName", userInfo.getUserName());
					if(g!=null){
					    map.put("groupName", g.getGroupName());
					}
					Gson json = new Gson();
			        String jsons = json.toJson(map);
					ajax(jsons);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    public String selectUserByType()
    {
    	String contextPath = getRequest().getContextPath();
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        moduleName = (moduleName == null?"":moduleName);
        if (type == SELECT_TYPE_BYGROUP)
        {
            if(getLoginUser()!=null) {
                // 根据部门选择
                GroupInfo forkGroup = null;
                //如果是发文分发，则不区分二级局
                if (moduleName != null && moduleName.equals("dispatch")) {
                    forkGroup = null;
                }
                int key = 0;
                if (forkGroup != null) {
                    key = forkGroup.getGroupId();
                }
                UserInfo loginUser = getLoginUser();
                treeNodes = userService.selectUserByGroup(loginUser, forkGroup, moduleName, showType, key, contextPath, type);
            }
        }
        else if (type == SELECT_TYPE_BYROLE)
        {
            // 根据角色选择
            treeNodes = userService.selectUserByRole(getLoginUser(), moduleName, showType,contextPath);
        }
        else if (type == SELECT_TYPE_SEARCH)
        {
            // 查找人员
            searchUser(treeNodes);
        }
        
        //add by jiayq,群组人员树
        else if(type == SELECT_TYPE_QUNZU){
        	treeNodes = userService.selectUserByQunzu(getLoginUser().getCompanyId(), qunzuType, contextPath);
        }
        // 根据群组选择
        else if (type== SELECT_TYPE_QUNZU4){
        	treeNodes = userService.selectUserByQunzu(getLoginUser().getCompanyId(),type, contextPath);
		}
        //带数据权限的部门树
        else if (type==SELECT_TYPE_BYGROUP_AUTHO) {
        	  if(getLoginUser()!=null) {
                  // 根据部门选择
                  GroupInfo forkGroup = null;
                  //如果是发文分发，则不区分二级局
                  if (moduleName != null && moduleName.equals("dispatch")) {
                      forkGroup = null;
                  }
                  int key = 0;
                  if (forkGroup != null) {
                      key = forkGroup.getGroupId();
                  }
                  UserInfo loginUser = getLoginUser();
                  treeNodes = userService.selecFilterGroup(loginUser, forkGroup, moduleName, showType, key, contextPath, 1);
              }
		}
        //查找人员 过滤隐藏人员和管理范围
        else if (type == SELECT_TYPE_SEARCH_FILTER){
        	searchUserDoFilter(treeNodes);
        }else if(type == SELECT_TYPE_SEARCH_FORKGROUP ){//分支机构 ;add by jiayq
        	List<GroupInfo> glist = groupService.findForkGroupList();
        	if(glist!=null){
        		for(int i=0; i<glist.size(); i++){
        			GroupInfo temp = glist.get(i);
        			TreeNode treeHead = new TreeNode();
	              	treeHead.setId("gid_"+temp.getGroupId());//部门ID前加gid表示类型为部门
	              	treeHead.setName(temp.getGroupName());
	              	treeHead.setTitle(temp.getGroupName());
	              	treeHead.setPId("gid_"+(temp.getParentId()==null?0:temp.getParentId()));
	              	treeHead.setIcon( getRequest().getContextPath()+ "/images/group.png");
	              	treeHead.setOpen(true);
	              	treeNodes.add(treeHead);
        		}
        	}
        }
        for(int i=0; i<treeNodes.size(); i++){
        	treeNodes.get(i).setOpen(true);
        }
        Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String jsons = json.toJson(treeNodes);
        ajax(jsons);
        return null;
    }

    /**
     * 查找人员
     * @param treeNodes SimpleTreeNode列表
     */
    private void searchUser(Collection<TreeNode> treeNodes)
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + path + "/";

        try
        {
            UserInfo adminUser = getLoginUser();
            if (adminUser != null)
            {
                if (StringUtil.isEmpty(searchName))
                {
                    return;
                }
                // 遍历人员
                List<UserInfo> userList = userService.findAllUsers(adminUser.getCompanyId(),
                        searchName);
                GroupInfo forkGroup = groupService.getForkGroup(adminUser.getCompanyId(),adminUser.getUserId());
                CompanyInfo companyInfo=companyService.findOne(adminUser.getCompanyId());
                List<UserInfo> forkUsers = getUsersByForkGroup(forkGroup, companyInfo);
                if (userList != null)
                {
                    for (UserInfo user : userList)
                    {
                        if ("roleManager".equals(this.moduleName) && user.getUserState().intValue() == UserInfo.USERSTATE_UNLOGIN){
                            continue;
                        }
                        
                        boolean flag = isExist(user,forkUsers);
                        if(!flag){
                        	continue;
                        }
                        
                        TreeNode treeNode = new TreeNode();
                        treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
                        treeNode.setName(user.getUserName());
                        if(user.getIsVirtual() != null && user.getIsVirtual().intValue() == 1){
	                       	 treeNode.setId("uid_" + user.getLinkId());// 部门ID前加gid表示类型为部门
                        }
                        treeNode.setObj(user.getPhone()); // 把号码存放到node里面，js里面调用
                        // 职务
                        treeNode.setTarget(user.getJob());
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
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    /**
     * 查找人员 过滤隐藏人员和管理范围
     * @param treeNodes SimpleTreeNode列表
     */
    private void searchUserDoFilter(Collection<TreeNode> treeNodes)
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + path + "/";

        try
        {
            UserInfo adminUser = getLoginUser();
            if (adminUser != null)
            {
                if (StringUtil.isEmpty(searchName))
                {
                    return;
                }
                // 遍历人员
                List<UserInfo> userList = userService.findAllUsersAndFilter(adminUser,searchName);
                GroupInfo forkGroup = groupService.getForkGroup(adminUser.getCompanyId(),adminUser.getUserId());
                CompanyInfo companyInfo=companyService.findOne(adminUser.getCompanyId());
                List<UserInfo> forkUsers = getUsersByForkGroup(forkGroup, companyInfo);
                if (userList != null)
                {
                    for (UserInfo user : userList)
                    {
                        if ("roleManager".equals(this.moduleName) && user.getUserState().intValue() == UserInfo.USERSTATE_UNLOGIN){
                            continue;
                        }
                        
                        boolean flag = isExist(user,forkUsers);
                        if(!flag){
                        	continue;
                        }
                        
                        TreeNode treeNode = new TreeNode();
                        treeNode.setId("uid_" + user.getUserId());// 部门ID前加gid表示类型为部门
                        treeNode.setName(user.getUserName());
                        treeNode.setObj(user.getPhone()); // 把号码存放到node里面，js里面调用
                        // 职务
                        treeNode.setTarget(user.getJob());
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
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private boolean isExist(UserInfo ui,List<UserInfo> userlist){
    	for(int i=0; i<userlist.size(); i++){
    		if(userlist.get(i).getUserId().intValue() == ui.getUserId().intValue()){
    			return true;
    		}
    	}
    	return false;
    }

    
    /**
     * add by jiayq
     * 功能：获取指定二级局下的所有人员
     * @param
     * @return
     * @throws   
     */
    private List<UserInfo> getUsersByForkGroup(GroupInfo forkGroup,CompanyInfo companyInfo){
    	List<GroupInfo> grouplist = new ArrayList<GroupInfo>();
    	if(forkGroup == null){
    		grouplist = groupService.getGroupList(companyInfo.getCompanyId(), 1);
    	}else{
    		grouplist = groupService.getSubGroupList(forkGroup.getGroupId());
    		grouplist.add(forkGroup);
    	}
    	String ids = "";
    	for(Iterator<GroupInfo> it = grouplist.iterator(); it.hasNext();){
    		GroupInfo temp = it.next();
    		ids+=temp.getGroupId();
    		if(it.hasNext()){
    			ids+=",";
    		}
    	}
    	 List<UserInfo> userList = userService.findUsersByGroupId(ids);
    	return userList;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getSearchName()
    {
        return searchName;
    }

    public void setSearchName(String searchName)
    {
        this.searchName = searchName;
    }

    public int getShowType()
    {
        return showType;
    }

    public void setShowType(int showType)
    {
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	/**
	 * 功能：获取所有用户
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public String all(){
        List<UserInfo> u = userService.findAll(companyId,userId);
		Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	    String jsons = json.toJson(u);
	    ajax(jsons);
	    return null;
	}
	
    @Override
    protected UserInfo getLoginUser() {
        // TODO Auto-generated method stub
        UserInfo ui = super.getLoginUser();
        if(ui == null){
            ui = userService.findOne(userId);
        }
        return ui;
    }
    
}
