package com.illumio.exception;

public class FirewallException extends Exception {

	private static final long serialVersionUID = 4049060184596939536L;

	public FirewallException() {
		super();
	}

	public FirewallException(String message) {
		super(message);
	}

	public FirewallException(String message, Throwable cause) {
		super(message, cause);
	}

}
