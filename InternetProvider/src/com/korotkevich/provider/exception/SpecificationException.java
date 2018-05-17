package com.korotkevich.provider.exception;

/**
 * Exception indicates condition connected with repository package classes functioning
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class SpecificationException extends Exception {
	private static final long serialVersionUID = 5889025215248159397L;
	
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 */
	public SpecificationException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause he cause (which is saved for later retrieval by the Throwable.getCause() method)
	 */
	public SpecificationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 */
	public SpecificationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the cause.
	 * @param cause he cause (which is saved for later retrieval by the Throwable.getCause() method)
	 */
	public SpecificationException(Throwable cause) {
		super(cause);
	}

}
