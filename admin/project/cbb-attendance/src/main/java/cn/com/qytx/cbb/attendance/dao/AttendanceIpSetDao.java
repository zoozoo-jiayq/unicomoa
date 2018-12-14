package cn.com.qytx.cbb.attendance.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.attendance.domain.AttendanceIpSet;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;


/**
 * 功能: 考勤-Ip设置 DAO
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
@Repository("attendanceIpSetDao")
public class AttendanceIpSetDao extends BaseDao<AttendanceIpSet, Integer> implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
	   *   查询所有的IP设置记录
	*/
	public List<AttendanceIpSet> findAllAttendanceIpSet() {
		String hql=" isDelete=0 ";
		return super.findAll(hql,new Sort(Direction.DESC, "createTime"));
	}

	 /**
	   *   通过Ip匹配可能项
	*/
	public List<AttendanceIpSet> findAttendAnceIpByReomoteIp(String remoteIp) {
		remoteIp = remoteIp.substring(0, remoteIp.lastIndexOf("."));
		String hql = "isDelete = 0 and beginIp like '"+remoteIp+"%' ";
		return this.findAll(hql,new Sort(Direction.DESC, "endIp"), null);
	}

	public Object deleteAttendanceIpById(Integer ipSetId) {
		String hql = "update AttendanceIpSet set isDelete=1  where ipSetId="+ipSetId;
		return super.bulkDelete(hql,null);
	}

}
