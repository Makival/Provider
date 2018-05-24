package com.korotkevich.provider.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.korotkevich.provider.entity.Promo;
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
import com.korotkevich.provider.specification.impl.AddPromoSqlSpecification;
import com.korotkevich.provider.specification.impl.RemovePromoSqlSpecification;

/**
 * Implements Repository interface. Interacts with promo data 
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class PromoRepository implements Repository<Promo> {
	private ConnectionPool connectionPool;
	
	public PromoRepository() throws RepositoryException {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new RepositoryException(e);
		}
	}
	
	@Override
	public void add(Promo promo) throws RepositoryException {
		WrapperConnection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.getConnection();
			AddPromoSqlSpecification sqlSpecification = new AddPromoSqlSpecification(promo);
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
	public void remove(Promo promo) throws RepositoryException {
		WrapperConnection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.getConnection();
			RemovePromoSqlSpecification sqlSpecification = new RemovePromoSqlSpecification(promo);
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
	public List<Promo> query(Specification specification) throws RepositoryException {
		List<Promo> promoList = new ArrayList<>();
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
				Promo promo = (Promo) sqlSpecification.retrieveObjectFromResultSet(rs);
				promoList.add(promo);
			}					
			
		} catch (ConnectionPoolException | SQLException | SpecificationException e) {
			throw new RepositoryException(e);
		} finally {
			ConnectionManager.closeStatement(ps);
			connectionPool.returnConnection(connection);
		}

		return promoList;
	}

}
