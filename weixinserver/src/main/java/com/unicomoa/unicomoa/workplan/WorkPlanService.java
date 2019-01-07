package com.unicomoa.unicomoa.workplan;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unicomoa.unicomoa.base.BaseRepository;
import com.unicomoa.unicomoa.base.BaseServiceProxy;

@Service
@Transactional
public class WorkPlanService extends BaseServiceProxy<WorkPlan> {

	@Resource
	private WorkPlanRepository workPlanRepository;
	
	@Override
	protected BaseRepository<WorkPlan> getBaseRepository() {
		// TODO Auto-generated method stub
		return workPlanRepository;
	}

	public List<WorkPlan> findByCreaterIDAndDayStr(int createrId,String dayStr,Sort sort){
		return workPlanRepository.findByCreaterIdAndDayStrLike(createrId, dayStr,sort);
	}
	
	public void updateTarget(int id) {
		workPlanRepository.updateTarget(id);
	}
	
	public List<WorkPlan> findByDayStr(String dayStr,Sort sort){
		return workPlanRepository.findByDayStrLike(dayStr, sort);
	}
}
