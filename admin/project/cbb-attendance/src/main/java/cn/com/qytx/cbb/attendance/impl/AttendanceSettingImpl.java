package cn.com.qytx.cbb.attendance.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.attendance.dao.AttendanceSettingDao;
import cn.com.qytx.cbb.attendance.domain.AttendanceSetting;
import cn.com.qytx.cbb.attendance.service.IAttendanceSetting;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
@Transactional
@Service("attendanceSettingImpl")
public class AttendanceSettingImpl extends BaseServiceImpl<AttendanceSetting> implements IAttendanceSetting{

	@Autowired
	private AttendanceSettingDao attendanceSettingDao;
	
	@Override
	public AttendanceSetting findAttendanceSetting(Integer companyId) {
		return attendanceSettingDao.findAttendanceSetting(companyId);
	}
	
}
