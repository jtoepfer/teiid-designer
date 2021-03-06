/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.core.compare;

import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.mapping.Mapping;
import org.eclipse.emf.mapping.MappingFactory;
import org.teiid.designer.metamodels.core.AnnotationContainer;
import org.teiid.designer.metamodels.core.ModelAnnotation;
import org.teiid.designer.metamodels.diagram.DiagramContainer;


/**
 * CoreModelImportMatcher
 *
 * @since 8.0
 */
public class CoreRootObjectMatcher extends AbstractEObjectMatcher {

    /**
     * Construct an instance of CoreAnnotationMatcher.
     * 
     */
    public CoreRootObjectMatcher() {
        super();
    }
    
    /**
     * @see org.teiid.designer.core.compare.EObjectMatcher#addMappingsForRoots(java.util.List, java.util.List, org.eclipse.emf.mapping.Mapping, org.eclipse.emf.mapping.MappingFactory)
     */
    @Override
	public void addMappingsForRoots(final List inputs, final List outputs,
                                    final Mapping mapping, final MappingFactory factory) {
        ModelAnnotation inputModelAnnotation = null;
        AnnotationContainer inputAnnotationContainer = null;
        DiagramContainer inputDiagramContainer = null;

        // Loop over the inputs and find any of the above objects ...
        final Iterator iter = inputs.iterator();
        while (iter.hasNext()) {
            final Object obj = iter.next();
            if ( obj instanceof ModelAnnotation ) {
                inputModelAnnotation = (ModelAnnotation)obj;
            } else if ( obj instanceof AnnotationContainer ) {
                inputAnnotationContainer = (AnnotationContainer)obj;
            } else if ( obj instanceof DiagramContainer ) {
                inputDiagramContainer = (DiagramContainer)obj;
            }
        }
        
        // Loop over the outputs and find matches for any of the above objects ...
        final Iterator outputIter = outputs.iterator();
        while (outputIter.hasNext()) {
            final Object obj = outputIter.next();
            if ( obj instanceof ModelAnnotation ) {
                if ( inputModelAnnotation != null ) {
                    outputIter.remove();
                    inputs.remove(inputModelAnnotation);
                    addMapping(inputModelAnnotation,(EObject)obj,mapping,factory);
                }
            } else if ( obj instanceof AnnotationContainer ) {
                if ( inputAnnotationContainer != null ) {
                    outputIter.remove();
                    inputs.remove(inputAnnotationContainer);
                    addMapping(inputAnnotationContainer,(EObject)obj,mapping,factory);
                }
            } else if ( obj instanceof DiagramContainer ) {
                if ( inputDiagramContainer != null ) {
                    outputIter.remove();
                    inputs.remove(inputDiagramContainer);
                    addMapping(inputDiagramContainer,(EObject)obj,mapping,factory);
                }
            }
        }
    }
    
    /**
     * @see org.teiid.designer.core.compare.EObjectMatcher#addMappings(org.eclipse.emf.ecore.EReference, java.util.List, java.util.List, org.eclipse.emf.mapping.Mapping, org.eclipse.emf.mapping.MappingFactory)
     */
    @Override
	public void addMappings( final EReference reference, final List inputs, final List outputs, 
                             final Mapping mapping, final MappingFactory factory) {
        // only processes roots ...
    }
    
}
