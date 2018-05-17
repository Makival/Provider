package com.korotkevich.provider.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.ConnectionPoolException;
import com.korotkevich.provider.exception.RepositoryException;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.pool.ConnectionManager;
import com.korotkevich.provider.pool.ConnectionPool;
import com.korotkevich.provider.pool.WrapperConnection;
import com.korotkevich.provider.repository.Repository;
import com.korotkevich.provider.specification.Specification;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;
import com.korotkevich.provider.specification.impl.AddUserSqlSpecification;
import com.korotkevich.provider.specification.impl.UpdateUserStatusSqlSpecification;

public class UserRepository implements Repository<User> {
	private ConnectionPool connectionPool;

	public UserRepository() throws RepositoryException {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new RepositoryException(e);
		}
	}
	
	@Override
	public void add(User user) throws RepositoryException {
		WrapperConnection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.getConnection();
			AddUserSqlSpecification sqlSpecification = new AddUserSqlSpecification(user);
			String sqlQuery = sqlSpecification.getSqlQuery();

			ps = connection.prepareStatement(sqlQuery);
			sqlSpecification.fillInStatementParameters(ps);
			ps.executeUpdate();

		} catch (ConnectionPoolException | SQLException | SpecificationException e) {
			throw new RepositoryException(e);
		}finally {
			ConnectionManager.closeStatement(ps);
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void update(Specification... specifications) throws RepositoryException {
		WrapperConnection connection = null;
		Statement statement = null;
		List<Statement> statementList = new ArrayList<>();
		try {
			connection = connectionPool.getConnection();
			ConnectionManager.setAutoCommit(connection, false);
			for (Specification specification : specifications) {
				SqlSpecification sqlSpecification = (SqlSpecification) specification;
				executeDataUpdate(connection, sqlSpecification, statement);
				statementList.add(statement);
			}
			ConnectionManager.commit(connection);
		} catch (ConnectionPoolException | SQLException | SpecificationException e) {
			ConnectionManager.rollback(connection);
			throw new RepositoryException(e);
		} finally {
			ConnectionManager.setAutoCommit(connection, true);
			for (Statement statementElement : statementList) {
				ConnectionManager.closeStatement(statementElement);
			}
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void remove(User user)  throws RepositoryException  {
		WrapperConnection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.getConnection();
			UpdateUserStatusSqlSpecification sqlSpecification = new UpdateUserStatusSqlSpecification(user);
			String sqlQuery = sqlSpecification.getSqlQuery();

			ps = connection.prepareStatement(sqlQuery);
			sqlSpecification.fillInStatementParameters(ps);
			ps.executeUpdate();

		} catch (ConnectionPoolException | SQLException | SpecificationException e) {
			throw new RepositoryException(e);
		}finally {
			ConnectionManager.closeStatement(ps);
			connectionPool.returnConnection(connection);
		}

	}

	@Override
	public List<User> query(Specification specification) throws RepositoryException {
		List<User> userList = new ArrayList<>();
		WrapperConnection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.getConnection();
			SqlRetrieveSpecification sqlSpecification = (SqlRetrieveSpecification) specification;
			String sqlQuery = sqlSpecification.getSqlQuery();

			ps = connection.prepareStatement(sqlQuery);
			sqlSpecification.fillInStatementParameters(ps);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				User user = (User) sqlSpecification.retrieveObjectFromResultSet(rs);
				userList.add(user);
			}					
			
		} catch (ConnectionPoolException | SQLException | SpecificationException e) {
			throw new RepositoryException(e);
		} finally {
			ConnectionManager.closeStatement(ps);
			connectionPool.returnConnection(connection);
		}

		return userList;
	}
	
	private void executeDataUpdate(WrapperConnection connection, SqlSpecification sqlSpecification, Statement statement)
			throws SQLException, SpecificationException {
		String sqlQuery = sqlSpecification.getSqlQuery();
		if (sqlSpecification.takeStatementType() == StatementType.PREPARED_STATEMENT) {
			statement = connection.prepareStatement(sqlQuery);
			PreparedStatement prepStatement = (PreparedStatement) statement;
			sqlSpecification.fillInStatementParameters(prepStatement);
			prepStatement.executeUpdate();
		} else {
			statement = connection.createStatement();
			statement.executeUpdate(sqlQuery);
		}
	}

}
