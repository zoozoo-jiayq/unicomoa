package cn.com.qytx.cbb.secret.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.cbb.secret.domain.SecretSettings;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserGroup;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 定义添加或者修改部门的事件
 * @author jiayongqiang
 *
 */
public class EventForUpdateSecretSettings extends ApplicationEvent {
	
private SecretSettings ss;
	
	public EventForUpdateSecretSettings(SecretSettings source) {
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
