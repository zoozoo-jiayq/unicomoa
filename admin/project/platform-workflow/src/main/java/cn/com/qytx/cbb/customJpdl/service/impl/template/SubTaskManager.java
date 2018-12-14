package cn.com.qytx.cbb.customJpdl.service.impl.template;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.OpenTask;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.utils.spring.SpringUtil;


/**
 * 功能：子任务管理器
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:27:34 
 * 修改日期：2013-3-22 上午11:27:34 
 * 修改列表：
 */
@Transactional
@Service("subTaskManager")
public class SubTaskManager implements Serializable{
	
	
	/**
	 * 功能：根据父任务创建子任务
	 * @param：parentTask：父任务；userID:用户ID
	 * @return：子任务
	 * @throws   
	 */
	public Task createSubTask(Task parentTask,String userId){
		ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
		Task task = engine.execute(new CreateSubTaskSign(parentTask, userId));
		return task;
	}
	
	

	/**
	 * 功能：完成子任务
	 * @param subTask:子任务
	 * @return
	 * @throws   
	 */
	public void completeSubTask(Task subTask,String outCome){
		ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
		engine.execute(new CompleteSubTask(subTask,outCome));
	}
}
/*=================================================内部类=================================================*/

 /**
 * 功能：创建子任务的内部实现类
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:29:20 
 * 修改日期：2013-3-22 上午11:29:20 
 * 修改列表：
 */
class CreateSubTaskSign implements Command<Task>{
	 
	private Task task;
	private String userID;
	 
	public CreateSubTaskSign(Task task,String userID){
		this.task = task;
		this.userID = userID;
	}
	
	/**
	 * 功能：获取历史任务实例的DBID
	 * @param executionId:实例的ID
	 * @return 实现的数据库主键
	 * @throws   
	 */
	private Long getHistInstanceId(String executionId){
		ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
		HistoryService historyService = engine.getHistoryService();
		List<HistoryProcessInstance> hInstances = historyService.createHistoryProcessInstanceQuery().processInstanceId(executionId).list();
		if(hInstances!=null && hInstances.size()>0){
			HistoryProcessInstanceImpl instance = (HistoryProcessInstanceImpl)hInstances.get(0);
			return instance.getDbid();
		}
		return 0L;
	}
	
	/**
	 * 功能：创建子任务并向历史表中插入记录
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public Task execute(Environment environment) throws Exception {
		ProcessEngine processEngine = (ProcessEngine) SpringUtil.getBean("processEngine");
		TaskService taskService = processEngine.getTaskService();
		JpaDbSessionImpl dbSessionImpl = (JpaDbSessionImpl) environment.get(DbSession.class); 
		EntityManager session = dbSessionImpl.getEntityManager();
		Task subTask = ((OpenTask)task).createSubTask();
		TaskImpl newask = (TaskImpl)subTask;
		newask.setExecution(((TaskImpl)task).getExecution());
		newask.setName(task.getName());
		newask.setProcessInstance(((TaskImpl)task).getExecution());
		newask.setActivityName(task.getActivityName());
		newask.setAssignee(userID);
		session.flush();
		String execuId = task.getExecutionId();
		//历史任务的DBID和任务的DBID相同
		long taskdbid = newask.getDbid();
		//历史活动的DBID比任务的DBID大1
		long actidbid = taskdbid+1;
		//创建子任务的同时向历史表HIST_TASK中插入历史记录
		session.createNativeQuery(
				"insert into JBPM4_HIST_TASK(DBID_,EXECUTION_,ASSIGNEE_,DBVERSION_,PRIORITY_,NEXTIDX_,CREATE_,DURATION_,MY_SUPER_TASK) " +
				"VALUES(?,?,?,?,?,?,?,?,?)"
				)
				.setParameter(1, taskdbid)
				.setParameter( 2, execuId)
				.setParameter( 3, userID)
				.setParameter(4, 1)
				.setParameter(5, 0)
				.setParameter(6, 1)
				.setParameter(7, new Timestamp(Calendar.getInstance().getTimeInMillis()))
				.setParameter(8, 0)
				.setParameter(9, task.getId())
				.executeUpdate();
		//向表JBPM4_HIST_ACTINST插入记录
		session.createNativeQuery(
				"insert into JBPM4_HIST_ACTINST(DBID_,CLASS_,DBVERSION_,HPROCI_,TYPE_,EXECUTION_,ACTIVITY_NAME_,START_,NEXTIDX_,HTASK_,DURATION_)" +
				" values(?,?,?,?,?,?,?,?,?,?,?)"
				)
				.setParameter(1, actidbid)
				.setParameter(2, "task")
				.setParameter(3, 1)
				.setParameter(4, getHistInstanceId(execuId))
				.setParameter(5, "task")
				.setParameter(6, execuId)
				.setParameter(7, task.getActivityName())
				.setParameter(8, new Timestamp(Calendar.getInstance().getTimeInMillis()))
				.setParameter(9, 1)
				.setParameter(10, taskdbid)
				.setParameter(11, 0)
				.executeUpdate();
		taskService.addTaskParticipatingUser(task.getId(), userID, Participation.CLIENT);
		return subTask;
	}
} 

/**
 * 功能：结束子任务并更新历史表的记录状态
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-3-22 上午11:32:48 
 * 修改日期：2013-3-22 上午11:32:48 
 * 修改列表：
 */
class CompleteSubTask implements Command<Boolean>{

