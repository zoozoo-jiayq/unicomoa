package cn.com.qytx.oa.record.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.record.dao.RecordTitleDao;
import cn.com.qytx.oa.record.domain.RecordTitle;
import cn.com.qytx.oa.record.service.IRecordTitle;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("recordTitleImpl")
@Transactional
public class RecordTitleImpl extends BaseServiceImpl<RecordTitle> implements IRecordTitle {
	private static final long serialVersionUID = -445554977441563704L;
	
	@Resource(name="recordTitleDao")
	private RecordTitleDao recordTitleDao;
	
	@Override
	public Page<RecordTitle> findRecordTitlePage(Pageable page,
			Integer userId) {
		return recordTitleDao.findRecordTitlePage(page,userId);
	}



}
