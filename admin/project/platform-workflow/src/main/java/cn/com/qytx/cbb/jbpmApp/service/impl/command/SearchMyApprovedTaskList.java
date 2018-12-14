package cn.com.qytx.cbb.jbpmApp.service.impl.command;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

import cn.com.qytx.cbb.jbpmApp.domain.WorkFlowView;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;

/**
 * 功能：查询经我处理的任务 版本: 1.0 开发人员：贾永强 创建日期: 上午11:36:20 修改日期：上午11:36:20 修改列表：
 */
public class SearchMyApprovedTaskList implements Command<Page<WorkFlowView>> {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private transient Pageable page;
	private String searchkey;

	public SearchMyApprovedTaskList(String userId,String searchkey, Pageable page) {
		super();
		this.userId = userId;
		this.page = page;
		this.searchkey = searchkey;
		if(this.searchkey == null){
			this.searchkey = "";
		}
	}

	@Override
	public Page<WorkFlowView> execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		IUser userService = (IUser) SpringUtil.getBean("userService");
		List<UserInfo> us = userService.findAll();
		List<WorkFlowView> l = new ArrayList<WorkFlowView>();
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment
				.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		searchkey = searchkey.replace("[", "[[]");
		String candition = " and (tv.title like '%"+searchkey+"%' or tv.refprocess like '%"+searchkey+"%')";
		String countSql = "select COUNT(*) as c from (select distinct(a.EXECUTION_) from JBPM4_HIST_TASK a,JBPM4_HIST_ACTINST b ,tb_cbb_workflow_var tv "
				+ "where a.DBID_ = b.HTASK_ and a.EXECUTION_ = tv.instanceid and ASSIGNEE_ = '"
				+ userId
				+ "' and STATE_ ='completed' and ACTIVITY_NAME_ != '发起流程'  "+candition+") v";
		String listSql = "select v.endTime,tv.instanceId,tv.title,tv.creater,tv.createTime,tv.breforeTaskName,tv.beforeUser,tv.processAttributeId,"
				+ "tv.currentTaskName,v.htask,tv.current_state,tv.currentUser,tv.advice ,tv.last_update_time "
				+ "from (select a.EXECUTION_ instanceId,MAX(a.END_) endTime,MAX(a.DBID_) htask from JBPM4_HIST_TASK a,JBPM4_HIST_ACTINST b "
				+ "where a.DBID_ = b.HTASK_ and ASSIGNEE_ = '"
				+ userId
				+ "' and STATE_ ='completed' and ACTIVITY_NAME_ != '发起流程'  "
				+ "GROUP BY A.EXECUTION_) v,tb_cbb_workflow_var tv where v.instanceId = tv.instanceId "+candition+" order by v.endTime desc";

		int count = Integer.valueOf(entityManager.createNativeQuery(countSql)
				.getSingleResult().toString());

		// page.setTotalCount(count);
		// int currentPage = page.getPageNo()-1;
		int currentPage = page.getPageNumber();
		int beginNum = currentPage * page.getPageSize();

		@SuppressWarnings("unchecked")
		List<Object[]> list = entityManager.createNativeQuery(listSql)
				.setFirstResult(beginNum).setMaxResults(page.getPageSize())
				.getResultList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object[] temp = list.get(i);
				WorkFlowView view = new WorkFlowView();
				view.setTaskEnd((Timestamp) temp[0]);
				view.setInstanceId(temp[1].toString());
				if (temp[2] == null) {
					view.setJobName("");
				} else {
					view.setJobName(temp[2].toString());
				}
				if (temp[3] == null) {
					view.setCreater("");
				} else {
					String loginName = temp[3].toString();

					view.setCreater(getUserNameByLoginName(loginName, us));
				}
				if (temp[4] == null) {
					view.setProcessStart(null);
				} else {
					view.setProcessStart((Timestamp) temp[4]);
				}
				if (temp[5] == null) {
					view.setBeforeTaskName(null);
				} else {
					view.setBeforeTaskName(temp[5].toString());
				}
				if (temp[6] == null) {
					view.setBeforeUser(null);
				} else {
					view.setBeforeUser(temp[6].toString());
				}
				if (temp[7] == null) {
					view.setProcessId("");
				} else {
					view.setProcessId(temp[7].toString());
				}
				if (temp[8] == null) {
					view.setCurrentTaskName(null);
				} else {
					view.setCurrentTaskName(temp[8].toString());
				}
				view.setTaskId(temp[9].toString());
				if (temp[10] == null) {
					view.setCurrentState("");
				} else {
					view.setCurrentState(temp[10].toString());
				}
				if (temp[11] == null) {
					view.setCurrentUser(null);
				} else {
					view.setCurrentUser(temp[11].toString());
				}
				int index = view.getInstanceId().lastIndexOf(".");
				view.setProcessType(view.getInstanceId()
						.substring(0, index));
				
				//add by jiqy
				view.setCategoryName(temp[12]==null?"":temp[12].toString());
				view.setTaskStart((Timestamp) temp[13]);
				l.add(view);
			}
		}
		return new PageImpl<WorkFlowView>(l, page, count);
	}

	private String getUserNameByLoginName(String loginName, List<UserInfo> ui) {
		if (loginName!=null&&loginName.indexOf("_")>-1) {
			loginName=loginName.substring(loginName.indexOf("_")+1);
		}
		for (int i = 0; i < ui.size(); i++) {
			UserInfo userInfo = ui.get(i);
			if (userInfo !=null && userInfo.getLoginName()!=null && userInfo.getLoginName().equals(loginName)) {
				return userInfo.getUserName();
			}
		}
		return "";
	}

}
