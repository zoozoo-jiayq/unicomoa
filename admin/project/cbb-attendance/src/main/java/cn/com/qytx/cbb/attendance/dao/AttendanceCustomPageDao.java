package cn.com.qytx.cbb.attendance.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.attendance.domain.AttendanceDays;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;


/**
 * 功能: 考勤记录 DAO
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2014-7-8
 */
@Repository("attendanceCustomPageDao")
public class AttendanceCustomPageDao  extends BaseDao<AttendanceDays, Integer> implements Serializable {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
	  /**
     * 分页查询考勤记录
     * @param pageInfo
     * @param userId 用户标识
     * @param beginT 开始时间
     * @param endT   结束时间
     * @return
     */
	public Page<AttendanceDays> findCustomPage(Pageable  pageInfo, Integer userId, String beginT, String endT) {
		  try{
			  String hql=" select ad from AttendanceDays ad where 1=1 ";
			  List<Object> values = new ArrayList<Object>();
			 if(StringUtils.isNotBlank(beginT)){
				 values.add(timeFormat.parse(beginT+" 00:00:00"));
				 hql+=" and day >='"+beginT+" 00:00:00'";
			 }
			 if(StringUtils.isNotBlank(endT)){
				 values.add(timeFormat.parse(endT+" 23:59:59"));
				 hql+=" and day <='"+endT+" 23:59:59'";
			 } 
			 hql += " order by day desc";
			 return super.findByPage(pageInfo, hql); 
		  }catch(Exception e){
			  return null;
		  }
	}

	
	 
   
}
