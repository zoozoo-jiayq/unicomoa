package cn.com.qytx.cbb.cache;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import cn.com.qytx.platform.utils.datetime.DateTimeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisCache {
	
	private static RedisCache instance = null;
	private RedisCache(){
		initPoolConfig();
	}
	public synchronized static RedisCache getInstance(){
		if(instance == null){
			instance = new RedisCache();
		}
		return instance;
	}
	
	
	public JedisPool pool = null;
	public Jedis jedis;//连接实例
	public int cacheOutTime = 3;//缓存超时时间
	public String cacheOnLine = "";//缓存上线时间
	/**
	 * redis连接池配置
	 * @return
	 */
	private void  initPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 控制一个pool最多有多少个状态为idle的jedis实例
		jedisPoolConfig.setMaxActive(1000);
		// 最大能够保持空闲状态的对象数
		jedisPoolConfig.setMaxIdle(300);
		// 超时时间
		jedisPoolConfig.setMaxWait(1000);
		// 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
		jedisPoolConfig.setTestOnBorrow(true);
		// 在还会给pool时，是否提前进行validate操作
		jedisPoolConfig.setTestOnReturn(true);
		pool = new JedisPool(jedisPoolConfig, getRedisServerIp());//连接池
	}
	/**
	 * redis服务器ip
	 */
	private String getRedisServerIp(){
		String serverIp = "";
		try {
			Properties prop =  new Properties();
			prop.load(getClass().getResourceAsStream("/application.properties"));
			serverIp = prop.getProperty("redisServerIp")==null?"127.0.0.1":prop.getProperty("redisServerIp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return serverIp;
	}
	/**
	 * 判断缓存是否可用
	 * @return true 可用  false不可用
	 */
	public boolean checkCacheEnable(){
		boolean cacheEnable = false;
		if (pool!=null) {
			try {
				jedis = pool.getResource();
				cacheEnable = true;
				// 还会到连接池
				pool.returnResource(jedis);
				System.out.println("缓存服务器正常");
			} catch (Exception e) {
				System.out.println("缓存服务器不正常");
			}
		}
		return cacheEnable;
	}
	/**
	 * 判断时间是否在缓存保存的时间内
	 * @return  true是  false不在
	 */
	public boolean checkTimeInCacheTime(Date checkDate){
		boolean inCacheTime = false;
		try {
			if (checkDate!=null) {
				String checkDateStr = DateTimeUtil.dateToString(checkDate, "yyyy-MM-dd HH:mm:ss");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DAY_OF_YEAR, -1*(getCacheOutTime()-1));
				String startCacheTime = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
				startCacheTime += " 00:00:00"; 
				//检测时间大于缓存上线时间并且检测时间大于缓存有效期内的开始时间
				if (checkDateStr.compareTo(getCacheOnLine())>=0&&startCacheTime.compareTo(checkDateStr)<=0) {
					inCacheTime = true;
				}
			}
		} catch (Exception e) {
			
		}
		return inCacheTime;
	}
	/**
	 * 读取缓存中的数据
	 * @param cacheName 缓存名字
	 * @param list  其他参数
	 * @return 
	 */
	public String getDataFromCache(String cacheName){
		String result = "";
		if (pool!=null) {
			try {
					jedis = pool.getResource();
					if (jedis!=null) {
						result = jedis.get(cacheName);
						if (result==null||result.equals("nil")) {
							return null;
						}
						// 还会到连接池
						pool.returnResource(jedis);
					}
			} catch (Throwable e) {
				// 销毁对象
				pool.returnBrokenResource(jedis);
				System.out.println("缓存服务器不正常");
				return null;
			}
		}
		return result;
	}
	/**
	 * 向缓存中存放数据
	 * @param cacheName 缓存名字
	 * @param resultGson 存放的gson字符串
	 * @param timeOut  过期时间  单位是秒  0为永久
	 * @return 0 失败  1成功
	 */
	public int putDataToCache(String cacheName,String resultGson,Integer timeOut){
		int result = 0;
		if (pool!=null) {
			try {
					jedis = pool.getResource();
					if (jedis!=null) {
						jedis.set(cacheName, resultGson);
						if (timeOut!=null&&timeOut!=0) {
							jedis.expire(cacheName, timeOut);
						}
						result=1;
						// 还会到连接池
						pool.returnResource(jedis);
					}
			} catch (Throwable e) {
				// 销毁对象
				pool.returnBrokenResource(jedis);
				System.out.println("缓存服务器不正常");
			}
		}
		return result;
	}
	/**
	 * 向缓存中存放数据（有效期是三天）
	 * @param cacheName 缓存名字
	 * @param resultGson 存放的gson字符串
	 * @return 0 失败  1成功
	 */
	public int putDataToCache(String cacheName,String resultGson){
		int result = 0;
		if (pool!=null) {
			try {
					jedis = pool.getResource();
					if (jedis!=null) {
						jedis.set(cacheName, resultGson);
						Integer timeOut = 3600*24*3;
						jedis.expire(cacheName, timeOut);
						result=1;
						// 还会到连接池
						pool.returnResource(jedis);
					}
			} catch (Throwable e) {
				// 销毁对象
				pool.returnBrokenResource(jedis);
				System.out.println("缓存服务器不正常");
			}
		}
		return result;
	}
	/**
	 * 从配置文件中得到缓存的保存时间
	 * @return 如果没有配置 默认是三天
	 */
	public int getCacheOutTime() {
		try {
			Properties prop =  new Properties();
			prop.load(getClass().getResourceAsStream("/application.properties"));
			cacheOutTime = prop.getProperty("cacheOutTime")==null?3:Integer.parseInt(prop.getProperty("cacheOutTime"));
		} catch (Exception e) {
			return 3;
		}
		
		return cacheOutTime;
	}
	/**
	 * 从配置文件中得到上线时间  
	 * @return  如果没有配置 返回当前时间  yyyy-MM-dd HH:mm:ss
	 */
	public String getCacheOnLine() {
		try {
			Properties prop =  new Properties();
			prop.load(getClass().getResourceAsStream("/application.properties"));
			cacheOnLine = prop.getProperty("cacheOnLineTime")==null?DateTimeUtil.getCurrentTime():prop.getProperty("cacheOnLineTime");
		} catch (Exception e) {
			return DateTimeUtil.getCurrentTime();
		}
		
		return cacheOnLine;
	}
}
