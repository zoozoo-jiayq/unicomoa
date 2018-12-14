package cn.com.qytx.cbb.jbpmApp.service.impl.command;

import java.util.List;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 功能：查询经我处理的任务,为手机端开发的命令对象
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:36:20 
 * 修改日期：上午11:36:20 
 * 修改列表：
 */
public class SearchMyApprovedTaskListForMobile implements Command<List<String>> {

	private String	userId;
	private Pageable	page;	
	
	public SearchMyApprovedTaskListForMobile(String userId, Pageable page) {
		super();
		this.userId = userId;
		this.page = page;
	}

	@Override
	public List<String> execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		IUser userService = (IUser) SpringUtil.getBean("userService");
		List<UserInfo> us = userService.findAll();
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		String countSql = "select COUNT(*) as c from (select distinct(a.EXECUTION_) from JBPM4_HIST_TASK a,JBPM4_HIST_ACTINST b " +
				"where a.DBID_ = b.HTASK_ and ASSIGNEE_ = '"+userId+"' and STATE_ ='completed' and ACTIVITY_NAME_ != '发起流程') v";
		String listSql = "select tv.instanceId " +
				"from (select a.EXECUTION_ instanceId,MAX(a.END_) endTime,MAX(a.DBID_) htask from JBPM4_HIST_TASK a,JBPM4_HIST_ACTINST b " +
				"where a.DBID_ = b.HTASK_ and ASSIGNEE_ = '"+userId+"' and STATE_ ='completed' and ACTIVITY_NAME_ != '发起流程'  " +
				"GROUP BY A.EXECUTION_) v,tb_cbb_workflow_var tv where v.instanceId = tv.instanceId order by v.endTime desc";
		
		int count=(Integer) entityManager.createNativeQuery(countSql).getSingleResult();
		
		/*page.setTotalCount(count);
		int currentPage = page.getPageNo()-1;*/
		int currentPage = page.getPageNumber()-1;
		int beginNum = currentPage*page.getPageSize();
		
		List<String> list = entityManager.createNativeQuery(listSql).setFirstResult(beginNum)
				.setMaxResults(page.getPageSize()).getResultList();
		return list;
	}


}
