package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class AddUserServicePlanSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "INSERT INTO internet_provider.clients_service_plans "
											+"(signing_date, users_id, service_plans_id) VALUES " 
											+ "(?,?,?)";
	private final static int PARAM_SIGN_DATE_INDEX = 1;
	private final static int PARAM_USER_ID_INDEX = 2;
	private final static int PARAM_SP_ID_INDEX = 3;
	private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private User user;
	private ServicePlan servicePlan;
	private StatementType statementType;
	
	public AddUserServicePlanSqlSpecification(User user, ServicePlan servicePlan){
		this.user = user; 
		this.servicePlan = servicePlan; 
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
		String signing_date = dateFormat.format(new Date());
		try {
			ps.setString(PARAM_SIGN_DATE_INDEX, signing_date);
			ps.setInt(PARAM_USER_ID_INDEX, user.getId());
			ps.setInt(PARAM_SP_ID_INDEX, servicePlan.getId());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}
}
