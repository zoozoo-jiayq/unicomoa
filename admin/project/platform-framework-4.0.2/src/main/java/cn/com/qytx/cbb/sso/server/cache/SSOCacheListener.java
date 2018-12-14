package cn.com.qytx.cbb.sso.server.cache;

/**
 * 功能：SSO缓存守护线程，定时扫描
 * 作者： jiayongqiang
 * 创建时间：2014年7月1日
 */
public class SSOCacheListener implements Runnable{

	private final Long SLEEP_TIME = 10*60*1000l;
//	private final Long SLEEP_TIME = 1*60*1000l;
	
	public void run() {
		while(true){
			// TODO Auto-generated method stub
			try {
				Thread.sleep(SLEEP_TIME);
				SSOCache.getInstance().clean();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
