package cn.com.qytx.platform.org.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.service.ICompany;

/**
 * 单位信息接口实现类
 * @param <T>
 */
@Transactional
@Service("companyService")
public class CompanyImpl  extends BaseServiceImpl<CompanyInfo> implements ICompany {
  
	@Override
	@CacheEvict(value="userTreeCache",allEntries=true)
	public CompanyInfo saveOrUpdate(CompanyInfo companyInfo){
		return super.saveOrUpdate(companyInfo);
	}
}
