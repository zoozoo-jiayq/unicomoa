/**
 * 
 */
package cn.com.qytx.oa.record.service;

import java.io.Serializable;

import cn.com.qytx.oa.record.domain.RecordRelation;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月20日
 * 修改日期: 2016年12月20日
 * 修改列表: 
 */
public interface IRecordRelation extends BaseService<RecordRelation>,Serializable{
      
	/**
	 * 
	 * 功能：分页查关系信息
	 * @return
	 */
	public Page<RecordRelation> findList(Pageable pageable,RecordRelation recordRelation);
}
