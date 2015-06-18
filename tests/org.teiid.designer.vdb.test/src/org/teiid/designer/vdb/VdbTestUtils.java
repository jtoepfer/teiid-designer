/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.designer.vdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.teiid.core.designer.util.StringConstants;
import org.teiid.core.util.SmartTestDesignerSuite;
import org.teiid.designer.core.ModelEditor;
import org.teiid.designer.core.ModelWorkspaceMock;
import org.teiid.designer.core.workspace.MockFileBuilder;
import org.teiid.designer.core.workspace.ModelFolderImpl;
import org.teiid.designer.core.workspace.ModelResource;
import org.teiid.designer.core.workspace.ModelResourceImpl;
import org.teiid.designer.komodo.vdb.DynamicModel;
import org.teiid.designer.komodo.vdb.Metadata;
import org.teiid.designer.vdb.dynamic.DynamicVdb;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

/**
 *
 */
@SuppressWarnings( "javadoc" )
public class VdbTestUtils implements StringConstants {

    public static final String BOOKS_MODEL = "Books";

    public static final String BOOKS_MODEL_DDL = NEW_LINE +
    "CREATE FOREIGN TABLE AUTHORS (" + NEW_LINE +
    TAB + "AUTHOR_ID long OPTIONS(NAMEINSOURCE 'AUTHOR_ID', NATIVE_TYPE 'BIGINT', CASE_SENSITIVE false, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH true, SEARCHABLE 'ALL_EXCEPT_LIKE')," + NEW_LINE +
    TAB + "FIRSTNAME string(255) OPTIONS(NAMEINSOURCE 'FIRSTNAME', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')," + NEW_LINE +
    TAB + "LASTNAME string(255) OPTIONS(NAMEINSOURCE 'LASTNAME', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')," + NEW_LINE +
    TAB + "MIDDLEINIT string(255) OPTIONS(NAMEINSOURCE 'MIDDLEINIT', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')" + NEW_LINE +
    ")" + NEW_LINE +
    "CREATE FOREIGN TABLE BOOK_AUTHORS (" + NEW_LINE +
    TAB + "ISBN string(255) OPTIONS(NAMEINSOURCE 'ISBN', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')," + NEW_LINE +
    TAB + "AUTHOR_ID long OPTIONS(NAMEINSOURCE 'AUTHOR_ID', NATIVE_TYPE 'BIGINT', CASE_SENSITIVE false, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH true, SEARCHABLE 'ALL_EXCEPT_LIKE')" + NEW_LINE +
    ")" + NEW_LINE +
    "CREATE FOREIGN TABLE BOOKS (" + NEW_LINE +
    TAB + "ISBN string(255) OPTIONS(NAMEINSOURCE 'ISBN', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')," + NEW_LINE +
    TAB + "TITLE string(255) OPTIONS(NAMEINSOURCE 'TITLE', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')," + NEW_LINE +
    TAB + "SUBTITLE string(255) OPTIONS(NAMEINSOURCE 'SUBTITLE', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')," + NEW_LINE +
    TAB + "PUBLISHER long OPTIONS(NAMEINSOURCE 'PUBLISHER', NATIVE_TYPE 'BIGINT', CASE_SENSITIVE false, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH true, SEARCHABLE 'ALL_EXCEPT_LIKE')," + NEW_LINE +
    TAB + "PUBLISH_YEAR long OPTIONS(NAMEINSOURCE 'PUBLISH_YEAR', NATIVE_TYPE 'BIGINT', CASE_SENSITIVE false, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH true, SEARCHABLE 'ALL_EXCEPT_LIKE')," + NEW_LINE +
    TAB + "EDITION long OPTIONS(NAMEINSOURCE 'EDITION', NATIVE_TYPE 'BIGINT', CASE_SENSITIVE false, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH true, SEARCHABLE 'ALL_EXCEPT_LIKE')," + NEW_LINE +
    TAB + "TYPE string(255) OPTIONS(NAMEINSOURCE 'TYPE', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')" + NEW_LINE +
    ")" + NEW_LINE +
    "CREATE FOREIGN TABLE PUBLISHERS (" + NEW_LINE +
    TAB + "PUBLISHER_ID long OPTIONS(NAMEINSOURCE 'PUBLISHER_ID', NATIVE_TYPE 'BIGINT', CASE_SENSITIVE false, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH true, SEARCHABLE 'ALL_EXCEPT_LIKE')," + NEW_LINE +
    TAB + "NAME string(255) OPTIONS(NAMEINSOURCE 'NAME', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')," + NEW_LINE +
    TAB + "LOCATION string(255) OPTIONS(NAMEINSOURCE 'LOCATION', NATIVE_TYPE 'VARCHAR', CASE_SENSITIVE true, SELECTABLE true, UPDATABLE true, SIGNED true, CURRENCY false, FIXED_LENGTH false, SEARCHABLE 'SEARCHABLE')" + NEW_LINE +
    ")" + NEW_LINE;

