package cn.com.qytx.cbb.attendance.action;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import cn.com.qytx.cbb.attendance.domain.Attendance;
import cn.com.qytx.cbb.attendance.domain.AttendanceDays;
import cn.com.qytx.cbb.attendance.domain.AttendancePlan;
import cn.com.qytx.cbb.attendance.service.AttendancePlanService;
import cn.com.qytx.cbb.attendance.service.IAttendance;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowLeave;
import cn.com.qytx.cbb.jbpmApp.service.IWorkflowLeaveService;
import cn.com.qytx.cbb.myapply.domain.MyStarted;
import cn.com.qytx.cbb.myapply.service.IMyStarted;
import cn.com.qytx.platform.utils.ExportExcel;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：新考勤统计
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2017年7月10日
 * 修改日期：2017年7月10日	
 */
public class AttendanceStatisticsAction extends BaseActionSupport{
	private static final long serialVersionUID = 1L;

	
	@Resource
	private AttendancePlanService planService;
	@Resource
	private IAttendance attendanceService;
	@Resource(name="workflowLeaveImpl")
	private IWorkflowLeaveService workflowLeaveImpl;
	@Autowired
	private IMyStarted myStartedImpl;	
	
	private String startTime;
	private String endTime;
	private Integer groupId;
	private String searchKey;
	private Integer userId;//用户Id
	
	private Integer attState;//1 正常 2 迟到 3 早退
	
