package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class UpdateServicePlanRelevanceSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "UPDATE internet_provider.service_plans "
										  + "SET relevant = ? " 
										  + "WHERE id = ?";
	private final static int PARAM_RELEVANT_ID_INDEX = 1;
	private final static int PARAM_ID_INDEX = 2;
	private ServicePlan servicePlan;
	private StatementType statementType;
	
	public UpdateServicePlanRelevanceSqlSpecification(ServicePlan servicePlan){
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
			ps.setBoolean(PARAM_RELEVANT_ID_INDEX, servicePlan.isRelevant());
			ps.setInt(PARAM_ID_INDEX, servicePlan.getId());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}

	}

}
