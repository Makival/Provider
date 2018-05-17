package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.korotkevich.provider.entity.Promo;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class AddPromoSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "INSERT INTO internet_provider.promos "
			+ "(name, access_discount, active, traffic_bonus, service_plans_id) VALUES " + "(?,?,?,?,?)";
	private final static int PARAM_NAME_INDEX = 1;
	private final static int PARAM_ACCESS_DISCOUNT_INDEX = 2;
	private final static int PARAM_ACTIVE_INDEX = 3;
	private final static int PARAM_BONUS_INDEX = 4;
	private final static int PARAM_SP_ID_INDEX = 5;

	private Promo promo;
	private StatementType statementType;

	public AddPromoSqlSpecification(Promo promo) {
		this.promo = promo;
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
			ps.setString(PARAM_NAME_INDEX, promo.getName());
			ps.setInt(PARAM_ACCESS_DISCOUNT_INDEX, promo.getAccessDiscount());
			ps.setBoolean(PARAM_ACTIVE_INDEX, promo.isActive());
			ps.setInt(PARAM_BONUS_INDEX, promo.getTrafficBonus());
			ps.setInt(PARAM_SP_ID_INDEX, promo.getServicePlanId());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

}
