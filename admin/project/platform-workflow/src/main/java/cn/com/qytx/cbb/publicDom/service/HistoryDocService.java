package cn.com.qytx.cbb.publicDom.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.cbb.publicDom.domain.HistoryDoc;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能：历史正文服务
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:15:02 
 * 修改日期：上午10:15:02 
 * 修改列表：
 */
public interface HistoryDocService extends BaseService<HistoryDoc> , Serializable{

	public List<HistoryDoc> findByInstanceId(String instanceId);
}
