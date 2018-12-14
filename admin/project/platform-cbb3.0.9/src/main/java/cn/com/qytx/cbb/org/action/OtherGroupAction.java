package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

/**
 * 功能 外部部门  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月6日
 * <br/>修改日期  2016年1月6日
 * <br/>修改列表
 */
public class OtherGroupAction extends BaseActionSupport {

	@Autowired
	IUser userService;
	
	@Autowired
	IGroup groupService;
	
	/**
	 * 功能：用户id
	 */
	private Integer userId;
	
	/**
	 * 功能：加载外部部门列表
	 */
	public void loadOtherGroups(){
		try{
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			UserInfo userInfo = userService.findOne(userId);
			List<UserInfo> virtualUserList = userService.findVirtualUsers(userId, userInfo.getCompanyId());
			if(virtualUserList!=null && virtualUserList.size()>0){
				for(int i = 0;i<virtualUserList.size();i++){
					UserInfo user = virtualUserList.get(i);
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("id", user.getUserId()+"");
					map.put("groupId", user.getGroupId()+"");
					map.put("groupName", groupService.findOne(user.getGroupId()).getGroupName());
					map.put("isDelete", "0");
					map.put("job", user.getJob()==null?"":user.getJob());
					map.put("telphone", user.getOfficeTel()==null?"":user.getOfficeTel());
					map.put("orderIndex", user.getOrderIndex());
					list.add(map);
				}
			}
			Gson json = new Gson();
			ajax(json.toJson(list));
		}catch(Exception e){
			ajax("[]");
		}
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
