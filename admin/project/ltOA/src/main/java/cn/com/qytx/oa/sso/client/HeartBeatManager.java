package cn.com.qytx.oa.sso.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 功能：心跳管理器,单例模式
 * 作者： jiayongqiang
 * 创建时间：2014年7月2日
 */
public final class HeartBeatManager{
	private static HeartBeatManager instance = null;
	private HeartBeatManager(){
		
	}
	public static synchronized HeartBeatManager getInstance(){
		if(instance == null){
			instance = new HeartBeatManager();
		}
		return instance;
	}
	
	//心跳服务监听的SESSION对象
	private List<HttpSession> context = new ArrayList<HttpSession>();
	
	public int getTokenCount(){
		return context.size();
	}
	
	/**
	 * 功能：添加监听对象
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public synchronized void addSession(HttpSession session){
		this.context.add(session);
	}
	
	public synchronized void removeSession(HttpSession session){
		this.context.remove(session);
	} 
	
	/**
	 * 功能：向制定的服务发送心跳请求
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 */
	public void sendHeartBeatRequest(String ip,int port){
		for(int i=0; i<context.size(); i++){
			HttpSession session = context.get(i);
			String token = (String) session.getAttribute("sso_token");
			if(token !=null){
				Map<String,String> m = new HashMap<String,String>();
				m.put("type", "heart");
				m.put("sso_token", token);
				NioClient client = new NioClient(ip, port);
				client.send(m);

			}
		}
		
	}
}
