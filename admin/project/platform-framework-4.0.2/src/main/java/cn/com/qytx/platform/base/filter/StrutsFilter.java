package cn.com.qytx.platform.base.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 *  继承StrutsPrepareAndExecuteFilter，
 *  <br/>
 *  使用ueditor上传文件的时候，struts2拦截器会拦截该请求，需要对使用ueditor上传文件的请求做特殊处理。
 */
public class StrutsFilter extends StrutsPrepareAndExecuteFilter {
    @Override
    protected void postInit(Dispatcher dispatcher, FilterConfig filterConfig) {
    	super.postInit(dispatcher, filterConfig);

    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;
		 String url = request.getRequestURI();
		if(url.contains("imageUp.jsp") || url.contains("controller.jsp")){
			chain.doFilter(req, res);
			return;
		}
		super.doFilter(req, res, chain);
	}
}
