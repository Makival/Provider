package com.korotkevich.provider.command.impl;

import java.math.BigDecimal;
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
import com.korotkevich.provider.validator.IncomingSimpleDataValidator;

public class FindServicePlansByCriteriaCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String DIRECTION = "direction";
	private final static String PAGE_NUMBER = "pageNumber";
	private final static int DEFAULT_VALUE = 0;
	private final static double BASE_VALUE = 100.0;
	private final static String STRING_TYPE = "String";
	private final static String DOUBLE_TYPE = "Double";
	private ServicePlanLogic servicePlanLogic;
	
	public FindServicePlansByCriteriaCommand(ServicePlanLogic servicePlanLogic) {
		this.servicePlanLogic = servicePlanLogic;
	}

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		User currentUser = (User) request.getSession(true).getAttribute(UserParameter.USER.getParameterName());
		String maxSpIdValue = request.getParameter(ServicePlanParameter.MAX_SP_ID.getParameterName());
		String minSpIdValue = request.getParameter(ServicePlanParameter.MIN_SP_ID.getParameterName());
		String directionValue = request.getParameter(DIRECTION); 
		String pageNumberValue = request.getParameter(PAGE_NUMBER);
		String pagingDirection = PaginationManager.takePageDirection(directionValue);	
		
		List<ServicePlan> servicePlanList = new ArrayList<>();
		Map<String, Object> parametersMap = new HashMap<>();
		int minSpId = 0;
		int maxSpId = 0;
		int lastSpId = 0;
		int pageNumber = 1;
		if(currentUser.getRole() == UserRole.ADMINISTRATOR) {
			router = new Router(JspAddress.SERVICE_PLAN_LIST_ADMIN.getPath());
		}else {
			router = new Router(JspAddress.SERVICE_PLAN_LIST.getPath());
		};
		
		boolean isDirectionCorrect = directionValue != null && !directionValue.isEmpty();
		
		if (isDirectionCorrect) {
			maxSpId = PaginationManager.parsePageStringParameterToInt(maxSpIdValue, maxSpId);
			minSpId = PaginationManager.parsePageStringParameterToInt(minSpIdValue, minSpId);
			pageNumber = PaginationManager.parsePageStringParameterToInt(pageNumberValue, pageNumber);
		}
		
		Criteria<SearchCriteria.ServicePlanCriteria> servicePlanCriteria = new Criteria<>();
		prepareCriteries(request, servicePlanCriteria);

		try {
			parametersMap = PaginationManager.preparePagingParameters(
					ServicePlanParameter.SERVICE_PLAN_ID.getParameterName(),
					ServicePlanParameter.LAST_SP_ID.getParameterName(), minSpId, maxSpId, pagingDirection);
			servicePlanList = servicePlanLogic.findServicePlansByCriteria(parametersMap, servicePlanCriteria);
			if (!servicePlanList.isEmpty()) {
				int listSize = servicePlanList.size();
				minSpId = servicePlanList.get(DEFAULT_VALUE).getId();
				maxSpId = servicePlanList.get(listSize - 1).getId();
				lastSpId = (int) parametersMap.get(ServicePlanParameter.LAST_SP_ID.getParameterName());
			}
			if (isDirectionCorrect) {
				pageNumber = PaginationManager.calculatePageNumber(pageNumber, pagingDirection);
			}

		} catch (LogicException e) {
			logger.log(Level.ERROR, "Error occured while getting service plan list: " + e);
		}
		
		if (currentUser.getRole() == UserRole.CLIENT) {
			prepareServicePlanListForClients(servicePlanList);
		}
		
		request.setAttribute(ServicePlanParameter.SERVICE_PLAN_LIST.getParameterName(), servicePlanList);
		request.setAttribute(ServicePlanParameter.MAX_SP_ID.getParameterName(), maxSpId);
		request.setAttribute(ServicePlanParameter.LAST_SP_ID.getParameterName(), lastSpId);
		request.setAttribute(ServicePlanParameter.MIN_SP_ID.getParameterName(), minSpId);
		request.setAttribute(PAGE_NUMBER, pageNumber);
		
		request.setAttribute(SearchCriteria.ServicePlanCriteria.NAME.getCriteriaName(), takeCriteriaValue(servicePlanCriteria, SearchCriteria.ServicePlanCriteria.NAME));
		request.setAttribute(SearchCriteria.ServicePlanCriteria.ACCESS_COST_MIN.getCriteriaName(), takeCriteriaValue(servicePlanCriteria, SearchCriteria.ServicePlanCriteria.ACCESS_COST_MIN));
		request.setAttribute(SearchCriteria.ServicePlanCriteria.ACCESS_COST_MAX.getCriteriaName(), takeCriteriaValue(servicePlanCriteria, SearchCriteria.ServicePlanCriteria.ACCESS_COST_MAX));
		request.setAttribute(SearchCriteria.ServicePlanCriteria.MONTHLY_FEE_MIN.getCriteriaName(), takeCriteriaValue(servicePlanCriteria, SearchCriteria.ServicePlanCriteria.MONTHLY_FEE_MIN));
		request.setAttribute(SearchCriteria.ServicePlanCriteria.MONTHLY_FEE_MAX.getCriteriaName(), takeCriteriaValue(servicePlanCriteria, SearchCriteria.ServicePlanCriteria.MONTHLY_FEE_MAX));
		
		return router;
	}
	
	private void prepareCriteries(HttpServletRequest request, Criteria<SearchCriteria.ServicePlanCriteria> servicePlanCriteria) {	
		// add validation error list
		addCriteria(request, servicePlanCriteria, SearchCriteria.ServicePlanCriteria.NAME);
		addCriteria(request, servicePlanCriteria, SearchCriteria.ServicePlanCriteria.ACCESS_COST_MIN);
		addCriteria(request, servicePlanCriteria, SearchCriteria.ServicePlanCriteria.ACCESS_COST_MAX);
		addCriteria(request, servicePlanCriteria, SearchCriteria.ServicePlanCriteria.MONTHLY_FEE_MIN);
		addCriteria(request, servicePlanCriteria, SearchCriteria.ServicePlanCriteria.MONTHLY_FEE_MAX);
	}
	
	private void addCriteria(HttpServletRequest request,
			Criteria<SearchCriteria.ServicePlanCriteria> servicePlanCriteria,
			SearchCriteria.ServicePlanCriteria searchCriteria) {

		String criteriaValue = request.getParameter(searchCriteria.getCriteriaName());

		if (criteriaValue == null || criteriaValue.isEmpty()) {
			return;
		}

		boolean isValueValidated = false;

		if (searchCriteria.getValueTypeName().equals(STRING_TYPE)) {
			isValueValidated = IncomingSimpleDataValidator.validateString(criteriaValue);
		} else if (searchCriteria.getValueTypeName().equals(DOUBLE_TYPE)) {
			isValueValidated = IncomingSimpleDataValidator.validateDouble(criteriaValue);
		}

		if (isValueValidated) {

			if (searchCriteria.getValueTypeName().equals(STRING_TYPE)) {
				String stringValue = criteriaValue;
				servicePlanCriteria.add(searchCriteria, stringValue);
			} else if (searchCriteria.getValueTypeName().equals(DOUBLE_TYPE)) {
				Double doubleValue = Double.parseDouble(criteriaValue);
				servicePlanCriteria.add(searchCriteria, doubleValue);
			}

		}

	}
	
	private String takeCriteriaValue(Criteria<SearchCriteria.ServicePlanCriteria> servicePlanCriteria, SearchCriteria.ServicePlanCriteria searchCriteria) {
		boolean isCriteriaFound = servicePlanCriteria.containsCriteriaKey(searchCriteria);
		String stringValue = "";

		if (isCriteriaFound) {
			if (searchCriteria.getValueTypeName().equals(STRING_TYPE)) {
				stringValue = (String) servicePlanCriteria.get(searchCriteria);
			} else if (searchCriteria.getValueTypeName().equals(DOUBLE_TYPE)) {
				Double doubleValue = (Double) servicePlanCriteria.get(searchCriteria);
				stringValue = doubleValue.toString();
			}
		}

		return stringValue;
	}
	
	private void prepareServicePlanListForClients(List<ServicePlan> servicePlanList) {
		for (ServicePlan plan : servicePlanList) {
			if (plan.getPromoDiscount() > DEFAULT_VALUE) {
				double accessCost = plan.getAccessCost() * (1 - plan.getPromoDiscount() / BASE_VALUE);
				BigDecimal bigAccessCost = new BigDecimal(accessCost);
				plan.setAccessCost(bigAccessCost);
			}
			if (plan.getTrafficLimit() > DEFAULT_VALUE && plan.getPromoTrafficBonus() > DEFAULT_VALUE) {
				int trafficLimit = (int) (plan.getTrafficLimit() * (1 + plan.getPromoTrafficBonus() / BASE_VALUE));
				plan.setTrafficLimit(trafficLimit);
			}
		}
	}

}

