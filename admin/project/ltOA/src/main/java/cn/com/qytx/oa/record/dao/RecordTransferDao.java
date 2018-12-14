package cn.com.qytx.oa.record.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.record.domain.RecordTransfer;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

@Repository("recordTransferDao")
public class RecordTransferDao extends BaseDao<RecordTransfer, Integer> implements Serializable{
	
	private static final long serialVersionUID = 5656121369831849806L;

	public Page<RecordTransfer> findRecordTransferPage(Pageable page,
			Integer userId) {
		String hql = " isDelete=0 and userInfo.userId="+userId;
		return super.findAll(hql, page);
	}
	
	
	public void updateTransferLast(Integer companyId){
		String hql = "update RecordTransfer set isLast=0 where companyId="+companyId;
		super.executeQuery(hql);
	}
	

}
