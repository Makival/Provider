package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserStatus;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.StatementType;

public class FindUserByIdSqlSpecification implements SqlRetrieveSpecification {
	private final static String SQL_QUERY = "SELECT users.id, users.login, users.name, users.surname, users.e_mail, DATE_FORMAT(users.birth_date, '%d-%m-%Y'), user_statuses.status "
										  + "FROM internet_provider.users "
										  + "LEFT JOIN internet_provider.user_statuses on internet_provider.users.user_statuses_id = internet_provider.user_statuses.id "
										  + "WHERE users.id = ?";
	private final static int PARAM_ID_INDEX = 1;
	private final static int ID_INDEX = 1;
	private final static int LOGIN_INDEX = 2;
	private final static int NAME_INDEX = 3;
	private final static int SURNAME_INDEX = 4;
	private final static int EMAIL_INDEX = 5;
	private final static int BIRTH_DATE_INDEX = 6;
	private final static int STATUS_INDEX = 7;
	
	private int idValue;
	private StatementType statementType;

	public FindUserByIdSqlSpecification(User user){	
		this.idValue = user.getId(); 
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
			ps.setInt(PARAM_ID_INDEX, idValue);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

	@Override
	public Object retrieveObjectFromResultSet(ResultSet rs) throws SpecificationException {
		User user;

		try {
			UserStatus status = UserStatus.valueOf(rs.getString(STATUS_INDEX).toUpperCase());
			user = new User(rs.getInt(ID_INDEX), rs.getString(LOGIN_INDEX), rs.getString(NAME_INDEX),
					rs.getString(SURNAME_INDEX), rs.getString(EMAIL_INDEX), rs.getString(BIRTH_DATE_INDEX), status);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}

		return user;
	}

}
