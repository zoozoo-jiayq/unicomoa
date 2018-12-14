package cn.com.qytx.cbb.sso.server.cache;

/**
 * 功能：事件接口
 * 作者： jiayongqiang
 * 创建时间：2014年7月1日
 */
public interface CacheEvent {

	public void updateLastUpdateTime(String token);
}
