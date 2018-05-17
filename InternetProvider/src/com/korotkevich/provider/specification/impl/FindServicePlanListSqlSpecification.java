package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.korotkevich.provider.entity.Promo;
import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.criteria.Criteria;
import com.korotkevich.provider.entity.criteria.SearchCriteria;
import com.korotkevich.provider.entity.criteria.SearchCriteria.ServicePlanCriteria;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.StatementType;

public class FindServicePlanListSqlSpecification implements SqlRetrieveSpecification {
	private final static String SQL_QUERY_PATTERN_DEFAULT = "SELECT service_plans.id, service_plans.name as spName, service_plans.traffic_limit, service_plans.monthly_fee, service_plans.description, service_plans.access_cost, service_plans.relevant, "
											+ "(SELECT MAX(service_plans.id) FROM internet_provider.service_plans WHERE service_plans.id > 0 %s ) as maxSpId, "
											+ "IFNULL(promos.id,0), promos.name, IFNULL(promos.access_discount,0), IFNULL(traffic_bonus, 0) "
											+ "FROM internet_provider.service_plans "
											+ "LEFT JOIN internet_provider.promos ON internet_provider.service_plans.id = internet_provider.promos.service_plans_id "
											+ "AND internet_provider.promos.active = true "
											+ "WHERE service_plans.id > ? AND service_plans.relevant = true %s " 
											+ "ORDER BY service_plans.id " 
											+ "LIMIT ?";
	private final static String SQL_QUERY_PATTERN_DESC = "SELECT * FROM (SELECT service_plans.id, service_plans.name as spName, service_plans.traffic_limit, service_plans.monthly_fee, service_plans.description, service_plans.access_cost, service_plans.relevant, "
											+ "(SELECT MAX(service_plans.id) FROM internet_provider.service_plans WHERE service_plans.id > 0 %s ) as maxSpId, "
											+ "IFNULL(promos.id,0), promos.name, IFNULL(promos.access_discount,0), IFNULL(traffic_bonus, 0) " 
											+ "FROM internet_provider.service_plans "
											+ "LEFT JOIN internet_provider.promos ON internet_provider.service_plans.id = internet_provider.promos.service_plans_id " 
											+ "AND internet_provider.promos.active = true "
											+ "WHERE service_plans.id < ? AND service_plans.relevant = true %s " 
											+ "ORDER BY service_plans.id DESC " 
											+ "LIMIT ?) as sp_data "
											+ "ORDER BY sp_data.id";
	private final static int ID_INDEX = 1;
	private final static int NAME_INDEX = 2;
	private final static int LIMIT_INDEX = 3;
	private final static int FEE_INDEX = 4;
	private final static int DESCRIPTION_INDEX = 5;
	private final static int ACCESS_COST_INDEX = 6;
	private final static int RELEVANT_INDEX = 7;
	private final static int MAX_ID_INDEX = 8;	
	private final static int PROMO_ID_INDEX = 9;
	private final static int PROMO_NAME_INDEX = 10;
	private final static int PROMO_DISCOUNT_INDEX = 11;
	private final static int PROMO_BONUS_INDEX = 12;	
	private final static int DEFAULT_LIMIT_VALUE = 7;
	private final static String DIRECTION_NEXT = "next";
	private final static String SP_ID = "servicePlanId";
	private final static String LAST_SP_ID = "lastSpId";
	private final static String DIRECTION = "direction";
	private final static String EXTENTION_WILDCARD = "%";
	private StatementType statementType;
	private Map<String,Object>parametersMap; 
	private Criteria<SearchCriteria.ServicePlanCriteria> servicePlanCriteria;
	private String nameCriteriaValue;
	private Double costMinCriteriaValue;
	private Double costMaxCriteriaValue;
	private Double feeMinCriteriaValue;
	private Double feeMaxCriteriaValue;	
	
	public FindServicePlanListSqlSpecification(Map<String,Object>parametersMap, Criteria<SearchCriteria.ServicePlanCriteria> servicePlanCriteria) {
		this.parametersMap = parametersMap; 
		this.servicePlanCriteria = servicePlanCriteria; 
		this.statementType = StatementType.PREPARED_STATEMENT; 
	}
	
	public StatementType takeStatementType() {
		return this.statementType; 
	}

	@Override
	public String getSqlQuery() {
		String direction = (String) parametersMap.get(DIRECTION); 	
		String sqlQuerry = direction.equals(DIRECTION_NEXT) ? SQL_QUERY_PATTERN_DEFAULT : SQL_QUERY_PATTERN_DESC; 
		sqlQuerry = prepareCriteriesPattern(sqlQuerry);
		return sqlQuerry;
	}

