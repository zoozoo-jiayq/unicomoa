package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;


/**
 * 
 * <br/>功能:部门列表
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-4-12
 * <br/>修改日期: 2013-4-12
 * <br/>修改列表:
 */
public class GroupListAction  extends BaseActionSupport{
	private static final long serialVersionUID = 1L;
    
    /**
     * 部门/群组管理接口
     */
    @Resource(name = "groupService")
    private IGroup groupService;
    
    /**用户信息*/
    @Resource(name = "userService")
    IUser userService;
    
    private int showUserNum;//是否显示人员数 0 不显示 1显示
    
	private Integer groupType;
	
	private Integer groupId;
	
	private String moduleName;
	
	public Integer getGroupType() {
		return groupType;
	}
	
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	/**
	 * 
	 * @Title: findGradeGroupTree 
	 * @Description: TODO(得到级别部门树) 
	 * @return String    返回类型
	 */
	public String findGradeGroupTree() {
		if(groupType==null){
			groupType=1;
		}
		List<GroupInfo> groupTree = null;
		GroupInfo forkGroup = groupService.getForkGroup(getLoginUser().getCompanyId(),getLoginUser().getUserId());
		if(forkGroup!=null){
			groupTree = groupService.getSubGroupList(forkGroup.getGroupId());
			groupTree.add(forkGroup);
		}else{
			groupTree = groupService.findGradeGroupTree(getLoginUser().getCompanyId(),groupType);
		}
		//根据id得到人员姓名
		if(groupTree!=null){
			for (GroupInfo groupInfoVO : groupTree) {
				Integer directorId=groupInfoVO.getDirectorId();
				Integer assistantId=groupInfoVO.getAssistantId();
				Integer topDirectorId=groupInfoVO.getTopDirectorId();
				Integer topChangeId=groupInfoVO.getTopChangeId();
				if(directorId!=null){
					UserInfo user=userService.findOne(directorId);
					if(user!=null){
						groupInfoVO.setDirectorName(user.getUserName());
					}
				}
				if(assistantId!=null){
					UserInfo user=userService.findOne( assistantId);
					if(user!=null){
						groupInfoVO.setAssistantName(user.getUserName());
					}
				}
				if(topDirectorId!=null){
					UserInfo user=userService.findOne( topDirectorId);
					if(user!=null){
						groupInfoVO.setTopDirectorName(user.getUserName());
					}
				}
				if(topChangeId!=null){
					UserInfo user=userService.findOne(topChangeId);
					if(user!=null){
						groupInfoVO.setTopChangeName(user.getUserName());
					}
				}
			}
		}
		this.getRequest().setAttribute("groupTree", groupTree);
		return SUCCESS;
	}
	
	/**
     * 获取部门/群组列表
     *
     * @return
     */
    public String getGroupList() {
        List<GroupInfo> groupList = groupService.getGroupList(getLoginUser().getCompanyId(), groupType);  //获取部门列表
        Map<Integer,Integer> groupUserNumList=userService.getUserNumByGroup(getLoginUser().getCompanyId());//获取部门人员数
        if (groupUserNumList==null)
        {
            groupUserNumList=new HashMap();
        }
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        
        //创建部门树
        buildTreeNodeList(groupList,groupUserNumList,treeNodes);
        Gson json = new Gson();
        String jsons = json.toJson(treeNodes);
        ajax(jsons);
        return null;
    }
    
    /**
     * 创建部门树
     * 在zTree树中使用时，只需要给TreeNode节点中pid赋值就可以了，不需要设置Children
     */
    private void buildTreeNodeList(final  List<GroupInfo> groupList,final  Map<Integer,Integer> groupUserNumList,Collection<TreeNode> data)
    {
    	Collection<TreeNode> treeNodes=data;
    	int allNum=0;//所有人员
        if(treeNodes==null)
        {
            treeNodes=new ArrayList<TreeNode>();
        }
        for (GroupInfo group : groupList) {
            int num=0;
            if(groupUserNumList.get(group.getGroupId())!=null)
            {
                num=groupUserNumList.get(group.getGroupId());
                allNum+=num;
            }
            String groupName = StringUtils.substring(group.getGroupName(), 0, 8);
            TreeNode treeNode = new TreeNode();
            treeNode.setId("gid_"+group.getGroupId().toString());//部门ID前加gid表示类型为部门
            if (showUserNum==0)
            {
             treeNode.setName(groupName);
            }
            else if(showUserNum==1)
            {
                treeNode.setName(groupName + "(" + num + ")");
            }
            treeNode.setPId("gid_"+group.getParentId().toString());
            treeNodes.add(treeNode);
         }
    }
    
    /**
     * 根据群组Id获取组下成员信息,不包含子组成员
     */
    @SuppressWarnings("unchecked")
    public void findUsersByGroupId(){
        List<UserInfo> userList = userService.findUsersByGroupId(groupId+"");            
        if (!StringUtils.isEmpty(this.moduleName))
        {
            //this.modulePrivService.removeNoPrivUser(userList,adminUser.getUserId(),adminUser.getCompanyId(),null);
        }
        
        // 组装结果信息
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();;
        if (userList != null)
        {
            for (UserInfo tempUserInfo : userList)
            {
                Map<String, Object> map = new HashMap<String, Object>();

                // userId
                Integer userId = tempUserInfo.getUserId();
                map.put("userId", userId);

                // 姓名
                String userName = tempUserInfo.getUserName();
                map.put("userName", userName);

                // 职务
                String job = tempUserInfo.getJob();
                map.put("job", job);
                
                // 电话
                String phone = tempUserInfo.getPhone();
                map.put("phone", phone);
                mapList.add(map);
            }
        }
        
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("aaData", mapList);
        Gson json = new Gson();
        String jsons = json.toJson(jsonMap);
        ajax(jsons);
    }
    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
    }

    public int getShowUserNum()
    {
        return showUserNum;
    }

    public void setShowUserNum(int showUserNum)
    {
        this.showUserNum = showUserNum;
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
