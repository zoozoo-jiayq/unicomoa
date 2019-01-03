package com.unicomoa.unicomoa.workplan;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.unicomoa.unicomoa.base.BaseRepository;
import com.unicomoa.unicomoa.base.BaseServiceProxy;

@Service
@Transactional
public class VisitorMydService extends BaseServiceProxy<VisitorMyd> {

	@Resource
	private VisitorMydRepository visitorMydRepository;
	
	@Override
	protected BaseRepository<VisitorMyd> getBaseRepository() {
		// TODO Auto-generated method stub
		return visitorMydRepository;
	}

	public List<VisitorMyd> findByWorkPlanId(int planId){
		return visitorMydRepository.findByWorkPlanId(planId);
	}
}
