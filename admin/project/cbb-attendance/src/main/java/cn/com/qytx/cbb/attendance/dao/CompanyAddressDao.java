package cn.com.qytx.cbb.attendance.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.attendance.domain.CompanyAddress;
import cn.com.qytx.platform.base.dao.BaseDao;

@Repository
public class CompanyAddressDao extends BaseDao<CompanyAddress, Serializable>{
	/**
	 * 获取公司地址信息
	 * @Title: findCompanyAddress   
	 * @param companyId
	 * @return
	 */
	public CompanyAddress findCompanyAddress(Integer companyId) {
		String hql=" isDelete=0 and companyId=?";
		List<CompanyAddress> list=super.findAll(hql, companyId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
