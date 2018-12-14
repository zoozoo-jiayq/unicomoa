package cn.com.qytx.platform.base.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.helper.StringUtil;

/**
 * 基于Filter的拦截器，用来给Druid监控程序添加访问权限。
 * <br/>
 * 如果在web.xml中不配置此filter,任何人都可以访问druid的监控信息。
 * <br/>
 * 添加了此filter的配置后，必须使用配置中的用户名和密码才能访问druid的监控信息
 */
public class DruidFilter implements Filter {
	
	private String username;
	private String password;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String url = request.getRequestURI();
		if(url.contains("/druid/login")){
			String usen = request.getParameter("username");
			String passw = request.getParameter("password");
			request.getSession().setAttribute("druidUser", usen);
			request.getSession().setAttribute("druidPwd", passw);
			response.getWriter().println("ok");
			
		}else if(url.contains("/druid/index.html")){
			String uname = (String) request.getSession().getAttribute("druidUser");
			String uword = (String) request.getSession().getAttribute("druidPwd");
			if(StringUtil.isBlank(username) || StringUtil.isBlank(password) || StringUtil.isBlank(uname) || StringUtil.isBlank(uword)){
				login(request, response);
				return ;
			}else{
				if(uname.equals(username) && uword.equals(password)){
					arg2.doFilter(arg0, arg1);
				}else{
					login(request, response);
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.username = arg0.getInitParameter("username");
		this.password = arg0.getInitParameter("password");
	}

	
	/**登陆窗口
	 * @param response
	 * @throws IOException 
	 */
	private void login(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter pw = response.getWriter();
		pw.write("<html>");
		pw.write("<head>");
		pw.write("<meta charset=\"UTF-8\"></meta>");
		pw.write("<title>禁止访问</title>");
		pw.write("<script type=\"text/javascript\" src=\""+request.getContextPath()+"/flat/plugins/dialog/artDialog.js?skin=default\"></script>");
		pw.write("<script type=\"text/javascript\" src=\""+request.getContextPath()+"/flat/plugins/dialog/artDialog_alert.js\"></script>");
		pw.write("<script type=\"text/javascript\" src=\""+request.getContextPath()+"/flat/plugins/dialog/iframeTools.js\"></script>");
		//pw.write("<script type=\"text/javascript\" src=\""+request.getContextPath()+"/js/common/artClose.js\"></script>");
		pw.write("<script type=\"text/javascript\" src=\""+request.getContextPath()+"/js/common/jquery-1.8.0.min.js\"></script>");
		pw.write("</head>");
		pw.write("<body></body>");
		pw.write("<script type=\"text/javascript\">"
				+"art.dialog({"
				+"	content:\"<ul><li><label>姓名：</label><input type='text' id='usrname' maxlength='50'/></li><li><label>密码：</label><input type='password' id='pwd' maxlength='50'/></li></ul>\","
				+"	title:\"登陆\","
				+"	width:200,"
				+"	height:100,"
				+"	ok:function(){"
				+"	var username = $('#usrname').val();"
				+"	var password = $('#pwd').val();"
				+"	$.post(\""+request.getContextPath()+"/druid/login?username=\"+username+\"&password=\"+encodeURI(password),function(data){"
				+"	window.location.href=\""+request.getContextPath()+"/druid/index.html\"; " 		
				+"	})"
				+"	},"
				+"	cancel:true"
				+"});"
				+ "</script>");
		pw.write("</html>");
		pw.flush();
	}
}
