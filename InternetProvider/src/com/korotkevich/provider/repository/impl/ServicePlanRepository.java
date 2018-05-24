package com.korotkevich.provider.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.korotkevich.provider.entity.ServicePlan;
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
import com.korotkevich.provider.specification.impl.AddServicePlanSqlSpecification;
import com.korotkevich.provider.specification.impl.UpdateServicePlanRelevanceSqlSpecification;

/**
 * Implements Repository interface. Interacts with service plan data 
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class ServicePlanRepository implements Repository<ServicePlan> {
	private ConnectionPool connectionPool;

	public ServicePlanRepository() throws RepositoryException {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new RepositoryException(e);
		}
	}
	@Override
	public void add(ServicePlan servicePlan) throws RepositoryException {
		WrapperConnection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.getConnection();
			AddServicePlanSqlSpecification sqlSpecification = new AddServicePlanSqlSpecification(servicePlan);
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
		PreparedStatement ps = null;
		List<PreparedStatement> psList = new ArrayList<>();
		try {
			connection = connectionPool.getConnection();
			ConnectionManager.setAutoCommit(connection, false);
			for (Specification specification : specifications) {
				SqlSpecification sqlSpecification = (SqlSpecification) specification;
				String sqlQuery = sqlSpecification.getSqlQuery();
				ps = connection.prepareStatement(sqlQuery);
				psList.add(ps);
				sqlSpecification.fillInStatementParameters(ps);
				ps.executeUpdate();
			}
			ConnectionManager.commit(connection);
		} catch (ConnectionPoolException | SQLException | SpecificationException e) {
			ConnectionManager.rollback(connection);
			throw new RepositoryException(e);
		} finally {
			ConnectionManager.setAutoCommit(connection, true);
			for (PreparedStatement psElement : psList) {
				ConnectionManager.closeStatement(psElement);
			}
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void remove(ServicePlan servicePlan) throws RepositoryException {
		WrapperConnection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.getConnection();
			UpdateServicePlanRelevanceSqlSpecification sqlSpecification = new UpdateServicePlanRelevanceSqlSpecification(servicePlan);
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
	public List<ServicePlan> query(Specification specification) throws RepositoryException {
		List<ServicePlan> servicePlanList = new ArrayList<>();
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
				ServicePlan servicePlan = (ServicePlan) sqlSpecification.retrieveObjectFromResultSet(rs);
				servicePlanList.add(servicePlan);
			}					
			
		} catch (ConnectionPoolException | SQLException | SpecificationException e) {
			throw new RepositoryException(e);
		} finally {
			ConnectionManager.closeStatement(ps);
			connectionPool.returnConnection(connection);
		}

		return servicePlanList;
	}

}
