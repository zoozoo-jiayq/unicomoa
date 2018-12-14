package cn.com.qytx.cbb.publicDom.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.publicDom.domain.HistoryDoc;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;


/**
 * 功能：正文修改历史DAO
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午9:46:03 
 * 修改日期：上午9:46:03 
 * 修改列表：
 */
@Repository("historyDocDao")
public class HistoryDocDao extends BaseDao<HistoryDoc, Integer>  implements Serializable{
	
	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 功能：根据流程实例ID查询所有的历史正文
	 * @param
	 * @return
	 * @throws   
	 */
	public List<HistoryDoc> findByInstanceId(String instanceId){
		String hql = " instanceId = ? ";
		Order o = new Order(Direction.DESC, "updateTime");
		Sort sort = new Sort(o);
		return super.findAll(hql,sort, instanceId);
	}
	
}