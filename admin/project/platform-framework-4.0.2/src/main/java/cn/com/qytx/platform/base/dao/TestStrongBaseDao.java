package cn.com.qytx.platform.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * StrongBaseDao.java测试类，case1,2,3测试单表查询（和BaseDao功能相同）；
 * case4,5,6测试多表查询，重点关注后者,多表关联查询支持返回多种数据结构。
 * @author jiayq
 *
 */
@Component
public class TestStrongBaseDao extends StrongBaseDao<UserInfo, Integer> {

	/**
	 * 用例1，单表，不带参数，带权限
	 * @return
	 */
	public List<UserInfo> case1(){
		String hql = "from UserInfo ";
		Order o = new Order(Direction.ASC,"userId");
		Sort sort = new Sort(o);
		// from UserInfo where UserInfo.companyId =: companyId and partitonCompanyId=:partitonCompanyId and ();
		List<UserInfo> ulist = this.companyId().dataFilter("test").sfindAll(hql, sort);
		return ulist;
	}
	
	/**
	 * 用例2,单表，带参数，带权限
	 * @return
	 */
	public List<UserInfo> case2(String userName){
		String hql = "from UserInfo  where userName  like ?";
		Order o = new Order(Direction.ASC,"userId");
		Sort sort = new Sort(o);
		List<UserInfo> ulist = this.companyId().dataFilter("test").sfindAll(hql, sort, "%"+userName+"%");
		return ulist;
	}
	
	/**
	 * 用例3,单表，带参数，分页查询
	 * @param userName
	 * @param page
	 * @return
	 */
	public Page<UserInfo> case3(String userName,Pageable page){
		String hql = "from UserInfo where userName like ?";
		return this.companyId().dataFilter("test").sfindByPage(hql, page, "%"+userName+"%");
	}
	
	/**
	 * 用例4,2个表关联，不带参数，带权限
	 * @return
	 */
	public List<Object[]> case4(){
		String hql = "select u.userId,g.groupName from UserInfo u,GroupInfo g where u.groupId = g.groupId";
		return this.companyId().dataFilter("test").sfindAll(hql, null);
	}
	
	/**
	 * 用例5，2个表关联，带参数，带权限
	 * @param groupId
	 * @return
	 */
	public List<Map<String,Object>> case5(int groupId){
		String hql = "select new map(u.userId as userId,g.groupName as groupName) from UserInfo u,GroupInfo g where u.groupId = g.groupId and g.groupId = ?";
		return this.companyId().dataFilter("test").sfindAll(hql, null, groupId);
	}
	
	/**2个表关联，分页查询，带权限
	 * @param groupId
	 * @param page
	 * @return
	 */
	public Page<UserInfo> case6(int groupId,Pageable page){
		String hql = "select u from UserInfo u,GroupInfo g where u.groupId = g.groupId and g.groupId = ?";
		return this.companyId().dataFilter("test").sfindByPage(hql, page, groupId);
	}
}
