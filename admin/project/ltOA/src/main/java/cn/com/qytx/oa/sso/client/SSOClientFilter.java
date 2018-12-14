package cn.com.qytx.oa.sso.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 功能：SSO客户端配置实现
 * 作者： jiayongqiang
 * 创建时间：2014年7月1日
 */
public class SSOClientFilter implements Filter {
	//SSO主机地址
	private String host = "";
	private int port = 0 ;
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)arg0;
		String token = request.getParameter("sso_token");
		
		//如果是SSO登录,如果请求token与session中的不一致，则向SSO服务器发出鉴权请求
		if(token!=null && !token.equals("")){
			
			NioClient client = new NioClient(host, port);
			Map<String,String> m = new HashMap<String,String>();
			m.put("type", "check");
			m.put("sso_token", token);
			Map<String,Object> u = (Map<String, Object>) client.send(m);
			if(u!=null){
				request.getSession().setAttribute("sso_token", token);
				arg2.doFilter(new SSORequestWraper(request,u), arg1);
			}else{
				arg2.doFilter(arg0, arg1);
			}
					
		}else{
			arg2.doFilter(arg0, arg1);
		}
		
	}

	public void init(FilterConfig cfg) throws ServletException {
		// TODO Auto-generated method stub
		host = cfg.getInitParameter("host");
		String p = cfg.getInitParameter("port");
		port = Integer.parseInt(p);
		
		//启动心跳线程
		new Heart(host,port).start();
		
	}
	
	/**
	 * 功能：创建心跳线程
	 * 作者： jiayongqiang
	 * 创建时间：2014年7月2日
	 */
	class Heart extends Thread{
		private String ip;
		private int port;
		
		public Heart(String ip,int port) {
			super();
			this.ip = ip;
			this.port = port;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try {
					Thread.sleep(10*60*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				HeartBeatManager.getInstance().sendHeartBeatRequest(ip,port);
			}
		}
	}

}
