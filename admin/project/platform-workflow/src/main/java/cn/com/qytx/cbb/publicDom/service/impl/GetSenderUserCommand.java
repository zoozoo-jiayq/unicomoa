package cn.com.qytx.cbb.publicDom.service.impl;

import org.hibernate.Session;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.hibernate.DbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：获取制定流程发送节点的处理人
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:31:13 
 * 修改日期：下午4:31:13 
 * 修改列表：
 */
public class GetSenderUserCommand implements Command<UserInfo> {

	private String processInstanceID;
	
	
	public GetSenderUserCommand(String processInstanceID) {
		super();
		this.processInstanceID = processInstanceID;
	}


	@Override
	public UserInfo execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		DbSessionImpl impl = (DbSessionImpl) environment.get(DbSession.class);
		Session session = impl.getSession();
		String sql = "select TASK.ASSIGNEE_ from JBPM4_HIST_TASK TASK,JBPM4_HIST_ACTINST ACT " +
				"WHERE TASK.DBID_ = ACT.HTASK_ AND ACT.ACTIVITY_NAME_ = '发文'  and TASK.EXECUTION_ = ?";
		String userId = (String) session.createSQLQuery(sql).setString(0, processInstanceID).uniqueResult();
		String hql = "from UserInfo u where u.userId = "+userId;
		UserInfo ui = (UserInfo) session.createQuery(hql).uniqueResult();
		return ui;
	}

}
