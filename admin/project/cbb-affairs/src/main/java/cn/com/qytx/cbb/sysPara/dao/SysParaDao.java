package cn.com.qytx.cbb.sysPara.dao;

import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.sysPara.domain.SysPara;
import cn.com.qytx.platform.base.dao.BaseDao;

@Component
public class SysParaDao extends BaseDao<SysPara, Integer>{

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *@Title:根据名称查询实体类
	 *@Description:
	 *@param @param paraName
	 *@param @return
	 *@return  返回类型
	 *@throws
	 */
	public SysPara findParaByName(String paraName) {
		String hql = " paraName = ?1";
		return  this.findOne(hql, paraName);
	}

}
