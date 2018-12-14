package cn.com.qytx.cbb.signcheck.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.cbb.signcheck.domain.SignCheck;
import cn.com.qytx.platform.base.service.BaseService;
/**
 * 
 * 功能:签阅接口
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */

public interface ISignCheck extends BaseService<SignCheck>,Serializable {
	/**
	 * 
	 * 功能：阅读记录
	 * @param moduleName
	 * @param refId
	 * @return List<SignCheck>
	 */
	public List<SignCheck> getReadUsersList(String moduleName,Integer refId);
   /**
    * 
    * 功能：获取签阅记录
    * @param moduleName
    * @param refId
    * @param createUserId
    * @return SignCheck
    */
	public SignCheck getReadUserYnCheck(String moduleName,Integer refId,int createUserId);
	
}