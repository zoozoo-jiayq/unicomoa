package cn.com.qytx.cbb.redpoint.service;

/**
 * 功能: 红点信息接口 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月19日
 * 修改日期: 2015年3月19日
 * 修改列表:
 */
public interface RedPointService {

	/**
	 * 功能：获得功能名字
	 * @return
	 */
	public String getName();
	
	/**
	 * 功能：处理aop信息
	 */
	public void dealAspect(Object[] args,String methodName,Integer companyId);
}
