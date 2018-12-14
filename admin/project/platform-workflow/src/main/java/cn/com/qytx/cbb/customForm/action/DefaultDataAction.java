package cn.com.qytx.cbb.customForm.action;

import javax.annotation.Resource;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;

/**
 * 功能  获得基础信息 当前登录人等
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月8日
 * <br/>修改日期  2016年1月8日
 * <br/>修改列表
 */
public class DefaultDataAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	@Resource(name="groupService")
	IGroup groupService;
	
	/**
	 * 功能：获得登录人信息
	 * @return 登录人用户名
	 * @throws Exception
	 */
	public String getLoginUserName() throws Exception{
		UserInfo ui = getLoginUser();
		ajax(ui.getUserName());
		return null;
	}
	
	/**
	 * 功能：获得登录人所在部门的名字
	 * @return
	 * @throws Exception
	 */
	public String getLoginUserGroup() throws Exception{
		UserInfo ui = getLoginUser();
		GroupInfo g = groupService.findOne(ui.getGroupId());
		 if(g!=null){
	            ajax(g.getGroupName());
	        }else {
				ajax("");
			}
		return null;
	}
}
