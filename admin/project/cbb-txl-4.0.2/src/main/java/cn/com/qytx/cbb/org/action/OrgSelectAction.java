package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.com.qytx.cbb.org.service.ISelectUser;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.StringUtil;
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OrgSelectAction extends BaseActionSupport{
    private int type;// 选择类型1 部门 2 角色  4 群组 5 在线

    private Integer showType;
    
    /**
     * 节点id
     */
    private String nodeId;
    /** 用户信息 */
    @Resource(name = "userService")
    IUser userService;
    
    @Resource
    ISelectUser orgService;
    
    /**
     * 部门/群组管理接口
     */
    @Resource(name = "groupService")
    private IGroup groupService;
    
    
    public void defaultSelect(){
      	String contextPath = getRequest().getContextPath();
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        if (type == 1){
            if(getLoginUser()!=null) {
                // 根据部门选择
                GroupInfo forkGroup = groupService.getForkGroup(getLoginUser().getCompanyId(), getLoginUser().getUserId());
                //如果是发文分发，则不区分二级局
                int key = 0;
                if (forkGroup != null) {
                    key = forkGroup.getGroupId();
                }
                UserInfo loginUser = getLoginUser();
                treeNodes = orgService.selectDefaultOrg(loginUser, forkGroup, showType, key, contextPath, type);
            }
        }else if (type == 2){// 根据角色选择
            treeNodes = orgService.selectDefaultRole();
        }else if (type==4){// 根据群组选择
        	treeNodes = orgService.selectDefaultQunzu(getLoginUser().getCompanyId(),type, contextPath);
		}
        Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String jsons = json.toJson(treeNodes);
        ajax(jsons);
    }

    public void getTreeByNode(){
    	String contextPath = getRequest().getContextPath();
    	List<TreeNode> treeNodes = new ArrayList<TreeNode>();
    	if(StringUtil.isEmpty(nodeId) || nodeId.length() <= 4 ){
    		return;
    	}
    	int theNodeId = Integer.valueOf(nodeId.substring(4));
    	treeNodes = orgService.getChildrenByNodeId(this.getLoginUser(), theNodeId, contextPath, showType, type);
    	
    	Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String jsons = json.toJson(treeNodes);
        ajax(jsons);
    }
    
    
    
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
    
	
    
}
