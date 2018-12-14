package cn.com.qytx.cbb.cache;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * <br/>功能:缓存名称生成工具类
 * <br/>版本: 1.0
 * <br/>开发人员: 吴洲
 * <br/>创建日期: 2015年5月4日
 * <br/>修改日期: 2015年5月4日
 * <br/>修改列表:
 */
public class CacheNameUtil {
	/**
	 * 生成缓存名称
	 * @param companyId 公司的id
	 * @param moduleName 模块名称
	 * @param processName 功能名称
	 * @return cacheName 缓存名字
	 */
	public static String createCacheName(Integer companyId,String moduleName,String processName){
		String keyStr = companyId+"_"+moduleName+"_"+processName;
		return keyStr;
	}
	/**
	 * 生成加时间戳的缓存名称
	 * @param companyId 公司的id
	 * @param moduleName 模块名称
	 * @param processName 功能名称
	 * @return cacheName 缓存名字
	 */
	public static String createCacheName(Integer companyId,String moduleName,String processName,Date date){
		String keyStr = companyId+"_"+moduleName+"_"+processName;
		if (date==null) {
			return keyStr;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		keyStr = keyStr + "_" +time;
		return keyStr;
	}
}
