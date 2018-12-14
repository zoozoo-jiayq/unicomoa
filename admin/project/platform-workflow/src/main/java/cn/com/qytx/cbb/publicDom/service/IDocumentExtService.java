package cn.com.qytx.cbb.publicDom.service;

import java.io.Serializable;

import cn.com.qytx.cbb.publicDom.domain.DocumentExt;
import cn.com.qytx.platform.base.service.BaseService;


/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 陈秋利
 * 创建日期: 2013-4-15
 * 修改日期: 2013-4-15
 * 修改列表: 公文管理扩展字段
 */
public interface IDocumentExtService extends BaseService<DocumentExt>,Serializable{

	/**
	 * 功能：查询某个公务类别下的公文数
	 * @param doctypeId
	 * @return
	 */
	int getDocTypeCount(Integer doctypeId);

	/**
	 * 功能：根据流程实例ID获取扩张字段
	 * @param processInstanceId
	 * @return
	 */
	DocumentExt findByProcessInstanceId(String processInstanceId);

}
