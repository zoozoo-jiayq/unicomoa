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
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能 查询已结束的任务  
 * <br/>版本  1.0
 * <br/>开发人员  zyf
 * <br/>创建日期  2016年1月8日
 * <br/>修改日期  2016年1月8日
 * <br/>修改列表
 */
public class SearchEndTaskList implements Command<Page<WorkFlowView>> {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	private transient Pageable page;
	private String searchkey;
	private String beginDate;
	private String endDate;
	private Integer processId;
	public SearchEndTaskList(Integer processId,String searchkey,String beginDate,String endDate,Pageable page) {
		super();
		this.page = page;
		this.searchkey = searchkey;
		if(this.searchkey == null){
			this.searchkey = "";
		}
		this.beginDate=beginDate;
		this.endDate=endDate;
		this.processId=processId;
	}

	@Override
	public Page<WorkFlowView> execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Object> values = new ArrayList<Object>();
//		IUser userService = (IUser) SpringUtil.getBean("userService");
//		List<UserInfo> us = userService.findAll();
		List<WorkFlowView> l = new ArrayList<WorkFlowView>();
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment
				.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
		searchkey = searchkey.replace("[", "[[]");
		String candition = " and (tv.title like '%"+searchkey+"%' or tv.refprocess like '%"+searchkey+"%') and tv.company_id="+TransportUser.get().getCompanyId();
		if (StringUtils.isNotBlank(beginDate)) {
			beginDate=beginDate+" 00:00:00";
			values.add(dateFormat.parse(beginDate));
			candition+=" and tv.createtime >? ";
		}
		if (StringUtils.isNotBlank(endDate)) {
			endDate=endDate+" 23:59:59";
			values.add(dateFormat.parse(endDate));
			candition+=" and tv.createtime <? ";
		}
		if (null!=processId) {
			candition+=" and tv.processattributeid="+processId;
		}
		String countSql = "select COUNT(*) as c from (select distinct(a.ID_) from JBPM4_HIST_PROCINST a,tb_cbb_workflow_var tv "
				+ "where a.ID_ = tv.instanceid and a.STATE_ ='ended' "+candition+") v";
		String listSql="select tv.instanceid,tv.createtime,tv.advice,tv.title,tv.refprocess,a.END_ ,tv.processattributeid from JBPM4_HIST_PROCINST a,tb_cbb_workflow_var tv where a.ID_=tv.instanceid and a.STATE_='ended' "+candition
				+" order by tv.createtime desc";

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

		int currentPage = page.getPageNumber();
		int beginNum = currentPage * page.getPageSize();

		@SuppressWarnings("unchecked")
		List<Object[]> list =null;
		if(values.size()>0){
			Query query = entityManager.createNativeQuery(listSql);
			int i = 0;
            for(Object value:values){
                query.setParameter(++i, value);
            }
            list = query.setFirstResult(beginNum).setMaxResults(page.getPageSize())
					.getResultList();
		}else{
			list = entityManager.createNativeQuery(listSql)
					.setFirstResult(beginNum).setMaxResults(page.getPageSize())
					.getResultList();
		}
		
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object[] temp = list.get(i);
				WorkFlowView view = new WorkFlowView();
				view.setInstanceId(temp[0]==null?"":temp[0].toString());
				view.setProcessStart(temp[1]==null?null:(Timestamp)temp[1]);
				view.setProcessType(temp[2]==null?"":temp[2].toString());
				view.setJobName(temp[3]==null?"":temp[3].toString());
				view.setCreater(temp[4]==null?"":temp[4].toString());
				view.setTaskEnd(temp[5]==null?null:(Timestamp)temp[5]);
				view.setProcessId(temp[6]==null?"":temp[6].toString());
				l.add(view);
			}
		}
		return new PageImpl<WorkFlowView>(l, page, count);
	}

}
