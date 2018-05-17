package com.korotkevich.provider.pool;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class provides methods connected with connection interactions
 * such as commit, rollback, setAutoCommit, statement close with exception check
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class ConnectionManager {
	private static Logger logger = LogManager.getLogger();

	/**
	 * Closes incoming statement and provides exception catch
	 * @param statement incoming object of Statement class and/or it's inheritors
	 */
	public static <T extends Statement> void closeStatement(T statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.log(Level.ERROR, "Unable to close statement: " + e);
			}
		}

	}
	
	/**
	 * Sets autoCommit value in incoming connection and provides exception catch
	 * @param connection incoming WrapperConnection object
	 * @param autoCommit incoming autoCommit value
	 */
	public static void setAutoCommit(WrapperConnection connection, boolean autoCommit) {
		try {
			connection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Unable to set auto-commit to connection: " + e);
		}
	}
	
	/**
	 * Executes rollback and provides exception catch
	 * @param connection incoming WrapperConnection object
	 */
	public static void rollback(WrapperConnection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Unable to rollback: " + e);
		}
	}
	
	/**
	 * Executes commit and provides exception catch
	 * @param connection incoming WrapperConnection object
	 */
	public static void commit(WrapperConnection connection) {
		try {
			connection.commit();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Unable to commit: " + e);
		}
	}
}
