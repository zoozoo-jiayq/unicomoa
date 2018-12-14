package cn.com.qytx.platform.org.service;

import org.springframework.context.ApplicationEvent;

/**
 * 发布批量保存用户事件
 * @author jiayongqiang
 *
 */
public class EventForBatchAddUser extends ApplicationEvent {

	public EventForBatchAddUser(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
