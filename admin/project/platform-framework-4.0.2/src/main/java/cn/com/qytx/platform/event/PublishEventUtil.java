package cn.com.qytx.platform.event;

import org.springframework.context.ApplicationEvent;

import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 发布事件工具类,发布的事件在整个spring容器内都可以被监听。
 * <br/>
 * 所有的事件都必须实现ApplicationEvent接口
 * @author jiayongqiang
 *
 */
public class PublishEventUtil {

	/**
	 * 向Spring容器内发布广播事件
	 * @param event 待发布的事件对象
	 */
	public static void publishEvent(ApplicationEvent event){
		SpringUtil.getApplicationContext().publishEvent(event);
	}
}
