package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.korotkevich.provider.entity.Promo;
import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.StatementType;

public class FindServicePlanByIdSqlSpecification implements SqlRetrieveSpecification {
	private final static String SQL_QUERY = "SELECT service_plans.id, service_plans.name, service_plans.traffic_limit, service_plans.monthly_fee, service_plans.access_cost, "
										  + "IFNULL(promos.id,0), promos.name, IFNULL(promos.access_discount,0), IFNULL(promos.traffic_bonus,0) "
										  + "FROM internet_provider.service_plans "
										  + "LEFT JOIN internet_provider.promos on internet_provider.service_plans.id = internet_provider.promos.service_plans_id "
										  + "WHERE service_plans.relevant = true AND service_plans.id = ? ";
	private final static int PARAM_ID_INDEX = 1;
	private final static int PARAM_NAME_INDEX = 2;
	private final static int PARAM_LIMIT_INDEX = 3;
	private final static int PARAM_FEE_INDEX = 4;
	private final static int PARAM_ACCESS_COST_INDEX = 5;
	private final static int PARAM_PROMO_ID_INDEX = 6;
	private final static int PARAM_PROMO_NAME_INDEX = 7;
	private final static int PARAM_PROMO_DISCOUNT_INDEX = 8;
	private final static int PARAM_PROMO_BONUS_INDEX = 9;
	private final static int ID_INDEX = 1;
	private int idValue;
	private StatementType statementType;

	public FindServicePlanByIdSqlSpecification(ServicePlan servicePlan){	
		this.idValue = servicePlan.getId(); 
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
		Promo promo;
		ServicePlan servicePlan;

		try {
			promo = new Promo(rs.getInt(PARAM_PROMO_ID_INDEX), rs.getString(PARAM_PROMO_NAME_INDEX),
					rs.getInt(PARAM_PROMO_DISCOUNT_INDEX), rs.getInt(PARAM_PROMO_BONUS_INDEX), idValue);

			servicePlan = new ServicePlan(rs.getInt(ID_INDEX), rs.getString(PARAM_NAME_INDEX),
					rs.getInt(PARAM_LIMIT_INDEX), rs.getDouble(PARAM_FEE_INDEX), rs.getDouble(PARAM_ACCESS_COST_INDEX),
					promo);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
		return servicePlan;
	}

}
