package com.korotkevich.provider.specification;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.korotkevich.provider.exception.SpecificationException;

/**
 * Interface for SQL interactions description with data retrieve possibility
 * for read interactions
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public interface SqlRetrieveSpecification extends SqlSpecification {
	
	/**
	 * Retrieves object according to prepared query and parameters
	 * @param rs prepared result set for retrieving data
	 * @return Object type
	 * @throws SQLException if problems occured while getting the object from result set
	 */
	public Object retrieveObjectFromResultSet(ResultSet rs) throws SpecificationException;
	
	
}
