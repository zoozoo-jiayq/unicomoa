package cn.com.qytx.cbb.signcheck.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.signcheck.dao.SignCheckDao;
import cn.com.qytx.cbb.signcheck.domain.SignCheck;
import cn.com.qytx.cbb.signcheck.service.ISignCheck;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

/**
 * 
 * 功能:签阅接口的实现类
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Service
@Transactional
public class SignCheckImpl extends BaseServiceImpl<SignCheck> implements ISignCheck {
	/**短信发送主表dao */
	@Resource(name="signCheckDao")
    SignCheckDao  signCheckDao;

  /**
   * 
   * 功能：添加签阅记录
   * @param arg0
   */
	public void save(SignCheck arg0) {

		signCheckDao.saveOrUpdate(arg0);

	}
   /**
    * 
    * 功能：添加或修改签阅记录
    * @param arg0
    */
	public void updateOrSave(SignCheck arg0) {
		signCheckDao.saveOrUpdate(arg0);
	}
  /**
   * 
   * 功能：删除签阅记录
   * @param arg0
   */
	public void delete(int arg0) {
		signCheckDao.delete(arg0);
	}
	
	/**
	 * 
	 * 功能：阅读记录
	 * @param moduleName
	 * @param refId
	 * @return List
	 */
	public List getReadUsersList(String moduleName, Integer refId) {
		return signCheckDao.getReadUsersList(moduleName, refId);
	}
   /**
    * 
    * 功能：获取签阅记录
    * @param moduleName
    * @param refId
    * @param createUserId
    * @return SignCheck
    */
	public SignCheck getReadUserYnCheck(String moduleName, Integer refId,
			int createUserId) {
		
		return signCheckDao.getReadUserYnCheck(moduleName, refId, createUserId);
	}
}