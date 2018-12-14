package cn.com.qytx.cbb.groupExt.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.groupExt.impl.EventForAddUserToGroup;
import cn.com.qytx.cbb.groupExt.service.IGroupExt;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.event.PublishEventUtil;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;

public class GroupExtAction extends BaseActionSupport{
	private static final long serialVersionUID = 2368581078937996191L;

	@Autowired
	IGroupExt groupExtService;
	
	@Autowired
	IGroup groupService;
	@Autowired
	IRole roleService;
	@Autowired
	IGroupUser groupUserService;
	@Autowired
	IUser userService;
	
	
	public int groupId = 0;
	public String searchName;
	
	public String userIds;
	
	public String ids;
	public String userNames;
	public Integer sex;
	public Integer companyId;
	public void getUserByGroupExtId(){
		try{
			Sort sort = new Sort(new Sort.Order(Direction.ASC, "orderIndex"));
			if (companyId==null) {
				companyId=getLoginUser().getCompanyId();
			}
			Integer loginId=getLoginUser().getUserId();
			Page<UserInfo> page = groupExtService.getPageUserListByGroupId(companyId,this.getPageable(sort), groupId,searchName,sex);
			
			List<UserInfo> userList = page.getContent();  //获取结果

	        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
	        if (userList != null) {
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        	  int n = page.getNumber() * page.getSize() + 1;
	            for(int i=0; i<userList.size(); i++) {
	            	UserInfo user = userList.get(i);
	                Map<String, Object> map = new HashMap<String, Object>();
	                map.put("id", user.getUserId());
	                map.put("loginId", loginId);
	                map.put("no", n);
	                n++;
	                map.put("mobileShowState", user.getMobileShowState());
	                
	                //用户名
	                String loginNameOut=user.getLoginName();
	                if(StringUtils.isNotEmpty(loginNameOut)) {
	                    map.put("loginName", loginNameOut);
	                } else {
	                    map.put("loginName", "&nbsp;");
	                }
	                //姓名
	                String userNameOut=user.getUserName();
	                if(StringUtils.isNotEmpty(userNameOut)) {
	                    map.put("userName", userNameOut);
	                } else {
	                    map.put("userName", "&nbsp;");
	                }
	                //性别
	                Integer sexOut=user.getSex();
	                if(sexOut!=null){
	                	if(sexOut.intValue() == 0){
	                		map.put("sex", "女");
	                	}else{
	                		map.put("sex", "男");
	                	}
	                }else{
	                    map.put("sex", "&nbsp;");
	                }
	                //最后登录日期
	                Date lastLoginTime=user.getLastLoginTime();
	                if(lastLoginTime!=null) {
	                    map.put("lastLoginTime",  new SimpleDateFormat("yyyy-MM-dd HH:mm").format(lastLoginTime));
	                } else {
	                    map.put("lastLoginTime", "&nbsp;");
	                }
	                //手机号
	                String phoneOut=user.getPhone();
	                if(StringUtils.isNotEmpty(phoneOut)) {
	                    map.put("phone", phoneOut);
	                } else {
	                    map.put("phone", "&nbsp;");
	                }
	                // 是否公开
	                Integer phonePublic  = user.getPhonePublic();
	                if(null != phonePublic) {
	                    map.put("phonePublic", phonePublic);
	                } else {
	                    map.put("phonePublic", "&nbsp;");
	                }
	                
	                //注册日期
	                Date registerTime=user.getRegisterTime();
	                if(registerTime!=null) {
	                    map.put("registerTime",  new SimpleDateFormat("yyyy-MM-dd").format(user.getRegisterTime()));
	                } else {
	                    map.put("registerTime", "&nbsp;");
	                }
	                
	                Date createTime=user.getCreateTime();
	                if(createTime!=null) {
	                    map.put("createTime",  new SimpleDateFormat("yyyy-MM-dd").format(createTime));
	                } else {
	                    map.put("createTime", "&nbsp;");
	                }
	                
	                
	                //用户是否禁止登录
	                Integer userStateOut=user.getUserState();
	                if(userStateOut==null){
	                	userStateOut=2;
	                }
	                map.put("userState", userStateOut);
	                //是否系统用户0,系统用户,1,普通用户
	                /*   Integer isDefault=user.getIsDefault();
	                if(isDefault==null){
	                	isDefault=1;
	                }
	                if(isDefault.intValue() == 1){
                	map.put("isDefault", "普通用户");
                   }else{
                	map.put("isDefault", "系统用户");
                    }*/
	                
	                Integer isDefault=user.getIsDefault();
                    if(isDefault==null){
                    	isDefault=1;
                    }
                    map.put("isDefault", isDefault);
	              
	                
	                // 职务
	                String job = user.getJob();
	                map.put("job", job == null ? "&nbsp;":job);
	                
	             // 称呼
	                String title ="-";
	                if(StringUtils.isNotBlank(user.getTitle())){
	                	title= user.getTitle();
	                }	               
	                map.put("title", title);
	                
	             // v网短号
	                String vnum = "-";
	                if(StringUtils.isNotBlank(user.getvNum())){
	                	vnum= user.getvNum();
	                }
	                map.put("vnum",vnum);
	                
	                // 角色
	                List<RoleInfo> roleList = roleService.getRoleByUser(user.getUserId());
	                StringBuffer roleString = new StringBuffer();
	                if (null != roleList && !roleList.isEmpty()){
	                    for (int r = 0 ; r < roleList.size(); r ++){
	                        roleString.append(roleList.get(r).getRoleName());
	                        if (r != roleList.size() - 1){
	                            roleString.append(',');
	                        }
	                    }
	                }
	                map.put("role", roleString.toString());
	                
	                // 部门path
	                String groupName = "";
	                GroupInfo g = groupService.getGroupByUserId(user.getCompanyId(),user.getUserId());
	                if(g!=null){
	                	groupName = g.getGroupName();
	                }
	                map.put("groupName", groupName);
	                mapList.add(map);
	            }
	        }
	        ajaxPage(page, mapList);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	 private String getGroupName(final List<GroupInfo> groupList) {
	        GroupInfo group = null;
	        for (GroupInfo info : groupList) {
	            //类型为部门
	            if (info.getGroupType() == 1) {
	                group = info;
	                break;
	            }
	        }
	        if(group!=null){
	        	return group.getGroupName();
	        }else{
	        	return "";
	        }
	    }

	 /**
	  * 将人员移动到群组
	  * @return
	  */
	 public void moveUserToGroup(){
		 try{
			 if(groupId==0||userIds==null||userIds.equals("")){
				 ajax(2);
				 return;
			 }
			 groupExtService.moveUserToGroup(userIds,groupId);
			 groupService.updateGroupTime(groupId);
			//发布群组新增人员广播
			 PublishEventUtil.publishEvent(new EventForAddUserToGroup(getLoginUser().getCompanyId()));
			 ajax(1);
		 }catch(Exception e){
			 e.printStackTrace();
			 ajax(0);
		 }
	 }
	 
	 /**
	  * 将人员从群组中删除
	  * @return
	  */
	 public void moveOutUserFromGroup(){
		 try{
			 if(ids==null||ids.equals("")){
				 ajax(2);
				 return;
			 }
			groupExtService.moveOutUserFromGroup(ids,groupId);
			groupService.updateGroupTime(groupId);
			 ajax(1);
		 }catch(Exception e){
			 e.printStackTrace();
			 ajax(0);
		 }
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
	        	int gus = groupUserService.getUsersCountBySetId(groupId);
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
	 
	/**
	 * 到群组新增页面    
	 * @return
	 */
	public String toAddGoupUser(){
		List<GroupUser> list = groupExtService.getGroupUserByGoupId(groupId);
		userIds = "";
		userNames = "";
		if(list!=null&&list.size()>0){
			for(GroupUser groupUser:list){
				userIds += groupUser.getUserId()+",";
				userNames += userService.findOne(groupUser.getUserId()).getUserName()+",";
			}
		}
		return SUCCESS;
	}
	
	 
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	
}

