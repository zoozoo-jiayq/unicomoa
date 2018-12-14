package cn.com.qytx.cbb.schedule.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.schedule.domain.Schedule;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能: 日程接口
 * 版本: 1.0
 * 开发人员: panbo
 * 创建日期: 2016年6月2日
 * 修改日期: 2016年6月2日
 * 修改列表: 
 */
public interface IScheduleService extends BaseService<Schedule> {
	/**
	 * 功能：获取日程列表 
	 * @param instanceId 实例Id
	 * @param type 实例类型
	 * @param status 日程状态 
	 * @param userId 创建人Id
	 * @return
	 */
	public List<Schedule> getScheduleList(String instanceId,String type,Integer status,Integer userId,String completeTime);
	
	
	/**
	 * 功能：根据状态获得 某个模块的 日程 数量
	 * @param instanceId
	 * @param type
	 * @param status
	 * @param userId
	 * @return
	 */
	public int getScheduleCountByStatus(String instanceId,String type,Integer status,Integer userId);
	
	
	/**
	 * 功能：获得模块与日程数量Map
	 * @param instanceIds
	 * @param type 模块类型
	 * @return
	 */
	public Map<Integer,Map<String,Integer>> getScheduleCountMap(String instanceIds,String type,String completeTime);
	
	/**
	 * 功能：日程月报表-日记录列表
	 * @param completeMonth
	 * @param userId
	 * @return
	 */
	public List<Map<String,List<Schedule>>> getScheduleListByDay(String completeMonth,Integer userId);
	
	/**
	 * 查询日历页上的点
	 * @param schedule 
	 * @return List<Schedule>
	 */
	List<Schedule> findCalendarPointByTime (Schedule schedule);
	
	/**
	 * 功能：获取日程月统计Map
	 * @param completeMonth 2016-05
	 * @param userId
	 * @return
	 */
	public Map<String,Integer> getScheduleCountByMonth(String completeMonth,Integer userId);
	
	/**
	 * 功能：导出指定时间内单位的日程信息
	 * @return
	 */
	public List<Map<String, Object>> getExportList(Integer companyId,String startTime,String endTime);
}
