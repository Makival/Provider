package com.korotkevich.provider.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.controller.Router;

/**
 * Changes localization according to incoming localization parameter
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class ChangeLocalizationCommand implements Command {
	private final static String PARAM_LOCAL = "local";

	@Override
	public Router execute(HttpServletRequest request) {
		String localValue = request.getParameter(PARAM_LOCAL);
		request.getSession(true).setAttribute(PARAM_LOCAL, localValue);

		Router router = new Router(JspAddress.LOGIN.getPath());
		
		return router;
	}

}
