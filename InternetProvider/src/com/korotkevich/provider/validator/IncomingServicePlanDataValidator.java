package com.korotkevich.provider.validator;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncomingServicePlanDataValidator {
	private final static String PARAM_ID = "id";
	private final static String PARAM_NAME = "name";
	private final static String PARAM_LIMIT = "trafficLimit";
	private final static String PARAM_FEE = "monthlyFee";
	private final static String PARAM_ACCESS_COST = "accessCost";
	private final static String PARAM_DESCRIPTION = "description";
	private final static String REG_EX_ID = "[\\d]{1,10}";
	private final static String REG_EX_NAME = "([A-Za-z]{1})([A-Za-z0-9-_\\s]{5,50})";
	private final static String REG_EX_TRAFFIC_LIMIT = "([1-9]{1})([0-9]{0,9})?$";
	private final static String REG_EX_FEE = "([1-9]{1}[0-9]{0,7})(\\.[0-9]{1,2})?$";
	private final static String REG_EX_ACCESS_COST = "([1-9]{1}[0-9]{0,7})(\\.[0-9]{1,2})?$";
	private final static String REG_EX_DESCRIPTION = "[^{}#()]{5,50}";	
	private final static String DEFAULT_VALUE = "";

	private static boolean validateValue(String value, String regEx){
		boolean isValidated = false;
		
		String valueForValidation = value == null ? DEFAULT_VALUE : value;  
		
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(valueForValidation);
		isValidated = matcher.matches();
		
		return isValidated; 
	}
	
	public static boolean validateSpData(Map<String,String> servicePlanMap, List<String> errorList) {
		boolean isIdCorrect;
		boolean isNameCorrect;
		boolean isLimitCorrect;
		boolean isFeeCorrect;
		boolean isAccessCostCorrect;
		boolean isDescriptionCorrect;
		boolean isServicePlanValidated;

		isIdCorrect = validateValue(servicePlanMap.get(PARAM_ID), REG_EX_ID);
		if(!isIdCorrect) {
			errorList.add("InvalidId");	
		}

		isNameCorrect = validateValue(servicePlanMap.get(PARAM_NAME), REG_EX_NAME);
		if(!isNameCorrect) {
			errorList.add("InvalidName");	
		}

		isLimitCorrect = validateValue(servicePlanMap.get(PARAM_LIMIT), REG_EX_TRAFFIC_LIMIT);
		if(!isLimitCorrect) {
			errorList.add("InvalidLimit");	
		}

		isFeeCorrect = validateValue(servicePlanMap.get(PARAM_FEE), REG_EX_FEE);
		if(!isFeeCorrect) {
			errorList.add("InvalidFee");	
		}

		isAccessCostCorrect = validateValue(servicePlanMap.get(PARAM_ACCESS_COST), REG_EX_ACCESS_COST);
		if(!isAccessCostCorrect) {
			errorList.add("InvalidAccessCost");	
		}

		isDescriptionCorrect = validateValue(servicePlanMap.get(PARAM_DESCRIPTION), REG_EX_DESCRIPTION);
		if(!isDescriptionCorrect) {
			errorList.add("InvalidDescription");	
		}

		isServicePlanValidated = isIdCorrect && isNameCorrect && isLimitCorrect && isFeeCorrect && isAccessCostCorrect
				&& isDescriptionCorrect;

		return isServicePlanValidated;
	}
	

}
