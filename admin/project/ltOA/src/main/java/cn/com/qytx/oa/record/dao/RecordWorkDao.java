/**
 * 
 */
package cn.com.qytx.oa.record.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.record.domain.RecordWork;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月19日
 * 修改日期: 2016年12月19日
 * 修改列表: 
 */
@Repository
public class RecordWorkDao extends BaseDao<RecordWork, Integer>implements Serializable{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = -3497873271174481591L;
	
	/**
	 * 
	 * 功能：分页查询工作信息
	 * @return
	 */
	public Page<RecordWork> findList(Pageable pageable,RecordWork recordWork){
		
		String hql=" select r from RecordWork r where r.isDelete=0";
		if(recordWork.getUserInfo()!=null){
			hql +=" and r.userInfo.userId="+recordWork.getUserInfo().getUserId();
		}
		if(recordWork.getCompanyId()!=null){
			hql +=" and r.companyId="+recordWork.getCompanyId();
		}
		hql +=" order by createTime desc";
		return super.findByPage(pageable, hql);
		
	}
    
}
