package com.korotkevich.provider.controller;

/**
 * Contains path and route type for request execution
 * @author Korotkevich Kirill 2018-05-22
 *
 */
public class Router {

	/**
	 * Contains route type - forward and redirect
	 * @author Korotkevich Kirill 2018-05-22
	 *
	 */
	public enum RouteType {
		FORWARD, REDIRECT;
	}
	
	private String jspPath;
	private RouteType route;
	
	/**
	 * Basic constructor
	 * @param jspPath stored path to jsp file
	 */
	public Router(String jspPath){
		this.jspPath = jspPath;
		this.route = RouteType.FORWARD;
	}
	
	/**
	 * Constructor with jspPath and route  
	 * @param jspPath stored path to jsp file
	 * @param route forward or redirect
	 */
	public Router(String jspPath, RouteType route){
		this.jspPath = jspPath;
		this.route = route;
	}

	/**
	 * Gets jspPath 
	 * @return jspPath
	 */
	public String getJspPath() {
		return jspPath;
	}

	/**
	 * Sets jspPath 
	 * @param jspPath stored path to jsp file
	 */
	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

	/**
	 * Gets route
	 * @return route
	 */
	public RouteType getRoute() {
		return route;
	}

	/**
	 * Sets route
	 * @param route forward or redirect
	 */
	public void setRoute(RouteType route) {
		this.route = route;
	}
	
}
