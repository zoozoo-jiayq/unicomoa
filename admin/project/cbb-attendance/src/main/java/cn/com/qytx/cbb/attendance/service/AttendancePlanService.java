package cn.com.qytx.cbb.attendance.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.attendance.domain.AttendancePlan;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能  考勤组方案接口 
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年7月19日
 * <br/>修改日期  2016年7月19日
 * <br/>修改列表
 */
public interface AttendancePlanService extends BaseService<AttendancePlan> {

	/**
	 * 功能：根据用户id获得考勤组设置
	 * @param userId
	 * @param companyId
	 * @return
	 */
	public AttendancePlan getPlanByUserId(Integer userId,Integer companyId);
	
	/**
	 * 功能：获得手机端打卡统计内容
	 * @param userId
	 * @param month
	 * @return
	 */
	public Map<String, Object> getRecordReport(Integer userId,String month,Integer companyId);
	
	/**
	 * 功能：获得单位下面所有的考勤组方案
	 * @param companyId
	 * @return
	 */
	public Page<AttendancePlan> getPlanList(Pageable page,Integer companyId);
	
	/**
	 * 功能：获取所有不在当前考勤方案里面的人员信息
	 * @param companyId
	 * @param userIds
	 * @return
	 */
	public Map<String, String> checkUserIds(Integer companyId,String userIds,Integer planId);
	
	/**
	 * 功能：保存考勤方案
	 * @param plan
	 */
	public void save(AttendancePlan plan);
	
	/**
	 * 功能：获得单位下面指定月份的所有考勤记录
	 * @param companyId
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> getAllRecord(Integer companyId,String month);
}
