package cn.com.qytx.cbb.login.action;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class LogoInitServlet extends HttpServlet {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
	    
		LogoConfig.getInstance().setLogoUrl("");
		LogoConfig.getInstance().setSysName("OA办公系统");
		
	}
}
