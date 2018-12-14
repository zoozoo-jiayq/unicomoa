package cn.com.qytx.cbb.publicDom.service.impl.command;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessEngine;
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
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.spring.SpringUtil;


/**
 * 功能：查询我处理过的公文
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午4:21:46 
 * 修改日期：下午4:21:46 
 * 修改列表：
 */
public class SearchProcessingTaskList implements Command<Page<PublicDomView>> {

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
	
	public SearchProcessingTaskList(String userId, List<String> nodes,
			String processType, String title,GroupInfo forkGroup, Pageable page) {
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
		ProcessEngine engine = (ProcessEngine)environment.get(ProcessEngine.class);
		HistoryService historyService = engine.getHistoryService();
		ExecutionService executionService = engine.getExecutionService();
		IUser userService = (IUser) SpringUtil.getBean("userService");
		List<PublicDomView> l = new ArrayList<PublicDomView>();
		JpaDbSessionImpl impl = (JpaDbSessionImpl) environment.get(DbSession.class);
		EntityManager entityManager = impl.getEntityManager();
//		entityManager.cre
//		Session session = impl.getSession();
		
		StringBuffer nodesStr = new StringBuffer("");
		if(nodes!=null){
			for(int i=0; i<nodes.size(); i++){
				nodesStr.append("'").append(nodes.get(i)).append("'");
				if(i<nodes.size()-1){
					nodesStr.append(",");
				}
			}
		}
		if(title == null){
			title = "";
		}
		if(processType == null){
			processType = "";
		}
		StringBuffer search = new StringBuffer("");
		search.append(" where  je.ID_ = tv.instanceId and jt.PROCINST_ = je.DBID_  and tv.company_id="+TransportUser.get().getCompanyId()+" " );
		if(userId != null){
			search.append("and jt.ASSIGNEE_ = '"+userId+"' ");
		}
		if(nodes!=null && nodes.size() == 1 && nodes.contains("收文阅读")){
			
		}else{
			search.append(" and jt.SUPERTASK_ is NULL ");
		}
		search.append("AND jt.NAME_ in ("+nodesStr.toString()+") " +
				"and tv.instanceId like '"+processType+"%' "); 
		//根据公文类型、标题、文号查询
		search.append(" and (tv.title like '%"+title+"%' or tv.wenhao like '%"+title+"%' or tv.gongwenTypeName like '%"+title+"%')");
		
		int forkGroupId = 0 ;
		if(forkGroup!=null){
			forkGroupId = forkGroup.getGroupId();
			search.append(" and tv.forkGroupId = "+forkGroupId +" ");
		}
		
		String countSql = "SELECT count(jt.DBID_) as c FROM JBPM4_TASK jt,tb_gongwen_var tv ,JBPM4_EXECUTION je " ;
				
		String listSql = "SELECT jt.DBID_,tv.instanceId,tv.title,tv.wenhao,tv.gongwenTypeName," +
				"tv.state,tv.fromGroup,tv.receiverGroup,tv.miji,tv.huanji,tv.gatherSource,tv.createTime,tv.creater,tv.endTime,tv.creater_id FROM JBPM4_TASK jt,tb_gongwen_var tv,JBPM4_EXECUTION je ";
		
		String order = " order by jt.CREATE_ desc";
		int count = Integer.valueOf(entityManager.createNativeQuery(countSql+search.toString()).getSingleResult().toString());
//		int count = (Integer) session.createSQLQuery(countSql+search.toString()).uniqueResult();
		
		/*page.setTotalCount(count);
		int currentPage = page.getPageNo()-1;*/
		int currentPage = page.getPageNumber();
		int beginNum = (currentPage)*page.getPageSize();
		
		List<Object[]> list = entityManager.createNativeQuery(listSql+search+order).setFirstResult(beginNum)
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
				if(temp[4]!=null){
					view.setGongwenTypeName(temp[4].toString());
				}else{
					view.setGongwenTypeName("");
				}
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
				String id = temp[14]==null?"":temp[14].toString();
				String phone = "";
				if(id == null || id.equals("")){
					
				}else{
					UserInfo user = userService.findOne(Integer.parseInt(id));
					if(user!=null){
						phone = user.getPhone();
					}
				}
				view.setPhone(phone);
				l.add(view);
			}
		}
		return new PageImpl<PublicDomView>(l, page, count);
	}


}
