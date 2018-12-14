/**
 * 
 */
package org.jbpm.pvm.internal.jpa;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jbpm.api.cmd.Environment;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

/**
 * @author Santanu
 *
 */
public class SpringEntityManagerAccessor implements EntityManagerAccessor {

	@Override
	@SuppressWarnings("unchecked")
	public EntityManager getEntityManager(Environment environment) {
		EntityManagerFactory entityManagerFactory = environment.get(EntityManagerFactory.class);
		return EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory, new HashMap());
	}

}
