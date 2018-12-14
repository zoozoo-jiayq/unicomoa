package cn.com.qytx.platform.springmvc;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * 自定义默认的SpringMVC前置控制器,默认加载根目录下的spring-mvc.xml文件
 * @author jiayongqiang
 *
 */
public class CutomerDispatchServlet extends DispatcherServlet {

	private final static String SPINRG_MVC_CONTEXT = "/WEB-INF/classes/spring-mvc.xml";

	@Override
	public String getContextConfigLocation() {
		// TODO Auto-generated method stub
		return SPINRG_MVC_CONTEXT;
	}
	
}
