package com.korotkevich.provider.exception;

/**
 * Exception indicates condition connected with connection pool class functioning
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class ConnectionPoolException extends Exception {
	private static final long serialVersionUID = -324103703381605855L;

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 */
	public ConnectionPoolException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause he cause (which is saved for later retrieval by the Throwable.getCause() method)
	 */
	public ConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 */
	public ConnectionPoolException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the cause.
	 * @param cause he cause (which is saved for later retrieval by the Throwable.getCause() method)
	 */
	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}

}
