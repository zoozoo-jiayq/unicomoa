package cn.com.qytx.cbb.schedule.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.schedule.domain.Schedule;
import cn.com.qytx.platform.base.dao.StrongBaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
/**
 * 功能: 日程dao层
 * 版本: 1.0
 * 开发人员: panbo
 * 创建日期: 2016年6月2日
 * 修改日期: 2016年6月2日
 * 修改列表: 
 */
@Repository("scheduleDao")
public class ScheduleDao extends StrongBaseDao<Schedule, Integer>{

	private static SimpleDateFormat sdfYYYYMM = new SimpleDateFormat("yyyy-MM");
	private static SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 功能：获取日程列表 
	 * @param instanceId 实例Id
	 * @param type 实例类型
	 * @param status 日程状态 
	 * @param userId 日程创建人
	 * @param completeTime 完成时间
	 * @return List<Schedule>
	 */
	public List<Schedule> getScheduleList(String instanceId,String type,Integer status,Integer userId,String completeTime){
		String hql = "1=1";
		if(StringUtils.isNotBlank(instanceId)){
			hql += " and instanceId='"+instanceId+"'";
		}
		if(StringUtils.isNotBlank(type)){
			hql += " and type='"+type+"'";
		}
		if(status!=null){
			hql += " and status="+status;
		}
		if(userId!=null){
			hql += " and createUserId="+userId;
		}
		if(StringUtils.isNotBlank(completeTime)){
			hql += " and completeTime='"+completeTime+"'";
		}
		Sort sort = new Sort(new Sort.Order(Direction.ASC,"status"));
		return super.unDeleted().findAll(hql,sort);
	}
	
	
	/**
	 * 功能：根据状态获得 某个模块的 日程 数量
	 * @param instanceId 实例Id
	 * @param type 实例类型
	 * @param status 日程状态 
	 * @param userId 日程创建人
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getScheduleCountByStatus(String instanceId,String type,Integer status,Integer userId){
		String sql = "select count(*) from tb_schedule where is_delete=0";
		if(StringUtils.isNotBlank(instanceId)){
			sql += " and instance_id='"+instanceId+"'";
		}
		if(StringUtils.isNotBlank(type)){
			sql += " and type='"+type+"'";
		}
		if(status!=null){
			sql += " and status="+status;
		}
		if(userId!=null){
			sql += " and create_user_id="+userId;
		}
		List<Object> list = super.entityManager.createNativeQuery(sql).getResultList();
		int count = 0;
		if(list!=null&&list.size()>0){
			count = (Integer)list.get(0);
		}
		return count;
	}
	
	
	/**
	 * 功能：获得模块与日程数量Map
	 * @param instanceIds
	 * @param type
	 * @param completeTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer,Map<String,Integer>> getScheduleCountMap(String instanceIds,String type,String completeTime){
		String sql = "select instance_id,count(*) as tocalCount,SUM (CASE WHEN status = 0 THEN 1 ELSE 0 END) AS beingCount,SUM (CASE WHEN status = 1 THEN 1 ELSE 0 END) AS doneCount,SUM (CASE WHEN status = 2 THEN 1 ELSE 0 END) AS unDoneCount FROM tb_schedule where is_delete=0";
		if(StringUtils.isNotBlank(instanceIds)){
			sql += " and instance_id in("+instanceIds+")"; 
		}
		if(StringUtils.isNotBlank(type)){
			sql += " and type='"+type+"'";
		}
		if(StringUtils.isNotBlank(completeTime)){
			sql += " and completeTime='"+completeTime+"'";
		}
		sql += " GROUP BY instance_id";
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		Map<Integer,Map<String,Integer>> countMap = null;
		if(list!=null&&list.size()>0){
			countMap = new HashMap<Integer, Map<String,Integer>>();
			for(Object[] obj:list){
				Map<String,Integer> map = new HashMap<String, Integer>();
				map.put("totalCount", Integer.parseInt(String.valueOf(obj[1])));
				map.put("beingCount", Integer.parseInt(String.valueOf(obj[2])));
				map.put("doneCount",Integer.parseInt(String.valueOf(obj[3])));
				map.put("unDoneCount", Integer.parseInt(String.valueOf(obj[4])));
				countMap.put((Integer)obj[0], map);
			}
		}
		return countMap;
	}
	
	
	/**
	 * 功能：获取日程月统计Map
	 * @param completeMonth 2016-05
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Integer> getScheduleCountByMonth(String completeMonth,Integer userId){
		Map<String,Integer> countMap = null;
		String sql = "select count(*) as totalCount,SUM (CASE WHEN status = 1 THEN 1 ELSE 0 END) as doneCount ,SUM (CASE WHEN status = 1 THEN 1 ELSE 0 END) as noDoneCount from tb_schedule where is_delete=0";
		if(userId!=null){
			sql += " and create_user_id="+userId;
		}
		if(StringUtils.isNotBlank(completeMonth)){
			sql += " and SUBSTRING(CONVERT(VARCHAR(100),complete_time,23),0,8)='"+completeMonth+"'";
		}
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		if(list!=null&&list.size()>0){
			countMap = new HashMap<String, Integer>();
			Object[] obj = list.get(0);
			countMap.put("totalCount", Integer.parseInt(String.valueOf(obj[0]!=null?obj[0]:0)));
			countMap.put("doneCount", Integer.parseInt(String.valueOf(obj[1]!=null?obj[1]:0)));
			countMap.put("noDoneCount", Integer.parseInt(String.valueOf(obj[2]!=null?obj[2]:0)));
		}
		return countMap;
	}
	
	/**
	 * 功能：日程月报表-日记录列表
	 * @param completeMonth
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,List<Schedule>>> getScheduleListByDay(String completeMonth,Integer userId){
		 String sql = "select id,content,complete_time,create_user_id,create_time,status,order_index,undone_reason from tb_schedule where is_delete=0 ";
		if(userId!=null){
			sql += " and create_user_id="+userId;
		}
		if(StringUtils.isNotBlank(completeMonth)){
			sql += " and SUBSTRING(CONVERT(VARCHAR(100),complete_time,23),0,8)='"+completeMonth+"'";
		}
		sql += " ORDER BY order_index,complete_time ";
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		List<Map<String,List<Schedule>>> mapList = null;
		Date currDate = new Date();
		String currDateStr = sdfYYYYMMDD.format(currDate);
		String currMonthStr = sdfYYYYMM.format(currDate);
		boolean flag = false;
		if(list!=null&&list.size()>0){
			mapList = new ArrayList<Map<String,List<Schedule>>>();
			int size = list.size();
			int mapListSize = 0;
			for(int i = 0;i < size;i++){
				mapListSize = mapList.size();
				Map<String,List<Schedule>> map = new HashMap<String, List<Schedule>>();
				Object[] obj = list.get(i);
				String completeTimeStr = obj[2].toString();
				Schedule schedule = new Schedule();
				schedule.setCompleteTime(obj[2].toString()+" 00:00:00");
				schedule.setContent((String)obj[1]);
				schedule.setStatus((Integer)obj[5]);
				schedule.setOrderIndex((Integer)obj[6]);
				schedule.setUndoneReason((String)obj[7]);
				schedule.setId((Integer)obj[0]);
				List<Schedule> scheduleList = null;
				for(int j =0;j<mapListSize;j++){
					Map<String,List<Schedule>> resultMap = mapList.get(j);
					if(resultMap!=null&&resultMap.containsKey(completeTimeStr)){
						map = resultMap;
						flag = true;
						break;
					}
				}
				if(map.containsKey(completeTimeStr)){
					scheduleList = map.get(completeTimeStr);
				}else{
					scheduleList = new ArrayList<Schedule>();
				}
				scheduleList.add(schedule);
				map.put(completeTimeStr, scheduleList);
				if(currMonthStr.equals(completeMonth)){//如果是本月 今天特殊处理
					if(completeTimeStr.equals(currDateStr)){
							if(!flag){
								if(mapListSize==0){
									mapList.add(map);
								}else{
									if(mapList.get(0).containsKey(completeTimeStr)){
										mapList.set(0,map);
									}else{
										mapList.add(0,map);
									}
								}
							}else{
								flag = false;
							}
					}else{
						if(!flag){
							mapList.add(map);
						}else{
							flag = false;
						}
					}
				}else{
					if(!flag){
						mapList.add(map);
					}else{
						flag = false;
					}
					
				}
			}
		}
		return mapList;
	}
	
	/**
	 * 根据实例ID和类型查询日程集合
	 * @param instanceIds 多个时逗号分割
	 * @param type
	 * @return
	 */
	public List<Schedule> findListByInstanceIds(String instanceIds, String type){
		String hql = "1=1";
		if(StringUtils.isNotBlank(instanceIds)){
			hql += " and instanceId in ("+instanceIds+")";
		}
		if(StringUtils.isNotBlank(type)){
			hql += " and type='"+type+"'";
		}
		return super.unDeleted().findAll(hql);
	}
	
