package cn.com.qytx.cbb.attendance.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.attendance.domain.Attendance;
import cn.com.qytx.cbb.attendance.domain.AttendanceDays;
import cn.com.qytx.cbb.attendance.domain.AttendancePlan;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;


/**
 * 功能: 考勤记录 DAO
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
@Repository("attendanceDao")
public class AttendanceDao extends BaseDao<Attendance, Integer> implements Serializable {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 /**
     *  返回某一天的打卡记录
	 * @param userId 
     * @param  day  日期  日期格式：yyyy-MM-dd
     */
	public List<Attendance> todayRecord(Integer userId, String day) {
		List res  = null;
		if(StringUtils.isNotBlank(day)){
			String beginT = day+" 00:00:00";
			Timestamp beginTime = Timestamp.valueOf(beginT);
			String endT = day+" 23:59:59";
			Timestamp endTime = Timestamp.valueOf(endT);
			String hql=" createTime >=? and createTime <=? " ;
			if(userId!=null){
				hql+=" and createUserId="+userId;
			}
			res = super.findAll(hql,new Sort(Direction.DESC, "createTime"), beginTime,endTime);
		}
		return res;
	}

	/**
     *查询用户的打卡次数统计
 	  * @param userIds 用户ID集合，用逗号隔开
 	  * @param beginT 开始时间
 	  * @param entT 结束时间
    */
	public Map<Integer, Integer> tjAttendanceCount(String userids, String beginT, String endT,Integer companyId) {
		Map<Integer,Integer > res = new HashMap<Integer,Integer>();
		try{
			//new map(gu.groupId as groupId,COUNT(gu.userId) as count)
			List<Object> values = new ArrayList<Object>();
			 String hql=" select new map(createUserId as createUserId,COUNT(attId)as count) from Attendance  where 1=1   ";
			 if (companyId!=null) {
				 hql+=" and  companyId = "+companyId;
			}
			 if(StringUtils.isNotBlank(userids)){
				 hql+=" and  createUserId in("+userids+")  ";
			 } 
			 if(StringUtils.isNotBlank(beginT)){
				 values.add(timeFormat.parse(beginT+" 00:00:00"));
				 hql+=" and  createTime >= ? ";
			 }
			 if(StringUtils.isNotBlank(endT)){
				 values.add(timeFormat.parse(endT+" 23:59:59"));
				 hql+=" and  createTime <= ? ";
			 }
			 hql+=" group by createUserId ";
			 List<Object> list = super.find(hql,values.toArray());
			 if(list!=null&&list.size()>0){
				 for(Object  obj :list){
					 Map  map = (HashMap)obj;
					 if(map.get("createUserId")!=null){
						 Integer userId =(Integer) map.get("createUserId");
						 Long count =(Long) map.get("count");
						 res.put(userId, count.intValue());
					 }
				 }
			 }
		}catch(Exception e){

		}
		 return res;
	}

	/**
	 * 导出每个人的打卡记录
	 * @param pageInfo
	 * @param userIds
	 * @param beginT
	 * @param endT
	 * @return
	 */
	public Page<Attendance> exportPage(Pageable pageInfo, String userIds, String beginT, String endT) {
		String hql="select ad from  Attendance ad where  1=1  ";
		if(StringUtils.isNotBlank(userIds)){
			hql+=" and createUserId in( "+userIds+") ";
		}
		if(StringUtils.isNotBlank(beginT)){
			hql+=" and createTime >= '"+beginT+"'";
		}
		if(StringUtils.isNotBlank(endT)){
			hql+=" and createTime <= '"+endT+"') ";
		}
		hql += "order by createTime desc";
		return super.findByPage(pageInfo, hql);
	}

	/**
     * 获取某个用户某段时间的所有打卡记录
     * @param uid 用户ID
     * @param beginT 开始时间
     * @param endT 结束时间
     * @return 打卡记录
     */
	public List<Attendance> getAttendanceRecodes(Integer uid, String beginT, String endT,Integer state,String order) {
		 try{
			 List<Object> values = new ArrayList<Object>();
			 String hql=" 1=1 ";
			 if(uid!=null){
				hql+=" and createUserId=? " ;
				values.add(uid);
			 }
			 if(state!=null){
				 values.add(state);
				 hql+=" and attState =? ";
			 }
			 if(StringUtils.isNotBlank(beginT)){
				 values.add(timeFormat.parse(beginT+" 00:00:00"));
				 hql+=" and createTime >=? ";
			 }
			 if(StringUtils.isNotBlank(beginT)){
				 values.add(timeFormat.parse(endT+" 23:59:59"));
				 hql+=" and createTime <=? ";
			 } 
			 if(StringUtils.isNotBlank(order)){
					if("asc".equals(order)){
						return super.findAll(hql,new Sort(Direction.ASC, "createTime"), values.toArray());
					}else if("desc".equals(order)){
						return super.findAll(hql,new Sort(Direction.DESC, "createTime"), values.toArray());
					}else{
						return super.findAll(hql, values.toArray());
					}
				}else{
					return super.findAll(hql, values.toArray());
				}
		 }catch(Exception e){
			 return null;
		 }
	}

	/**
	 * 查询指定日期的打卡记录
	 * @param userId
	 * @param day
	 * @param source
	 * @return
	 */
	public List<Attendance> todayRecord(Integer userId, String day, Integer source) {
		List res  = null;
		if(StringUtils.isNotBlank(day)){
			String beginT = day+" 00:00:00";
			Timestamp beginTime = Timestamp.valueOf(beginT);
			String endT = day+" 23:59:59";
			Timestamp endTime = Timestamp.valueOf(endT);
			String hql=" createUserId=? and  createTime >=? and createTime <=? ";
			if(source!=null&&source>0){
				hql+=" and attSource="+source ;
			}
			res = super.findAll(hql,new Sort(Direction.DESC, "createTime"),userId, beginTime,endTime);
		}
		return res;
	}

	/**
	 * 功能：根据用户id获得打过卡的日期
	 * @param userId
	 * @return
	 */
	public List<String> getRecordDays(int userId){
		String sql = "select DISTINCT create_time as day from tb_attendance where create_user_id ="+userId;
		List<Object> list = entityManager.createNativeQuery(sql).getResultList();
		List<String> result = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		if(list!=null && list.size()>0){
			for(Object obj : list){
				String time = format.format(obj);
				result.add(time);
			}
		}
		
		return result;
	}
	
	/**
	 * 功能：重新打卡的时候删除老的打卡记录
	 */
	public void delTodyRecord(Integer companyId,Integer attType,Integer userId){
		if(companyId!=null && attType!=null && userId!=null){
			String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			super.entityManager.createQuery("delete Attendance where companyId='"+companyId+"' and createUserId='"+userId+"' and createTime>'"+today+"' and attType='"+attType+"' ").executeUpdate();
		}
	}
	
	public Attendance getAttendanceByAttTypeAndUserId(Integer companyId,Integer attType,Integer userId){
		Attendance attendance = null;
		if(companyId!=null && attType!=null && userId!=null){
			String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			attendance = super.findOne(" companyId=? and createUserId=? and createTime>'"+today+"' and attType=?", companyId,userId,attType);
		}
		return attendance;
	}

	public Page<Map<String, Object>> monthStatistics(Pageable pageable,
			String startTime, String endTime, Integer companyId,
			String userName, Integer groupId, AttendancePlan ap,Integer state) {
		String attendUserIds = ap.getUserIds();
		
		SimpleDateFormat sdfHHMM = new SimpleDateFormat("HH:mm:ss");
		String onTime = sdfHHMM.format(ap.getCommonOn());
		String amOffTime = null;//sdfHHMM.format(ap.getCommonAmOff());
		String pmOnTime = null;//sdfHHMM.format(ap.getCommonPmOn());
		String offTime = sdfHHMM.format(ap.getCommonOff());
		if(attendUserIds!=null){
			attendUserIds = attendUserIds.substring(1);
			attendUserIds = attendUserIds.substring(0, attendUserIds.length()-1);
		}
		//select create_user_id,CONVERT(varchar(10),create_time,23),count(*) from tb_attendance group by create_user_id,CONVERT(varchar(10),create_time,23) HAVING count(*) = 4;
		String sqlCondition = "select ug.user_id as userId,ug.group_id as groupId,MIN(ug.user_name) as userName,MIN(ug.group_name) as groupName,COUNT(DISTINCT DATE_FORMAT(att.create_time,'%Y-%m-%d')) AS attendDays,SUM(CASE WHEN att.att_state = 2 THEN 1 ELSE 0 END) lateTimes,"
				+ "SUM(CASE WHEN att.att_state = 2 THEN case when att.att_type=10 then TIMESTAMPDIFF(MINUTE,DATE_FORMAT(att.create_time,'%Y-%m-%d')+' "+onTime+"',att.create_time)  else 0 end ELSE 0 END) AS lateDuration,"
				+ "SUM(CASE WHEN att.att_state = 3 THEN 1 ELSE 0 END) AS earlyTimes,"
				+ "SUM(CASE WHEN att.att_state = 3 THEN case when att.att_type=20 then 0 else TIMESTAMPDIFF(MINUTE,DATE_FORMAT(att.create_time,'%Y-%m-%d')+' "+offTime+"',att.create_time) end ELSE 0 END) AS earlyDuration,"
				+ "SUM(CASE WHEN att.att_type = 10 or  att.att_type = 11 THEN 1 ELSE 0 END) AS onTimes,"
				+ "SUM(CASE WHEN att.att_type = 20 or  att.att_type = 21 THEN 1 ELSE 0 END) AS offTimes from ";
		sqlCondition += "(select u.user_id,u.user_name,u.group_id,gi.group_name,gi.order_index as orderIndex  FROM tb_user_info u INNER JOIN tb_group_info gi ON u.group_id = gi.group_id WHERE u.is_delete = 0 and u.group_id is not null and u.company_id="+companyId;
		if(StringUtils.isNotBlank(attendUserIds)){
			sqlCondition += " and u.user_id IN ("+attendUserIds+")";
		}
		if(StringUtils.isNoneBlank(userName)){
			sqlCondition += " and u.user_name like '%"+userName+"%'";
		}
		if(groupId!=null){
			sqlCondition += " and (gi.path like '"+groupId+",%' or gi.group_id="+groupId+")";
		}
		sqlCondition += ") ug LEFT JOIN (select att_1.* from tb_attendance att_1 INNER JOIN tb_attendance_days ad on DATE_FORMAT(att_1.create_time, '%Y-%m-%d') = DATE_FORMAT(ad.day, '%Y-%m-%d') where att_1.company_id="+companyId+" AND ad.week not in (6,7)  ";
		if(StringUtils.isNoneBlank(startTime)){
			sqlCondition += " and att_1.create_time > '"+startTime+" 00:00:00.000'";
		}
		if(StringUtils.isNoneBlank(endTime)){
			sqlCondition += " and att_1.create_time < '"+endTime+" 23:59:59.999'";
		}
		sqlCondition+=") att ON ug.user_id = att.create_user_id ";
		sqlCondition +=" where 1=1";
		if(state!=null){
			if(state==1){//迟到
				sqlCondition+=" and att.att_state=2";
			}
			
		}
		sqlCondition += " group by ug.user_id,ug.group_id";
		String countSqlCondition = "select count(*) from ("+sqlCondition+") n";
		Object objTotal =  entityManager.createNativeQuery(countSqlCondition).getSingleResult();
		Integer total = 0;
		if(objTotal!=null &&!"".equals(objTotal)){
			total = Integer.parseInt(objTotal.toString());
		}
		Query query = entityManager.createNativeQuery(sqlCondition);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        List<Map<String,Object>> content = total > pageable.getOffset() ? query.getResultList() : Collections.<Map<String,Object>> emptyList();
        return new PageImpl<Map<String,Object>>(content,pageable,total);
	}

	public List<AttendanceDays> getAttendanceDays(String startTime,
			String endTime) {
		String sql = "select day,week from tb_attendance_days where week not in(6,7) and DATE_FORMAT(day, '%Y-%m-%d %H:%i:%s') >= '"+startTime+"' and DATE_FORMAT(day, '%Y-%m-%d %H:%i:%s') <= '"+endTime+"'";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(AttendanceDays.class));
		return query.getResultList();
	}

	public Page<Map<String, Object>> dayStatistics(Pageable pageable,
			String startTime, String endTime, Integer companyId,
			String searchKey, Integer groupId, AttendancePlan plan,Integer state) {
		try {
		String attendUserIds = plan.getUserIds();
		if(attendUserIds!=null){
			attendUserIds = attendUserIds.substring(1);
			attendUserIds = attendUserIds.substring(0, attendUserIds.length()-1);
		}
		String sql="select a.days,a.week,b.att_state as onAttState,DATE_FORMAT(b.create_time, '%Y-%m-%d %H:%i:%s') AS onTime,b.out_of_range as onOutOfRange,b.memo as onMemo,b.position as onPosition,c.att_state as offAttState,DATE_FORMAT(c.create_time,'%Y-%m-%d %H:%i:%s') AS offTime,c.out_of_range as offOutOfRange,c.memo as offMemo,c.position as offPosition,a.userId,a.userName,a.groupName, "
				+ " d.att_state AS amOffAttState,DATE_FORMAT(d.create_time, '%Y-%m-%d %H:%i:%s') AS amOffTime,d.out_of_range AS amOffOutOfRange,d.memo AS amOffMemo,d.position AS amOffPosition,"
				+ " e.att_state AS pmOnAttState,DATE_FORMAT(e.create_time, '%Y-%m-%d %H:%i:%s') AS pmOnTime,e.out_of_range AS pmOnOutOfRange,e.memo AS pmOnMemo,e.position AS pmOnPosition from"
				+ " (select DATE_FORMAT(m.DAY, '%Y-%m-%d') AS days,m.week,n.user_name as userName,p.group_name as groupName,n.user_id as userId,p.order_index as orderIndex from tb_attendance_days m,tb_user_info n,tb_group_info p where n.company_id="+companyId;
				if(StringUtils.isNoneBlank(startTime)){
					sql += " and DATE_FORMAT(m.DAY, '%Y-%m-%d') >='"+startTime+"'"; 
				}
				if(StringUtils.isNoneBlank(endTime)){
					sql += " and DATE_FORMAT(m.DAY, '%Y-%m-%d') <='"+endTime+"'";
				}
				if(StringUtils.isNoneBlank(attendUserIds)){
					sql += " and n.user_id in ("+attendUserIds+")";
				}
				if(StringUtils.isNoneBlank(searchKey)){
					sql += " and n.user_name like '%"+searchKey+"%'";
				}
				if(groupId!=null){
					sql += " and (p.path like '"+groupId+",%' or p.group_id="+groupId+")";
				}
				sql += "  and n.group_id=p.group_id) a";
				sql += " left join"
				+ " (select DATE_FORMAT(create_time, '%Y-%m-%d') AS recordTime,create_time,att_state,out_of_range,memo,position,create_user_id from tb_attendance where att_type=10) b"
				+ " on a.days=b.recordTime and a.userId=b.create_user_id"
				+ " left JOIN"
				+ " (select DATE_FORMAT(create_time, '%Y-%m-%d') AS recordTime,create_time,att_state,out_of_range,memo,position,create_user_id from tb_attendance where att_type=21) c"
				+ " on a.days=c.recordTime and a.userId=c.create_user_id "
				+ " left join"
				+ " (select DATE_FORMAT(create_time, '%Y-%m-%d') AS recordTime,create_time,att_state,out_of_range,memo,position,create_user_id from tb_attendance where att_type=20) d"
				+ " on a.days=d.recordTime and a.userId=d.create_user_id"
				+ " left join"
				+ " (select DATE_FORMAT(create_time, '%Y-%m-%d') AS recordTime,create_time,att_state,out_of_range,memo,position,create_user_id from tb_attendance where att_type=11) e"
				+ " on a.days=e.recordTime and a.userId=e.create_user_id";
				sql+=" where 1=1";
				if(state!=null){
					if(state==1){//迟到
						sql+=" and b.att_state=2";
					}
					if(state==3){//外勤
						sql+=" and b.out_of_range=1";
					}
				}
				String countSql = "select count(*) from ("+sql+") n";
				sql += " order by a.orderIndex asc,a.userId asc,a.days asc";
				Object objTotal =  entityManager.createNativeQuery(countSql).getSingleResult();
				Integer total = 0;
				if(objTotal!=null &&!"".equals(objTotal)){
					total = Integer.parseInt(objTotal.toString());
				}
				Query query = entityManager.createNativeQuery(sql).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
		        String[] weekArr={"Mon","Tues","Wed","Thur","Fri","Sat","Sun"};
				String[] weekArr2={"周一","周二","周三","周四","周五","周六","周日"};
				SimpleDateFormat timeSDF=new SimpleDateFormat("HH:mm");
				SimpleDateFormat timeSDF2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				List<Object[]> list = query.getResultList();
				List<Map<String, Object>> listMap = null;
				if(list!=null && list.size()>0){
					listMap = new ArrayList<Map<String,Object>>(); 
					for(Object[] arr:list){
						int missingCard = 0;//0 未缺卡 1缺卡  
						if(arr[12]!=null){
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
							map.put("userId", arr[12]==null?null:(Integer)arr[12]);
							
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
		        List<Map<String,Object>> content = total > pageable.getOffset() ? listMap : Collections.<Map<String,Object>> emptyList();
		        return new PageImpl<Map<String,Object>>(content,pageable,total);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询周六周日
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<AttendanceDays> getAttendanceWeekDays(String startTime,
			String endTime) {
		String sql = "select day,week from tb_attendance_days where week  in(6,7) and DATE_FORMAT(day, '%Y-%m-%d') >= '"+startTime+"' and DATE_FORMAT(day, '%Y-%m-%d') <= '"+endTime+"'";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(AttendanceDays.class));
		return query.getResultList();
	}
	
	
	
	
	/**
	 * 查询某个人月考勤
	 * @return
	 */
	public  List<Object[]> findAttendanceByUserId(String userIds,String month,Integer companyId){
		String sql= " SELECT COUNT(DISTINCT DATE_FORMAT(day, '%Y-%m-%d %H:%i:%s')) attendanceDayNum,";
		sql+=" SUM(CASE WHEN att_state = 2 THEN 1 ELSE 0 END )lateNum,SUM(CASE WHEN att_state = 3 THEN 1 ELSE 0 END )leaveEarlyNum,";
		sql+=" sum(CASE WHEN att_type = 10 THEN 1 ELSE 0 END )amOnNum,sum(CASE WHEN att_type = 11 THEN 1 ELSE 0 END )pmOnNum,";
		sql+=" sum(CASE WHEN att_type = 20 THEN 1 ELSE 0 END )amOffNum,sum( CASE WHEN att_type = 21 THEN 1 ELSE 0 END)pmOffNum,create_user_id";
		sql+=" FROM tb_attendance WHERE 1=1";
		if(StringUtils.isNoneBlank(month)){
			sql+=" and DATE_FORMAT(create_time, '%Y-%m') = '"+month+"'";
		}
		if(companyId!=null){
			sql+=" and company_id = "+companyId;
		}
		if(StringUtils.isNoneBlank(userIds)){
			sql+=" and create_user_id in ("+userIds+")";
		}
		sql+=" group by create_user_id";
		Query query = entityManager.createNativeQuery(sql);
		return query.getResultList();
	}
	
	/**
	 * 功能：根据用户id获得打过卡的日期(yyyy-MM-dd)
	 * @param userId
	 * @return
	 */
	public List<String> getRecordDaysTime(int userId,String startTime,String endTime){
		String sql = "select DISTINCT DATE_FORMAT(create_time, '%Y-%m-%d') as day from tb_attendance where create_user_id ="+userId;
		if(StringUtils.isNoneBlank(startTime)){
			sql+=" and create_time >='"+startTime+"'";
		}
		if(StringUtils.isNoneBlank(endTime)){
			sql+=" and create_time <='"+endTime+"'";
		}
		
		List<Object> list = entityManager.createNativeQuery(sql).getResultList();
		List<String> result = new ArrayList<String>();
		if(list!=null && list.size()>0){
			for(Object obj : list){
				String time = obj.toString();
				result.add(time);
			}
		}
		
		return result;
	}
	
	/**
	 * 查询已打卡记录时间
	 * @return
	 */
	public List<Object[]> findMissingCardList(Integer userId,String beginTime, String endTime,Integer companyId){
		String sql=" select sum(case when att_type=10 then 1 else 0 END) amon ,sum(case when att_type=11 then 1 else 0 END) pmon,";
		sql+=" sum(case when att_type=20 then 1 else 0 END) amoff,sum(case when att_type=21 then 1 else 0 END) pmoff, DATE_FORMAT(create_time, '%Y-%m-%d'),create_user_id from tb_attendance where 1=1";
		if(userId!=null){
			sql+=" and create_user_id = "+userId;
		}
		if(StringUtils.isNoneBlank(beginTime)){
			sql+=" and create_time >='"+beginTime+"'";
		}
		if(StringUtils.isNoneBlank(endTime)){
			sql+=" and create_time <='"+endTime+"'";
		}
		if(companyId!=null){
			sql+=" and company_id="+companyId;
		}
		sql+=" group by DATE_FORMAT(create_time, '%Y-%m-%d'),create_user_id";
		Query query = entityManager.createNativeQuery(sql);
		return query.getResultList();
		
	} 
	
	/**
	 * 查询迟到、早退 等人数
	 * @return
	 */
	public List<Object[]> findPeopleByState(String userIds,String beginTime, String endTime,Integer companyId){
		String sql=" select sum(case when att_state=2  then 1 else 0 END) lateNum ,sum(case when att_state=3 then 1 else 0 END) leaveEarly,";
		sql+=" sum(case when out_of_range=1 then 1 else 0 END) outSideNum,create_user_id  from tb_attendance where 1=1";
		if(StringUtils.isNoneBlank(beginTime)){
			sql+=" and create_time >='"+beginTime+"'";
		}
		if(StringUtils.isNoneBlank(endTime)){
			sql+=" and create_time <='"+endTime+"'";
		}
		if(companyId!=null){
			sql+=" and company_id="+companyId;
		}
		if(StringUtils.isNoneBlank(userIds)){
			sql+=" and create_user_id in ("+userIds+")";
		}
		sql+=" group by create_user_id";
		Query query = entityManager.createNativeQuery(sql);
		return query.getResultList();
		
	} 
	/**
	 * 统计打卡次数
	 * @return
	 */
	public List<Object[]> findListClockNum(String userIds,String month,Integer companyId){
		String sql=" select count(create_user_id),create_user_id,COUNT( DISTINCT DATE_FORMAT(create_time, '%Y-%m-%d')) from tb_attendance WHERE 1=1" ;
		if(StringUtils.isNoneBlank(month)){
			if(month.length()==7){
				sql+=" and DATE_FORMAT(create_time, '%Y-%m') = '"+month+"'";
			}else{
				sql+=" and DATE_FORMAT(create_time, '%Y-%m') = '"+month+"'";
			}
			
		}
		if(companyId!=null){
			sql+=" and company_id = "+companyId;
		}
		if(StringUtils.isNoneBlank(userIds)){
			sql+=" and create_user_id in ("+userIds+")";
		}
		sql+=" group by create_user_id";
		Query query = entityManager.createNativeQuery(sql);
		
		return (List<Object[]>) query.getResultList();
	}
	
	/**
	 * 查询userId 、groupName
	 * @return
	 */
	public List<Object[]> findUserIdAndGroupName(String userIds,Integer companyId){
		String sql=" select u.user_id,g. group_name  from  tb_user_info u INNER JOIN tb_group_info g ON u.group_id=g.group_id where 1=1" ;
		if(companyId!=null){
			sql+=" and g.company_id = "+companyId;
		}
		if(StringUtils.isNoneBlank(userIds)){
			sql+=" and u.user_id in ("+userIds+")";
		}
		Query query = entityManager.createNativeQuery(sql);
		
		return (List<Object[]>) query.getResultList();
	}

	public Map<Integer, Double> getUserAttendDaysMap(String startTime,
			String endTime, Integer companyId) {
		//String sql = "select aa.create_user_id, count(*) from (select att.create_user_id ,CONVERT(varchar(100), att.create_time, 23) as recordTime,count(*) as recordNum from tb_attendance att INNER JOIN tb_attendance_days ad on CONVERT(varchar(100), att.create_time, 23) = CONVERT(varchar(100), ad.[day], 23)  where ad.week not in (6,7) ";
		String sql = "select aa.create_user_id, SUM(case when aa.recordDayNum>0 then 1 else 0 end)  from (select att.create_user_id ,DATE_FORMAT(att.create_time, '%Y-%m-%d') as recordTime,count(*) as recordNum,sum(case when att.att_type=10  then 1 else 0 end) as amRecordNum,sum(case when  att.att_type=21 then 1 else 0 end) as pmRecordNum,SUM(CASE WHEN att.att_type = 10 or att.att_type = 21 THEN 1 ELSE 0 END ) AS recordDayNum from tb_attendance att INNER JOIN tb_attendance_days ad on DATE_FORMAT(att.create_time, '%Y-%m-%d') = DATE_FORMAT(ad.day, '%Y-%m-%d') where ad.week not in (6,7) ";
		if(companyId!=null){
			sql += " and att.company_id = "+companyId;
		}
		if(StringUtils.isNoneBlank(startTime)){
			sql+=" and att.create_time >='"+startTime+" 00:00:00.000'";
		}
		if(StringUtils.isNoneBlank(endTime)){
			sql+=" and att.create_time <='"+endTime+" 23:59:59.000'";
		}
		sql += " group by att.create_user_id,DATE_FORMAT(att.create_time, '%Y-%m-%d %H:%i:%s') ) aa where aa.amRecordNum>0 or aa.pmRecordNum>0 group by aa.create_user_id ";
		//sql += " group by att.create_user_id,CONVERT(varchar(100), att.create_time, 23) HAVING COUNT(*)=4) aa group by aa.create_user_id ";
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		Map<Integer,Double> userMap = new HashMap<Integer, Double>();
		if(list!=null&&list.size()>0){
			for(Object[] obj:list){
				Integer count = (Integer)obj[1];
				userMap.put((Integer)obj[0], count!=null?count.doubleValue():0);
			}
		}
		return userMap;
	}
	
	
}
