package com.korotkevich.provider.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserRole;

public class IncomingUserDataValidator {
	private final static String REG_EX_LOGIN = "([A-Za-z]{1})([A-Za-z0-9-_]{5,50})";
	private final static String REG_EX_PASSWORD = "[^{}#()]{3,50}";
	private final static String REG_EX_NAME = "([A-Za-z]{2,20})";
	private final static String REG_EX_SURNAME = "([A-Za-z]{2,20})";
	private final static String REG_EX_EMAIL = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";	
	private final static String REG_EX_DATE = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](17|18|19|20)\\d\\d$";
	private final static String DEFAULT_VALUE = "";	
	private final static String LOGIN_ERROR = "InvalidLogin";
	private final static String PASSWORD_ERROR = "InvalidPassword";
	private final static String NAME_ERROR = "InvalidName";
	private final static String SURNAME_ERROR = "InvalidSurname";
	private final static String EMAIL_ERROR = "InvalidEmail";
	private final static String DATE_ERROR = "InvalidDate";
	private final static String ROLE_ERROR = "InvalidRole";
	
	private static boolean validateValue(String value, String regEx){
		boolean isValidated = false;
		
		String valueForValidation = value == null ? DEFAULT_VALUE : value;  
		
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(valueForValidation);
		isValidated = matcher.matches();
		
		return isValidated; 
	}
	
	public static boolean validateLogin(String login){		
		return validateValue(login, REG_EX_LOGIN);
	}
	
	public static boolean validatePassword(String password){		
		return validateValue(password, REG_EX_PASSWORD);
	}
	
	public static boolean validatePassword(String password, List<String> errorList) {
		if (!errorList.contains(PASSWORD_ERROR)) {
			errorList.add(PASSWORD_ERROR);
		}
		return validateValue(password, REG_EX_PASSWORD);
	}
	
	public static boolean validateEmail(String email){		
		return validateValue(email, REG_EX_EMAIL);
	}

	public static boolean validateRole(String incomingRoleName, List<String> errorList) {
		UserRole[] roles = UserRole.values();
		for (UserRole role : roles)
			if (role.getRoleName().equals(incomingRoleName.toLowerCase())) {
				return true;
			}

		errorList.add(ROLE_ERROR);
		return false;
	}
	
	public static boolean validateUserDataMin(User user, List<String> errorList) {
		boolean isLoginCorrect;
		boolean isNameCorrect;
		boolean isSurnameCorrect;
		boolean isEmailCorrect;
		boolean isDateCorrect;
		boolean isUserValidated;

		isLoginCorrect = validateValue(user.getLogin(), REG_EX_LOGIN);
		if (!isLoginCorrect) {
			errorList.add(LOGIN_ERROR);
		}

		isNameCorrect = validateValue(user.getName(), REG_EX_NAME);
		if (!isNameCorrect) {
			errorList.add(NAME_ERROR);
		}

		isSurnameCorrect = validateValue(user.getSurname(), REG_EX_SURNAME);
		if (!isSurnameCorrect) {
			errorList.add(SURNAME_ERROR);
		}

		isEmailCorrect = validateValue(user.getEmail(), REG_EX_EMAIL);
		if (!isEmailCorrect) {
			errorList.add(EMAIL_ERROR);
		}

		isDateCorrect = validateValue(user.getBirthDate(), REG_EX_DATE);
		if (!isDateCorrect) {
			errorList.add(DATE_ERROR);
		}

		isUserValidated = isLoginCorrect && isNameCorrect && isSurnameCorrect && isEmailCorrect && isDateCorrect;

		return isUserValidated;
	}
	
	public static boolean validateUserData(User user, List<String> errorList) {
		boolean isLoginCorrect;
		boolean isPasswordCorrect;
		boolean isNameCorrect;
		boolean isSurnameCorrect;
		boolean isEmailCorrect;
		boolean isDateCorrect;
		boolean isUserValidated;

		isLoginCorrect = validateValue(user.getLogin(), REG_EX_LOGIN);
		if (!isLoginCorrect) {
			errorList.add(LOGIN_ERROR);
		}

		isPasswordCorrect = validateValue(String.valueOf(user.getPassword()), REG_EX_PASSWORD);
		if (!isPasswordCorrect) {
			errorList.add(PASSWORD_ERROR);
		}

		isNameCorrect = validateValue(user.getName(), REG_EX_NAME);
		if (!isNameCorrect) {
			errorList.add(NAME_ERROR);
		}

		isSurnameCorrect = validateValue(user.getSurname(), REG_EX_SURNAME);
		if (!isSurnameCorrect) {
			errorList.add(SURNAME_ERROR);
		}

		isEmailCorrect = validateValue(user.getEmail(), REG_EX_EMAIL);
		if (!isEmailCorrect) {
			errorList.add(EMAIL_ERROR);
		}

		isDateCorrect = validateValue(user.getBirthDate(), REG_EX_DATE);
		if (!isDateCorrect) {
			errorList.add(DATE_ERROR);
		}

		isUserValidated = isLoginCorrect && isPasswordCorrect && isNameCorrect && isSurnameCorrect && isEmailCorrect
				&& isDateCorrect;

		return isUserValidated;
	}

}
