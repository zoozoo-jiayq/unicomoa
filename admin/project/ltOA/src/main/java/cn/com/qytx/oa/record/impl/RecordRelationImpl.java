/**
 * 
 */
package cn.com.qytx.oa.record.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.record.dao.RecordRelationDao;
import cn.com.qytx.oa.record.domain.RecordRelation;
import cn.com.qytx.oa.record.service.IRecordRelation;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月20日
 * 修改日期: 2016年12月20日
 * 修改列表: 
 */
@Service
@Transactional
public class RecordRelationImpl extends BaseServiceImpl<RecordRelation> implements IRecordRelation {
  
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1868611580682956233L;
	@Autowired
	private RecordRelationDao recordRelationDao; 
	
	/**
	 * 功能：
	 * @param pageable
	 * @param recordRelation
	 * @return
	 */
	@Override
	public Page<RecordRelation> findList(Pageable pageable,
			RecordRelation recordRelation) {
		// TODO Auto-generated method stub
		return recordRelationDao.findList(pageable, recordRelation);
	}

}
