package cn.com.qytx.platform.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;


public class CheckCodeFilter implements Filter
{

    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException
    {
        HttpServletRequest hsr = (HttpServletRequest)arg0;
        HttpServletResponse response = (HttpServletResponse)arg1;
        String checkCode =  hsr.getParameter("checkCode");
        //String sessionRand = (String)hsr.getSession().getAttribute("rand");
        String sessionRand = "0000"; 
        checkCode = "0000";
        //add by jiaq,如果登录类型是SSO登录，则不使用验证码
	        if (!StringUtils.isEmpty(checkCode) && !StringUtils.isEmpty(sessionRand)){
	            if (checkCode.equals(sessionRand)){
	                arg2.doFilter(arg0, arg1);
	            }
	            else
	            {
	                response.sendRedirect(hsr.getContextPath()+"/login.jsp?loginFailure=codeError");
	            }
	        }else{
	            response.sendRedirect(hsr.getContextPath()+"/login.jsp?loginFailure=codeError");
	        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
        // TODO Auto-generated method stub

    }

}
