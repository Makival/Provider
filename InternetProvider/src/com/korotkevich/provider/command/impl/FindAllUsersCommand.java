package com.korotkevich.provider.command.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.command.UserParameter;
import com.korotkevich.provider.command.pagination.PaginationManager;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserRole;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.UserLogic;

public class FindAllUsersCommand  implements Command  {
	private static Logger logger = LogManager.getLogger();
	private final static String USER_LIST = "userList";
	private final static String USER_LIST_VISIBILITY = "userListVisibility";
	private final static String MIN_USER_ID = "minUserId";
	private final static String MAX_USER_ID = "maxUserId";
	private final static String LAST_USER_ID = "lastUserId";
	private final static String DIRECTION = "direction";
	private final static String PAGE_NUMBER = "pageNumber";
	private final static int DEFAULT_VALUE = 0;
	private UserLogic userLogic;
	
	public FindAllUsersCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router(JspAddress.MAIN_ADMIN.getPath());
		User currentUser = (User) request.getSession(true).getAttribute(UserParameter.USER.getParameterName());
		String maxUserIdValue = request.getParameter(MAX_USER_ID);
		String minUserIdValue = request.getParameter(MIN_USER_ID);
		String directionValue = request.getParameter(DIRECTION); 
		String pageNumberValue = request.getParameter(PAGE_NUMBER);
		String pagingDirection = PaginationManager.takePageDirection(directionValue);
		List<User> userList = new ArrayList<>();
		Map<String, Object> parametersMap = new HashMap<>();
		int minUserId = 0;
		int maxUserId = 0;
		int lastUserId = 0;
		int pageNumber = 1;
		
		if (!directionValue.isEmpty()) {
			maxUserId = PaginationManager.parsePageStringParameterToInt(maxUserIdValue, maxUserId);
			minUserId = PaginationManager.parsePageStringParameterToInt(minUserIdValue, minUserId); 
			pageNumber = PaginationManager.parsePageStringParameterToInt(pageNumberValue, pageNumber);
		} 
		
		if (currentUser == null || currentUser.getRole() != UserRole.ADMINISTRATOR) {
			return router;
		}
		
		logger.log(Level.INFO, "getting user list, direction " + directionValue);

		try {
			parametersMap = PaginationManager.preparePagingParameters(UserParameter.USER_ID.getParameterName(), LAST_USER_ID, minUserId, maxUserId, pagingDirection); 
			
			userList = userLogic.findAllUsers(parametersMap);
			if (!userList.isEmpty()) {
				int listSize = userList.size();
				minUserId = userList.get(DEFAULT_VALUE).getId();
				maxUserId = userList.get(listSize - 1).getId();
				lastUserId = (int) parametersMap.get(LAST_USER_ID);
			}
			if (!directionValue.isEmpty()) {
				pageNumber = PaginationManager.calculatePageNumber(pageNumber, pagingDirection);
			}

		} catch (LogicException e) {
			logger.log(Level.ERROR, "Error occured while getting user list: " + e);
		}

		boolean isUserListVisible = !userList.isEmpty() || !directionValue.isEmpty();  
		
		request.setAttribute(USER_LIST, userList);
		request.setAttribute(MAX_USER_ID, maxUserId);
		request.setAttribute(LAST_USER_ID, lastUserId);
		request.setAttribute(MIN_USER_ID, minUserId);
		request.setAttribute(USER_LIST_VISIBILITY, isUserListVisible);
		request.setAttribute(PAGE_NUMBER, pageNumber);
		
		return router;
	}

}
