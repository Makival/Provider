package com.korotkevich.provider.command.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.command.PromoParameter;
import com.korotkevich.provider.command.RedirectAddress;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.controller.Router.RouteType;
import com.korotkevich.provider.entity.Promo;
import com.korotkevich.provider.exception.LogicException;
import com.korotkevich.provider.logic.PromoLogic;
import com.korotkevich.provider.validator.IncomingSimpleDataValidator;

public class PromoRemoveCommand implements Command {
	private static Logger logger = LogManager.getLogger();
	private final static String PROMO_REMOVE_SUCCESS_MSG = "promoRemoveSuccess";
	private final static String PROMO_REMOVE_ERROR_MSG = "promoRemoveError";
	private final static String PROMO_NOT_FOUND_ERROR_MSG = "promoNotFound";
	private PromoLogic promoLogic;
	
	public PromoRemoveCommand(PromoLogic promoLogic) {
		this.promoLogic = promoLogic;
	}
	
	@Override
	public Router execute(HttpServletRequest request) {
		Promo promoDB;
		boolean isPromoRemoved;
		String promoIdValue = request.getParameter(PromoParameter.PROMO_ID.getParameterName());
		Router router = new Router(RedirectAddress.SERVICE_PLAN_LIST_REDIRECT.getPath());
		router.setRoute(RouteType.REDIRECT);
		
		boolean isIdValidated = IncomingSimpleDataValidator.validatePositiveInt(promoIdValue); 

		if (!isIdValidated) {
			router.setJspPath(JspAddress.ERROR.getPath());
			router.setRoute(RouteType.FORWARD);
			request.setAttribute(PROMO_REMOVE_ERROR_MSG, true);
			return router;
		}
		
		int promoId = Integer.parseInt(promoIdValue);
		Promo incomingPromo = new Promo(promoId);
		try {
			promoDB = promoLogic.findPromoById(incomingPromo);
		} catch (LogicException e) {
			promoDB = null;
			logger.log(Level.ERROR, "Errors occured while finding promo by id:" + e);
		}

		if (promoDB != null) {
			try {
				isPromoRemoved = promoLogic.removePromo(incomingPromo);
			} catch (LogicException e) {
				isPromoRemoved = false;
				logger.log(Level.ERROR, "Errors occured while deleting promo:" + e);
			}
			
			if (isPromoRemoved) {
				HttpSession session = request.getSession(true); 
				@SuppressWarnings("unchecked")
				HashMap<String, Boolean> messageMap = (HashMap<String, Boolean>)session.getAttribute("messageMap");
				messageMap.put(PROMO_REMOVE_SUCCESS_MSG, true);
			} else {
				router.setJspPath(JspAddress.ERROR.getPath());
				router.setRoute(RouteType.FORWARD);
				request.setAttribute(PROMO_REMOVE_ERROR_MSG, true);
			}

		}else {
			router.setJspPath(JspAddress.ERROR.getPath());
			router.setRoute(RouteType.FORWARD);
			request.setAttribute(PROMO_NOT_FOUND_ERROR_MSG, true);
		}
		return router;

	}

}
