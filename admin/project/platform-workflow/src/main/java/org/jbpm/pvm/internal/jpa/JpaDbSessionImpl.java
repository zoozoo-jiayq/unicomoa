/**
 * 
 */
package org.jbpm.pvm.internal.jpa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.jbpm.api.Execution;
import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.history.HistoryComment;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.client.ClientExecution;
import org.jbpm.pvm.internal.client.ClientProcessDefinition;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.model.HistoryCommentImpl;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;
import org.jbpm.pvm.internal.id.DbidGenerator;
import org.jbpm.pvm.internal.job.JobImpl;
import org.jbpm.pvm.internal.job.StartProcessTimer;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.query.DeploymentQueryImpl;
import org.jbpm.pvm.internal.query.HistoryActivityInstanceQueryImpl;
import org.jbpm.pvm.internal.query.HistoryDetailQueryImpl;
import org.jbpm.pvm.internal.query.HistoryProcessInstanceQueryImpl;
import org.jbpm.pvm.internal.query.JobQueryImpl;
import org.jbpm.pvm.internal.query.ProcessInstanceQueryImpl;
import org.jbpm.pvm.internal.query.TaskQueryImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;

/**
 * @author Santanu
 *
 */
public class JpaDbSessionImpl implements DbSession {

	private static Log log = Log.getLog(JpaDbSessionImpl.class.getName());
	
	protected EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#cascadeExecutionResume(org.jbpm.pvm.internal.model.ExecutionImpl)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cascadeExecutionResume(ExecutionImpl execution) {
	    // cascade resume to jobs
	    Query query = entityManager.createQuery(
				    		"select job from "
				    		+ JobImpl.class.getName()+" as job " 
				    		+ "where job.execution = :execution " 
				    		+ "and job.state = '"+JobImpl.STATE_SUSPENDED+"' "
				    	).setParameter("execution", execution);
	    List<JobImpl> jobs = query.getResultList();
	    for (JobImpl job: jobs) {
	      job.resume();
	    }

	    // cascade suspend to tasks
	    query = entityManager.createQuery(
			    		"select task  from "
			    		+ TaskImpl.class.getName() + " as task " 
			    		+ "where task.execution = :execution " 
			    		+ "and task.state = '"+Task.STATE_SUSPENDED+"' "
			    	).setParameter("execution", execution);
	    List<TaskImpl> tasks = query.getResultList();
	    for (TaskImpl task: tasks) {
	      task.resume();
	    }	
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#cascadeExecutionSuspend(org.jbpm.pvm.internal.model.ExecutionImpl)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cascadeExecutionSuspend(ExecutionImpl execution) {
	    // cascade suspend to jobs
	    Query query = entityManager.createQuery(
				    		"select job from "
				    		+ JobImpl.class.getName()+" as job " 
				    		+ "where job.execution = :execution " 
				    		+ "and job.state != '"+JobImpl.STATE_SUSPENDED+"' "
				    	).setParameter("execution", execution);
	    List<JobImpl> jobs = query.getResultList();
	    for (JobImpl job: jobs) {
	      job.suspend();
	    }