	@Override
	public void fillInStatementParameters(PreparedStatement ps) throws SpecificationException {
		int servicePlanId = (int) parametersMap.get(SP_ID);
		int paramIndex = 0;
		int iterationLimit = 2;
		
		try {
			if (!servicePlanCriteria.isEmpty()) {
				int iterationNum = 0;

				while (iterationNum < iterationLimit) {
					iterationNum++;
					if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.NAME)) {
						paramIndex++;
						ps.setString(paramIndex, EXTENTION_WILDCARD + nameCriteriaValue + EXTENTION_WILDCARD);
					}

					if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.ACCESS_COST_MIN)) {
						paramIndex++;
						ps.setDouble(paramIndex, costMinCriteriaValue);
					}

					if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.ACCESS_COST_MAX)) {
						paramIndex++;
						ps.setDouble(paramIndex, costMaxCriteriaValue);
					}

					if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.MONTHLY_FEE_MIN)) {
						paramIndex++;
						ps.setDouble(paramIndex, feeMinCriteriaValue);
					}

					if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.MONTHLY_FEE_MAX)) {
						paramIndex++;
						ps.setDouble(paramIndex, feeMaxCriteriaValue);
					}

					if (iterationNum < iterationLimit) {
						paramIndex++;
						ps.setInt(paramIndex, servicePlanId);
					}

				}
			} else {
				paramIndex++;
				ps.setInt(paramIndex, servicePlanId);
			}
			paramIndex++;
			ps.setInt(paramIndex, DEFAULT_LIMIT_VALUE);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

	@Override
	public Object retrieveObjectFromResultSet(ResultSet rs) throws SpecificationException {
		Promo promo;
		ServicePlan servicePlan;
		try {
			int promoId = rs.getInt(PROMO_ID_INDEX);
			int servicePlanId = rs.getInt(ID_INDEX);
			if (promoId > 0) {
				promo = new Promo(promoId, rs.getString(PROMO_NAME_INDEX), rs.getInt(PROMO_DISCOUNT_INDEX),
						rs.getInt(PROMO_BONUS_INDEX), true, servicePlanId);
			} else {
				promo = new Promo();
			}

			servicePlan = new ServicePlan(servicePlanId, rs.getString(NAME_INDEX), rs.getInt(LIMIT_INDEX),
					rs.getDouble(FEE_INDEX), rs.getString(DESCRIPTION_INDEX), rs.getDouble(ACCESS_COST_INDEX),
					rs.getBoolean(RELEVANT_INDEX), promo);

			int lastSpId = rs.getInt(MAX_ID_INDEX);
			parametersMap.put(LAST_SP_ID, lastSpId);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}

		return servicePlan;
	}
	
	private String prepareCriteriesPattern(String sqlQuerry) {	
		
		String param = "";
		String innerParam = "";
		
		if (!servicePlanCriteria.isEmpty()) {
			
			if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.NAME)) {
				param += "AND service_plans.name LIKE ? ";
				innerParam += "AND service_plans.name LIKE ? ";
				nameCriteriaValue = (String)servicePlanCriteria.get(ServicePlanCriteria.NAME);
			}

			if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.ACCESS_COST_MIN)) {
				param += "AND service_plans.access_cost * (1 - IFNULL(promos.access_discount,0)/100)  >= ? ";
				innerParam += "AND service_plans.access_cost * (1 - IFNULL(promos.access_discount,0)/100) >= ? ";
				costMinCriteriaValue = (Double)servicePlanCriteria.get(ServicePlanCriteria.ACCESS_COST_MIN); 
			}

			if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.ACCESS_COST_MAX)) {
				param += "AND service_plans.access_cost * (1 - IFNULL(promos.access_discount,0)/100) <= ? ";
				innerParam += "AND service_plans.access_cost * (1 - IFNULL(promos.access_discount,0)/100) <= ? ";
				costMaxCriteriaValue = (Double)servicePlanCriteria.get(ServicePlanCriteria.ACCESS_COST_MAX); 
			}
			
			if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.MONTHLY_FEE_MIN)) {
				param += "AND service_plans.monthly_fee >= ? ";
				innerParam += "AND service_plans.monthly_fee >= ? ";
				feeMinCriteriaValue = (Double)servicePlanCriteria.get(ServicePlanCriteria.MONTHLY_FEE_MIN);
			}
			
			if (servicePlanCriteria.containsCriteriaKey(ServicePlanCriteria.MONTHLY_FEE_MAX)) {
				param += "AND service_plans.monthly_fee <= ?";
				innerParam += "AND service_plans.monthly_fee <= ?";
				feeMaxCriteriaValue = (Double)servicePlanCriteria.get(ServicePlanCriteria.MONTHLY_FEE_MAX); 
			}
		}
		return String.format(sqlQuerry, innerParam, param);
	}

}
