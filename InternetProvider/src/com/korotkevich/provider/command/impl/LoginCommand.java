package com.korotkevich.provider.command.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.command.UserParameter;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.controller.session.SessionPool;
import com.korotkevich.provider.entity.ClientAccount;
import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserRole;
import com.korotkevich.provider.entity.UserStatus;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.ClientAccountLogic;
import com.korotkevich.provider.logic.ServicePlanLogic;
import com.korotkevich.provider.logic.UserLogic;
import com.korotkevich.provider.validator.IncomingUserDataValidator;

/**
 * Executes authentication and authorization procedures
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class LoginCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String ATTR_SERVICE_PLAN = "servicePlan";
	private final static String ATTR_CLIENT_ACCOUNT = "clientAccount";
	private final static String ATTR_MSG_MAP = "messageMap";
	private final static String ATTR_USER_NOT_FOUND = "userNotFound";
	private final static String ATTR_USER_BLOCKED = "userProfileBlocked";
	private final static String ATTR_LOGIN_INVALIDATED = "loginNotValid";
	private final static String ATTR_PASSWORD_INVALIDATED = "passNotValid";
	private final static String ATTR_USER_LOGGED = "userAlreadyLogged";
	private UserLogic userLogic;
	private ServicePlanLogic servicePlanLogic;
	private ClientAccountLogic accountLogic;
	private SessionPool sessionPool;

	public LoginCommand(UserLogic userLogic, ServicePlanLogic servicePlanLogic, ClientAccountLogic accountLogic) {
		this.userLogic = userLogic;
		this.servicePlanLogic = servicePlanLogic;
		this.accountLogic = accountLogic;
		this.sessionPool = SessionPool.getInstance(); 
	}

	@Override
	public Router execute(HttpServletRequest request) {
		String loginValue = request.getParameter(UserParameter.LOGIN.getParameterName());
		String passwordValue = request.getParameter(UserParameter.PASSWORD.getParameterName());
		User currentUser;
		Router router = new Router(JspAddress.LOGIN.getPath());

		boolean isLoginValidated = IncomingUserDataValidator.validateLogin(loginValue);
		boolean isPasswordValidated = IncomingUserDataValidator.validatePassword(passwordValue);

		if (!isLoginValidated || !isPasswordValidated) {
			request.setAttribute(ATTR_LOGIN_INVALIDATED, !isLoginValidated);
			request.setAttribute(ATTR_PASSWORD_INVALIDATED, !isPasswordValidated);
			return router;
		}

		User incomingUser = new User(loginValue, passwordValue.toCharArray());
		try {
			currentUser = userLogic.findUser(incomingUser);
		} catch (LogicException e) {
			currentUser = null;
			logger.log(Level.ERROR, "Error occured while getting user: " + e);
		}

		if (currentUser != null && currentUser.getStatus() != UserStatus.BLOCKED) {
			updateUserStatus(currentUser);
			HttpSession session = request.getSession(true);
			boolean isUserSessionAdded = sessionPool.addUserSession(currentUser, session);
			if (!isUserSessionAdded) {
				request.setAttribute(ATTR_USER_LOGGED, true);
				return router;
			}
			session.setAttribute(ATTR_MSG_MAP, new HashMap<String, Boolean>());
			if (currentUser.getRole() == UserRole.ADMINISTRATOR) {
				router.setJspPath(JspAddress.MAIN_ADMIN.getPath());
			} else {
				router.setJspPath(JspAddress.MAIN_USER.getPath());
				ServicePlan servicePlan = findUserServicePlan(currentUser);
				ClientAccount account = findUserAccount(currentUser);
				request.setAttribute(ATTR_SERVICE_PLAN, servicePlan);
				request.setAttribute(ATTR_CLIENT_ACCOUNT, account);
			}

		} else if (currentUser == null) {
			request.setAttribute(ATTR_USER_NOT_FOUND, true);
		} else if (currentUser.getStatus() == UserStatus.BLOCKED) {
			request.setAttribute(ATTR_USER_BLOCKED, true);
		}

		return router;
	}

	public UserLogic getUserLogic() {
		return userLogic;
	}
	
	private void updateUserStatus(User user) {
		if (user.getStatus() == UserStatus.NEW) {
			try {
				boolean isUserUpdated = userLogic.updateUserStatus(user);
				if (isUserUpdated) {
					user.setStatus(UserStatus.ACTIVE);
				}
			} catch (LogicException e) {
				logger.log(Level.ERROR, "Unable to update user status, user ID = " + user.getId() + " :" + e);
			}
		}
	}
	
	private ServicePlan findUserServicePlan(User user) {
		ServicePlan servicePlan = null;
		try {
			servicePlan = servicePlanLogic.findServicePlanByUserId(user);
		} catch (LogicException e) {
			logger.log(Level.ERROR, "Error occured while getting service plan: " + e);
		}
		return servicePlan;

	}
	
	private ClientAccount findUserAccount(User user) {
		ClientAccount account = null;
		try {
			account = accountLogic.findAccountByUserId(user);
		} catch (LogicException e) {
			logger.log(Level.ERROR, "Error occured while getting client account : " + e);
		}
		return account;

	}

}

