package cn.com.qytx.cbb.attendance.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.attendance.domain.Attendance;
import cn.com.qytx.cbb.attendance.domain.AttendanceDays;
import cn.com.qytx.cbb.attendance.domain.AttendancePlan;
import cn.com.qytx.cbb.attendance.domain.OAException;
import cn.com.qytx.cbb.attendance.service.AttendancePlanService;
import cn.com.qytx.cbb.attendance.service.IAttendance;
import cn.com.qytx.platform.utils.DateUtils;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

import com.google.gson.Gson;
/**
 * 
 * 功能:考勤管理 手机端
 * 版本: 1.0
 * 开发人员: CQL
 */
public class AttendanceWapAction extends BaseActionSupport{
	/**  序列号 */
    private static final long serialVersionUID = 2314184560157105391L;
    /**  日志类 */
    private static final Logger logger = Logger.getLogger(AttendanceIpSetAction.class);
    
    private static final SimpleDateFormat sdfEEEE = new SimpleDateFormat("EEEE");
    private static final SimpleDateFormat sip = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfYYYYMM = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat sdfHHMMSS = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat sdfHHMM = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat sdfYYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	//用户信息
	@Resource(name="userService")
	IUser userService;
	//打卡信息接口
	@Resource(name="attendanceService")
	IAttendance attendanceService;
	
	@Autowired
	AttendancePlanService attendancePlanService;
	
	@Autowired
	IGroup groupService;
	
	
    private Integer userId;
	//最后打卡时间
	private String lastTime;
 
  private String position;// 位置
  private String longitude; //经度
  private String latitude; //纬度
  private Integer source;//来源 1:PC 2：手机
  
  
  private String month;//年月
  
  private Integer state;//1.旷工 2.缺卡 3.迟到 4.早退 5外勤
  
  private Integer leaveType;//1 请假 2 外勤（工作假）
  
  
  
  public final static int SOURCE_PC = 1;
  public final static int SOURCE_MOBILE = 2;   
       
    private  Integer type;//0 没打记录 1.有打卡记录
     
