package cn.com.qytx.oa.email.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.com.qytx.oa.email.service.IEmailBox;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.EventForDeleteUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;

@Component
public class ListenerForDeleteUser implements
		ApplicationListener<EventForDeleteUser> {

	
	@Async
	@Override
	public void onApplicationEvent(EventForDeleteUser event) {
		// TODO Auto-generated method stub
		List<UserInfo> userlist = (List<UserInfo>) event.getSource();
		IEmailBox emailBoxService = SpringUtil.getBean(IEmailBox.class);
		String ids = "";
		for(Iterator<UserInfo> it = userlist.iterator(); it.hasNext();){
			ids+=it.next().getUserId();
			if(it.hasNext()){
				ids+=",";
			}
		}
		 emailBoxService.deleteEmailBoxByUserIds(ids);
	}

}
