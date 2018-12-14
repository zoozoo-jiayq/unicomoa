package cn.com.qytx.cbb.groupExt.impl;

import org.springframework.context.ApplicationEvent;

/**
 * 定义添加或者修改部门的事件
 * @author jiayongqiang
 *
 */
public class EventForDeleteUserToGroup extends ApplicationEvent {

	
	private Integer companyId;
	public EventForDeleteUserToGroup(Integer source) {
		super(source);
		// TODO Auto-generated constructor stub
		this.companyId = source;
	}
	@Override
	public Integer getSource() {
		// TODO Auto-generated method stub
		return this.companyId;
	}

	

}
