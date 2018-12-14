package cn.com.qytx.cbb.login.event;

import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.EventForUpdateUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 功能:监听修改用户事件 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2016年2月29日
 * 修改日期: 2016年2月29日
 * 修改列表:
 */
@Component
public class ListenerForUpdateUser implements ApplicationListener<EventForUpdateUser> {

	@Async
	@Override
	public void onApplicationEvent(EventForUpdateUser event) {
		Map<String,Object> m = (Map<String,Object>) event.getSource();
		if(m!=null && m.get("userInfo")!=null){
			UserInfo u = (UserInfo) m.get("userInfo");
			if(u.getIsDefault()!=0 && !u.getPhone().equals(u.getLoginName())){
				IUser userService = (IUser) SpringUtil.getBean(IUser.class);
				u.setLoginName(u.getPhone());
				userService.saveOrUpdate(u);
			}
		}
	}

}
