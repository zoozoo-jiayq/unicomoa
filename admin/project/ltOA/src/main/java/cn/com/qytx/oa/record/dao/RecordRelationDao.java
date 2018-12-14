/**
 * 
 */
package cn.com.qytx.oa.record.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.record.domain.RecordRelation;
import cn.com.qytx.oa.record.domain.RecordWork;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能:人员档案-关系dao
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月20日
 * 修改日期: 2016年12月20日
 * 修改列表: 
 */
@Repository
public class RecordRelationDao extends BaseDao<RecordRelation, Integer>implements Serializable {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 875598477167527069L;
	
	/**
	 * 
	 * 功能：分页查询工作信息
	 * @return
	 */
	public Page<RecordRelation> findList(Pageable pageable,RecordRelation recordRelation){
		
		String hql=" select r from RecordRelation r where r.isDelete=0";
		if(recordRelation.getUserInfo()!=null){
			hql +=" and r.userInfo.userId="+recordRelation.getUserInfo().getUserId();
		}
		if(recordRelation.getCompanyId()!=null){
			hql +=" and r.companyId="+recordRelation.getCompanyId();
		}
		hql +=" order by createTime desc";
		return super.findByPage(pageable, hql);
		
	}

}
