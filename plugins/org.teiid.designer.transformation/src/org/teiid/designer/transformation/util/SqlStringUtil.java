/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.transformation.util;

import org.teiid.core.designer.util.CoreArgCheck;
import org.teiid.designer.query.sql.ISQLConstants;

/**
 * SQL String utility methods.
 *
 * @since 8.0
 */
public final class SqlStringUtil implements ISQLConstants {

    /**
    * Get the index location of the SELECT from a SQL statement
    * @param SQLString the SQL statment
    * @return the index of the SELECT
    */
    public static int getSelectIndex (String SQLString) {
        int selectSpaceIndx = getSelectSpaceIndex(SQLString);
        int selectReturnIndx = getSelectReturnIndex(SQLString);
        if(selectSpaceIndx>selectReturnIndx) {
            return selectSpaceIndx;
        }
        return selectReturnIndx;
        //String SQLUpper = SQLString.toUpperCase();
        //int index = SQLUpper.indexOf(SELECT+SPACE);
        //return index;
    }

    private static int getSelectSpaceIndex (String SQLString) {
        String SQLUpper = SQLString.toUpperCase();
        int index = SQLUpper.indexOf(SELECT+SPACE);
        return index;
    }
    private static int getSelectReturnIndex (String SQLString) {
        String SQLUpper = SQLString.toUpperCase();
        int index = SQLUpper.indexOf(SELECT+RETURN);
        return index;
    }

    /**
    * Get the index location of the FROM from a SQL statement
    * @param SQLString the SQL statment
    * @return the index of the FROM
    */
    public static int getFromIndex (String SQLString) {
        int fromSpaceIndx = getFromSpaceIndex(SQLString);
        int fromReturnIndx = getFromReturnIndex(SQLString);
        if(fromSpaceIndx>fromReturnIndx) {
            return fromSpaceIndx;
        }
        return fromReturnIndx;
    }

    private static int getFromSpaceIndex (String SQLString) {
        String SQLUpper = SQLString.toUpperCase();
        int startIndex = 0;
        int index = SQLUpper.indexOf(FROM+SPACE,startIndex);

        while(index>0) {
            String preceding = SQLString.substring(index-1,index);
            if( RETURN.equals(preceding) || SPACE.equals(preceding) ) {
                return index;
            }
            startIndex = index+1;
            index = SQLUpper.indexOf(FROM+SPACE,startIndex);
        }
        // If FROM is last item, set the index
        if(SQLUpper.endsWith(FROM)) {
            index = SQLUpper.lastIndexOf(FROM);
        }
        return index;
    }
    private static int getFromReturnIndex (String SQLString) {
        String SQLUpper = SQLString.toUpperCase();
        int startIndex = 0;
        int index = SQLUpper.indexOf(FROM+RETURN,startIndex);

        while(index>0) {
            String preceding = SQLString.substring(index-1,index);
            if( RETURN.equals(preceding) || SPACE.equals(preceding) ) {
                return index;
            }
            startIndex = index+1;
            index = SQLUpper.indexOf(FROM+RETURN,startIndex);
        }
        // If FROM is last item, set the index
        if(SQLUpper.endsWith(FROM)) {
            index = SQLUpper.lastIndexOf(FROM);
        }
        return index;
    }

    /**
    * Get the index location of the WHERE from a SQL statement
    * @param SQLString the SQL statment
    * @return the index of the WHERE
    */
    public static int getWhereIndex (String SQLString) {
        int whereSpaceIndx = getWhereSpaceIndex(SQLString);
        int whereReturnIndx = getWhereReturnIndex(SQLString);
        if(whereSpaceIndx>whereReturnIndx) {
            return whereSpaceIndx;
        }
        return whereReturnIndx;
    }

