package cn.com.qytx.cbb.attendance.service;

import cn.com.qytx.cbb.attendance.domain.AttendanceSetting;
import cn.com.qytx.platform.base.service.BaseService;
/**
 * 打卡设置接口
 * @ClassName: IAttendanceSetting   
 * @author: WANG
 * @Date: 2016年6月2日 下午2:45:52   
 *
 */
public interface IAttendanceSetting extends BaseService<AttendanceSetting>{
	/**
	 * 获取打卡设置信息
	 * @Title: findAttendanceSetting   
	 * @param companyId
	 * @return
	 */
	public AttendanceSetting findAttendanceSetting(Integer companyId);
}
