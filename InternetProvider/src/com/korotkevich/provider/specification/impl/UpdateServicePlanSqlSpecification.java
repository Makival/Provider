package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class UpdateServicePlanSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "UPDATE internet_provider.service_plans "
									      + "SET name = ?, traffic_limit = ?, monthly_fee = ?, description = ?, access_cost = ? " 
									      + "WHERE id = ?";
	
	private final static int PARAM_NAME_INDEX = 1;
	private final static int PARAM_LIMIT_INDEX = 2;
	private final static int PARAM_FEE_INDEX = 3;
	private final static int PARAM_DESCRIPTION_INDEX = 4;
	private final static int PARAM_ACCESS_COST_INDEX = 5;
	private final static int PARAM_ID_INDEX = 6;
	private ServicePlan servicePlan;
	private StatementType statementType;
	
	public UpdateServicePlanSqlSpecification(ServicePlan servicePlan){
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
		try {
			ps.setString(PARAM_NAME_INDEX, servicePlan.getName());
			ps.setDouble(PARAM_LIMIT_INDEX, servicePlan.getTrafficLimit());
			ps.setDouble(PARAM_FEE_INDEX, servicePlan.getMonthlyFee());
			ps.setString(PARAM_DESCRIPTION_INDEX, servicePlan.getDescription());
			ps.setDouble(PARAM_ACCESS_COST_INDEX, servicePlan.getAccessCost());
			ps.setInt(PARAM_ID_INDEX, servicePlan.getId());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}

	}

}
