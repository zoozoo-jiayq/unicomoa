package cn.com.qytx.cbb.datafilter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.datafilter.dao.DataFilterDao;
import cn.com.qytx.cbb.datafilter.service.IDataFilter;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.security.domain.DataFilter;

@Service
@Transactional
public class DataFilterImpl extends BaseServiceImpl<DataFilter> implements IDataFilter {

	@Resource
	private DataFilterDao dataFilterDao;
	
	@Override
	public List<DataFilter> findByModule(String moduleClass) {
		// TODO Auto-generated method stub
		return dataFilterDao.findByModule(moduleClass);
	}

}
