package com.korotkevich.provider.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains user roles, role names, and access level for command execution
 * @author Korotkevich Kirill 2018-05-11
 *
 */
public enum UserRole {
	CLIENT("client", 1), ADMINISTRATOR("administrator", 2);

	private String roleName;
	private int accessLevel;
	
	/**
	 * Basic constructor
	 * @param roleName
	 * @param accessLevel
	 */
	UserRole(String roleName, int accessLevel) {
		this.roleName = roleName;
		this.accessLevel = accessLevel;
	}

	/**
	 * Get the role name
	 * @return roleName
	 */
	public String getRoleName() {
		return this.roleName;
	}
	
	/**
	 * Get the access level 
	 * @return accessLevel
	 */
	public int getAccessLevel() {
		return this.accessLevel;
	}
	
	/**
	 * get the role list
	 * @return roleList
	 */
	public List<String> getRoleList() {
		List<String> roleList = new ArrayList<>();

		for (UserRole role : UserRole.values()) {
			roleList.add(role.toString().toLowerCase());
		}

		return roleList;
	}


}
