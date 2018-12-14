package cn.com.qytx.cbb.publicDom.service.impl.command;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

import cn.com.qytx.cbb.publicDom.vo.PublicDomView;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageImpl;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.GroupInfo;



/**
 * 功能：查询待我处理的公文任务
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:21:46 
 * 修改日期：下午4:21:46 
 * 修改列表：
 */
public class SearchCompletedTaskList implements Command<Page<PublicDomView>> {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	private String			userId;
	private List<String> 	nodes;
	private String			processType;
	private String			title;
	private transient  Pageable	page;	
	private GroupInfo forkGroup;
	
	public SearchCompletedTaskList(String userId, List<String> nodes,
			String processType, String title, GroupInfo forkGroup,Pageable page) {
		super();
		this.userId = userId;
		this.nodes = nodes;
		this.processType = processType;
		this.title = title;
		this.page = page;
		this.forkGroup = forkGroup;
	}

	@Override
	public Page<PublicDomView> execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		List<PublicDomView> l = new ArrayList<PublicDomView>();
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment.get(DbSession.class);
		EntityManager session = impl.getEntityManager();
		
		StringBuffer nodesStr = new StringBuffer("");
		for(int i=0; i<nodes.size(); i++){
			nodesStr.append("'").append(nodes.get(i)).append("'");
			if(i<nodes.size()-1){
				nodesStr.append(",");
			}
		}
		if(title == null){
			title = "";
		}
		if(processType == null){
			processType = "";
		}
		StringBuffer search = new StringBuffer("");
		search.append(" where jhp.ID_ = tv.instanceId and ja.HPROCI_=jhp.DBID_ and jt.DBID_ = ja.HTASK_ and tv.company_id="+TransportUser.get().getCompanyId()+" ") ;
		if(userId != null){
			search.append("and jt.ASSIGNEE_ = '"+userId+"' ");
		}
		search.append("AND ja.ACTIVITY_NAME_ in ("+nodesStr.toString()+") " +
				"and tv.instanceId like '"+processType+"%' " +
				" and jt.STATE_ = 'completed' "); 
		
		//根据公文类型、标题、文号查询
		search.append(" and (tv.title like '%"+title+"%' or tv.wenhao like '%"+title+"%' or tv.gongwenTypeName like '%"+title+"%')");
		int forkGroupId = 0 ;
		if(forkGroup!=null){
			forkGroupId = forkGroup.getGroupId();
			search.append(" and tv.forkGroupId = "+forkGroupId +" ");
		}
		
		String countSql = "SELECT count(jt.DBID_) c FROM JBPM4_hist_TASK jt,tb_gongwen_var tv ,JBPM4_HIST_ACTINST ja,JBPM4_HIST_PROCINST jhp " ;
				
		String listSql = "SELECT jt.DBID_,tv.instanceId,tv.title,tv.wenhao,tv.gongwenTypeName," +
				"tv.state,tv.fromGroup,tv.receiverGroup,tv.miji,tv.huanji,tv.gatherSource,tv.createTime,tv.creater,tv.endTime,jt.END_ FROM JBPM4_hist_TASK jt,tb_gongwen_var tv ,JBPM4_HIST_ACTINST ja,JBPM4_HIST_PROCINST jhp  ";
		String order =  " order by jt.END_ DESC";
		int count = Integer.valueOf(session.createNativeQuery(countSql+search.toString()).getSingleResult().toString());
		
		/*page.setTotalCount(count);
		int currentPage = page.getPageNo()-1;*/
		int currentPage = page.getPageNumber();
		int beginNum = (currentPage)*page.getPageSize();
		
		List<Object[]> list = session.createNativeQuery(listSql+search+order).setFirstResult(beginNum)
				.setMaxResults(page.getPageSize()).getResultList();
		if(list!=null){
			for(int i=0; i<list.size(); i++){
				Object[] temp = list.get(i);
				PublicDomView view = new PublicDomView();
				view.setTaskId(temp[0].toString());
				view.setInstanceId(temp[1].toString());
				view.setTitle(temp[2].toString());
				if(temp[3]!=null){
					view.setWenhao(temp[3].toString());
				}else{
					view.setWenhao(null);
				}
				view.setGongwenTypeName(temp[4].toString());
				if(temp[5]!=null){
					view.setState(temp[5].toString());
				}else{
					view.setState(null);
				}
				view.setFromGroup(temp[6].toString());
				if(temp[7] == null){
					view.setReceiverGroup(null);
				}else{
					view.setReceiverGroup(temp[7].toString());
				}
				view.setMiji(temp[8].toString());
				view.setHuanji(temp[9].toString());
				view.setGatherSource(temp[10].toString());
				view.setCreateTime((Timestamp)temp[11]);
				view.setCreater(temp[12].toString());
				view.setEndTime((Timestamp)temp[13]);
				view.setLastTimeStamp((Timestamp)temp[14]);
				l.add(view);
			}
		}
		return new PageImpl<PublicDomView>(l, page, count);
	}


}
