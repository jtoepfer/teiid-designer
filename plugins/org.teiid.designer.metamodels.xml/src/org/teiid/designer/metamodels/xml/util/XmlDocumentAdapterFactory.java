/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.xml.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.teiid.designer.metamodels.xml.ChoiceOption;
import org.teiid.designer.metamodels.xml.ProcessingInstruction;
import org.teiid.designer.metamodels.xml.ProcessingInstructionHolder;
import org.teiid.designer.metamodels.xml.XmlAll;
import org.teiid.designer.metamodels.xml.XmlAttribute;
import org.teiid.designer.metamodels.xml.XmlBaseElement;
import org.teiid.designer.metamodels.xml.XmlBuildable;
import org.teiid.designer.metamodels.xml.XmlChoice;
import org.teiid.designer.metamodels.xml.XmlComment;
import org.teiid.designer.metamodels.xml.XmlCommentHolder;
import org.teiid.designer.metamodels.xml.XmlContainerNode;
import org.teiid.designer.metamodels.xml.XmlDocument;
import org.teiid.designer.metamodels.xml.XmlDocumentEntity;
import org.teiid.designer.metamodels.xml.XmlDocumentNode;
import org.teiid.designer.metamodels.xml.XmlDocumentPackage;
import org.teiid.designer.metamodels.xml.XmlElement;
import org.teiid.designer.metamodels.xml.XmlEntityHolder;
import org.teiid.designer.metamodels.xml.XmlFragment;
import org.teiid.designer.metamodels.xml.XmlFragmentUse;
import org.teiid.designer.metamodels.xml.XmlHolderEntity;
import org.teiid.designer.metamodels.xml.XmlNamespace;
import org.teiid.designer.metamodels.xml.XmlRoot;
import org.teiid.designer.metamodels.xml.XmlSequence;
import org.teiid.designer.metamodels.xml.XmlValueHolder;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code> method for each
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.teiid.designer.metamodels.xml.XmlDocumentPackage
 * @generated
 *
 * @since 8.0
 */
