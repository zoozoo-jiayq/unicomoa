package cn.com.qytx.ydzj.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.ydzj.domain.PublicPhoneBook;
/**
 * 
 * <br/>功能: 公司接口
 * <br/>版本: 1.0
 * <br/>开发人员: REN
 * <br/>创建日期: 2014-9-9
 * <br/>修改日期: 2014-9-9
 * <br/>修改列表:
 */
@Repository("publicPhoneBookDao")
public class PublicPhoneBookDao extends BaseDao<PublicPhoneBook,Integer> implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 功能：得到每种电话本类别类别下的电话号码数
	 * <br/>@return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getPhoneBookNum(){
		String sql = "select categoryId,categoryName,COUNT(*) as phoneCount from PublicPhoneBook group by categoryId, categoryName ";
		Query query = this.entityManager.createQuery(sql);
		List<Object> resultList = query.getResultList();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		if (resultList!=null&&resultList.size()>0) {
			for (Object object : resultList) {
				Object[] obj = (Object[]) object;
				map.put(Integer.parseInt(obj[0].toString()), Integer.parseInt(obj[2].toString()));
			}
		}
		return map;
	}
	/**
	 * 
	 * 功能：根据类别id获得公公电话本
	 * <br/>@param categoryId
	 * <br/>@param keyWord
	 * <br/>@return
	 */
	public List<PublicPhoneBook> findBooks(int categoryId,String keyWord){
		List<PublicPhoneBook> list = new ArrayList<PublicPhoneBook>();
		String jpql = " categoryId=?1 ";
		if (StringUtils.isNotBlank(keyWord)) {
			jpql += " and (name like  '%"+keyWord+"%' or phone like '%"+keyWord+"%') ";
		}
		list = super.findAll(jpql, categoryId);
		return list;
	}
}
