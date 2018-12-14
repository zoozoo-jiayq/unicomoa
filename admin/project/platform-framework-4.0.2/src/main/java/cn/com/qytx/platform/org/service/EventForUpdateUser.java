package cn.com.qytx.platform.org.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * @author jiayongqiang
 * 定义事件：创建用户的时候，发布事件，参数是添加用户的ID
 */
public class EventForUpdateUser extends ApplicationEvent {
	
	private UserInfo u;
	private String phone;
	
	public EventForUpdateUser(UserInfo u,String phone) {
		super(u);
		this.u = u;
		this.phone = phone;
	}

	@Override
	public Object getSource() {
		// TODO Auto-generated method stub
		Map<String,Object> s = new HashMap<String, Object>();
		s.put("userInfo", this.u);
		s.put("oldPhone", this.phone);
		return s;
	}

}
