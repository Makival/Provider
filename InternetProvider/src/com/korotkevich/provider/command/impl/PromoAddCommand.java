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
import com.korotkevich.provider.command.PromoParameter;
import com.korotkevich.provider.command.RedirectAddress;
import com.korotkevich.provider.command.ServicePlanParameter;
import com.korotkevich.provider.command.pagination.PaginationManager;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.controller.Router.RouteType;
import com.korotkevich.provider.entity.Promo;
import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.criteria.Criteria;
import com.korotkevich.provider.entity.criteria.SearchCriteria;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.PromoLogic;
import com.korotkevich.provider.logic.ServicePlanLogic;
import com.korotkevich.provider.validator.IncomingPromoDataValidator;
import com.korotkevich.provider.validator.IncomingSimpleDataValidator;

public class PromoAddCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String ATTR_ERROR_LIST_NAME = "validationErrorList";
	private final static String PROMO_CREATE_MSG = "promoCreationSuccess";
	private final static String PROMO_CREATE_ERROR_MSG = "promoCreationError";
	private final static String SP_NOT_FOUND_MSG = "servicePlanNotFound";
	private final static String SP_UNDER_PROMO = "servicePlanIsUnderPromo";
	private final static String PAGE_NUMBER = "pageNumber";
	private PromoLogic promoLogic;
	private ServicePlanLogic servicePlanLogic;

	public PromoAddCommand(PromoLogic promoLogic, ServicePlanLogic servicePlanLogic) {
		this.promoLogic = promoLogic;
		this.servicePlanLogic = servicePlanLogic;
	}
	
	@Override
	public Router execute(HttpServletRequest request) {
		String defaultId = "0";
		String nameValue = request.getParameter(PromoParameter.NAME.getParameterName());
		String trafficBonusValue = request.getParameter(PromoParameter.TRAFFIC_BONUS.getParameterName());
		String accessDiscountValue = request.getParameter(PromoParameter.ACCESS_DISCOUNT.getParameterName());
		String servicePlanIdValue = request.getParameter(PromoParameter.PROMO_SP_ID.getParameterName()); 
		ServicePlan servicePlanDB;
		Promo promoDB;
		boolean isSpUnderPromo = false;
		boolean isPromoAdded = false;
		Router router = new Router(JspAddress.SERVICE_PLAN_LIST_ADMIN.getPath());
		List<String> validationErrorList = new ArrayList<>();
		Map<String,String> promoParametersMap = preparePromoValuesMap(defaultId, nameValue, trafficBonusValue, accessDiscountValue);
		
		boolean isPromoValidated = IncomingPromoDataValidator.validatePromoData(promoParametersMap, validationErrorList);
		boolean isSpIdValidated = IncomingSimpleDataValidator.validatePositiveInt(servicePlanIdValue);
	
		
		if (!isPromoValidated || !isSpIdValidated) {
			prepareServicePlanList(request);
			request.setAttribute(ATTR_ERROR_LIST_NAME, validationErrorList);
			if (!isSpIdValidated) {
				validationErrorList.add(SP_NOT_FOUND_MSG);
			}
			return router;
		}
		
		int trafficBonus = Integer.parseInt(trafficBonusValue);
		int accessDiscount = Integer.parseInt(accessDiscountValue);
		int servicePlanId = Integer.parseInt(servicePlanIdValue); 
		
		ServicePlan incomingServicePlan = new ServicePlan(servicePlanId);
		
		try {
			servicePlanDB = servicePlanLogic.checkServicePlanById(incomingServicePlan);
		} catch (LogicException e) {
			servicePlanDB = null;
			logger.log(Level.ERROR, "Errors occured while searching for service plan:" + e);
			return router;
		}
		
		try {
			promoDB = promoLogic.checkPromoByServicePlanId(servicePlanDB);
			isSpUnderPromo = (promoDB != null);
		} catch (LogicException e) {
			promoDB = null;
			logger.log(Level.ERROR, "Errors occured while searching for promo by service plan id:" + e);
		}
		
		if (isSpUnderPromo) {
			request.setAttribute(SP_UNDER_PROMO, true);
			return router;
		}
		
		if (servicePlanDB != null) {
			Promo incomingPromo = new Promo(nameValue, accessDiscount, trafficBonus, servicePlanId);
			
			try {
				isPromoAdded = promoLogic.addPromo(incomingPromo);
			} catch (LogicException e) {
				logger.log(Level.ERROR, "Errors occured while creating promo action:" + e);
			}
			
			if (isPromoAdded) {
				router.setJspPath(RedirectAddress.SERVICE_PLAN_LIST_REDIRECT.getPath());	
				router.setRoute(RouteType.REDIRECT);
				@SuppressWarnings("unchecked")
				HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>)request.getSession(true).getAttribute("messageMap");
				messageMap.put(PROMO_CREATE_MSG, true);
			} else {
				router.setJspPath(JspAddress.ERROR.getPath());
				request.setAttribute(PROMO_CREATE_ERROR_MSG, true);
			}	
		} else {
			router.setJspPath(JspAddress.ERROR.getPath());
			request.setAttribute(SP_NOT_FOUND_MSG, true);
		}

		return router;

	}
	
	private Map<String, String> preparePromoValuesMap(String defaultId, String nameValue, String trafficBonus,
			String accessDiscount) {

		Map<String, String> servicePlanMap = new HashMap<String, String>();
		servicePlanMap.put(PromoParameter.PROMO_ID.getParameterName(), defaultId);
		servicePlanMap.put(PromoParameter.NAME.getParameterName(), nameValue);
		servicePlanMap.put(PromoParameter.TRAFFIC_BONUS.getParameterName(), trafficBonus);
		servicePlanMap.put(PromoParameter.ACCESS_DISCOUNT.getParameterName(), accessDiscount);

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