	/**
	 * 查询日历页上的点
	 * @param schedule
	 * @return
	 */
	public List<Schedule> findCalendarPointByTime (Schedule schedule) {
		Integer createUserId = schedule.getCreateUserId();
		String beginTime = schedule.getBeginTime();
		String endTime = schedule.getEndTime();
		
		if(createUserId == null || StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)) {
			return null;
		}
		
		StringBuffer hql = new StringBuffer();
		hql.append("1=1 ");
		hql.append("and createUserId = ").append(createUserId).append(" ");
		hql.append("and completeTime >= '").append(beginTime).append("' ");
		hql.append("and completeTime <= '").append(endTime).append("' ");
		
		return super.unDeleted().findAll(hql.toString());
	}
	
	/**
	 * 获取和操作的日程相同类型的所有子日程中未结束的数量
	 * @param schedule (要求 type 和 instanceId 必须有值。 DAO中不做验证。)
	 * @return
	 */
	public int getSameTypeUnFinishCount (Schedule schedule) {
		String instanceId = schedule.getInstanceId();
		String type = schedule.getType();
		
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(s.id) as num ");
		hql.append("FROM Schedule s  ");
		hql.append("WHERE s.isDelete = 0 and s.type = ? and s.instanceId = ? and s.status = 0 ");
		
		Object obj = super.entityManager.createQuery(hql.toString())
				.setParameter(1, type)
				.setParameter(2, instanceId)
				.getSingleResult();
		return ((Number)obj).intValue();
	}
	
	/**
	 * 获取和操作的日程相同类型的所有子日程的总数量
	 * @param schedule (要求 type 和 instanceId 必须有值。 DAO中不做验证。)
	 * @return
	 */
	public int getCount(Schedule schedule){
		String instanceId = schedule.getInstanceId();
		String type = schedule.getType();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(*) as num ");
		sb.append("FROM Schedule s ");
		sb.append("WHERE s.isDelete = ? and s.type = ? and s.instanceId = ? ");
		Object obj = super.entityManager.createQuery(sb.toString())
					.setParameter(1, 0)
					.setParameter(2, type)
					.setParameter(3, instanceId)
					.getSingleResult();
		return ((Number)obj).intValue();
	}
	

	/**
	 * 修改模块的ID
	 * @param schedule
	 */
	public void updateModuleId(Schedule schedule, Integer taskStatus, Integer decompose){
		if("task".equals(schedule.getType())){
			// 任务模块， 修改对应任务的状态
			super.entityManager.createNativeQuery("UPDATE tb_task set task_status = ?,decompose = ? where id = ?")
				.setParameter(1, taskStatus)
				.setParameter(2, decompose)
				.setParameter(3, Integer.parseInt(schedule.getInstanceId()))
				.executeUpdate();
		}
		
	}
	
	/**
	 * 功能：获取指定时间内单位下面所有未删除日程
	 * @param companyId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Object[]> getAllSchedule(Integer companyId,String startTime,String endTime){
		String sql="select a.create_user_id,a.complete_time,a.status,a.content,b.user_name,c.group_name from tb_schedule a,tb_user_info b,tb_group_info c where a.create_user_id=b.user_id and b.group_id=c.group_id ";
		sql+=" and (a.status=1 or a.status=0) ";
		sql+=" and a.company_id='"+companyId+"' ";
		if(StringUtils.isNotEmpty(startTime)){
			sql+=" and complete_time>='"+startTime+"' ";
		}
		if(StringUtils.isNotEmpty(endTime)){
			sql+=" and complete_time<='"+endTime+" 23:59:59'";
		}
		sql+=" order by a.create_user_id asc,a.complete_time asc,a.create_time asc";
		
		return super.entityManager.createNativeQuery(sql).getResultList();
	}
}
