package cn.com.qytx.cbb.jbpmApp.service.impl.command;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

/**
 * 功能  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月8日
 * <br/>修改日期  2016年1月8日
 * <br/>修改列表
 */
@SuppressWarnings("serial")
public class GetSuperTaskCommand implements Command<String> {

	private String joinTaskId;
	
	
	public GetSuperTaskCommand(String joinTaskId) {
		super();
		this.joinTaskId = joinTaskId;
	}


	@Override
	public String execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		String sql = "select j.SUPERTASK_ FROM JBPM4_TASK j WHERE j.DBID_ = ?1 ";
		Object  o = entityManager.createNativeQuery(sql).setParameter(1, joinTaskId).getResultList().get(0);
		if(o==null){
			return null;
		}else{
			return o.toString();
		}
	}
}
