package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class UpdateUserStatusSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "UPDATE internet_provider.users "
			+ "SET user_statuses_id = ? " 
			+ "WHERE id = ?";
	
	private final static int PARAM_STATUS_ID_INDEX = 1;
	private final static int PARAM_ID_INDEX = 2;
	private User user;
	private StatementType statementType;
	
	public UpdateUserStatusSqlSpecification(User user){
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
			ps.setInt(PARAM_STATUS_ID_INDEX, user.getStatus().getId());
			ps.setInt(PARAM_ID_INDEX, user.getId());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

}
