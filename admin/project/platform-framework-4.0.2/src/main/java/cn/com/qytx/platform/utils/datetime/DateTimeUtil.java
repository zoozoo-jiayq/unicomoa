package cn.com.qytx.platform.utils.datetime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 日期时间工具类
 * User: 黄普友
 * Date: 13-1-24
 * Time: 上午9:56
 */
public class DateTimeUtil {


    /**
     * Timestamp 转换成String
     * @param time 要转换的Timestamp 时间
     * @param format  要转换成的格式
     * @return  转换失败返回 空字符串
     */
    public static String timestampToString(Timestamp time,String format)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            String dateString = formatter.format(time);
            return dateString;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "";
        }
    }
    
    /**
     * 字符串转换成timestamp
     * @param dateTime  要转换的时间字符串
     * @return    转换失败返回 null
     */
    public static Timestamp stringToTimestamp(String dateTime,String format)
    {
        try
        {
            SimpleDateFormat df1 = new SimpleDateFormat(format);
            Date date11 = df1.parse(dateTime);
            Timestamp ts =new Timestamp(date11.getTime());
            return ts;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * 字符串转换成timestamp
     * @param dateTime  要转换的时间字符串
     * @return    转换失败返回 null
     */
    public static Timestamp stringToTimestamp(String dateTime)
    {
        try
        {
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date date11 = df1.parse(dateTime);
            String time = df1.format(date11);
            Timestamp ts = Timestamp.valueOf(time);
            return ts;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * 计算两个日期的时间差精确到秒
     * @param time1  最晚的时间
     * @param time2  最早的时间
     * @return  返回相差的时间字符串
     */
    public static String getTimeDifference(Date time1,Date time2)
    {
        try
        {
            SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
            long t1 = 0L;
            long t2 = 0L;
            t1 = timeformat.parse(getDateNumberFormat(time1)).getTime();
            t2= timeformat.parse(getDateNumberFormat(time2)).getTime();

            //因为t1-t2得到的是毫秒级,所以要除3600000得出小时.算天数或秒同理

            int hours=(int) ((t1 - t2)/3600000);
            int minutes=(int) (((t1 - t2)/1000-hours*3600)/60);
            int seconds= (int) (((t1 - t2)/1000-hours*3600));
            int day= hours/24;
            int month=day/30;
            int year=month/12;
            if(year<0)
                year=0;
            if(month<0)
                month=0;
            if(day<0)
                day=0;
            if(hours<0)
                hours=0;
            if(minutes<0)
                minutes=0;
            if(seconds<0)
                seconds=0;
            String ret="0秒前";
            if(year>0)
            {
                ret=dateToString(time2,"yyyy-MM-dd");
            }
            else if(month>0)
            {
                ret=dateToString(time2,"yyyy-MM-dd");
            }
            else if(day>0)
            {
                if(day>7)
                {
                    ret=dateToString(time2,"yyyy-MM-dd");
                }
                else
                {
                  ret =day+"天前";
                }
            }
            else if(hours>0)
            {
                ret=hours+"小时前";
            }
            else if(minutes>0)
            {
                ret =minutes+"分钟前";
            }
            else if(seconds>0)
            {
                ret =seconds+"秒前";
            }
            return ret;
        }
        catch (Exception ex)
        {
            return "0秒前";
        }
    }
    /**
     * 计算两个日期的时间差精确到秒
     * @param time1  最晚的时间
     * @param time2  最早的时间
     * @return  返回相差的秒数
     */
    public static int getTimeDifferenceSeconds(Timestamp time1, Timestamp time2) throws Exception{
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        long t1 = 0L;
        long t2 = 0L;
        try {
            t1 = timeformat.parse(getTimeStampNumberFormat(time1)).getTime();
        } catch (ParseException e) {
            throw e;
        }
        try {
            t2 = timeformat.parse(getTimeStampNumberFormat(time2)).getTime();
        } catch (ParseException e) {
            throw e;
        }
        Long diff =t1 - t2;   //两时间差，精确到毫秒
        int num=(int)(diff/1000);
        return num;
    }

    /**
     * 格式化时间
     * Locale是设置语言敏感操作
     * @param formatTime
     * @return
     */
    private static String getTimeStampNumberFormat(Timestamp formatTime) {
        SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
        return m_format.format(formatTime);
    }
    /**
     * 格式化时间
     * Locale是设置语言敏感操作
     * @param formatTime
     * @return
     */
    private static String getDateNumberFormat(Date formatTime) {
        SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
        return m_format.format(formatTime);
    }
    /**
     * 获取当前时间 默认格式为 "yyyy-MM-dd HH:mm:ss"
     * @return 返回格式化后的当前时间
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    /**
     * 获取当前时间
     * @param format 时间格式
     * @return 如果格式有错误，返回空字符串
     */
    public static String getCurrentTime(String format) {
        try
        {
          SimpleDateFormat sdf = new SimpleDateFormat(format);
          return sdf.format(new Date());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 字符串格式转换成date格式
     * @param time 要转换的时间
     * @param format 要转换成的时间格式
     * @return 转换失败返回null
     */
    public static Date stringToDate(String time,String format) {

        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(time, pos);
            return strtodate;
        }
        catch (Exception ex)
        {
             ex.printStackTrace();
             return null;
        }
    }
    /**
     * date格式转换成字符串格式
     * @param time 要转换的时间
     * @param format 要转换成的时间格式
     * @return 转换失败返回空字符串
     */
    public static String dateToString(Date time,String format) {

        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            String dateString = formatter.format(time);
            return dateString;

        }
        catch (Exception ex)
        {
            return "";
        }
    }

    /**
     * 计算某年某周到开始日期
     * @param year
     * @param week
     * @return
     * @throws java.text.ParseException
     */
    public static String getYearWeekFirstDay(int year,int week)throws ParseException
    {
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.WEEK_OF_YEAR,week);
        cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);//周一第一天
        //分别取得当前的日期的年月日
        String tempYear=Integer.toString(year);
        String tempMonth=Integer.toString(cal.get(Calendar.MONTH)+1);
        String tempDay=Integer.toString(cal.get(Calendar.DATE));
        if(week==1&&tempMonth.equals("12"))
        {
            //第一周，并且月是12月，开始日期应该为上一年
            tempYear=Integer.toString(year-1);
        }
        String date=tempYear+"-"+tempMonth+"-"+tempDay;
        return formatDateTime(date,"yyyy-MM-dd");
    }
    
    /**
     * @param year
     * @param week
     * @return
     * @throws ParseException
     */
    public static String getYearWeekEndDay(int year,int week)throws ParseException
    {
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.WEEK_OF_YEAR,week+1);
        cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);//周日
        //分别取得当前的日期的年月日
        String tempYear=Integer.toString(year);
        String tempMonth=Integer.toString(cal.get(Calendar.MONTH)+1);
        String tempDay=Integer.toString(cal.get(Calendar.DATE));

        String date=tempYear+"-"+tempMonth+"-"+tempDay;
        return formatDateTime(date,"yyyy-MM-dd");
    }
    
    /**
     * 格式化日期字符串为指定的格式
     * @param date 日期字符串
     * @param format 指定的格式
     * @return 日期字符串
     * @throws ParseException
     */
    public static String formatDateTime(String date,String format)throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        String sDate=sdf.format(sdf.parse(date));
        return  sDate;
    }

    /**
     * 获取周/日期对应的列表
     * @param year 年
     * @return 返回zhou/日期列表
     */
    public static List<WeekDate> getWeekDateList(int year)
    {
        List<WeekDate> list=new ArrayList<WeekDate>();
         for(int i=1;i<=52;i++)
         {
             try
             {
                 String startDate=getYearWeekFirstDay(year,i);
                 String endDate=getYearWeekEndDay(year,i);
                 WeekDate weekDate=new WeekDate();
                 weekDate.setYear(year);
                 weekDate.setWeek(i);
                 weekDate.setStartDate(startDate);
                 weekDate.setEndDate(endDate);
                 list.add(weekDate);
             }
             catch (Exception ex)
             {

             }

         }
        return list;
    }
    /**
     *if(同年 同月 同天){result.append(小时：分钟);}
     *<br/>else if(同年){result.append(X月X日 小时：分钟);}
     *<br/>else{//跨年result.append(XXXX年X月X日 小时：分钟);
     *<br/>}
     */
    public static String getTime(Date time1,Date time2)
    {
        try
        {
        	String nowDay=DateTimeUtil.dateToString(time1, "yyyy-MM-dd");
    		String dataDay=DateTimeUtil.dateToString(time2, "yyyy-MM-dd");
    		if (nowDay.equals(dataDay)) {
    			return "今天  "+DateTimeUtil.dateToString(time2, "HH:mm");
    		}
            SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
            long t1 = 0L;
            long t2 = 0L;
            t1 = timeformat.parse(getDateNumberFormat(time1)).getTime();
            t2= timeformat.parse(getDateNumberFormat(time2)).getTime();
            //因为t1-t2得到的是毫秒级,所以要除3600000得出小时.算天数或秒同理
            int hours=(int) ((t1 - t2)/3600000);
            int minutes=(int) (((t1 - t2)/1000-hours*3600)/60);
            int seconds= (int) (((t1 - t2)/1000-hours*3600));
            int day= hours/24;
            int month=day/30;
            int year=month/12;
           /* if(year<0)
                year=0;*/
            if(month<0)
                month=0;
            if(day<0)
                day=0;
            if(hours<0)
                hours=0;
            if(minutes<0)
                minutes=0;
            if(seconds<0)
                seconds=0;
            String ret="";
            if(year!=0)
            {
                ret=dateToString(time2,"yyyy-M-dd HH:mm");
            }
            else if(month>0)
            {
            	ret=dateToString(time2,"M-dd HH:mm");
            }
            else {
            	ret=dateToString(time2,"M-dd HH:mm");
			}
            return ret;
        }
        catch (Exception ex)
        {
            return dateToString(time2,"yyyy-M-dd HH:mm");
        }
    }
    
    /**
     * 功能：验证日期格式是否合法
     * @param str 日期格式的字符串
     * @param format 格式化标准
     * @return
     */
    public static boolean isValidDate(String str,String format) {
        boolean convertSuccess=true;
         SimpleDateFormat thisFormat = new SimpleDateFormat(format);
         try {
        	 thisFormat.setLenient(false);
        	 thisFormat.parse(str);
         } catch (ParseException e) {
             convertSuccess=false;
         } 
         return convertSuccess;
  }
    
    /**
     * 功能：比较日期
     * @param date1
     * @param date2
     * @param format
     * @return
     */
    public static boolean isSameDate(Date date1,Date date2,String format){
    	SimpleDateFormat timeformat = new SimpleDateFormat(format);
    	String date2Str = timeformat.format(date1);
    	Timestamp date2Ts = Timestamp.valueOf(date2Str+" 00:00:00.000");
    	long time1=0L;
    	long time2=0L;
		try {
			time1 = date2Ts.getTime();
			time2=date2.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return time1>time2;
    }
    
    /**
    if(同年 同月 同天){result.append(小时：分钟);}
    else if(同年){result.append(xX月xX日 小时：分钟);}
    else{//跨年result.append(XXXX年xX月Xx日 小时：分钟);
    }
    */
   public static String getTime1(Date time1,Date time2)
   {
       try
       {
    	   String ret="";
    	   int nowYear=time1.getYear();
    	   int nowMonth=time1.getMonth();
    	   int nowDay=time1.getDay();
    	   int year=time2.getYear();
    	   int month=time2.getMonth();
    	   int day=time2.getDay();
    	   if(nowYear==year){
    		   if(nowMonth==month&&nowDay==day){
    			   //同一天
    			   ret= "今天 "+DateTimeUtil.dateToString(time2, "HH:mm");
    		   }else{
    			   //同年不同天
    			   ret= DateTimeUtil.dateToString(time2, "MM-dd HH:mm");
    		   }
    	   }else{
    		   //不同年
    		   ret= DateTimeUtil.dateToString(time2, "yyyy-MM-dd HH:mm");
    	   }
    	   
           return ret;
       }
       catch (Exception ex)
       {
           return dateToString(time2,"yyyy-MM-dd HH:mm");
       }
   }
   /**
   if(同年 同月 同天){result.append(今天);}
   else if(同年){result.append(xX月xX日 );}
   else{//跨年result.append(XXXX年xX月Xx日 );
   }
   */
  public static String getTime2(Date time1,Date time2)
  {
      try
      {
   	   String ret="";
   	   int nowYear=time1.getYear();
   	   int nowMonth=time1.getMonth();
   	   int nowDay=time1.getDay();
   	   int year=time2.getYear();
   	   int month=time2.getMonth();
   	   int day=time2.getDay();
   	   if(nowYear==year){
   		   if(nowMonth==month&&nowDay==day){
   			   //同一天
   			   ret= "今天";
   		   }else{
   			   //同年不同天
   			   ret= DateTimeUtil.dateToString(time2, "MM月dd日");
   		   }
   	   }else{
   		   //不同年
   		   ret= DateTimeUtil.dateToString(time2, "yyyy年MM月dd日");
   	   }
   	   
          return ret;
      }
      catch (Exception ex)
      {
          return dateToString(time2,"yyyy年MM月dd日");
      }
  }
}
