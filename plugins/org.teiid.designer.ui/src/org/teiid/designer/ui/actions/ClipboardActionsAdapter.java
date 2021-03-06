/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.ui.actions;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.teiid.designer.ui.UiConstants;
import org.teiid.designer.ui.views.DescriptionView;

/**
 * ClipboardActionsAdapter This class can be used by Views that contain controls that need local Cut, Copy, Paste, SelectAll, Undo
 * and Redo actions. Because of an Eclipse/SWT problem, the keyboard accelerators for those actions are not automatically
 * supported in Views.
 * 
 * @see DescriptionView
 *
 * @since 8.0
 */
public class ClipboardActionsAdapter {
    //
    // Class constants:
    //
    private static final String MENU_CUT = UiConstants.Util.getString("org.teiid.designer.ui.actions.CutAction.text"); //$NON-NLS-1$
    private static final String MENU_COPY = UiConstants.Util.getString("org.teiid.designer.ui.actions.CopyAction.text"); //$NON-NLS-1$
    private static final String MENU_PASTE = UiConstants.Util.getString("org.teiid.designer.ui.actions.PasteAction.text"); //$NON-NLS-1$
    private static final String MENU_DELETE = UiConstants.Util.getString("org.teiid.designer.ui.actions.DeleteAction.text"); //$NON-NLS-1$
    private static final String MENU_SELECT_ALL = UiConstants.Util.getString("org.teiid.designer.ui.actions.SelectAllAction.text"); //$NON-NLS-1$

    //
    // Instance variables:
    //
    private final Control ctrl;
    final ClipboardActionHandler clipboardActionHandler;
    MenuItem cut;
    MenuItem copy;
    MenuItem paste;
    MenuItem delete;
    MenuItem selectall;

    //
    // Constructors:
    //
    public ClipboardActionsAdapter( Control control,
                                    ClipboardActionHandler clipHandler ) {
        ctrl = control;
        clipboardActionHandler = clipHandler;

        initContextMenu(); // note that disposal of the menu is handled by Control itself...
        clipboardActionHandler.addSelectionChangedListener(new MySelectionChangedListener());
    }

    //
    // Methods:
    //
    public Control getControl() {
        return ctrl;
    }

    /**
     * Creates and registers the context menu. Override so that we can get the context menu identifier we want.
     */
    private void initContextMenu() {
        // menu setup:
        Menu menu = new Menu(ctrl.getShell(), SWT.POP_UP);
        ctrl.setMenu(menu);

        // cut:
        cut = new MenuItem(menu, SWT.PUSH);
        cut.setText(MENU_CUT);
        cut.setEnabled(false);
        cut.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                clipboardActionHandler.cut();
            }
        });

        // copy:
        copy = new MenuItem(menu, SWT.PUSH);
        copy.setText(MENU_COPY);
        copy.setEnabled(false);
        copy.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                clipboardActionHandler.copy();
            }
        });

        // paste:
        paste = new MenuItem(menu, SWT.PUSH);
        paste.setText(MENU_PASTE);
        paste.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                clipboardActionHandler.paste();
            }
        });

        // delete:
        delete = new MenuItem(menu, SWT.PUSH);
        delete.setText(MENU_DELETE);
        delete.setEnabled(false);
        delete.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                clipboardActionHandler.delete();
            }
        });

        // separator:
        new MenuItem(menu, SWT.SEPARATOR);

        // selectall:
        selectall = new MenuItem(menu, SWT.PUSH);
        selectall.setText(MENU_SELECT_ALL);
        selectall.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                clipboardActionHandler.selectAll();
            }
        });
    }

    //
    // Inner classes:
    //
    class MySelectionChangedListener implements ISelectionChangedListener {
        @Override
		public void selectionChanged( SelectionChangedEvent event ) {
            boolean empty = event.getSelection().isEmpty();
            cut.setEnabled(!empty);
            copy.setEnabled(!empty);
            delete.setEnabled(!empty);
        }
    }
}
