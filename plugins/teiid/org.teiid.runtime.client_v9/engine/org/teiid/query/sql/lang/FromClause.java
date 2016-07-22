/* Generated By:JJTree: Do not edit this line. FromClause.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import java.util.Collection;
import org.teiid.designer.annotation.Removed;
import org.teiid.designer.annotation.Since;
import org.teiid.designer.query.sql.lang.IFromClause;
import org.teiid.designer.runtime.version.spi.TeiidServerVersion.Version;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidParser;
import org.teiid.query.sql.lang.Option.MakeDep;
import org.teiid.query.sql.symbol.GroupSymbol;

/**
 * A FromClause is an interface for subparts held in a FROM clause.  One 
 * type of FromClause is {@link UnaryFromClause}, which is the more common 
 * use and represents a single group.  Another, less common type of FromClause
 * is the {@link JoinPredicate} which represents a join between two FromClauses
 * and may contain criteria.
 */
public abstract class FromClause extends SimpleNode implements IFromClause<LanguageVisitor> {

    /**
     * 
     */
    public static final String MAKEIND = "MAKEIND"; //$NON-NLS-1$

    /**
     * 
     */
    @Since(Version.TEIID_8_0)
    public static final String PRESERVE = "PRESERVE"; //$NON-NLS-1$

    private boolean optional;

    private MakeDep makeDep;

    private boolean makeNotDep;

    @Removed(Version.TEIID_8_12_4)
    private boolean makeIndOld;

    @Since(Version.TEIID_8_12_4)
    private MakeDep makeIndNew;

    private boolean noUnnest;

    private boolean preserve;

    /**
     * @param p
     * @param id
     */
    public FromClause(TeiidParser p, int id) {
        super(p, id);
    }

    private boolean isLessThanTeiid124() {
        return isLessThanTeiidVersion(Version.TEIID_8_12_4);
    }

    /**
     * @return whether has any hints set
     */
    public boolean hasHint() {
        boolean hasHint =  optional || makeDep != null || makeNotDep || noUnnest || preserve;
        if (isLessThanTeiid124())
            hasHint = hasHint || isMakeInd();
        else
            hasHint = hasHint || getMakeInd() != null;

        return hasHint;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }
    
    @Override
    public void setOptional(boolean optional) {
        this.optional = optional;
    }
    
    /**
     * @return make ind flag
     */
    @Removed(Version.TEIID_8_12_4)
    public boolean isMakeInd() {
        if (! isLessThanTeiid124())
            return false;

        return makeIndOld;
    }

    /**
     * @return the makeIndNew
     */
    @Since(Version.TEIID_8_12_4)
    public MakeDep getMakeInd() {
        if (isLessThanTeiid124())
            return null;

        return this.makeIndNew;
    }

    /**
     * @param makeInd
     */
    @Removed(Version.TEIID_8_12_4)
    public void setMakeInd(boolean makeInd) {
        if (! isLessThanTeiid124())
            return;

        this.makeIndOld = makeInd;
    }

    /**
     * @param makeInd the makeInd to set
     */
    @Since(Version.TEIID_8_12_4)
    public void setMakeInd(MakeDep makeInd) {
        if (isLessThanTeiid124())
            return;

        this.makeIndNew = makeInd;
    }

    /**
     * @param noUnnest
     */
    public void setNoUnnest(boolean noUnnest) {
        this.noUnnest = noUnnest;
    }
    
    /**
     * @return no unnest flag
     */
    public boolean isNoUnnest() {
        return noUnnest;
    }

    @Override
    public boolean isMakeDep() {
        return this.makeDep != null;
    }

    public MakeDep getMakeDep() {
        return makeDep;
    }

    public void setMakeDep(MakeDep makedep) {
        this.makeDep = makedep;
    }

    @Override
    public void setMakeDep(boolean makeDep) {
        if (makeDep) {
            if (this.makeDep == null) {
                setMakeDep(new MakeDep(getTeiidVersion()));
            }
        } else {
            this.makeDep = null;
        }
    }

    @Override
    public boolean isMakeNotDep() {
        return this.makeNotDep;
    }

    @Override
    public void setMakeNotDep(boolean makeNotDep) {
        this.makeNotDep = makeNotDep;
    }
    
    /**
     * @return preserve flag
     */
    public boolean isPreserve() {
        return preserve;
    }
    
    /**
     * @param preserve
     */
    public void setPreserve(boolean preserve) {
        this.preserve = preserve;
    }

    /**
     * @param groups
     */
    public abstract void collectGroups(Collection<GroupSymbol> groups);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.makeDep == null ? 0 : this.makeDep.hashCode());

        if (isLessThanTeiid124())
            result = prime * result + (this.makeIndOld ? 1231 : 1237);
        else
            result = prime * result + (this.makeIndNew == null ? 0 : this.makeIndNew.hashCode());

        result = prime * result + (this.makeNotDep ? 1231 : 1237);
        result = prime * result + (this.noUnnest ? 1231 : 1237);
        result = prime * result + (this.optional ? 1231 : 1237);
        result = prime * result + (this.preserve ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        FromClause other = (FromClause)obj;
        
        if (this.makeDep == null) {
            if (other.makeDep != null)
                return false;
        } else if (!this.makeDep.equals(other.makeDep))
            return false;

        if (isLessThanTeiid124())
            if (this.makeIndOld != other.makeIndOld) return false;
        else {
            if (this.makeIndNew == null) {
                if (other.makeIndNew != null)
                    return false;
            } else if (!this.makeIndNew.equals(other.makeIndNew))
                return false;
        }

        
        if (this.makeNotDep != other.makeNotDep) return false;
        if (this.noUnnest != other.noUnnest) return false;
        if (this.optional != other.optional) return false;
        if (this.preserve != other.preserve) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public abstract FromClause clone();

}
/* JavaCC - OriginalChecksum=908130697ce6a37a6c778dfefda987bb (do not edit this line) */