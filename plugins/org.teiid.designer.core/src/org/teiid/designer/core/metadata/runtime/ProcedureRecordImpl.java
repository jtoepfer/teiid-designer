/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.core.metadata.runtime;

import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.teiid.designer.core.index.IndexConstants;
import org.teiid.designer.core.metamodel.aspect.sql.SqlProcedureAspect;


/**
 * ProcedureRecordImpl
 *
 * @since 8.0
 */
public class ProcedureRecordImpl extends org.teiid.designer.metadata.runtime.impl.ProcedureRecordImpl {
    
    private static final long serialVersionUID = -1036361384376387789L;

	/**
	 * Flags to determine if values have been set.
	 */
	private boolean parameterIDsSet;
	private boolean isFunctionSet;
	private boolean isVirtualSet;
	private boolean resultSetIDSet;
    private boolean updateCountSet;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public ProcedureRecordImpl(final SqlProcedureAspect sqlAspect, final EObject eObject) {
		super(new ModelerMetadataRecordDelegate(sqlAspect, eObject));
		setRecordType(IndexConstants.RECORD_TYPE.CALLABLE);
		this.eObject = eObject;
	}

	private SqlProcedureAspect getProcedureAspect() {
		return (SqlProcedureAspect) ((ModelerMetadataRecordDelegate)this.delegate).getSqlAspect();			
	}

    /*
     * @see org.teiid.designer.metadata.runtime.ProcedureRecord#getParameterIDs()
     */
    @Override
    public List getParameterIDs() {
		if(eObject != null && !parameterIDsSet) {
			List params = getProcedureAspect().getParameters((EObject)eObject);
			setParameterIDs( ((ModelerMetadataRecordDelegate)this.delegate).getObjectIDs(params));    		
		}
        return super.getParameterIDs();
    }

    /*
     * @see org.teiid.designer.metadata.runtime.ProcedureRecord#isFunction()
     */
    @Override
    public boolean isFunction() {
		if(eObject != null && !isFunctionSet) {
			setFunction(getProcedureAspect().isFunction((EObject)eObject));
		}
        return super.isFunction();
    }

    /*
     * @see org.teiid.designer.metadata.runtime.ProcedureRecord#isVirtual()
     */
    @Override
    public boolean isVirtual() {
		if(eObject != null && !isVirtualSet) {
			setVirtual(getProcedureAspect().isVirtual((EObject)eObject));
		}
        return super.isVirtual();
    }

    /*
     * @see org.teiid.designer.metadata.runtime.ProcedureRecord#getResultSetID()
     */
    @Override
    public Object getResultSetID() {
		if(eObject != null && !resultSetIDSet) {
			EObject resultSet = (EObject)getProcedureAspect().getResult((EObject)eObject);
			setResultSetID( ((ModelerMetadataRecordDelegate)this.delegate).getObjectID(resultSet));    		
        }
        return super.getResultSetID();
    }

    /**
     * @param list
     */
    @Override
    public void setParameterIDs(List list) {
        super.setParameterIDs(list);
		parameterIDsSet = true;        
    }

    /**
     * @param object
     */
    @Override
    public void setResultSetID(Object object) {
        super.setResultSetID(object);
		resultSetIDSet = true;
    }

    /**
     * @param b
     */
    @Override
    public void setFunction(boolean b) {
        super.setFunction(b);
		isFunctionSet = true;
    }

    /**
     * @param b
     */
    @Override
    public void setVirtual(boolean b) {
        super.setVirtual(b);
		isVirtualSet = true;
    }

    /**
	  * @see org.teiid.designer.metadata.runtime.ProcedureRecord#getUpdateCount()
	  * @since 5.5.3
	 */
	 @Override
    public int getUpdateCount() {
		 if (eObject != null && !updateCountSet) {
			setUpdateCount(getProcedureAspect().getUpdateCount((EObject)eObject));
		 }
		 return super.getUpdateCount();
	 }

    @Override
    public void setUpdateCount(int count) {
		 super.setUpdateCount(count);
		 updateCountSet = true;
    }
}
