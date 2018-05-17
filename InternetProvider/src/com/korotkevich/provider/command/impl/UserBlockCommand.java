package com.korotkevich.provider.command.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.command.RedirectAddress;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.controller.Router.RouteType;
import com.korotkevich.provider.controller.session.SessionPool;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserStatus;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.UserLogic;
import com.korotkevich.provider.validator.IncomingSimpleDataValidator;

public class UserBlockCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String PARAM_ID = "chosenUserId";
	private final static String USER_BLOCK_SUCCESS_MSG = "userBlockSuccess";
	private final static String USER_BLOCK_ERROR_MSG = "userBlockError";
	private final static String USER_NOT_FOUND_ERROR_MSG = "userNotFound";
	private final static String USER_ALREADY_BLOCKED_MSG = "userAlreadyBlocked"; 
	private UserLogic userLogic;
	private SessionPool sessionPool;
	
	public UserBlockCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
		this.sessionPool  = SessionPool.getInstance();
	}
	
	@Override
	public Router execute(HttpServletRequest request) {
		User userDB;
		boolean isUserBlocked;
		String idValue = request.getParameter(PARAM_ID);
		Router router = new Router(RedirectAddress.MAIN_REDIRECT.getPath());
		router.setRoute(RouteType.REDIRECT);
		
		boolean isIdValidated = IncomingSimpleDataValidator.validatePositiveInt(idValue); 

		if (!isIdValidated) {
			router.setJspPath(JspAddress.ERROR.getPath());
			router.setRoute(RouteType.FORWARD);
			request.setAttribute(USER_BLOCK_ERROR_MSG, true);
			return router;
		}
		
		int userId = Integer.parseInt(idValue);
		User incomingUser = new User(userId);
		incomingUser.setStatus(UserStatus.BLOCKED);
		
		try {
			userDB = userLogic.checkUserById(incomingUser);
		} catch (LogicException e) {
			logger.log(Level.ERROR, "Errors occured while searchinf user by id:" + e);
			userDB = null;
		}

		if (userDB != null) {
			
			if (userDB.getStatus() == UserStatus.BLOCKED) {
				router.setJspPath(JspAddress.ERROR.getPath());
				router.setRoute(RouteType.FORWARD);
				request.setAttribute(USER_ALREADY_BLOCKED_MSG, true);
				logger.log(Level.INFO, "User " + userDB.getLogin() + " is already blocked!");
				return router;
			}
			
			try {
				isUserBlocked = userLogic.removeUser(incomingUser);
			} catch (LogicException e) {
				isUserBlocked = false;
				logger.log(Level.ERROR, "Errors occured while blocking user:" + e);
			}
			
			if (isUserBlocked) {
				HttpSession session = request.getSession(true); 
				@SuppressWarnings("unchecked")
				HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>)session.getAttribute("messageMap");
				messageMap.put(USER_BLOCK_SUCCESS_MSG, true);
				sessionPool.invalidateUserSession(incomingUser);
			} else {
				router.setJspPath(JspAddress.ERROR.getPath());
				router.setRoute(RouteType.FORWARD);
				request.setAttribute(USER_BLOCK_ERROR_MSG, true);
			}

		}else {
			router.setJspPath(JspAddress.ERROR.getPath());
			router.setRoute(RouteType.FORWARD);
			request.setAttribute(USER_NOT_FOUND_ERROR_MSG, true);
		}
		return router;

	}

	public UserLogic getUserLogic() {
		return userLogic;
	}

}
