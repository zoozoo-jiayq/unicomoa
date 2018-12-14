/**
 * 
 */
package org.jbpm.pvm.internal.wire.binding;

import org.jbpm.pvm.internal.wire.descriptor.JpaEntityManagerDescriptor;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;

/**
 * @author Santanu
 *
 */
public class JpaEntityManagerBinding extends WireDescriptorBinding {

	public static final String TAG = "entity-manager";
	
	public JpaEntityManagerBinding() {
		super(TAG);
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.xml.Binding#parse(org.w3c.dom.Element, org.jbpm.pvm.internal.xml.Parse, org.jbpm.pvm.internal.xml.Parser)
	 */
	@Override
	public Object parse(Element element, Parse parse, Parser parser) {
		JpaEntityManagerDescriptor descriptor = new JpaEntityManagerDescriptor();
		
		if (element.hasAttribute("accessor-class")) {
			descriptor.setAccessorClassName(element.getAttribute("accessor-class"));
		}
		if (element.hasAttribute("create-new")) {
			descriptor.setCreateNew(Boolean.valueOf(element.getAttribute("create-new")));
		}
		return descriptor;
	}

}
