package cn.com.qytx.oa.record.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.record.domain.RecordTitle;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

@Repository("recordTitleDao")
public class RecordTitleDao extends BaseDao<RecordTitle, Integer> implements Serializable{
	
	private static final long serialVersionUID = 5656121369831849806L;

	public Page<RecordTitle> findRecordTitlePage(Pageable page,
			Integer userId) {
		String hql = " isDelete=0 and userInfo.userId="+userId;
		return super.findAll(hql, page);
	}
}
