package cn.com.qytx.platform.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能：创建一个单线程线程池，该线程池只有一个线程，放在线程池的中的线程按照放入顺序执行
 * 作者： jiayongqiang
 * 创建时间：2014年8月22日
 */
public class ThreadPoolUtils {
	private final static Integer MAX = 5;

	//单例模式
	private static ThreadPoolUtils instance = null;
	
	//线程池
	private ExecutorService executor;
	
	private ThreadPoolUtils(){
		executor = Executors.newFixedThreadPool(MAX);
	}
	
	public synchronized static ThreadPoolUtils getInstance(){
		if(instance == null){
			instance = new ThreadPoolUtils();
		}
		return instance;
	}
	
	public ExecutorService getThreadPool(){
		return executor;
	}
}
