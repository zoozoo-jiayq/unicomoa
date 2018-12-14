package cn.com.qytx.cbb.attendance.service;

import java.util.List;

import cn.com.qytx.cbb.attendance.domain.AttendanceIpSet;
import cn.com.qytx.platform.base.service.BaseService;



/**
 * 功能: 考勤-IP设置 接口
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
public interface IAttendanceIpSet extends BaseService<AttendanceIpSet> {
 
     /**
	 *   查询所有的IP设置记录
	 */
    public  List<AttendanceIpSet>   findAll();

	/**
	 * 根据ID删除打卡记录
	 * @param ipSetId
	 */
	public void deleteAttendanceIpById(Integer ipSetId);
    
}
