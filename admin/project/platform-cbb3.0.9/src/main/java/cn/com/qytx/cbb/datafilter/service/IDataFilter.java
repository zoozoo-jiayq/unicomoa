package cn.com.qytx.cbb.datafilter.service;

import java.util.List;

import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.security.domain.DataFilter;

public interface IDataFilter extends BaseService<DataFilter>{

	public List<DataFilter> findByModule(String moduleClass);
}
