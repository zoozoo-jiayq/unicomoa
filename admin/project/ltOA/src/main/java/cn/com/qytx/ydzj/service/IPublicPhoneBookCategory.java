package cn.com.qytx.ydzj.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.ydzj.domain.PublicPhoneBookCategory;

/**
 * 
 * 功能:公共电话本接口
 * <br/>版本: 1.0
 * <br/>开发人员: 吴洲
 * <br/>创建日期: 2014-9-15
 * <br/>修改日期: 2014-9-15
 * <br/>修改列表:
 */
public interface IPublicPhoneBookCategory  extends Serializable  {

	/**
	 * 
	 * 功能：得到所有公共电话本类别
	 * <br/>@return
	 */
	public List<PublicPhoneBookCategory> findAllPhoneBookCategory();

}
