package com.korotkevich.provider.command.impl;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.command.RedirectAddress;
import com.korotkevich.provider.command.UserParameter;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.controller.Router.RouteType;
import com.korotkevich.provider.entity.ClientAccount;
import com.korotkevich.provider.entity.ServicePlan;
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.ClientAccountLogic;
import com.korotkevich.provider.logic.ServicePlanLogic;
import com.korotkevich.provider.validator.IncomingSimpleDataValidator;

public class ChangeUserServicePlanCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String PARAM_SP_ID = "chosenSpId";
	private final static String ATTR_SP_INVALIDATED = "spInvalidated";
	private final static String ATTR_MSG_MAP = "messageMap";
	private final static String SP_CHOSEN_MSG = "servicePlanChosenSuccess";
	private final static String SP_CHOSEN_ERROR_MSG = "servicePlanChosenError";
	private final static String SP_NOT_FOUND_MSG = "servicePlanNotFound";
	private final static String ACCOUNT_NOT_FOUND_MSG = "clientAccountNotFound";
	private final static String INSUFFISIENT_FUNDS_MSG = "insufficientFunds";
	private final static int DEFAULT_VALUE = 0;
	private final static double BASE_VALUE = 100.0;
	private ServicePlanLogic servicePlanLogic;
	private ClientAccountLogic accountLogic;
	
	public ChangeUserServicePlanCommand(ServicePlanLogic servicePlanLogic, ClientAccountLogic accountLogic) {
		this.servicePlanLogic = servicePlanLogic;
		this.accountLogic = accountLogic;
	}

	@Override
	public Router execute(HttpServletRequest request) {
		String servicePlanIdValue = request.getParameter(PARAM_SP_ID);
		Router router = new Router(RedirectAddress.MAIN_REDIRECT.getPath());
		ServicePlan servicePlanDB;
		ClientAccount account;
		
		boolean isSpIdValidated = IncomingSimpleDataValidator.validatePositiveInt(servicePlanIdValue);
		
		if (!isSpIdValidated) {
			request.setAttribute(ATTR_SP_INVALIDATED, true);
			router.setJspPath(JspAddress.MAIN_USER.getPath());
			return router;
		}
		
		int incomingSpId = Integer.parseInt(servicePlanIdValue);	
		ServicePlan incomingServicePlan = new ServicePlan(incomingSpId);
		User currentUser = (User)request.getSession(true).getAttribute(UserParameter.USER.getParameterName()); 
		
		try {
			servicePlanDB = servicePlanLogic.checkServicePlanById(incomingServicePlan);
			account = accountLogic.findAccountByUserId(currentUser); 
		} catch (LogicException e) {
			logger.log(Level.ERROR, "Errors occured while searching service plan/ client account:" + e);
			servicePlanDB = null;
			account = null;
		}
		
		if (servicePlanDB != null && account != null) {	
			boolean isFundsSufficient = checkFunds(account, servicePlanDB);
			if(!isFundsSufficient) {
				request.setAttribute(INSUFFISIENT_FUNDS_MSG, true);
				router.setJspPath(JspAddress.SERVICE_PLAN_LIST.getPath());
				return router;
			}
			
			updateAccount(account, servicePlanDB);
			
			boolean isServicePlanChosen;
			boolean isUserAccountUpdated;
			
			try {
				isServicePlanChosen = servicePlanLogic.changeUserServicePlan(currentUser, incomingServicePlan);
				isUserAccountUpdated = accountLogic.updateUserAccount(account);
			} catch (LogicException e) {
				logger.log(Level.ERROR, "Errors occured while changing user's service plan:" + e);
				isServicePlanChosen = false;
				isUserAccountUpdated = false;
			}
			
			if (isServicePlanChosen && isUserAccountUpdated) {
				router.setJspPath(RedirectAddress.MAIN_REDIRECT.getPath());
				router.setRoute(RouteType.REDIRECT);
				HttpSession session = request.getSession(true); 
				
				@SuppressWarnings("unchecked")
				HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>)session.getAttribute(ATTR_MSG_MAP);
				messageMap.put(SP_CHOSEN_MSG, true);		
				
			} else {
				router.setJspPath(JspAddress.ERROR.getPath());
				request.setAttribute(SP_CHOSEN_ERROR_MSG, true);
			}

		}else {
			router.setJspPath(JspAddress.MAIN_USER.getPath());
			if (servicePlanDB == null) {
				request.setAttribute(SP_NOT_FOUND_MSG, true);
			}
			if (account == null) {
				request.setAttribute(ACCOUNT_NOT_FOUND_MSG, true);
			}
		}

		return router;
	}
	
	private boolean checkFunds(ClientAccount account, ServicePlan servicePlan) {
		boolean isFundsSufficient = account.getCashBalance() >= servicePlan.getAccessCost() - servicePlan.getPromoDiscount();
		return isFundsSufficient;
	}
	
	private void updateAccount(ClientAccount account, ServicePlan servicePlan) {

		int trafficBalance = (int) ((servicePlan.getTrafficLimit() > DEFAULT_VALUE)
				? (servicePlan.getTrafficLimit() * (1 + servicePlan.getPromoTrafficBonus() / BASE_VALUE))
				: DEFAULT_VALUE);
	
		account.setTrafficBalance(trafficBalance);
		
		double accessCostValue = servicePlan.getPromoDiscount() > 0 ? servicePlan.getAccessCost() * (servicePlan.getPromoDiscount() / BASE_VALUE) : servicePlan.getAccessCost();
		BigDecimal cashBalance = new BigDecimal(account.getCashBalance());
		BigDecimal accessCost = new BigDecimal(accessCostValue);
		account.setCashBalance(cashBalance.subtract(accessCost));
	}

}
