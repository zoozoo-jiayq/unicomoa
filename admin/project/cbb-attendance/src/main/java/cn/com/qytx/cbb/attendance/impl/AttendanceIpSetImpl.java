package cn.com.qytx.cbb.attendance.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.attendance.dao.AttendanceIpSetDao;
import cn.com.qytx.cbb.attendance.domain.AttendanceIpSet;
import cn.com.qytx.cbb.attendance.service.IAttendanceIpSet;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;


/**
 * 功能: 考勤记录 实现类
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
@Transactional
@Service("attendanceIpSetService")
public class AttendanceIpSetImpl extends BaseServiceImpl<AttendanceIpSet> implements IAttendanceIpSet,Serializable {
	private static final long serialVersionUID = 1L;
	@Resource(name="attendanceIpSetDao")
	private AttendanceIpSetDao attendanceIpSetDao;

	    /**
		 *   查询所有的IP设置记录
		 */
	    @Override
	    public  List<AttendanceIpSet>   findAll(){
	    	return attendanceIpSetDao.findAllAttendanceIpSet();
	    }
	    public List<AttendanceIpSet> findAttendAnceIpByReomoteIp(String remoteIp){
			return attendanceIpSetDao.findAttendAnceIpByReomoteIp(remoteIp);
		}
		@Override
		public void deleteAttendanceIpById(Integer ipSetId) {
			attendanceIpSetDao.deleteAttendanceIpById(ipSetId);
		}    
}
