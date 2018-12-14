package cn.com.qytx.cbb.jbpmApp.service.impl.command;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

/**判断流程是否可以被删除，只有当除了发起人外没其他人处理的时候才可以删除
 * @author Administrator
 *
 */
public class JudgmentInstanceCanDelete implements Command<Boolean> {

	private String instanceId;
	
	public JudgmentInstanceCanDelete(String instanceId) {
		super();
		this.instanceId = instanceId;
	}

	@Override
	public Boolean execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		String sql = "select COUNT(DBID_)as c FROM JBPM4_HIST_TASK where EXECUTION_ = ?1 and STATE_ = 'completed'";
		int result = (Integer)  entityManager.createNativeQuery(sql).setParameter(1, instanceId).getSingleResult();
		if(result>1){
			return false;
		}
		return true;
	}

}
