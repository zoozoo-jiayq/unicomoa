package cn.com.qytx.cbb.sso.server.cache;

/**
 * 功能：保存缓存对象监听
 * 作者： jiayongqiang
 * 创建时间：2014年7月1日
 */
public class ReadCacheListener implements CacheListener {

	private String token;
	
	
	public ReadCacheListener(String token) {
		super();
		this.token = token;
	}

	public void eventChange(CacheEvent event) {
		// TODO Auto-generated method stub
		event.updateLastUpdateTime(token);
	}

}
