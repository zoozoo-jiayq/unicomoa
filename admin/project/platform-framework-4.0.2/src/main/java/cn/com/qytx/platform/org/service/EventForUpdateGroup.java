package cn.com.qytx.platform.org.service;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.platform.org.domain.GroupInfo;

/**
 * 定义添加或者修改部门的事件
 * @author jiayongqiang
 *
 */
public class EventForUpdateGroup extends ApplicationEvent {

	private GroupInfo g;
	
	public EventForUpdateGroup(GroupInfo source) {
		super(source);
		// TODO Auto-generated constructor stub
		this.g = source;
	}

	@Override
	public GroupInfo getSource() {
		// TODO Auto-generated method stub
		return this.g;
	}
	
	

}
