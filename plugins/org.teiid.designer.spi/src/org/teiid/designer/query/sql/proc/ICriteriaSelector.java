/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.query.sql.proc;

import org.teiid.designer.annotation.Removed;
import org.teiid.designer.query.sql.ILanguageVisitor;
import org.teiid.designer.query.sql.lang.ILanguageObject;
import org.teiid.designer.runtime.version.spi.TeiidServerVersion.Version;

/**
 *
 */
@Removed(Version.TEIID_8_0)
public interface ICriteriaSelector <LV extends ILanguageVisitor> extends ILanguageObject<LV> {

}
