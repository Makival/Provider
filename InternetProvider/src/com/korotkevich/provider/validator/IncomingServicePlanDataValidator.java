package com.korotkevich.provider.validator;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.korotkevich.provider.command.ServicePlanParameter;

/**
 * Validates data of the Service plan class object values
 * @author Korotkevich Kirill 2018-05-25
 *
 */
public class IncomingServicePlanDataValidator {
	private final static String REG_EX_ID = "[\\d]{1,10}";
	private final static String REG_EX_NAME = "([A-Z]{1})([A-Za-z0-9-_\\s]{5,50})";
	private final static String REG_EX_TRAFFIC_LIMIT = "([1-9]{1})([0-9]{0,3})?$";
	private final static String REG_EX_FEE = "([1-9]{1}[0-9]{0,3})(\\.[0-9]{1,2})?$";
	private final static String REG_EX_ACCESS_COST = "([1-9]{1}[0-9]{0,3})(\\.[0-9]{1,2})?$";
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

		isIdCorrect = validateValue(servicePlanMap.get(ServicePlanParameter.ID.getParameterName()), REG_EX_ID);
		if(!isIdCorrect) {
			errorList.add("InvalidId");	
		}

		isNameCorrect = validateValue(servicePlanMap.get(ServicePlanParameter.NAME.getParameterName()), REG_EX_NAME);
		if(!isNameCorrect) {
			errorList.add("InvalidName");	
		}

		isLimitCorrect = validateValue(servicePlanMap.get(ServicePlanParameter.TRAFFIC_LIMIT.getParameterName()), REG_EX_TRAFFIC_LIMIT);
		if(!isLimitCorrect) {
			errorList.add("InvalidLimit");	
		}

		isFeeCorrect = validateValue(servicePlanMap.get(ServicePlanParameter.MONTHLY_FEE.getParameterName()), REG_EX_FEE);
		if(!isFeeCorrect) {
			errorList.add("InvalidFee");	
		}

		isAccessCostCorrect = validateValue(servicePlanMap.get(ServicePlanParameter.ACCESS_COST.getParameterName()), REG_EX_ACCESS_COST);
		if(!isAccessCostCorrect) {
			errorList.add("InvalidAccessCost");	
		}

		isDescriptionCorrect = validateValue(servicePlanMap.get(ServicePlanParameter.DESCRIPTION.getParameterName()), REG_EX_DESCRIPTION);
		if(!isDescriptionCorrect) {
			errorList.add("InvalidDescription");	
		}

		isServicePlanValidated = isIdCorrect && isNameCorrect && isLimitCorrect && isFeeCorrect && isAccessCostCorrect
				&& isDescriptionCorrect;

		return isServicePlanValidated;
	}
	

}
