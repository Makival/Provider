package com.korotkevich.provider.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.controller.Router;

public class GoToUserUpdatePageCommand implements Command {

	@Override
	public Router execute(HttpServletRequest request) {

		Router router = new Router(JspAddress.USER_UPDATE.getPath());
		return router;
	}
}
