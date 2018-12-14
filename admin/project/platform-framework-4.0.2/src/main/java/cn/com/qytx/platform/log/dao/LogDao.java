package cn.com.qytx.platform.log.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.org.domain.UserInfo;


/**
 * 日志类dao
 */
@Repository
public class LogDao extends BaseDao<Log, Integer> implements Serializable
{
    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	// 日期格式化
    private final  SimpleDateFormat SDFYMD = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 添加日志
     * @param companyId 企业ID
     * @param userId 登陆人员ID
     * @param sysName 系统名称
     * @param moduleName 模块名称
     * @param action //操作类型，如 人员登陆，添加部门。。。。。
     * @param content // 操作内容
     * @param refId //第三方id 比方说删除了某个人员，这里填写删除人员的ID
     * @param ip
     */
    public void save(int companyId, int userId, String userName, String sysName, String moduleName,
            String action, String content, int refId, String ip)
    {
        Log log = new Log();
        log.setAction(action);
        log.setCompanyId(companyId);
        log.setUserId(userId);
        log.setUserName(userName);
        log.setSysName(sysName);
        log.setModuleName(moduleName);
        log.setLogContent(content);
        log.setRefId(refId);
        log.setIp(ip);
        saveOrUpdate(log);
    }

    /**
     * 删除日志
     * @param arg0 日志ID
     */
    public void delete(int arg0)
    {
        String hql = "update Log set isDelete=1 where id ='" + arg0 + "'";
        this.bulkDelete(hql);
    }

    /**
     * 批量删除日志
     * @param max 
     * @param logType 日志类型
     * @param userIds 日志记录人ID集合，多个ID之间用","隔开
     * @param startTime 日志的开始时间
     * @param endTime 日志的结束时间
     * @param ip 日志的来源IP
     * @param remark 备注
     */
    public void deleteListByParam(Integer max, Integer logType, String userIds, String startTime,
            String endTime, String ip, String remark)
    {
        String hql = "update Log as log set log.isDelete=1 where log.id in (select l.id from Log as l where companyId="+TransportUser.get().getCompanyId()+" and l.type=0 and l.isDelete=0";
        if (logType != null && logType != 0)
        {
            hql += " and logType =" + logType + "";
        }
        if (userIds != null && !userIds.equals(""))
        {
            hql += " and l.userId in (" + userIds + ")";
        }
        if (startTime != null && !startTime.equals(""))
        {
            hql += " and l.insertTime>='" + startTime + "'";
        }
        if (endTime != null && !endTime.equals(""))
        {
            hql += " and l.insertTime<='" + endTime + "'";
        }
        if (ip != null && !ip.equals(""))
        {
            hql += " and l.ip like '%" + ip + "%'";
        }
        if (remark != null && !remark.equals(""))
        {
            hql += " and l.remark like '%" + remark + "%'";
        }
        hql += ")";
        this.bulkDelete(hql);
    }

    /**
     * 功能：根据参数查询日志
     * @param max 最大显示条数
     * @param logType 系统日志类型
     * @param userIds 登陆人
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param ip IP
     * @param remark 备注
     * @return 根据查询条件查询 loglist
     */
    public List<Log> getLogListByParam(Integer max, Integer logType, String userIds,
            String startTime, String endTime, String ip, String remark,String userName,String groupIds)
    {
    	try {
	    	List<Object> value = new ArrayList<Object>();
	        String hql = "from Log where isDelete=0 and type=0 and companyId="+TransportUser.get().getCompanyId();
	        if (logType != null && logType != 0)
	        {
	            hql += " and logType =" + logType + "";
	        }
	        if (userIds != null && !userIds.equals(""))
	        {
	            hql += " and userId in (" + userIds + ")";
	        }
	        if (startTime != null && !startTime.equals(""))
	        {
	            hql += " and insertTime>=? ";
					value.add(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTime));
	        }
	        if (endTime != null && !endTime.equals(""))
	        {
	            hql += " and insertTime<=? ";
	            value.add(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTime));
	        }
	        if (ip != null && !ip.equals(""))
	        {
	            hql += " and ip like '%" + ip + "%'";
	        }
	        if (remark != null && !remark.equals(""))
	        {
	            hql += " and remark like '%" + remark + "%'";
	        }
	        if(userName!=null && !userName.equals("")){
	        	hql+=" and userName like '%" + userName + "%'";
	        }
	    	if(groupIds!=null && !groupIds.equals("")){
				hql+=" and userName in (select ui.userName from UserInfo ui where ui.groupId in ("+groupIds+")) ";
			}
	    	hql+=" and companyId = "+TransportUser.get().getCompanyId();
	        hql += " order by insertTime desc";
	        
