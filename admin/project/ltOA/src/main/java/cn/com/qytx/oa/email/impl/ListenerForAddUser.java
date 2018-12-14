package cn.com.qytx.oa.email.impl;

import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.com.qytx.oa.email.service.IEmailBox;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.EventForAddUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * @author jiayongqiang
 *  添加用户的时候会触发该监听器
 */
@Component
public class ListenerForAddUser implements
		ApplicationListener<EventForAddUser> {

	@Async
	@Override
	public void onApplicationEvent(EventForAddUser event) {
		// TODO Auto-generated method stub
		Map<String,Object> map =  (Map<String, Object>) event.getSource();
		if(map!=null){
			IEmailBox emailBoxService = SpringUtil.getBean(IEmailBox.class);
			Integer userId =  (Integer) map.get("userId");
			UserInfo currentUser  = (UserInfo) map.get("currentUser");
			emailBoxService.saveDefaultAfterCheckExist(userId,currentUser);
		}
	}

}
