package cn.com.qytx.cbb.secret.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.secret.domain.SecretProperty;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能: 单位设置可保密信息字段
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年12月23日
 * 修改日期: 2014年12月23日
 * 修改列表:
 */
@Component
public class SecretPropertyDao extends BaseDao<SecretProperty, Serializable> {

	/**
	 * 功能：根据单位id获得单位的人员信息保密字段
	 * @param companyId
	 * @return
	 */
	public List<SecretProperty> allSecretProperty(int companyId){
		return this.findAll(" companyId=? ", companyId);
	}
	
	/**
	 * 功能：删除单位下面所有的保密字段设置
	 * @param companyId
	 */
	public void deleteAll(int companyId){
		this.entityManager.createQuery("delete SecretProperty where companyId = "+companyId+" ").executeUpdate();
	}
}
