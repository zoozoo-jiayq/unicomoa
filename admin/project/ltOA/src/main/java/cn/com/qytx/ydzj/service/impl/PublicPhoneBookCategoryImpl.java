package cn.com.qytx.ydzj.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.ydzj.dao.PublicPhoneBookCategoryDao;
import cn.com.qytx.ydzj.domain.PublicPhoneBookCategory;
import cn.com.qytx.ydzj.service.IPublicPhoneBookCategory;
/**
 * 功能:公共电话本类别
 * <br/>版本: 1.0
 * <br/>开发人员: 吴洲
 * <br/>创建日期: 2014-9-15
 * <br/>修改日期: 2014-9-15
 * <br/>修改列表:
 */
@Transactional
@Service("publicPhoneBookCategoryImpl")
public class PublicPhoneBookCategoryImpl  extends BaseServiceImpl<PublicPhoneBookCategory> implements IPublicPhoneBookCategory {
	private static final long serialVersionUID = 1L;
	
	//公共电话本接口
	@Resource(name="publicPhoneBookCategoryDao")
    private PublicPhoneBookCategoryDao publicPhoneBookCategoryDao;
	
	/**
	 * 
	 * 功能：得到所有公共电话本类别
	 * <br/>@return
	 */
	public List<PublicPhoneBookCategory> findAllPhoneBookCategory(){
		return publicPhoneBookCategoryDao.findAll();
	}
	
}