	private Integer type;//1 请假 2 工作假 3 休假
	
	
	private Integer state;//1/迟到 2/缺卡3/外勤4/请假
	
	
	/**
	 * 功能：考勤月度汇总
	 * @return
	 */
	public String monthStatistics(){
		try{
			Page<Map<String,Object>> pageInfo = null;
			UserInfo userInfo = this.getLoginUser();
			if(userInfo!=null){
					List<AttendancePlan> apList = planService.findAll();//获取所有的考勤计划
					AttendancePlan ap = apList.get(0);//至少会有一个，新密检察院特殊处理
					pageInfo = attendanceService.monthStatistics(getPageable(), startTime, endTime, userInfo.getCompanyId(), searchKey, groupId, ap,state);
					List<Map<String,Object>> list = pageInfo.getContent();
					if(list!=null&&list.size()>0){
						List<AttendanceDays> adList = attendanceService.getAttendanceDays(startTime, endTime);//获取应出勤日期和对应周期
						/*获取此时间段 用户与请假时长 */
						Map<Integer,Double> leaveMap = attendanceService.getUserLeaveDays(startTime, endTime, userInfo.getCompanyId(), ap,1);
						/*获取此时间段 用户与工作假时长 */
						Map<Integer,Double> workLeaveMap = attendanceService.getUserLeaveDays(startTime, endTime, userInfo.getCompanyId(), ap,2);
						/*获取此时间段 用户与休息假时长 */
						Map<Integer,Double> sleepLeaveMap = attendanceService.getUserLeaveDays(startTime, endTime, userInfo.getCompanyId(), ap,3);
						/*获取此时间段 用户出勤天数 */
						Map<Integer,Double> userAttendDaysMap = attendanceService.getUserAttendDaysMap(startTime, endTime, userInfo.getCompanyId());
						
						int no = this.getIDisplayStart()+1;
						DecimalFormat df = new DecimalFormat("#0.0");//格式化
						Integer lateDuration = 0;
						Integer earlyDuration = 0;
						Double attendDays = 0.0;
						Double leaveHour = 0.0;
						Double workLeavehour = 0.0;
						Double sleepLeavehour = 0.0;
						for(Map<String,Object> map:list){
							lateDuration = 0;
							earlyDuration = 0;
							attendDays = 0.0;
							leaveHour = 0.0;
							workLeavehour = 0.0;
							sleepLeavehour = 0.0;
							map.put("no", no++);
							Integer userId = (Integer)map.get("userId");
							if(userAttendDaysMap.containsKey(userId)){
								attendDays = userAttendDaysMap.get(userId);
							}
							lateDuration = (Integer)map.get("lateDuration");
							earlyDuration = (Integer)map.get("earlyDuration");
							map.put("lateDurationNum",lateDuration);
							map.put("earlyDurationNum",earlyDuration);
							if(lateDuration > 60){
			 					map.put("lateDuration", (lateDuration/60)+"小时"+(lateDuration%60>0?(lateDuration%60+"分钟"):""));
							}else{
								map.put("lateDuration", lateDuration+"分钟");
							}
							if(earlyDuration > 60){
								map.put("earlyDuration", (earlyDuration/60)+"小时"+(earlyDuration%60>0?(earlyDuration%60+"分钟"):""));
							}else{
								map.put("earlyDuration", earlyDuration+"分钟");
							}
							Double allLackTimes = 0.0;
							Integer onDutyLackTimes = 0;
							Integer offDutyLackTimes = 0;
							Integer onTimes = (Integer)map.get("onTimes");
							Integer offTimes = (Integer)map.get("offTimes");
							if(adList!=null&&adList.size()>0){
								allLackTimes = adList.size() - attendDays;
								onDutyLackTimes = adList.size() - onTimes;
								offDutyLackTimes = adList.size() - offTimes;
							}
							
							map.put("outDays", 0);
							leaveHour = leaveMap.get(userId);
							if(leaveHour!=null){
								String hourStr = df.format(leaveHour);
			 					if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
			 						hourStr = (new Double(leaveHour)).intValue()+"";
			 					}
								map.put("leaveDays", hourStr);
							}else{
								leaveHour = 0.0;
								map.put("leaveDays","0");
							}
							
							workLeavehour = workLeaveMap.get(userId);
							if(workLeavehour!=null){
								String hourStr = df.format(workLeavehour);
			 					if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
			 						hourStr = (new Double(workLeavehour)).intValue()+"";
			 					}
								map.put("workLeaveDays", hourStr);
							}else{
								workLeavehour = 0.0;
								map.put("workLeaveDays","0");
							}
							
							sleepLeavehour = sleepLeaveMap.get(userId);
							if(sleepLeavehour!=null){
								String hourStr = df.format(sleepLeavehour);
			 					if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
			 						hourStr = (new Double(sleepLeavehour)).intValue()+"";
			 					}
								map.put("sleepLeaveDays", hourStr);
							}else{
								sleepLeavehour = 0.0;
								map.put("sleepLeaveDays","0");
							}
							
							allLackTimes = allLackTimes-sleepLeavehour-workLeavehour;
							if(allLackTimes>=0.0){
								String allLackTimesStr = df.format(allLackTimes);
								if(allLackTimesStr.indexOf(".")>0&&"0".equals(allLackTimesStr.substring(allLackTimesStr.indexOf(".")+1))){
									allLackTimesStr = (new Double(allLackTimes)).intValue()+"";
			 					}
								map.put("allLackTimes", allLackTimesStr);
							}else{
								map.put("allLackTimes", "0");
							}
							
							//attendDays = attendDays+sleepLeavehour+workLeavehour;
							if(attendDays>=0.0){
								String attendDaysStr = df.format(attendDays);
								if(attendDaysStr.indexOf(".")>0&&"0".equals(attendDaysStr.substring(attendDaysStr.indexOf(".")+1))){
									attendDaysStr = (new Double(attendDays)).intValue()+"";
			 					}
								map.put("attendDays", attendDaysStr);
							}else{
								map.put("attendDays", "0");
							}
							map.put("onDutyLackTimes", onDutyLackTimes);
							map.put("offDutyLackTimes", offDutyLackTimes);
						}
					}
			}
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("iTotalRecords", pageInfo!=null?pageInfo.getTotalElements():0);
			jsonMap.put("iTotalDisplayRecords", pageInfo!=null?pageInfo.getTotalElements():0);
			jsonMap.put("aaData", pageInfo!=null?pageInfo.getContent():null);
			Gson gson = new Gson();
			String gsonStr = gson.toJson(jsonMap);
			ajax(gsonStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String dayStatistics(){
		try{
			Page<Map<String,Object>> pageInfo = null;
			UserInfo userInfo = this.getLoginUser();
			if(userInfo!=null){
					List<AttendancePlan> apList = planService.findAll();
					AttendancePlan ap = apList.get(0);
					pageInfo = attendanceService.dayStatistics(getPageable(), startTime, endTime, userInfo.getCompanyId(), searchKey, groupId, ap, state);
					List<Map<String,Object>> listContent = pageInfo.getContent();
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>(listContent); 
					if(list!=null&&list.size()>0){
						Timestamp onTimes = ap.getCommonOn();
						/*Timestamp amOffTimes = ap.getCommonAmOff();
						Timestamp pmOnTimes = ap.getCommonPmOn();*/
						Timestamp offTimes = ap.getCommonOff();
						SimpleDateFormat sdfMMDD = new SimpleDateFormat("HH:mm");
						String onTimesHHMM = sdfMMDD.format(onTimes);
						/*String amOffTimesHHMM = sdfMMDD.format(amOffTimes);
						String pmOnTimesHHMM = sdfMMDD.format(pmOnTimes);*/
						String offTimesHHMM = sdfMMDD.format(offTimes);
						/*List<AttendanceDays> adList = attendanceService.getAttendanceDays(startTime, endTime);*/
						Map<String,Double> leaveMap = attendanceService.getDateLeaveNum(startTime, endTime, userInfo.getCompanyId(), ap,1);
						Map<String,Double> workLeaveMap = attendanceService.getDateLeaveNum(startTime, endTime, userInfo.getCompanyId(), ap,2);
						Map<String,Double> sleepLeaveMap = attendanceService.getDateLeaveNum(startTime, endTime, userInfo.getCompanyId(), ap,3);
						DecimalFormat df = new DecimalFormat("#0.0");
						int no = this.getIDisplayStart()+1;
						Timestamp tempOnTimes = null;
						/*Timestamp tempAmOffTimes = null;
						Timestamp tempPmOnTimes = null;*/
						Timestamp tempOffTimes = null;
						Integer lateTimes = 0;
						Integer earlyTimes = 0;
						Long lateDuration = 0l;
						Long earlyDuration = 0l;
						Integer onDutyLackTimes = 0;
						Integer offDutyLackTimes = 0;
						String createDate = null;
						Double hour = 0.00;
						Double attendDays = 0.0;
						Double allLackTimes = 1.0;
						for(Map<String,Object> map:list){
							map.put("no", no++);
							String onTime = (String)map.get("onTime");
							/*String amOffTime = (String)map.get("amOffTime");
							String pmOnTime = (String)map.get("pmOnTime");*/
							String offTime = (String)map.get("offTime");
							String onState = (String)map.get("onState");
							/*String amOffState = (String)map.get("amOffState");
							String pmOnState = (String)map.get("pmOnState");*/
							String offState = (String)map.get("offState");
							attendDays = 0.0;
							allLackTimes = 1.0;
							if(StringUtils.isNoneBlank(onTime)&&StringUtils.isNoneBlank(offTime)){
								attendDays = 1.0;
								allLackTimes = 0.0;
							}else if((StringUtils.isNoneBlank(onTime)||StringUtils.isNoneBlank(offTime))){
								attendDays = 1.0;
								allLackTimes = 0.0;
							}
							String week = (String)map.get("week");
							if("周六".equals(week)||"周日".equals(week)){
								map.put("attendTime", "休息");
							}else{
								//map.put("attendTime", "日常考勤"+onTimesHHMM+"-"+amOffTimesHHMM+"&nbsp;"+pmOnTimesHHMM+"-"+offTimesHHMM);
								map.put("attendTime", "日常考勤"+onTimesHHMM+"-"+offTimesHHMM);
							}
							
							lateTimes = 0;
							earlyTimes = 0;
							lateDuration = 0l;
							earlyDuration = 0l;
							onDutyLackTimes = 1;
							offDutyLackTimes = 1;
							if(StringUtils.isNoneBlank(onTime)){
								onDutyLackTimes--;
							}
							/*if(StringUtils.isNoneBlank(pmOnTime)){
								onDutyLackTimes--;
							}
							if(StringUtils.isNoneBlank(amOffTime)){
								offDutyLackTimes--;
							}*/
							if(StringUtils.isNoneBlank(offTime)){
								offDutyLackTimes--;
							}
							if("迟到".equals(onState)||"外勤-迟到".equals(onState)){
								tempOnTimes = Timestamp.valueOf("1970-01-01 "+onTime+":00.000");
								lateDuration += (tempOnTimes.getTime()-onTimes.getTime())/(1000*60);
								lateTimes ++;
							}
							/*if("早退".equals(amOffState)){
								tempAmOffTimes = Timestamp.valueOf("1970-01-01 "+amOffTime+":00.000");
								earlyDuration += (amOffTimes.getTime()-tempAmOffTimes.getTime())/(1000*60);
								earlyTimes ++;
							}
							if("迟到".equals(pmOnState)){
								tempPmOnTimes = Timestamp.valueOf("1970-01-01 "+pmOnTime+":00.000");
								lateDuration += (tempPmOnTimes.getTime()-pmOnTimes.getTime())/(1000*60);
								lateTimes ++;
							}*/
							if("早退".equals(offState)){
								tempOffTimes = Timestamp.valueOf("1970-01-01 "+offTime+":00.000");
								earlyDuration += (offTimes.getTime()-tempOffTimes.getTime())/(1000*60);
								earlyTimes ++;
							}
							map.put("lateTimes", lateTimes);
							map.put("earlyTimes", earlyTimes);
							
							if(lateDuration > 60){
			 					map.put("lateDuration", (lateDuration/60)+"小时"+(lateDuration%60>0?(lateDuration%60+"分钟"):""));
							}else{
								map.put("lateDuration", lateDuration+"分钟");
							}
							if(earlyDuration > 60){
								map.put("earlyDuration", (earlyDuration/60)+"小时"+(earlyDuration%60>0?(earlyDuration%60+"分钟"):""));
							}else{
								map.put("earlyDuration", earlyDuration+"分钟");
							}
							
							
							map.put("lateDurationNum", lateDuration);
							map.put("earlyDurationNum", earlyDuration);
							map.put("onDutyLackTimes", onDutyLackTimes);
							map.put("offDutyLackTimes", offDutyLackTimes);
							createDate = (String)map.get("createDate");
							userId = (Integer)map.get("userId");
							if(leaveMap.containsKey(createDate+"_"+userId)){
								hour = leaveMap.get(createDate+"_"+userId);
								String hourStr = df.format(hour);
			 					if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
			 						hourStr = (new Double(hour)).intValue()+"";
			 					}
								map.put("leaveDays", hourStr);
							}else{
								map.put("leaveDays","0");
							}
							if(sleepLeaveMap.containsKey(createDate+"_"+userId)){
								hour = sleepLeaveMap.get(createDate+"_"+userId);
								allLackTimes = allLackTimes - hour;
								String hourStr = df.format(hour);
								if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
									hourStr = (new Double(hour)).intValue()+"";
								}
								map.put("sleepLeaveDays", hourStr);
							}else{
								map.put("sleepLeaveDays","0");
							}
							
							if(workLeaveMap.containsKey(createDate+"_"+userId)){
								hour = workLeaveMap.get(createDate+"_"+userId);
								allLackTimes = allLackTimes - hour;
								String hourStr = df.format(hour);
								if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
									hourStr = (new Double(hour)).intValue()+"";
								}
								map.put("workLeaveDays", hourStr);
							}else{
								map.put("workLeaveDays","0");
							}
							
							if(allLackTimes>=0.0){
								String allLackTimesStr = df.format(allLackTimes);
								if(allLackTimesStr.indexOf(".")>0&&"0".equals(allLackTimesStr.substring(allLackTimesStr.indexOf(".")+1))){
									allLackTimesStr = (new Double(allLackTimes)).intValue()+"";
			 					}
								map.put("allLackTimes", allLackTimesStr);
							}else{
								map.put("allLackTimes", "0");
							}
							
							//attendDays = attendDays+sleepLeavehour+workLeavehour;
							if(attendDays>=0.0){
								String attendDaysStr = df.format(attendDays);
								if(attendDaysStr.indexOf(".")>0&&"0".equals(attendDaysStr.substring(attendDaysStr.indexOf(".")+1))){
									attendDaysStr = (new Double(attendDays)).intValue()+"";
			 					}
								map.put("attendDays", attendDaysStr);
							}else{
								map.put("attendDays", "0");
							}
							
						}
					}
			}
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("iTotalRecords", pageInfo!=null?pageInfo.getTotalElements():0);
			jsonMap.put("iTotalDisplayRecords", pageInfo!=null?pageInfo.getTotalElements():0);
			jsonMap.put("aaData", pageInfo!=null?pageInfo.getContent():null);
			Gson gson = new Gson();
			String gsonStr = gson.toJson(jsonMap);
			ajax(gsonStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public String lateOrEarlyList(){
		try{
			List<Attendance> attendList = attendanceService.getAttendanceRecodes(userId, startTime, endTime, attState, "asc");
			List<Map<String,Object>> mapList = null;
			if(attendList!=null&&!attendList.isEmpty()){
				mapList = new ArrayList<Map<String,Object>>();
				List<AttendancePlan> apList = planService.findAll();
				AttendancePlan ap = apList.get(0);
				Timestamp onTimes = ap.getCommonOn();
 				Timestamp amOffTimes = ap.getCommonAmOff();
 				Timestamp pmOnTimes = ap.getCommonPmOn();
 				Timestamp offTimes = ap.getCommonOff();
 				SimpleDateFormat sdfHHMM = new SimpleDateFormat("HH:mm");
				SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
				String onTimesHHMM = sdfHHMM.format(onTimes);
				/*String amOffTimesHHMM = sdfHHMM.format(amOffTimes);
				String pmOnTimesHHMM = sdfHHMM.format(pmOnTimes);*/
				String offTimesHHMM = sdfHHMM.format(offTimes);
				Integer tempState = null;
				Integer tempType = null;
				long hour = 0;
				long minute = 0;
				//String hourStr = "";
				Timestamp recordTime = null;
				Timestamp newRecordTime = null;
				String formatRecordTime = "";
				//DecimalFormat df = new DecimalFormat("#0.00");
				for(Attendance attend:attendList){
					Map<String,Object> map = new HashMap<String, Object>();
					tempState = attend.getAttState();
					recordTime = attend.getCreateTime();
					tempType = attend.getAttType();
					formatRecordTime = sdfHHMM.format(recordTime);
					if(tempState.intValue()==2){//迟到
						if(tempType.intValue()==10){
							newRecordTime = Timestamp.valueOf("1970-01-01 "+formatRecordTime+":00.000");
							minute = (newRecordTime.getTime() - onTimes.getTime())/(1000*60);
						}
						/*if(tempType.intValue()==11){
							newRecordTime = Timestamp.valueOf("1970-01-01 "+formatRecordTime+":00.000");
							minute = (newRecordTime.getTime() - pmOnTimes.getTime())/(1000*60);
						}*/
					}
					
					if(tempState.intValue()==3){//早退
						/*if(tempType.intValue()==20){
							newRecordTime = Timestamp.valueOf("1970-01-01 "+formatRecordTime+":00.000");
							minute = ( amOffTimes.getTime()- newRecordTime.getTime())/(1000*60);
						}*/
						if(tempType.intValue()==21){
							newRecordTime = Timestamp.valueOf("1970-01-01 "+formatRecordTime+":00.000");
							minute = (offTimes.getTime() - newRecordTime.getTime())/(1000*60);
						}
					}
					
					if(minute > 60){
						hour = minute/60;
	 					map.put("timeLong", hour+"小时"+(minute%60>0?(minute%60+"分钟"):""));
					}else{
						map.put("timeLong", minute+"分钟");
					}
					
					map.put("recordTime", formatRecordTime);
					map.put("recordDate", sdfYYYYMMDD.format(recordTime));
					map.put("attendTime", "日常考勤("+onTimesHHMM+"-"+offTimesHHMM+")");
					mapList.add(map);
				}
			}
			Gson gson = new Gson();
			String gsonStr = gson.toJson(mapList);
			ajax(gsonStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	
	

	
	public String leaveList(){
		try {
			UserInfo userInfo = this.getLoginUser();
			List<WorkflowLeave> wflList = workflowLeaveImpl.getWorkflowLeaveList(startTime, endTime, userInfo.getCompanyId(), userId,type);
			List<Map<String,Object>> mapList = null;
 			if(wflList!=null&&!wflList.isEmpty()){
 				mapList = new ArrayList<Map<String,Object>>();
 				List<AttendancePlan> apList = planService.findAll();
 				AttendancePlan ap = apList.get(0);
 				Timestamp onTimes = ap.getCommonOn();
 				Timestamp offTimes = ap.getCommonOff();
 				double hourDay = ((double)(offTimes.getTime() - onTimes.getTime()))/(1000*60*60);
 				Calendar calendarStart = Calendar.getInstance();
 				Calendar calendarEnd = Calendar.getInstance();
 				SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
 				SimpleDateFormat sdfYYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
 				SimpleDateFormat sdfHHMMSS = new SimpleDateFormat("HH:mm:ss");
 				double hour = 0.0;
 				double dayNum = 0.0;
 				DecimalFormat df = new DecimalFormat("#0.0");
 				for(WorkflowLeave wfl:wflList){
 					Map<String,Object> map = new HashMap<String, Object>();
 					String instanceId = wfl.getInstanceId();
 					MyStarted myStart = myStartedImpl.findByInstanceId(instanceId);
 					map.put("title", myStart!=null?myStart.getTitle():"--");
 					hour = 0.0;
 					Timestamp startLeaveTime = wfl.getStartLeaveTime();
 					Timestamp endLeaveTime = wfl.getEndLeaveTime();
 					map.put("startLeaveTime", sdfYYYYMMDDHHMM.format(startLeaveTime));
 					map.put("endLeaveTime", sdfYYYYMMDDHHMM.format(endLeaveTime));
 					String startLeaveTimestr = sdfYYYYMMDD.format(startLeaveTime);
 					String endLeaveTimestr = sdfYYYYMMDD.format(endLeaveTime);
 					String startLeaveHHMMSS = sdfHHMMSS.format(startLeaveTime);
 					String endLeaveHHMMSS = sdfHHMMSS.format(endLeaveTime);
 					Timestamp newStartLeaveTs = Timestamp.valueOf("1970-01-01 "+startLeaveHHMMSS);
 					Timestamp newEndLeaveTs = Timestamp.valueOf("1970-01-01 "+endLeaveHHMMSS);
 					if(!startLeaveTimestr.equals(endLeaveTimestr)){
						if(newStartLeaveTs.getTime()<onTimes.getTime()){
							hour += hourDay;
						}
						if(newStartLeaveTs.getTime()>=onTimes.getTime()&&newStartLeaveTs.getTime()<=offTimes.getTime()){
							hour+=(double)(offTimes.getTime() - newStartLeaveTs.getTime())/(1000*60*60);
						}
						if(newEndLeaveTs.getTime()>=onTimes.getTime()&&newEndLeaveTs.getTime()<=offTimes.getTime()){
							hour+=(double)(newEndLeaveTs.getTime() - onTimes.getTime())/(1000*60*60);
						}
						if(newEndLeaveTs.getTime()>offTimes.getTime()){
							hour += hourDay;
						}
 					}else{
						if(newStartLeaveTs.getTime()<=onTimes.getTime()){
							if(newEndLeaveTs.getTime()>=onTimes.getTime()&&newEndLeaveTs.getTime()<=offTimes.getTime()){
								hour+=(double)(newEndLeaveTs.getTime() - onTimes.getTime())/(1000*60*60);
							}
							if(newEndLeaveTs.getTime()>offTimes.getTime()){
								hour += hourDay;
							}
						}
						
						if(newStartLeaveTs.getTime()>onTimes.getTime()&&newStartLeaveTs.getTime()<=offTimes.getTime()){
							if(newEndLeaveTs.getTime()>onTimes.getTime()&&newEndLeaveTs.getTime()<=offTimes.getTime()){
								hour+=(double)(newEndLeaveTs.getTime() - newStartLeaveTs.getTime())/(1000*60*60);
							}
							
							if(newEndLeaveTs.getTime()>offTimes.getTime()){
								hour+=(double)(offTimes.getTime() - onTimes.getTime())/(1000*60*60);
							}
							
						}
				}
 					calendarStart.setTime(Timestamp.valueOf(startLeaveTimestr+" 00:00:00"));
 					calendarStart.add(Calendar.DAY_OF_YEAR, 1);
 					calendarEnd.setTime(Timestamp.valueOf(endLeaveTimestr+" 00:00:00"));
 					while (calendarStart.before(calendarEnd)) {
 						calendarStart.add(Calendar.DAY_OF_YEAR, 1);
 						int week = calendarStart.get(Calendar.WEEK_OF_YEAR);
 						if(week!=1&&week!=7){
 							hour += hourDay;
 						}
 				    }
 					
 					dayNum = hour/hourDay;//将小时换算成天数
					if(hour>hourDay){
						if(hour%hourDay>(hourDay/2)){//如果 余下 大于 半天，则按一天计算
							dayNum+=1;
						}else if(hour%hourDay>0){//按半天计算
							dayNum+=0.5;
						}
					}
 					
 					String dayNumStr = df.format(dayNum);
 					if(dayNumStr.indexOf(".")>0&&"0".equals(dayNumStr.substring(dayNumStr.indexOf(".")+1))){
 						dayNumStr = (new Double(dayNum)).intValue()+"";
 					}
 					String hourStr = df.format(hour);
 					if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
 						hourStr = (new Double(hour)).intValue()+"";
 					}
 					map.put("dayNum", dayNumStr);
 					map.put("hour", hourStr);
 					mapList.add(map);
 				}
 			}
			Gson gson = new Gson();
			String gsonStr = gson.toJson(mapList);
			ajax(gsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	public String exportMonthStatistics(){
		HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");
        OutputStream outp = null;
		try{
			Page<Map<String,Object>> pageInfo = null;
			UserInfo userInfo = this.getLoginUser();
			this.setIDisplayStart(0);
    		this.setIDisplayLength(999999);
			if(userInfo!=null){
					List<AttendancePlan> apList = planService.findAll();
					AttendancePlan ap = apList.get(0);
					if(StringUtils.isNoneBlank(searchKey)){
						searchKey = URLDecoder.decode(searchKey, "UTF-8");
					}
					pageInfo = attendanceService.monthStatistics(getPageable(), startTime, endTime, userInfo.getCompanyId(), searchKey, groupId, ap,state);
					List<Map<String,Object>> list = pageInfo.getContent();
					if(list!=null&&list.size()>0){
						List<AttendanceDays> adList = attendanceService.getAttendanceDays(startTime, endTime);//获取应出勤日期和对应周期
						/*获取此时间段 用户与请假时长 */
						Map<Integer,Double> leaveMap = attendanceService.getUserLeaveDays(startTime, endTime, userInfo.getCompanyId(), ap,1);
						/*获取此时间段 用户与工作假时长 */
						Map<Integer,Double> workLeaveMap = attendanceService.getUserLeaveDays(startTime, endTime, userInfo.getCompanyId(), ap,2);
						/*获取此时间段 用户与休息假时长 */
						Map<Integer,Double> sleepLeaveMap = attendanceService.getUserLeaveDays(startTime, endTime, userInfo.getCompanyId(), ap,3);
						/*获取此时间段 用户出勤天数 */
						Map<Integer,Double> userAttendDaysMap = attendanceService.getUserAttendDaysMap(startTime, endTime, userInfo.getCompanyId());
						int no = this.getIDisplayStart()+1;
						DecimalFormat df = new DecimalFormat("#0.0");//格式化
						Integer lateDuration = 0;
						Integer earlyDuration = 0;
						Double attendDays = 0.0;
						Double leaveHour = 0.0;
						Double workLeavehour = 0.0;
						Double sleepLeavehour = 0.0;
						for(Map<String,Object> map:list){
							map.put("no", no++);
							Integer userId = (Integer)map.get("userId");
							if(userAttendDaysMap.containsKey(userId)){
								attendDays = userAttendDaysMap.get(userId);
							}
							lateDuration = (Integer)map.get("lateDuration");
							earlyDuration = (Integer)map.get("earlyDuration");
							map.put("lateDurationNum",lateDuration);
							map.put("earlyDurationNum",earlyDuration);
							if(lateDuration > 60){
			 					map.put("lateDuration", (lateDuration/60)+"小时"+(lateDuration%60>0?(lateDuration%60+"分钟"):""));
							}else{
								map.put("lateDuration", lateDuration+"分钟");
							}
							if(earlyDuration > 60){
								map.put("earlyDuration", (earlyDuration/60)+"小时"+(earlyDuration%60>0?(earlyDuration%60+"分钟"):""));
							}else{
								map.put("earlyDuration", earlyDuration+"分钟");
							}
							Double allLackTimes = 0.0;
							Integer onDutyLackTimes = 0;
							Integer offDutyLackTimes = 0;
							Integer onTimes = (Integer)map.get("onTimes");
							Integer offTimes = (Integer)map.get("offTimes");
							if(adList!=null&&adList.size()>0){
								allLackTimes = adList.size() - attendDays;
								onDutyLackTimes = adList.size() - onTimes;
								offDutyLackTimes = adList.size() - offTimes;
							}
							
							map.put("outDays", 0);
							leaveHour = leaveMap.get(userId);
							if(leaveHour!=null){
								String hourStr = df.format(leaveHour);
			 					if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
			 						hourStr = (new Double(leaveHour)).intValue()+"";
			 					}
								map.put("leaveDays", hourStr);
							}else{
								leaveHour = 0.0;
								map.put("leaveDays","0");
							}
							
							workLeavehour = workLeaveMap.get(userId);
							if(workLeavehour!=null){
								String hourStr = df.format(workLeavehour);
			 					if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
			 						hourStr = (new Double(workLeavehour)).intValue()+"";
			 					}
								map.put("workLeaveDays", hourStr);
							}else{
								workLeavehour = 0.0;
								map.put("workLeaveDays","0");
							}
							
							sleepLeavehour = sleepLeaveMap.get(userId);
							if(sleepLeavehour!=null){
								String hourStr = df.format(sleepLeavehour);
			 					if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
			 						hourStr = (new Double(sleepLeavehour)).intValue()+"";
			 					}
								map.put("sleepLeaveDays", hourStr);
							}else{
								sleepLeavehour = 0.0;
								map.put("sleepLeaveDays","0");
							}
							
							allLackTimes = allLackTimes-sleepLeavehour-workLeavehour;
							if(allLackTimes>=0.0){
								String allLackTimesStr = df.format(allLackTimes);
								if(allLackTimesStr.indexOf(".")>0&&"0".equals(allLackTimesStr.substring(allLackTimesStr.indexOf(".")+1))){
									allLackTimesStr = (new Double(allLackTimes)).intValue()+"";
			 					}
								map.put("allLackTimes", allLackTimesStr);
							}else{
								map.put("allLackTimes", "0");
							}
							
							//attendDays = attendDays+sleepLeavehour+workLeavehour;
							if(attendDays>=0.0){
								String attendDaysStr = df.format(attendDays);
								if(attendDaysStr.indexOf(".")>0&&"0".equals(attendDaysStr.substring(attendDaysStr.indexOf(".")+1))){
									attendDaysStr = (new Double(attendDays)).intValue()+"";
			 					}
								map.put("attendDays", attendDaysStr);
							}else{
								map.put("attendDays", "0");
							}
							map.put("onDutyLackTimes", onDutyLackTimes);
							map.put("offDutyLackTimes", offDutyLackTimes);
						}
					}
			}
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String("考勤月度汇总.xls".getBytes(), "iso8859-1"));// 解决中文
			outp = response.getOutputStream();
            ExportExcel exportExcel = new ExportExcel(outp, getExportMonthHeadList(), pageInfo.getContent(), getExportMonthKeyList());
            exportExcel.exportWithSheetName("考勤月度汇总");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public String exportDayStatistics(){
		HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");
        OutputStream outp = null;
		try{
			Page<Map<String,Object>> pageInfo = null;
			UserInfo userInfo = this.getLoginUser();
			this.setIDisplayStart(0);
    		this.setIDisplayLength(999999);
			if(userInfo!=null){
				List<AttendancePlan> apList = planService.findAll();
				AttendancePlan ap = apList.get(0);
				if(StringUtils.isNoneBlank(searchKey)){
					searchKey = URLDecoder.decode(searchKey, "UTF-8");
				}
				pageInfo = attendanceService.dayStatistics(getPageable(), startTime, endTime, userInfo.getCompanyId(), searchKey, groupId, ap, state);
				List<Map<String,Object>> list = pageInfo.getContent();
				if(list!=null&&list.size()>0){
					Timestamp onTimes = ap.getCommonOn();
					/*Timestamp amOffTimes = ap.getCommonAmOff();
					Timestamp pmOnTimes = ap.getCommonPmOn();*/
					Timestamp offTimes = ap.getCommonOff();
					SimpleDateFormat sdfMMDD = new SimpleDateFormat("HH:mm");
					String onTimesHHMM = sdfMMDD.format(onTimes);
					/*String amOffTimesHHMM = sdfMMDD.format(amOffTimes);
					String pmOnTimesHHMM = sdfMMDD.format(pmOnTimes);*/
					String offTimesHHMM = sdfMMDD.format(offTimes);
					/*List<AttendanceDays> adList = attendanceService.getAttendanceDays(startTime, endTime);*/
					Map<String,Double> leaveMap = attendanceService.getDateLeaveNum(startTime, endTime, userInfo.getCompanyId(), ap,1);
					Map<String,Double> workLeaveMap = attendanceService.getDateLeaveNum(startTime, endTime, userInfo.getCompanyId(), ap,2);
					Map<String,Double> sleepLeaveMap = attendanceService.getDateLeaveNum(startTime, endTime, userInfo.getCompanyId(), ap,3);
					DecimalFormat df = new DecimalFormat("#0.0");
					int no = this.getIDisplayStart()+1;
					Timestamp tempOnTimes = null;
					/*Timestamp tempAmOffTimes = null;
					Timestamp tempPmOnTimes = null;*/
					Timestamp tempOffTimes = null;
					Integer lateTimes = 0;
					Integer earlyTimes = 0;
					Long lateDuration = 0l;
					Long earlyDuration = 0l;
					Integer onDutyLackTimes = 0;
					Integer offDutyLackTimes = 0;
					String createDate = null;
					Double hour = 0.00;
					Double attendDays = 0.0;
					Double allLackTimes = 1.0;
					for(Map<String,Object> map:list){
						map.put("no", no++);
						String onTime = (String)map.get("onTime");
						/*String amOffTime = (String)map.get("amOffTime");
						String pmOnTime = (String)map.get("pmOnTime");*/
						String offTime = (String)map.get("offTime");
						String onState = (String)map.get("onState");
						/*String amOffState = (String)map.get("amOffState");
						String pmOnState = (String)map.get("pmOnState");*/
						String offState = (String)map.get("offState");
						attendDays = 0.0;
						allLackTimes = 1.0;
						if(StringUtils.isNoneBlank(onTime)&&StringUtils.isNoneBlank(offTime)){
							attendDays = 1.0;
							allLackTimes = 0.0;
						}else if((StringUtils.isNoneBlank(onTime)||StringUtils.isNoneBlank(offTime))){
							attendDays = 1.0;
							allLackTimes = 0.0;
						}
						String week = (String)map.get("week");
						if("周六".equals(week)||"周日".equals(week)){
							map.put("attendTime", "休息");
						}else{
							//map.put("attendTime", "日常考勤"+onTimesHHMM+"-"+amOffTimesHHMM+"&nbsp;"+pmOnTimesHHMM+"-"+offTimesHHMM);
							map.put("attendTime", "日常考勤"+onTimesHHMM+"-"+offTimesHHMM);
						}
						
						lateTimes = 0;
						earlyTimes = 0;
						lateDuration = 0l;
						earlyDuration = 0l;
						onDutyLackTimes = 1;
						offDutyLackTimes = 1;
						if(StringUtils.isNoneBlank(onTime)){
							onDutyLackTimes--;
						}
						/*if(StringUtils.isNoneBlank(pmOnTime)){
							onDutyLackTimes--;
						}
						if(StringUtils.isNoneBlank(amOffTime)){
							offDutyLackTimes--;
						}*/
						if(StringUtils.isNoneBlank(offTime)){
							offDutyLackTimes--;
						}
						if("迟到".equals(onState)||"外勤-迟到".equals(onState)){
							tempOnTimes = Timestamp.valueOf("1970-01-01 "+onTime+":00.000");
							lateDuration += (tempOnTimes.getTime()-onTimes.getTime())/(1000*60);
							lateTimes ++;
						}
						/*if("早退".equals(amOffState)){
							tempAmOffTimes = Timestamp.valueOf("1970-01-01 "+amOffTime+":00.000");
							earlyDuration += (amOffTimes.getTime()-tempAmOffTimes.getTime())/(1000*60);
							earlyTimes ++;
						}
						if("迟到".equals(pmOnState)){
							tempPmOnTimes = Timestamp.valueOf("1970-01-01 "+pmOnTime+":00.000");
							lateDuration += (tempPmOnTimes.getTime()-pmOnTimes.getTime())/(1000*60);
							lateTimes ++;
						}*/
						if("早退".equals(offState)){
							tempOffTimes = Timestamp.valueOf("1970-01-01 "+offTime+":00.000");
							earlyDuration += (offTimes.getTime()-tempOffTimes.getTime())/(1000*60);
							earlyTimes ++;
						}
						map.put("lateTimes", lateTimes);
						map.put("earlyTimes", earlyTimes);
						
						if(lateDuration > 60){
		 					map.put("lateDuration", (lateDuration/60)+"小时"+(lateDuration%60>0?(lateDuration%60+"分钟"):""));
						}else{
							map.put("lateDuration", lateDuration+"分钟");
						}
						if(earlyDuration > 60){
							map.put("earlyDuration", (earlyDuration/60)+"小时"+(earlyDuration%60>0?(earlyDuration%60+"分钟"):""));
						}else{
							map.put("earlyDuration", earlyDuration+"分钟");
						}
						
						
						map.put("lateDurationNum", lateDuration);
						map.put("earlyDurationNum", earlyDuration);
						map.put("onDutyLackTimes", onDutyLackTimes);
						map.put("offDutyLackTimes", offDutyLackTimes);
						createDate = (String)map.get("createDate");
						userId = (Integer)map.get("userId");
						if(leaveMap.containsKey(createDate+"_"+userId)){
							hour = leaveMap.get(createDate+"_"+userId);
							String hourStr = df.format(hour);
		 					if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
		 						hourStr = (new Double(hour)).intValue()+"";
		 					}
							map.put("leaveDays", hourStr);
						}else{
							map.put("leaveDays","0");
							if(state!=null && state==4){
								list.remove(map);
								continue;
							}
						}
						if(sleepLeaveMap.containsKey(createDate+"_"+userId)){
							hour = sleepLeaveMap.get(createDate+"_"+userId);
							allLackTimes = allLackTimes - hour;
							String hourStr = df.format(hour);
							if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
								hourStr = (new Double(hour)).intValue()+"";
							}
							map.put("sleepLeaveDays", hourStr);
						}else{
							map.put("sleepLeaveDays","0");
						}
						
						if(workLeaveMap.containsKey(createDate+"_"+userId)){
							hour = workLeaveMap.get(createDate+"_"+userId);
							allLackTimes = allLackTimes - hour;
							String hourStr = df.format(hour);
							if(hourStr.indexOf(".")>0&&"0".equals(hourStr.substring(hourStr.indexOf(".")+1))){
								hourStr = (new Double(hour)).intValue()+"";
							}
							map.put("workLeaveDays", hourStr);
						}else{
							map.put("workLeaveDays","0");
						}
						
						if(allLackTimes>=0.0){
							String allLackTimesStr = df.format(allLackTimes);
							if(allLackTimesStr.indexOf(".")>0&&"0".equals(allLackTimesStr.substring(allLackTimesStr.indexOf(".")+1))){
								allLackTimesStr = (new Double(allLackTimes)).intValue()+"";
		 					}
							map.put("allLackTimes", allLackTimesStr);
						}else{
							map.put("allLackTimes", "0");
						}
						
						//attendDays = attendDays+sleepLeavehour+workLeavehour;
						if(attendDays>=0.0){
							String attendDaysStr = df.format(attendDays);
							if(attendDaysStr.indexOf(".")>0&&"0".equals(attendDaysStr.substring(attendDaysStr.indexOf(".")+1))){
								attendDaysStr = (new Double(attendDays)).intValue()+"";
		 					}
							map.put("attendDays", attendDaysStr);
						}else{
							map.put("attendDays", "0");
						}
						
					}
				}
			}
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String("每日考勤汇总.xls".getBytes(), "iso8859-1"));// 解决中文
			outp = response.getOutputStream();
            ExportExcel exportExcel = new ExportExcel(outp, getExportDayHeadList(), pageInfo.getContent(), getExportDayKeyList());
            exportExcel.exportWithSheetName("每日考勤汇总");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	private List<String> getExportMonthHeadList(){
        List<String> headList = new ArrayList<String>();
        headList.add("序号");
        headList.add("姓名");
        headList.add("部门");
        headList.add("出勤天数");
        headList.add("迟到次数");
        headList.add("迟到时长");
        headList.add("早退次数");
        headList.add("早退时长");
        headList.add("上班缺卡次数");
        headList.add("下班缺卡次数");
        /*headList.add("旷工天数");*/
        headList.add("请假(天)");
        headList.add("工作假(天)");
        headList.add("公休假(天)");
        return headList;
    }
    
    private List<String> getExportMonthKeyList(){
        List<String> headList = new ArrayList<String>();
        headList.add("no");
        headList.add("userName");
        headList.add("groupName");
        headList.add("attendDays");
        headList.add("lateTimes");
        headList.add("lateDuration");
        headList.add("earlyTimes");
        headList.add("earlyDuration");
        headList.add("onDutyLackTimes");
        headList.add("offDutyLackTimes");
        /*headList.add("allLackTimes");*/
        headList.add("leaveDays");
        headList.add("workLeaveDays");
        headList.add("sleepLeaveDays");
        return headList;
    }
    
    
    private List<String> getExportDayHeadList(){
        List<String> headList = new ArrayList<String>();
        headList.add("序号");
        headList.add("姓名");
        headList.add("部门");
        
        headList.add("日期");
        headList.add("考勤时间");
        
        headList.add("上班打卡时间");
        headList.add("上班打卡结果");
        headList.add("上班打卡地点");
        headList.add("上班打卡备注");
        
       /* headList.add("下班1打卡时间");
        headList.add("下班1打卡结果");
        headList.add("下班1打卡地点");
        headList.add("下班1打卡备注");
        
        headList.add("上班2打卡时间");
        headList.add("上班2打卡结果");
        headList.add("上班2打卡地点");
        headList.add("上班2打卡备注");*/
        
        headList.add("下班打卡时间");
        headList.add("下班打卡结果");
        headList.add("下班打卡地点");
        headList.add("下班打卡备注");
        
        headList.add("出勤天数");
        headList.add("迟到次数");
        headList.add("迟到时长");
        headList.add("早退次数");
        headList.add("早退时长");
        headList.add("上班缺卡次数");
        headList.add("下班缺卡次数");
        /*headList.add("旷工天数");*/
        headList.add("请假(天)");
        headList.add("工作假(天)");
        headList.add("公休假(天)");
        return headList;
    }
    
    private List<String> getExportDayKeyList(){
        List<String> headList = new ArrayList<String>();
        headList.add("no");
        headList.add("userName");
        headList.add("groupName");
        headList.add("createDate");
        headList.add("attendTime");
        
        headList.add("onTime");
        headList.add("onState");
        headList.add("onPosition");
        headList.add("onMemo");
        
        /*headList.add("amOffTime");
        headList.add("amOffState");
        headList.add("amOffPosition");
        headList.add("amOffMemo");
        
        headList.add("pmOnTime");
        headList.add("pmOnState");
        headList.add("pmOnPosition");
        headList.add("pmOnMemo");*/
        
        headList.add("offTime");
        headList.add("offState");
        headList.add("offPosition");
        headList.add("offMemo");
        
        
        
        
        headList.add("attendDays");
        headList.add("lateTimes");
        headList.add("lateDuration");
        headList.add("earlyTimes");
        headList.add("earlyDuration");
        headList.add("onDutyLackTimes");
        headList.add("offDutyLackTimes");
        /*headList.add("allLackTimes");*/
        headList.add("leaveDays");
        headList.add("workLeaveDays");
        headList.add("sleepLeaveDays");
        return headList;
    }
	
	
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Integer getAttState() {
		return attState;
	}


	public void setAttState(Integer attState) {
		this.attState = attState;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
}
