package com.korotkevich.provider.entity;

/**
 * Contains user statuses, user statuses id
 * @author Korotkevich Kirill 2018-05-16
 *
 */
public enum UserStatus {
	NEW(1), ACTIVE(2), BLOCKED(3);

	private int id;

	/**
	 * Basic constructor
	 * @param id
	 */
	UserStatus(int id) {
		this.id = id;
	}

	/**
	 * Get the user status id
	 * @return id
	 */
	public int getId() {
		return this.id;
	}

}
