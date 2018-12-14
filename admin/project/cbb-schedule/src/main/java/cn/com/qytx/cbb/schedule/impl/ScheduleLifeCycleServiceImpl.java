package cn.com.qytx.cbb.schedule.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.schedule.dao.ScheduleLifeCycleDao;
import cn.com.qytx.cbb.schedule.domain.ScheduleLifeCycle;
import cn.com.qytx.cbb.schedule.service.IScheduleLifeCycleService;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 
 * @author lilipo
 *
 */
@Transactional
@Service("scheduleLifeCycleService")
public class ScheduleLifeCycleServiceImpl extends BaseServiceImpl<ScheduleLifeCycle> implements IScheduleLifeCycleService{

	@Resource(name="scheduleLifeCycleDao")
	private ScheduleLifeCycleDao slcDao;

}
