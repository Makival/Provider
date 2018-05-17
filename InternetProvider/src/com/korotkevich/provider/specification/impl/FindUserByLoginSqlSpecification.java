package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.StatementType;

public class FindUserByLoginSqlSpecification implements SqlRetrieveSpecification {
	private final static String SQL_QUERY = "SELECT users.id FROM internet_provider.users WHERE users.login = ?";
	private final static int PARAM_LOGIN_INDEX = 1;
	private final static int ID_INDEX = 1;
	private String loginValue;
	private StatementType statementType;

	public FindUserByLoginSqlSpecification(User user){	
		this.loginValue = user.getLogin(); 
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
			ps.setString(PARAM_LOGIN_INDEX, loginValue);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

	@Override
	public Object retrieveObjectFromResultSet(ResultSet rs) throws SpecificationException {
		User user;
		try {
			user = new User(rs.getInt(ID_INDEX), loginValue);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}

		return user;
	}

}
