package com.unicomoa.unicomoa.workplan;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.unicomoa.unicomoa.base.BaseRepository;
import com.unicomoa.unicomoa.base.BaseServiceProxy;

@Service
@Transactional
public class WorkPlanProgressService extends BaseServiceProxy<WorkPlanProgress> {

	@Resource
	private WorkPlanProgressRepository workPlanProgressRepository;
	
	@Override
	protected BaseRepository<WorkPlanProgress> getBaseRepository() {
		// TODO Auto-generated method stub
		return workPlanProgressRepository;
	}
	
	public List<WorkPlanProgress> findByWorkPlanId(int workPlanId){
		return workPlanProgressRepository.findByWorkPlanId(workPlanId);
	}

}
