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
import com.korotkevich.provider.command.ServicePlanParameter;
import com.korotkevich.provider.command.UserParameter;
import com.korotkevich.provider.command.pagination.PaginationManager;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.entity.UserRole;
import com.korotkevich.provider.entity.criteria.Criteria;
import com.korotkevich.provider.entity.criteria.SearchCriteria;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.ServicePlanLogic;

/**
 * Prepares service plan list page
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class GoToServicePlanListPageCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String PAGE_NUMBER = "pageNumber";
	private final static int DEFAULT_VALUE = 0;
	private ServicePlanLogic servicePlanLogic;
	
	public GoToServicePlanListPageCommand(ServicePlanLogic servicePlanLogic){
		 this.servicePlanLogic = servicePlanLogic; 
	}

	@Override
	public Router execute(HttpServletRequest request) {
		User currentUser = (User) request.getSession(true).getAttribute(UserParameter.USER.getParameterName());
		List<ServicePlan> servicePlanList = new ArrayList<>();
		Map<String, Object> parametersMap = new HashMap<>();
		Router router;
		int minSpId = 0;
		int maxSpId = 0;
		int lastSpId = 0;
		int pageNumber = 1;
		
		if(currentUser.getRole() == UserRole.ADMINISTRATOR) {
			router = new Router(JspAddress.SERVICE_PLAN_LIST_ADMIN.getPath());
		}else {
			router = new Router(JspAddress.SERVICE_PLAN_LIST.getPath());
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

		try {
			parametersMap = PaginationManager.preparePagingParameters(
					ServicePlanParameter.SERVICE_PLAN_ID.getParameterName(),
					ServicePlanParameter.LAST_SP_ID.getParameterName(), minSpId, maxSpId,
					PaginationManager.takeDefaultDirection());
			Criteria<SearchCriteria.ServicePlanCriteria> servicePlanCriteria = new Criteria<>();
			servicePlanList = servicePlanLogic.findServicePlansByCriteria(parametersMap, servicePlanCriteria);
			if (!servicePlanList.isEmpty()) {
				int listSize = servicePlanList.size();
				minSpId = servicePlanList.get(DEFAULT_VALUE).getId();
				maxSpId = servicePlanList.get(listSize - 1).getId();
				lastSpId = (int) parametersMap.get(ServicePlanParameter.LAST_SP_ID.getParameterName());
			}

		} catch (LogicException e) {
			logger.log(Level.ERROR, "Error occured while getting service plan list: " + e);
		}
 
		request.setAttribute(ServicePlanParameter.SERVICE_PLAN_LIST.getParameterName(), servicePlanList);
		request.setAttribute(ServicePlanParameter.MAX_SP_ID.getParameterName(), maxSpId);
		request.setAttribute(ServicePlanParameter.LAST_SP_ID.getParameterName(), lastSpId);
		request.setAttribute(ServicePlanParameter.MIN_SP_ID.getParameterName(), minSpId);
		request.setAttribute(PAGE_NUMBER, pageNumber);
		
		return router;
	}
	
}
