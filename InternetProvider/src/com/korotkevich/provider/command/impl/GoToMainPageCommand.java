package com.korotkevich.provider.command.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.command.UserParameter;
import com.korotkevich.provider.command.pagination.PaginationManager;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.entity.ClientAccount;
import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserRole;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.ClientAccountLogic;
import com.korotkevich.provider.logic.ServicePlanLogic;
import com.korotkevich.provider.logic.UserLogic;

public class GoToMainPageCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String ATTR_SERVICE_PLAN = "servicePlan";
	private final static String ATTR_CLIENT_ACCOUNT = "clientAccount";
	private final static String ATTR_USER_LIST_VISIBILITY = "userListVisibility";
	private final static String USER_LIST = "userList";
	private final static String MIN_USER_ID = "minUserId";
	private final static String MAX_USER_ID = "maxUserId";
	private final static String LAST_USER_ID = "lastUserId";
	private final static String PAGE_NUMBER = "pageNumber";
	private UserLogic userLogic;
	private ServicePlanLogic servicePlanLogic;
	private ClientAccountLogic accountLogic;
	
	public GoToMainPageCommand(UserLogic userLogic, ServicePlanLogic servicePlanLogic, ClientAccountLogic accountLogic){
		this.userLogic = userLogic;
		this.servicePlanLogic = servicePlanLogic;  
		this.accountLogic = accountLogic; 
	}

	@Override
	public Router execute(HttpServletRequest request) {
		User currentUser = (User) request.getSession(true).getAttribute(UserParameter.USER.getParameterName());
		Router router;
		
		if(currentUser.getRole() == UserRole.ADMINISTRATOR) {
			router = new Router(JspAddress.MAIN_ADMIN.getPath());
			request.setAttribute(ATTR_USER_LIST_VISIBILITY, true);
			prepareUserList(request);
		}else {
			router = new Router(JspAddress.MAIN_USER.getPath());
			ServicePlan servicePlan = findUserServicePlan(currentUser); 
			ClientAccount account = findUserAccount(currentUser);
			request.setAttribute(ATTR_SERVICE_PLAN, servicePlan);
			request.setAttribute(ATTR_CLIENT_ACCOUNT, account);
		};

		HttpSession session = request.getSession(true);
		@SuppressWarnings("unchecked")
		HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>) session.getAttribute("messageMap");

		for (Map.Entry<String, Boolean> entry : messageMap.entrySet()) {
			String key = entry.getKey();
			Boolean value = entry.getValue();
			request.setAttribute(key, value);		
		}

		messageMap.clear();
	
		return router;
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
	
	private void prepareUserList(HttpServletRequest request) {
		int defaultValue = 0;
		int pageNumber = 1;
		int minUserId = 0;
		int maxUserId = 0;
		List<User> userList = new ArrayList<>();
		try {		
			Map<String, Object> parametersMap = PaginationManager.preparePagingParameters(UserParameter.USER_ID.getParameterName(), LAST_USER_ID, minUserId, maxUserId, PaginationManager.takeDefaultDirection()); 	
			userList = userLogic.findAllUsers(parametersMap);
			if (!userList.isEmpty()) {
				int listSize = userList.size();
				minUserId = userList.get(defaultValue).getId();
				maxUserId = userList.get(listSize - 1).getId();
				defaultValue = (int) parametersMap.get(LAST_USER_ID);
			}

		} catch (LogicException e) {
			logger.log(Level.ERROR, "Error occured while getting service plan list: " + e);
		}
 
		request.setAttribute(USER_LIST, userList);
		request.setAttribute(MAX_USER_ID, maxUserId);
		request.setAttribute(LAST_USER_ID, defaultValue);
		request.setAttribute(MIN_USER_ID, minUserId);
		request.setAttribute(PAGE_NUMBER, pageNumber);	
	}
}
