package com.korotkevich.provider.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implements ProtectedConnection interface
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class WrapperConnection implements ProtectedConnection {
	private Connection connection;
	
	WrapperConnection(Connection connection){
		this.connection = connection;
	}
	
	void close() throws SQLException {
		connection.close();	
	}

	@Override
	public void commit() throws SQLException {
		connection.commit();	
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	@Override
	public void rollback() throws SQLException {
		connection.rollback();	
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		connection.setAutoCommit(autoCommit);		
	}

	@Override
	public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}


}
