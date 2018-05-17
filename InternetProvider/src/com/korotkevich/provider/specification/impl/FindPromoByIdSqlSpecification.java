package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.korotkevich.provider.entity.Promo;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.StatementType;

public class FindPromoByIdSqlSpecification implements SqlRetrieveSpecification {
	private final static String SQL_QUERY = "SELECT promos.id, promos.name FROM internet_provider.promos WHERE promos.id = ?";
	private final static int PARAM_ID_INDEX = 1;
	private final static int PROMO_ID_INDEX = 1;
	private final static int PROMO_NAME_INDEX = 2;
	private Promo promo;
	private StatementType statementType;

	public FindPromoByIdSqlSpecification(Promo promo) {
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
			ps.setInt(PARAM_ID_INDEX, promo.getId());
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

	@Override
	public Object retrieveObjectFromResultSet(ResultSet rs) throws SpecificationException {
		Promo promo;
		
		try {
			promo = new Promo(rs.getInt(PROMO_ID_INDEX), rs.getString(PROMO_NAME_INDEX));
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
		return promo;
	}

}
