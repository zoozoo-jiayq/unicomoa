package cn.com.qytx.oa.sso.client;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 功能：SSO请求包装器
 * 作者： jiayongqiang
 * 创建时间：2014年7月1日
 */
public class SSORequestWraper extends HttpServletRequestWrapper {

	private Map<String,Object> user;
	
	public SSORequestWraper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	
	public SSORequestWraper(HttpServletRequest request, Map user) {
		super(request);
		this.user = user;
	}


	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		if(name.equals("j_username")){
			return (String) user.get("loginName");
		}
		if(name.equals("j_password")){
			return (String) user.get("loginPass");
		}
		return super.getParameter(name);
	}

}
