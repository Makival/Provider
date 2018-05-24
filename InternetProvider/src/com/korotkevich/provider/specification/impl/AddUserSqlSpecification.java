package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class AddUserSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "INSERT INTO internet_provider.users "
											+"(name, surname, login, password, role, user_statuses_id, e_mail, registration_date, birth_date) VALUES " 
											+ "(?,?,?,SHA1(?),?,?,?,?,?)";
	private final static int PARAM_NAME_INDEX = 1;
	private final static int PARAM_SURNAME_INDEX = 2;
	private final static int PARAM_LOGIN_INDEX = 3;
	private final static int PARAM_PASSWORD_INDEX = 4;
	private final static int PARAM_ROLE_INDEX = 5;
	private final static int PARAM_STATUS_INDEX = 6;
	private final static int PARAM_EMAIL_INDEX = 7;
	private final static int PARAM_REG_DATE_INDEX = 8;
	private final static int PARAM_BIRTH_DATE_INDEX = 9;
	private final static String SIMPLE_DATE_FORMAT = "dd-MM-yyyy";
	private final static String SQL_DATE_FORMAT = "yyyy-MM-dd";
	private User user;
	private StatementType statementType;
	
	public AddUserSqlSpecification(User user){
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
		DateFormat simpleFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);

		try {
			Date regDate = simpleFormat.parse(user.getRegistrationDate());
			Date birthDate = simpleFormat.parse(user.getBirthDate());
			DateFormat sqlFormat = new SimpleDateFormat(SQL_DATE_FORMAT);
			String sqlRegDate = sqlFormat.format(regDate);
			String sqlBirthDate = sqlFormat.format(birthDate);

			ps.setString(PARAM_NAME_INDEX, user.getName());
			ps.setString(PARAM_SURNAME_INDEX, user.getSurname());
			ps.setString(PARAM_LOGIN_INDEX, user.getLogin());
			ps.setString(PARAM_PASSWORD_INDEX, String.valueOf(user.getPassword()));
			ps.setString(PARAM_ROLE_INDEX, user.getRole().toString().toLowerCase());
			ps.setInt(PARAM_STATUS_INDEX, user.getStatus().getId());
			ps.setString(PARAM_EMAIL_INDEX, user.getEmail());
			ps.setString(PARAM_REG_DATE_INDEX, sqlRegDate);
			ps.setString(PARAM_BIRTH_DATE_INDEX, sqlBirthDate);
		} catch (SQLException | ParseException e) {
			throw new SpecificationException(e);
		}
	}

}