      /**
       * 请求的指定天的打卡记录
       */
    private String date;
    
    
    private Integer today;//1 当天
    
	
    /**
     * 查询某个人月考勤
     */
    public void findAttendanceByUserId(){
    	try {
    		HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("ContentType", "text/json");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");   
            PrintWriter writer = new PrintWriter(response.getWriter());
			if(userId!=null){
				UserInfo user= userService.findOne(userId);
				String day = sip.format(new Date());
				if(user!=null){
					GroupInfo group =groupService.findOne(user.getGroupId());
					Integer attendanceDayNum =0; //出勤天数
					Integer restNum=0;
					Integer lateNum=0;//迟到数
					Integer missingCardNum=0;
					Integer absenteeismNum=0;
					Integer leaveEarlyNum=0;//早退数
					Integer amOnNum=0;//上午上班打卡次数
					Integer pmOffNum=0;//下午下班打卡次数
					
					
					List<AttendancePlan> attendancePlanlist = attendancePlanService.findAll();
					AttendancePlan attendancePlan =attendancePlanlist.get(0);
					//出勤天数
					List<String> recordDaysTime = new ArrayList<String>();
					List<String> restTimeList =new ArrayList<String>();
					List<String> leaveEarlyTime = new ArrayList<String>(); //早退时间
					List<String> lateTimeList = new ArrayList<String>(); //迟到时间
					List<String> absenteeismTimeList= new ArrayList<String>();//旷工时间
					List<String> missingCardTimeList = new ArrayList<String>();//缺卡时间
					List<Object[]> listObj= attendanceService.findAttendanceByUserId(userId.toString(), month, user.getCompanyId());
					Object[] obj=null;
					if(listObj!=null && listObj.size()>0){
						obj=listObj.get(0);
					}
					String startTime = month+"-01 00:00:00";
					String endTime = getEndTime(month);
					 String startTimeYYYYMMDD=month+"-01";
					  String endTimeYYYYMMDD=endTime.substring(0, endTime.length()-9);
					//请假
					List<String> getLeaveTime = new ArrayList<String>();
					//公休假
					List<String> getRestTime = new ArrayList<String>();
					//外勤
					//List<WorkflowLeave> workflowGoOutList= WorkflowLeaveService.getWorkflowLeaveList(startTimeYYYYMMDD, endTimeYYYYMMDD, user.getCompanyId(), userId,2);
					//List<String> getGoOutTime = leaveTime(workflowGoOutList);
					
					List<Map<String,Object>> leaveMapList = null;//请假流程
					List<AttendanceDays> restDaysList= attendanceService.getAttendanceWeekDays(startTime,endTime);//周六周日
				    restNum = restDaysList.size()+getRestTime.size();//休息天数+公休
					for(AttendanceDays ad:restDaysList){
						String week=sdfEEEE.format(ad.getDay());
						restTimeList.add(sip.format(ad.getDay())+"("+week+")");
					}
					for(String s:getRestTime){//公休
						restTimeList.add(s+"("+sdfEEEE.format(Timestamp.valueOf(s))+")");
					}
					Map<String,Object> map =new HashMap<String, Object>();
					if(obj!=null){
						//出勤天数
						attendanceDayNum = obj[0]==null?0:Integer.valueOf(obj[0].toString());
						//迟到天数
						lateNum = obj[1]==null?0:Integer.valueOf(obj[1].toString());
						//早退
						leaveEarlyNum=obj[2]==null?0:Integer.valueOf(obj[2].toString());
						amOnNum=obj[3]==null?0:Integer.valueOf(obj[3].toString());
						pmOffNum=obj[6]==null?0:Integer.valueOf(obj[6].toString());
					}
						//获得出勤map
						Map<String,String> mapAttendance= attendanceService.getMapByTime(userId,startTime, endTime);
						
						
						List<AttendanceDays> list= attendanceService.getAttendanceDays(startTime, endTime);//不包含周六周日
						Integer sumAttendanceDayNum =0;//本月应考勤天数
						
						
						List<Object[]> missingCardList = attendanceService.findMissingCardList(userId, startTime, endTime, user.getCompanyId());
						Map<String,Object> mapPunchtheClock = findMap(missingCardList);//已打卡Map
						Timestamp amOn =  Timestamp.valueOf(day+" "+sdfHHMMSS.format(attendancePlan.getCommonOn()));
						Timestamp pmOff =  Timestamp.valueOf(day+" "+sdfHHMMSS.format(attendancePlan.getCommonOff()));
						if(list!=null && list.size()>0){
							sumAttendanceDayNum= list.size();
							missingCardNum=sumAttendanceDayNum*2-(amOnNum+pmOffNum);//缺卡次数
							absenteeismNum=sumAttendanceDayNum-attendanceDayNum;//旷工天数
							Long ThisLongTime=new Date().getTime();
							String week="";
							for(AttendanceDays a:list){
								if(sip.format(a.getDay()).equals(day)){//当前月的当天除去
									week=sdfEEEE.format(list.get(list.size()-1).getDay());
									if(amOn.getTime()<=ThisLongTime && ThisLongTime<pmOff.getTime()){
										missingCardNum=missingCardNum-1;
									}else if(ThisLongTime<amOn.getTime()){
										missingCardNum = missingCardNum-2;
									}
								}else{
									if(mapAttendance.get(sip.format(a.getDay()))==null){//旷工
										week=sdfEEEE.format(a.getDay());
										if(!getLeaveTime.contains(sip.format(a.getDay()))&&!getRestTime.contains(sip.format(a.getDay()))){
											absenteeismTimeList.add(sip.format(a.getDay())+"("+week+")");
										}
										//全天缺卡
										missingCardTimeList.add(sip.format(a.getDay())+"("+week+")"+sdfHHMM.format(attendancePlan.getCommonOn()));
										missingCardTimeList.add(sip.format(a.getDay())+"("+week+")"+sdfHHMM.format(attendancePlan.getCommonOff()));
									}else{
										//absenteeismNum=absenteeismNum-1;
										if(mapPunchtheClock.get(sip.format(a.getDay()))!=null){
											String missingCardStr="";
											String weekStr="";
											String hour="";
											Object[] o =(Object[]) mapPunchtheClock.get(sip.format(a.getDay())); 
											if(Integer.valueOf(o[0].toString())==0){
												weekStr=getWeekStr(o[4].toString());
												hour=sdfHHMM.format(attendancePlan.getCommonOn());
												missingCardStr= o[4].toString()+"("+weekStr+")"+hour;
												missingCardTimeList.add(missingCardStr);
											}
											if(Integer.valueOf(o[3].toString())==0){
												weekStr=getWeekStr(o[4].toString());
												hour=sdfHHMM.format(attendancePlan.getCommonOff());
												missingCardStr= o[4].toString()+"("+weekStr+")"+hour;
												missingCardTimeList.add(missingCardStr);
											}
										}
									}
								}
							}
						}
						//获得打卡天数
						List<String> recordDays = attendanceService.getRecordDaysTime(userId,startTime, endTime);
						if(recordDays!=null && recordDays.size()>0){
							for(String r : recordDays){
								recordDaysTime.add(r+"("+getWeekStr(r)+")");//出勤天数
							}
						}
						List<Attendance> attendanceList = attendanceService.getAttendanceRecodes(userId,startTime,endTime,null,"desc");
						
						
						if(attendanceList!=null && attendanceList.size()>0){
							for(Attendance a:attendanceList){
								if(a.getAttState()==2){
									String time =sip.format(a.getCreateTime());
									String weekStr= sdfEEEE.format(a.getCreateTime());
									String hour = sdfHHMM.format(a.getCreateTime());
									lateTimeList.add(time+"("+weekStr+")"+hour);
								}
								if(a.getAttState()==3){//早退
									String time =sip.format(a.getCreateTime());
									String weekStr= sdfEEEE.format(a.getCreateTime());
									String hour = sdfHHMM.format(a.getCreateTime());
									leaveEarlyTime.add(time+"("+weekStr+")"+hour);
								}
								
							}
						}
                       
					
					map.put("name", user.getUserName());
                    map.put("groupName",group.getGroupName());
					map.put("attendanceDayNum",attendanceDayNum );
					map.put("restNum",restNum );
					map.put("lateNum",lateNum );
					map.put("leaveEarlyNum",leaveEarlyNum );
					map.put("missingCardNum",missingCardNum );
					map.put("businessTravel",0 );//出差
					map.put("absenteeismNum",absenteeismNum );//旷工
					
					map.put("recordDaysTime",recordDaysTime );//出勤天数
					map.put("restTimeList",restTimeList );//休时间
					map.put("lateTimeList",lateTimeList );//迟到时间
					map.put("leaveEarlyTimeNum",leaveEarlyTime );//早退时间
					map.put("missingCardTimeList",missingCardTimeList );//缺卡时间
					map.put("absenteeismTimeList",absenteeismTimeList );//旷工时间
					
					map.put("leaveMapList",leaveMapList);//请假
					
					
					Gson gson = new Gson();
					String json = gson.toJson(map);
		            writer.print("100||"+json);
		            writer.flush();
		            writer.close();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private static String getWeekStr(String timeStr){
    	Timestamp timeTs = Timestamp.valueOf(timeStr+" 00:00:00.000");
    	return sdfEEEE.format(timeTs);
    }
    
    /**
     * 封装已打卡数据
     * @return
     */
    private static Map<String,Object> findMap(List<Object[]> missingCardList){
    	Map<String,Object> map= new HashMap<String, Object>();
    	if(missingCardList!=null && missingCardList.size()>0){
    		for(Object[] obj :missingCardList){
    			map.put(obj[4].toString(), obj);
    		}
    	}
		return map;
    	
    }
    
    
    
    
    /**
     * 获得一个月的天数
     * @return
     */
   private static Integer findMonthDay(Integer month){
	   Integer dayNum=30;
	   Calendar c = Calendar.getInstance();  
       c.set(Calendar.MONTH, month-1); // 6 月  
       dayNum=c.getActualMaximum(Calendar.DAY_OF_MONTH);
       return dayNum;
   } 
  /**
   * 统计所有人月考勤
   */
  public  void findALLMonthAttendance(){
	  try {
		  HttpServletResponse response = ServletActionContext.getResponse();
	      response.setHeader("ContentType", "text/json");
	      response.setCharacterEncoding("utf-8");
	      response.setContentType("text/html;charset=utf-8");   
	      PrintWriter writer = new PrintWriter(response.getWriter());
	      if(userId!=null){
	    	  Map<String,Object> map= new HashMap<String, Object>();
	    	  UserInfo user =userService.findOne(userId);
	    	  String day = sip.format(new Date());
	    	  Integer totalNum=0;//考勤人数
	    	  Integer lateNum=0;//迟到的人数
	    	  Integer leaveEarlyNum=0;//早退的人数
	    	  Integer outSideNum=0;//外勤
	    	  Integer businessTravelNum=0;//出差人数
	    	  Integer missinCardNum=0;//缺卡人数
	    	  Integer absenteeismNum=0;//旷工人数
	    	  Integer leaveNum=0;//请假人数
	    	  List<String> lateName=new ArrayList<String>();//迟到的人员
	    	  List<String> leaveEarly = new ArrayList<String>();//早退的人员
	    	  List<String> outSide = new ArrayList<String>();//外勤的人员
	    	  List<String> leave = new ArrayList<String>();//请假人员
	    	  List<String> allPeopleList= new ArrayList<String>();//所有的人员
	    	  
	    	  if(user!=null){
	    		  List<AttendancePlan> attendancePlanlist = attendancePlanService.findAll();
				  AttendancePlan attendancePlan =attendancePlanlist.get(0); 
				  String userIds=attendancePlan.getUserIds();
				  if(StringUtils.isNotBlank(userIds)){
					  if(userIds.startsWith(",")){
						  userIds= userIds.substring(1,userIds.length());
					  }
					  if(userIds.endsWith(",")){
						  userIds= userIds.substring(0,userIds.length()-1);
					  }
					  totalNum=userIds.split(",").length;
					  allPeopleList=Arrays.asList(userIds.split(","));
				  }
				  String startTimeYYYYMMDD=month+"-01";
				  String startTime = month+"-01 00:00:00";
				  String endTime = getEndTime(month);
				  String endTimeYYYYMMDD=endTime.substring(0, endTime.length()-9);
                  Map<Integer,String> userInfoMap =userMap(userIds);
                  List<Attendance> attendanceList = attendanceService.getAttendanceRecodes(null, startTime, endTime, null, null);
				  if(attendanceList!=null && attendanceList.size()>0){
					  for(Attendance a:attendanceList){
						  String userName=userInfoMap.get(a.getCreateUserId());
							  if(StringUtils.isNoneBlank(userName)){
								  if(a.getAttState()==2 && !lateName.contains(userName)){//迟到的人员
									  lateName.add(userName);
									  lateNum=lateNum+1;
								  }
								  if(a.getAttState()==3 && !leaveEarly.contains(userName)){//早退的人员
									  leaveEarlyNum=leaveEarlyNum+1;
									  leaveEarly.add(userName);
								  }
								 if(a.getOutOfRange()==1 && !outSide.contains(userName)){//外勤的人员
									  outSideNum=outSideNum+1;
									  outSide.add(userName);
								  }
							  }
						  }
					  }
				  
				 leaveNum = attendanceService.leaveUserNum(startTimeYYYYMMDD, endTimeYYYYMMDD, user.getCompanyId(), attendancePlan,1);//请假
				 //outSideNum = attendanceService.leaveUserNum(startTimeYYYYMMDD, endTimeYYYYMMDD, user.getCompanyId(), attendancePlan,2);//外勤
				  Integer sumClockNum=0;//一共打卡次数
				  Integer sumDay=0;//应出勤天数
				  List<AttendanceDays> listAttendanceDays= attendanceService.getAttendanceDays(startTime, endTime);//不包含周六周日
				  if(listAttendanceDays!=null && listAttendanceDays.size()>0){
					  sumDay=listAttendanceDays.size();
				  }
				  List<Object[]> objList= attendanceService.findListClockNum(userIds, month, user.getCompanyId());//获得打卡人员打卡和次数
				  Map<String,String> mapClockMap=new HashMap<String, String>();//每个人每个月打卡次数、  
				  Map<String,String> mapClockDayMap=new HashMap<String, String>();//每个人每个月打天数、  
				  sumClockNum=sumDay*2;//总打卡次数
				  Timestamp amOn =  Timestamp.valueOf(day+" "+sdfHHMMSS.format(attendancePlan.getCommonOn()));
				  Timestamp pmOff =  Timestamp.valueOf(day+" "+sdfHHMMSS.format(attendancePlan.getCommonOff()));
				  Long ThisLongTime=new Date().getTime();
				  if(month.equals(sdfYYYYMM.format(new Date()))){//如果当月今天
					  if(amOn.getTime()<=ThisLongTime && ThisLongTime<pmOff.getTime()){
						  sumClockNum=sumClockNum-1;
					  }else if(ThisLongTime<amOn.getTime()){
						  sumClockNum=sumClockNum-2;
					  }
				  }
				  if(objList!=null && objList.size()>0){
					  for(Object[] o:objList){
						  mapClockMap.put(o[1].toString(),o[0].toString());
						  mapClockDayMap.put(o[1].toString(),o[2].toString());
					  }
				  }
				 
				  for(String s:allPeopleList){
					  if(mapClockMap.get(s)!=null){//缺卡
						  if(Integer.valueOf(mapClockMap.get(s))!=sumClockNum){
							  missinCardNum=missinCardNum+1;
						  }
					  }else{//一个月 一次卡都没打
						  missinCardNum=missinCardNum+1;
					  }
					  if(mapClockDayMap.get(s)!=null){//旷工
						  if(Integer.valueOf(mapClockDayMap.get(s))!=sumDay){
							  absenteeismNum=absenteeismNum+1;
						  }
					  }else{
						  absenteeismNum=absenteeismNum+1;
					  }
					  
					  
				  }
	      }  
	    	  
	    	  map.put("totalNum",totalNum );//考勤人数
	    	  map.put("lateNum",lateNum );//迟到人数
	    	  map.put("leaveEarlyNum",leaveEarlyNum );//早退人数
	    	  map.put("missinCardNum",missinCardNum );//缺卡人数
	    	  map.put("absenteeismNum",absenteeismNum );//旷工人数
	    	  map.put("outSideNum", outSideNum);//外勤人数
	    	  map.put("businessTravelNum",businessTravelNum );//出差人数
	    	  map.put("leaveNum",leaveNum );//请假人数
	    	  Gson gson = new Gson();
			  String json = gson.toJson(map);
	          writer.print("100||"+json);
	          writer.flush();
	          writer.close(); 
	  }	  
	} catch (Exception e) {
		e.printStackTrace();
	}
	  
	  
  }
    /**
     * 获得考勤人员集合
     * @param userIds
     * @return
     */
    private Map<Integer,String> userMap(String userIds){
    	 List<UserInfo> userlist=userService.findUsersByIds(userIds);
    	 Map<Integer,String> map=new HashMap<Integer, String>();
    	 if(userlist!=null && userlist.size()>0){
    		 for(UserInfo u:userlist){
    			 map.put(u.getUserId(),u.getUserName());
    		 }
    	 }
		return map;
    }
    
    
    
 /**
  * 获得考勤人员部门Map 集合
  * @return
  */
    private Map<Integer,String> getGroupName( Integer companyId,String userIds){
    	if(userIds.startsWith(",")){
    		userIds=userIds.substring(1,userIds.length());
    	}
    	if(userIds.endsWith(",")){
    		 userIds= userIds.substring(0,userIds.length()-1);
    	}
    	List<Object[]> list=attendanceService.findUserIdAndGroupName(userIds,companyId);
    	 Map<Integer,String> map=new HashMap<Integer, String>();
    	 if(list!=null && list.size()>0){
    		 for(Object[] o:list){
    			 map.put(Integer.valueOf(o[0].toString()),o[1].toString());
    		 }
    	 }
    	return map;
    	
    }
    
    
    
    
    /**
     * 获得请假 或 外勤人员列表
     */
    public void findPageLeaveList(){
    	try {
    		 HttpServletResponse response = ServletActionContext.getResponse();
   	         response.setHeader("ContentType", "text/json");
   	         response.setCharacterEncoding("utf-8");
   	         response.setContentType("text/html;charset=utf-8");   
   	         PrintWriter writer = new PrintWriter(response.getWriter());
    		 if(userId!=null){
    			 UserInfo user = userService.findOne(userId);
    			 if(user!=null){
    				 String day = sip.format(new Date());
    				 String endTime=getEndTime(month);
   				     endTime=endTime.substring(0, endTime.length()-9);
   				     String startTime = month+"-01";
    				 if(today==1){
	   					startTime=day;
	   					endTime=day;
	   				  }
    			      List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
    			      List<AttendancePlan> attendancePlanlist = attendancePlanService.findAll();
    				  AttendancePlan attendancePlan =attendancePlanlist.get(0); 
    				  String userIds=attendancePlan.getUserIds();
    			      Map<Integer,String> userMap = userMap(userIds);
    			      Map<Integer,String> getGroupName = getGroupName(user.getCompanyId(),userIds);
    			      List<Integer> userList= new ArrayList<Integer>();
    			      Gson gson = new Gson();
    				  String json = gson.toJson(listMap);
    		          writer.print("100||"+json);
    		          writer.flush();
    		          writer.close(); 
    			 }
    		 }
    		
    		  
		} catch (Exception e) {
			
		}
    }
    
    /**
     * 查询旷工
     */
   public void findAbsenteeisList(){
    try {
	     HttpServletResponse response = ServletActionContext.getResponse();
         response.setHeader("ContentType", "text/json");
         response.setCharacterEncoding("utf-8");
         response.setContentType("text/html;charset=utf-8");   
         PrintWriter writer = new PrintWriter(response.getWriter());
   		 if(userId!=null){
   			 UserInfo user = userService.findOne(userId);
   			 if(user!=null){
   				  String day = sip.format(new Date());
   				  String endTime=getEndTime(month);
 				  String startTime = month+"-01 00:00:00";
   				  if(today==1){
   					startTime =day+" 00:00:00";
   					endTime=day+" 23:59:59";
   				  }
   				  
   				  List<String> allPeopleList= new ArrayList<String>();//所有需要考勤的人员
   				  List<Map<String,Object>> listMap= new ArrayList<Map<String,Object>>();//旷工人员集合
   			      List<AttendancePlan> attendancePlanlist = attendancePlanService.findAll();
   				  AttendancePlan attendancePlan =attendancePlanlist.get(0); 
   				  Integer sumClockNum=0;//一共打卡次数
				  Integer sumDay=0;//应出勤天数
				  List<AttendanceDays> listAttendanceDays= attendanceService.getAttendanceDays(startTime, endTime);//不包含周六周日
				  if(listAttendanceDays!=null && listAttendanceDays.size()>0){
					  sumDay=listAttendanceDays.size();
				  }
				  sumClockNum=sumDay*2;
				  Timestamp amOn =  Timestamp.valueOf(day+" "+sdfHHMMSS.format(attendancePlan.getCommonOn()));
				  //Timestamp amOff =  Timestamp.valueOf(day+" "+sdfHHMMSS.format(attendancePlan.getCommonAmOff()));
				  //Timestamp pmOn =  Timestamp.valueOf(day+" "+sdfHHMMSS.format(attendancePlan.getCommonPmOn()));
				  Timestamp pmOff =  Timestamp.valueOf(day+" "+sdfHHMMSS.format(attendancePlan.getCommonOff()));
				  Long ThisLongTime=new Date().getTime();
				  if(month.equals(sdfYYYYMM.format(new Date()))){//如果当月今天
					  if(amOn.getTime()<=ThisLongTime && ThisLongTime<pmOff.getTime()){
						  sumClockNum=sumClockNum-1;
					  }
				  }
   				  String userIds=attendancePlan.getUserIds();
   				  if(StringUtils.isNotBlank(userIds)){
					  if(userIds.startsWith(",")){
						  userIds= userIds.substring(1,userIds.length());
					  }
					  if(userIds.endsWith(",")){
						  userIds= userIds.substring(0,userIds.length()-1);
					  }
					  allPeopleList=Arrays.asList(userIds.split(","));
				  }

   			     Map<Integer,String> userMap = userMap(userIds);
   			     Map<Integer,String> getGroupName = getGroupName(user.getCompanyId(),userIds);
   				 List<Object[]> objList= attendanceService.findListClockNum(userIds, day, user.getCompanyId());//获得打卡人员打卡和次数
   				 Map<String,String> mapClockMap=new HashMap<String, String>(); 
   				Map<String,String> mapClockDayMap=new HashMap<String, String>();//每个人每个月打天数、
   				 if(objList!=null && objList.size()>0){
					  for(Object[] o:objList){
						  mapClockMap.put(o[1].toString(),o[0].toString());
						  mapClockDayMap.put(o[1].toString(),o[2].toString());
					  }
				 } 
   				 //请假   
   				List<Integer> leaveList= findLeaveUserId(startTime.substring(0, 10),endTime.substring(0, 10),user.getCompanyId() ,1);
   				//外勤
   				//List<Integer> goOutList= findLeaveUserId(startTime.substring(0, 10),endTime.substring(0, 10),user.getCompanyId() ,2);
   				//公休假
   				List<Integer> restList= findLeaveUserId(startTime.substring(0, 10),endTime.substring(0, 10),user.getCompanyId() ,3);
   				for(String s:allPeopleList){
	   				 Map<String,Object> map =new HashMap<String, Object>();
	   				 if(state==1){
	   					 if(mapClockDayMap.get(s)!=null){//没有打卡记录（旷工）
	   						 if(Integer.valueOf(mapClockDayMap.get(s))!=sumDay){
								  if(StringUtils.isNoneBlank(userMap.get(Integer.valueOf(s)))){
									  map.put("userId",Integer.valueOf(s));
									  map.put("userName",userMap.get(Integer.valueOf(s)));
									  map.put("groupName",getGroupName.get(Integer.valueOf(s)));
									  listMap.add(map);
								  }
	   						 }
						  }else{
							  if(StringUtils.isNoneBlank(userMap.get(Integer.valueOf(s)))){
								  map.put("userId",Integer.valueOf(s));
								  map.put("userName",userMap.get(Integer.valueOf(s)));
								  map.put("groupName",getGroupName.get(Integer.valueOf(s)));
								  listMap.add(map);
							  }
						  }
	   				 }
					  if(state==2){//缺卡
						  if(mapClockDayMap.get(s)!=null){
							  if(Integer.valueOf(mapClockMap.get(s))!=sumClockNum){
								  if(StringUtils.isNoneBlank(userMap.get(Integer.valueOf(s)))){
									  map.put("userId",Integer.valueOf(s));
									  map.put("userName",userMap.get(Integer.valueOf(s)));
									  map.put("groupName",getGroupName.get(Integer.valueOf(s)));
									  listMap.add(map);
								  }
							  } 
						  }else{
							  if(StringUtils.isNoneBlank(userMap.get(Integer.valueOf(s)))){
								  map.put("userId",Integer.valueOf(s));
								  map.put("userName",userMap.get(Integer.valueOf(s)));
								  map.put("groupName",getGroupName.get(Integer.valueOf(s)));
								  listMap.add(map);
							  }
						  }
						  
					  }
				 }
   			      Gson gson = new Gson();
   				  String json = gson.toJson(listMap);
   		          writer.print("100||"+json);
   		          writer.flush();
   		          writer.close(); 
   			 }
   		 }
   		  
	} catch (Exception e) {
		
	}
}
    /**
     * 
     * @return
     */
   public List<Integer> findLeaveUserId(String startTime,String endTime,Integer companyId, Integer type){
	   List<Integer> leaveList= new ArrayList<Integer>();
	   return leaveList;
   }
   
   
   
    
    /**
     * 通过状态查询考勤信息
     *  state;//1.旷工 2.缺卡 3.迟到 4.早退//外勤
     */
 public void findAttendanceListByState(){
	try {
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("ContentType", "text/json");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");   
        PrintWriter writer = new PrintWriter(response.getWriter());
        List<Map<String,Object>> listMap= new ArrayList<Map<String,Object>>(); 
        if(userId!=null){
        	UserInfo user =  userService.findOne(userId);
        	if(user!=null){
        		String day = sip.format(new Date());
			    String endTime=getEndTime(month);
			    String startTime = month+"-01 00:00:00";
			    if(today==1){
   					startTime =day+" 00:00:00";
   					endTime=day+" 23:59:59";
   				  }
			    List<AttendancePlan> attendancePlanlist = attendancePlanService.findAll();
 			    AttendancePlan attendancePlan =attendancePlanlist.get(0); 
			    Map<Integer,String> userMap = userMap(attendancePlan.getUserIds());
  			    Map<Integer,String> getGroupName = getGroupName(user.getCompanyId(),attendancePlan.getUserIds());
        		List<Attendance> attendanceList = attendanceService.getAttendanceRecodes(null, startTime, endTime, null, null);
        		List<Integer> userIdList1= new ArrayList<Integer>();
        		List<Integer> userIdList2= new ArrayList<Integer>();
        		List<Integer> userIdList3= new ArrayList<Integer>();
        		if(attendanceList!=null && attendanceList.size()>0){
        			for(Attendance a:attendanceList){
        				Map<String,Object> map =new HashMap<String, Object>();
        				if(state==3){//迟到
        					if(a.getAttState()==2){
        						if(!userIdList1.contains(a.getCreateUserId())){
        							if(StringUtils.isNoneBlank(userMap.get(a.getCreateUserId()))||StringUtils.isNoneBlank(getGroupName.get(a.getCreateUserId()))){
        								 map.put("userId",a.getCreateUserId());
       								     map.put("userName",userMap.get(a.getCreateUserId()));
       								     map.put("groupName",getGroupName.get(a.getCreateUserId()));
       								     userIdList1.add(a.getCreateUserId());
       								     listMap.add(map);
        							}
        						}
        						 
        					}
        				}
        				if(state==4){//早退
        					if(a.getAttState()==3){
        						if(!userIdList2.contains(a.getCreateUserId())){
        							if(StringUtils.isNoneBlank(userMap.get(a.getCreateUserId()))||StringUtils.isNoneBlank(getGroupName.get(a.getCreateUserId()))){
       								 map.put("userId",a.getCreateUserId());
      								     map.put("userName",userMap.get(a.getCreateUserId()));
      								     map.put("groupName",getGroupName.get(a.getCreateUserId()));
      								     userIdList2.add(a.getCreateUserId());
      								     listMap.add(map);
       							     }
       						    }
        					}
        				}
        				if(state==5){//外勤
        					if(a.getOutOfRange()==1){
        						if(!userIdList3.contains(a.getCreateUserId())){
        							if(StringUtils.isNoneBlank(userMap.get(a.getCreateUserId()))||StringUtils.isNoneBlank(getGroupName.get(a.getCreateUserId()))){
          								 map.put("userId",a.getCreateUserId());
     								     map.put("userName",userMap.get(a.getCreateUserId()));
     								     map.put("groupName",getGroupName.get(a.getCreateUserId()));
     								     userIdList3.add(a.getCreateUserId());
     								     listMap.add(map);
          							  }
       						     }
        					}
        				}
        			}
        		}
        		  Gson gson = new Gson();
 				  String json = gson.toJson(listMap);
 		          writer.print("100||"+json);
 		          writer.flush();
 		          writer.close(); 
        	}
        }
		
	} catch (Exception e) {
		e.printStackTrace();
	}
 }
   
   
   
  
    
    
    
    
    
    
    /**
     * 获得结束日期
     * @return
     */
    private static String getEndTime(String month){
    	  String day = sip.format(new Date());
		  String ThisDay=day.substring(5,day.length()-3 );
		  Integer thisMonth=1;
		  if("0".endsWith(ThisDay.substring(0, ThisDay.length()-1))){
			thisMonth=Integer.valueOf(ThisDay.substring(1, ThisDay.length()));
		  }else{
			thisMonth=Integer.valueOf(ThisDay);
		  }
		  String monthStr=month.substring(5, month.length());
		  Integer monthNum=1;//月份
		  if("0".endsWith(monthStr.substring(0, monthStr.length()-1))){
			monthNum=Integer.valueOf(monthStr.substring(1, monthStr.length()));
		  }else{
			monthNum=Integer.valueOf(monthStr);
		  }
		  Integer sumMonthNum=findMonthDay(monthNum);//一个月总天数
		  String endTime = month+"-"+sumMonthNum+" 23:59:59";
          if(monthNum>=thisMonth){
        	endTime=day +" 23:59:59";
		  }
          return endTime;
    }
    
    
    
    
    
    
    
    
    /**
     * 获得当天考勤记录数
     * @return
     */
    public String findAttendanceNum(){
    	try {
    		HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("ContentType", "text/json");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");   
            PrintWriter writer = new PrintWriter(response.getWriter());
    		if(userId!=null){
    			UserInfo user= userService.findOne(userId);
    			Map<String,Object> map =new HashMap<String, Object>();
    			if(user!=null){
					String day = sip.format(new Date());
    				Integer totalNum=0;//应打卡人数
    				Integer realPeopleNum = 0;//实际打卡人数
    				Integer notRealPeopleNum =0;//未打卡人数
    				Integer lateNum=0;//迟到人数
    				Integer goOutNum=0;//外出人数
    				Integer leaveNum=0;//请假人数
    				Integer restPublicNum=0;//公休人数
    			    Calendar c=Calendar.getInstance();
    			    c.setTime(new Date());
    			    int weekday=c.get(Calendar.DAY_OF_WEEK);
    				if(weekday!=1 ||weekday!=7){
        				List<AttendancePlan> list = attendancePlanService.findAll();
        				AttendancePlan attendancePlan=new AttendancePlan();
        				String userIds="";
        				if(list!=null && list.size()>0){
        					attendancePlan = list.get(0);
        					userIds=attendancePlan.getUserIds();
        					if(StringUtils.isNoneBlank(userIds)){
                                if(userIds.startsWith(",")){
                                	userIds=userIds.substring(1, userIds.length());
        						}
        						if(userIds.endsWith(",")){
        							userIds = userIds.substring(0, userIds.length()-1);
        						}
        						totalNum = userIds.split(",").length;
        					}
        				}
        				leaveNum = attendanceService.leaveUserNum(day, day, user.getCompanyId(), attendancePlan,1);//请假
//        				goOutNum = attendanceService.leaveUserNum(day, day, user.getCompanyId(), attendancePlan,2);//外勤
        				restPublicNum = attendanceService.leaveUserNum(day, day, user.getCompanyId(), attendancePlan,3);//外勤
        				String beginTime=day+" 00:00:00";
        				String  endTime=day+" 23:59:59";
        				List<Object[]> attendanceList =attendanceService.findPeopleByState(userIds,beginTime, endTime,user.getCompanyId());
        				 if(attendanceList!=null && attendanceList.size()>0){
        					realPeopleNum = attendanceList.size();
        					for(Object[] obj:attendanceList){
        						if(Integer.valueOf(obj[0].toString())!=0){
        							lateNum+=1;//迟到
        						}
        						if(Integer.valueOf(obj[2].toString())!=0){
        							goOutNum+=1;//外勤
        						}
        					}
        				}
        				notRealPeopleNum = totalNum-realPeopleNum-leaveNum-restPublicNum;
    				}
    				
    				map.put("totalNum",totalNum );
    				map.put("realPeopleNum",realPeopleNum );
    				map.put("notRealPeopleNum",notRealPeopleNum );
    				map.put("lateNum",lateNum );
    				map.put("goOutNum",goOutNum );
    				map.put("leaveNum", leaveNum);
    				Gson gson = new Gson();
    	            String json = gson.toJson(map);
    	            writer.print("100||"+json);
    	            writer.flush();
    	            writer.close();
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    /**
     * 打卡记录
     */
    public void findPunchTheClockByType(){
    	try {
    		HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("ContentType", "text/json");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");   
            PrintWriter writer = new PrintWriter(response.getWriter());
            List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			if(userId!=null){
				UserInfo user = userService.findOne(userId);
				if(user!=null){
					String day = sip.format(new Date());
					List<AttendancePlan> list = attendancePlanService.findAll();
					String userIds=null;
					if(list!=null && list.size()>0){
						AttendancePlan attendancePlan = list.get(0);
						userIds = attendancePlan.getUserIds();
						if(userIds.startsWith(",")){
                        	userIds=userIds.substring(1, userIds.length());
						}
						if(userIds.endsWith(",")){
							userIds = userIds.substring(0, userIds.length()-1);
						}
						Map<Integer,Integer> mapCount=attendanceService.tjAttendanceCount(userIds, day, day, user.getCompanyId());
						String [] userArray = userIds.split(",");
						for(int i=0;i<userArray.length;i++){
							Map<String,Object> map =new HashMap<String, Object>();
							UserInfo userOne = userService.findOne(Integer.valueOf(userArray[i]));
							GroupInfo group = groupService.findOne(userOne.getGroupId());
							
							Integer count=mapCount.get(Integer.valueOf(userArray[i]));
							if(count==null){
								map.put("userId", userOne.getUserId());
								map.put("name",userOne.getUserName());
								map.put("groupName",group.getGroupName()); 
								map.put("operation", "缺卡");
								map.put("state", 3);
							}else{
								if(count==2){
									map.put("userId", userOne.getUserId());
									map.put("name",userOne.getUserName());
									map.put("groupName",group.getGroupName());
									map.put("operation", "正常");
									map.put("state",1);
								}else{
									map.put("userId", userOne.getUserId());
									map.put("name",userOne.getUserName());
									map.put("groupName",group.getGroupName());
									map.put("operation", "缺卡");
									map.put("state", 2);
								}
							}
							listMap.add(map);
						}
							
					}
				}
				Gson gson = new Gson();
				String json = gson.toJson(listMap);
	            writer.print("100||"+json);
	            writer.flush();
	            writer.close();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    } 
    
    
     /**
      * 查询个人考勤详情 
      */
    public void findUserDetail(){
    	try {
    		HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("ContentType", "text/json");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");   
            PrintWriter writer = new PrintWriter(response.getWriter());
            List<AttendancePlan> list = attendancePlanService.findAll();
            if(!StringUtils.isNoneBlank(date)){
            	date=sip.format(new Date());
            }
            AttendancePlan a= new AttendancePlan();
            if(list!=null && list.size()>0){
        		a=list.get(0);
        	}
            if(userId!=null){
            	Map<String,Object> map =new HashMap<String, Object>();
            	String weekStr=sdfEEEE.format(Timestamp.valueOf(date+" 00:00:00.0"));
            	String todayStr= DateUtils.date2ShortStr(Timestamp.valueOf(date+" 00:00:00.0"));
            	map.put("todayStr", todayStr+" "+weekStr);
            	UserInfo user = userService.findOne(userId);
            	GroupInfo group = groupService.findOne(user.getGroupId());
            	map.put("userName", user.getUserName());
            	map.put("groupName", group.getGroupName());
            	map.put("amOnTime", sdfHHMM.format(a.getCommonOn()));
            	map.put("amOffTime", sdfHHMM.format(a.getCommonAmOff()));
            	map.put("pmOnTime",sdfHHMM.format(a.getCommonPmOn()));
            	map.put("pmOffTime",sdfHHMM.format(a.getCommonOff()));
            	
            	map.put("attendanceTime", sdfHHMM.format(a.getCommonOn())+"-"+sdfHHMM.format(a.getCommonOff()));
            	List<Map<String,Object>> attendanceMapList = new ArrayList<Map<String,Object>>();
            	List<Attendance> attendanceList = attendanceService.todayRecord(userId, date);
            	Integer num=0;//打卡次数
            	if(attendanceList!=null&& attendanceList.size()>0){
            		num=attendanceList.size();
            		for(Attendance attendance:attendanceList){
            			Map<String,Object> attendanceMap =new HashMap<String, Object>(); 
            			attendanceMap.put("attType",attendance.getAttType() );
            			attendanceMap.put("attState",attendance.getAttState() );
            			attendanceMap.put("outOfRange",attendance.getOutOfRange());
            			attendanceMap.put("timestr",sdfHHMM.format(attendance.getCreateTime()));
            			attendanceMap.put("position",attendance.getPosition());
            			attendanceMapList.add(attendanceMap);
            		}
            	}
            	map.put("leaveMapList", null);
            	map.put("attendanceList", attendanceMapList);
            	map.put("num",num);//打卡次数
            	
            	Gson gson = new Gson();
				String json = gson.toJson(map);
	            writer.print("100||"+json);
	            writer.flush();
	            writer.close();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    
    
	/**
	 * 功能：获取服务器时间
	 * @return
	 */
	public String getServiceTime(){
			
		    try {
	            HttpServletResponse response = ServletActionContext.getResponse();
	            response.setHeader("ContentType", "text/json");
	            response.setCharacterEncoding("utf-8");
	            response.setContentType("text/html;charset=utf-8");   
	            PrintWriter writer = new PrintWriter(response.getWriter());

	            Date date=new Date();
				   //判断打卡限制（0不能打卡,1可以打卡）
				   int canDo=0;
				   Date lastT =null; //最后一次打卡时间
				    if(userId!=null){
				    	 SimpleDateFormat sip = new SimpleDateFormat("yyyy-MM-dd");
							String day = sip.format(new Date());
							List<Attendance> punchRecords=attendanceService.todayRecord(userId,day);
							//获取最后打卡时间
							if(punchRecords!=null&&punchRecords.size()>0){
									Attendance att = punchRecords.get(punchRecords.size()-1);
									if(att!=null){
										lastT = att.getCreateTime();
									}
							}
				    }
					if(lastT!=null){
						long a1 = date.getTime()-lastT.getTime();
						long b=10*60*1000;
						if(a1<b){
							canDo=0;
						}else{
							canDo=1;
						}
					}else{
						canDo=1;
					}
			    Map<String,Object> map = new HashMap<String,Object>();
			    map.put("canDo",canDo);
			    SimpleDateFormat simp1 = new SimpleDateFormat("yyyy年MM月dd日");
			    SimpleDateFormat simp2 = new SimpleDateFormat("HH:mm:ss");
			    map.put("date",simp1.format(date).replace("年0", "年").replace("月0", "月"));
			    map.put("time",simp2.format(date));
	            Gson gson = new Gson();
	            String json = gson.toJson(map);
	            writer.print("100||"+json);
	            writer.flush();
	            writer.close();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	 
		return null;
	}
	/**
	 * 
	 * 功能：打卡
	 * @return
	 */
	public String punchCard(){
		 HttpServletResponse response = ServletActionContext.getResponse();
         response.setHeader("ContentType", "text/json");
         response.setCharacterEncoding("utf-8");
         response.setContentType("text/html;charset=utf-8");   
         PrintWriter writer = null; 
         Gson gson = new Gson();
	    try {
	    	writer = new PrintWriter(response.getWriter());
            String info="0";
			if(userId==null){
			     throw new OAException("用户ID不能为空！");
			 }
			if(StringUtils.isBlank(position)){
			     throw new OAException("打卡位置不能为空！");
			 }
			 
			if(StringUtils.isBlank(longitude)){
			     throw new OAException("打卡位置的经度不能为空！");
			 }
			if(StringUtils.isBlank(latitude)){
			     throw new OAException("打卡位置的纬度不能为空！");
			 }
			
			Attendance att=new Attendance();
			Date date=new Date();				
			att.setCreateTime(new Timestamp(date.getTime()));
			att.setCreateUserId(userId);				
			att.setPosition(this.position);
			att.setLatitude(this.latitude);
			att.setLongitude(this.getLongitude());
			att.setAttSource(SOURCE_MOBILE);//手机端端打卡
			UserInfo userInfo = TransportUser.get();
			att.setCompanyId(userInfo.getCompanyId());
			attendanceService.saveOrUpdate(att);
			
			String json = gson.toJson("1");
            writer.print("100||"+json);
            writer.flush();
            writer.close();
        }catch(OAException e){
            String info = e.getMessage();
            String json = gson.toJson(info);
            writer.print("103||"+json);
            writer.flush();
            writer.close();
        }catch(Exception e1){
            e1.printStackTrace();
        }
  
	   return null;
	}
	/**
	 * 功能：获取当天/某天的打卡记录
	 * @return
	 */
	public String punchReport(){
         Gson gson = new Gson();
	    try {
			if(userId==null){
			     throw new OAException("用户ID不能为空！");
			 }
			if(StringUtils.isEmpty(date)){
				SimpleDateFormat sip = new SimpleDateFormat("yyyy-MM-dd");
				date = sip.format(new Date());
			}
			List<Attendance> punchRecords=attendanceService.todayRecord(userId,date,source);
			
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			SimpleDateFormat sipTime  = new SimpleDateFormat("HH:mm:ss");
			String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			if(punchRecords!=null&&punchRecords.size()>0){
				int i=1;
				for(Attendance atd : punchRecords){
					Map<String,Object> map = new HashMap<String,Object>();
					if(source!=null){
						//纬度
						String latitude = atd.getLatitude()!=null?atd.getLatitude():"";
						map.put("latitude", latitude);
						//经度
						String longitude = atd.getLongitude()!=null?atd.getLongitude():"";
						map.put("longitude", longitude);
					}else{
						map.put("time", sipTime.format(atd.getCreateTime()));
						String position = atd.getPosition()!=null?atd.getPosition():"";
						map.put("position", position);
					}
					map.put("index", i++);
					mapList.add(map);
				}
			}
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("list",mapList);
			result.put("currentTime",currentTime);
			
			String json = gson.toJson(result);
            ajax("100||"+json);
        }catch(OAException e){
            String info = e.getMessage();
            ajax("103||"+info);
        }catch(Exception e1){
            e1.printStackTrace();
        }
    
		return null;
	}

	/**
	 * 功能：请求返回打过卡的天
	 */
	public void getRecordDays(){
		try{
			if(userId==null){
				ajax("102||参数不正确");
				return;
			}
			
			List<String> list = attendanceService.getRecordDays(userId);
			Gson json = new Gson();
			ajax("100||"+json.toJson(list));
		}catch(Exception e){
			ajax("101||服务器异常");
		}
	}

	
	/**
	 * 获得大屏报表（签到、外勤、迟到、请假）
	 */
	public void getBigAttendanceReport(){
		try {
			if(userId==null){
				ajax("102||参数不正确");
				return;
			}
			UserInfo userInfo = userService.findOne(userId);
			String time = DateUtils.date2ShortStr(new Date());
			Map<String,Object> map =new HashMap<String, Object>();
			List<Attendance> signList= new ArrayList<Attendance>();//已签到
			List<Attendance> lateList= new ArrayList<Attendance>();//迟到
			List<Attendance> goOutList= new ArrayList<Attendance>();//外勤
			Map<Integer ,String> userMap= findUserMap().get("name");
			if(userInfo!=null){
				List<Attendance> list=attendanceService.todayRecord(null, time);
				if(list!=null && list.size()>0){
					for(Attendance a:list){
						if(a.getAttType()==10){//上午签到
							a.setUserName(userMap.get(a.getCreateUserId()));
							signList.add(a);
							if(a.getAttState()==2){//迟到
								lateList.add(a);
							}
							if(a.getOutOfRange()==1){//外勤
								goOutList.add(a);
							}
						}
					}
				}
				map.put("signList", signList);
				map.put("lateList", lateList);
				map.put("goOutList", goOutList);
				map.put("leaveList", null);
			}
				
			Gson json = new Gson();
			ajax("100||"+json.toJson(map));
			
		} catch (Exception e) {
			e.printStackTrace();
			ajax("101||服务器异常");
		}
	}
	
	/**
	 * 获得大屏报表（签到列表）
	 */
	public void getBigAttendanceReportOther(){
		try {
			if(userId==null){
				ajax("102||参数不正确");
				return;
			}
			UserInfo userInfo = userService.findOne(userId);
			String time = DateUtils.date2ShortStr(new Date());
			Map<String,Object> map =new HashMap<String, Object>();
			List<Attendance> signList= new ArrayList<Attendance>();//已签到
			Map<Integer ,String> userMap= findUserMap().get("name");
			Map<Integer ,String> photoMap= findUserMap().get("photo");
			if(userInfo!=null){
				List<Attendance> list=attendanceService.todayRecord(null, time);
				
				if(list!=null && list.size()>0){
					for(Attendance a:list){
						if(a.getAttType()==10){//上午签到
							a.setUserName(userMap.get(a.getCreateUserId()));
							a.setPhotoPath(photoMap.get(a.getCreateUserId()));
							signList.add(a);
						}
					}
				}
				map.put("signList", signList);
			}
				
			Gson json = new Gson();
			ajax("100||"+json.toJson(map));
			
		} catch (Exception e) {
			e.printStackTrace();
			ajax("101||服务器异常");
		}
	}
	
	
	/**
	 *封装USER
	 * @return
	 */
	private  Map<String ,Map> findUserMap(){
		List<UserInfo> list= userService.findAll();
		Map<String,Map> map =new HashMap<String, Map>();
		Map<Integer,String> nameMap =new HashMap<Integer, String>();
		Map<Integer,String> photoMap =new HashMap<Integer, String>();
		if(list!=null && list.size()>0){
			for(UserInfo u:list){
				nameMap.put(u.getUserId(),u.getUserName());
			}
			for(UserInfo u:list){
				photoMap.put(u.getUserId(),u.getPhoto());
			}
		}
		map.put("name", nameMap);
		map.put("photo", photoMap);
		return map;
	}
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static Logger getLogger() {
		return logger;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}


	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	/**
	 * @return the source
	 */
	public Integer getSource() {
		return source;
	}


	/**
	 * @param source the source to set
	 */
	public void setSource(Integer source) {
		this.source = source;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}

	public String getMonth() {
		return month;
	}


	public void setMonth(String month) { 
		this.month = month;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getToday() {
		return today;
	}

	public void setToday(Integer today) {
		this.today = today;
	}

	public Integer getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(Integer leaveType) {
		this.leaveType = leaveType;
	}
	 
	
	
	
}
