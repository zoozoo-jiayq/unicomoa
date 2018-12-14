package cn.com.qytx.cbb.attendance.service;

import cn.com.qytx.cbb.attendance.domain.CompanyAddress;
import cn.com.qytx.platform.base.service.BaseService;
/**
 * 公司地址设置接口
 * @ClassName: ICompanyAddress   
 * @author: WANG
 * @Date: 2016年6月2日 下午2:45:52   
 *
 */
public interface ICompanyAddress extends BaseService<CompanyAddress>{
	/**
	 * 获取公司地址信息
	 * @Title: findCompanyAddress   
	 * @param companyId
	 * @return
	 */
	public CompanyAddress findCompanyAddress(Integer companyId);
}
