package cn.com.qytx.oa.myWait;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

public class MyWait {
	@Autowired
	IUser userService;
	
	@Autowired
	private IMyWaitProcess myWaitProcessService;
	
	public void execute(){
		System.out.println("定时器已经启动");
		List<UserInfo> list = userService.findAll();
		String toIds="";
		UserInfo pUser = null;
		int i = 0;
		if(list!=null && list.size()>0){
			for(UserInfo user:list){
				if(user.getIsDelete()==0){
					int count = myWaitProcessService.myWaitCount(user.getUserId());
					if(count > 0){
						toIds+=user.getUserId()+",";
						pUser = user;
						i++;
					}
				}
			}
		}
		if(toIds.length()>0){
		}
		System.out.println("共有"+i+"个推送目标");
	}
}
