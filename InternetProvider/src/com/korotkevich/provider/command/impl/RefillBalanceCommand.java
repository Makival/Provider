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
import com.korotkevich.provider.entity.User;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.ClientAccountLogic;
import com.korotkevich.provider.validator.IncomingSimpleDataValidator;

/**
 * Changes clients balance data
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class RefillBalanceCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String PARAM_SUMM = "refillSumm";
	private final static String ATTR_SUMM_INVALIDATED = "summInvalidated";
	private final static String REFILL_BALANCE_MSG = "refillBalanceSuccess";
	private final static String REFILL_BALANCE_ERROR_MSG = "refillBalanceError";
	private ClientAccountLogic accountLogic;
	
	public RefillBalanceCommand(ClientAccountLogic accountLogic) {
		this.accountLogic = accountLogic;
	}

	@Override
	public Router execute(HttpServletRequest request) {
		ClientAccount account;
		String summValue = request.getParameter(PARAM_SUMM);
		Router router = new Router(RedirectAddress.MAIN_REDIRECT.getPath());
		
		boolean isSummValidated = IncomingSimpleDataValidator.validatePositiveDouble(summValue);
		
		if (!isSummValidated) {
			request.setAttribute(ATTR_SUMM_INVALIDATED, !isSummValidated);
			router.setJspPath(JspAddress.MAIN_USER.getPath());
			return router;
		}
		
		User currentUser = (User)request.getSession(true).getAttribute(UserParameter.USER.getParameterName());
		
		try {
			account = accountLogic.findAccountByUserId(currentUser);
		} catch (LogicException e) {
			logger.log(Level.ERROR, "Errors occured while getting client account :" + e);
			router.setJspPath(JspAddress.ERROR.getPath());
			request.setAttribute(REFILL_BALANCE_ERROR_MSG, true);
			return router;
		}
			
		boolean isBalanceRefilled;
		BigDecimal refillSumm = new BigDecimal(Double.parseDouble(summValue));	
		BigDecimal currentBalance = new BigDecimal(account.getCashBalance());
		BigDecimal newBalance =   currentBalance.add(refillSumm);
		account.setCashBalance(newBalance);
		
		try {
			isBalanceRefilled = accountLogic.refillBalance(account);
		} catch (LogicException e) {
			isBalanceRefilled = false;
			logger.log(Level.ERROR, "Errors occured while refilling cash balance :" + e);
		}
		
		if (isBalanceRefilled) {
			HttpSession session = request.getSession(true); 
			@SuppressWarnings("unchecked")
			HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>)session.getAttribute("messageMap");
			messageMap.put(REFILL_BALANCE_MSG, true);
			router.setRoute(RouteType.REDIRECT);	
		}else {
			router.setJspPath(JspAddress.ERROR.getPath());
			request.setAttribute(REFILL_BALANCE_ERROR_MSG, true);
		}
		
		return router;
	}

}
