/**
 * 
 */
package org.jbpm.pvm.internal.wire.descriptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.jpa.EntityManagerAccessor;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.WireDefinition;
import org.jbpm.pvm.internal.wire.WireException;

/**
 * @author Santanu
 *
 */
public class JpaEntityManagerDescriptor extends AbstractDescriptor {

	private static final long serialVersionUID = 1L;
	
	private String factoryName;
	private String accessorClassName;
	private boolean create = false;

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.wire.Descriptor#construct(org.jbpm.pvm.internal.wire.WireContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object construct(WireContext wireContext) {
		EnvironmentImpl environment = EnvironmentImpl.getCurrent();
	    if (environment==null) {
	      throw new WireException("no environment");
	    }
	    
	    EntityManagerFactory entityManagerFactory = null;
	    if (StringUtils.isNotBlank(factoryName)) {
	    	entityManagerFactory = (EntityManagerFactory)wireContext.get(factoryName);
	    } else {
	    	entityManagerFactory = environment.get(EntityManagerFactory.class);
	    }
	    
	    // this is not fair!! how can we create if we do not have the factory?
	    // every reason to crib
	    if (entityManagerFactory == null) {
	    	throw new WireException("No entity manager factory found");
	    }
	    
	    EntityManager entityManager = null;
	    // here we fight to get the entity manager
	    // entity manager can be obtained in multiple ways
	    // it can be injected by the container
	    // it can be a "resource local" entity manager, where onus is 
	    // on the application to manage entity manager
		
		// injected by the container!! how? where do i put @PersistenceContext
		// annotation.
	    
		// for resource local also, how do we get the current entity manager?
		
		// so to handle both the situations above, we can pass the responsibility
		// to the user to provide us with the entity manager. So lets allow the 
		// user to plug in some code 
	    if ((entityManager == null) && StringUtils.isNotBlank(accessorClassName)) {
	    	try {
				Class providerClass = Class.forName(accessorClassName);
				EntityManagerAccessor entityManagerProvider 
						= (EntityManagerAccessor)providerClass.newInstance();
				entityManager = entityManagerProvider.getEntityManager(environment);
			} catch (ClassNotFoundException e) {
				throw new WireException("Problem loading class " + accessorClassName, e);
			} catch (InstantiationException e) {
				throw new WireException("Problem while creating object of type " + accessorClassName, e);
			} catch (IllegalAccessException e) {
				throw new WireException("Problem while creating object of type " + accessorClassName, e);
			}
	    }
		
		// else if we are allowed to create an entity manager and have a factory
		// somewhere in the wire context then we can easily create one
	    if ((entityManager == null) && create) {
		    entityManager = entityManagerFactory.createEntityManager();
	    }
	    
	    return entityManager;
	}

	/**
	 * @param factoryName the factoryName to set
	 */
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	/**
	 * @param accessorClassName the accessorClassName to set
	 */
	public void setAccessorClassName(String providerClassName) {
		this.accessorClassName = providerClassName;
	}

	/**
	 * @param create the create to set
	 */
	public void setCreateNew(boolean create) {
		this.create = create;
	}
	
	public Class<?> getType(WireDefinition wireDefinition) {
		return EntityManager.class;
	}
}
