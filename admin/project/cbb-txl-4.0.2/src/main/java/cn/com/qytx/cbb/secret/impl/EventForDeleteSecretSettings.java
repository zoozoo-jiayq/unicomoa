package cn.com.qytx.cbb.secret.impl;

import org.springframework.context.ApplicationEvent;

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
