package cn.com.qytx.cbb.sso.server.cache;

import java.util.Calendar;

/**
 * 功能：保存对象监听触发事件，更新事件
 * 作者： jiayongqiang
 * 创建时间：2014年7月1日
 */
public class ReadCacheEvent implements CacheEvent {

	public void updateLastUpdateTime(String token) {
		// TODO Auto-generated method stub
		SSOCache.getInstance().cacheTime.put(token, Calendar.getInstance().getTimeInMillis());
	}

}
