package cn.com.qytx.cbb.schedule.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.schedule.dao.ScheduleDao;
import cn.com.qytx.cbb.schedule.domain.Schedule;
import cn.com.qytx.cbb.schedule.service.IScheduleService;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 功能: 日程接口实现
 * 版本: 1.0
 * 开发人员: panbo
 * 创建日期: 2016年6月2日
 * 修改日期: 2016年6月2日
 * 修改列表: 
 */
@Transactional
@Service("scheduleServiceImpl")
public class ScheduleServiceImpl extends BaseServiceImpl<Schedule> implements IScheduleService{

	@Resource(name="scheduleDao")
	private ScheduleDao scheduleDao;
	
	@Override
	public Schedule saveOrUpdate(Schedule schedule) {
		if(StringUtils.isNotBlank(schedule.getInstanceId()) && StringUtils.isNotBlank(schedule.getType())){
			int unFinishCount = scheduleDao.getSameTypeUnFinishCount(schedule);
			//未完成数量
			if(unFinishCount == 0){
				int sum = scheduleDao.getCount(schedule);
				if(sum==0){
					//改为进行中 未分解
					scheduleDao.updateModuleId(schedule, 1, 0);
				}else{
					//改为已结束 已分解
					scheduleDao.updateModuleId(schedule, 5, 1);
				}
			}else {
				//改为进行中 已分解
				scheduleDao.updateModuleId(schedule, 1, 1);
			}
		}
		
		return super.saveOrUpdate(schedule);
	}

	@Override
	public List<Schedule> getScheduleList(String instanceId,String type,Integer status,Integer userId,String completeTime)
	{
		return scheduleDao.getScheduleList(instanceId, type, status,userId,completeTime);
	}

	@Override
	public Map<Integer, Map<String, Integer>> getScheduleCountMap(
			String instanceIds, String type,String completeTime) {
		return scheduleDao.getScheduleCountMap(instanceIds, type,completeTime);
	}

	@Override
	public int getScheduleCountByStatus(String instanceId, String type,
			Integer status, Integer userId) {
		return scheduleDao.getScheduleCountByStatus(instanceId, type, status, userId);
	}

	@Override
	public List<Map<String, List<Schedule>>> getScheduleListByDay(
			String completeMonth, Integer userId) {
		return scheduleDao.getScheduleListByDay(completeMonth, userId);
	}

	@Override
	public List<Schedule> findCalendarPointByTime(Schedule schedule) {
		return scheduleDao.findCalendarPointByTime(schedule);
	}

	/**
	 * 功能：
	 * @param completeMonth
	 * @param userId
	 * @return
	 */
	@Override
	public Map<String, Integer> getScheduleCountByMonth(String completeMonth,
			Integer userId) {
		return scheduleDao.getScheduleCountByMonth(completeMonth, userId);
	}

	/**
	 * 功能：导出指定时间内单位的日程信息
	 * @return
	 */
	public List<Map<String, Object>> getExportList(Integer companyId,String startTime,String endTime){
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		List<Object[]> list = scheduleDao.getAllSchedule(companyId, startTime, endTime);
		Map<String, List<Map<String,Object>>> tempList = new HashMap<String, List<Map<String,Object>>>();//key:userId,value:用户的日程，按日
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(list!=null && list.size()>0){
			for(Object[] arr:list){
				Integer userId = (Integer) arr[0];
				String completeTime = (String) arr[1];
				String key = userId+"";
				String content = arr[3]==null?"":(String) arr[3];
				if(arr[2]!=null){
					int status = (Integer) arr[2];
					if(tempList.get(key)!=null){
						Map<String, Object> temp = getMapByDate(tempList.get(key), completeTime);
						if(temp!=null){
							if(status==0){//未完成
								List<String> failedList = (List<String>) temp.get("failedList");
								failedList.add(content);
								temp.put("failedList", failedList);
							}else if(status==1){//已完成
								List<String> successList = (List<String>) temp.get("successList");
								successList.add(content);
								temp.put("successList", successList);
							}
						}else{
							temp = new HashMap<String, Object>();
							String userName = arr[4]==null?"":(String) arr[4];
							String groupName = arr[5]==null?"":(String) arr[5];
							temp.put("createDate", completeTime);//日期
							temp.put("userName", userName);//姓名
							temp.put("groupName", groupName);//部门
							List<String> failedList = new ArrayList<String>();
							List<String> successList = new ArrayList<String>();
							if(status==0){//未完成
								failedList.add(content);
							}else if(status==1){//已完成
								successList.add(content);
							}
							temp.put("failedList", failedList);
							temp.put("successList", successList);
							temp.put("success", "");
							temp.put("failed", "");
							tempList.get(key).add(temp);
						}
					}else{
						Map<String, Object> map = new HashMap<String, Object>();
						String userName = arr[4]==null?"":(String) arr[4];
						String groupName = arr[5]==null?"":(String) arr[5];
						map.put("createDate", completeTime);//日期
						map.put("userName", userName);//姓名
						map.put("groupName", groupName);//部门
						List<String> failedList = new ArrayList<String>();
						List<String> successList = new ArrayList<String>();
						if(status==0){//未完成
							failedList.add(content);
						}else if(status==1){//已完成
							successList.add(content);
						}
						map.put("failedList", failedList);
						map.put("successList", successList);
						map.put("success", "");
						map.put("failed", "");
						List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
						l.add(map);
						tempList.put(key, l);
					}
				}
			}
			
			//前面封装过数据，下面封装列表字段
			Iterator it = tempList.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				List<Map<String,Object>> l = tempList.get(key);
				if(l!=null && l.size()>0){
					for(Map<String, Object> map:l){
						map = convertMap(map);
						listMap.add(map);
					}
				}
			}
		}
		return listMap;
	}
	
	/**
	 * 功能：根据日期，获得当天的日程
	 * @param list
	 * @param completeTime
	 * @return
	 */
	private Map<String, Object> getMapByDate(List<Map<String,Object>> list,String completeTime){
		if(list!=null && list.size()>0){
			for(Map<String, Object> map : list){
				String time = map.get("createDate").toString();
				if(time.equals(completeTime)){
					return map;
				}
			}
		}
		return null;
	}
	
	/**
	 * 功能：转换map的内容为导出数据
	 * @param map
	 * @return
	 */
	private Map<String, Object> convertMap(Map<String, Object> map){
		if(map.get("failedList")!=null){
			List<String> failedList = (List<String>) map.get("failedList");
			String failed="";
			for(int i=1;i<=failedList.size();i++){
				String content = failedList.get(i-1)==null?"":failedList.get(i-1);
				if(i>1){
					failed+="\n";
				}
				failed += i+","+failedList.get(i-1);
			}
			map.put("failed", failed);
		}
		if(map.get("successList")!=null){
			List<String> successList = (List<String>) map.get("successList");
			String success="";
			for(int i=1;i<=successList.size();i++){
				String content = successList.get(i-1)==null?"":successList.get(i-1);
				if(i>1){
					success+="\n";
				}
				success += i+","+successList.get(i-1);
			}
			map.put("success", success);
		}
		return map;
	}

}
