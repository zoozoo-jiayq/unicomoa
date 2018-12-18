package com.unicomoa.unicomoa.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 最近被使用的对象放在数组的尾部，每次从数组头部取值，保证数组中的每个对象都有一样的机会被使用
 * @author Administrator
 *
 */
public class LRUCircle {

	private  List<String> list = null;
	private static LRUCircle circle = null;
	private LRUCircle() {
		this.list = new ArrayList<String>();
	};
	public static synchronized LRUCircle getInstance(){
		if(circle == null) {
			circle = new LRUCircle();
		}
		return circle;
	}
	
	public synchronized void  init(List<String> init) {
		if(!init.isEmpty()) {
			this.list.clear();
			this.list.addAll(init);
		}
	}
	
	public synchronized String get() {
		if(!this.list.isEmpty()) {
			String result = this.list.remove(0);
			this.list.add(result);
			return result;
		}
		return null;
	}
	
	public synchronized String get(String defaultValue) {
		String k = this.get();
		if(k == null) {
			return defaultValue;
		}
		return k;
	}
	
//	
//	public static void main(String[] args) {
//		List<String> init = new ArrayList<String>();
//		init.add("receivemsgfrom1");
//		init.add("receivemsgfrom2");
//		init.add("receivemsgfrom3");
//		LruCircle.getInstance().init(init);
//		
//		System.out.println(LruCircle.getInstance().get());
//		System.out.println(LruCircle.getInstance().get());
//		System.out.println(LruCircle.getInstance().get());
//		System.out.println(LruCircle.getInstance().get());
//		System.out.println(LruCircle.getInstance().get());
//		System.out.println(LruCircle.getInstance().get());
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				for(int i=0; i<100; i++) {
//					System.out.println(LruCircle.getInstance().get());
//				}
//			}
//		}).start();
//new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				for(int i=0; i<100; i++) {
//					System.out.println(LruCircle.getInstance().get());
//				}
//			}
//		}).start();
//	}
//	
}
