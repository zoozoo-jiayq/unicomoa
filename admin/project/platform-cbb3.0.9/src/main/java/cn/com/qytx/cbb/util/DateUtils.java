package cn.com.qytx.cbb.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.consts.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能:日期工具类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-23
 * 修改日期: 2013-03-23
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
public class DateUtils
{


//    private final static SimpleDateFormat sdfShort = new SimpleDateFormat("yyyy-MM-dd");
//    private final static SimpleDateFormat sdfLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    private final static SimpleDateFormat sdfMonthDay = new SimpleDateFormat("MM月dd日");
    
    private final static String SDFSHORTSTR = "yyyy-MM-dd";
    private final static String SDFLONGSTR = "yyyy-MM-dd HH:mm:ss";
    private final static String SDFMONTHDAYSTR = "MM月dd日";
    private final static String SDFHOURMINUTESTR = "HH:mm";
    
    /**
     * 时间转成字符串格式，供给邮件系统使用
     * @param date 时间
     * @return
     */
    public static String date2StrForEmail1(Date date)
    {
        String str1 = date2ShortStr(null);
        String str2 = date2ShortStr(date);
        if (str1.equals(str2))
        {
            // 今日，返回小时，分钟
            SimpleDateFormat sdfHourMinute = new SimpleDateFormat(SDFHOURMINUTESTR);  
            return sdfHourMinute.format(date);
        }
        else
        {
            SimpleDateFormat sdfMonthDay = new SimpleDateFormat(SDFMONTHDAYSTR);  
            return sdfMonthDay.format(date);
        }
    }

    /**
     * 日期转成字符串
     * @param date
     * @return yyyy-MM-dd
     */
    public static String date2ShortStr(Date d)
    {
        Date date = (d == null ? new Date() : d);
        SimpleDateFormat sdfShort = new SimpleDateFormat(SDFSHORTSTR);  
        return sdfShort.format(date);
    }

    /**
     * 日期转成字符串
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2LongStr(Date d)
    {
        Date date = (d == null ? new Date() : d);
        SimpleDateFormat sdfLong = new SimpleDateFormat(SDFLONGSTR);  
        return sdfLong.format(date);
    }

    /**
     * 字符串日期转成短日期，
     * @param dateStr 字符串日期如 2012-01-01 00:00:00.0
     * @return 短日期字符串，如：2012-01-01
     */
    public static String dateStr2ShortStr(String dateStr)
    {

        if (dateStr == null)
        {
            return "";
        }
        if (dateStr.length() <= 10)
        {
            return dateStr;
        }
        // 如2012-01-01
        return dateStr.substring(0, 10);
    }

