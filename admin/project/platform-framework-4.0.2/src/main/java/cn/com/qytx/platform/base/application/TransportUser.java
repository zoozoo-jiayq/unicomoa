package cn.com.qytx.platform.base.application;

import cn.com.qytx.platform.org.domain.UserInfo;


/**
 *使用THREADLOCAL模式，用来保存用户信息，方便在线程内随时调用，可跨层调用。由TransprotUserFilter初始化，线程销毁的时候自动销毁
 */
public class TransportUser {
	// 新建一个静态的ThreadLocal变量，并通过get方法将其变为一个可访问的对象
    private static ThreadLocal<UserInfo> counterContext = new ThreadLocal<UserInfo>();
	
	/**
	 * 通过静态的get方法访问ThreadLocal中存储的用户对象
	 * @return
	 */
	public static UserInfo get() {
		return counterContext.get();
	}
	
	
	/**
	 * 设置ThreadLocal中的用户对象
	 * @param value 用户对象
	 */
	public static void set(UserInfo value) {
		counterContext.set(value);
	}

}