package cn.com.qytx.cbb.jbpmApp.service.impl;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能  待我审批的桌面实现  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月8日
 * <br/>修改日期  2016年1月8日
 * <br/>修改列表
 */
@Service("deskImplProcessing")
@Transactional
public class DeskImplProcessing extends BaseDeskImpl {

	@Override
	public int countOfTask(int userId) {
		// TODO Auto-generated method stub
		return getProcessEngin().execute(new Processing(getLoginNameByUserId(userId)));
	}

	@Override
	public String getTaskUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "待我审批";
	}

}

class Processing implements Command<Integer>{

	private String userId;
	
	public Processing(String userId) {
		super();
		this.userId = userId;
	}

	@Override
	public Integer execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment
				.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		String sql = "select count(*)as c from JBPM4_TASK where ASSIGNEE_ = ? and STATE_ = 'open'";
		Integer count =(Integer)entityManager.createNativeQuery(sql).setParameter(1, userId).getSingleResult();
		return count;
	}
	
}
