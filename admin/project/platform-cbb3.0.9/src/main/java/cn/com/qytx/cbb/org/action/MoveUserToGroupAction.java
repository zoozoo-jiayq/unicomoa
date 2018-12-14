package cn.com.qytx.cbb.org.action;

import java.io.PrintWriter;

import javax.annotation.Resource;

import org.jadira.usertype.spi.utils.lang.StringUtils;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IUser;
/**
 * 功能:移动人员到群组
 * <br/>版本: 1.0
 * <br/>开发人员: 潘博
 * <br/>创建日期: 2014-7-24
 * <br/>修改日期: 2014-7-24
 * <br/>修改列表:
 */
public class MoveUserToGroupAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	/**用户信息*/
	@Resource(name = "userService")
	IUser userService;
	/**组用户信息*/
	@Resource(name = "groupUserService")
	IGroupUser groupUserService; 
	/**
     * 部门,群组管理接口
     */
    @Resource(name="groupService")
    private IGroup groupService;
    
    private String userIds;
    
    private Integer groupId;
    private Integer oldGroupId;
    
    public Integer getOldGroupId() {
		return oldGroupId;
	}

	public void setOldGroupId(Integer oldGroupId) {
		this.oldGroupId = oldGroupId;
	}

	private String groupName;
    
    public String moveUserToGroup(){
    	PrintWriter out = null;
    	try{
    		UserInfo userInfo = this.getLoginUser();
    		out = new PrintWriter(this.getResponse().getWriter());
    		if(userInfo != null){
    			if(StringUtils.isNotEmpty(this.userIds) && StringUtils.isNotEmpty(this.groupId+"") && StringUtils.isNotEmpty(groupName) && StringUtils.isNotEmpty(oldGroupId+"")){
    				   
    				  if(userIds.startsWith(",")){
    					  userIds = userIds.substring(1,userIds.length());
    				  }
    				  if(userIds.endsWith(",")){
    					  userIds = userIds.substring(0,userIds.length()-1);
    				  }
    				  this.groupUserService.deleteGroupUserByUserIds(userInfo.getCompanyId(), oldGroupId, userIds);
    				  String userIdArray[] = userIds.split(",");
    				  if(userIdArray != null && userIdArray.length > 0){
    					  GroupUser gUser = null;
    					  for(int i =0;i < userIdArray.length;i++){
    						  gUser = new GroupUser();
    						  gUser.setCompanyId(userInfo.getCompanyId());
    						  gUser.setUserId(Integer.parseInt(userIdArray[i]));
    						  gUser.setGroupId(groupId);
    						  this.groupUserService.saveOrUpdate(gUser);
    					  }
    				  }
    				out.print(0);
    			}else{
    				out.print(1);
    			}
    		}else{
    			out.print(1);
    		}
    	}catch(Exception e){
    		LOGGER.error(e.getMessage());
    	}finally{
    		if(out != null)
    			out.close();
    	}
    	return null;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
    
    
}
