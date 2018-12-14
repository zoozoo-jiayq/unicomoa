package cn.com.qytx.cbb.sysPara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.sysPara.dao.SysParaDao;
import cn.com.qytx.cbb.sysPara.domain.SysPara;
import cn.com.qytx.cbb.sysPara.service.ISysPara;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service
@Transactional
public class SysParaImpl extends BaseServiceImpl<SysPara> implements ISysPara {

	@Autowired
	private SysParaDao sysParaDao;
	


	/**
	 *@Title:根据名称查询实体类
	 *@Description:
	 *@param @param paraName
	 *@param @return
	 *@return  返回类型
	 *@throws
	 */
	public SysPara findParaByName(String paraName) {
		return sysParaDao.findParaByName(paraName);
	}
	
	/**
	 * @return the sysParaDao
	 */
	public SysParaDao getSysParaDao() {
		return sysParaDao;
	}

	/**
	 * @param sysParaDao the sysParaDao to set
	 */
	public void setSysParaDao(SysParaDao sysParaDao) {
		this.sysParaDao = sysParaDao;
	}
}
