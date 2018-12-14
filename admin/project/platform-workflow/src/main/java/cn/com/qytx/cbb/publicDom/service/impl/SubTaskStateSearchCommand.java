package cn.com.qytx.cbb.publicDom.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

import cn.com.qytx.cbb.publicDom.vo.ReadStateView;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 功能：查询子任务的状态
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-24 下午3:19:20 
 * 修改日期：2013-4-24 下午3:19:20 
 * 修改列表：
 */
public class SubTaskStateSearchCommand implements Command<List<ReadStateView>> {

	private String instanceId;
	
	/**
	 * @param taskID
	 */
	public SubTaskStateSearchCommand(String instanceId) {
		super();
		this.instanceId = instanceId;
	}

	/**
	 * 功能：
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public List<ReadStateView> execute(Environment environment)
			throws Exception {
		IGroup groupService = (IGroup) SpringUtil.getBean("groupService");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		IUser userService = (IUser) SpringUtil.getBean("userService");
		JpaDbSessionImpl dbSessionImpl = (JpaDbSessionImpl) environment.get(DbSession.class); 
		EntityManager session = dbSessionImpl.getEntityManager();
		String sql = "select STATE_,ASSIGNEE_,END_ from JBPM4_HIST_TASK WHERE EXECUTION_ = ? and  MY_SUPER_TASK IS NOT NULL ";
		List<Object[]> list = session.createNativeQuery(sql).setParameter(1, instanceId).getResultList();
		List<ReadStateView> viewList = new ArrayList<ReadStateView>();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Object[] os = list.get(i);
				String state = (String) os[0];
				String userId = (String) os[1];
				Timestamp endTime = (Timestamp) os[2];
				UserInfo ui = userService.findOne(Integer.parseInt(userId));
				String userName = ui.getUserName();
				ReadStateView view = new ReadStateView();
				if(state==null){
					state = "未阅读";
					view.setReadTime("--");
				}else if(state.equals(HistoryTask.STATE_COMPLETED)){
					//state = "<font color='green'>已阅读</font>";
					state = "<font >已阅读</font>";
					view.setReadTime(sdf.format(endTime));
				}
				view.setState(state);
				view.setUserName(userName);
				GroupInfo g = groupService.findOne(ui.getGroupId());
				view.setGroupName("");
				if(g!=null){
					view.setGroupName(g.getGroupName());
				}
				String role ="";
				role = ui.getJob()!=null?ui.getJob():"&nbsp;";
				view.setRole(role);
				viewList.add(view);
			}
		}
		return viewList;
	}

}
