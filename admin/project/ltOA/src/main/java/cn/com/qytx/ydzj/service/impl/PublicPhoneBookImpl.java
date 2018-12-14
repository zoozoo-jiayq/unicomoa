package cn.com.qytx.ydzj.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.ydzj.dao.PublicPhoneBookDao;
import cn.com.qytx.ydzj.domain.PublicPhoneBook;
import cn.com.qytx.ydzj.service.IPublicPhoneBook;
/**
 * 功能:公共电话本
 * <br/>版本: 1.0
 * <br/>开发人员: 吴洲
 * <br/>创建日期: 2014-9-15
 * <br/>修改日期: 2014-9-15
 * <br/>修改列表:
 */
@Transactional
@Service("publicPhoneBookImpl")
public class PublicPhoneBookImpl  extends BaseServiceImpl<PublicPhoneBook> implements IPublicPhoneBook {
	private static final long serialVersionUID = 1L;
	
	//公共电话本接口
	@Resource(name="publicPhoneBookDao")
    private PublicPhoneBookDao publicPhoneBookDao;
	
	/**
	 * 
	 * 功能：得到每种电话本类别类别下的电话号码数
	 * <br/>@return
	 */
	public Map<Integer, Integer> getPhoneBookNum(){
		return publicPhoneBookDao.getPhoneBookNum();
	}
	
	/**
	 * 
	 * 功能：根据类别id获得公公电话本
	 * <br/>@param categoryId
	 * <br/>@param keyWord
	 * <br/>@return
	 */
	public List<PublicPhoneBook> findBooks(int categoryId,String keyWord){
		return publicPhoneBookDao.findBooks(categoryId, keyWord);
	}
	
}
