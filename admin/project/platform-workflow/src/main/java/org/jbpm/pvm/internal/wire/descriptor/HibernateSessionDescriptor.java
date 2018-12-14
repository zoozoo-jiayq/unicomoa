/**
 * 
 */
package org.jbpm.pvm.internal.wire.descriptor;

import java.sql.Connection;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.impl.SessionImpl;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.tx.HibernateSessionResource;
import org.jbpm.pvm.internal.tx.StandardTransaction;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.WireDefinition;
import org.jbpm.pvm.internal.wire.WireException;

/**
 * 
 * @author Tom Baeyens
 */
public class HibernateSessionDescriptor extends AbstractDescriptor {

	  private static final long serialVersionUID = 1L;
	  
	  private static final Log LOG = Log.getLog(HibernateSessionDescriptor.class.getName());
	  
	  //hibernate specific properties
	  protected String factoryName;
	  protected boolean useCurrent = false;
	  protected boolean tx = true;
	  protected boolean close = true;
	  protected String connectionName;
	  
	  //now we need some more if this class works to satisfy hibernate session seekers
	  //in a JPA environment - the strategy works only if the JPA provider is hibernate
	  protected boolean jpa = false;
	  
	  protected String standardTransactionName;

	  public Object construct(WireContext wireContext) {
		
	    EnvironmentImpl environment = EnvironmentImpl.getCurrent();
	    if (environment==null) {
	      throw new WireException("no environment");
	    }

	    if (jpa) {
	    	return getSessionFromEntityManager(environment);
	    }
	    
	    // get the hibernate-session-factory
	    SessionFactory sessionFactory = null;
	    if (factoryName!=null) {
	      sessionFactory = (SessionFactory) wireContext.get(factoryName);
	    } else {
	      sessionFactory = environment.get(SessionFactory.class);
	    }
	    if (sessionFactory==null) {
	      throw new WireException("couldn't find hibernate-session-factory "+(factoryName!=null ? "'"+factoryName+"'" : "by type ")+"to open a hibernate-session");
	    }

	    // open the hibernate-session
	    Session session = null;
	    if (useCurrent) {
	      if (LOG.isTraceEnabled()) LOG.trace("getting current hibernate session");
	      session = sessionFactory.getCurrentSession();
	      
	    } else if (connectionName!=null) {
	      Connection connection = (Connection) wireContext.get(connectionName);
	      if (LOG.isTraceEnabled()) LOG.trace("creating hibernate session with connection "+connection);
	      session = sessionFactory.openSession(connection);

	    } else {
	      if (LOG.isTraceEnabled()) LOG.trace("creating hibernate session");
	      session = sessionFactory.openSession();
	    }
	    
	    StandardTransaction standardTransaction = environment.get(StandardTransaction.class);
	    if (standardTransaction!=null) {
	      HibernateSessionResource hibernateSessionResource = new HibernateSessionResource(session);
	      standardTransaction.enlistResource(hibernateSessionResource);
	    }

	    return session;
	  }
	  
	  private Session getSessionFromEntityManager(EnvironmentImpl environment) {
		  EntityManager entityManager = environment.get(EntityManager.class);
	      Session session = (Session)entityManager.getDelegate();
	      return session;
	  }
	  
	  public Class<?> getType(WireDefinition wireDefinition) {
	    return SessionImpl.class;
	  }

	  public void setFactoryName(String factoryName) {
	    this.factoryName = factoryName;
	  }
	  
	  public void setTx(boolean tx) {
	    this.tx = tx;
	  }
	  
	  public void setStandardTransactionName(String standardTransactionName) {
	    this.standardTransactionName = standardTransactionName;
	  }
	  
	  public void setConnectionName(String connectionName) {
	    this.connectionName = connectionName;
	  }
	  
	  public void setUseCurrent(boolean useCurrent) {
	    this.useCurrent = useCurrent;
	  }
	  
	  public void setClose(boolean close) {
	    this.close = close;
	  }
	  
	  public void setJpa(boolean jpa) {
		  this.jpa = jpa;
	  }
}
