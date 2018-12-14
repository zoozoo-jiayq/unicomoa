package cn.com.qytx.cbb.jbpmApp.service.impl;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Environment;
import org.jbpm.api.cmd.VoidCommand;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

/**
 * 功能：删除历史任务
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:49:11 
 * 修改日期：上午11:49:11 
 * 修改列表：
 */
public class DeleteInstanceCmd extends VoidCommand {

	private String histProcessInstance;
	
	public DeleteInstanceCmd(String histProcessInstance) {
		super();
		this.histProcessInstance = histProcessInstance;
	}

	@Override
	protected void executeVoid(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment
				.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		
		String deleteVarSql = "delete from JBPM4_HIST_VAR where PROCINSTID_ = ?";
		String deleteActinstSql = "delete from JBPM4_HIST_ACTINST where EXECUTION_ = ?";
		String deleteInstanceSql = "delete from JBPM4_HIST_PROCINST WHERE ID_ = ?";
		String deleteTaskSql = "DELETE FROM JBPM4_HIST_TASK WHERE EXECUTION_ = ?";
		entityManager.createNativeQuery(deleteVarSql).setParameter(1, histProcessInstance).executeUpdate();
		entityManager.createNativeQuery(deleteActinstSql).setParameter(1, histProcessInstance).executeUpdate();
		entityManager.createNativeQuery(deleteInstanceSql).setParameter(1, histProcessInstance).executeUpdate();
		entityManager.createNativeQuery(deleteTaskSql).setParameter(1, histProcessInstance).executeUpdate();
	}
}
