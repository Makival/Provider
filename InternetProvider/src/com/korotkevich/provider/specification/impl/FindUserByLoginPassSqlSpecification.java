package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserRole;
import com.korotkevich.provider.entity.UserStatus;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.StatementType;

public class FindUserByLoginPassSqlSpecification implements SqlRetrieveSpecification {
	private final static String SQL_QUERY ="SELECT users.name, users.surname, users.login, users.id, users.role, user_statuses.status, "
											+ "users.e_mail, DATE_FORMAT(users.registration_date, '%d-%m-%Y'), DATE_FORMAT(users.birth_date, '%d-%m-%Y') " 
			 								+ "FROM internet_provider.users "
			 								+"LEFT JOIN internet_provider.user_statuses ON internet_provider.users.user_statuses_id = internet_provider.user_statuses.id "
			 								+"WHERE users.login = ? AND users.password = MD5(?)";
	private final static int PARAM_LOGIN_INDEX = 1;
	private final static int PARAM_PASS_INDEX = 2;
	private final static int NAME_INDEX = 1;
	private final static int SURNAME_INDEX = 2;
	private final static int LOGIN_INDEX = 3;
	private final static int ID_INDEX = 4;
	private final static int ROLE_INDEX = 5;
	private final static int STATUS_INDEX = 6;
	private final static int EMAIL_INDEX = 7;
	private final static int REG_DATE_INDEX = 8;
	private final static int BIRTH_DATE_INDEX = 9;
	
	private String loginValue;
	private String passwordValue;
	private StatementType statementType;
	
	public FindUserByLoginPassSqlSpecification(User user){	
		this.loginValue = user.getLogin(); 
		this.passwordValue = String.valueOf(user.getPassword());
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
			ps.setString(PARAM_PASS_INDEX, passwordValue);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

	@Override
	public User retrieveObjectFromResultSet(ResultSet rs) throws SpecificationException {
		User user;

		try {
			String roleName = rs.getString(ROLE_INDEX).toUpperCase();
			String statusName = rs.getString(STATUS_INDEX).toUpperCase();
			UserRole role = UserRole.valueOf(roleName);
			UserStatus status = UserStatus.valueOf(statusName);

			user = new User(rs.getInt(ID_INDEX), rs.getString(LOGIN_INDEX), rs.getString(NAME_INDEX),
					rs.getString(SURNAME_INDEX), rs.getString(EMAIL_INDEX), role, status, rs.getString(REG_DATE_INDEX), rs.getString(BIRTH_DATE_INDEX));
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}

		return user;
	}

}
	

