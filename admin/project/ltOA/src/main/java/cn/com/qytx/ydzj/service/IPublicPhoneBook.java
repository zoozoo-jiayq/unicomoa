package cn.com.qytx.ydzj.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.qytx.ydzj.domain.PublicPhoneBook;

/**
 * 
 * 功能:公共电话本接口
 * <br/>版本: 1.0
 * <br/>开发人员: 吴洲
 * <br/>创建日期: 2014-9-15
 * <br/>修改日期: 2014-9-15
 * <br/>修改列表:
 */
public interface IPublicPhoneBook  extends Serializable  {

	/**
	 * 
	 * 功能：得到每种电话本类别类别下的电话号码数
	 * <br/>@return
	 */
	public Map<Integer, Integer> getPhoneBookNum();
	
	/**
	 * 
	 * 功能：根据类别id获得公公电话本
	 * <br/>@param categoryId
	 * <br/>@param keyWord
	 * <br/>@return
	 */
	public List<PublicPhoneBook> findBooks(int categoryId,String keyWord);

}
