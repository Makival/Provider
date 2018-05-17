package com.korotkevich.provider.command;

/**
 * Contains parameter names for service plan objects interactions
 * @author Korotkevich Kirill 2018-05-11
 *
 */
public enum ServicePlanParameter {
	MIN_SP_ID("minSpId"), 
	MAX_SP_ID("maxSpId"), 
	LAST_SP_ID("lastSpId"),
	SERVICE_PLAN_ID("servicePlanId"),
	ID("spId"),
	NAME("name"),
	TRAFFIC_LIMIT("trafficLimit"),
	MONTHLY_FEE("monthlyFee"),
	ACCESS_COST("accessCost"),
	DESCRIPTION("description"),
	SERVICE_PLAN_LIST("servicePlanList");

	private String parameterName;

	/**
	 * Constructor with parameterName
	 * @param parameterName name of the parameter (String)
	 */
	ServicePlanParameter(String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * Get the name of a parameter
	 * @return parameterName (String)
	 */
	public String getParameterName() {
		return parameterName;
	}
	
}
