package cn.com.qytx.ydzj.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.ydzj.domain.PublicPhoneBookCategory;
/**
 * 
 * 功能:公共电话本类别dao
 * <br/>版本: 1.0
 * <br/>开发人员: 吴洲
 * <br/>创建日期: 2014-9-15
 * <br/>修改日期: 2014-9-15
 * <br/>修改列表:
 */
@Repository("publicPhoneBookCategoryDao")
public class PublicPhoneBookCategoryDao extends BaseDao<PublicPhoneBookCategory,Integer> implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 功能：得到所有公共电话本类别
	 * <br/>@return
	 */
	public List<PublicPhoneBookCategory> findAllPhoneBookCategory(){
		List<PublicPhoneBookCategory> list = this.findAll();
		return list;
	}
}
