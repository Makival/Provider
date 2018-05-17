package com.korotkevich.provider.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/jsp/*" }, initParams = { @WebInitParam(name = "DEFAULT_PATH", value = "/index.jsp") })
public class PageRedirectSecurityFilter implements Filter {
	private String defaultPath;

	public void init(FilterConfig filterConfig) throws ServletException {
		defaultPath = filterConfig.getInitParameter("DEFAULT_PATH");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect(httpRequest.getContextPath() + defaultPath);
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