public class XmlDocumentAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static XmlDocumentPackage modelPackage;

    /**
     * The switch the delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected XmlDocumentSwitch modelSwitch = new XmlDocumentSwitch() {
        @Override
        public Object caseChoiceOption( final ChoiceOption object ) {
            return createChoiceOptionAdapter();
        }

        @Override
        public Object caseProcessingInstruction( final ProcessingInstruction object ) {
            return createProcessingInstructionAdapter();
        }

        @Override
        public Object caseProcessingInstructionHolder( final ProcessingInstructionHolder object ) {
            return createProcessingInstructionHolderAdapter();
        }

        @Override
        public Object caseXmlAll( final XmlAll object ) {
            return createXmlAllAdapter();
        }

        @Override
        public Object caseXmlAttribute( final XmlAttribute object ) {
            return createXmlAttributeAdapter();
        }

        @Override
        public Object caseXmlBaseElement( final XmlBaseElement object ) {
            return createXmlBaseElementAdapter();
        }

        @Override
        public Object caseXmlBuildable( final XmlBuildable object ) {
            return createXmlBuildableAdapter();
        }

        @Override
        public Object caseXmlChoice( final XmlChoice object ) {
            return createXmlChoiceAdapter();
        }

        @Override
        public Object caseXmlComment( final XmlComment object ) {
            return createXmlCommentAdapter();
        }

        @Override
        public Object caseXmlCommentHolder( final XmlCommentHolder object ) {
            return createXmlCommentHolderAdapter();
        }

        @Override
        public Object caseXmlContainerNode( final XmlContainerNode object ) {
            return createXmlContainerNodeAdapter();
        }

        @Override
        public Object caseXmlDocument( final XmlDocument object ) {
            return createXmlDocumentAdapter();
        }

        @Override
        public Object caseXmlDocumentEntity( final XmlDocumentEntity object ) {
            return createXmlDocumentEntityAdapter();
        }

        @Override
        public Object caseXmlDocumentNode( final XmlDocumentNode object ) {
            return createXmlDocumentNodeAdapter();
        }

        @Override
        public Object caseXmlElement( final XmlElement object ) {
            return createXmlElementAdapter();
        }

        @Override
        public Object caseXmlEntityHolder( final XmlEntityHolder object ) {
            return createXmlEntityHolderAdapter();
        }

        @Override
        public Object caseXmlFragment( final XmlFragment object ) {
            return createXmlFragmentAdapter();
        }

        @Override
        public Object caseXmlFragmentUse( final XmlFragmentUse object ) {
            return createXmlFragmentUseAdapter();
        }

        @Override
        public Object caseXmlHolderEntity( final XmlHolderEntity object ) {
            return createXmlHolderEntityAdapter();
        }

        @Override
        public Object caseXmlNamespace( final XmlNamespace object ) {
            return createXmlNamespaceAdapter();
        }

        @Override
        public Object caseXmlRoot( final XmlRoot object ) {
            return createXmlRootAdapter();
        }

        @Override
        public Object caseXmlSequence( final XmlSequence object ) {
            return createXmlSequenceAdapter();
        }

        @Override
        public Object caseXmlValueHolder( final XmlValueHolder object ) {
            return createXmlValueHolderAdapter();
        }

        @Override
        public Object defaultCase( final EObject object ) {
            return createEObjectAdapter();
        }
    };

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public XmlDocumentAdapterFactory() {
        if (modelPackage == null) modelPackage = XmlDocumentPackage.eINSTANCE;
    }

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter( final Notifier target ) {
        return (Adapter)modelSwitch.doSwitch((EObject)target);
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.ChoiceOption <em>Choice Option</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a
     * case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.ChoiceOption
     * @generated
     */
    public Adapter createChoiceOptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null. <!--
     * end-user-doc -->
     * 
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.ProcessingInstruction
     * <em>Processing Instruction</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.ProcessingInstruction
     * @generated
     */
    public Adapter createProcessingInstructionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.ProcessingInstructionHolder
     * <em>Processing Instruction Holder</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.ProcessingInstructionHolder
     * @generated
     */
    public Adapter createProcessingInstructionHolderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlAll <em>Xml All</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlAll
     * @generated
     */
    public Adapter createXmlAllAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlAttribute <em>Xml Attribute</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a
     * case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlAttribute
     * @generated
     */
    public Adapter createXmlAttributeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlBaseElement <em>Xml Base Element</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
     * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlBaseElement
     * @generated
     */
    public Adapter createXmlBaseElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlBuildable <em>Xml Buildable</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a
     * case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlBuildable
     * @generated
     */
    public Adapter createXmlBuildableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlChoice <em>Xml Choice</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlChoice
     * @generated
     */
    public Adapter createXmlChoiceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlComment <em>Xml Comment</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlComment
     * @generated
     */
    public Adapter createXmlCommentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlCommentHolder
     * <em>Xml Comment Holder</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlCommentHolder
     * @generated
     */
    public Adapter createXmlCommentHolderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlContainerNode
     * <em>Xml Container Node</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlContainerNode
     * @generated
     */
    public Adapter createXmlContainerNodeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlDocument <em>Xml Document</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlDocument
     * @generated
     */
    public Adapter createXmlDocumentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlDocumentEntity <em>Entity</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlDocumentEntity
     * @generated
     */
    public Adapter createXmlDocumentEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlDocumentNode <em>Node</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlDocumentNode
     * @generated
     */
    public Adapter createXmlDocumentNodeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlElement <em>Xml Element</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlElement
     * @generated
     */
    public Adapter createXmlElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class <em>Xml Element Holder</em>}. 
     * 
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @generated
     */
    public Adapter createXmlElementHolderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlEntityHolder
     * <em>Xml Entity Holder</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlEntityHolder
     * @generated
     */
    public Adapter createXmlEntityHolderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlFragment <em>Xml Fragment</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlFragment
     * @generated
     */
    public Adapter createXmlFragmentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlFragmentUse <em>Xml Fragment Use</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
     * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlFragmentUse
     * @generated
     */
    public Adapter createXmlFragmentUseAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlHolderEntity
     * <em>Xml Holder Entity</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlHolderEntity
     * @generated
     */
    public Adapter createXmlHolderEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlNamespace <em>Xml Namespace</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a
     * case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlNamespace
     * @generated
     */
    public Adapter createXmlNamespaceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlRoot <em>Xml Root</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlRoot
     * @generated
     */
    public Adapter createXmlRootAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlSequence <em>Xml Sequence</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlSequence
     * @generated
     */
    public Adapter createXmlSequenceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.xml.XmlValueHolder <em>Xml Value Holder</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
     * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.xml.XmlValueHolder
     * @generated
     */
    public Adapter createXmlValueHolderAdapter() {
        return null;
    }

    /**
     * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This implementation returns
     * <code>true</code> if the object is either the model's package or is an instance object of the model. <!-- end-user-doc -->
     * 
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType( final Object object ) {
        if (object == modelPackage) return true;
        if (object instanceof EObject) return ((EObject)object).eClass().getEPackage() == modelPackage;
        return false;
    }

} // XmlDocumentAdapterFactory
