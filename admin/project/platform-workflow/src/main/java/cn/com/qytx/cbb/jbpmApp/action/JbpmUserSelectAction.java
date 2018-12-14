package cn.com.qytx.cbb.jbpmApp.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.StringUtil;
import cn.com.qytx.platform.utils.tree.SimpleTreeNode;

import com.google.gson.Gson;

/**
 * 人员选择action
 * 版本: 1.0
 * 开发人员：陈秋利
 * 创建日期: 2013-7-17
 * 修改日期：2013-7-17
 * 修改列表：
 */
public class JbpmUserSelectAction extends BaseActionSupport
{
    private int type;// 选择类型1 部门 2 角色 3 分组 4 在线
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
	//节点服务类
	@Resource(name = "nodeFormAttributeService")
	NodeFormAttributeService nodeFormAttributeService;
    private String searchName;
    private int showType;// 选择类型 1只显示部门 2 显示角色 3 显示人员

    private Integer nodeId;
    /**
     * 模块名称
     */
    private String moduleName;
    
    //1,选人；2，根据部门选人
    private int selectUserMode;
    
	public int getSelectUserMode() {
		return selectUserMode;
	}

	public void setSelectUserMode(int selectUserMode) {
		this.selectUserMode = selectUserMode;
	}

	
    /**
	 * 通过节点id得到该节点上的所有候选人员
	 */
	public String getUser(){
			UserInfo user = getLoginUser();
			List<UserInfo> userAlllist = new ArrayList<UserInfo>();//所有人员
			NodeFormAttribute nodeFormAtt =nodeFormAttributeService.findById(nodeId);
			if (nodeFormAtt!=null) {
				String candidateStr = nodeFormAtt.getCandidate();
				String deptsStr = nodeFormAtt.getDepts();
				String rolesStr = nodeFormAtt.getRoles();
				List<UserInfo> userlist = userService.findUsersByIds(candidateStr, deptsStr, rolesStr);
				for(int i=0; i<userlist.size(); i++){
					UserInfo t = userlist.get(i);
					if(StringUtil.isEmpty(searchName)){
						userAlllist.add(t);
					}else if(t.getPy().contains(searchName) || t.getUserName().contains(searchName)){
						userAlllist.add(t);
					}
				}
				Collection<SimpleTreeNode> treeNodes = new ArrayList<SimpleTreeNode>();
				//放到树中
				if(userAlllist.size()>0){
					String userIds = "";
					for(Iterator<UserInfo> it = userAlllist.iterator(); it.hasNext();){
						userIds+="'"+it.next().getUserId()+"'";
						if(it.hasNext()){
							userIds+=",";
						}
					}
					String groupName="无";
	                for (UserInfo userinfo : userAlllist)
	                {
	                    SimpleTreeNode treeNode = new SimpleTreeNode();
	                    treeNode.setId("uid_"+userinfo.getUserId());
	                    treeNode.setName(userinfo.getUserName());
	                    treeNode.setObj(groupName+"_"+userinfo.getLoginName()); 
	                    treeNode.setPId("gid_"+userinfo.getGroupId());
	                    if (null != userinfo.getSex() && 0 == userinfo.getSex()){
                            treeNode.setIcon(getRequest().getContextPath() + "/images/woman.png");
                        }else{
                            treeNode.setIcon(getRequest().getContextPath() + "/images/man.png");
                        }
	                    treeNodes.add(treeNode);
	                }
	                //add by jiayq，如果是按照部门选人,则增加部门
	                if(selectUserMode == 2){
	                	List<GroupInfo> grouplist = groupService.getGroupsByUserIds(user.getCompanyId(), userIds);
	                	for(int i=0; i<grouplist.size(); i++){
	                		GroupInfo giv = grouplist.get(i);
	                		SimpleTreeNode treeNode = new SimpleTreeNode();
		                    treeNode.setId("gid_"+giv.getGroupId());
		                    treeNode.setName(giv.getGroupName());
		                    treeNode.setPId("gid_"+giv.getParentId());
		                    treeNode.setIcon(getRequest().getContextPath() + "/images/group.png");
		                    treeNodes.add(treeNode);
	                	}
	                }
	            }
			 Gson json = new Gson();
	         String jsons = json.toJson(treeNodes);
	         ajax(jsons);
			}
	 
		return null;
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

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
    
    
    
}
