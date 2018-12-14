package cn.com.qytx.cbb.sso.server.cache;

/**
 * 功能：缓存事件监听接口
 * 作者： jiayongqiang
 * 创建时间：2014年7月1日
 */
public interface CacheListener {

	public void eventChange(CacheEvent event);
}
