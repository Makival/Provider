package com.korotkevich.provider.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.RedirectAddress;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.controller.Router.RouteType;
import com.korotkevich.provider.controller.session.SessionPool;

/**
 * Prepares session for log out
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class LogoutCommand implements Command  {
	private SessionPool sessionPool;
	
	public LogoutCommand() {
		this.sessionPool = SessionPool.getInstance(); 
	}
	
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router(RedirectAddress.LOGIN_REDIRECT.getPath());
		router.setRoute(RouteType.REDIRECT);
		sessionPool.invalidateUserSession(request.getSession(true));
		
		return router;	
	}

}
