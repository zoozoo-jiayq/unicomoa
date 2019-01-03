package com.unicomoa.unicomoa.workplan;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unicomoa.unicomoa.base.BaseRepository;

@Repository
public interface VisitorMydRepository extends BaseRepository<VisitorMyd> {

	public List<VisitorMyd> findByWorkPlanId(int workPlanId);
}
