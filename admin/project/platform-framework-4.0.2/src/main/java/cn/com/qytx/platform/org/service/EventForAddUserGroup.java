package cn.com.qytx.platform.org.service;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserGroup;

/**
 * 定义添加或者修改部门的事件
 * @author jiayongqiang
 *
 */
public class EventForAddUserGroup extends ApplicationEvent {

	private UserGroup g;
	
	public EventForAddUserGroup(UserGroup source) {
		super(source);
		// TODO Auto-generated constructor stub
		this.g = source;
	}

	@Override
	public UserGroup getSource() {
		// TODO Auto-generated method stub
		return this.g;
	}
	
	

}
