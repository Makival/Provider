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
import com.korotkevich.provider.command.RedirectAddress;
import com.korotkevich.provider.command.ServicePlanParameter;
import com.korotkevich.provider.command.pagination.PaginationManager;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.controller.Router.RouteType;
import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.criteria.Criteria;
import com.korotkevich.provider.entity.criteria.SearchCriteria;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.ServicePlanLogic;
import com.korotkevich.provider.validator.IncomingServicePlanDataValidator;

/**
 * Adds new service plan
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class ServicePlanAddCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String ATTR_ERROR_LIST_NAME = "validationErrorList";
	private final static String SP_CREATE_MSG = "spCreationSuccess";
	private final static String SP_CREATE_ERROR_MSG = "spCreationError";
	private final static String PAGE_NUMBER = "pageNumber";
	
	private ServicePlanLogic servicePlanLogic;

	public ServicePlanAddCommand(ServicePlanLogic servicePlanLogic) {
		this.servicePlanLogic = servicePlanLogic;
	}
	
	@Override
	public Router execute(HttpServletRequest request) {
		String defaultId = "0";		
		String nameValue = request.getParameter(ServicePlanParameter.NAME.getParameterName());
		String trafficLimitValue = request.getParameter(ServicePlanParameter.TRAFFIC_LIMIT.getParameterName());
		String monthlyFeeValue = request.getParameter(ServicePlanParameter.MONTHLY_FEE.getParameterName());
		String accessCostValue = request.getParameter(ServicePlanParameter.ACCESS_COST.getParameterName());
		String descriptionValue = request.getParameter(ServicePlanParameter.DESCRIPTION.getParameterName());

		boolean isSpAdded = false;
		Router router = new Router(JspAddress.SERVICE_PLAN_LIST_ADMIN.getPath());
		List<String> validationErrorList = new ArrayList<>();
		Map<String,String> servicePlanMap = prepareServicePlanValuesMap(defaultId, nameValue, trafficLimitValue, monthlyFeeValue, accessCostValue, descriptionValue);
		
		boolean isSpValidated = IncomingServicePlanDataValidator.validateSpData(servicePlanMap, validationErrorList);

		if (!isSpValidated) {
			prepareServicePlanList(request);
			request.setAttribute(ATTR_ERROR_LIST_NAME, validationErrorList);
			return router;
		}
		
		int trafficLimit = Integer.parseInt(trafficLimitValue);
		double monthlyFee = Double.parseDouble(monthlyFeeValue);
		double accessCost = Double.parseDouble(accessCostValue);
		
		ServicePlan incomingServicePlan = new ServicePlan(nameValue, trafficLimit, monthlyFee, descriptionValue, accessCost);

			try {
				isSpAdded = servicePlanLogic.addServicePlan(incomingServicePlan);
			} catch (LogicException e) {
				logger.log(Level.ERROR, "Errors occured while creating service plan:" + e);
			}
			
			if (isSpAdded) {
				router.setJspPath(RedirectAddress.SERVICE_PLAN_LIST_REDIRECT.getPath());	
				router.setRoute(RouteType.REDIRECT);
				@SuppressWarnings("unchecked")
				HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>)request.getSession(true).getAttribute("messageMap");
				messageMap.put(SP_CREATE_MSG, true);
			} else {
				router.setJspPath(JspAddress.ERROR.getPath());
				request.setAttribute(SP_CREATE_ERROR_MSG, true);
			}

		return router;

	}
	
	private Map<String, String> prepareServicePlanValuesMap(String defaultId, String nameValue, String trafficLimit,
			String monthlyFee, String accessCost, String descriptionValue) {

		Map<String, String> servicePlanMap = new HashMap<String, String>();
		servicePlanMap.put(ServicePlanParameter.ID.getParameterName(), defaultId);
		servicePlanMap.put(ServicePlanParameter.NAME.getParameterName(), nameValue);
		servicePlanMap.put(ServicePlanParameter.TRAFFIC_LIMIT.getParameterName(), trafficLimit);
		servicePlanMap.put(ServicePlanParameter.MONTHLY_FEE.getParameterName(), monthlyFee);
		servicePlanMap.put(ServicePlanParameter.ACCESS_COST.getParameterName(), accessCost);
		servicePlanMap.put(ServicePlanParameter.DESCRIPTION.getParameterName(), descriptionValue);
		
		return servicePlanMap;
	}
	
	private void prepareServicePlanList(HttpServletRequest request) {
		int defaultValue = 0;
		int pageNumber = 1;
		int minSpId = 0;
		int maxSpId = 0;
		List<ServicePlan> servicePlanList = new ArrayList<>();
		try {			
			Criteria<SearchCriteria.ServicePlanCriteria> servicePlanCriteria = new Criteria<>();
			Map<String, Object>parametersMap = PaginationManager.preparePagingParameters(ServicePlanParameter.SERVICE_PLAN_ID.getParameterName(), ServicePlanParameter.LAST_SP_ID.getParameterName(), minSpId, maxSpId, PaginationManager.takeDefaultDirection()); 	
			servicePlanList = servicePlanLogic.findServicePlansByCriteria(parametersMap, servicePlanCriteria);
			if (!servicePlanList.isEmpty()) {
				int listSize = servicePlanList.size();
				minSpId = servicePlanList.get(defaultValue).getId();
				maxSpId = servicePlanList.get(listSize - 1).getId();
				defaultValue = (int) parametersMap.get(ServicePlanParameter.LAST_SP_ID.getParameterName());
			}

		} catch (LogicException e) {
			logger.log(Level.ERROR, "Error occured while getting service plan list: " + e);
		}
 
		request.setAttribute(ServicePlanParameter.SERVICE_PLAN_LIST.getParameterName(), servicePlanList);
		request.setAttribute(ServicePlanParameter.MAX_SP_ID.getParameterName(), maxSpId);
		request.setAttribute(ServicePlanParameter.LAST_SP_ID.getParameterName(), defaultValue);
		request.setAttribute(ServicePlanParameter.MIN_SP_ID.getParameterName(), minSpId);
		request.setAttribute(PAGE_NUMBER, pageNumber);	
	}
}
