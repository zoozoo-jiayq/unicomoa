package cn.com.qytx.cbb.util;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.com.qytx.platform.org.domain.UserInfo;


/**
 * 
 * 功能:得到在线人员列表 版本: 1.0 开发人员: 徐长江 创建日期: 2013-4-19 修改日期: 2013-4-19 修改列表:
 */
public class OnlineUserListener implements HttpSessionListener {
	
	public static  ArrayList<Integer> onlineUserIdList=new ArrayList<Integer>();
	private static Object lock = new Object();

	/**
	 * 
	 * 功能：
	 * 
	 * @param event
	 */
	public void sessionCreated(HttpSessionEvent event) {
		 
	}

	/**
	 * 
	 * 功能：
	 * 
	 * @param event
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		// 取得登录的用户名
		Object userObject = session.getAttribute("adminUser");
		UserInfo userInfo = (UserInfo) userObject;

		//-----
		if(userInfo!=null){
			removeOnlineUser(userInfo.getUserId());
		}
	}
	
	public static void removeOnlineUser(Integer userId){
		synchronized (lock) {
			onlineUserIdList.remove(userId);
		}
	}
	
	public static void addOnlineUser(Integer userId){
		synchronized (lock) {
			if(!onlineUserIdList.contains(userId)){
				onlineUserIdList.add(userId);
			}
		}
		
		System.out.println(getOnlineUser().toString()+"----------------");
	}
	
	public static ArrayList<Integer> getOnlineUser(){
		return onlineUserIdList;
	}
}
