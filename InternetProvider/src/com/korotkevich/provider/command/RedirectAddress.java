package com.korotkevich.provider.command;

/**
 * Contains definition of the jsp for redirect operaion
 * @author Korotkevich Kirill 2018-05-16
 *
 */
public enum RedirectAddress {
	MAIN_REDIRECT("?command=go_to_main_page"),
	LOGIN_REDIRECT("?command=go_to_login_page"),
	SERVICE_PLAN_LIST_REDIRECT("?command=go_to_service_plan_list_page"),
	ERROR_PAGE_REDIRECT("?command=go_to_error_page");

	private String path;
	
	/**
	 * Constructor with path
	 * @param path to the jsp
	 */
	RedirectAddress(String path) {
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