    public static final String BOOKS_VDB_PROJECT = "books.vdb.project";

    public static final String RUNTIME_INF = "runtime-inf";

    public static final String TEST_2120  = "Test_TEIIDDES_2120";

    public static final String TEST_UDF  = "TestUDF";

    public static final String DYNAMIC_VDBS = "dynamic_vdbs";

    public static final String TEST_PROJECT = BOOKS_VDB_PROJECT + File.separator + TEST_2120 + File.separator;

    public static final File TEST_DATA_DIR = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, EMPTY_STRING);

    public static final File PORTFOLIO_VDB_FILE = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, DYNAMIC_VDBS + File.separator + "portfolio-vdb.xml");

    public static final File BOOKS_VDB_FILE = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, "books.vdb");

    public static final File BOOKS_77_VDB_FILE = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, "books-7.7.x.vdb");

    public static final File BOOKS_84_VDB_FILE = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, "books-8.4.x.vdb");

    public static final File UDF_VDB_FILE = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, "TestUDF.vdb");

    public static final File BOOK_DATATYPES_XSD = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, TEST_PROJECT + "BookDatatypes.xsd");

    public static final File BOOKS_XMI = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, TEST_PROJECT + "Books.xmi");

    public static final File BOOKS_XSD = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, TEST_PROJECT + "Books.xsd");

    public static final File BOOKSXML_XMI = SmartTestDesignerSuite.getTestDataFile(VdbTestUtils.class, TEST_PROJECT + "BooksXML.xmi");

    public static ModelResource mockModelResource(IPath path) {
        ModelResource parent = mock(ModelResource.class);
        when(parent.getPath()).thenReturn(path.removeLastSegments(1));

        final ModelResource modelResource = mock(ModelResource.class);
        when(modelResource.getItemName()).thenReturn(path.lastSegment());
        when(modelResource.getParent()).thenReturn(parent);

        return modelResource;
    }

    /**
     * @return a mocked books vdb based on the testdata
     * @throws Exception
     */
    public static Vdb mockBooksVdb(ModelWorkspaceMock modelWksp) throws Exception {

        MockFileBuilder testData = new MockFileBuilder(TEST_DATA_DIR);

        List<MockFileBuilder> builders = new ArrayList<MockFileBuilder>();
        MockFileBuilder booksDatatypesXSD = new MockFileBuilder(BOOK_DATATYPES_XSD);
        builders.add(booksDatatypesXSD);
        MockFileBuilder booksXMI = new MockFileBuilder(BOOKS_XMI);
        builders.add(booksXMI);
        MockFileBuilder booksXSD = new MockFileBuilder(BOOKS_XSD);
        builders.add(booksXSD);
        MockFileBuilder booksXMLXMI = new MockFileBuilder(BOOKSXML_XMI);
        builders.add(booksXMLXMI);

        /*
         * Need to ensure that the paths provided by the vdb point to the same file in the workspace
         * so need to mock the workspace finder and point the vdb paths to the testdata files.
         */
        for (MockFileBuilder builder : builders) {
            IPath path = new Path(File.separator + TEST_2120 + File.separator + builder.getName());
            when(modelWksp.getEclipseMock().workspaceRoot().findMember(path)).thenReturn(builder.getResourceFile());
        }

        MockFileBuilder booksVdbBuilder = new MockFileBuilder(BOOKS_VDB_FILE);
        Vdb booksVdb = new XmiVdb(booksVdbBuilder.getResourceFile());

        IFolder testDataFolder = testData.getResourceFolder();
        when(testDataFolder.getFile(new Path(booksXMI.getName()))).thenReturn(booksXMI.getResourceFile());
        when(testDataFolder.getFile(new Path(booksXMLXMI.getName()))).thenReturn(booksXMLXMI.getResourceFile());

        ModelFolderImpl tdModelFolder = new ModelFolderImpl(testDataFolder, null);
        ModelResourceImpl booksXmiMResource = new ModelResourceImpl(tdModelFolder, booksXMI.getName());
        ModelResourceImpl booksXmlXmiMResource = new ModelResourceImpl(tdModelFolder, booksXMLXMI.getName());

        ModelEditor modelEditor = modelWksp.getModelEditor();
        when(modelEditor.findModelResource(booksXMI.getResourceFile())).thenReturn(booksXmiMResource);
        when(modelEditor.findModelResource(booksXMLXMI.getResourceFile())).thenReturn(booksXmlXmiMResource);

        assertTrue(booksXmiMResource.getAllRootEObjects().size() > 0);
        assertTrue(booksXmlXmiMResource.getAllRootEObjects().size() > 0);

        return booksVdb;
    }

    public static DynamicVdb mockPortfolioDynamicVdb() throws Exception {
        MockFileBuilder portfolio = new MockFileBuilder(PORTFOLIO_VDB_FILE);

        DynamicVdb vdb = new DynamicVdb();
        vdb.setFile(portfolio.getResourceFile());
        vdb.setName("Portfolio");
        vdb.setVersion(1);
        vdb.setDescription("The Portfolio Dynamic VDB");

        vdb.setProperty("UseConnectorMetadata", "true");

        DynamicModel marketData = new DynamicModel("MarketData");
        marketData.addSource(new VdbSource(vdb, "text-connector", "java:/marketdata-file" , "file"));
        vdb.addDynamicModel(marketData);

        DynamicModel accounts = new DynamicModel("Accounts");
        accounts.setProperty("importer.useFullSchemaName", "false");
        accounts.addSource(new VdbSource(vdb, "h2-connector", "java:/accounts-ds" , "h2"));
        vdb.addDynamicModel(accounts);

        DynamicModel valuations = new DynamicModel("PersonalValuations");
        valuations.setProperty("importer.headerRowNumber", "1");
        valuations.setProperty("importer.ExcelFileName", "otherholdings.xls");
        valuations.addSource(new VdbSource(vdb, "excelconnector", "java:/excel-file" , "excel"));
        String metadataText = EMPTY_STRING +
        "SET NAMESPACE 'http://www.teiid.org/translator/excel/2014' AS teiid_excel;" + NEW_LINE +
        NEW_LINE +
        "CREATE FOREIGN TABLE Sheet1 (" + NEW_LINE +
        "ROW_ID integer OPTIONS (SEARCHABLE 'All_Except_Like', \"teiid_excel:CELL_NUMBER\" 'ROW_ID')," + NEW_LINE +
        "ACCOUNT_ID integer OPTIONS (SEARCHABLE 'Unsearchable', \"teiid_excel:CELL_NUMBER\" '1')," + NEW_LINE +
        "PRODUCT_TYPE string OPTIONS (SEARCHABLE 'Unsearchable', \"teiid_excel:CELL_NUMBER\" '2')," + NEW_LINE +
        "PRODUCT_VALUE string OPTIONS (SEARCHABLE 'Unsearchable', \"teiid_excel:CELL_NUMBER\" '3')," + NEW_LINE +
        "CONSTRAINT PK0 PRIMARY KEY(ROW_ID)" + NEW_LINE +
        ") OPTIONS (\"teiid_excel:FILE\" 'otherholdings.xls', \"teiid_excel:FIRST_DATA_ROW_NUMBER\" '2');";
        valuations.setMetadata(new Metadata(metadataText, Metadata.Type.DDL));
        vdb.addDynamicModel(valuations);

        DynamicModel stocks = new DynamicModel("Stocks");
        stocks.setModelType(DynamicModel.Type.VIRTUAL);
        metadataText = EMPTY_STRING +
        "CREATE VIEW StockPrices (" + NEW_LINE +
        "symbol string," + NEW_LINE +
        "price bigdecimal" + NEW_LINE +
        ")" + NEW_LINE +
        "AS" + NEW_LINE +
        "SELECT SP.symbol, SP.price" + NEW_LINE +
        "FROM (EXEC MarketData.getTextFiles('*.txt')) AS f," + NEW_LINE +
        "TEXTTABLE(f.file COLUMNS symbol string, price bigdecimal HEADER) AS SP;" + NEW_LINE +
        NEW_LINE +
        NEW_LINE +
        "CREATE VIEW Stock (" + NEW_LINE +
        "product_id integer," + NEW_LINE +
        "symbol string," + NEW_LINE +
        "price bigdecimal," + NEW_LINE +
        "company_name   varchar(256)" + NEW_LINE +
        ")" + NEW_LINE +
        "AS" + NEW_LINE +
        "SELECT  A.ID, S.symbol, S.price, A.COMPANY_NAME" + NEW_LINE +
        "FROM StockPrices AS S, Accounts.PRODUCT AS A" + NEW_LINE +
        "WHERE S.symbol = A.SYMBOL;";
        stocks.setMetadata(new Metadata(metadataText, Metadata.Type.DDL));
        vdb.addDynamicModel(stocks);

        DynamicModel stocksMatModel = new DynamicModel("StocksMatModel");
        stocksMatModel.setModelType(DynamicModel.Type.VIRTUAL);
        metadataText = EMPTY_STRING +
        "CREATE view stockPricesMatView" + NEW_LINE +
        "(" + NEW_LINE +
        "product_id integer," + NEW_LINE +
        "symbol string," + NEW_LINE +
        "price bigdecimal," + NEW_LINE +
        "company_name   varchar(256)" + NEW_LINE +
        ") OPTIONS (MATERIALIZED 'TRUE', UPDATABLE 'TRUE'," + NEW_LINE +
        "MATERIALIZED_TABLE 'Accounts.h2_stock_mat'," + NEW_LINE +
        "\"teiid_rel:MATVIEW_TTL\" 120000," + NEW_LINE +
        "\"teiid_rel:MATVIEW_BEFORE_LOAD_SCRIPT\" 'execute accounts.native(''truncate table h2_stock_mat'');'," + NEW_LINE +
        "\"teiid_rel:MATVIEW_AFTER_LOAD_SCRIPT\"  'execute accounts.native('''')'," + NEW_LINE +
        "\"teiid_rel:ON_VDB_DROP_SCRIPT\" 'DELETE FROM Accounts.status WHERE Name=''stock'' AND schemaname = ''Stocks'''," + NEW_LINE +
        "\"teiid_rel:MATERIALIZED_STAGE_TABLE\" 'Accounts.h2_stock_mat'," + NEW_LINE +
        "\"teiid_rel:ALLOW_MATVIEW_MANAGEMENT\" 'true'," + NEW_LINE +
        "\"teiid_rel:MATVIEW_STATUS_TABLE\" 'status'," + NEW_LINE +
        "\"teiid_rel:MATVIEW_SHARE_SCOPE\" 'NONE'," + NEW_LINE +
        "\"teiid_rel:MATVIEW_ONERROR_ACTION\" 'THROW_EXCEPTION')" + NEW_LINE +
        "AS SELECT  A.ID, S.symbol, S.price, A.COMPANY_NAME" + NEW_LINE +
        "FROM Stocks.StockPrices AS S, Accounts.PRODUCT AS A" + NEW_LINE +
        "WHERE S.symbol = A.SYMBOL;";
        stocksMatModel.setMetadata(new Metadata(metadataText, Metadata.Type.DDL));
        vdb.addDynamicModel(stocksMatModel);

        return vdb;
    }

    public static Document readDocument(Reader reader) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new InputSource(reader));
        doc.setXmlStandalone(true);
        doc.normalizeDocument();

        return doc;
    }

    public static Document readDocument(File file) throws Exception {
        return readDocument(new FileReader(file));
    }

    public static Document readDocument(String xml) throws Exception {
        return readDocument(new StringReader(xml));
    }

    private static boolean compareAttributes(Element expected, Element actual) {
        NamedNodeMap expectedAttrs = expected.getAttributes();
        NamedNodeMap actualAttrs = actual.getAttributes();

        if (expectedAttrs.getLength() != actualAttrs.getLength())
            return false;

        for (int i = 0; i < expectedAttrs.getLength(); i++) {
            Attr expectedAttr = (Attr)expectedAttrs.item(i);

            Attr actualAttr = null;
            if (expectedAttr.getNamespaceURI() == null)
                actualAttr = (Attr)actualAttrs.getNamedItem(expectedAttr.getName());
            else
                actualAttr = (Attr)actualAttrs.getNamedItemNS(expectedAttr.getNamespaceURI(), expectedAttr.getLocalName());

            if (actualAttr == null) {
                return false;
            }

            if (! expectedAttr.getValue().equals(actualAttr.getValue())) {
                return false;
            }
        }

        return true;
    }

    private static boolean compareElements(org.w3c.dom.Element expected, org.w3c.dom.Element actual, StringBuilder log) {
        // compare element names
        if (expected.getLocalName() != null) {
            if (! expected.getLocalName().equals(actual.getLocalName()))
                return false;
        }

        // compare element ns
        if (expected.getNamespaceURI() != null) {
            if (! expected.getNamespaceURI().equals(actual.getNamespaceURI()))
                return false;
        }

        // compare attributes
        if (! compareAttributes(expected, actual)) {
            return false;
        }

        // compare children
        NodeList expectedChildren = expected.getChildNodes();
        NodeList actualChildren = actual.getChildNodes();
        if (expectedChildren.getLength() != actualChildren.getLength()) {
            return false;
        }

        // Same number but could be in a different order
        for (int i = 0; i < expectedChildren.getLength(); ++i) {
            org.w3c.dom.Node expectedChild = expectedChildren.item(i);

            boolean matchMade = false;
            for (int j = 0; j < actualChildren.getLength(); ++j) {
                org.w3c.dom.Node actualChild = actualChildren.item(j);
                if (! expectedChild.getNodeName().equals(actualChild.getNodeName()))
                    continue;

                if (expectedChild.getNodeType() != actualChild.getNodeType())
                    continue;

                if (expectedChild instanceof Element) {
                    matchMade = compareElements((Element) expectedChild, (Element) actualChild, log);
                } else if (expectedChild instanceof Text) {
                    matchMade = compareTextNode((Text) expectedChild, (Text) actualChild, log);
                }

                if (matchMade)
                    break;
            }

            if (! matchMade) {
                log.append("Failed to find: " + NEW_LINE);
                logNode(log, expectedChild);
                return false;
            }
        }

        return true;
    }

    private static void logNode(StringBuilder log, org.w3c.dom.Node node) {
        if (node.getParentNode() != null)
            logNode(log, node.getParentNode());

        log.append(TAB + OPEN_ANGLE_BRACKET + SPACE + node.getNodeName() + SPACE);

        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            for (int j = 0; j < attributes.getLength(); j++) {
                Attr attr = (Attr)attributes.item(j);
                log.append(attr.getName() + EQUALS + attr.getValue() + SPACE);
            }
        }

        log.append(CLOSE_ANGLE_BRACKET + NEW_LINE);
    }

    /**
     * Should reduce a spread out block of text to a single line
     * with single spaces between tokens
     *
     * @param data
     * @return single space line of text from block
     */
    private static String normalizeSpacing(String data) {
        data = data.trim();
        data = data.replaceAll(NEW_LINE, SPACE);
        data = data.replaceAll(">[\\s]+<", CLOSE_ANGLE_BRACKET + OPEN_ANGLE_BRACKET);
        data = data.replaceAll("[\\s]+", SPACE);
        data = data.replaceAll("CDATA\\[[\\s]+", "CDATA[");
        data = data.replaceAll("; \\]\\]", ";]]");
        return data;
    }

    private static boolean compareTextNode(org.w3c.dom.Text expected, org.w3c.dom.Text actual, StringBuilder errorMessages) {
        String expectedData = normalizeSpacing(expected.getData());
        String actualData = normalizeSpacing(actual.getData());

        if (expectedData.equalsIgnoreCase(actualData))
            return true;

        errorMessages.append(expected.getData() + NEW_LINE + " does not match " + actual.getData() + NEW_LINE);
        return false;
    }

    private static String printDocument(Document document) throws Exception {
        StringWriter writer = new StringWriter();

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();
        } finally {
            writer.close();
        }
    }

    /**
     * Compare two documents
     *
     * @param expected expected document
     * @param actual actual document
     * @throws Exception 
     */
    public static void compareDocuments(Document expected, Document actual) throws Exception {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getNodeType(), actual.getNodeType());

        StringBuilder log = new StringBuilder();
        if (! compareElements(expected.getDocumentElement(), actual.getDocumentElement(), log)) {
            log.append(NEW_LINE + "=== EXPECTED ===" + NEW_LINE);
            log.append(printDocument(expected));
            log.append(NEW_LINE);

            log.append(NEW_LINE + "=== ACTUAL ===" + NEW_LINE);
            log.append(printDocument(actual));

            fail(log.toString());
        }
    }
}
