/**
 * Copyright (C) 2014 serv (liuyuhua69@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.qytx.platform.base.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * 自定义Spring上下文对象,持有Spring应用上下文，要正常使用此对象，必须在applicationContext.xml配置文件中定义
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	/**
	 * Spring应用上下文属性
	 */
	private static ApplicationContext applicationContext = null;

	private final static Logger LOGGER = LoggerFactory.getLogger(SpringContextHolder.class);

	/**
	 * 获取Spring应用上下文
	 * @return Spring应用上下文
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 根据spring定义的bean名称获取对应的java对象实例
	 * @param name spring定义的bean名称
	 * @return name对应的java对象实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 根据class类型获取对应的java对象实例
	 * @param requiredType 指定对象的class类型 
	 * @return class对应的bean实例
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 清空持有的spring应用上下文
	 */
	public static void clearHolder() {
		LOGGER.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	/* 设置spring应用上下文对象
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		LOGGER.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			LOGGER.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ SpringContextHolder.applicationContext);
		}

		SpringContextHolder.applicationContext = applicationContext; //NOSONAR
	}

	/**
	 * 参考clearHolder()方法
	 */
	public void destroy() throws Exception {
		SpringContextHolder.clearHolder();
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {
		Assert.state(applicationContext != null,"applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
	}
}