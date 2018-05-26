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

/**
 * Checks and updates encoding if it is empty
 * @author Korotkevich Kirill 2018-05-22
 *
 */
@WebFilter(urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "REQUEST_ENCODING", value = "UTF-8") })
public class WebCharsetFilter implements Filter {
	private String encoding;

	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("REQUEST_ENCODING");
		if (encoding == null)
			encoding = "UTF-8";
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
			throws IOException, ServletException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(encoding);
		}

		response.setCharacterEncoding(encoding);
		next.doFilter(request, response);
	}

	public void destroy() {
	}
}



