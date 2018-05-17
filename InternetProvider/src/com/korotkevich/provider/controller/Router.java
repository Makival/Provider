package com.korotkevich.provider.controller;

public class Router {

	public enum RouteType {
		FORWARD, REDIRECT;
	}
	
	private String jspPath;
	private RouteType route;
	
	public Router(String jspPath){
		this.jspPath = jspPath;
		this.route = RouteType.FORWARD;
	}
	
	public Router(String jspPath, RouteType route){
		this.jspPath = jspPath;
		this.route = route;
	}

	public String getJspPath() {
		return jspPath;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

	public RouteType getRoute() {
		return route;
	}

	public void setRoute(RouteType route) {
		this.route = route;
	}
	
}