	        Query query = entityManager.createQuery(hql);
	        query.setFirstResult(0);
	        query.setMaxResults(max);
	        if(value.size()>0){
	        	for(int i=0;i<value.size();i++){
	        		query.setParameter(i+1, value.get(i));
	        	}
	        }
	        return query.getResultList();
    	} catch (ParseException e) {
    		e.printStackTrace();
    		return new ArrayList<Log>();
    	}
    }

    /**
     * 功能：最新的修改密码对象
     * @return
     */
    public Log getLasterLog()
    {
        String hql = "from Log where  isDelete=0 and type=0 and logType=11 and companyId="+TransportUser.get().getCompanyId()+" order by insertTime desc";
        List list = this.find(hql);
        Log l = null;
        if (list != null && list.size() != 0)
        {
            l = (Log) list.get(0);
        }
        return l;
    }

    /**
     * 日志概况
     * @return 返回日志概况的字符串
     */
    public String getAllLogNums()
    {
        Date now = new Date();
        String nowStr = SDFYMD.format(now);
        String year = nowStr.substring(0, 4);
        String month = nowStr.substring(5, 7);
        String day = nowStr.substring(8, 10);
     
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = getEndDateOfMonth(sdf.format(now));
        String hql = " isDelete=0 and type=0 and logType=1 and companyId="+TransportUser.get().getCompanyId();
        String hqlYear = " isDelete=0 and  logType=1 and type=0 and companyId="+TransportUser.get().getCompanyId()+" and insertTime>='" + year
                + "-01-01 00:00:00' and insertTime <='" + year
                + "-12-31 23:59:59' ";
        String hqlMonth = "isDelete=0 and  logType=1 and type=0 and companyId="+TransportUser.get().getCompanyId()+"  and insertTime>='" + year + "-"
                + month + "-01 00:00:00' and insertTime <='" + endTime
                + " 23:59:59'";
        String hqlDay = " isDelete=0 and logType=1 and type=0 and companyId="+TransportUser.get().getCompanyId()+" and insertTime>='" + year + "-"
                + month + "-" + day + " 00:00:00' and insertTime <='" + year + "-" + month + "-"
                + day + " 23:59:59'";

        long allDay = 0;
        Integer allNum = 0;
        Integer thisYearNum = 0;
        Integer thisMonthNum = 0;
        Integer todayNum = 0;
        long averageNum = 0;
        thisYearNum = this.count(hqlYear);
        thisMonthNum = this.count(hqlMonth);
        todayNum = this.count(hqlDay);

        allNum = this.count(hql);
        Date endD = (Date) super.createSqlQuery("select max(insert_time) m from log where company_id="+TransportUser.get().getCompanyId()).get(0);
        Date beginD = (Date) super.createSqlQuery("select min(insert_time) m from log where company_id="+TransportUser.get().getCompanyId()).get(0);
        allDay = getTimeSpan2(SDFYMD.format(beginD),SDFYMD.format(endD));

        if (allDay != 0)
        {
            averageNum = allNum / allDay;
        }
        return allDay + "-" + allNum + "-" + thisYearNum + "-" + thisMonthNum + "-" + todayNum
                + "-" + averageNum;
    }

    /**
     * 获取一个月的最后一天
     * @param dat 
     * @return
     */
    private  static String getEndDateOfMonth(String dat)
    {

        String str = dat.substring(0, 8);

        String month = dat.substring(5, 7);

        int mon = Integer.parseInt(month);

        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12)
        {

            str += "31";

        }
        else if (mon == 4 || mon == 6 || mon == 9 || mon == 11)
        {

            str += "30";

        }
        else
        {

            if (isLeapYear(dat))
            {

                str += "29";

            }
            else
            {

                str += "28";

            }

        }

        return str;

    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     * @param strDate
     * @return
     */

    private static Date strToDate(String strDate)
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        ParsePosition pos = new ParsePosition(0);

        Date strtodate = formatter.parse(strDate, pos);

        return strtodate;

    }

    /**
     * 判断是否润年
     * @param ddate
     * @return
     */

    private static boolean isLeapYear(String ddate)
    {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */

        Date d = strToDate(ddate);

        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();

        gc.setTime(d);

        int year = gc.get(Calendar.YEAR);

        if ((year % 400) == 0)

            return true;

        else if ((year % 4) == 0)
        {

            if ((year % 100) == 0)

                return false;

            else

                return true;

        }
        else

            return false;

    }

    /**
     * 获取两个时间差
     * @param startTime
     * @param endTime
     * @return
     */
    private static long getTimeSpan2(String startTime, String endTime)
    {
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date now = df.parse(startTime);
            Date date = df.parse(endTime);
            long day = (date.getTime() - now.getTime()) / 86400000;
            // System.out.println(date.getDay()+"-----"+now.getDay());
            if (day <= 0)
            {
                day = 0;
            }
            long allDay = day + 1;
            return allDay;
        }
        catch (Exception ex)
        {
            return 0;
        }
    }
    
    
	/**
	 * 功能：通过日志字段查询得到分页对象
	 * @param pageable
	 * @param logType 日志类型
	 * @param keyWord 关键字 (IP地址 备注 登录人)
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 * @return
	 */
	public Page<Log> findByPage(UserInfo user,Pageable pageable, Integer logType,
			String keyWord, String startTime, String endTime) {
		StringBuffer query=new StringBuffer();
		query.append(" isDelete = 0 and companyId="+user.getCompanyId());
		if(logType != null && logType.intValue() > 0){
			query.append(" and logType="+logType.intValue());
		}
		if(StringUtils.isNotBlank(startTime)){
			query.append(" and insertTime >='"+startTime+"'");
		}
		if(StringUtils.isNotBlank(endTime)){
			query.append(" and insertTime <='"+endTime+"'");
		}
		if(StringUtils.isNotBlank(keyWord)){
			query.append(" and (ip like '%"+keyWord+"%' or ipAddress like '%"+keyWord+"%' or userName like '%"+keyWord+"%' or remark like '%"+keyWord+"%')");
		}
		return this.findAll(query.toString(), pageable);
	}

	/**
	 * 获取用户数据
	 * @param companyId
	 * @return
	 */
	public int getUserInfoReport(int companyId){
		String sql = "SELECT COUNT(*) AS c FROM (SELECT l.user_id FROM LOG AS l,tb_user_info AS u "
				+ "WHERE l.user_id = u.user_id AND l.company_id = "+companyId+" GROUP BY l.user_id) AS v ";
		 Object o = super.entityManager.createNativeQuery(sql).getSingleResult();
		 if(o!=null){
			 return Integer.parseInt(o.toString());
		 }else{
			 return 0;
		 }
	}
}
