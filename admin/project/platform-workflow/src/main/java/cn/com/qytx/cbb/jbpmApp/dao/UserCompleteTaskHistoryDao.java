package cn.com.qytx.cbb.jbpmApp.dao;

import org.springframework.stereotype.Repository;

import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 上次建立的工作DAO
 * @版本：1.0  
 * @开发人员：陈秋利   
 * @创建日期：2013-3-21 
 * @修改日期：
 */
@Repository("userCompleteTaskHistoryDao")
public class UserCompleteTaskHistoryDao extends BaseDao<String, Integer> {

	/**
	 * 功能：查询流程历史记录
	 * @param page
	 * @param userId
	 * @return
	 */
	public Page<Object> findCompletePage(Pageable page, String userId) {
		String sql="select  JHT.EXECUTION_ from JBPM4_HIST_ACTINST JHA,JBPM4_HIST_TASK JHT WHERE" +
				         " JHA.HTASK_ = JHT.DBID_ AND STATE_ = 'completed' " +
				         "AND ASSIGNEE_ = '"+userId+"'  " +
				         "and JHA.ACTIVITY_NAME_ != '发起流程' order by JHA.END_ DESC";
		return this.createPageQuery(page, sql);
	}

 
}
