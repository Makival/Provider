package com.korotkevich.provider.pool;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interface provides encapsulation for the connection class
 * and provides declaration for it's encapsulated methods
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public interface ProtectedConnection {

	/**
	 * Declaration for the commit method
	 * @throws SQLException error occurs or this method is called on a closed connection
	 */
	public void commit() throws SQLException;

	/**
	 * Declaration for the creating prepared statement method
	 * @param sql incoming sql query value (String)
	 * @return prepared statement object
	 * @throws SQLException error occurs or this method is called on a closed connection
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException;
	
	/**
	 * Declaration for the creating statement method
	 * @return statement object
	 * @throws SQLException error occurs or this method is called on a closed connection
	 */
	public Statement createStatement() throws SQLException;

	/**
	 * Declaration for the rollback method
	 * @throws SQLException error occurs or this method is called on a closed connection
	 */
	public void rollback() throws SQLException;

	/**
	 * Declaration for the setAutoCommit method
	 * @param autoCommit incoming autoCommit value(boolean)
	 * @throws SQLException error occurs or this method is called on a closed connection
	 */
	public void setAutoCommit(boolean autoCommit) throws SQLException;
}
