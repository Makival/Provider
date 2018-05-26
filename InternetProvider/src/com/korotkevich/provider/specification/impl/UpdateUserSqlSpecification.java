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

public class UpdateUserSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "UPDATE internet_provider.users "
			+ "SET name = ?, surname = ?, login = ?, e_mail = ?, birth_date = ?, avatar_path = ? " 
			+ "WHERE id = ?";
	
	private final static int PARAM_NAME_INDEX = 1;
	private final static int PARAM_SURNAME_INDEX = 2;
	private final static int PARAM_LOGIN_INDEX = 3;
	private final static int PARAM_EMAIL_INDEX = 4;
	private final static int PARAM_BIRTH_DATE_INDEX = 5;
	private final static int PARAM_AVATAR_PATH_INDEX = 6;
	private final static int PARAM_ID_INDEX = 7;
	private final static String SIMPLE_DATE_FORMAT = "dd-MM-yyyy";
	private final static String SQL_DATE_FORMAT = "yyyy-MM-dd";
	private User user;
	private StatementType statementType;
	
	public UpdateUserSqlSpecification(User user){
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
			Date birthDate = simpleFormat.parse(user.getBirthDate());
			DateFormat sqlFormat = new SimpleDateFormat(SQL_DATE_FORMAT);
			String sqlBirthDate = sqlFormat.format(birthDate);

			ps.setString(PARAM_NAME_INDEX, user.getName());
			ps.setString(PARAM_SURNAME_INDEX, user.getSurname());
			ps.setString(PARAM_LOGIN_INDEX, user.getLogin());
			ps.setString(PARAM_EMAIL_INDEX, user.getEmail());
			ps.setString(PARAM_BIRTH_DATE_INDEX, sqlBirthDate);
			ps.setString(PARAM_AVATAR_PATH_INDEX, user.getAvatarPath());
			ps.setInt(PARAM_ID_INDEX, user.getId());
		} catch (SQLException | ParseException e) {
			throw new SpecificationException(e);
		}
	}

}