	private Task subTask;
	private String outCome;

	/**
	 * @param subTask
	 */
	public CompleteSubTask(Task subTask,String outCome) {
		super();
		this.subTask = subTask;
		this.outCome = outCome;
	}


	/**
	 * 功能：结束子任务并在历史表中更新记录
	 * @param
	 * @return
	 * @throws   
	 */
	@Override
	public Boolean execute(Environment environment) throws Exception {
		ProcessEngine processEngine = (ProcessEngine) SpringUtil.getBean("processEngine");
		TaskService taskService = processEngine.getTaskService();
		TaskImpl impl = (TaskImpl)subTask;
		Task parentTask = impl.getSuperTask();
		//判断是否是子任务
		if(parentTask!=null){
			JpaDbSessionImpl dbSessionImpl = (JpaDbSessionImpl) environment.get(DbSession.class); 
			EntityManager session = dbSessionImpl.getEntityManager();
			Long taskDBID = impl.getDbid();
			Long actiDBID = taskDBID+1;
			//查询任务创建时间
			Date create = (Date) session.createNativeQuery("select CREATE_ FROM JBPM4_HIST_TASK WHERE DBID_ = ?")
					.setParameter(1, taskDBID).getSingleResult();
			Timestamp end = new Timestamp(Calendar.getInstance().getTimeInMillis());
			//计算持续时间
			Long dur = end.getTime()-create.getTime();
			//更新HIST_TASK表的状态
			session.createNativeQuery("update JBPM4_HIST_TASK SET STATE_ =?, END_=?,DURATION_=? WHERE DBID_ = ?")
			.setParameter(1, HistoryTask.STATE_COMPLETED)
			.setParameter(2, end)
			.setParameter(3, dur)
			.setParameter(4, taskDBID)
			.executeUpdate();
			
			//更新HIST_ACTIV表的状态
			Date start = (Date) session.createNativeQuery("select START_ FROM JBPM4_HIST_ACTINST WHERE DBID_ = ?")
			.setParameter(1,actiDBID).getSingleResult();
			Timestamp acend = new Timestamp(Calendar.getInstance().getTimeInMillis());
			Long actdur = acend.getTime()-start.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			session.createNativeQuery("update JBPM4_HIST_ACTINST SET END_ = ?,DURATION_ = ? WHERE DBID_ = ?")
			.setParameter(1, sdf.format(new Date()))
			.setParameter(2, actdur)
			.setParameter(3, actiDBID)
			.executeUpdate();
		}
		taskService.completeTask(subTask.getId());
		if(parentTask!=null){
			List<Task> tasks = taskService.getSubTasks(parentTask.getId());
			if(tasks==null || tasks.size() ==0){
				if(outCome==null){
					taskService.completeTask(parentTask.getId());
				}else{
					taskService.completeTask(parentTask.getId(),outCome);
				}
			}
		}
		return true;
	}
	
}
