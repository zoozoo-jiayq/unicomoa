/**
 * 
 */
package org.jbpm.pvm.internal.wire.binding;

import org.jbpm.pvm.internal.wire.descriptor.JpaDbSessionDescriptor;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;

/**
 * @author Santanu
 *
 */
public class JpaDbSessionBinding extends WireDescriptorBinding {

	public static final String TAG = "jpa-session";
	
	public JpaDbSessionBinding() {
		super(TAG);
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.xml.Binding#parse(org.w3c.dom.Element, org.jbpm.pvm.internal.xml.Parse, org.jbpm.pvm.internal.xml.Parser)
	 */
	@Override
	public Object parse(Element element, Parse parse, Parser parser) {
		JpaDbSessionDescriptor descriptor = new JpaDbSessionDescriptor();
	    
	    if (element.hasAttribute("entity-manager")) {
	      descriptor.setEntityManagerName(element.getAttribute("entity-manager"));
	    }
	    
	    return descriptor;
	}

}
