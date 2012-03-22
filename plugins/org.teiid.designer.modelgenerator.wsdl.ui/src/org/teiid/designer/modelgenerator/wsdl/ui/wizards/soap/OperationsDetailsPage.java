/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.modelgenerator.wsdl.ui.wizards.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.impl.XSDElementDeclarationImpl;
import org.eclipse.xsd.impl.XSDParticleImpl;
import org.eclipse.xsd.provider.XSDSemanticItemProviderAdapterFactory;
import org.teiid.designer.modelgenerator.wsdl.ui.Messages;
import org.teiid.designer.modelgenerator.wsdl.ui.wizards.soap.panels.ColumnsInfoPanel;
import org.teiid.designer.modelgenerator.wsdl.ui.wizards.soap.panels.ElementsInfoPanel;
import org.teiid.designer.modelgenerator.wsdl.ui.wizards.soap.panels.WrapperProcedurePanel;

import com.metamatrix.core.util.CoreStringUtil;
import com.metamatrix.modeler.core.types.DatatypeConstants;
import com.metamatrix.modeler.modelgenerator.wsdl.model.Model;
import com.metamatrix.modeler.modelgenerator.wsdl.model.ModelGenerationException;
import com.metamatrix.modeler.modelgenerator.wsdl.model.Operation;
import com.metamatrix.modeler.modelgenerator.wsdl.model.Part;
import com.metamatrix.modeler.modelgenerator.wsdl.schema.extensions.SOAPSchemaProcessor;
import com.metamatrix.modeler.modelgenerator.wsdl.ui.ModelGeneratorWsdlUiConstants;
import com.metamatrix.modeler.modelgenerator.wsdl.ui.internal.util.ModelGeneratorWsdlUiUtil;
import com.metamatrix.modeler.modelgenerator.wsdl.ui.internal.wizards.WSDLImportWizardManager;
import com.metamatrix.modeler.schema.tools.model.schema.SchemaModel;
import com.metamatrix.modeler.schema.tools.model.schema.SchemaObject;
import com.metamatrix.modeler.schema.tools.model.schema.impl.BaseSchemaObject;
import com.metamatrix.modeler.schema.tools.model.schema.impl.SimpleRelationship;
import com.metamatrix.modeler.schema.tools.processing.SchemaProcessingException;
import com.metamatrix.modeler.schema.tools.processing.SchemaProcessor;
import com.metamatrix.modeler.transformation.ui.editors.sqleditor.SqlTextViewer;
import com.metamatrix.ui.graphics.ColorManager;
import com.metamatrix.ui.internal.util.WidgetFactory;
import com.metamatrix.ui.internal.util.WidgetUtil;
import com.metamatrix.ui.internal.util.WizardUtil;
import com.metamatrix.ui.internal.wizard.AbstractWizardPage;

