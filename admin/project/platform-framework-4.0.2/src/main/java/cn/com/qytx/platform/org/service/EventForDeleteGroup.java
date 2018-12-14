package cn.com.qytx.platform.org.service;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.platform.org.domain.GroupInfo;

/**
 * 定义删除部门的事件,参数是部门列表(List<GroupInfo>)
 * @author jiayongqiang
 *
 */
public class EventForDeleteGroup extends ApplicationEvent {

	private List<GroupInfo> glist;
	
	public EventForDeleteGroup(List<GroupInfo> glist) {
		super(glist);
		this.glist = glist;
	}

	@Override
	public List<GroupInfo> getSource() {
		return this.glist;
	}

}
