package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.korotkevich.provider.entity.ClientAccount;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.StatementType;

public class FindClientAccountByUserIdSqlSpecification implements SqlRetrieveSpecification {
	private final static String SQL_QUERY = "SELECT clients_accounts.cash_balance, clients_accounts.traffic_balance, clients_accounts.users_id "
										  + "FROM internet_provider.clients_accounts "
										  + "WHERE users_id = ?";
	private final static int PARAM_USER_ID_INDEX = 1;
	private final static int CASH_BALANCE_INDEX = 1;
	private final static int TRAFFIC_BALANCE_INDEX = 2;
	private final static int USER_ID_INDEX = 3;
	private User user;
	private StatementType statementType;

	public FindClientAccountByUserIdSqlSpecification(User user) {
		this.user = user;
		this.statementType = StatementType.PREPARED_STATEMENT;
	}

	public StatementType takeStatementType() {
		return this.statementType;
	}

	@Override
	public String getSqlQuery() {
		return SQL_QUERY;
	}

	@Override
	public void fillInStatementParameters(PreparedStatement ps) throws SpecificationException {
		try {
			ps.setInt(PARAM_USER_ID_INDEX, user.getId());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

	@Override
	public Object retrieveObjectFromResultSet(ResultSet rs) throws SpecificationException {
		ClientAccount account;
		
		try {
			account = new ClientAccount(rs.getDouble(CASH_BALANCE_INDEX), rs.getDouble(TRAFFIC_BALANCE_INDEX),
					rs.getInt(USER_ID_INDEX));
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}

		return account;
	}

}