public class OperationsDetailsPage extends AbstractWizardPage implements
		ModelGeneratorWsdlUiConstants {

	/** <code>IDialogSetting</code>s key for saved dialog height. */
	private static final String DIALOG_HEIGHT = "dialogHeight"; //$NON-NLS-1$

	/** <code>IDialogSetting</code>s key for saved dialog width. */
	private static final String DIALOG_WIDTH = "dialogWidth"; //$NON-NLS-1$

	/** <code>IDialogSetting</code>s key for saved dialog X position. */
	private static final String DIALOG_X = "dialogX"; //$NON-NLS-1$

	/** <code>IDialogSetting</code>s key for saved dialog Y position. */
	private static final String DIALOG_Y = "dialogY"; //$NON-NLS-1$

	private static final int REQUEST = ProcedureInfo.REQUEST;
	private static final int RESPONSE = ProcedureInfo.RESPONSE;
	private static final int BOTH = 2;

	/** The import manager. */
	WSDLImportWizardManager importManager;

	// ========== UI COMPONENTS =========================

	/** The Operations Combo Selector */
	private Combo operationsCombo;

	private XSDSemanticItemProviderAdapterFactory semanticAdapterFactory;
	private AdapterFactoryLabelProvider schemaLabelProvider;
	private SchemaTreeContentProvider schemaContentProvider;

	/** This keeps track of the root object of the model. */
	protected XSDSchema xsdSchema;
	// protected XSD xsdSchema1;

	TabFolder tabFolder;

	TabItem requestTab;
	Text requestProcedureNameText;
	TreeViewer requestXmlTreeViewer;
	TextViewer requestSqlTextViewer;
	IDocument requestSqlDocument;
	Action requestCreateElementAction;
	// Button requestAddElementButton;
	ElementsInfoPanel requestElementsInfoPanel;

	TabItem responseTab;
	Text responseProcedureNameText;
	TreeViewer responseXmlTreeViewer;
	TextViewer responseSqlTextViewer;
	IDocument responseSqlDocument;
	Action responseCreateElementAction;
	// Button responseAddElementButton;
	ColumnsInfoPanel responseElementsInfoPanel;

	TabFolder wrapperTab;
	WrapperProcedurePanel wrapperPanel;
	Button overwriteExistingCB;

	private ProcedureGenerator procedureGenerator;

	// ==================================================
	public OperationsDetailsPage(WSDLImportWizardManager theImportManager) {
		super(OperationsDetailsPage.class.getSimpleName(), Messages.ProcedureDefinition);
		this.importManager = theImportManager;
		this.importManager.setSelectedOperations(new ArrayList());
		setImageDescriptor(ModelGeneratorWsdlUiUtil.getImageDescriptor(Images.NEW_MODEL_BANNER));

		semanticAdapterFactory = new XSDSemanticItemProviderAdapterFactory();
		schemaLabelProvider = new AdapterFactoryLabelProvider(semanticAdapterFactory);
		schemaContentProvider = new SchemaTreeContentProvider(semanticAdapterFactory);
	}

	public ProcedureGenerator getProcedureGenerator() {
		return this.procedureGenerator;
	}

	private void notifyOperationChanged(Operation operation) {
		this.procedureGenerator = importManager.getProcedureGenerator(operation);

		this.wrapperPanel.notifyOperationChanged(operation);

		this.requestProcedureNameText.setText(this.procedureGenerator.getRequestProcedureName());
		this.responseProcedureNameText.setText(this.procedureGenerator.getResponseProcedureName());

		// Now update the two column info panels
		this.requestElementsInfoPanel.setProcedureInfo(this.procedureGenerator.getRequestInfo());
		this.responseElementsInfoPanel.setProcedureInfo(this.procedureGenerator.getResponseInfo());
		
		this.overwriteExistingCB.setSelection(this.procedureGenerator.doOverwriteExistingProcedures());
		this.overwriteExistingCB.setEnabled(this.importManager.viewModelExists());

		updateSqlText(BOTH);
		updateSchemaTree(BOTH);

		updateStatus();
	}

	public void notifyColumnDataChanged() {
		this.requestElementsInfoPanel.refresh();
		this.responseElementsInfoPanel.refresh();
		updateSqlText(BOTH);
		this.wrapperPanel.notifyOperationChanged(this.getProcedureGenerator()
				.getOperation());

		updateStatus();
	}

	public void updateStatus() {
		setPageStatus();
	}

	public WSDLImportWizardManager getImportManager() {
		return this.importManager;
	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 * @since 4.2
	 */
	public void createControl(Composite theParent) {
		Composite pnlMain = WidgetFactory.createPanel(theParent, SWT.NONE,
				GridData.FILL_BOTH);
		GridLayout layout = new GridLayout(2, false);
		pnlMain.setLayout(layout);
		setControl(pnlMain);

		createOperationsSelectionPanel(pnlMain);

		createTabbedDetailsPanel(pnlMain);

		restoreState();
	}

	@SuppressWarnings("unused")
	private void createOperationsSelectionPanel(Composite parent) {
		Group operationsGroup = WidgetFactory.createGroup(parent, Messages.Operations, GridData.FILL_BOTH, 2, 2);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		operationsGroup.setLayoutData(gd);

		ACTION_COMBO: {
			operationsCombo = new Combo(operationsGroup, SWT.NONE | SWT.READ_ONLY);
			operationsCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			gd.horizontalSpan = 2;
			operationsCombo.setLayoutData(gd);

			operationsCombo.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent ev) {
					selectComboItem(operationsCombo.getSelectionIndex());
				}
			});
		}

		overwriteExistingCB = new Button(operationsGroup, SWT.CHECK);
		overwriteExistingCB.setText(Messages.OverwriteExistingProcedures);
		gd.horizontalSpan = 2;
		overwriteExistingCB.setLayoutData(gd);
		overwriteExistingCB.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleOverwriteSelected();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		this.overwriteExistingCB.setEnabled(this.importManager.viewModelExists());
		
	}

	private List<String> getOperationsNameList() {
		List<String> nameList = new ArrayList<String>();
		for (Operation op : this.importManager.getSelectedOperations()) {
			nameList.add(op.getName());
		}
		return nameList;
	}

	private void createTabbedDetailsPanel(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.TOP | SWT.BORDER);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

		createRequestTab(tabFolder);
		createResponseTab(tabFolder);
		createWrapperTab(tabFolder);
	}

	private void createRequestTab(TabFolder tabFolder) {
		Composite panel = WidgetFactory.createPanel(tabFolder);
		this.requestTab = new TabItem(tabFolder, SWT.NONE);
		this.requestTab.setControl(panel);
		this.requestTab.setText(Messages.Request);

		panel.setLayout(new GridLayout(2, false));

		Composite namePanel = WidgetFactory.createPanel(panel);
		namePanel.setLayout(new GridLayout(2, false));
		GridData namePanelGD = new GridData(GridData.FILL_HORIZONTAL);
		namePanelGD.horizontalSpan = 2;
		namePanel.setLayoutData(namePanelGD);

		Label procedureNameLabel = new Label(namePanel, SWT.NONE);
		procedureNameLabel.setText(Messages.GeneratedProcedureName);

		requestProcedureNameText = new Text(namePanel, SWT.BORDER | SWT.SINGLE);
		requestProcedureNameText.setBackground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		requestProcedureNameText.setForeground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_DARK_BLUE));
		requestProcedureNameText.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		requestProcedureNameText.setEditable(false);

		createRequestSplitter(panel);

		createRequestSqlGroup(panel);
	}

	private void createRequestSplitter(Composite parent) {
		SashForm splitter = new SashForm(parent, SWT.HORIZONTAL);
		GridData gid = new GridData();
		gid.grabExcessHorizontalSpace = gid.grabExcessVerticalSpace = true;
		gid.horizontalAlignment = gid.verticalAlignment = GridData.FILL;
		splitter.setLayoutData(gid);

		createRequestSchemaContentsGroup(splitter);
		createRequestElementsInfoGroup(splitter);

		splitter.setWeights(new int[] { 60, 40 });
	}

	private void createRequestSchemaContentsGroup(Composite parent) {
		Group schemaContentsGroup = WidgetFactory.createGroup(parent,
				Messages.SchemaContents, SWT.NONE, 1, 4);
		schemaContentsGroup.setLayout(new GridLayout(4, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 1;
		gd.heightHint = 120;
		schemaContentsGroup.setLayoutData(gd);

		this.requestXmlTreeViewer = new TreeViewer(schemaContentsGroup,
				SWT.SINGLE);

		this.requestXmlTreeViewer.setContentProvider(schemaContentProvider);
		this.requestXmlTreeViewer.setLabelProvider(schemaLabelProvider);
		this.requestXmlTreeViewer.setAutoExpandLevel(3);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 4;
		this.requestXmlTreeViewer.getControl().setLayoutData(data);
		this.requestXmlTreeViewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				// filter built-ins if needed:
				if (!(parentElement instanceof EObject)
						&& element instanceof XSDSimpleTypeDefinition) {
					// parent is not an EObject, and kid is a STD; need to
					// filter out built-ins.
					XSDSimpleTypeDefinition std = (XSDSimpleTypeDefinition) element;
					return std.getSchema() == xsdSchema;
				} // endif

				return true;
			}
		});
		this.requestXmlTreeViewer.setInput(null);

		// Add a Context Menu
		final MenuManager columnMenuManager = new MenuManager();
		this.requestXmlTreeViewer.getControl().setMenu(
				columnMenuManager.createContextMenu(parent));
		this.requestXmlTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see oblafond@redhat.comrg.eclipse.jface.viewers.
					 *      ISelectionChangedListener
					 *      #selectionChanged(org.eclipse
					 *      .jface.viewers.SelectionChangedEvent)
					 */
					@Override
					public void selectionChanged(
							final SelectionChangedEvent event) {
						columnMenuManager.removeAll();
						IStructuredSelection sel = (IStructuredSelection) requestXmlTreeViewer
								.getSelection();
						if (sel.size() == 1
								&& sel.getFirstElement() instanceof XSDParticleImpl) {
							columnMenuManager.add(requestCreateElementAction);
						}

					}
				});
		
		this.requestXmlTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				if( selection != null && !selection.isEmpty() && selection.getFirstElement() instanceof XSDParticleImpl ) {
					createRequestColumn();
				}
			}
		});

		this.requestCreateElementAction = new Action(Messages.AddAsNewElement) {
			@Override
			public void run() {
				
			}
		};
	}

	private void createRequestElementsInfoGroup(Composite parent) {
		requestElementsInfoPanel = new ElementsInfoPanel(parent, SWT.NONE,
				REQUEST, this);
	}

	private void createRequestSqlGroup(Composite parent) {
		Group group = WidgetFactory.createGroup(parent,
				Messages.GeneratedSQLStatement, SWT.NONE, 2);
		group.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);

		ColorManager colorManager = new ColorManager();
		int styles = SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.FULL_SELECTION;

		requestSqlTextViewer = new SqlTextViewer(group, new VerticalRuler(0),
				styles, colorManager);
		requestSqlDocument = new Document();
		requestSqlTextViewer.setInput(requestSqlDocument);
		requestSqlTextViewer.setEditable(false);
		requestSqlTextViewer.getTextWidget().setBackground(
				Display.getCurrent().getSystemColor(
						SWT.COLOR_WIDGET_LIGHT_SHADOW));
		requestSqlDocument.set(CoreStringUtil.Constants.EMPTY_STRING);
		requestSqlTextViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_BOTH));
	}

	private void createResponseTab(TabFolder tabFolder) {
		Composite panel = WidgetFactory.createPanel(tabFolder);
		this.responseTab = new TabItem(tabFolder, SWT.NONE);
		this.responseTab.setControl(panel);
		this.responseTab.setText(Messages.Response);

		panel.setLayout(new GridLayout(2, false));

		Composite namePanel = WidgetFactory.createPanel(panel);
		namePanel.setLayout(new GridLayout(2, false));
		GridData namePanelGD = new GridData(GridData.FILL_HORIZONTAL);
		namePanelGD.horizontalSpan = 2;
		namePanel.setLayoutData(namePanelGD);

		Label procedureNameLabel = new Label(namePanel, SWT.NONE);
		procedureNameLabel.setText(Messages.GeneratedProcedureName);

		responseProcedureNameText = new Text(namePanel, SWT.BORDER | SWT.SINGLE);
		responseProcedureNameText.setBackground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		responseProcedureNameText.setForeground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_DARK_BLUE));
		responseProcedureNameText.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		responseProcedureNameText.setEditable(false);

		createResponseSplitter(panel);

		createResponseSqlGroup(panel);
	}

	private void createResponseSplitter(Composite parent) {
		SashForm splitter = new SashForm(parent, SWT.HORIZONTAL);
		GridData gid = new GridData();
		gid.grabExcessHorizontalSpace = gid.grabExcessVerticalSpace = true;
		gid.horizontalAlignment = gid.verticalAlignment = GridData.FILL;
		splitter.setLayoutData(gid);

		createResponseSchemaContentsGroup(splitter);
		createResponseColumnInfoGroup(splitter);

		splitter.setWeights(new int[] { 40, 60 });
	}

	private void createResponseSchemaContentsGroup(Composite parent) {
		Group schemaContentsGroup = WidgetFactory.createGroup(parent,
				Messages.SchemaContents, SWT.NONE, 2, 4);
		schemaContentsGroup.setLayout(new GridLayout(4, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 120;
		gd.horizontalSpan = 1;
		schemaContentsGroup.setLayoutData(gd);

		responseXmlTreeViewer = new TreeViewer(schemaContentsGroup, SWT.SINGLE);
		semanticAdapterFactory = new XSDSemanticItemProviderAdapterFactory();
		this.responseXmlTreeViewer.setContentProvider(schemaContentProvider);
		this.responseXmlTreeViewer.setLabelProvider(schemaLabelProvider);
		this.responseXmlTreeViewer.setAutoExpandLevel(3);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 4;
		this.responseXmlTreeViewer.getControl().setLayoutData(data);
		this.responseXmlTreeViewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				// filter built-ins if needed:
				if (!(parentElement instanceof EObject)
						&& element instanceof XSDSimpleTypeDefinition) {
					// parent is not an EObject, and kid is a STD; need to
					// filter out built-ins.
					XSDSimpleTypeDefinition std = (XSDSimpleTypeDefinition) element;
					return std.getSchema() == xsdSchema;
				} // endif

				return true;
			}
		});
		this.responseXmlTreeViewer.setInput(null);

		// Add a Context Menu
		final MenuManager columnMenuManager = new MenuManager();
		this.responseXmlTreeViewer.getControl().setMenu(
				columnMenuManager.createContextMenu(parent));
		this.responseXmlTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see oblafond@redhat.comrg.eclipse.jface.viewers.
					 *      ISelectionChangedListener
					 *      #selectionChanged(org.eclipse
					 *      .jface.viewers.SelectionChangedEvent)
					 */
					@Override
					public void selectionChanged(
							final SelectionChangedEvent event) {
						columnMenuManager.removeAll();
						IStructuredSelection sel = (IStructuredSelection) responseXmlTreeViewer
								.getSelection();
						if (sel.size() == 1
								&& sel.getFirstElement() instanceof XSDParticleImpl) {
							columnMenuManager.add(responseCreateElementAction);
						}

					}
				});
		this.responseXmlTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				if( selection != null && !selection.isEmpty() && selection.getFirstElement() instanceof XSDParticleImpl ) {
					createResponseColumn();
				}
			}
		});

		this.responseCreateElementAction = new Action(Messages.AddAsNewElement) {
			@Override
			public void run() {
				createResponseColumn();
			}
		};
	}

	private void createResponseColumnInfoGroup(Composite parent) {
		responseElementsInfoPanel = new ColumnsInfoPanel(parent, SWT.NONE,
				RESPONSE, this);
	}

	private void createResponseSqlGroup(Composite parent) {
		Group group = WidgetFactory.createGroup(parent,
				Messages.GeneratedSQLStatement, SWT.NONE, 2);
		group.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);

		ColorManager colorManager = new ColorManager();
		int styles = SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.FULL_SELECTION;

		responseSqlTextViewer = new SqlTextViewer(group, new VerticalRuler(0),
				styles, colorManager);
		responseSqlDocument = new Document();
		responseSqlTextViewer.setInput(responseSqlDocument);
		responseSqlTextViewer.setEditable(false);
		responseSqlTextViewer.getTextWidget().setBackground(
				Display.getCurrent().getSystemColor(
						SWT.COLOR_WIDGET_LIGHT_SHADOW));
		responseSqlDocument.set(CoreStringUtil.Constants.EMPTY_STRING);
		responseSqlTextViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_BOTH));
	}

	private void createWrapperTab(TabFolder tabFolder) {
		Composite panel = WidgetFactory.createPanel(tabFolder);
		this.responseTab = new TabItem(tabFolder, SWT.NONE);
		this.responseTab.setControl(panel);
		this.responseTab.setText(Messages.WrapperProcedure);

		panel.setLayout(new GridLayout(1, false));

		wrapperPanel = new WrapperProcedurePanel(panel, this);
	}

	private void selectComboItem(int selectionIndex) {
		if (selectionIndex >= 0) {
			operationsCombo.select(selectionIndex);
			String operationName = operationsCombo.getItem(selectionIndex);

			for (Operation op : this.importManager.getSelectedOperations()) {
				if (op.getName().equalsIgnoreCase(operationName)) {
					notifyOperationChanged(op);
					break;
				}
			}
		}
	}
	
	private void handleOverwriteSelected() {
		// For the selected operation, set the procedure generator's value
		if( this.getProcedureGenerator() != null ) {
			this.getProcedureGenerator().setOverwriteExistingProcedures(this.overwriteExistingCB.getSelection());
			notifyOperationChanged(this.getProcedureGenerator().getOperation());
		}
	}

	void updateSqlText(int type) {
		if (this.procedureGenerator != null) {
			if (type == REQUEST) {
				requestSqlTextViewer.getDocument().set(
						this.procedureGenerator.getRequestInfo().getSqlString(
								new Properties()));
			} else if (type == RESPONSE) {
				responseSqlTextViewer.getDocument().set(
						this.procedureGenerator.getResponseInfo().getSqlString(
								new Properties()));
			} else if (type == BOTH) {
				requestSqlTextViewer.getDocument().set(
						this.procedureGenerator.getRequestInfo().getSqlString(
								new Properties()));
				responseSqlTextViewer.getDocument().set(
						this.procedureGenerator.getResponseInfo().getSqlString(
								new Properties()));
			}
		}
	}

	void updateSchemaTree(int type) {
		if (type == REQUEST) {
			requestXmlTreeViewer.setInput(getSchemaForSelectedOperation(type));
		} else if (type == RESPONSE) {
			responseXmlTreeViewer.setInput(getSchemaForSelectedOperation(type));
		} else {
			requestXmlTreeViewer
					.setInput(getSchemaForSelectedOperation(REQUEST));
			responseXmlTreeViewer
					.setInput(getSchemaForSelectedOperation(RESPONSE));
		}
	}

	/**
	 * @return
	 */
	public List getSchemaForSelectedOperation(final int type) {

		Model wsdlModel = null;
		XSDTypeDefinition elementDeclaration = null;

		try {
			wsdlModel = importManager.getWSDLModel();
		} catch (ModelGenerationException e) {
			throw new RuntimeException(e);
		}

		XSDSchema[] schemas = wsdlModel.getSchemas();

		Operation selectedOperation = procedureGenerator.getOperation();
		String partElementName = null;
		Part[] partArray = null;
       
		
		
		if (type == REQUEST) {
			if( selectedOperation.getInputMessage() != null ) {
				partArray = selectedOperation.getInputMessage().getParts();
    		}
		} else {
			if( selectedOperation.getOutputMessage() != null ) {
				partArray = selectedOperation.getOutputMessage().getParts();
			}
		}
		
	    List elementArrayList = new ArrayList();
        
        for (Part part:partArray) {
        	partElementName = getPartElementName(part);
        
	        for (XSDSchema schema : schemas) {
				xsdSchema = schema;
				EList<XSDTypeDefinition> types = schema.getTypeDefinitions();
				for (XSDTypeDefinition xsdType : types) {
					String elementName = xsdType.getName();
					if (elementName.equals(partElementName)) {
						elementDeclaration = xsdType;
						break;
					}
				}
				
				if (elementDeclaration == null) {
					
				EList<XSDElementDeclaration> elements = schema
						.getElementDeclarations();
				for (XSDElementDeclaration element : elements) {
					String elementName = element.getName();
					if (elementName.equals(partElementName)) {
						elementDeclaration = element.getTypeDefinition();
						break;
					}
				}
				}
				elementArrayList.add(elementDeclaration);
			}
	    }

		return elementArrayList;
	}

	private String getPartElementName(Part part) {
		String partElementName = null;
		
		partElementName = part.getTypeName();
	    if (partElementName == null){
			partElementName = part.getElementName();
	    }
		
		return partElementName;
	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 * @since 4.2
	 */
	@Override
	public void dispose() {
		saveState();
	}

	/**
	 * Override to replace the NewModelWizard settings with the section devoted
	 * to the Web Service Model Wizard.
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#getDialogSettings()
	 * @since 4.2
	 */
	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = super.getDialogSettings();

		if (settings != null) {
			// get the right section of the NewModelWizard settings
			IDialogSettings temp = settings.getSection(DIALOG_SETTINGS_SECTION);

			if (temp == null) {
				settings = settings.addNewSection(DIALOG_SETTINGS_SECTION);
			} else {
				settings = temp;
			}
		}

		return settings;
	}

	/**
	 * Restores dialog size and position of the last time wizard ran.
	 * 
	 * @since 4.2
	 */
	private void restoreState() {
		IDialogSettings settings = getDialogSettings();

		if (settings != null) {
			Shell shell = getContainer().getShell();

			if (shell != null) {
				try {
					int x = settings.getInt(DIALOG_X);
					int y = settings.getInt(DIALOG_Y);
					int width = settings.getInt(DIALOG_WIDTH);
					int height = settings.getInt(DIALOG_HEIGHT);
					shell.setBounds(x, y, width, height);
				} catch (NumberFormatException theException) {
					// getInt(String) throws exception if not found.
					// just means no settings exist yet.
				}
			}
		}
	}

	/**
	 * Persists dialog size and position.
	 * 
	 * @since 4.2
	 */
	private void saveState() {
		IDialogSettings settings = getDialogSettings();

		if (settings != null) {
			Shell shell = getContainer().getShell();

			if (shell != null) {
				Rectangle r = shell.getBounds();
				settings.put(DIALOG_X, r.x);
				settings.put(DIALOG_Y, r.y);
				settings.put(DIALOG_WIDTH, r.width);
				settings.put(DIALOG_HEIGHT, r.height);
			}
		}
	}

	/**
	 * Sets the wizard page status message.
	 * 
	 * @since 4.2
	 */
	void setPageStatus() {
		// TODO:

		IStatus generatorStatus = procedureGenerator.validate();
		if (generatorStatus.isOK()
				|| generatorStatus.getSeverity() == IStatus.INFO) {
			this.setErrorMessage(null);
			WizardUtil.setPageComplete(this);
		} else {
			WizardUtil.setPageComplete(this, generatorStatus.getMessage(),
					generatorStatus.getSeverity());
			this.setErrorMessage(generatorStatus.getMessage());
			this.setPageComplete(false);
		}

		getContainer().updateButtons();
	}

	@Override
	public void setVisible(boolean isVisible) {
		if (isVisible) {
			WidgetUtil.setComboItems(operationsCombo, getOperationsNameList(),
					null, true);

			selectComboItem(0);

			setPageStatus();
		}
		super.setVisible(isVisible);
		this.wrapperPanel.setVisible();
		this.overwriteExistingCB.setEnabled(this.importManager.viewModelExists());
	}
    
    public void updateDesignerProperties() {

    }

	Object[] getNodeChildren(Object element) {
		return new Object[0];
	}

	boolean getNodeHasChildren(Object element) {
		return false;
	}

	Image getNodeImage(Object element) {
		return null;
	}

	String getNodeName(Object element) {
		return "<name>"; //$NON-NLS-1$
	}

	Object getNodeParent(Object element) {
		return null;
	}

	public String createRequestColumn() {
		IStructuredSelection sel = (IStructuredSelection) requestXmlTreeViewer
				.getSelection();

		Object obj = sel.getFirstElement();
		if (obj instanceof XSDParticleImpl
				&& ((XSDParticleImpl) obj).getContent() instanceof XSDElementDeclarationImpl) {
			String name = ((XSDElementDeclarationImpl) ((XSDParticleImpl) obj)
					.getContent()).getName();
			String ns = ((XSDElementDeclarationImpl) ((XSDParticleImpl) obj)
					.getContent()).getTargetNamespace();
			this.procedureGenerator.getRequestInfo().addColumn(name, false,
					DatatypeConstants.RuntimeTypeNames.STRING, null, ns);
			notifyColumnDataChanged();
			return null;
		}

		return schemaLabelProvider.getText(obj);
	}

	public String createResponseColumn() {
		IStructuredSelection sel = (IStructuredSelection) responseXmlTreeViewer
				.getSelection();

		Object obj = sel.getFirstElement();
		if (obj instanceof XSDParticleImpl && ((XSDParticleImpl) obj).getContent() instanceof XSDElementDeclarationImpl) {

			Model wsdlModel = null;
			SchemaModel schemaModel;
			XSDSchema[] schemas;

			try {
				wsdlModel = importManager.getWSDLModel();
			} catch (ModelGenerationException e) {
				throw new RuntimeException(e);
			}

			SchemaProcessor processor = new SOAPSchemaProcessor(null);
			processor.representTypes(true);
			processor.setNamespaces(wsdlModel.getNamespaces());
			schemas = wsdlModel.getSchemas();
			try {
				processor.processSchemas(schemas);
			} catch (SchemaProcessingException e) {
				throw new RuntimeException(e);
			}
			schemaModel = processor.getSchemaModel();

			List<SchemaObject> elements = schemaModel.getElements();
			String name = ((XSDElementDeclarationImpl) ((XSDParticleImpl) obj)
					.getContent()).getName();
			StringBuilder xpath = new StringBuilder();
			String namespace = null;
			String prefix = null;
			StringBuilder parentXpath = new StringBuilder();
			for (SchemaObject schemaObject : elements) {
				if (schemaObject.getName().equals(name)) {
					getParentXpath(schemaObject, parentXpath);
					xpath.append("/").append(schemaObject.getRelativeXpath()); //$NON-NLS-1$
					namespace = schemaObject.getNamespace();
					prefix = ((BaseSchemaObject) schemaObject)
							.getNamespacePrefix();
					if (namespace != null) {
						this.procedureGenerator.getResponseInfo().addNamespace(
								prefix, namespace);
					}
					this.procedureGenerator.getResponseInfo().setRootPath(
							parentXpath.toString());
					responseElementsInfoPanel.getRootPathText().setText(
							parentXpath.toString());
				}
			}
			this.procedureGenerator.getResponseInfo().addColumn(name, false,
					DatatypeConstants.RuntimeTypeNames.STRING, null,
					xpath.toString());

			notifyColumnDataChanged();
			return null;
		}

		return schemaLabelProvider.getText(obj);
	}

	private void getParentXpath(SchemaObject child, StringBuilder parentXpath) {
		List<SimpleRelationship> parents = child.getParents();
		for (SimpleRelationship parent : parents) {
			parentXpath.append("/").append(parent.getParent().getSimpleName()); //$NON-NLS-1$
		}
	}

	class OperationsListProvider extends LabelProvider implements
			ITreeContentProvider {
		private final Image OPERATION_ICON_IMG = ModelGeneratorWsdlUiUtil
				.getImage(Images.OPERATION_ICON);

		public void dispose() {
		}

		public Object[] getChildren(final Object node) {
			if (node instanceof ArrayList) {
				ArrayList theList = ((ArrayList) node);

				return theList.toArray();
			}
			return CoreStringUtil.Constants.EMPTY_STRING_ARRAY;
		}

		public Object[] getElements(final Object inputElement) {
			return getChildren(inputElement);
		}

		public Object getParent(final Object node) {
			return null;
		}

		public boolean hasChildren(final Object node) {
			return false;
		}

		public void inputChanged(final Viewer viewer, final Object oldInput,
				final Object newInput) {
		}

		@Override
		public Image getImage(final Object node) {
			if (node instanceof Operation) {
				return OPERATION_ICON_IMG;
			}
			return null;
		}

		@Override
		public String getText(final Object node) {
			if (node instanceof Operation) {
				return ((Operation) node).getName();
			}
			return "unknownElement"; //$NON-NLS-1$
		}
	}
}
