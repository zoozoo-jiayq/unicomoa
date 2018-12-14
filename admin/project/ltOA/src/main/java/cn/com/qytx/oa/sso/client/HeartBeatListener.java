package cn.com.qytx.oa.sso.client;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 功能：监听session的创建和销毁
 * 作者： jiayongqiang
 * 创建时间：2014年7月2日
 */
public class HeartBeatListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		HeartBeatManager.getInstance().addSession(session);
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		HeartBeatManager.getInstance().removeSession(session);
	}

}
