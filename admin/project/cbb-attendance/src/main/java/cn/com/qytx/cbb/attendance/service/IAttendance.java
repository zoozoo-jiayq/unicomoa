package cn.com.qytx.cbb.attendance.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.attendance.domain.Attendance;
import cn.com.qytx.cbb.attendance.domain.AttendanceDays;
import cn.com.qytx.cbb.attendance.domain.AttendancePlan;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;



/**
 * 功能: 考勤记录 接口
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
public interface IAttendance extends BaseService<Attendance> {

	  /**
	  *   判断IP是否在正确的范围内：
      *  True: IP地址在设置范围内
      *  False: IP地址不再设置范围内
	  * @param currentIpAddr 当前IP地址
	  */
       public   boolean ipCheck(String currentIpAddr);

      /**
       *查询用户的打卡次数统计
   	  * @param userIds 用户ID集合，用逗号隔开
   	  * @param beginT 开始时间
   	  * @param entT 结束时间
      */
      public   Map<Integer,Integer> tjAttendanceCount( String userids,String beginT,String endT,Integer companyId);

      /**
     *  返回某一天的打卡记录
     * @param  day  日期  日期格式：yyyy-MM-dd
     */
      public List<Attendance> todayRecord(Integer userId,String day);
      
      /**
       *  返回某一天的打卡记录
       * @param  day  日期  日期格式：yyyy-MM-dd
       * @param  source  1:PC端 2：手机端
      */
       public List<Attendance> todayRecord(Integer userId,String day,Integer source);
  

     /**
      * 分页查询考勤记录
      * @param pageInfo
      * @param userId 用户标识
      * @param beginT 开始时间
      * @param endT   结束时间
      * @return
      */
      public Page<AttendanceDays>  findCustomPage(Pageable pageInfo,Integer userId,String beginT,String endT);
      
      /**
       * 分页查询考勤记录
       * @param pageInfo
       * @param groupId  部门
       * @param companyId  部门
       * @param keyword  姓名
       * @param beginT 开始时间
       * @param endT   结束时间
       * @return
       */
       public Page<Attendance>  exportPage(Pageable pageInfo,Integer groupId,Integer companyId,String keyword,String beginT,String endT);
      
       /**
        * 分页查询考勤记录
        * @param pageInfo
        * @param groupId  部门
        * @param companyId  部门
        * @param keyword  姓名
        * @return
        */
        public Page<UserInfo>  pageUserInfo(Pageable pageInfo,Integer groupId,Integer companyId,String keyword);

        /**
         * 获取某个用户某段时间的所有打卡记录
         * @param uid 用户ID
         * @param beginT 开始时间
         * @param endT 结束时间
         * @return 打卡记录
         */
        public List<Attendance> getAttendanceRecodes(Integer uid, String beginT, String endT,Integer state,String order);
        
	
		/**
		 * 功能：根据用户id获得打过卡的日期
		 * @param userId
		 * @return
		 */
		public List<String> getRecordDays(int userId);
		
		public void save(Attendance a);
		
		/**
		 * 功能：获取某段时间的考勤天数
		 * @param startTime
		 * @param endTime
		 * @return
		 */
		public List<AttendanceDays> getAttendanceDays(String startTime,String endTime);
		
		/**
		 * 功能：考勤月度汇总
		 * @param pageable
		 * @param startTime
		 * @param endTime
		 * @param companyId
		 * @param userName
		 * @param groupId
		 * @param ap
		 * @return
		 */
		public Page<Map<String,Object>> monthStatistics(Pageable pageable,String startTime,String endTime,Integer companyId,String userName,Integer groupId,AttendancePlan ap,Integer state);
		
		
		public Map<Integer,Double> getUserLeaveDays(String startTime,String endTime,Integer companyId,AttendancePlan ap,Integer type);
		
		
		/**
		 * 功能：获取应到人数
		 * @param startTime
		 * @param endTime
		 * @param companyId
		 * @param ap
		 * @return
		 
		public int attendanceUserNum(String startTime,String endTime,Integer companyId,AttendancePlan ap);*/
		
		/**
		 * 功能：获取应到人数
		 * @param startTime
		 * @param endTime
		 * @param companyId
		 * @param ap
		 * @return
		 */
		public int leaveUserNum(String startTime,String endTime,Integer companyId,AttendancePlan ap,Integer type);

		public Page<Map<String, Object>> dayStatistics(Pageable pageable,
				String startTime, String endTime, Integer companyId,
				String searchKey, Integer groupId, AttendancePlan ap,Integer state);
		

		/**
		 * 查询某个人月考勤
		 * @return
		 */
		public  List<Object[]> findAttendanceByUserId(String userIds,String month,Integer companyId);
		
		/**
		 * 功能：根据用户id获得打过卡的日期(yyyy-MM-dd)
		 * @param userId
		 * @return
		 */
		public List<String> getRecordDaysTime(int userId,String startTime,String endTime);
		
		/**
		 * 查询缺卡时间
		 * @return
		 */
		public List<Object[]> findMissingCardList(Integer userId,String beginTime, String endTime,Integer companyId);
		
		/**
		 * 查询周六周日
		 * 
		 * @return
		 */
		public List<AttendanceDays> getAttendanceWeekDays(String startTime,
				String endTime);
		
		
		public Map<String,String> getMapByTime( Integer userId,String startTime,
		        String endTime);
		
		

		/**
		 * 功能：获取日请假数
		 * @return
		 */
		public Map<String,Object> dayLeaveNumMap(String startTime,String endTime,Integer companyId,AttendancePlan ap); 
		
		public Map<String,Double> getDateLeaveNum(String startTime,String endTime,Integer companyId,AttendancePlan ap,Integer type);

		/**
		 * 查询迟到、早退 等人数
		 * @return
		 */
		public List<Object[]> findPeopleByState(String userIds,String beginTime, String endTime,Integer companyId);
		
		/**
		 * 统计打卡次数
		 * @return
		 */
		public List<Object[]> findListClockNum(String userIds,String month,Integer companyId);
		
		
		/**
		 * 查询userId 、groupName
		 * @return
		 */
		public List<Object[]> findUserIdAndGroupName(String userIds,Integer companyId);
		
		public Map<Integer,Double> getUserAttendDaysMap(String startTime,String endTime,Integer companyId);
		
}
