package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;



/**
 * @Description:  [角色列表ACTION]   
 * @Author:       [REN]   
 * @CreateDate:   [2012-10-12 上午09:31:32]   
 * @UpdateUser:   [REN]   
 * @UpdateDate:   [2012-10-12 上午09:31:32]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
 */
public class RoleListAction  extends BaseActionSupport{
	/**角色信息*/
	@Resource(name = "roleService")
	IRole roleService;
	/**角色人员信息*/
	@Resource(name = "roleUserService")
	IRoleUser roleUserService;
	@Resource(name="userService")
	private IUser userService;
	
	private Integer roleId;
	
	/**
	 * 
	 * @Title: findRoleList 
	 * @Description: TODO(得到角色列表) 
	 * @param
	 * @return String    返回类型
	 */
	@SuppressWarnings("unchecked")
	public String findRoleList(){
        Page<RoleInfo> pageInfo = roleService.findByPage(getPageable());
        List<RoleInfo> roleList = pageInfo.getContent();
        int count = this.getIDisplayStart()+1;
        //把角色信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (roleList != null) {
            for(int i=0; i<roleList.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                RoleInfo role = roleList.get(i);
                map.put("id", role.getRoleId());
                
                map.put("no", (pageInfo.getNumber())*(pageInfo.getSize())+i+1);
                //角色名称
                String roleNameOut=role.getRoleName();
                if(StringUtils.isNotEmpty(roleNameOut)) {
                    map.put("roleName", roleNameOut);
                } else {
                    map.put("roleName", "&nbsp;");
                }
                if(StringUtils.isNotEmpty(roleNameOut)) {
                    map.put("roleNameView", roleNameOut);
                } else {
                    map.put("roleNameView", "&nbsp;");
                }
                //角色代码
                String roleCodeOut=role.getRoleCode();
                if(StringUtils.isNotEmpty(roleCodeOut)) {
                    map.put("roleCode", roleCodeOut);
                } else {
                    map.put("roleCode", "&nbsp;");
                }
                //角色描述
                String memoOut=role.getMemo();
                if(StringUtils.isNotEmpty(memoOut)) {
                    map.put("memo", memoOut);
                } else {
                    map.put("memo", "&nbsp;");
                }
                //角色类型
                Integer roleTypeOut=role.getRoleType();
                if(roleTypeOut==null) {
                    map.put("roleType", 1);
                } else {
                    map.put("roleType", roleTypeOut);
                }
                //用户列表
                //主角色
               // List<UserInfo> usersList=userService.findUsersByRoleId(role.getRoleId(),getLoginUser().getCompanyId(),1);
                map.put("usersList", "");
                //辅助角色
              //  List<UserInfo> usersListAssist=userService.findUsersByRoleId(role.getRoleId(),getLoginUser().getCompanyId(),0);
                map.put("usersListAssist", "");
                mapList.add(map);
            }
        }
        ajaxPage(pageInfo, mapList);
		return null;
	}
	
	
	/**
	 * 
	 * @Title: findRoleList 
	 * @Description: TODO(得到角色用户) 
	 * @param
	 * @return String    返回类型
	 */
	@SuppressWarnings("unchecked")
	public String findRoleUser(){
		String userIds = "";
		String userNames = "";
		String userNameHtml = "";
          //主角色
          List<UserInfo> usersList=userService.findUsersByRoleId(roleId,getLoginUser().getCompanyId(),1);
          //辅助角色
          List<UserInfo> usersListAssist=userService.findUsersByRoleId(roleId,getLoginUser().getCompanyId(),0);
          Map<String,Object>  map = new HashMap<String,Object>();
          if(usersList!=null&&usersList.size()>0){
        	  for(UserInfo userInfo :usersList){
        		  Integer userId = userInfo.getUserId();
        		  String userName = userInfo.getUserName();
        		  userIds+=userId+",";
        		  userNames+=userName+",";
        		  userNameHtml+="<span>"+userName+"、</span>";
        	  }
          }
          /**if(usersListAssist!=null&&usersListAssist.size()>0){
        	  for(UserInfo userInfo :usersListAssist){
        		  Integer userId = userInfo.getUserId();
        		  String userName = userInfo.getUserName();
        		  userIds+=userId+",";
        		  userNames+=userName+",";
        		  userNameHtml+="<span>"+userName+"、</span>";
        	  }
          }**/
          if(userNameHtml.endsWith("、</span>")){
        	  userNameHtml = userNameHtml.substring(0,userNameHtml.length()-8)+"</span>";
          }
          
          this.getRequest().setAttribute("userIds",userIds);
          this.getRequest().setAttribute("userNames",userNames);
          this.getRequest().setAttribute("userNameHtml",userNameHtml);
		 return SUCCESS;
	}


	
	/**
	 * 
	 * @Title: findRoleList 
	 * @Description: TODO(得到角色用户) 
	 * @param
	 * @return String    返回类型
	 */
	@SuppressWarnings("unchecked")
	public String findRoleUserAjax(){
		 String userIds = "";
          //主角色
          List<UserInfo> usersList=userService.findUsersByRoleId(roleId,getLoginUser().getCompanyId(),1);
          //辅助角色
          List<UserInfo> usersListAssist=userService.findUsersByRoleId(roleId,getLoginUser().getCompanyId(),0);
          Map<String,Object>  map = new HashMap<String,Object>();
          if(usersList!=null&&usersList.size()>0){
        	  for(UserInfo userInfo :usersList){
        		  Integer userId = userInfo.getUserId();
        		  userIds+=userId+",";
        	  }
          }
          ajax(userIds);
		 return null;
	}

	
		/**
		 * @return the roleId
		 */
		public Integer getRoleId() {
			return roleId;
		}


		/**
		 * @param roleId the roleId to set
		 */
		public void setRoleId(Integer roleId) {
			this.roleId = roleId;
		}
	
	
	
	
}
