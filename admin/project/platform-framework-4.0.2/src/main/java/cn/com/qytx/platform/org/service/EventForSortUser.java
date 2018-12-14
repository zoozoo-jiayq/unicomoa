package cn.com.qytx.platform.org.service;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserGroup;

/**
 * 
 * <br/>功能:人员排序时间
 * <br/>版本: 1.0
 * <br/>开发人员: 吴洲
 * <br/>创建日期: 2015年5月4日
 * <br/>修改日期: 2015年5月4日
 * <br/>修改列表:
 */
public class EventForSortUser extends ApplicationEvent {

	
	private Integer companyId;
	public EventForSortUser(Integer source) {
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
