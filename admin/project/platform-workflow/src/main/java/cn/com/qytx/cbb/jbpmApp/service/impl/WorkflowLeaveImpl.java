package cn.com.qytx.cbb.jbpmApp.service.impl;


import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.jbpmApp.dao.WorkflowLeaveDao;
import cn.com.qytx.cbb.jbpmApp.domain.AttenceVo;
import cn.com.qytx.cbb.jbpmApp.domain.WorkflowLeave;
import cn.com.qytx.cbb.jbpmApp.service.IWorkflowLeaveService;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
@Service("workflowLeaveImpl")
@Transactional
public class WorkflowLeaveImpl extends BaseServiceImpl<WorkflowLeave> implements IWorkflowLeaveService{

	@Resource(name="workflowleaveDao")
	private WorkflowLeaveDao workflowleaveDao;
	
	@Override
	public List<WorkflowLeave> findLeaveList(Integer userId) {
		return workflowleaveDao.findLeaveList(userId);
	}
	
	
	/**
	 * 功能：获取请假数
	 * @param userId
	 * @return
	 */
	public Integer getLeaveDays(Integer userId){
		long hour = 0;
		List<WorkflowLeave> wflList = workflowleaveDao.findLeaveList(userId);
		if(wflList!=null&&wflList.size()>0){
			for(WorkflowLeave wfl:wflList){
				Integer isRemove = wfl.getIsRemove();
				Timestamp startTime = wfl.getStartLeaveTime();
				if(isRemove.intValue()==1){
					Timestamp removeTime = wfl.getRemoveTime();
					hour += (removeTime.getTime()-startTime.getTime())/60*60*1000;
				}else{
					Timestamp currTime = new Timestamp(System.currentTimeMillis());
					hour += (currTime.getTime()-startTime.getTime())/60*60*1000;
				}
			}
		}
		hour = (hour/24)+(hour%24==0?0:1);
		return Integer.parseInt(hour+"");
	}

	@Override
	public Map<String, Object> findMapNum(Integer companyId,Integer state,Integer groupId) {
		// TODO Auto-generated method stub
		return workflowleaveDao.findMapNum(companyId,state,groupId);
	}

	
	@Override
	public Page<AttenceVo> getPageList(Pageable pageable, Integer state,
			Integer groupId) {
		// TODO Auto-generated method stub
		return workflowleaveDao.getPageList(pageable, state, groupId);
	}


	@Override
	public List<WorkflowLeave> getWorkflowLeaveList(String startTime,
			String endTime, Integer companyId,Integer userId,Integer type) {
		return workflowleaveDao.getWorkflowLeaveList(startTime,endTime,companyId,userId,type);
	}


	@Override
	public List<WorkflowLeave> findLeaveList(String time,Integer companyId) {
		// TODO Auto-generated method stub
		return workflowleaveDao.findLeaveList(time,companyId);
	}



}
