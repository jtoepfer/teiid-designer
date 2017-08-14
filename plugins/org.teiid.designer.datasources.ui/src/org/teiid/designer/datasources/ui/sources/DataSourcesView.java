/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.designer.datasources.ui.sources;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class DataSourcesView extends ViewPart {

    private Composite control;
    
    GlobalConnectionManager manager;

    /**
     * The constructor.
     */
    public DataSourcesView() {
        super();
    }

    /**
     * This is a call-back that will allow us to create the viewer and initialize it.
     */
    @Override
    public void createPartControl( Composite parent ) {
        control = new Composite(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().margins(0, 0).applyTo(control);

        new DataSourcesPanel(control, manager);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus() {
        if (control != null) {
            control.setFocus();
        }
    }
}
