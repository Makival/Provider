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
import com.korotkevich.provider.validator.IncomingSimpleDataValidator;

/**
 * Removes (changes status) service plan
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class ServicePlanRemoveCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String PARAM_ID = "spId";
	private final static String SP_NOT_FOUND_MSG = "servicePlanNotFound";
	private final static String SP_REMOVE_MSG = "servicePlanRemoveSuccess";
	private final static String SP_REMOVE_ERROR_MSG = "servicePlanRemoveError";
	private final static String PAGE_NUMBER = "pageNumber";
	
	private ServicePlanLogic servicePlanLogic;

	public ServicePlanRemoveCommand(ServicePlanLogic servicePlanLogic) {
		this.servicePlanLogic = servicePlanLogic;
	}
	
	@Override
	public Router execute(HttpServletRequest request) {
		String idValue = request.getParameter(PARAM_ID);
		ServicePlan servicePlanDB;

		boolean isSpRemoved = false;
		Router router = new Router(JspAddress.SERVICE_PLAN_LIST_ADMIN.getPath());
		
		boolean isIdValidated = IncomingSimpleDataValidator.validatePositiveInt(idValue); 

		if (!isIdValidated) {
			request.setAttribute(SP_REMOVE_ERROR_MSG, true);
			return router;
		}
		
		int spId = Integer.parseInt(idValue);
		
		ServicePlan incomingServicePlan = new ServicePlan(spId);
		incomingServicePlan.setRelevant(false);
		
		try {
			servicePlanDB = servicePlanLogic.checkServicePlanById(incomingServicePlan);
		} catch (LogicException e) {
			servicePlanDB = null;
			logger.log(Level.ERROR, "Errors occured while searching service plan by id:" + e);
		}
		
		if (servicePlanDB != null) {
			try {
				isSpRemoved = servicePlanLogic.removeServicePlan(incomingServicePlan);
			} catch (LogicException e) {
				logger.log(Level.ERROR, "Errors occured while updating service plan:" + e);
			}

			if (isSpRemoved) {
				prepareServicePlanList(request);
				router.setJspPath(RedirectAddress.SERVICE_PLAN_LIST_REDIRECT.getPath());
				router.setRoute(RouteType.REDIRECT);
				@SuppressWarnings("unchecked")
				HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>) request.getSession(true)
						.getAttribute("messageMap");
				messageMap.put(SP_REMOVE_MSG, true);
			} else {
				router.setJspPath(JspAddress.ERROR.getPath());
				request.setAttribute(SP_REMOVE_ERROR_MSG, true);
			}
		} else {
			router.setJspPath(JspAddress.ERROR.getPath());
			request.setAttribute(SP_NOT_FOUND_MSG, true);
		}

		return router;

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
