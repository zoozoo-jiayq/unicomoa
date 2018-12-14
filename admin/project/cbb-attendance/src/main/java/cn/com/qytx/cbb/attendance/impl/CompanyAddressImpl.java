package cn.com.qytx.cbb.attendance.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.attendance.dao.CompanyAddressDao;
import cn.com.qytx.cbb.attendance.domain.CompanyAddress;
import cn.com.qytx.cbb.attendance.service.ICompanyAddress;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
@Transactional
@Service("companyAddressImpl")
public class CompanyAddressImpl extends BaseServiceImpl<CompanyAddress> implements ICompanyAddress{

	@Autowired
	private CompanyAddressDao companyAddressDao;
	
	@Override
	public CompanyAddress findCompanyAddress(Integer companyId) {
		return companyAddressDao.findCompanyAddress(companyId);
	}
	
}
