package cn.com.qytx.cbb.org.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.encrypt.MD5;

/**
 * 
 * <br/>
 * 功能: 更新用户状 <br/>
 * 版本: 1.0 <br/>
 * 创建日期: 2013-4-9 <br/>
 * 修改日期: 2013-4-9 <br/>
 * 修改列表:
 */
public class UserSetAction extends BaseActionSupport {

	/** 用户信息 */
	@Resource(name = "userService")
	IUser userService;

	@Resource(name = "groupUserService")
	IGroupUser groupUserService;

	private String userIds = "";

	private Integer userId;

	private Integer userState;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	/**
	 * 更新登录状�?
	 * 
	 * @return
	 */
	public String updateUserState() {
		if (userId != null && userState != null) {
			UserInfo userInfo = userService.findOne(userId);
			if (userInfo != null) {
				userInfo.setUserState(userState);
				userService.saveOrUpdate(userInfo);
				ajax("");
			}
		} else {
			ajax("请选择要更新的人员！");
		}
		return null;
	}

	/**
	 * 更新密码
	 * 
	 * @return
	 */
	public String updateUserPassword() {
		if (StringUtils.isNotEmpty(userIds)) {
			if (userIds.endsWith(",")) {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			if (userIds.startsWith(",")) {
				userIds = userIds.substring(1, userIds.length());
			}
			MD5 md5 = new MD5();
			String pass = md5.encrypt("123456");
			userService.updatePasswordByIds(userIds, pass);
			ajax("");
			return null;
		} else {
			ajax("请选择要更新的人员！");
			return null;
		}
	}

}
