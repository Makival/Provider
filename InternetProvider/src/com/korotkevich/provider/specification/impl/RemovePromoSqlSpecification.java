package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.korotkevich.provider.entity.Promo;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlSpecification;
import com.korotkevich.provider.specification.StatementType;

public class RemovePromoSqlSpecification implements SqlSpecification {
	private final static String SQL_QUERY = "DELETE FROM internet_provider.promos " + "WHERE promos.id = ?";
	private final static int PARAM_ID_INDEX = 1;

	private Promo promo;
	private StatementType statementType;

	public RemovePromoSqlSpecification(Promo promo) {
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

}
