package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.korotkevich.provider.entity.ClientAccount;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class AddClientAccountSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "INSERT INTO internet_provider.clients_accounts (users_id, cash_balance, traffic_balance)"
			+ "VALUES(?,?,?)";
	private final static int PARAM_USER_ID_INDEX = 1;
	private final static int PARAM_CASH_BALANCE_INDEX = 2;
	private final static int PARAM_TRAFFIC_BALANCE_INDEX = 3;

	private ClientAccount account;
	private StatementType statementType;

	public AddClientAccountSqlSpecification(ClientAccount account) {
		this.account = account;
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
			ps.setInt(PARAM_USER_ID_INDEX, account.getClientId());
			ps.setDouble(PARAM_CASH_BALANCE_INDEX, account.getCashBalance());
			ps.setDouble(PARAM_TRAFFIC_BALANCE_INDEX, account.getTrafficBalance());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}
}
