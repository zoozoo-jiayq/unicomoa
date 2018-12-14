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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

public class SessionFilter implements Filter
{

    public void init(FilterConfig arg0) throws ServletException
    {

    }

    public void destroy()
    {

    }


    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if(StringUtils.equals("wap",request.getParameter("_clientType"))){
       	 chain.doFilter(req, res);
           return;
       }


        String requestURL = request.getRequestURI().toUpperCase();
        
        //富文本编辑器文件图片上传。
        if(requestURL.indexOf("HEADPICTUREUPLOAD.ACTION")>=0 || requestURL.indexOf("UPLOADUSERPHOTO.ACTION")>=0 || requestURL.indexOf("UPLOADSPEAKVOICE.ACTION")>=0){
        	chain.doFilter(req, res);
            return;
        }
        String ctxPath = request.getContextPath();
        String filename = requestURL
                .substring(requestURL.lastIndexOf("/") + 1, requestURL.length());
        
        // 不过滤customForm包下的文件
        if (!(requestURL.indexOf("CUSTOMFORM") >= 0 || requestURL.indexOf("WORKFLOWFORM") >= 0)){
            response.setHeader("X-UA-Compatible", "IE=100"); // 解决IE8兼容问题
        }
        
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + ctxPath + "/";

        if (!filename.equals("MONITOR") && filename.indexOf(".") == -1)
        {
            filename = filename + ".ACTION";
        }

        HttpSession session = request.getSession();
        if (filename.endsWith("JSP") || filename.endsWith("ACTION"))
        {
            if (filename.equalsIgnoreCase("LOGIN.JSP")|| filename.equalsIgnoreCase("LOGIN.ACTION")
                    || filename.equalsIgnoreCase("LOGOUT.JSP")
                    || filename.equalsIgnoreCase("SESSIONOUT.JSP")
                    || filename.equalsIgnoreCase("lOGINAJAX.ACTION")
                    || filename.equalsIgnoreCase("AUTOLOGIN.JSP")
                    || filename.equalsIgnoreCase("CODE.JSP")
                    || filename.equalsIgnoreCase("J_SPRING_SECURITY_CHECK.ACTION")
                    || filename.equalsIgnoreCase("CALENDAR_ADDAFFAIRSBODY.ACTION")
                    || requestURL.indexOf("/REG/")>=0 //注册不过滤
                    || requestURL.indexOf("/LOGIN/")>=0//登录不过滤
                    || requestURL.equals("/")
                    || requestURL.indexOf("/INDEX.JSP")>=0
                    || requestURL.indexOf("/PRODUCT.JSP")>=0
                    || requestURL.indexOf("/HELP.JSP")>=0
                    || requestURL.indexOf("/TOLOGIN.ACTION")>=0 || requestURL.indexOf("/INDEX.HTML")>=0)
            {
                chain.doFilter(req, res);
            }
            else
            {
                if (session.getAttribute("adminUser") == null&&session.getAttribute("adminUserXC") == null)
                {
                    response.sendRedirect(basePath + "login.jsp");
                }
                else
                {
                	if( session.getAttribute("adminUser") == null){
                		session.setAttribute("adminUser",session.getAttribute("adminUserXC") );
                	}
                    chain.doFilter(req, res);
                }
            }
        }
        else
        {
            chain.doFilter(req, res);
        }
    }
}
