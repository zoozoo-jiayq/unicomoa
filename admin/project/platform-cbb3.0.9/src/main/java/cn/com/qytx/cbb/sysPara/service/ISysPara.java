package cn.com.qytx.cbb.sysPara.service;

import cn.com.qytx.cbb.sysPara.domain.SysPara;
import cn.com.qytx.platform.base.service.BaseService;

public interface ISysPara extends BaseService<SysPara>  {
	
	/**
	 *@Title:根据名称查询
	 *@Description:
	 *@param @param paraName
	 *@param @return
	 *@return  返回类型
	 *@throws
	 */
	SysPara findParaByName(String paraName);
}
