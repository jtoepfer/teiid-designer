/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.core.index;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * ResourceDocument
 *
 * @since 8.0
 */
public interface ModelDocument extends ResourceDocument {

    /**
     * Get the {@link org.eclipse.emf.ecore.resource.Resource} 
     * instance that this document contains
     * @return Resource for this document
     */
	Resource getResource();
}
