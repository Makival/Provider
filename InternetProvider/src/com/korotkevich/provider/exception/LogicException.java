package com.korotkevich.provider.exception;

/**
 * Exception indicates condition connected with logic package classes functioning
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class LogicException extends Exception {

	private static final long serialVersionUID = -4501670383377338312L;
	
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 */
	public LogicException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause he cause (which is saved for later retrieval by the Throwable.getCause() method)
	 */
	public LogicException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 */
	public LogicException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the cause.
	 * @param cause he cause (which is saved for later retrieval by the Throwable.getCause() method)
	 */
	public LogicException(Throwable cause) {
		super(cause);
	}
}
