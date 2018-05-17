package com.korotkevich.provider.entity;

import java.math.BigDecimal;

/**
 * 
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public enum CurrencyParameter {
	BYN(2, BigDecimal.ROUND_HALF_UP);

	private int scale;
	private int rounding;

	/**
	 * Basic constructor of the CurrencyParameter 
	 * @param scale contains scale for currency calculations
	 * @param rounding contains rounding for currency calculations
	 */
	CurrencyParameter(int scale, int rounding) {
		this.scale = scale;
		this.rounding = rounding;
	}

	/**
	 * Get the scale of the CurrencyParameter
	 * @return scale
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * Get the rounding of the CurrencyParameter 
	 * @return rounding
	 */
	public int getRounding() {
		return rounding;
	}

}
