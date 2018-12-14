package cn.com.qytx.cbb.secret.impl;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.cbb.secret.domain.SecretSettings;

/**
 * 定义添加或者修改部门的事件
 * @author jiayongqiang
 *
 */
public class EventForAddSecretSettings extends ApplicationEvent {

	private SecretSettings ss;
	
	public EventForAddSecretSettings(SecretSettings source) {
		super(source);
		// TODO Auto-generated constructor stub
		this.ss = source;
	}

	@Override
	public SecretSettings getSource() {
		// TODO Auto-generated method stub
		return this.ss;
	}
	
	

}
