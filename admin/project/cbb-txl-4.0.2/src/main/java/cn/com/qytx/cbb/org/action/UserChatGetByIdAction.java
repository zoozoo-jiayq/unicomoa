package cn.com.qytx.cbb.org.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;


/**
 * <br/>功能: 得到用户详情
 * <br/>版本: 1.0
 * <br/>开发人员: 任
 * <br/>创建日期: 2013-4-10
 * <br/>修改日期: 2013-4-10
 * <br/>修改列表:
 */
public class UserChatGetByIdAction extends BaseActionSupport {

    /**
     * 用户信息
     */
    @Resource(name = "userService")
    IUser userService;
    /**
     * 部门，群组管理接口
     */
    @Resource(name = "groupService")
    IGroup groupService;

    /**
     * 角色人员信息
     */
    @Resource(name = "roleUserService")
    IRoleUser roleUserService;

    private Integer userId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
   private Integer userids;
   
    public Integer getUserids() {
	return userids;
}

public void setUserids(Integer userids) {
	this.userids = userids;
}

	/**
     * 功能：获取文件夹的权限列表
     * @return
     */
    public String getUserById()
    {
    	UserInfo user= userService.findOne(userId);
        int sex = user.getSex();
        Map map = new HashMap();
        map.put("sex", sex);
        Gson json = new Gson();
        String jsonChat = json.toJson(map);
        ajax(jsonChat);
        return null;
    }
    /**
     * 功能：
     * @return
     */
    public String getUserByGroupId(){
    	 List<UserInfo>  userinfolist=userService.findUsersByGroupId(String.valueOf(userids));
    	 StringBuffer finaluserids=new StringBuffer();
    	 for(UserInfo userinfo:userinfolist)
    	 {
    		 finaluserids.append(userinfo.getUserId()+",");
    		 
    	 }
        Map map = new HashMap();
        if(finaluserids.toString()!=null&&!"".equals(finaluserids.toString())){
        	map.put("finaluserids", finaluserids.toString().substring(0, finaluserids.toString().length()-1));         	
        }else{
        	map.put("finaluserids", "");
        }
        Gson json = new Gson();
        String jsonChat = json.toJson(map);
        ajax(jsonChat);
        return null;
    }
}
