package com.korotkevich.provider.command.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.command.RedirectAddress;
import com.korotkevich.provider.command.UserParameter;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.controller.Router.RouteType;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserRole;
import com.korotkevich.provider.entity.UserStatus;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.UserLogic;
import com.korotkevich.provider.validator.IncomingUserDataValidator;

/**
 * Adds new user
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class UserRegistrationCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
	private final static String ATTR_ERROR_LIST_NAME = "validationErrorList";	
	private final static String ATTR_USER_FOUND_MSG = "userFound";
	private final static String ATTR_ROLE_LIST_NAME = "roleList";	
	private final static String USER_CREATE_MSG = "userCreationSuccess";
	private final static String USER_CREATE_ERROR_MSG = "userCreationError";
	
	private UserLogic userLogic;

	public UserRegistrationCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}
	
	@Override
	public Router execute(HttpServletRequest request) {
		int defaultId = 0;
		UserStatus defaultStatus = UserStatus.NEW;
		String loginValue = request.getParameter(UserParameter.LOGIN.getParameterName());
		String passwordValue = request.getParameter(UserParameter.PASSWORD.getParameterName());
		String nameValue = request.getParameter(UserParameter.NAME.getParameterName());
		String surnameValue = request.getParameter(UserParameter.SURNAME.getParameterName());
		String emailValue = request.getParameter(UserParameter.EMAIL.getParameterName());
		String roleValue = request.getParameter(UserParameter.ROLE.getParameterName());
		String birthDateValue = request.getParameter(UserParameter.BIRTHDATE.getParameterName());
		User userDB;
		boolean isUserAdded = false;
		Router router = new Router(JspAddress.REGISTRATION.getPath());
		List<String> validationErrorList = new ArrayList<>();
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	    String regDateValue = dateFormat.format(new Date()); 
		
	    boolean isRoleValidated = IncomingUserDataValidator.validateRole(roleValue, validationErrorList); 
	    
		if (!isRoleValidated) {
			request.setAttribute(ATTR_ERROR_LIST_NAME, validationErrorList);
			return router;
		}
	    
		UserRole role = UserRole.valueOf(roleValue.toUpperCase());
		
		User incomingUser = new User(defaultId, loginValue, passwordValue.toCharArray(), nameValue, surnameValue, emailValue,
				role, defaultStatus, regDateValue, birthDateValue);	
	
		boolean isUserValidated = IncomingUserDataValidator.validateUserData(incomingUser, validationErrorList);
		
		if (!isUserValidated) {
			request.setAttribute(ATTR_ERROR_LIST_NAME, validationErrorList);
			return router;
		}

		try {
			userDB = userLogic.checkUserByLogin(incomingUser);
		} catch (LogicException e) {
			userDB = null;
			logger.log(Level.ERROR, "Errors occured while searching user by login:" + e);
		}

		if (userDB == null) {
			try {
				isUserAdded = userLogic.addUser(incomingUser);
			} catch (LogicException e) {
				logger.log(Level.ERROR, "Errors occured while creating user:" + e);
			}
			
			if (isUserAdded) {
				router.setJspPath(RedirectAddress.MAIN_REDIRECT.getPath());	
				router.setRoute(RouteType.REDIRECT);
				@SuppressWarnings("unchecked")
				HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>)request.getSession(true).getAttribute("messageMap");
				messageMap.put(USER_CREATE_MSG, true);
			} else {
				router.setJspPath(JspAddress.ERROR.getPath());
				request.setAttribute(USER_CREATE_ERROR_MSG, true);
			}

		}else {
			List<String> roleList = fillInRoleList();
			request.setAttribute(ATTR_USER_FOUND_MSG, true);		
			request.setAttribute(ATTR_ROLE_LIST_NAME, roleList);	
		}

		return router;

	}

	public UserLogic getUserLogic() {
		return userLogic;
	}
	
	private List<String> fillInRoleList() {
		List<String> roleList = new ArrayList<>();

		for (UserRole role : UserRole.values()) {
			roleList.add(role.toString().toLowerCase());
		}

		return roleList;
	}

}
