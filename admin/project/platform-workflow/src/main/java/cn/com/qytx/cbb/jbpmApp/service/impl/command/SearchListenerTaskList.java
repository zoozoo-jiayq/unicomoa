package cn.com.qytx.cbb.jbpmApp.service.impl.command;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

import cn.com.qytx.cbb.jbpmApp.domain.WorkFlowView;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能：查询监控列表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午10:14:52 
 * 修改日期：上午10:14:52 
 * 修改列表：
 */
public class SearchListenerTaskList implements Command<Page<WorkFlowView>> {
	
	/**
     * 描述含义
     */
    private static final long serialVersionUID = 7420444914892088659L;
    public final static String PROCESS_STATE_ACTIVE = "approve";
	public final static String PROCESS_STATE_SUSPEND = "suspend";

    private transient Pageable page;
	private String state;
	private String searchkey;
	private String beginDate;
	private String endDate;
	
	public SearchListenerTaskList(Pageable page, String state,String searchkey,String beginDate,String endDate) {
		super();
		this.page = page;
		this.state = state;
		this.searchkey = searchkey;
		if(this.searchkey == null){
			this.searchkey = "";
		}
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	@Override
	public Page<WorkFlowView> execute(Environment environment)
			throws Exception {
		List<WorkFlowView> l = new ArrayList<WorkFlowView>();
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		List<Object> values = new ArrayList<Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		searchkey = searchkey.replace("[", "[[]");
		String candition = " and (tv.title like '%"+searchkey+"%' or tv.refprocess like '%"+searchkey+"%')";
		if (StringUtils.isNotBlank(beginDate)) {
			beginDate=beginDate+" 00:00:00";
			values.add(dateFormat.parse(beginDate));
			candition+=" and tv.createtime > ?";
		}
		if (StringUtils.isNotBlank(endDate)) {
			endDate=endDate+" 23:59:59";
			values.add(dateFormat.parse(endDate));
			candition+=" and tv.createtime < ?";
		}
		
		String countSql = "select COUNT(jt.DBID_) as c from JBPM4_TASK jt,tb_cbb_workflow_var tv where jt.EXECUTION_ID_=tv.instanceId and jt.ASSIGNEE_ is not null  and tv.current_state='"+state+"'"+candition;
		String listsql = "select jt.CREATE_,tv.instanceId,tv.title,tv.creater,tv.createTime,tv.breforeTaskName,tv.beforeUser,tv.processAttributeId,jt.DBID_,tv.currentTaskName,tv.suspendTime,tv.advice,tv.current_state,tv.currentuser,tv.last_update_time " +
				"from JBPM4_TASK jt,tb_cbb_workflow_var tv where jt.EXECUTION_ID_=tv.instanceId and jt.ASSIGNEE_ is not null  and tv.current_state='"+state+"' "+candition+" order by jt.CREATE_ desc";
		
		//查询总行数
		int count =0;
		if(values.size()>0){
			Query query = entityManager.createNativeQuery(countSql);
			int i = 0;
            for(Object value:values){
                query.setParameter(++i, value);
            }
            count = Integer.valueOf(query.getSingleResult().toString());
		}else{
			count = Integer.valueOf(entityManager.createNativeQuery(countSql)
					.getSingleResult().toString());
		}
		
		//设置分页信息
		/*page.setTotalCount(count);
		int currentPage = page.getPageNo()-1;*/
		int currentPage = page.getPageNumber();
		int beginNum = currentPage*page.getPageSize();
		
		@SuppressWarnings("unchecked")
		List<Object[]> list =null;
		if(values.size()>0){
			Query query = entityManager.createNativeQuery(listsql);
			int i = 0;
            for(Object value:values){
                query.setParameter(++i, value);
            }
            list = query.setFirstResult(beginNum).setMaxResults(page.getPageSize())
					.getResultList();
		}else{
			list = entityManager.createNativeQuery(listsql)
					.setFirstResult(beginNum).setMaxResults(page.getPageSize())
					.getResultList();
		}
		if(list!=null){
			for(int i=0; i<list.size(); i++){
				Object[] temp = list.get(i);
				WorkFlowView view = new WorkFlowView();
				view.setInstanceId(temp[1].toString());
				view.setJobName(temp[2].toString());
				view.setCreater(temp[3].toString());
				view.setProcessStart((Timestamp)temp[4]);
				view.setBeforeTaskName(temp[5]==null?"":temp[5].toString());
				view.setBeforeUser(temp[6]==null?"":temp[6].toString());
				view.setProcessId(temp[7].toString());
				view.setTaskId(temp[8].toString());
				view.setCurrentTaskName(temp[9]==null?"":temp[9].toString());
				view.setSuspendTime((Timestamp)temp[10]);
				int index = view.getInstanceId().lastIndexOf(".");
				view.setProcessType(view.getInstanceId().substring(0, index));
				//add by jiayq,添加流程分类
				view.setCategoryName(temp[11]==null?"":temp[11].toString());
				view.setCurrentState(temp[12].toString());
				view.setCurrentUser(temp[13]==null?"":temp[13].toString());
				view.setTaskStart((Timestamp)temp[14]);
				l.add(view);
			}
		}
		return new PageImpl<WorkFlowView>(l, page, count);
	}

}
