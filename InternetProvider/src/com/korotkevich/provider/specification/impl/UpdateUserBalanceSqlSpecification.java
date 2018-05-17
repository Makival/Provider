package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.korotkevich.provider.entity.ClientAccount;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class UpdateUserBalanceSqlSpecification  implements SqlSpecification  {
	private final static String SQL_QUERY = "UPDATE internet_provider.clients_accounts "
										  + "SET cash_balance = ?" 
										  + "WHERE users_id = ?";
	
	private final static int PARAM_CASH_BALANCE_INDEX = 1;
	private final static int PARAM_ID_INDEX = 2;
	private ClientAccount account;
	private StatementType statementType;
	
	public UpdateUserBalanceSqlSpecification(ClientAccount account){
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
			ps.setDouble(PARAM_CASH_BALANCE_INDEX, account.getCashBalance());
			ps.setInt(PARAM_ID_INDEX, account.getClientId());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

}