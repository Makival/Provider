package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.StatementType;

public class FindServicePlanByUserIdSqlSpecification implements SqlRetrieveSpecification {
	private final static String SQL_QUERY = "SELECT service_plans.name, service_plans.traffic_limit, service_plans.monthly_fee, DATE_FORMAT(clients_service_plans.signing_date, '%d-%m-%Y') FROM internet_provider.clients_service_plans "
										   +"LEFT JOIN internet_provider.service_plans ON internet_provider.clients_service_plans.service_plans_id = internet_provider.service_plans.id "
										   +"WHERE clients_service_plans.users_id = ? AND clients_service_plans.cancelation_date IS NULL";
	private final static int PARAM_ID_INDEX = 1;
	private final static int SP_NAME_INDEX = 1;
	private final static int SP_LIMIT_INDEX = 2;
	private final static int SP_FEE_INDEX = 3;
	private final static int SP_SIGN_DATE_INDEX = 4;
	private int idValue;
	private StatementType statementType;

	public FindServicePlanByUserIdSqlSpecification(User user){	
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
		ServicePlan servicePlan;
		try {
			servicePlan = new ServicePlan(rs.getString(SP_NAME_INDEX), rs.getInt(SP_LIMIT_INDEX),
					rs.getDouble(SP_FEE_INDEX), rs.getString(SP_SIGN_DATE_INDEX));
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}

		return servicePlan;
	}

}
