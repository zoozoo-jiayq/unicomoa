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
	
	public static ArrayList<Integer> onlineUserIdList=new ArrayList<Integer>();

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
//		ServletContext application = session.getServletContext();
		// 取得登录的用户名
		Object userObject = session.getAttribute("adminUser");
		UserInfo userInfo = (UserInfo) userObject;

		//-----
		if(userInfo!=null){
		onlineUserIdList.remove(userInfo.getUserId());
		}
		// System.out.println("#######-->logout:online userId list:"+Arrays.deepToString(OnlineUserListener.onlineUserIdList.toArray()));
        
		//-----
		
	
	}
}
