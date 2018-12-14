package cn.com.qytx.cbb.jbpmApp.util;

/**格式化时间的工具类
 * @author jiayongqiang
 *
 */
public class TimeFormatUtil {

	//每分钟的毫秒数
	private final static long MINU_TIMES = 60*1000;
	//每小时的毫秒数
	private final static long HOUR_TIMES = 60*MINU_TIMES;
	//每天的毫秒数
	private final static long DAY_TIMES =  24*HOUR_TIMES;
	public static String formatTime(long times){
		long day = times/DAY_TIMES;
		long yu  = times%DAY_TIMES;
		
		long hour = yu/HOUR_TIMES;
		yu = yu%HOUR_TIMES;
		
		long minute = yu/MINU_TIMES;
		return day+"天"+hour+"小时"+minute+"分";
	}
	
	public static void main(String[] args) {
		System.out.println(formatTime(4560000));
	}
}
