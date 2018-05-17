package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;

import com.korotkevich.provider.entity.ClientAccount;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class UpdateUserAccountSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERRY_PATTERN = "UPDATE internet_provider.clients_accounts "
			+ "SET cash_balance = %s, traffic_balance = %s " 
			+ "WHERE users_id = %s";
	private ClientAccount account;
	private StatementType statementType;
	
	public UpdateUserAccountSqlSpecification(ClientAccount account){
		this.account = account; 
		this.statementType = StatementType.STATEMENT; 
	}
	
	public StatementType takeStatementType() {
		return this.statementType; 
	}
	
	@Override
	public String getSqlQuery() {
		Object parameters[] = {account.getCashBalance(), account.getTrafficBalance(), account.getClientId()};
		String sqlQuerry = String.format(SQL_QUERRY_PATTERN, parameters);
		return sqlQuerry;
	}

	@Override
	public void fillInStatementParameters(PreparedStatement ps) throws SpecificationException {	
		throw new UnsupportedOperationException();	
	}

}
