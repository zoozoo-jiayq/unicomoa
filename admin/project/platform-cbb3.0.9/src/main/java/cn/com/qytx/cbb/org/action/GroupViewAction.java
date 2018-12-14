package cn.com.qytx.cbb.org.action;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能:查看部门详细
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-4-8
 * <br/>修改日期: 2013-4-8
 * <br/>修改列表:
 */
public class GroupViewAction extends BaseActionSupport {

	/**
     * 部门,群组管理接口
     */
    @Resource(name = "groupService")
    private IGroup groupService;
	/**
     * 用户接口
     */
    @Resource(name = "userService")
    private IUser userService;
    
    @Resource(name="groupUserService")
    private IGroupUser groupUserService;

	private Integer groupId;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
     * 更新部门,群组
     * @return
     */
    public String loadGroupInfo()
    {
    	GroupInfo groupInfo = groupService.findOne(groupId);
    	if(groupInfo!=null){
        	this.getRequest().setAttribute("groupInfoVO", groupInfo);
        	//得到上级部门名称
        	Integer parentId=groupInfo.getParentId();
        	if(parentId!=null){	
            	GroupInfo parentGroupInfoVO=groupService.findOne(parentId);
            	if(parentGroupInfoVO!=null){
	            	String parentGroupName=parentGroupInfoVO.getGroupName();
	            	this.getRequest().setAttribute("parentGroupName", parentGroupName);
            	}
        	}
        	//得到部门主管
        	Integer directorId=groupInfo.getDirectorId();
        	if(directorId!=null){
        		UserInfo userInfo=userService.findOne( directorId);
        		if(userInfo!=null){
            		String directorName=userInfo.getUserName();
            		this.getRequest().setAttribute("directorName", directorName);
        		}
        	}
        	//得到部门助理
        	Integer assistantId=groupInfo.getAssistantId();
        	if(assistantId!=null){
        		UserInfo userInfo=userService.findOne( assistantId);
        		if(userInfo!=null){
            		String assistantName=userInfo.getUserName();
            		this.getRequest().setAttribute("assistantName", assistantName);
        		}
        	}
        	//得到上级主管领导
        	Integer topDirectorId=groupInfo.getTopDirectorId();
        	if(topDirectorId!=null){
        		UserInfo userInfo=userService.findOne(topDirectorId);
        		if(userInfo!=null){
            		String topDirectorName=userInfo.getUserName();
            		this.getRequest().setAttribute("topDirectorName", topDirectorName);
        		}
        	}
        	//得到上级分管领导
        	Integer topChangeId=groupInfo.getTopChangeId();
        	if(topChangeId!=null){
        		UserInfo userInfo=userService.findOne( topChangeId);
        		if(userInfo!=null){
            		String topChangeName=userInfo.getUserName();
            		this.getRequest().setAttribute("topChangeName", topChangeName);
        		}
        	}
        	//得到是否有子组
        	int childs = groupService.checkExistsChildGroup(getLoginUser().getCompanyId(),groupId);
        	boolean isHasChild = false;
        	if(childs>0){
        		isHasChild = true;
        	}
        	
        	if(isHasChild){
        		this.getRequest().setAttribute("isHasChild", 1);
        	}else{
        		this.getRequest().setAttribute("isHasChild", 0);
        	}
        	//得到是否组下有人
        	int gus = userService.getUserNumsByGroupId(getLoginUser().getCompanyId(),groupId);
        	
        	boolean isHasGroupUser = false;
        	if(gus>0){
        		isHasGroupUser = true;
        	}
        	if(isHasGroupUser){
        		this.getRequest().setAttribute("isHasGroupUser", 1);
        	}else{
        		this.getRequest().setAttribute("isHasGroupUser", 0);
        	}
    	}
        return SUCCESS;
    }
}
