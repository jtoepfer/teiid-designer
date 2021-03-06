/* Generated By:JJTree: Do not edit this line. SubqueryCompareCriteria.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import org.teiid.designer.annotation.Since;
import org.teiid.designer.query.sql.lang.ISubqueryCompareCriteria;
import org.teiid.designer.runtime.version.spi.TeiidServerVersion.Version;
import org.teiid.language.SQLConstants;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidNodeFactory.ASTNodes;
import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.query.sql.symbol.Expression;
import org.teiid.query.sql.symbol.ScalarSubquery;

/**
 *
 */
public class SubqueryCompareCriteria extends AbstractCompareCriteria
    implements SubqueryContainer<QueryCommand>, ISubqueryCompareCriteria< LanguageVisitor, QueryCommand> {

    /**
     * Predicate quantifiers
     */
    public enum PredicateQuantifier {

        /** "Some" predicate quantifier (equivalent to "Any") */
        SOME,

        /** "Any" predicate quantifier (equivalent to "Some") */
        ANY,

        /** "All" predicate quantifier */
        ALL;

        /**
         * @return index of predicate
         */
        public int getQuantifier() {
            return ordinal() + 2;
        }

        /**
         * @param quantifier
         * @return {@link PredicateQuantifier} with the given quantifier index
         */
        public static PredicateQuantifier findQuantifier(int quantifier) {
            for (PredicateQuantifier pq : values()) {
                if (pq.getQuantifier() == quantifier)
                    return pq;
            }

            throw new IllegalStateException();
        }
    }

    private PredicateQuantifier predicateQuantifier = PredicateQuantifier.ALL;

    private QueryCommand command;

    @Since(Version.TEIID_8_10)
    private SubqueryHint subqueryHint = new SubqueryHint();

    /**
     * @param p
     * @param id
     */
    public SubqueryCompareCriteria(ITeiidServerVersion p, int id) {
        super(p, id);
    }

    /**
     * Get the predicate quantifier - returns one of the following:
     * <ul>
     * <li>{@link #ANY}</li>
     * <li>{@link #SOME}</li>
     * <li>{@link #ALL}</li></ul>
     * @return the predicate quantifier
     */
    public PredicateQuantifier getPredicateQuantifier() {
        return this.predicateQuantifier;
    }

    /**
     * Returns the predicate quantifier as a string.
     * @return String version of predicate quantifier
     */
    public String getPredicateQuantifierAsString() {
        String name = this.predicateQuantifier.name();

        if (getTeiidVersion().isLessThan(Version.TEIID_8_10))
            return name + SQLConstants.Tokens.SPACE;

        return name;
    }

    /**
     * Set the predicate quantifier - use one of the following:
     * <li>{@link PredicateQuantifier#ANY}</li>
     * <li>{@link PredicateQuantifier#SOME}</li>
     * <li>{@link PredicateQuantifier#ALL}</li></ul>
     *
     * @param predicateQuantifier the predicate quantifier
     */
    public void setPredicateQuantifier(PredicateQuantifier predicateQuantifier) {
        this.predicateQuantifier = predicateQuantifier;
    }

    @Override
    public QueryCommand getCommand() {
        return this.command;
    }

    /**
     * Set the subquery command (either a SELECT or a procedure execution).
     * @param command Command to execute to get the values for the criteria
     */
    @Override
    public void setCommand(QueryCommand command) {
        this.command = command;
    }

    @Override
    public Expression getRightExpression() {
        ScalarSubquery scalarSubquery = createASTNode(ASTNodes.SCALAR_SUBQUERY);
        scalarSubquery.setCommand(getCommand());
        return scalarSubquery;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.command == null) ? 0 : this.command.hashCode());
        result = prime * result + ((this.predicateQuantifier == null) ? 0 : this.predicateQuantifier.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SubqueryCompareCriteria other = (SubqueryCompareCriteria)obj;
        if (this.command == null) {
            if (other.command != null) return false;
        } else if (!this.command.equals(other.command)) return false;
        if (this.predicateQuantifier != other.predicateQuantifier) return false;

        if (getTeiidVersion().isGreaterThanOrEqualTo(Version.TEIID_8_10)) {
            if (this.subqueryHint == null) {
                if (other.subqueryHint != null)
                    return false;
            } else if (! this.subqueryHint.equals(other.subqueryHint))
                return false;
        }

        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SubqueryCompareCriteria clone() {
        SubqueryCompareCriteria clone = new SubqueryCompareCriteria(getTeiidVersion(), this.id);

        if(getCommand() != null)
            clone.setCommand(getCommand().clone());
        if(getPredicateQuantifier() != null)
            clone.setPredicateQuantifier(getPredicateQuantifier());
        if(operator != null)
            clone.setOperator(operator);
        if(getLeftExpression() != null)
            clone.setLeftExpression(getLeftExpression().clone());

        if (this.subqueryHint != null) {
            clone.subqueryHint = this.subqueryHint.clone();
        }

        return clone;
    }

    @Since(Version.TEIID_8_10)
    public SubqueryHint getSubqueryHint() {
        return subqueryHint;
    }

    @Since(Version.TEIID_8_10)
    public void setSubqueryHint(SubqueryHint subqueryHint) {
        this.subqueryHint = subqueryHint;
    }
}
/* JavaCC - OriginalChecksum=e9b141cd60d09da32342d127668258f8 (do not edit this line) */
