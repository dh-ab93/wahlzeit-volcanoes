package org.wahlzeit.model;

public interface CoordinateException {
	/**
	 * @return true if exception is caused by illegal arguments or improper use of methods in client code
	 * (violation of preconditions),
	 * false if exception was caused by a bug in the code behind the interface (violation of postconditions or
	 * class invariants)
	 */
	boolean isBlameOnCaller();
}

abstract class AbstractCoordinateException extends Exception implements CoordinateException {
	AbstractCoordinateException() {
		super();
	}

	AbstractCoordinateException(Throwable cause) {
		super(cause);
	}

	AbstractCoordinateException(String message) {
		super(message);
	}

	AbstractCoordinateException(String message, Throwable cause) {
		super(message, cause);
	}
}

/**
 * indicates violation of precondition
 */
class CoordinateUseException extends AbstractCoordinateException implements CoordinateException {
	CoordinateUseException() {
		super();
	}

	CoordinateUseException(Throwable cause) {
		super(cause);
	}

	CoordinateUseException(String message) {
		super(message);
	}

	CoordinateUseException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public boolean isBlameOnCaller() {
		return true;
	}
}

/**
 * indicates violation of postcondition or class invariants
 */
class CoordinateError extends AbstractCoordinateException {
	CoordinateError() {
		super();
	}

	CoordinateError(Throwable cause) {
		super(cause);
	}

	CoordinateError(String message) {
		super(message);
	}

	CoordinateError(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public boolean isBlameOnCaller() {
		return false;
	}
}
