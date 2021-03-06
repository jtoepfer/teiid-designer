/* Generated By:JJTree: Do not edit this line. From.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import java.util.ArrayList;
import java.util.List;
import org.teiid.designer.query.sql.lang.IFrom;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidNodeFactory.ASTNodes;
import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.query.sql.symbol.GroupSymbol;

/**
 *
 */
public class From extends SimpleNode
    implements IFrom<FromClause, GroupSymbol, LanguageVisitor> {

    private List<FromClause> clauses;

    /**
     * @param p
     * @param id
     */
    public From(ITeiidServerVersion p, int id) {
        super(p, id);
    }

    /** 
     * Get all the clauses in FROM
     * @return List of {@link FromClause}
     */
    @Override
    public List<FromClause> getClauses() {
        return this.clauses;
    }
    
    /** 
     * Set all the clauses
     * @param clauses List of {@link FromClause}
     */
    @Override
    public void setClauses(List<? extends FromClause> clauses) {
        if (clauses == null) {
            this.clauses = null;
            return;
        }

        this.clauses = new ArrayList<FromClause>( clauses );
    }

    /**
     * Add a from clause to this {@link From}
     * @param clause
     */
    @Override
    public void addClause(FromClause clause) {
        if (this.clauses == null)
            this.clauses = new ArrayList<FromClause>();

        this.clauses.add(clause);
    }

    /**
     * Adds a new group to the list (it will be wrapped in a UnaryFromClause)
     * @param group Group to add
     */
    @Override
    public void addGroup( GroupSymbol group ) {
        if( group == null )
            return;

        UnaryFromClause unaryFromClause = createASTNode(ASTNodes.UNARY_FROM_CLAUSE);
        unaryFromClause.setGroup(group);
        addClause(unaryFromClause);
    }  

    @Override
    public List<GroupSymbol> getGroups() {
        List<GroupSymbol> groups = new ArrayList<GroupSymbol>();
        if(clauses != null) {
            for(int i=0; i<clauses.size(); i++) {
                FromClause clause = clauses.get(i);
                clause.collectGroups(groups);
            }
        }
            
        return groups;
    }

    @Override
    public boolean containsGroup(GroupSymbol group) {
        return getGroups().contains(group);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.clauses == null) ? 0 : this.clauses.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        From other = (From)obj;
        if (this.clauses == null) {
            if (other.clauses != null) return false;
        } else if (!this.clauses.equals(other.clauses)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public From clone() {
        From clone = new From(getTeiidVersion(), this.id);

        if(getClauses() != null)
            clone.setClauses(cloneList(getClauses()));

        return clone;
    }
}
/* JavaCC - OriginalChecksum=ae9ea7eb99f07c1bca94932f9265c796 (do not edit this line) */
