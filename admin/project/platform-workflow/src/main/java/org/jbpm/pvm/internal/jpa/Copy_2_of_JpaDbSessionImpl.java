/**
 * 
 */
package org.jbpm.pvm.internal.jpa;

import org.hibernate.Session;
import org.jbpm.pvm.internal.hibernate.DbSessionImpl;

/**
 * @author Santanu
 *
 */
public class Copy_2_of_JpaDbSessionImpl extends DbSessionImpl {

//	private EntityManager entityManager;
//	
//	public EntityManager getEntityManager() {
//		return entityManager;
//	}
//
//	public void setEntityManager(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}

	@Override
	public void setSession(Session session) {
		// TODO Auto-generated method stub
		super.setSession(session);
	}

}
