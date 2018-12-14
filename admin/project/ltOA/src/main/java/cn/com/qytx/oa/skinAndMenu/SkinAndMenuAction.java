package cn.com.qytx.oa.skinAndMenu;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
/**
 * 功能:换肤 和 菜单样式 
 * 版本:1.0
 * 开发人员: 潘博
 * 创建日期: 2014-12-17
 * 修改日期: 2014-12-17
 * 修改人员: 
 */
public class SkinAndMenuAction extends BaseActionSupport{
	private static final long serialVersionUID = 1L;
	private Integer userId;//用户 ID
	
	@Resource(name="userService")
	private IUser userService; //用户业务 接口
	@Override
	public String execute() throws Exception {
		UserInfo userInfo = (UserInfo)this.getRequest().getSession().getAttribute("adminUser");
		if(userInfo != null){
			UserInfo tempUserInfo = this.userService.findOne(userInfo.getUserId());
			tempUserInfo.setSkinLogo(userInfo.getSkinLogo());
			userService.saveOrUpdate(tempUserInfo);
		}
		return null;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
