package com.unicomoa.unicomoa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LRU缓存,
 * 超过配置的容量后，多余的最少最久未使用的键值对被移除
 * @author Administrator
 *
 */
public class LRUMap<K,V> {

	private Map<K,V> map = null;
	private List<K> keys = null;
	private Object lock = new Object();
	private int cap = 0;
	public LRUMap(int cap) {
		this.map = new HashMap<K,V>(cap);
		this.keys = new ArrayList<>(cap);
		this.cap = cap;
	}
	
	public void put(K key,V obj) {
		synchronized (this.lock) {
			if(this.keys.size()<this.cap) {
				this.map.put(key, obj);
				this.keys.remove(key);
				this.keys.add(key);
			}else {
				K _key = this.keys.remove(0);
				this.map.remove(_key);
				this.map.put(key, obj);
				this.keys.add(key);
			}
		}
	}
	
	public V get(K key) {
		synchronized (lock) {
			this.keys.remove(key);
			this.keys.add(key);
			return this.map.get(key);
		}
	}
	
	public List<K> keys(){
		synchronized (lock) {
			return this.keys;
		}
	}
}
