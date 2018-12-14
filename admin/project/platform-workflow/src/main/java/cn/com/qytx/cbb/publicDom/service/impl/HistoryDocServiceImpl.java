package cn.com.qytx.cbb.publicDom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.publicDom.dao.HistoryDocDao;
import cn.com.qytx.cbb.publicDom.domain.HistoryDoc;
import cn.com.qytx.cbb.publicDom.service.HistoryDocService;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:15:55 
 * 修改日期：上午10:15:55 
 * 修改列表：
 */
@Service("historyDocService")
@Transactional
public class HistoryDocServiceImpl extends BaseServiceImpl<HistoryDoc> implements HistoryDocService {

	@Resource
	private HistoryDocDao historyDocDao;
	
	

	@Override
	public List<HistoryDoc> findByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		return historyDocDao.findByInstanceId(instanceId);
	}

}
