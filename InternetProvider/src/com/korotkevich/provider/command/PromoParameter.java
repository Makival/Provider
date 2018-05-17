package com.korotkevich.provider.command;

/**
 * Contains parameter names for promo objects interactions
 * @author Korotkevich Kirill 2018-05-16
 *
 */
public enum PromoParameter {
	PROMO_ID("promoId"),
	PROMO_SP_ID("promoSpId"), 
	NAME("promoName"), 
	TRAFFIC_BONUS("trafficBonus"), 
	ACCESS_DISCOUNT("accessDiscount");

	private String parameterName;

	/**
	 * Constructor with parameterName
	 * @param parameterName name of the parameter (String)
	 */
	PromoParameter(String parameterName) {
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
