package cn.com.qytx.platform.org.service;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 删除用户的时候，发布的事件,参数是用户列表(List<UserInfo>)
 * @author jiayongqiang
 *
 */
public class EventForDeleteUser extends ApplicationEvent {

	private List<UserInfo> ulist;
	public EventForDeleteUser(List<UserInfo> userList) {
		super(userList);
		this.ulist=userList;
	}
	
	public List<UserInfo> getSource(){
		return this.ulist;
	}

}
