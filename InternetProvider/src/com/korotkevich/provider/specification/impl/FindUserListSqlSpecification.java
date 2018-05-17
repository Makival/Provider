package com.korotkevich.provider.specification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserRole;
import com.korotkevich.provider.entity.UserStatus;
import com.korotkevich.provider.exception.SpecificationException;
import com.korotkevich.provider.specification.SqlRetrieveSpecification;
import com.korotkevich.provider.specification.StatementType;

public class FindUserListSqlSpecification implements SqlRetrieveSpecification {
	private final static String SQL_QUERRY_PATTERN = "SELECT * FROM (SELECT users.id, users.login, users.name, users.surname, users.e_mail, DATE_FORMAT(users.birth_date, %s), users.role, user_statuses.status, DATE_FORMAT(users.registration_date, %s), "
											+ "(SELECT MAX(users.id) FROM internet_provider.users) as maxUserId "
											+ "FROM internet_provider.users "
											+ "LEFT JOIN internet_provider.user_statuses ON internet_provider.users.user_statuses_id = internet_provider.user_statuses.id "
											+ "WHERE users.id %s ? " 
											+ "ORDER BY users.id %s " 
											+ "LIMIT ?) as user_data "
											+ "ORDER BY user_data.id";
	private final static int PARAM_ID_INDEX = 1;
	private final static int PARAM_LIMIT_INDEX = 2;
	private final static int ID_INDEX = 1;
	private final static int LOGIN_INDEX = 2;
	private final static int NAME_INDEX = 3;
	private final static int SURNAME_INDEX = 4;
	private final static int EMAIL_INDEX = 5;
	private final static int BIRTH_DATE_INDEX = 6;
	private final static int ROLE_INDEX = 7;
	private final static int STATUS_INDEX = 8;
	private final static int REG_DATE_INDEX = 9;
	private final static int MAX_ID_INDEX = 10;
	private final static int DEFAULT_LIMIT_VALUE = 7;
	private final static String DIRECTION_NEXT = "next";
	private final static String GREATER_THAN_SYMBOL = ">";
	private final static String LESSER_THAN_SYMBOL = "<";
	private final static String USER_ID = "userId";
	private final static String LAST_USER_ID = "lastUserId";
	private final static String DIRECTION = "direction";
	private final static String ORDER_DESC = "DESC";
	private final static String ORDER_DEFAULT = "";
	private StatementType statementType;
	
	private Map<String,Object>parametersMap; 
	
	public FindUserListSqlSpecification(Map<String,Object>parametersMap) {
		this.parametersMap = parametersMap; 
		this.statementType = StatementType.PREPARED_STATEMENT; 
	}
	
	public StatementType takeStatementType() {
		return this.statementType; 
	}

	@Override
	public String getSqlQuery() {
		String direction = (String) parametersMap.get(DIRECTION); 
		String conditionSymbol;
		String orderDirection;
		String dateFormat = "'%d-%m-%Y'";
		if (direction.equals(DIRECTION_NEXT)) {
			conditionSymbol = GREATER_THAN_SYMBOL;
			orderDirection = ORDER_DEFAULT;
		}else {
			conditionSymbol = LESSER_THAN_SYMBOL;
			orderDirection = ORDER_DESC;
		}
		Object parameters[] = {dateFormat, dateFormat, conditionSymbol, orderDirection};
		String sqlQuerry = String.format(SQL_QUERRY_PATTERN, parameters);
		return sqlQuerry;
	}

	@Override
	public void fillInStatementParameters(PreparedStatement ps) throws SpecificationException {
		try {
			int userId = (int) parametersMap.get(USER_ID);
			ps.setInt(PARAM_ID_INDEX, userId);
			ps.setInt(PARAM_LIMIT_INDEX, DEFAULT_LIMIT_VALUE);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}
	}

	@Override
	public Object retrieveObjectFromResultSet(ResultSet rs) throws SpecificationException {
		User user;

		try {
			String roleName = rs.getString(ROLE_INDEX).toUpperCase();
			String statusName = rs.getString(STATUS_INDEX).toUpperCase();
			UserRole role = UserRole.valueOf(roleName);
			UserStatus status = UserStatus.valueOf(statusName);

			user = new User(rs.getInt(ID_INDEX), rs.getString(LOGIN_INDEX), rs.getString(NAME_INDEX),
					rs.getString(SURNAME_INDEX), rs.getString(EMAIL_INDEX), role, status, rs.getString(REG_DATE_INDEX), rs.getString(BIRTH_DATE_INDEX));

			int lastUserId = rs.getInt(MAX_ID_INDEX);
			parametersMap.put(LAST_USER_ID, lastUserId);
		} catch (SQLException e) {
			throw new SpecificationException(e);
		}

		return user;
	}

}
