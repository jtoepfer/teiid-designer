/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.xsd.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsd.XSDPackage;
import org.teiid.designer.core.compare.EObjectMatcher;
import org.teiid.designer.core.compare.EObjectMatcherFactory;


/** 
 * @since 8.0
 */
public class XsdMatcherFactory implements EObjectMatcherFactory {

    private final List standardMatchers;
    private final EObjectMatcher facetMatcher = new XsdFacetToXsdFacetMatcher();

    /**
     * Construct an instance of XmlMatcherFactory.
     */
    public XsdMatcherFactory() {
        super();
        this.standardMatchers = new LinkedList();
    }

    /**
     * @see org.teiid.designer.core.compare.EObjectMatcherFactory#createEObjectMatchersForRoots()
     */
    @Override
	public List createEObjectMatchersForRoots() {
        this.standardMatchers.add(new XsdQNameToQNameMatcher());
        this.standardMatchers.add(new XsdQNameToQNameIgnoreCaseMatcher());
        this.standardMatchers.add(new XsdTagNameToTagNameMatcher());
        this.standardMatchers.add(new XsdTagNameToTagNameIgnoreCaseMatcher());
        // XSD objects can be roots, so return the matchers 
        return this.standardMatchers;
    }

    /**
     * @see org.teiid.designer.core.compare.EObjectMatcherFactory#createEObjectMatchers(org.eclipse.emf.ecore.EReference)
     */
    @Override
	public List createEObjectMatchers(final EReference reference) {
        // Make sure the reference is in the xsd metamodel ...
        final EClass containingClass = reference.getEContainingClass();
        final EPackage metamodel = containingClass.getEPackage();
        if ( !XSDPackage.eINSTANCE.equals(metamodel) ) {
            // The feature isn't in the xsd metamodel so return nothing ...
            return Collections.EMPTY_LIST;
        }
        
        final int refID = reference.getFeatureID();
        List result = new ArrayList();
        switch (refID) {
            case XSDPackage.XSD_SIMPLE_TYPE_DEFINITION__FACET_CONTENTS:
            case XSDPackage.XSD_SIMPLE_TYPE_DEFINITION__FUNDAMENTAL_FACETS:
            case XSDPackage.XSD_SIMPLE_TYPE_DEFINITION__SYNTHETIC_FACETS: {
                result.add(facetMatcher);
                break;
            }

            default: {
                result = standardMatchers;
                break;
            }
        }
        
        return result;
    }

}
