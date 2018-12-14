/**
 * 
 */
package cn.com.qytx.cbb.signcheck.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.qytx.cbb.signcheck.domain.SignCheck;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 
 * 功能:签阅dao类
 * 版本: 1.0
 * 开发人员: 徐长江
 * 创建日期: 2013-3-21
 * 修改日期: 2013-3-21
 * 修改列表:
 */
@Component
public class SignCheckDao extends BaseDao<SignCheck, Integer> implements Serializable{

  /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
/**
   * 
   * 功能：删除签阅
   * @param arg0
   */
	public void delete(int arg0) {
		String hql="update SignCheck set isDelete =1 where checkId = "+arg0;
		this.bulkDelete(hql);
	}
  /**
   * 
   * 功能：阅读记录
   * @param moduleName
   * @param refId
   * @return List
   */
	public List getReadUsersList(String moduleName, Integer refId) {
		String hql="select s from SignCheck s where s.moduleName='"+moduleName+"' and s.refId ='"+refId+"' and s.isDelete=0 order by s.createTime desc";
		return this.find(hql);
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
			int createUserId)
	{
		String hql="select signCheck from SignCheck signCheck where signCheck.moduleName=? and signCheck.refId=? and signCheck.createUserId=? and signCheck.isDelete = 0";
		return (SignCheck)this.findUnique(hql, moduleName,refId,createUserId);
		
	}
}
