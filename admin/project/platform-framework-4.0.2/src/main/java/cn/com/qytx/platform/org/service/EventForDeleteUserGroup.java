package cn.com.qytx.platform.org.service;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserGroup;

/**
 * 定义添加或者修改部门的事件
 * @author jiayongqiang
 *
 */
public class EventForDeleteUserGroup extends ApplicationEvent {

	
	public EventForDeleteUserGroup(String ids) {
		super(ids);
	}

}
