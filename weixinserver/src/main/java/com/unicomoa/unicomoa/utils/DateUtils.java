package com.unicomoa.unicomoa.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能:日期工具类
 * 版本:1.0
 * 开发人员: 耿永冲
 * 创建日期:  2018-05-04
 * 修改列表:初始加入的方法
 */
public class DateUtils
{
    private final static String SDFSHORTSTR = "yyyy-MM-dd";
    private final static String SDFLONGSTR = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 日期转时间
     * @param d
     * @param format
     * @return
     */
    public static String date2Str(Date d,String format)
    {
        Date date = (d == null ? new Date() : d);
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(date);
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
     * 得到几分钟前的时间
     * @param minute
     * @return
     */
    public static String getTimeMinuteBefore(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }
    public static long getTimeSecondsGap(Date begin,Date end){
    	long interval = (end.getTime() - begin.getTime())/1000;
    	return interval;
    }
}
