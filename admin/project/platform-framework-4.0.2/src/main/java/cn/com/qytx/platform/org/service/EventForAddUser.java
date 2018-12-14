package cn.com.qytx.platform.org.service;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * @author jiayongqiang
 * 定义事件：创建用户的时候，发布事件，参数是被添加的用户(UserInfo)
 */
public class EventForAddUser extends ApplicationEvent {

	private UserInfo u;
	
	public EventForAddUser(UserInfo u) {
		super(u);
		this.u = u;
	}

	@Override
	public Object getSource() {
		return u;
	}

}
