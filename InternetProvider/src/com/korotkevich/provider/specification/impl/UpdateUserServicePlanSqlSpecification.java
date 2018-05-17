package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class UpdateUserServicePlanSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "UPDATE internet_provider.clients_service_plans "
			+ "SET cancelation_date = ? " 
			+ "WHERE users_id = ? AND cancelation_date IS NULL ";
	
	private final static int PARAM_CANCEL_DATE_ID_INDEX = 1;
	private final static int PARAM_USER_ID_INDEX = 2;
	private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private User user;
	private StatementType statementType;

	public UpdateUserServicePlanSqlSpecification(User user) {
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
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		String cancelation_date = dateFormat.format(new Date());

		try {
			ps.setString(PARAM_CANCEL_DATE_ID_INDEX, cancelation_date);
			ps.setInt(PARAM_USER_ID_INDEX, user.getId());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

}
