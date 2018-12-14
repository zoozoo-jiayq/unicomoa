package cn.com.qytx.cbb.jbpmApp.service.impl.command;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

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
 * 功能：查询我发起的任务列表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午8:49:47 
 * 修改日期：上午8:49:47 
 * 修改列表：
 */
public class SearchAllStartTaskList implements Command<Page<WorkFlowView>> {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	private String	userId;
	private transient  Pageable	page;	
	private String searchkey;
	private Integer processattributeid;
	
	public SearchAllStartTaskList(String userId,String searchkey, Pageable page,Integer processattributeid) {
		super();
		this.userId = userId;
		this.page = page;
		this.searchkey = searchkey;
		if(this.searchkey == null){
			this.searchkey = "";
		}
		this.processattributeid=processattributeid;
	}

	@Override
	public Page<WorkFlowView> execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		List<WorkFlowView> l = new ArrayList<WorkFlowView>();
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		searchkey = searchkey.replace("[", "[[]");
		String countSql = "select COUNT(jhp.ID_) as c from JBPM4_HIST_PROCINST jhp,tb_cbb_workflow_var tv " +
				"where jhp.ID_ = tv.instanceId  and tv.title like '%"+this.searchkey+"%'";
		         if(StringUtils.isNoneBlank(this.userId)){
		        	 countSql +=" AND tv.creater = '"+this.userId+"'";
		         }
		         if(processattributeid!=null){
		        	 countSql +=" AND tv.processattributeid = "+this.processattributeid;
		         }
		String listSql = "select tv.createTime,tv.title,jhp.ID_,tv.currentTaskName,tv.currentUser,tv.current_state,tv.processAttributeId ,tv.advice ,tv.last_update_time " +
				" from JBPM4_HIST_PROCINST jhp,tb_cbb_workflow_var tv where jhp.ID_ = tv.instanceId  and tv.title like '%"+this.searchkey+"%'";
				if(StringUtils.isNoneBlank(this.userId)){
					listSql +=" AND tv.creater = '"+this.userId+"'";
		        }
		        if(processattributeid!=null){
		        	listSql +=" AND tv.processattributeid = "+this.processattributeid;
		        }
		        listSql +=" order by tv.createTime desc";
		int count=Integer.valueOf(entityManager.createNativeQuery(countSql).getSingleResult().toString());
		
	//	page.setTotalCount(count);
	//	int currentPage = page.getPageNo()-1;
		int currentPage = page.getPageNumber();
		int beginNum = currentPage*page.getPageSize();
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = entityManager.createNativeQuery(listSql).setFirstResult(beginNum)
				.setMaxResults(page.getPageSize()).getResultList();
		if(list!=null){
			for(int i=0; i<list.size(); i++){
				Object[] temp = list.get(i);
				WorkFlowView view = new WorkFlowView();
				view.setProcessStart((Timestamp)temp[0]);
				view.setJobName(temp[1].toString());
				view.setInstanceId(temp[2].toString());
				view.setCurrentTaskName(temp[3]==null?"":temp[3].toString());
				view.setCurrentUser(temp[4]==null?null:temp[4].toString());
				if(temp[5]!=null){
					view.setCurrentState(temp[5].toString());
				}else{
					view.setCurrentState("");
				}
				view.setProcessId(temp[6].toString());
				view.setCategoryName(temp[7]==null?"":temp[7].toString());
				int index = view.getInstanceId().lastIndexOf(".");
				view.setProcessType(view.getInstanceId().substring(0, index));
				//上一步任务的结束时间，下一步任务的开始时间
				view.setTaskStart((Timestamp) temp[8]);
				l.add(view);
			}
		}
		return  new PageImpl(l, page, count);
	}

}
