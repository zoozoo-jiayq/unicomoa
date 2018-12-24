package com.unicomoa.unicomoa.workplan;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unicomoa.unicomoa.base.BaseRepository;

@Repository
public interface WorkPlanProgressRepository extends BaseRepository<WorkPlanProgress> {

	public List<WorkPlanProgress> findByWorkPlanId(int workPlanId); 
}
