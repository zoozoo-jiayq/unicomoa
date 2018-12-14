/**
 * 
 */
package cn.com.qytx.oa.record.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.record.dao.RecordWorkDao;
import cn.com.qytx.oa.record.domain.RecordWork;
import cn.com.qytx.oa.record.service.IRecordWork;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月19日
 * 修改日期: 2016年12月19日
 * 修改列表: 
 */
@Service
@Transactional
public class RecordWorkImpl extends BaseServiceImpl<RecordWork> implements IRecordWork {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 4889119643953867909L;
 
	@Autowired
	private RecordWorkDao recordWorkDao;
	
	/**
	 * 功能：
	 * @param pageable
	 * @param recordWork
	 * @return
	 */
	@Override
	public Page<RecordWork> findList(Pageable pageable, RecordWork recordWork) {
		// TODO Auto-generated method stub
		return recordWorkDao.findList(pageable, recordWork);
	}

}
