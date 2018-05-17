package com.korotkevich.provider.command.impl;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.controller.Router;

public class GoToErrorPageCommand implements Command {
	private static final String ATTR_ACCESS_VIOLATION = "accessViolation";
	private static final String ATTR_COMMAND_ERROR = "commandError";

	@Override
	public Router execute(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		boolean isAccessViolated = false;
		boolean isCommandIncorrect = false;
		Router router = new Router(JspAddress.ERROR.getPath());

		Enumeration<String> attributeNames = session.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement().toString();
			if (attributeName.equals(ATTR_ACCESS_VIOLATION)) {
				isAccessViolated = true;
			}
			if (attributeName.equals(ATTR_COMMAND_ERROR)) {
				isCommandIncorrect = true;
			}
		}

		if (isAccessViolated) {
			request.setAttribute(ATTR_ACCESS_VIOLATION, true);
			session.removeAttribute(ATTR_ACCESS_VIOLATION);
		}
		
		if (isCommandIncorrect) {
			request.setAttribute(ATTR_COMMAND_ERROR, true);
			session.removeAttribute(ATTR_COMMAND_ERROR);
		}

		return router;
	}
}
