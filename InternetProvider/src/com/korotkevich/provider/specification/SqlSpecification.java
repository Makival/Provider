package com.korotkevich.provider.specification;

import java.sql.PreparedStatement;

import com.korotkevich.provider.exception.SpecificationException;

/**
 * Basic interface for SQL interactions description
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public interface SqlSpecification extends Specification {
	
	/**
	 * Prepares and retrieves the specified sql query in String format
	 * @return the prepared sql query (String)
	 */
	public String getSqlQuery();
	
	/**
	 * Adds parameters to prepared statement
	 * @param ps prepared statement for fill the parameters in
	 * @throws SpecificationException if problems occured while filling the parameters in
	 */
	public void fillInStatementParameters(PreparedStatement ps) throws SpecificationException;
	
	/**
	 * Retrieves statement type 
	 * @return statement enum StatementType
	 */
	public StatementType takeStatementType();
	
}
