package com.korotkevich.provider.validator;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncomingPromoDataValidator {
	private final static String PARAM_ID = "promoId";
	private final static String PARAM_NAME = "promoName";
	private final static String PARAM__BONUS = "trafficBonus";
	private final static String PARAM_DISCOUNT = "accessDiscount";
	private final static String REG_EX_ID = "[\\d]{1,10}";
	private final static String REG_EX_NAME ="([A-Za-z]{1})([A-Za-z0-9-_\\s]{5,50})";
	private final static String REG_EX_BONUS = "([1-9]{1}[0-9]{0,1})";
	private final static String REG_EX_DISCOUNT = "([1-9]{1}[0-9]{0,1})";
	private final static String DEFAULT_VALUE = "";

	private static boolean validateValue(String value, String regEx){
		boolean isValidated = false;
		
		String valueForValidation = value == null ? DEFAULT_VALUE : value;  
		
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(valueForValidation);
		isValidated = matcher.matches();
		
		return isValidated; 
	}
	
	public static boolean validatePromoData(Map<String, String> promoParametersMap, List<String> errorList) {
		boolean isIdCorrect;
		boolean isNameCorrect;
		boolean isBonusCorrect;
		boolean isDiscountCorrect;
		boolean isPromoValidated;

		isIdCorrect = validateValue(promoParametersMap.get(PARAM_ID), REG_EX_ID);
		if (!isIdCorrect) {
			errorList.add("InvalidPromoId");
		}

		isNameCorrect = validateValue(promoParametersMap.get(PARAM_NAME), REG_EX_NAME);
		if (!isNameCorrect) {
			errorList.add("InvalidPromoName");
		}

		isBonusCorrect = validateValue(promoParametersMap.get(PARAM__BONUS), REG_EX_BONUS);
		if (!isBonusCorrect) {
			errorList.add("InvalidPromoTrafficBonus");
		}

		isDiscountCorrect = validateValue(promoParametersMap.get(PARAM_DISCOUNT), REG_EX_DISCOUNT);
		if (!isDiscountCorrect) {
			errorList.add("InvalidPromoAccessDiscount");
		}

		isPromoValidated = isIdCorrect && isNameCorrect && isBonusCorrect && isDiscountCorrect;

		return isPromoValidated;
	}
	

}
