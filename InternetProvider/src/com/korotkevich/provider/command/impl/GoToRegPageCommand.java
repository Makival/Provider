package com.korotkevich.provider.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.korotkevich.provider.command.Command;
import com.korotkevich.provider.command.JspAddress;
import com.korotkevich.provider.controller.Router;
import com.korotkevich.provider.entity.UserRole;

public class GoToRegPageCommand implements Command {

	@Override
	public Router execute(HttpServletRequest request) {

		List<String> roleList = new ArrayList<>();

		for (UserRole role : UserRole.values()) {
			roleList.add(role.toString().toLowerCase());
		}

		request.setAttribute("roleList", roleList);

		Router router = new Router(JspAddress.REGISTRATION.getPath());
		return router;
	}
}
