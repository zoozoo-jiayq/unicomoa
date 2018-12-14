/**
 * 
 */
package cn.com.qytx.oa.record.service;

import java.io.Serializable;

import cn.com.qytx.oa.record.domain.RecordWork;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月19日
 * 修改日期: 2016年12月19日
 * 修改列表: 
 */
public interface IRecordWork extends BaseService<RecordWork>,Serializable {
	/**
	 * 
	 * 功能：分页查询工作信息
	 * @return
	 */
	public Page<RecordWork> findList(Pageable pageable,RecordWork recordWork);
		
}
