package cn.com.qytx.cbb.attendance.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.attendance.domain.AttendancePlan;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能 考勤组方案  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年7月19日
 * <br/>修改日期  2016年7月19日
 * <br/>修改列表
 */
@Component
public class AttendancePlanDao extends BaseDao<AttendancePlan, Integer> implements Serializable {

	/**
	 * 功能：根据用户id获得考勤组设置
	 * @param userId
	 * @param companyId
	 * @return
	 */
	public AttendancePlan getPlanByUserId(Integer userId,Integer companyId){
		List<AttendancePlan> list = super.findAll(" isDelete=? and companyId=? and userIds like '%,"+userId+",%'", 0,companyId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 功能：获取指定用户某月的考勤记录
	 * @param userId
	 * @param month
	 * @return
	 */
	public Map<String, Object> recordReport(Integer userId,String month,Integer companyId){
		int noData=1;
		Map<String, Object> result = new HashMap<String, Object>();
		int lackCounts=0;
		int normalCounts=0;
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String today=dayFormat.format(new Date());
		String sql="select a.days,a.week,b.att_state as onAttState,CONVERT(varchar(100), b.create_time, 120) as onTime,b.out_of_range as onOutOfRange,b.memo as onMemo,b.[position] as onPosition,c.att_state as offAttState,CONVERT(varchar(100), c.create_time, 120) as offTime,c.out_of_range as offOutOfRange,c.memo as offMemo,c.[position] as offPosition, "
				+ " d.att_state AS pmOnAttState,CONVERT(varchar(100), d.create_time, 120) as pmOnTime,d.out_of_range AS pmOnOutOfRange,d.memo AS pmOnMemo,d.[position] AS pmOnPosition,e.att_state AS amOffAttState,CONVERT(varchar(100), e.create_time, 120) as amOffTime,e.out_of_range AS amOffOutOfRange,e.memo AS amOffMemo,e.[position] AS amOffPosition from"
				+ " (select CONVERT(varchar(100), day, 23) as days,week from tb_attendance_days where CONVERT(varchar(100), day, 23) like '"+month+"%' and CONVERT(varchar(100), day, 23) <='"+today+"') a"
				+ " left join"
				+ " (select CONVERT(varchar(100), create_time, 23) as recordTime,create_time,att_state,out_of_range,memo,[position] from tb_attendance where create_user_id="+userId+" and att_type=10) b"
				+ " on a.days=b.recordTime"
				+ " left JOIN"
				+ " (select CONVERT(varchar(100), create_time, 23) as recordTime,create_time,att_state,out_of_range,memo,[position] from tb_attendance where create_user_id="+userId+" and att_type=21) c"
				+ " on a.days=c.recordTime "
				+ " left JOIN"
				+ " (select CONVERT(varchar(100), create_time, 23) as recordTime,create_time,att_state,out_of_range,memo,[position] from tb_attendance where create_user_id="+userId+" and att_type=11) d"
				+ " on a.days=d.recordTime "
				+ " left JOIN"
				+ " (select CONVERT(varchar(100), create_time, 23) as recordTime,create_time,att_state,out_of_range,memo,[position] from tb_attendance where create_user_id="+userId+" and att_type=20) e"
				+ " on a.days=e.recordTime "
				+ "order by a.days desc";
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		AttendancePlan plan = this.getPlanByUserId(userId, companyId);
		String[] weekArr={"Mon","Tues","Wed","Thur","Fri","Sat","Sun"};
		if(list!=null && list.size()>0){
			for(Object[] arr:list){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("recordTime", arr[0]==null?"":arr[0].toString());
				map.put("onAttState", arr[2]==null?"1":arr[2].toString());
				map.put("onTime", arr[3]==null?"":arr[3].toString());
				map.put("onOutOfRange", arr[4]==null?"0":arr[4].toString());
				map.put("onMemo", arr[5]==null?"":arr[5].toString());
				map.put("onPosition", arr[6]==null?"":arr[6].toString());
				
				map.put("offAttState", arr[7]==null?"1":arr[7].toString());
				map.put("offTime", arr[8]==null?"":arr[8].toString());
				map.put("offOutOfRange", arr[9]==null?"0":arr[9].toString());
				map.put("offMemo", arr[10]==null?"":arr[10].toString());
				map.put("offPosition", arr[11]==null?"":arr[11].toString());
				
				map.put("pmOnAttState", arr[12]==null?"1":arr[12].toString());
				map.put("pmOnTime", arr[13]==null?"":arr[13].toString());
				map.put("pmOnOutOfRange", arr[14]==null?"0":arr[14].toString());
				map.put("pmOnMemo", arr[15]==null?"":arr[15].toString());
				map.put("pmOnPosition", arr[16]==null?"":arr[16].toString());
				
				
				map.put("amOffAttState", arr[17]==null?"1":arr[17].toString());
				map.put("amOffTime", arr[18]==null?"":arr[18].toString());
				map.put("amOffOutOfRange", arr[19]==null?"0":arr[19].toString());
				map.put("amOffMemo", arr[20]==null?"":arr[20].toString());
				map.put("amOffPosition", arr[21]==null?"":arr[21].toString());
				
				if(arr[3]!=null || arr[8]!=null || arr[13]!=null || arr[18]!=null){//本月有打卡记录
					noData=0;
				}
				int week=Integer.valueOf(arr[1].toString());//星期几 1,2,3,4,5,6,7
				if(plan==null){
					if(arr[3]==null){
						map.put("onAttState","6");//不需要打卡而且没有打卡
					}
					if(arr[8]==null){
						map.put("offAttState","6");//不需要打卡而且没有打卡
					}
					if(arr[12]==null){
						map.put("pmOnAttState","6");//不需要打卡而且没有打卡
					}
					if(arr[17]==null){
						map.put("amOffAttState","6");//不需要打卡而且没有打卡
					}
				}else{
					Class clazz = plan.getClass();
					try {
						Method restMethod =clazz.getDeclaredMethod("get"+weekArr[week-1]+"Rest");
						if(restMethod.invoke(plan)!=null && (Integer)restMethod.invoke(plan)==1){//今天是休息日
							if(arr[3]==null){
								map.put("onAttState","6");//不需要打卡而且没有打卡
							}
							if(arr[8]==null){
								map.put("offAttState","6");//不需要打卡而且没有打卡
							}
							if(arr[12]==null){
								map.put("pmOnAttState","6");//不需要打卡而且没有打卡
							}
							if(arr[17]==null){
								map.put("amOffAttState","6");//不需要打卡而且没有打卡
							}
						}else{//今天是工作日
							if(arr[3]==null){
								map.put("onAttState","5");//缺卡
								lackCounts++;
							}

							if(arr[8]==null){
								if(today.equals(arr[0])){
									map.put("offAttState","6");//时辰未到 不显示下班缺卡
									Method offMethod =clazz.getDeclaredMethod("get"+weekArr[week-1]+"Off");
									if(offMethod.invoke(plan)!=null){
										Timestamp offTime = (Timestamp)offMethod.invoke(plan);
										String offTimeStr = dayFormat.format(new Date())+" "+timeFormat.format(offTime);
										Long offTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(offTimeStr).getTime();
										if(System.currentTimeMillis()>offTimes){
											map.put("offAttState","5");//超过下班时间未打卡缺卡
											lackCounts++;
										}
									}
								}else{
									map.put("offAttState","5");//超过下班时间未打卡缺卡
									lackCounts++;
								}
							}
							
							/*if(arr[12]==null){
								map.put("pmOnAttState","5");//缺卡
								lackCounts++;
								
								if(today.equals(arr[0])){
									map.put("pmOnAttState","6");//时辰未到 不显示下班缺卡
									Timestamp pmOnTime = plan.getCommonPmOn();
									String pmOnTimeStr = dayFormat.format(new Date())+" "+timeFormat.format(pmOnTime);
									Long pmOnTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(pmOnTimeStr).getTime();
									if(System.currentTimeMillis()>pmOnTimes){
										map.put("pmOnAttState","5");//超过下班时间未打卡缺卡
										lackCounts++;
									}
								}else{
									map.put("pmOnAttState","5");//超过下班时间未打卡缺卡
									lackCounts++;
								}
								
							}
							
							if(arr[17]==null){
								if(today.equals(arr[0])){
									map.put("amOffAttState","6");//时辰未到 不显示下班缺卡
									Timestamp amOffTime = plan.getCommonAmOff();
									String amOffTimeStr = dayFormat.format(new Date())+" "+timeFormat.format(amOffTime);
									Long amOffTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(amOffTimeStr).getTime();
									if(System.currentTimeMillis()>amOffTimes){
										map.put("amOffAttState","5");//超过下班时间未打卡缺卡
										lackCounts++;
									}
								}else{
									map.put("amOffAttState","5");//超过下班时间未打卡缺卡
									lackCounts++;
								}
							}*/
							
							if(map.get("onAttState").equals("1") && map.get("amOffAttState").equals("1")&&map.get("pmOnAttState").equals("1")&&map.get("offAttState").equals("1") ){//正常日打卡
								normalCounts++;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				listMap.add(map);
			}
		}
		int lateCounts= 0;
		Object c1=super.entityManager.createNativeQuery("select count(*) from tb_attendance where create_user_id="+userId+" and att_state=2 and CONVERT(varchar(100), create_time, 23) like '"+month+"%'").getSingleResult();
		if(c1!=null){
			lateCounts= Integer.valueOf(c1.toString());
		}
		int leaveCounts= 0;
		Object c2=super.entityManager.createNativeQuery("select count(*) from tb_attendance where create_user_id="+userId+" and att_state=3 and CONVERT(varchar(100), create_time, 23) like '"+month+"%'").getSingleResult();
		if(c2!=null){
			leaveCounts= Integer.valueOf(c2.toString());
		}
		result.put("list", listMap);
		result.put("lackCounts", lackCounts);//缺卡次数
		result.put("lateCounts", lateCounts);//迟到次数
		result.put("leaveCounts", leaveCounts);//早退次数
		result.put("normalCounts", normalCounts);//正常天数
		result.put("noData", noData);//无数据1 有数据0
		return result;
	}
	
	/**
	 * 功能：获得单位下面所有的考勤组方案
	 * @param companyId
	 * @return
	 */
	public Page<AttendancePlan> getPlanList(Pageable page,Integer companyId){
		String condition=" 1=1 ";
		return super.unDeleted().findAll(condition, page);
	}
	
	/**
	 * 功能：获取所有不在当前考勤方案里面的人员信息
	 * @param companyId
	 * @param userIds
	 * @return
	 */
	public Map<String, String> checkUserIds(Integer companyId,String userIds,Integer planId){
		Map<String, String> result = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(userIds) && !",".equals(userIds) && !",,".equals(userIds)){
			if(userIds.startsWith(",")){
				userIds=userIds.substring(1);
			}
			if(userIds.endsWith(",")){
				userIds=userIds.substring(0,userIds.length()-1);
			}
			String sql="select distinct b.user_id,b.user_name from tb_attendance_plan a,tb_user_info b where a.is_delete=0 and a.company_id='"+companyId+"' and b.company_id='"+companyId+"' and a.user_ids like CONCAT('%,',cast(b.user_id as CHAR),',%') and b.user_id in ("+userIds+")";
			if(planId!=null){
				sql+=" and a.id<>"+planId+" ";
			}
			List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
			if(list!=null && list.size()>0){
				for(Object[] arr:list){
					String userId=arr[0].toString();
					String userName=arr[1].toString();
					result.put(userId, userName);
				}
			}
		}
		return result;
	}
	
	/**
	 * 功能：将用户从其他考勤方案中删掉
	 */
	public void cleanUserIds(Integer companyId,String userIds,Integer planId){
		if(StringUtils.isNotEmpty(userIds) && !",".equals(userIds) && !",,".equals(userIds)){
			if(userIds.startsWith(",")){
				userIds=userIds.substring(1);
			}
			if(userIds.endsWith(",")){
				userIds=userIds.substring(0,userIds.length()-1);
				}
			String sql="select distinct a.id,a.user_ids from tb_attendance_plan a,tb_user_info b where a.is_delete=0 and a.company_id='"+companyId+"' and b.company_id='"+companyId+"' and a.user_ids like CONCAT('%,',cast(b.user_id as CHAR),',%') and b.user_id in ("+userIds+") ";
			if(planId!=null){
				sql+=" and a.id<>"+planId+" ";
			}
			List<Object[]> planList = super.entityManager.createNativeQuery(sql).getResultList();
			if(planList!=null && planList.size()>0){
				for(Object[] plan:planList){
					int id = (Integer) plan[0];
					String oldUserIds=plan[1].toString();
					String[] arr = userIds.split(",");
					for(String userId:arr){
						if(StringUtils.isNotEmpty(userId)){
							oldUserIds = oldUserIds.replace(","+userId+",", ",");
						}
					}
					super.entityManager.createNativeQuery("update tb_attendance_plan set user_ids='"+oldUserIds+"' where id="+id+" ").executeUpdate();
				}
			}
		}
	}
	
	/**
	 * 功能：获得单位下面指定月份的所有考勤记录
	 * @param companyId
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> getAllRecord(Integer companyId,String month){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>(); 
		try {
			SimpleDateFormat timeSDF=new SimpleDateFormat("HH:mm");
			SimpleDateFormat timeSDF2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String monthStr = new SimpleDateFormat("yyyy-MM").format(sdf.parse(month));
			String today=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String sql="select a.days,a.week,b.att_state as onAttState,CONVERT(varchar(100), b.create_time, 120) as onTime,b.out_of_range as onOutOfRange,b.memo as onMemo,b.[position] as onPosition,c.att_state as offAttState,CONVERT(varchar(100), c.create_time, 120) as offTime,c.out_of_range as offOutOfRange,c.memo as offMemo,c.[position] as offPosition,a.userId,a.userName,a.groupName, "
					+ " d.att_state AS amOffAttState,CONVERT (VARCHAR (100),d.create_time,120) AS amOffTime,d.out_of_range AS amOffOutOfRange,d.memo AS amOffMemo,d.[position] AS amOffPosition,"
					+ " e.att_state AS pmOnAttState,CONVERT (VARCHAR (100),e.create_time,120) AS pmOnTime,e.out_of_range AS pmOnOutOfRange,e.memo AS pmOnMemo,e.[position] AS pmOnPosition from"
					+ " (select CONVERT(varchar(100), m.day, 23) as days,m.week,n.user_name as userName,p.group_name as groupName,n.user_id as userId,p.order_index as orderIndex from tb_attendance_days m,tb_user_info n,tb_group_info p where CONVERT(varchar(100), m.day, 23) like '"+monthStr+"%' and CONVERT(varchar(100), m.day, 23) <='"+today+"' and n.company_id='"+companyId+"' and n.group_id=p.group_id) a"
					+ " left join"
					+ " (select CONVERT(varchar(100), create_time, 23) as recordTime,create_time,att_state,out_of_range,memo,[position],create_user_id from tb_attendance where att_type=10) b"
					+ " on a.days=b.recordTime and a.userId=b.create_user_id"
					+ " left JOIN"
					+ " (select CONVERT(varchar(100), create_time, 23) as recordTime,create_time,att_state,out_of_range,memo,[position],create_user_id from tb_attendance where att_type=21) c"
					+ " on a.days=c.recordTime and a.userId=c.create_user_id "
					+ " left join"
					+ " (select CONVERT(varchar(100), create_time, 23) as recordTime,create_time,att_state,out_of_range,memo,[position],create_user_id from tb_attendance where att_type=20) d"
					+ " on a.days=d.recordTime and a.userId=d.create_user_id"
					+ " left join"
					+ " (select CONVERT(varchar(100), create_time, 23) as recordTime,create_time,att_state,out_of_range,memo,[position],create_user_id from tb_attendance where att_type=11) e"
					+ " on a.days=e.recordTime and a.userId=e.create_user_id"
					+ " order by a.orderIndex asc,a.userId asc,a.days asc";
			List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
			String[] weekArr={"Mon","Tues","Wed","Thur","Fri","Sat","Sun"};
			String[] weekArr2={"周一","周二","周三","周四","周五","周六","周日"};
			Map<Integer, AttendancePlan> planMap = getAllPlan(companyId);
			if(list!=null && list.size()>0){
				for(Object[] arr:list){
					if(arr[12]!=null){
						int userId = Integer.valueOf(arr[12].toString());
						AttendancePlan plan = planMap.get(userId);
						if(plan == null){
							continue;
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("createDate", arr[0]==null?"":arr[0].toString());
						map.put("onTime", "");
						if(arr[3]!=null){
							map.put("onTime", timeSDF.format(timeSDF2.parse(arr[3].toString())));
						}
						map.put("onMemo", arr[5]==null?"":arr[5].toString());
						map.put("onPosition", arr[6]==null?"":arr[6].toString());
						map.put("offTime","");
						if(arr[8]!=null){
							map.put("offTime", timeSDF.format(timeSDF2.parse(arr[8].toString())));
						}
						map.put("offMemo", arr[10]==null?"":arr[10].toString());
						map.put("offPosition", arr[11]==null?"":arr[11].toString());
						map.put("userName", arr[13]==null?"":arr[13].toString());
						map.put("groupName", arr[14]==null?"":arr[14].toString());
						
						map.put("amOffTime","");
						if(arr[16]!=null){
							map.put("amOffTime", timeSDF.format(timeSDF2.parse(arr[16].toString())));
						}
						map.put("amOffMemo", arr[18]==null?"":arr[18].toString());
						map.put("amOffPosition", arr[19]==null?"":arr[19].toString());
						
						map.put("pmOnTime","");
						if(arr[21]!=null){
							map.put("pmOnTime", timeSDF.format(timeSDF2.parse(arr[21].toString())));
						}
						map.put("pmOnMemo", arr[23]==null?"":arr[23].toString());
						map.put("pmOnPosition", arr[24]==null?"":arr[24].toString());
						
						int week=Integer.valueOf(arr[1].toString());//星期几 1,2,3,4,5,6,7
						map.put("week", weekArr2[week-1]);
						
						map.put("onState", "");
						map.put("amOffState", "");
						map.put("pmOnState", "");
						map.put("offState", "");
						if(arr[2]!=null){
							map.put("onState","正常");
							if(arr[2].toString().equals("1") ){//正常日打卡
								if(arr[4]!=null && arr[4].toString().equals("1")){//外勤打卡
									map.put("onState","外勤");
								}
							}else if(arr[2].toString().equals("2")){//迟到
								map.put("onState","迟到");
								if(arr[4]!=null && arr[4].toString().equals("1")){//外勤打卡
									map.put("onState","外勤-迟到");
								}
							}else if(arr[2].toString().equals("7")){//加班
								map.put("onState","加班");
								if(arr[4]!=null && arr[4].toString().equals("1")){//外勤打卡
									map.put("onState","外勤-加班");
								}
							}
						}
						
						if(arr[7]!=null){
							map.put("offState","正常");
							if(arr[7].toString().equals("1")){//正常日打卡
								if(arr[9]!=null && arr[9].toString().equals("1")){//外勤打卡
									map.put("offState","外勤");
								}
							}else if(arr[7].toString().equals("3")){//早退
								map.put("offState","早退");
								if(arr[9]!=null && arr[9].toString().equals("1")){//外勤打卡
									map.put("offState","外勤-早退");
								}
							}else if(arr[7].toString().equals("7")){//加班
								map.put("offState","加班");
								if(arr[9]!=null && arr[9].toString().equals("1")){//外勤打卡
									map.put("offState","外勤-加班");
								}
							}
						}
						
						
						if(arr[15]!=null){
							map.put("amOffState","正常");
							if(arr[15].toString().equals("1")){//正常日打卡
								if(arr[17]!=null && arr[17].toString().equals("1")){//外勤打卡
									map.put("amOffState","外勤");
								}
							}else if(arr[15].toString().equals("3")){//早退
								map.put("amOffState","早退");
								if(arr[17]!=null && arr[17].toString().equals("1")){//外勤打卡
									map.put("amOffState","外勤-早退");
								}
							}else if(arr[15].toString().equals("7")){//加班
								map.put("amOffState","加班");
								if(arr[17]!=null && arr[17].toString().equals("1")){//外勤打卡
									map.put("amOffState","外勤-加班");
								}
							}
						}
						
						if(arr[20]!=null){
							map.put("pmOnState","正常");
							if(arr[20].toString().equals("1") ){//正常日打卡
								if(arr[22]!=null && arr[22].toString().equals("1")){//外勤打卡
									map.put("pmOnState","外勤");
								}
							}else if(arr[20].toString().equals("2")){//迟到
								map.put("pmOnState","迟到");
								if(arr[22]!=null && arr[22].toString().equals("1")){//外勤打卡
									map.put("pmOnState","外勤-迟到");
								}
							}else if(arr[20].toString().equals("7")){//加班
								map.put("pmOnState","加班");
								if(arr[22]!=null && arr[22].toString().equals("1")){//外勤打卡
									map.put("pmOnState","外勤-加班");
								}
							}
						}
						
						if(plan==null){
							if(arr[3]!=null&&"".equals(map.get("onState"))){
								map.put("onState","加班");//不需要打卡而且没有打卡
							}
							if(arr[8]!=null&&"".equals(map.get("offState"))){
								map.put("offState","加班");//不需要打卡而且没有打卡
							}
							if(arr[15]!=null&&"".equals(map.get("amOffState"))){
								map.put("amOffState","加班");//不需要打卡而且没有打卡
							}
							if(arr[20]!=null&&"".equals(map.get("pmOnState"))){
								map.put("pmOnState","加班");//不需要打卡而且没有打卡
							}
						}else{
							Class clazz = plan.getClass();
							try {
								Method restMethod =clazz.getDeclaredMethod("get"+weekArr[week-1]+"Rest");
								if(restMethod.invoke(plan)!=null && (Integer)restMethod.invoke(plan)==1){//今天是休息日
									if(arr[3]!=null&&"".equals(map.get("onState"))){
										map.put("onState","加班");
										if(arr[4]!=null && arr[4].toString().equals("1")){
											map.put("onState","外勤-加班");//不需要打卡而且没有打卡
										}
									}
									if(arr[8]!=null&&"".equals(map.get("offState"))){
										map.put("offState","加班");
										if(arr[9]!=null && arr[9].toString().equals("1")){
											map.put("offState","外勤-加班");//不需要打卡而且没有打卡
										}
									}
									if(arr[15]!=null&&"".equals(map.get("amOffState"))){
										map.put("amOffState","加班");
										if(arr[16]!=null && arr[16].toString().equals("1")){
											map.put("amOffState","外勤-加班");//不需要打卡而且没有打卡
										}
									}
									if(arr[20]!=null&&"".equals(map.get("pmOnState"))){
										map.put("offState","加班");
										if(arr[21]!=null && arr[21].toString().equals("1")){
											map.put("pmOnState","外勤-加班");//不需要打卡而且没有打卡
										}
									}
								}else{//今天是工作日
									if(arr[3]==null&&"".equals(map.get("onState"))){
										map.put("onState","缺卡");//缺卡
									}
									if(arr[8]==null&&"".equals(map.get("offState"))){
										map.put("offState","缺卡");//缺卡
									}
									if(arr[15]==null&&"".equals(map.get("amOffState"))){
										map.put("amOffState","缺卡");//缺卡
									}
									if(arr[20]==null&&"".equals(map.get("pmOnState"))){
										map.put("pmOnState","缺卡");//缺卡
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						listMap.add(map);
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listMap;
	}
	
	/**
	 * 功能：获得所有的考勤方案
	 * @param companyId
	 * @return
	 */
	public Map<Integer, AttendancePlan> getAllPlan(Integer companyId){
		Map<Integer, AttendancePlan> map = new HashMap<Integer, AttendancePlan>();
		List<AttendancePlan> list = this.findAll(" isDelete=0 and companyId='"+companyId+"'");
		if(list!=null && list.size()>0){
			for(AttendancePlan plan:list){
				if(plan.getUserIds()!=null && !"".equals(plan.getUserIds())){
					String[] arr = plan.getUserIds().split(",");
					for(String uId:arr){
						if(StringUtils.isNotEmpty(uId)){
							int userId = Integer.valueOf(uId);
							map.put(userId, plan);
						}
					}
				}
			}
		}
		return map;
	}
}
