package cn.com.qytx.cbb.jbpmApp.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.cbb.jbpmApp.domain.AttenceVo;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowLeave;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

public interface IWorkflowLeaveService extends BaseService<WorkflowLeave> {
	 /**
	    * 获取在职人数/到岗人数/出勤率
	    * @return
	    */
		public Map<String,Object> findMapNum(Integer companyId,Integer state,Integer groupId);
		
		/**
		 * 获得考勤详情列表
		 * @return
		 */
		public Page<AttenceVo> getPageList(Pageable pageable, Integer state,Integer groupId);
	
	/**
	 * 功能：获取请假列表
	 * @return
	 */
	public List<WorkflowLeave> findLeaveList(Integer userId);
	
	
	
	/**
	 * 功能：获取请假数
	 * @param userId
	 * @return
	 */
	public Integer getLeaveDays(Integer userId);
	
	public List<WorkflowLeave> getWorkflowLeaveList(String startTime,String endTime,Integer companyId,Integer userId,Integer type);
	/**
	 * 功能：获取请假列表
	 * @return
	 */
	public List<WorkflowLeave> findLeaveList(String time,Integer companyId);
}
