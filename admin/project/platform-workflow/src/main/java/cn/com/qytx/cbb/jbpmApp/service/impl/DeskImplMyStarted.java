package cn.com.qytx.cbb.jbpmApp.service.impl;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能：我发起的桌面实现
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:02:43 
 * 修改日期：上午10:02:43 
 * 修改列表：
 */
@Service("deskImplMyStarted")
@Transactional
public class DeskImplMyStarted extends BaseDeskImpl {

	@Override
	public int countOfTask(int userId) {
		// TODO Auto-generated method stub
		
		return getProcessEngin().execute(new MyStartedCount(getLoginNameByUserId(userId))); 
	}

	@Override
	public String getTaskUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "申请列表";
	}


}

	class MyStartedCount implements Command<Integer>{

		private String userId;
		
		public MyStartedCount(String userId) {
			super();
			this.userId = userId;
		}

		@Override
		public Integer execute(Environment environment) throws Exception {
			// TODO Auto-generated method stub
			JpaDbSessionImpl impl = (JpaDbSessionImpl) environment
					.get(DbSession.class);
			EntityManager entityManager = impl.getEntityManager();
			String sql = "select COUNT(*)as c from JBPM4_HIST_PROCINST " +
					"WHERE STATE_ = 'active' AND ID_ IN " +
					"(select jht.EXECUTION_ from JBPM4_HIST_TASK jht,JBPM4_HIST_ACTINST jha " +
					"where  jha.HTASK_ = jht.DBID_ and jht.ASSIGNEE_ = ? and jha.ACTIVITY_NAME_ = '发起流程' and jht.STATE_ = 'completed')";
			Integer count = Integer.valueOf(entityManager.createNativeQuery(sql).setParameter(1, userId).getSingleResult().toString());
			return count;
		}
		
	}
