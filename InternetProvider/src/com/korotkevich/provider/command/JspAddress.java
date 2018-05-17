package com.korotkevich.provider.command;

/**
 * Contains definition of the jsp
 * @author Korotkevich Kirill 2018-05-16
 *
 */
public enum JspAddress {
	INDEX("index.jsp"), 
	LOGIN("jsp/login.jsp"), 
	REGISTRATION("jsp/registration.jsp"), 
	USER_UPDATE("jsp/userUpdate.jsp"), 
	MAIN_ADMIN("jsp/mainAdmin.jsp"),
	MAIN_USER("jsp/mainUser.jsp"),
	SERVICE_PLAN_LIST("jsp/servicePlanList.jsp"),
	SERVICE_PLAN_LIST_ADMIN("jsp/servicePlanListAdmin.jsp"),
	ERROR("jsp/error.jsp");

	private String path;
	
	/**
	 * Constructor with path
	 * @param path to the jsp
	 */
	JspAddress(String path) {
		this.path = path;
	}

	/**
	 * Get the path to the jsp
	 * @return path to the jsp(String)
	 */
	public String getPath() {
		return path;
	}

}
