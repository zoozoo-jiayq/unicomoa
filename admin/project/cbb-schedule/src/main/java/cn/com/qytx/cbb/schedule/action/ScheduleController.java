package cn.com.qytx.cbb.schedule.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.qytx.cbb.schedule.domain.Schedule;
import cn.com.qytx.cbb.schedule.service.IScheduleService;
import cn.com.qytx.cbb.worklog.domain.TemporaryWorkLog;
import cn.com.qytx.cbb.worklog.service.TemporaryWorkLogService;
import cn.com.qytx.cbb.worklog.service.impl.TemporaryWorkLogImpl;
import cn.com.qytx.platform.base.action.BaseControllerSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;

/**
 * 功能: 日程
 * 版本: 1.0
 * 开发人员: panbo
 * 创建日期: 2016年6月2日
 * 修改日期: 2016年6月2日
 * 修改列表: 
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseControllerSupport{
	
	private static final Logger logger=Logger.getLogger(ScheduleController.class);
	/**
	 * 日程接口
	 */
	@Resource(name="scheduleServiceImpl")
	IScheduleService scheduleServiceImpl;
	/**
	 * 保存临时任务日程
	 */
	@Resource(name="temporaryWorkLogImpl")
	TemporaryWorkLogService temporaryWorkLogImpl;
	@Resource
	IUser userService;
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/testScheduleList.c")
	public String testScheduleList(HttpServletRequest request ,HttpServletResponse response){
		List<Map<String,List<Schedule>>> mapList = scheduleServiceImpl.getScheduleListByDay("2016-06", 29220397);
		response.setHeader("ContentType", "text/json");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(response.getWriter());
			Gson gson = new Gson();
			writer.print(gson.toJson(mapList));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.flush();
				writer.close();
			}
		}
		
		return null;
	}

	/**
	 * 
	 * 功能：增加日程
	 */
	@RequestMapping("/saveScheduleList.c")
	public void saveScheduleList(Schedule schedule,HttpServletResponse response){
		response.setHeader("ContentType", "text/json");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter pw=null;
        int num=0;
        try {
			pw=new PrintWriter(response.getWriter());
			if(schedule.getId()==null){//新增
				schedule.setCreateTime(new Timestamp(System.currentTimeMillis()));
				UserInfo user= userService.findOne(schedule.getCreateUserId());
				schedule.setCompanyId(user.getCompanyId());
				schedule.setLastUpdateUserId(schedule.getCreateUserId());
				schedule.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
				schedule.setStatus(0);
				schedule.setIsDelete(0);
				scheduleServiceImpl.saveOrUpdate(schedule);
			}else{//修改
				Schedule oldSchedule = scheduleServiceImpl.findOne(schedule.getId());
				oldSchedule.setContent(schedule.getContent());
				oldSchedule.setCompleteTime(schedule.getCompleteTime().toString());
				oldSchedule.setLastUpdateUserId(schedule.getCreateUserId());
				oldSchedule.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
				scheduleServiceImpl.saveOrUpdate(oldSchedule);
			}
			
			pw.print(num);
		} catch (Exception e) {
			num=1;
			pw.print(num);
			e.printStackTrace();
		}finally{
			if(pw!=null){
				pw.flush();
				pw.close();
			}
		}
        
	}

	
	/**
	 * 返回日程日历页的方法
	 * @param userId
	 * @param month
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCalendar.c")
	public void getCalendar(@RequestParam(value="userId",required=true) Integer userId,
			@RequestParam(value="month",required = false) String selectedMonth,
			HttpServletRequest request ,HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if(userId!=null){
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
			     //垃圾代码未删除
				/*int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
				int hourofday = cal.get(Calendar.HOUR_OF_DAY);
				int minute = cal.get(Calendar.MINUTE);
				int second = cal.get(Calendar.SECOND);
				int millisecond = cal.get(Calendar.MILLISECOND);
				long time = cal.getTimeInMillis();*/
				result.put("today", yyyyMMdd(year, month, dayofmonth));
				
				if(StringUtils.isNotBlank(selectedMonth)) {
					cal.set(Calendar.DAY_OF_MONTH, 1);
					cal.set(Calendar.YEAR, Integer.parseInt(selectedMonth.split("-")[0]));
					cal.set(Calendar.MONTH, Integer.parseInt(selectedMonth.split("-")[1])-1);
				}
				year = cal.get(Calendar.YEAR);
				month = cal.get(Calendar.MONTH);
				dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
				
				// 年月日
				List<Map<String, Object>> listMap = new LinkedList<Map<String, Object>>();
				
				for(int i = 1; i <= dayNum(year, month+1); i++){
					Map<String, Object> oneday = new HashMap<String, Object>();
					oneday.put("date", yyyyMMdd(year, month, i));
					listMap.add(oneday);
				}
				
				fullCalPage (listMap);
				
				Schedule schedule = new Schedule();
				schedule.setCreateUserId(userId);
				schedule.setBeginTime((String)listMap.get(0).get("date"));
				schedule.setEndTime((String)listMap.get(listMap.size()-1).get("date"));
				Set<String> pointDaySet = pointDay( scheduleServiceImpl.findCalendarPointByTime(schedule) );
				
				for(Map<String, Object> daymap: listMap){
					if(pointDaySet != null && pointDaySet.contains(daymap.get("date"))){
						daymap.put("point", 1);
					}else {
						daymap.put("point", 0);
					}
				}
				
				result.put("list", listMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		Gson json = new Gson();
		String jsons = json.toJson(result);
		
		response.setHeader("ContentType", "text/json");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(response.getWriter());
			writer.print(jsons);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.flush();
				writer.close();
			}
		}
	}
	
	/**
	 * 日程列表
	 * @param userId
	 * @param completeTime
	 * @param instanceId
	 * @param type
	 * @param status
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getScheduleList.c")
	public void getScheduleList(@RequestParam(value="userId",required=true) Integer userId,
			@RequestParam(value="completeTime",required = false) String completeTime,
			@RequestParam(value="instanceId",required = false) String instanceId,
			@RequestParam(value="type",required = false) String type,
			@RequestParam(value="status",required = false) Integer status,
			HttpServletRequest request ,HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			List<Schedule> list = scheduleServiceImpl.getScheduleList(instanceId, type, status, userId, completeTime);
			Gson json = new Gson();
			String jsonStr = json.toJson(list);
			response.setHeader("ContentType", "text/json");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			writer.print(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(writer != null){
				writer.flush();
				writer.close();
			}
		}
	}
	
	
	/**
	 * 修改日程状态
	 * @param userId 
	 * @param scheduleId 日程Id
	 * @param status -1删除 0撤销 1完成 2未完成
	 * @param undoneReason 未完成原因
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateScheduleStatus.c")
	public void updateScheduleStatus(@RequestParam(value="userId",required=true) Integer userId,
			@RequestParam(value="scheduleId",required = true) Integer scheduleId,
			@RequestParam(value="status",required = true) Integer status,
			@RequestParam(value="undoneReason",required = false) String undoneReason,
			HttpServletRequest request ,HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			Schedule schedule = scheduleServiceImpl.findOne(scheduleId);
			if(schedule==null){
				writer.print(0);
				return;
			}
			if(status==-1){
				schedule.setIsDelete(1);
			}else{
				schedule.setStatus(status);
				//2016.06.15xcj加入代码  再点击完成，未完成的时候，在日志表中生成一条数据
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				TemporaryWorkLog  temporaryWorkLog=new TemporaryWorkLog();
				temporaryWorkLog.setCreateTime(new Date());
				temporaryWorkLog.setCreateUserId(userId);
				temporaryWorkLog.setLastUpdateUserId(userId);
				temporaryWorkLog.setLastUpdateUserId(userId);
				temporaryWorkLog.setNowDate(sdf.format(new Date()));
				temporaryWorkLog.setStatus(status);
				temporaryWorkLog.setStatusShare(2);//默认未分享
				temporaryWorkLog.setIsInOrOut(1);;//来自任务日志
				temporaryWorkLog.setIsDelete(0);
				UserInfo user = userService.findOne(userId);
				temporaryWorkLog.setCompanyId(user.getCompanyId());
				temporaryWorkLog.setWorkLogContent(schedule.getContent());
				if(status==2)
				{
					temporaryWorkLog.setNotFinishReason(schedule.getUndoneReason());	
				}
				temporaryWorkLogImpl.saveTemporaryWorkLog(temporaryWorkLog);
				//2016.06.15xcj加入代码结束
				if(status==2)
					schedule.setUndoneReason(undoneReason);
			}
			schedule.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			schedule.setLastUpdateUserId(userId);
			scheduleServiceImpl.saveOrUpdate(schedule);
			//垃圾代码为删除
			/*response.setHeader("ContentType", "text/json");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");*/
			writer.print(1);
		} catch (Exception e) {
			e.printStackTrace();
			if(writer != null){
				writer.print(0);
			}
		} finally {
			if(writer != null){
				writer.flush();
				writer.close();
			}
		}
	}
	
	
	
	private Set<String> pointDay (List<Schedule> list) {
		Set<String> set = null;
		if(list != null && !list.isEmpty()){
			set = new HashSet<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(Schedule s: list){
				if(s.getCompleteTime() != null){
					set.add(sdf.format(s.getCompleteTime()));
				}
			}
		}
		return set;
	}
	
	/**
	 * 补全日历页(第一列是星期日)
	 * @param listMap
	 */
	private void fullCalPage (List<Map<String, Object>> listMap) {
		// 下标0 yyyy, 下标1 MM(比Calendar大1), 下标2 dd
		String[] firstdayofmonth = ((String)listMap.get(0).get("date")).split("-");
		String[] lastdayofmonth = ((String)listMap.get(listMap.size()-1).get("date")).split("-");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(firstdayofmonth[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(firstdayofmonth[1])-1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(firstdayofmonth[2]));
		do{
			// 先减一天
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Map<String, Object> oneday = new HashMap<String, Object>();
			oneday.put("date", yyyyMMdd(cal));
			listMap.add(0, oneday);
		}while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY);
		
		cal.set(Calendar.YEAR, Integer.parseInt(lastdayofmonth[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(lastdayofmonth[1])-1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(lastdayofmonth[2]));
		
		int lastday = 7*6-listMap.size();
		for(int i = 0; i < lastday; i++){
			// 先加一天
			cal.add(Calendar.DAY_OF_MONTH, 1);
			Map<String, Object> oneday = new HashMap<String, Object>();
			oneday.put("date", yyyyMMdd(cal));
			listMap.add(oneday);
		}
	}
	
	/**
	 * 日期补全0
	 * @param cal
	 * @return
	 */
	private String yyyyMMdd(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
		return year + "-" + ((month+1)<10?("0"+(month+1)):(month+1)) + "-" + (dayofmonth<10?("0"+dayofmonth):dayofmonth);
	}
	
	/**
	 * 日期补全0
	 * @param year
	 * @param month(从0开始)
	 * @param dayofmonth
	 * @return
	 */
	private String yyyyMMdd(int year, int month, int dayofmonth) {
		return year + "-" + ((month+1)<10?("0"+(month+1)):(month+1)) + "-" + (dayofmonth<10?("0"+dayofmonth):dayofmonth);
	}
	
	/**
	 * 计算这个月有多少天
	 * @param year 年
	 * @param month 月(从1开始)
	 * @return int 天数
	 */
	private int dayNum(int year, int month){
		int result = 31;
		switch(month){
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			break;
		case 2:
			if(isLeapYear(year)){
				result = 29;
			}else {
				result = 28;
			}
			break;
		default:
			result = 30;
			break;
		}
		return result;
	}
	
	/**
	 * 判断是不是闰年
	 * @param year
	 * @return
	 */
	private boolean isLeapYear(int year){
		if(year>=3200){
			if(year%3200==0 && year%172800==0){
				return true;
			}else{
				return false;
			}
		}else{
			return (year%4==0&&year%100!=0)||year%400==0;
		}
	}
	/**
	 * 
	 * 功能：日程月报表-日记录列表
	 * @param completeMonth
	 * @param userId
	 * @param response
	 */
	@RequestMapping("/getScheduleListByDay.c")
    public void getScheduleListByDay(String completeMonth,Integer userId,HttpServletResponse response){
    	response.setHeader("ContentType", "text/json");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw=null;
        try {
			pw=new PrintWriter(response.getWriter());
			
			List<Map<String,List<Schedule>>> mapList=scheduleServiceImpl.getScheduleListByDay(completeMonth, userId);
			Map<String, Object> map=new HashMap<String, Object>();	
			if(mapList!=null && mapList.size()>0){
				
				List<Schedule> listes=new ArrayList<Schedule>();
				for(int i=0;i<mapList.size();i++){				
					Map<String,List<Schedule>> mapes=mapList.get(i);
					Set<String> set = mapes.keySet();
					for (String time : set) {
						List<Schedule> list = mapes.get(time);
						 for(Schedule s:list){
							 SimpleDateFormat sf=new SimpleDateFormat("dd日");
							 s.setTime(sf.format(s.getCompleteTime()));				
							 listes.add(s);
						 }
					}			
				}
				map.put("list", listes);
				map.put("today", new SimpleDateFormat("dd日").format(new Date()));
			}else{
				map.put("list", 0);
			}
			
			List<Map<String,Integer>> countList=new ArrayList<Map<String,Integer>>();
			
			Map<String,Integer> countMap=scheduleServiceImpl.getScheduleCountByMonth(completeMonth, userId);			
			countList.add(countMap);				
			map.put("count",countList);						
			Gson json = new Gson();
			String jsons = json.toJson(map);
			pw.print(jsons);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
        
    }
}