    /**
     * 获取短日期转换后的某天的第一时刻时间对象
     * @param dateShort 短日期字符串
     * @return Timestamp对象，指定天的第一时刻
     * @throws Exception 日期转换异常
     */
    public static Timestamp dateShortStr2DateStart(String dateShort) throws Exception
    {
        SimpleDateFormat sdfShort = new SimpleDateFormat(SDFSHORTSTR);  
        Date date = sdfShort.parse(dateShort);
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    /**
     * 获取短日期转换后的某天的最后时刻时间对象
     * @param dateShort 短日期字符串
     * @return Timestamp对象，指定天的最后时刻
     * @throws Exception 日期转换异常
     */
    public static Timestamp dateShortStr2DateEnd(String dateShort) throws Exception
    {
        SimpleDateFormat sdfShort = new SimpleDateFormat(SDFSHORTSTR);  
        Date date = sdfShort.parse(dateShort);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 短日期转为Timestamp 无法转换时时返回null
     * @param dateShort 日期字符串形式
     * @return 日期 Timestamp形式
     */
    public static Timestamp dateShort2DateNoExp(String dateShort)
    {
        if (StringUtils.isEmpty(dateShort))
        {
            return null;
        }
        try
        {
            SimpleDateFormat sdfShort = new SimpleDateFormat(SDFSHORTSTR);  
            return new Timestamp(sdfShort.parse(dateShort).getTime());
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 时间转成字符串格式，供给邮件系统使用
     * 原则 1，1分钟内显示多少秒（3秒前）
     * 2，1小时内显示多少分钟（40分钟前）
     * 3，一天内显示多少小时（12：52）
     * 4，前一天显示昨天上午或下午和时间（昨天 上午11:12）
     * 5，两天前显示月/日 （06-02）
     * 6，一年前显示年/月/白（2012-06-03）
     * @param date 时间
     * @return
     */
    public static String dateToStrForDynamic(Date date)
    {
        Date now = new Date();
        // 获取时间差值
        long difference = now.getTime() - date.getTime();
        if (difference >= 0 && difference < 60 * 1000)
        {
            // 1分钟内显示多少秒（3秒前）
            return difference / 1000 + "秒前";
        }
        else if (difference >= 60 * 1000 && difference < 60 * 60 * 1000)
        {
            // 1小时内显示多少分钟（40分钟前）
            return difference / (60 * 1000) + "分钟前";
        }
        else if (difference >= 0
                && DateTimeUtil.dateToString(now, CbbConst.DATE_FORMAT_STR).equals(
                        DateTimeUtil.dateToString(date, CbbConst.DATE_FORMAT_STR)))
        {
            // 一天内显示多少小时（12：52）
            return DateTimeUtil.dateToString(date, CbbConst.MINUTE_FORMAT_STR);
        }
        else if (difference >= 0
                && DateTimeUtil.dateToString(new Date(now.getTime() - 24 * 60 * 60 * 1000),
                        CbbConst.DATE_FORMAT_STR).equals(
                        DateTimeUtil.dateToString(date, CbbConst.DATE_FORMAT_STR)))
        {
            // 前一天显示昨天上午或下午和时间（昨天 上午11:12）
            return "昨天 " + DateTimeUtil.dateToString(date, CbbConst.MINUTE_FORMAT_STR);
        }
        else if (difference >= 0
                && !DateTimeUtil.dateToString(now, CbbConst.YEAR_FORMAT_STR).equals(
                        DateTimeUtil.dateToString(date, CbbConst.YEAR_FORMAT_STR)))
        {
            // 一年前显示年/月/白（2012-06-03）
            return DateTimeUtil.dateToString(date, CbbConst.DATE_FORMAT_STR);
        }
        else if (difference >= 0)
        {
            // 两天前显示月/日 （06-02）
            SimpleDateFormat sdfMonthDay = new SimpleDateFormat(SDFSHORTSTR);  
            return sdfMonthDay.format(date);
        }
        return DateTimeUtil.dateToString(date, CbbConst.TIME_FORMAT_STR);
    }
    
	    /**
	     * 得到一个时间n分钟之后/之前的时间
	     */
	    public static Timestamp getDaysAgoTime(Timestamp oldTime,int day){
	    	String oneHoursAgoTime = "";
	    	try {
	            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            Date newdate = df.parse(df.format(oldTime));
	            Calendar c = Calendar.getInstance();
	            c.setTime(newdate);
	            c.add(Calendar.DAY_OF_MONTH, day);
	            oneHoursAgoTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	                    .format(c.getTime());
			} catch (Exception e) {
				// TODO: handle exception
			}
	        
	        return Timestamp.valueOf(oneHoursAgoTime);
	    }
	    /**
	     * 两个日期相差的天数
	     * @return
	     */
	    public static Integer findDays(Timestamp beginTime, Timestamp endTime){
	    	long time= endTime.getTime()-beginTime.getTime();
	    	long between_days=time/(1000*3600*24);  
	    	
	    	return Integer.parseInt(String.valueOf(between_days));           
	    }
	    /**
	     * 获得当月最后一天
	     * @return
	     */
	    public static String getMonthLastDay(){
	    	  SimpleDateFormat s=new SimpleDateFormat(SDFSHORTSTR);
	    	  Calendar ca = Calendar.getInstance();    
	          ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	          String last = s.format(ca.getTime());
	          return last;
	    }
	    /**
	     * 获得本周最后一天
	     */
	    public static String getWeekLastDay(){
	    	SimpleDateFormat s=new SimpleDateFormat(SDFSHORTSTR);
	    	Calendar c = new GregorianCalendar();
	        c.setFirstDayOfWeek(Calendar.MONDAY);
	        c.setTime(new Date());
	        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
	        return s.format(c.getTime());
	    }
	    
	    
	    
}