	    // cascade suspend to tasks
	    query = entityManager.createQuery(
			    		"select task  from "
			    		+ TaskImpl.class.getName() + " as task " 
			    		+ "where task.execution = :execution " 
			    		+ "and task.state != '"+Task.STATE_SUSPENDED+"' "
			    	).setParameter("execution", execution);
	    List<TaskImpl> tasks = query.getResultList();
	    for (TaskImpl task: tasks) {
	      task.suspend();
	    }
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#createDeploymentQuery()
	 */
	@Override
	public DeploymentQueryImpl createDeploymentQuery() {
		return new DeploymentQueryImpl();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#createHistoryActivityInstanceQuery()
	 */
	@Override
	public HistoryActivityInstanceQueryImpl createHistoryActivityInstanceQuery() {
		return new HistoryActivityInstanceQueryImpl();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#createHistoryDetailQuery()
	 */
	@Override
	public HistoryDetailQueryImpl createHistoryDetailQuery() {
		return new HistoryDetailQueryImpl();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#createHistoryProcessInstanceQuery()
	 */
	@Override
	public HistoryProcessInstanceQueryImpl createHistoryProcessInstanceQuery() {
		return new HistoryProcessInstanceQueryImpl();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#createJobQuery()
	 */
	@Override
	public JobQueryImpl createJobQuery() {
		return new JobQueryImpl();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#createProcessInstanceQuery()
	 */
	@Override
	public ProcessInstanceQueryImpl createProcessInstanceQuery() {
		// TODO Auto-generated method stub
		return new ProcessInstanceQueryImpl();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#createTask()
	 */
	@Override
	public TaskImpl createTask() {
		TaskImpl task = newTask();
		task.setCreateTime(new Date());
		return task;
	}

	protected TaskImpl newTask() {
		TaskImpl task = new TaskImpl();
		long dbid = EnvironmentImpl.getFromCurrent(DbidGenerator.class).getNextId();
		task.setDbid(dbid);
		task.setNew(true);
		return task;
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#createTaskQuery()
	 */
	@Override
	public TaskQueryImpl createTaskQuery() {
		return new TaskQueryImpl();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object entity) {
		entityManager.remove(entity);
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#deleteProcessDefinition(java.lang.String, boolean, boolean)
	 */
	//@Override
	public void deleteProcessDefinition(String processDefinitionId) {
	    List<String> processInstanceIds = findProcessInstanceIds(processDefinitionId);
	    
	    if ( 
	         (isHistoryEnabled())
	       ) {
	      List<HistoryProcessInstance> historyProcessInstances = createHistoryProcessInstanceQuery()
	        .processDefinitionId(processDefinitionId)
	        .list();
	      
	      for (HistoryProcessInstance historyProcessInstance : historyProcessInstances) {
	    	  entityManager.remove(historyProcessInstance);
	      }
	    }
	    
	    if (true) {
	      for (String processInstanceId : processInstanceIds) {
	        deleteProcessInstance(processInstanceId, true);
	      }
	    } else {
	      if (processInstanceIds.size()>0) {
	        throw new JbpmException("still "+processInstanceIds.size()+" process instances for process definition "+processDefinitionId);
	      }
	    }
	    
	    ProcessDefinition processDefinition = findProcessDefinitionById(processDefinitionId);
	    entityManager.remove(processDefinition);	
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#deleteProcessDefinitionHistory(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteProcessDefinitionHistory(String processDefinitionId) {
	    List<HistoryProcessInstanceImpl> historyProcessInstances = 
	          entityManager.createQuery("select hpi from "
	        		  + HistoryProcessInstanceImpl.class.getName()+" hpi "
	        		  + "where hpi.processDefinitionId = :processDefinitionId "
	          ).setParameter("processDefinitionId", processDefinitionId)
	           .getResultList();
	    
	    for (HistoryProcessInstanceImpl hpi: historyProcessInstances) {
	    	entityManager.remove(hpi);
	    }
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#deleteProcessInstance(java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteProcessInstance(String processInstanceId, boolean deleteHistory) {
	    if (processInstanceId==null) {
	        throw new JbpmException("processInstanceId is null");
	      }
	      
	      // if history should be deleted 
	      if ( deleteHistory 
	           && (isHistoryEnabled())
	         ) {
	        // try to get the history 
	        HistoryProcessInstanceImpl historyProcessInstance = findHistoryProcessInstanceById(processInstanceId);
	    
	        // if there is a history process instance in the db
	        if (historyProcessInstance!=null) {
	          if (log.isDebugEnabled()) {
	            log.debug("deleting history process instance "+processInstanceId);
	          }
	          entityManager.remove(historyProcessInstance);
	        }
	      }
	      
	      ExecutionImpl processInstance = (ExecutionImpl) findProcessInstanceByIdIgnoreSuspended(processInstanceId);
	      if (processInstance!=null) {
	        deleteSubProcesses(processInstance, deleteHistory);
	        
	        // delete remaining tasks for this process instance
	        List<TaskImpl> tasks = findTasks(processInstanceId);
	        for (TaskImpl task: tasks) {
	        	entityManager.remove(task);
	        }

	        // delete remaining jobs for this process instance
	        JobImpl currentJob = EnvironmentImpl.getFromCurrent(JobImpl.class, false);
	        List<JobImpl> jobs = findJobs(processInstanceId);
	        for (JobImpl job: jobs) {
	          if (job!=currentJob){ 
	        	  entityManager.remove(job);
	          }
	        }

	        if (log.isDebugEnabled()) {
	          log.debug("deleting process instance "+processInstanceId);
	        }

	        entityManager.remove(processInstance);
	      }
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findCommentsByTaskId(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<HistoryComment> findCommentsByTaskId(String taskId) {
	    Long taskDbid = null;
	    try {
	    	taskDbid = Long.parseLong(taskId);
	    } catch (NumberFormatException e) {
	    	throw new JbpmException("invalid taskId: " + taskId);
	    }
	    return entityManager.createQuery(
					"select hc from " 
					+ HistoryCommentImpl.class.getName()
					+ " as hc where hc.historyTask.dbid = :taskDbid" 
					+ " order by hc.historyTaskIndex asc"
	    	   ).setParameter("taskDbid", taskDbid).getResultList();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findExclusiveJobs(org.jbpm.api.Execution)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<JobImpl> findExclusiveJobs(Execution processInstance) {
	    Query query = entityManager.createNamedQuery("findExclusiveJobs");
	    query.setParameter("now", new Date());
	    query.setParameter("processInstance", processInstance);
	    return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findExecutionById(java.lang.String)
	 */
	@Override
	public ClientExecution findExecutionById(String executionId) {
	    // query definition can be found at the bottom of resource jbpm.pvm.execution.hbm.xml
		// we avoid query.getSingleResult() here. Because that thrown NoResultException
		// we would prefer null instead
	    Query query = entityManager.createNamedQuery("findExecutionById");
	    query.setParameter("id", executionId);
	    query.setMaxResults(1);
	    @SuppressWarnings("unchecked")
		List<ClientExecution> executions = query.getResultList();
	    if (!executions.isEmpty()) {
	    	return (ClientExecution) executions.get(0);
	    }
	    return null;
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findFirstAcquirableJob()
	 */
	@Override
	public JobImpl findFirstAcquirableJob() {
	    Query query = entityManager.createNamedQuery("findFirstAcquirableJob");
	    query.setParameter("now", new Date());
	    query.setMaxResults(1);
	    return (JobImpl) query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findFirstDueJob()
	 */
	@Override
	public JobImpl findFirstDueJob() {
	    Query query = entityManager.createNamedQuery("findFirstDueJob");
	    query.setMaxResults(1);
	    return (JobImpl) query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findLatestProcessDefinitionByKey(java.lang.String)
	 */
	//@Override
	public ClientProcessDefinition findLatestProcessDefinitionByKey(String processDefinitionKey) {
		Query query = entityManager.createNamedQuery("findProcessDefinitionsByKey");
	    query.setParameter("key", processDefinitionKey);
	    query.setMaxResults(1);
		return (ClientProcessDefinition)query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findProcessDefinitionById(java.lang.String)
	 */
//	@Override
	public ClientProcessDefinition findProcessDefinitionById(String processDefinitionId) {
	    Query query = entityManager.createNamedQuery("findProcessDefinitionById");
	    query.setParameter("id", processDefinitionId);
	    query.setMaxResults(1);
	    return (ClientProcessDefinition)query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findProcessDefinitionKeys()
	 */
//	@Override
//	@SuppressWarnings("unchecked")
	public List<String> findProcessDefinitionKeys() {
		return entityManager.createNamedQuery("findProcessDefinitionKeys").getResultList();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findProcessDefinitionsByKey(java.lang.String)
	 */
//	@Override
//	@SuppressWarnings("unchecked")
	public List<ClientProcessDefinition> findProcessDefinitionsByKey(String processDefinitionKey) {
	    Query query = entityManager.createNamedQuery("findProcessDefinitionsByKey");
	    query.setParameter("key", processDefinitionKey);
	    return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findProcessInstanceById(java.lang.String)
	 */
	@Override
	public ClientExecution findProcessInstanceById(String processInstanceId) {
	    // query definition can be found at the bottom of resource jbpm.pvm.execution.hbm.xml
	    Query query = entityManager.createNamedQuery("findProcessInstanceById");
	    query.setParameter("processInstanceId", processInstanceId);
	    query.setMaxResults(1);
	    return (ClientExecution) query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findProcessInstanceIds(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> findProcessInstanceIds(String processDefinitionId) {
		Query query = entityManager.createQuery("select processInstance.id " +
				"from org.jbpm.pvm.internal.model.ExecutionImpl as processInstance " +
			    "where processInstance.processDefinitionId = :processDefinitionId " +
			    "and processInstance.parent is null");
		query.setParameter("processDefinitionId", processDefinitionId);
	    return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#findTaskByExecution(org.jbpm.api.Execution)
	 */
	@Override
	public TaskImpl findTaskByExecution(Execution execution) {
		Query query = entityManager.createQuery("select task from " 
				+ TaskImpl.class.getName()
				+ " as task where task.execution = :execution");
		query.setParameter("execution", execution);
		List<TaskImpl> list = query.getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#flush()
	 */
	@Override
	public void flush() {
		entityManager.flush();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#get(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T get(Class<T> entityClass, Object primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#save(java.lang.Object)
	 */
	@Override
	public void save(Object entity) {
		entityManager.persist(entity);
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.session.DbSession#update(java.lang.Object)
	 */
	@Override
	public void update(Object entity) {
		//this is slightly casual
		//hibernate update and merge are not all the same...
		//this is a TODO
		entityManager.merge(entity);
	}

	public ClientExecution findProcessInstanceByIdIgnoreSuspended(
			String processInstanceId) {
		// query definition can be found at the bottom of resource jbpm.pvm.execution.hbm.xml
		Query query = entityManager.createNamedQuery("findProcessInstanceByIdIgnoreSuspended");
		query.setParameter("processInstanceId", processInstanceId);
		query.setMaxResults(1);
		return (ClientExecution) query.getSingleResult();
	}
	
	public HistoryProcessInstanceImpl findHistoryProcessInstanceById(String processInstanceId) {
		Query query = entityManager.createQuery(
				"select hpi from "
				+ HistoryProcessInstance.class.getName() 
				+ " as hpi where hpi.processInstanceId = :processInstanceId");
		query.setParameter("processInstanceId", processInstanceId);
		return (HistoryProcessInstanceImpl)query.getSingleResult();
	}

	private void deleteSubProcesses(ExecutionImpl execution,
			boolean deleteHistory) {
		ExecutionImpl subProcessInstance = execution.getSubProcessInstance();
		if (subProcessInstance != null) {
			subProcessInstance.setSuperProcessExecution(null);
			execution.setSubProcessInstance(null);
			deleteProcessInstance(subProcessInstance.getId(), deleteHistory);
		}
		Collection<ExecutionImpl> childExecutions = execution.getExecutions();
		if (childExecutions != null) {
			for (ExecutionImpl childExecution : childExecutions) {
				deleteSubProcesses(childExecution, deleteHistory);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	List<TaskImpl> findTasks(String processInstanceId) {
		Query query = entityManager.createQuery("select task from "
				+ TaskImpl.class.getName() + " as task "
				+ "where task.processInstance.id = :processInstanceId");
		query.setParameter("processInstanceId", processInstanceId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	List<JobImpl> findJobs(String processInstanceId) {
		Query query = entityManager.createQuery("select job " + "from "
				+ JobImpl.class.getName() + " as job "
				+ "where job.processInstance.id = :processInstanceId");
		query.setParameter("processInstanceId", processInstanceId);
		return query.getResultList();
	}
	
	public boolean isHistoryEnabled() {
		//i'm sure life can be better and we can get rid of session here
		//so..lets mark it as TODO
		ClassMetadata historyHibernateMetadata = 
			((Session)entityManager.getDelegate()).getSessionFactory().getClassMetadata(HistoryProcessInstanceImpl.class);
		return historyHibernateMetadata != null;
	}

	@Override
	public List<StartProcessTimer> findStartProcessTimers(
			String processDefinitionId) {
		// TODO Auto-generated method stub
		return null;
	}
}
