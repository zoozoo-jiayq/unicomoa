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
public class EventForDeleteSecretSettings extends ApplicationEvent {
	
	private Integer companyId;
	
	public EventForDeleteSecretSettings(Integer companyId) {
		super(companyId);
		// TODO Auto-generated constructor stub
		this.companyId = companyId;
	}

	@Override
	public Integer getSource() {
		// TODO Auto-generated method stub
		return this.companyId;
	}
}
