package cn.com.qytx.cbb.sso.server;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.qytx.cbb.sso.server.cache.SSOCache;
import cn.com.qytx.cbb.sso.server.cache.SSOCacheListener;
import cn.com.qytx.platform.base.application.SpringContextHolder;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.encrypt.MD5;

import com.google.gson.Gson;

/**
 * 功能：SSO实现
 * 作者： jiayongqiang
 * 创建时间：2014年7月1日
 */
public class SSOAuthority extends HttpServlet {
	
	//使用用户名密码登陆地址
	private final String URL_CHECK_LOGIN = "/sso/login";
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		final String port = config.getInitParameter("port");
		//启动缓存扫描线程
		new Thread(new SSOCacheListener()).start();
		//启动监听线程，1,监听客户端的心跳请求，2,监听客户端的验证token请求
		if(port!=null && !port.equals("")){
			try {
				new TcpServer(Integer.parseInt(port));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		uri = uri.substring(context.length());
		
		//登陆鉴权
		if(uri.equalsIgnoreCase(URL_CHECK_LOGIN)){
			checkUserName(request, response);
		}
	}
	
	/**
	 * 功能：验证用户的用户名和密码是否正确
	 * 作者：jiayongqiang
	 * 参数：
	 * 输出：
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void checkUserName(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		String loginName = request.getParameter("j_username");
		String password = request.getParameter("j_password");
//		String checkCode = request.getParameter("checkCode");
		IUser userService = SpringContextHolder.getBean(IUser.class);
		UserInfo u = userService.findByLoginName(loginName);
		Map<String,String> result = new HashMap<String,String>();
		//验证码不需要验证
//	    String sessionRand = (String)request.getSession().getAttribute("rand");
//	    if (!StringUtils.isEmpty(checkCode) && !StringUtils.isEmpty(sessionRand)){
//	        if (checkCode.equals(sessionRand)){}else{
//	        	result.put("result", "codeError");
//	        }
//		}
		

    	if(u!=null){
			u.setOrialPassword(password);
			MD5 md5=new MD5();
	    	String pass = md5.encrypt(password);
			if(u.getLoginPass().equals(pass) && u.getUserState()!=null && u.getUserState() == 0){
				String token = UUID.randomUUID().toString();
				SSOCache.getInstance().store(token, u);
				request.setAttribute("sso_token", token);
				result.put("sso_token", token);
				result.put("result", "success");
			}else{
				result.put("result", "forbid");
				if(u!=null&&!u.getLoginPass().equals(pass)){
					//密码错误
					Log log = new Log();
					log.setCompanyId(u!=null?u.getCompanyId():1);
					log.setInsertTime(new Timestamp(new Date().getTime()));
					log.setIp(request.getRemoteAddr());
					log.setIsDelete(0);
					log.setLogType(LogType.LOG_LOGIN_PASSWARD);//对照LogType类中的常量修改
					log.setRefId(u.getUserId());
					log.setRemark("密码错误");
					log.setUserId(u.getUserId());
					log.setUserName(u.getUserName());
					log.setType(0);//手动添加系统日志
					SpringContextHolder.getBean(ILog.class).saveOrUpdate(log);
				}
			}
		}else{
			result.put("result", "loginNameError");
		}
    
		 
		Gson gson = new Gson();
		String str = gson.toJson(result);
		response.getWriter().write(str);
		response.getWriter().flush();
		return;
	}
	
}