    private static int getWhereSpaceIndex (String SQLString) {
        String SQLUpper = SQLString.toUpperCase();
        int startIndex = 0;
        int index = SQLUpper.indexOf(WHERE+SPACE,startIndex);
        while(index>0) {
            String preceding = SQLString.substring(index-1,index);
            if( RETURN.equals(preceding) || SPACE.equals(preceding) ) {
                return index;
            }
            startIndex = index+1;
            index = SQLUpper.indexOf(WHERE+SPACE,startIndex);
        }
        // If WHERE is last item, set the index
        if(SQLString.endsWith(WHERE)) {
            index = SQLUpper.lastIndexOf(WHERE);
        }
        return index;
    }
    private static int getWhereReturnIndex (String SQLString) {
        String SQLUpper = SQLString.toUpperCase();
        int startIndex = 0;
        int index = SQLUpper.indexOf(WHERE+RETURN,startIndex);
        while(index>0) {
            String preceding = SQLString.substring(index-1,index);
            if( RETURN.equals(preceding) || SPACE.equals(preceding) ) {
                return index;
            }
            startIndex = index+1;
            index = SQLUpper.indexOf(WHERE+RETURN,startIndex);
        }
        // If WHERE is last item, set the index
        if(SQLString.endsWith(WHERE)) {
            index = SQLUpper.lastIndexOf(WHERE);
        }
        return index;
    }

    /**
    * Get the SELECT String from a SQL statement
    * @param SQLString the SQL statment
    * @param includeSelect flag to indicate whether to include the leading SELECT or not
    * @return the SELECT String
    */
    public static String getSelectString (String SQLString,boolean includeSelect) {
        int selectIndex = getSelectIndex(SQLString);
        int fromIndex = getFromIndex(SQLString);
        int whereIndex = getWhereIndex(SQLString);
        String result = BLANK;
        int offset=SELECT.length();
        if(includeSelect) {
            offset=0;
        }
        if(selectIndex!=-1) {
            if(fromIndex!=-1) {
                result = SQLString.substring(selectIndex+offset,fromIndex);
            } else if(whereIndex!=-1) {
                result = SQLString.substring(selectIndex+offset,whereIndex);
            } else {
                result = SQLString.substring(selectIndex+offset);
            }
        }
        return result;
    }

    /**
    * Get the FROM String from a SQL statement
    * @param SQLString the SQL statment
    * @param includeFrom flag to indicate whether to include the leading FROM or not
    * @return the FROM String
    */
    public static String getFromString (String SQLString,boolean includeFrom) {
        int fromIndex = getFromIndex(SQLString);
        int whereIndex = getWhereIndex(SQLString);
        String result = BLANK;
        int offset=FROM.length();
        if(includeFrom) {
            offset=0;
        }
        if(fromIndex!=-1) {
            if(whereIndex!=-1) {
                result = SQLString.substring(fromIndex+offset,whereIndex);
            } else {
                result = SQLString.substring(fromIndex+offset);
            }
        }
        return result;
    }

    /**
    * Get the WHERE String from a SQL statement
    * @param SQLString the SQL statment
    * @param includeWhere flag to indicate whether to include the leading WHERE or not
    * @return the WHERE String
    */
    public static String getWhereString (String SQLString,boolean includeWhere) {
        int whereIndex = getWhereIndex(SQLString);

        String result = BLANK;
        int offset=WHERE.length();
        if(includeWhere) {
            offset=0;
        }
        if(whereIndex!=-1) {
            result = SQLString.substring(whereIndex+offset);
        }
        return result;
    }

    /**
    * Determine if there is anything in the SELECT part of the statement
    * @param SQLString the SQL statment
    * @return true if there is anything besides spaces in the SELECT
    */
    public static boolean hasSelect (String SQLString) {
        String selectString = getSelectString(SQLString,false);
        if(selectString.trim().length()>0) return true;
        return false;
    }

    /**
    * Determine if there is anything in the FROM part of the statement
    * @param SQLString the SQL statment
    * @return true if there is anything besides spaces in the FROM
    */
    public static boolean hasFrom (String SQLString) {
        String fromString = getFromString(SQLString,false);
        if(fromString.trim().length()>0) return true;
        return false;
    }

    /**
    * Determine if there is anything in the WHERE part of the statement
    * @param SQLString the SQL statment
    * @return true if there is anything besides spaces in the WHERE
    */
    public static boolean hasWhere (String SQLString) {
        String whereString = getWhereString(SQLString,false);
        if(whereString.trim().length()>0) return true;
        return false;
    }

