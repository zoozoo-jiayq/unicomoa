package cn.com.qytx.oa.record.dao;

import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.record.domain.RecordTraining;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能：培训dao
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2016年12月21日
 * 修改日期：2016年12月21日	
 */
@Repository("recordTrainingDao")
public class RecordTrainingDao extends BaseDao<RecordTraining, Integer>{

	public Page<RecordTraining> findRecordTrainingPage(Pageable page,
			Integer userId) {
		String hql = " isDelete=0 and userInfo.userId="+userId;
		return super.findAll(hql, page);
	}

}
