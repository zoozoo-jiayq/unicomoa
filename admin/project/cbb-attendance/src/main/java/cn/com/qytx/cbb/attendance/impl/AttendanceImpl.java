package cn.com.qytx.cbb.attendance.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.attendance.dao.AttendanceCustomPageDao;
import cn.com.qytx.cbb.attendance.dao.AttendanceDao;
import cn.com.qytx.cbb.attendance.dao.AttendanceIpSetDao;
import cn.com.qytx.cbb.attendance.domain.Attendance;
import cn.com.qytx.cbb.attendance.domain.AttendanceDays;
import cn.com.qytx.cbb.attendance.domain.AttendanceIpSet;
import cn.com.qytx.cbb.attendance.domain.AttendancePlan;
import cn.com.qytx.cbb.attendance.service.IAttendance;
import cn.com.qytx.cbb.jbpmApp.dao.WorkflowLeaveDao;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowLeave;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.UserVo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
/**
 * 功能: 考勤记录 实现类
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
@Transactional
@Service("attendanceService")
public class AttendanceImpl extends BaseServiceImpl<Attendance> implements IAttendance,Serializable {
	private static final long serialVersionUID = 1L;
	@Resource(name="attendanceDao")
	private AttendanceDao attendanceDao;
	@Resource(name="attendanceIpSetDao")
	private AttendanceIpSetDao attendanceIpSetDao;
	/** 用户管理 */
	@Resource
	private IUser userService;
	 
	@Resource(name="groupService")
	private IGroup groupService;
	
	@Resource(name="attendanceCustomPageDao")
	private AttendanceCustomPageDao attendanceCustomPageDao;
	@Resource(name="workflowleaveDao")
	private WorkflowLeaveDao workflowLeaveDao;
  	 /**
	  *   判断IP是否在正确的范围内：
      *  True: IP地址在设置范围内
      *  False: IP地址不再设置范围内
	  * @param currentIpAddr 当前IP地址
	  */
	@Override
	public boolean ipCheck(String currentIpAddr) {
		return this.checkIp(currentIpAddr);
	}
	
	/**
     *查询用户的打卡次数统计
 	  * @param userIds 用户ID集合，用逗号隔开
 	  * @param beginT 开始时间
 	  * @param entT 结束时间
    */
	@Override
	public Map<Integer, Integer> tjAttendanceCount(String userids, String beginT, String endT,Integer companyId) {
		return attendanceDao.tjAttendanceCount(userids,beginT,endT,companyId);
	}
	
	
	 /**
     *  返回某一天的打卡记录
     * @param  day  日期  日期格式：yyyy-MM-dd
     */
	@Override
	public List<Attendance> todayRecord(Integer userId,String day) {
		return attendanceDao.todayRecord(userId,day);
	}

	  /**
     * 分页查询考勤记录
     * @param pageInfo
     * @param userId 用户标识
     * @param beginT 开始时间
     * @param endT   结束时间
     * @return
     */
	@Override
	public  Page<AttendanceDays> findCustomPage(Pageable pageInfo, Integer userId, String beginT, String endT) {
		return attendanceCustomPageDao.findCustomPage(pageInfo,userId,beginT,endT);  
	}

	@Override
	public Page<Attendance> exportPage(Pageable pageInfo, Integer groupId,Integer companyId, String keyword, String beginT, String endT) {
		if(StringUtils.isNotBlank(keyword)){
			keyword="%"+keyword+"%";
		}else{
			keyword="%";
		}
		String userIds="";
		if(groupId!=null){
			List<UserInfo> list = null;
			if(groupId.intValue()>0){
				list = userService.findAll(" isDelete=0 and loginName like ? and userId in ( select userId from GroupUser where groupId=? ) ", keyword,groupId);
			}else{
				list = userService.findAll(" isDelete=0 and loginName like ? and userId in ( select userId from GroupUser  ) ", keyword);
			}
			
			if(list!=null&&list.size()>0){
				for(UserInfo userInfo:list){
					userIds+=userInfo.getUserId()+",";
				}
			}
			if(userIds.endsWith(",")){
				userIds = userIds.substring(0,userIds.length()-1);
			}
		}
		return attendanceDao.exportPage(pageInfo,userIds,beginT,endT);  
	}

	@Override
	public Page<UserInfo> pageUserInfo(Pageable pageInfo, Integer groupId, Integer companyId, String keyword) {
		Page<UserInfo> res = null;
		String groupIds = "";
		if(groupId!=null){
			groupIds=groupId+"";
 	    }else{
 	    	groupIds="";
 		}
		UserVo vo = new UserVo();
		vo.setUserName(keyword);
		vo.setLoginName(keyword);
		vo.setSex(null);
		res = userService.findAllUsersByPage(vo , pageInfo, groupIds);
		List<UserInfo> ulist = res.getContent();
		if(ulist!=null&&ulist.size()>0){
			for(UserInfo uinfo:ulist){
				GroupInfo ginfo =groupService.getGroupByUserId(companyId, uinfo.getUserId());
				if(ginfo!=null){
					uinfo.setGroupId(ginfo.getGroupId());
					uinfo.setGroupName(ginfo.getGroupName());
				}
			}
			 
		}
		
		return res;
	}
	/**
	 * 检查Ip是否符合打卡
	 * @return
	 */
	private boolean checkIp(String remoteIp){
		//先比较前三项 
		//比较完之后 再把前三项的最后一位比较 大小
		String ips = remoteIp.substring(remoteIp.lastIndexOf(".")+1);
			Integer lastIp = Integer.parseInt(remoteIp.substring(remoteIp.lastIndexOf(".")+1));
			List<AttendanceIpSet> aisList = attendanceIpSetDao.findAttendAnceIpByReomoteIp(remoteIp);
			if(aisList != null && aisList.size() > 0){
				for(AttendanceIpSet ais:aisList){
					String beginIps = ais.getBeginIp();
					Integer beginIpLast = Integer.parseInt(beginIps.substring(beginIps.lastIndexOf(".")+1));
					String endIps = ais.getEndIp();
					Integer endIpLast = Integer.parseInt(endIps.substring(endIps.lastIndexOf(".")+1));
					if(lastIp<=endIpLast&&lastIp>=beginIpLast){
						return true;
					}
				}
			}
	
		return false;
	} 

	/**
     * 获取某个用户某段时间的所有打卡记录
     * @param uid 用户ID
     * @param beginT 开始时间
     * @param endT 结束时间
     * @return 打卡记录
     */
	@Override
	public List<Attendance> getAttendanceRecodes(Integer uid, String beginT, String endT,Integer state,String order) {
		 return attendanceDao.getAttendanceRecodes(  uid,  beginT,   endT,state,order);
	}

	  
    /**
     *  返回某一天的打卡记录
     * @param  day  日期  日期格式：yyyy-MM-dd
     * @param  source  1:PC端 2：手机端
    */
	@Override
	public List<Attendance> todayRecord(Integer userId, String day, Integer source) {
		return attendanceDao.todayRecord(userId,day,source);
	}
	
	
	/**
	 * 功能：根据用户id获得打过卡的日期
	 * @param userId
	 * @return
	 */
	public List<String> getRecordDays(int userId){
		return attendanceDao.getRecordDays(userId);
	}

	 
	public void save(Attendance a){
		if(a!=null){
			//attendanceDao.delTodyRecord(a.getCompanyId(), a.getAttType(), a.getCreateUserId());
			Attendance oldAttendance = attendanceDao.getAttendanceByAttTypeAndUserId(a.getCompanyId(), a.getAttType(), a.getCreateUserId());
			if(oldAttendance!=null){
				Integer carType = a.getCarType();
				if(carType==null||carType.intValue()!=1||a.getAttType().intValue()==21){
					oldAttendance.setAttSource(a.getAttSource());
					oldAttendance.setAttState(a.getAttState());
					oldAttendance.setLatitude(a.getLatitude());
					oldAttendance.setLongitude(a.getLongitude());
					oldAttendance.setMemo(a.getMemo());
					oldAttendance.setOutOfRange(a.getOutOfRange());
					oldAttendance.setPosition(a.getPosition());
					oldAttendance.setSettingTime(a.getSettingTime());
					oldAttendance.setCreateTime(a.getCreateTime());
					super.saveOrUpdate(oldAttendance);
				}
				
			}else{
				super.saveOrUpdate(a);
			}
		}
	}

	@Override
	public Page<Map<String, Object>> monthStatistics(Pageable pageable,
			String startTime, String endTime, Integer companyId,
			String userName, Integer groupId, AttendancePlan ap,Integer state) {
		
		return attendanceDao.monthStatistics(pageable,
				startTime, endTime, companyId,
			 userName,  groupId,  ap,state);
		
	}

	@Override
	public List<AttendanceDays> getAttendanceDays(String startTime,
			String endTime) {
		return attendanceDao.getAttendanceDays(startTime,endTime);
	}

	@Override
	public Map<Integer, Double> getUserLeaveDays(String startTime,
			String endTime, Integer companyId,AttendancePlan ap,Integer type) {
		Map<Integer,Double> map = new HashMap<Integer, Double>();
		List<WorkflowLeave> wflList = workflowLeaveDao.getWorkflowLeaveList(startTime, endTime, companyId,null,type);
		Timestamp onTimes = ap.getCommonOn();
		Timestamp offTimes = ap.getCommonOff();
		double hourDay = ((double)(offTimes.getTime() - onTimes.getTime()))/(1000*60*60);
		double dayNum = 0.00;
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfHHMMSS = new SimpleDateFormat("HH:mm:ss");
		Timestamp endTimeTs = Timestamp.valueOf(endTime+" 23:59:59.999");
		if(wflList!=null&&wflList.size()>0){
			double hour = 0.0;
			for(WorkflowLeave wfl:wflList){
				Integer userId = wfl.getUserId();
				hour = 0.0;
				Timestamp startLeaveTime = wfl.getStartLeaveTime();
				Timestamp endLeaveTime = wfl.getEndLeaveTime();
				if(endTimeTs.getTime()<endLeaveTime.getTime()){
					endLeaveTime = endTimeTs;
				}
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
				if(map.containsKey(userId)){
					Double oldHour = map.get(userId);
					hour +=oldHour;
				}
				dayNum = hour/hourDay;
				if(hour>hourDay){
					if(hour%hourDay>(hourDay/2)){
						dayNum+=1;
					}else if(hour%hourDay>0){
						dayNum+=0.5;
					}
				}
				
				map.put(userId, dayNum);
			}
		}
		return map;
	}

	
	
	public static void main(String[] args) {
		/*Timestamp startTs = Timestamp.valueOf("2017-07-11 00:00:00");
		Timestamp startTs1 = Timestamp.valueOf("2017-07-12 00:00:00");
		Calendar calendar = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		calendar.setTime(startTs);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendarEnd.setTime(startTs1);
		System.out.println(calendar.before(calendarEnd));
		while (calendar.before(calendarEnd)) {
	        calendar.add(Calendar.DAY_OF_YEAR, 1);
	        System.out.println(calendar.getTime());
	    }*/
		
		double hourDay = 7;
		double hour = 8;
		System.out.println(hour/hourDay);
		
		
		
		/*Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTs);
		System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
		calendar.setTime(startTs1);
		System.out.println(calendar.get(Calendar.DAY_OF_WEEK));*/
	}

	@Override
	public int leaveUserNum(String startTime, String endTime,
			Integer companyId, AttendancePlan ap,Integer type) {
		Map<Integer,Double> map = this.getUserLeaveDays(startTime, endTime, companyId, ap,type);
		return map.size();
	}


	
	@Override
	public List<Object[]> findAttendanceByUserId(String userIds, String month,
			Integer companyId) {
		// TODO Auto-generated method stub
		return attendanceDao.findAttendanceByUserId(userIds, month, companyId);
	}

	
	@Override
	public List<String> getRecordDaysTime(int userId,String startTime,String endTime) {
		// TODO Auto-generated method stub
		return attendanceDao.getRecordDaysTime(userId,startTime,endTime);
	}

	/* (non-Javadoc)
	 * @see cn.com.qytx.cbb.attendance.service.IAttendance#findMissingCardList(java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Object[]> findMissingCardList(Integer userId, String beginTime,
			String endTime, Integer companyId) {
		// TODO Auto-generated method stub
		return attendanceDao.findMissingCardList(userId, beginTime, endTime, companyId);
	}

	/* (non-Javadoc)
	 * @see cn.com.qytx.cbb.attendance.service.IAttendance#getAttendanceWeekDays()
	 */
	@Override
	public List<AttendanceDays> getAttendanceWeekDays(String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		return attendanceDao.getAttendanceWeekDays(startTime,endTime);
	}
	
	@Override
	public Map<String, String> getMapByTime(Integer userId,String startTime,String endTime) {
		// TODO Auto-generated method stub
		List<String> list = attendanceDao.getRecordDaysTime(userId,startTime,endTime);
		Map<String,String> map =new HashMap<String, String>();
		if(list!=null && list.size()>0){
			for(String string:list){
				map.put(string,string);
			}
		}
		return map;
	}


	@Override
	public Page<Map<String, Object>> dayStatistics(Pageable pageable,
			String startTime, String endTime, Integer companyId,
			String searchKey, Integer groupId, AttendancePlan ap,Integer state) {
		return attendanceDao.dayStatistics(pageable, startTime, endTime, companyId, searchKey, groupId, ap, state);
	}

	@Override
	public Map<String, Object> dayLeaveNumMap(String startTime, String endTime,
			Integer companyId, AttendancePlan ap) {
		
		
		
		
		return null;
	}

	@Override
	public Map<String, Double> getDateLeaveNum(String startTime,
			String endTime, Integer companyId, AttendancePlan ap,Integer type) {
		Map<String,Double> map = new HashMap<String, Double>();
		List<WorkflowLeave> wflList = workflowLeaveDao.getWorkflowLeaveList(startTime, endTime, companyId,null,type);
		Timestamp onTimes = ap.getCommonOn();
		Timestamp offTimes = ap.getCommonOff();
		double hourDay = ((double)(offTimes.getTime() - onTimes.getTime()))/(1000*60*60);
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfHHMMSS = new SimpleDateFormat("HH:mm:ss");
		Timestamp endTimeTs = Timestamp.valueOf(endTime+" 23:59:59.999");
		if(wflList!=null&&wflList.size()>0){
			double oneHour = 0.0;
			double twoHour = 0.0;
			String calendarStartYYYYMMDD = "";
			double dayNum = 0.0;
			double twoDayNum = 0.0;
			for(WorkflowLeave wfl:wflList){
				Integer userId = wfl.getUserId();
				oneHour = 0.0;
				twoHour = 0.0;
				Timestamp startLeaveTime = wfl.getStartLeaveTime();
				Timestamp endLeaveTime = wfl.getEndLeaveTime();
				if(endTimeTs.getTime()<endLeaveTime.getTime()){
					endLeaveTime = endTimeTs;
				}
				String startLeaveTimestr = sdfYYYYMMDD.format(startLeaveTime);
				String endLeaveTimestr = sdfYYYYMMDD.format(endLeaveTime);
				String startLeaveHHMMSS = sdfHHMMSS.format(startLeaveTime);
				String endLeaveHHMMSS = sdfHHMMSS.format(endLeaveTime);
				Timestamp newStartLeaveTs = Timestamp.valueOf("1970-01-01 "+startLeaveHHMMSS);
				Timestamp newEndLeaveTs = Timestamp.valueOf("1970-01-01 "+endLeaveHHMMSS);
				if(!startLeaveTimestr.equals(endLeaveTimestr)){
					
					if(newStartLeaveTs.getTime()<onTimes.getTime()){
						oneHour = hourDay;
					}
					if(newStartLeaveTs.getTime()>=onTimes.getTime()&&newStartLeaveTs.getTime()<=offTimes.getTime()){
						oneHour = (double)(offTimes.getTime() - newStartLeaveTs.getTime())/(1000*60*60);
					}
					if(newEndLeaveTs.getTime()>=onTimes.getTime()&&newEndLeaveTs.getTime()<=offTimes.getTime()){
						twoHour = (double)(newEndLeaveTs.getTime() - onTimes.getTime())/(1000*60*60);
					}
					if(newEndLeaveTs.getTime()>offTimes.getTime()){
						twoHour = hourDay;
					}
					
					dayNum = oneHour/hourDay;
					if(oneHour>hourDay){
						if(oneHour%hourDay>(hourDay/2)){
							dayNum+=1;
						}else if(oneHour%hourDay>0){
							dayNum+=0.5;
						}
					}
					twoDayNum = twoHour/hourDay;
					if(twoHour>hourDay){
						if(twoHour%hourDay>(hourDay/2)){
							twoDayNum+=1;
						}else if(twoHour%hourDay>0){
							twoDayNum+=0.5;
						}
					}
					if(map.containsKey(startLeaveTimestr+"_"+userId)){
						dayNum += map.get(startLeaveTimestr+"_"+userId);
					}
					map.put(startLeaveTimestr+"_"+userId, dayNum);
					if(map.containsKey(endLeaveTimestr+"_"+userId)){
						twoDayNum += map.get(endLeaveTimestr+"_"+userId);
					}
					map.put(endLeaveTimestr+"_"+userId, twoDayNum);
				}else{
					if(newStartLeaveTs.getTime()<=onTimes.getTime()){
						if(newEndLeaveTs.getTime()>=onTimes.getTime()&&newEndLeaveTs.getTime()<=offTimes.getTime()){
							oneHour =(double)(newEndLeaveTs.getTime() - onTimes.getTime())/(1000*60*60);
						}
						if(newEndLeaveTs.getTime()>offTimes.getTime()){
							oneHour = hourDay;
						}
					}
					
					if(newStartLeaveTs.getTime()>onTimes.getTime()&&newStartLeaveTs.getTime()<=offTimes.getTime()){
						if(newEndLeaveTs.getTime()>onTimes.getTime()&&newEndLeaveTs.getTime()<=offTimes.getTime()){
							oneHour = (double)(newEndLeaveTs.getTime() - newStartLeaveTs.getTime())/(1000*60*60);
						}
						
						if(newEndLeaveTs.getTime()>offTimes.getTime()){
							oneHour = (double)(offTimes.getTime() - onTimes.getTime())/(1000*60*60);
						}
						
					}
					dayNum = oneHour/hourDay;
					if(oneHour>hourDay){
						if(oneHour%hourDay>(hourDay/2)){
							dayNum+=1;
						}else if(oneHour%hourDay>0){
							dayNum+=0.5;
						}
					}
					if(map.containsKey(startLeaveTimestr+"_"+userId)){
						dayNum += map.get(endLeaveTimestr+"_"+userId);
					}
					map.put(startLeaveTimestr+"_"+userId, dayNum);
				}
				calendarStart.setTime(Timestamp.valueOf(startLeaveTimestr+" 00:00:00"));
				calendarStart.add(Calendar.DAY_OF_YEAR, 1);
				calendarEnd.setTime(Timestamp.valueOf(endLeaveTimestr+" 00:00:00"));
				while (calendarStart.before(calendarEnd)) {
					calendarStart.add(Calendar.DAY_OF_YEAR, 1);
					int week = calendarStart.get(Calendar.WEEK_OF_YEAR);
					if(week!=1&&week!=7){
						dayNum = 1;
						oneHour = hourDay;
					}
					calendarStartYYYYMMDD =  sdfYYYYMMDD.format(calendarStart.getTime());
					if(map.containsKey(calendarStartYYYYMMDD+"_"+userId)){
						dayNum += map.get(calendarStartYYYYMMDD+"_"+userId);
					}
					map.put(calendarStartYYYYMMDD+"_"+userId, dayNum);
			    }
			}
		}
		return map;
	}

	
	@Override
	public List<Object[]> findPeopleByState(String userIds,String beginTime, String endTime,
			Integer companyId) {
		// TODO Auto-generated method stub
		return attendanceDao.findPeopleByState(userIds,beginTime, endTime, companyId);
	}

	/* (non-Javadoc)
	 * @see cn.com.qytx.cbb.attendance.service.IAttendance#findListClockNum(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Object[]> findListClockNum(String userIds, String month,
			Integer companyId) {
		// TODO Auto-generated method stub
		return attendanceDao.findListClockNum(userIds, month, companyId);
	}

	/* (non-Javadoc)
	 * @see cn.com.qytx.cbb.attendance.service.IAttendance#findUserIdAndGroupName(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Object[]> findUserIdAndGroupName(String userIds,
			Integer companyId) {
		// TODO Auto-generated method stub
		return attendanceDao.findUserIdAndGroupName(userIds, companyId);
	}

	@Override
	public Map<Integer, Double> getUserAttendDaysMap(String startTime,
			String endTime, Integer companyId) {
		return attendanceDao.getUserAttendDaysMap(startTime,endTime,companyId);
	}

 
}
