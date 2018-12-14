package cn.com.qytx.platform.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 自定义Spring Security 登陆异常处理
 * User: 黄普友
 * Date: 13-2-19
 * Time: 上午11:20
 */
public class AccessAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private String defaultFailureUrl;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    public AccessAuthenticationFailureHandler() {
    }
    public AccessAuthenticationFailureHandler(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        request.getSession().setAttribute("adminUser", null);
        if (defaultFailureUrl == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + e.getMessage());
        } else {
            defaultFailureUrl = defaultFailureUrl.split("\\?")[0];
            StringBuffer param = new StringBuffer();
            String error="failure";
            if(request.getSession().getAttribute("loginFailure")!=null)
            {
                 error= request.getSession().getAttribute("loginFailure").toString();
            }
            String sp=param.toString();
            if(sp.equals(""))
            {
               defaultFailureUrl+="?loginFailure="+error;
            }
            else
            {
                defaultFailureUrl+=param.toString()+"&loginFailure="+error;
            }
            redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
        }
    }

    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }
}
