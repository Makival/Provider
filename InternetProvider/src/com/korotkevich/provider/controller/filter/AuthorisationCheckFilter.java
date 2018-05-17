package com.korotkevich.provider.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korotkevich.provider.command.CommandType;
import com.korotkevich.provider.command.RedirectAddress;
import com.korotkevich.provider.entity.User;

@WebFilter(urlPatterns = { "/FrontController" })
public class AuthorisationCheckFilter implements Filter {
	private final static String ATTR_USER = "currentUser";
	private static final String ATTR_ACCESS_VIOLATION = "accessViolation";
	private static final String ATTR_COMMAND_ERROR = "commandError";
	private static final String PARAM_COMMAND = "command";
	private static final String SERVLET_NAME = "/FrontController";
	private static final int NO_COMMAND_RESTRICTION = 99;
	
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		int userAccessLevel = 0;
		int commandRestrictionLevel;
		String RedirectAddressPath;
		
		HttpSession session = httpRequest.getSession(true); 
		User currentUser = (User)session.getAttribute(ATTR_USER);
		
		if (currentUser != null) {
			userAccessLevel = currentUser.getRole().getAccessLevel(); 
		} 
		
		String commandName = request.getParameter(PARAM_COMMAND);
		if (commandName == null || commandName.isEmpty()) {		
			commandRestrictionLevel = NO_COMMAND_RESTRICTION;
			RedirectAddressPath = RedirectAddress.LOGIN_REDIRECT.getPath();
			session.setAttribute(ATTR_COMMAND_ERROR, true);
		}else {
			CommandType commandType = CommandType.valueOf(commandName.toUpperCase());		
			commandRestrictionLevel = commandType.getRestrictionLevel();
			RedirectAddressPath = RedirectAddress.ERROR_PAGE_REDIRECT.getPath();
			session.setAttribute(ATTR_ACCESS_VIOLATION, true);
		} 
		
		if (commandRestrictionLevel > userAccessLevel) {		
			httpResponse.sendRedirect(httpRequest.getContextPath() + SERVLET_NAME + RedirectAddressPath);	
		} else {
			chain.doFilter(request, response);	
		} 	
	}

	public void destroy() {
	}
}