/**
 * 
 */
package org.jbpm.pvm.internal.wire.descriptor;

import javax.persistence.EntityManager;

import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.WireDefinition;
import org.jbpm.pvm.internal.wire.WireException;

/**
 * @author Santanu
 * 
 */
public class JpaDbSessionDescriptor extends AbstractDescriptor {

	private static final long serialVersionUID = 1L;
	
	private String entityManagerName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jbpm.pvm.internal.wire.Descriptor#construct(org.jbpm.pvm.internal.wire.WireContext)
	 */
	@Override
	public Object construct(WireContext wireContext) {
		return new JpaDbSessionImpl();
	}

	public void initialize(Object object, WireContext wireContext) {
		EntityManager entityManager = null;
		if (entityManagerName != null) {
			entityManager = (EntityManager) wireContext.get(entityManagerName);
		} else {
			entityManager = wireContext.get(EntityManager.class);
		}

		if (entityManager == null) {
			throw new WireException("couldn't find entity manager "
					+ (entityManagerName != null ? "'" + entityManagerName + "'"
							: "by type ") + "to create db-session");
		}

		// inject the session
		((JpaDbSessionImpl) object).setEntityManager(entityManager);
//		SessionImpl sessionImpl = (SessionImpl)entityManager.getDelegate();
//        Session session = sessionImpl.getSessionFactory().openSession();
		
	}

	public Class<?> getType(WireDefinition wireDefinition) {
		return JpaDbSessionImpl.class;
	}

	public void setEntityManagerName(String sessionName) {
		this.entityManagerName = sessionName;
	}
}
