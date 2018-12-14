package cn.com.qytx.cbb.jbpmApp.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.stereotype.Repository;

import cn.com.qytx.cbb.jbpmApp.domain.WorkflowVar;
import cn.com.qytx.platform.base.dao.BaseDao;

/**
 * 功能：工作流流程变量DAO
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午2:38:29 
 * 修改日期：下午2:38:29 
 * 修改列表：
 */
@Repository("workflowVarDao")
public class WorkflowVarDao extends BaseDao<WorkflowVar, Integer> implements Serializable {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 功能：更新流程变量
	 * @param
	 * @return
	 * @throws   
	 */
	public void updateWorkflowVar(WorkflowVar var){
		this.saveOrUpdate(var);
	}
	
	/**
	 * 功能：根据流程id获得工作流流程变量
	 * @param instanceId
	 * @return
	 */
	public WorkflowVar findByInstanceId(String instanceId){
		String hql="select w from WorkflowVar w where instanceId = ?";
		return  findUnique(hql, instanceId);
	}
	
	/**
	 * 功能：根据流程id删除工作流程变量
	 * @param instanceId
	 */
	public void deleteWorkflowVar(String instanceId){
		String hql = "delete from WorkflowVar where instanceId = ?";
		super.bulkUpdate(hql, instanceId);
	}
	
	@Override
	public WorkflowVar saveOrUpdate(WorkflowVar entity) {
		// TODO Auto-generated method stub
		entity.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		return super.saveOrUpdate(entity);
	}
	
//	public void deleteAll(){
//		String hql = "delete from WorkflowVar";
//		super.bulkDelete(hql);
//	}
//	
//	public void execute(){
//		deleteAll();
////		ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
////		ExecutionService executionService =engine.getExecutionService();
//		List<WorkflowVar> list = new ArrayList<WorkflowVar>();
//		String sql1 = "select ID_ INSTANCEID,KEY_ TITLE,START_,STATE_ from JBPM4_HIST_PROCINST WHERE PROCDEFID_ NOT LIKE 'gather%' and PROCDEFID_ NOT LIKE 'dispatch%'";
//		List<Object[]> os =entityManager.createNativeQuery(sql1).getResultList();
//		for(int i=0; i<os.size(); i++){
//			WorkflowVar var = new WorkflowVar();
//			Object[] temp = os.get(i);
//			String instanceId = temp[0].toString();
//			String title  = temp[1].toString();
//			Timestamp create = (Timestamp) temp[2];
//			String processAttributeId = getLastValue(instanceId,"processAttributeId");
//			String creater = getLastValue(instanceId,"creater");
//			String beforeUser = getLastValue(instanceId,"beforeUser");
//			String currentTaskName = getLastValue(instanceId,"currentTaskName");
//			String currentUser = getLastValue(instanceId,"currentUser");
//			String breforeTaskName = getLastValue(instanceId,"breforeTaskName");
//			String currentState = getLastValue(instanceId,"currentState");
//			var.setInstanceId(instanceId);
//			var.setTitle(title);
//			var.setCreateTime(create);
//			var.setProcessAttributeId(Integer.parseInt(processAttributeId));
//			var.setCreater(creater);
//			var.setBeforeUser(beforeUser);
//			var.setCurrentTaskName(currentTaskName);
//			var.setCurrentUser(currentUser);
//			var.setBeforeTaskName(breforeTaskName);
//			var.setCurrentState(currentState);
//			list.add(var);
//		}
//		for(int i=0; i<list.size(); i++){
//			updateWorkflowVar(list.get(i));
//			if(i%20 == 0){
//				entityManager.flush();
//			}
//		}
//	}
//	
//	private String getLastValue(String instanceId,String key){
//		String sql = "select VALUE_ from JBPM4_HIST_VAR WHERE DBID_ IN(select MAX(DBID_) from JBPM4_HIST_VAR WHERE PROCINSTID_ = ? AND VARNAME_ = ? GROUP BY VARNAME_)";
//		Object result = entityManager.createNativeQuery(sql).setParameter(0, instanceId).setParameter(1, key).getResultList();
//		if(result!=null){
//			return result.toString();
//		}
//		return null;
//	}
}
