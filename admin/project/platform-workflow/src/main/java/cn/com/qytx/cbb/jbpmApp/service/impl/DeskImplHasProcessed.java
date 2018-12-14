package cn.com.qytx.cbb.jbpmApp.service.impl;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能  菜单模块 显示模块名字和经我审批的数量  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月8日
 * <br/>修改日期  2016年1月8日
 * <br/>修改列表
 */
@Service("deskImplHasProcessed")
@Transactional
public class DeskImplHasProcessed extends BaseDeskImpl {

	@Override
	public int countOfTask(int userId) {
		// TODO Auto-generated method stub
		return getProcessEngin().execute(new HasStartedCount(getLoginNameByUserId(userId))); 
	}

	@Override
	public String getTaskUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "经我审批";
	}

}

class HasStartedCount implements Command<Integer>{

	private String userId;
	
	public HasStartedCount(String userId) {
		super();
		this.userId = userId;
	}

	@Override
	public Integer execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment
				.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		String sql = "select COUNT(jht.DBID_)as c from JBPM4_HIST_TASK jht,JBPM4_HIST_ACTINST jha where jha.HTASK_ = jht.DBID_ and jht.ASSIGNEE_ = ? and jha.ACTIVITY_NAME_ != '发起流程' and jht.STATE_ = 'completed'";
		Integer count = (Integer)entityManager.createNativeQuery(sql).setParameter(1, userId).getSingleResult();
		return count;
	}
	
}