    /**
    * Append a string to the end of the From clause
    * @param SQLString the original SQL statment
    * @param appendString the String to append
    * @return the new SQL statement
    */
    public static String appendToFrom (String SQLString,String appendString) {
        String selectString = getSelectString(SQLString,true);
        String whereString = getWhereString(SQLString,true);

        if(!hasFrom(SQLString)) {
            return selectString+FROM+SPACE+appendString+SPACE+whereString;
        }
        String fromString = getFromString(SQLString,true);
        return selectString+fromString+COMMA+appendString+SPACE+whereString;
    }

    /**
    * Replace the Select clause of a query with the statement
    * @param SQLString the original SQL statment
    * @param selectString the new Select clause
    * @return the new SQL statement
    */
    public static String replaceSelect (String SQLString,String selectString) {
        String fromString = getFromString(SQLString,true);
        String whereString = getWhereString(SQLString,false);
        if(whereString.trim().length()!=0) {
            whereString = getWhereString(SQLString,true);
        } else {
            whereString = BLANK;
        }
        // Build the result string
        StringBuffer resultBuffer = new StringBuffer(selectString.trim());
        if(selectString.trim().length()!=0) {
            resultBuffer.append(SPACE);
        }
        resultBuffer.append(fromString.trim());
        if(fromString.trim().length()!=0 && whereString.trim().length()!=0) {
            resultBuffer.append(SPACE);
        }
        resultBuffer.append(whereString.trim());

        return resultBuffer.toString();
    }
    
    /**
    * Replace the From clause of a query with the statement
    * @param SQLString the original SQL statment
    * @param fromString the String to append
    * @return the new SQL statement
    */
    public static String replaceFrom (String SQLString,String fromString) {
        String selectString = getSelectString(SQLString,true);
        String whereString = getWhereString(SQLString,true);
        StringBuffer resultBuffer = new StringBuffer(selectString.trim());
        if(selectString.trim().length()!=0) {
            resultBuffer.append(SPACE);
        }
        resultBuffer.append(fromString.trim());
        if(fromString.trim().length()!=0 && whereString.trim().length()!=0) {
            resultBuffer.append(SPACE);
        }
        resultBuffer.append(whereString.trim());

        return resultBuffer.toString();
    }

    /*
     * Replace all occurrences of the search string with the replace string
     * in the source string buffer. If any of the strings is null or the search string
     * is zero length, the source string is returned.
     * @param source the source string buffer whose contents will be altered
     * @param search the string to search for in source
     * @param replace the string to substitute for search if present
     */
    public static void replaceAll(StringBuffer source, String search, String replace) {
        if (source != null && search != null && search.length() > 0 && replace != null) {
            int start = source.toString().indexOf(search);
            while (start > -1) {
                int end = start + search.length();
                source.replace(start, end, replace);
                start = source.toString().indexOf(search, start + replace.length());
            }
        }
    }
    
    /**
     * Utility method used to insert or replace SQL text, or any text for that matter
     * 
     * @param originalSqlText
     * @param newSqlText
     * @param insertOption (See <code>ISQLConstants.INSERT_OPTIONS</code> for values)
     * @param cursorOffset
     * @return
     */
    public static String insertSql(final String originalSqlText, final String newSqlText, final int insertOption, final int cursorOffset) {
    	CoreArgCheck.isTrue(insertOption <= INSERT_OPTIONS.INSERT_AT_END, "Insert SQL text option does not exist"); //$NON-NLS-1$
    	// Insert at the offset
    	switch( insertOption ) {

    		case INSERT_OPTIONS.INSERT_AT_BEGINNING:  {
        		return newSqlText + originalSqlText;
        	}
    		case INSERT_OPTIONS.INSERT_AT_CURSOR:  {
    	    	StringBuilder sb = new StringBuilder();
    	    	sb.append(originalSqlText.substring(0, cursorOffset));
    	    	sb.append(newSqlText);
    	    	sb.append(originalSqlText.substring(cursorOffset));
    	    	return sb.toString();
        	}
    		case INSERT_OPTIONS.INSERT_AT_END:  {
        		return originalSqlText + newSqlText;
        	}
    		case INSERT_OPTIONS.REPLACE_ALL:
    		default: return newSqlText;
    	}
    }
}
