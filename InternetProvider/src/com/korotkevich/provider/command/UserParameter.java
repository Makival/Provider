package com.korotkevich.provider.command;

/**
 * Contains parameter names for user objects interactions
 * @author Korotkevich Kirill 2018-05-11
 *
 */
public enum UserParameter {
	USER_ID("userId"),
	LOGIN("login"), 
	PASSWORD("password"), 
	NAME("name"),
	SURNAME("surname"),
	EMAIL("email"),
	BIRTHDATE("birthDate"),
	ROLE("role"),
	USER("currentUser");

	private String parameterName;

	/**
	 * Constructor with parameterName
	 * @param parameterName name of the parameter (String)
	 */
	UserParameter(String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * Get the name of a parameter
	 * @return parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}
	
}
