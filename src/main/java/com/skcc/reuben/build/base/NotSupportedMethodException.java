package com.skcc.reuben.build.base;

public class NotSupportedMethodException extends Exception {

	public NotSupportedMethodException() {

	}

	public NotSupportedMethodException(String message) {

		super(message);
	}

	public NotSupportedMethodException(Throwable t) {
		super(t);
	}
	
	public NotSupportedMethodException(String message, Throwable t) {
		super(message, t);
	}
}
