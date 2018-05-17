package com.korotkevich.provider.command;

import javax.servlet.http.HttpServletRequest;

import com.korotkevich.provider.controller.Router;

/**
 * Basic interface for command implementation
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public interface Command {
	
	/**
	 * Declaration of the main command method
	 * which executes incoming HttpServletRequest 
	 * @param request incoming HttpServletRequest object
	 * @return Router object which contains url and route type
	 */
	Router execute(HttpServletRequest request);
}
