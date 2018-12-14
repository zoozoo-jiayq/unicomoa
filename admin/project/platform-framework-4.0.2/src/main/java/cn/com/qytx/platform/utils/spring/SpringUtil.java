package cn.com.qytx.platform.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring工具类，获取spring容器创建的bean
 * <br/>User: 黄普友
 * <br/>Date: 13-2-21
 * <br/>Time: 下午5:13
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    /* 
     * 设置该工具类持有的容器上下文对象
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 销毁该工具类持有的容器上下文对象
     * @throws Exception
     */
    public void destroy() throws Exception {
        this.applicationContext = null;
    }

    /**
     * 获取容器上下文对象
     * @return applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据spring容器定义的bean名称获取对象实例
     * @param name
     * @return
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
    
    /**
     * 根据class类型获取spring容器定义的bean实例
     * @param c
     * @return
     */
    public static <T> T getBean(Class<T> c){
    	return applicationContext.getBean(c);
    }
}
