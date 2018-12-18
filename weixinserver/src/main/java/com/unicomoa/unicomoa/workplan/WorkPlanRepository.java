package com.unicomoa.unicomoa.workplan;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.unicomoa.unicomoa.base.BaseRepository;

@Repository
public interface WorkPlanRepository extends BaseRepository<WorkPlan> {

	public List<WorkPlan> findByCreaterIdAndDayStr(int createrId,String dayStr,Sort sort);
}
