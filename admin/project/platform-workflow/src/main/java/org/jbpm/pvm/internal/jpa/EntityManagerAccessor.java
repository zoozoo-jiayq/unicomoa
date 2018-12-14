/**
 * 
 */
package org.jbpm.pvm.internal.jpa;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Environment;

/**
 * @author Santanu
 */
public interface EntityManagerAccessor {
	
	/**
	 * Implementing class should return the relevant entity manager.
	 * @param environment
	 * @return
	 */
	public EntityManager getEntityManager(Environment environment);
}
