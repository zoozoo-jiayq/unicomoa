package cn.com.qytx.oa.record.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.record.dao.RecordTrainingDao;
import cn.com.qytx.oa.record.domain.RecordTraining;
import cn.com.qytx.oa.record.service.IRecordTraining;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("recordTrainingImpl")
@Transactional
public class RecordTrainingImpl extends BaseServiceImpl<RecordTraining> implements IRecordTraining {
	private static final long serialVersionUID = -445554977441563704L;
	
	@Resource(name="recordTrainingDao")
	private RecordTrainingDao recordTrainingDao;
	
	@Override
	public Page<RecordTraining> findRecordTrainingPage(Pageable page,
			Integer userId) {
		return recordTrainingDao.findRecordTrainingPage(page,userId);
	}



}
