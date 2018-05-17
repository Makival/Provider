package com.korotkevich.provider.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncomingSimpleDataValidator {
	private final static String REG_EX_POSITIVE_INT = "([1-9]{1})([\\d]{0,9})";
	private final static String REG_EX_POSITIVE_DOUBLE = "([1-9]{1}[0-9]{0,7})(\\.[0-9]{2})?$";
	private final static String REG_EX_DOUBLE = "([0-9]{0,8})(\\.[0-9]{1,2})?$";
	private final static String REG_EX_STRING ="([A-Za-z0-9-_\\s]{0,50})";
	private final static String DEFAULT_VALUE = "";
	
	public static boolean validatePositiveInt(String intValue){		
		return validateValue(intValue, REG_EX_POSITIVE_INT);
	}
	
	public static boolean validatePositiveDouble(String doubleValue){		
		return validateValue(doubleValue, REG_EX_POSITIVE_DOUBLE);
	}
	
	public static boolean validateString(String stringValue){		
		return validateValue(stringValue, REG_EX_STRING);
	}
	
	public static boolean validateDouble(String doubleValue){		
		return validateValue(doubleValue, REG_EX_DOUBLE);
	}
	
	private static boolean validateValue(String value, String regEx){
		boolean isValidated = false;
		
		String valueForValidation = value == null ? DEFAULT_VALUE : value;  
		
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(valueForValidation);
		isValidated = matcher.matches();
		
		return isValidated; 
		
	}

}
