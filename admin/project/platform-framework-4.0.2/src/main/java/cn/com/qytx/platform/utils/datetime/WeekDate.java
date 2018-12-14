package cn.com.qytx.platform.utils.datetime;

/**
 * 周/日期对应类
 * User:黄普友
 * Date: 13-6-5
 * Time: 上午9:56
 */
public class WeekDate {
    private int year;
    private int week;
    private String startDate; //开始日期
    private String endDate;   //结束日期

    public String toString()
    {
        return year+"年，第"+week+"周"+"，开始日期："+startDate+"，结束日期："+endDate;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
