package com.pappucoder.in.freemarker.exception;

import org.springframework.http.HttpStatus;

public class FreeMarkerServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;
	private ErrorCodes errorCodes;
	private String message;

	public FreeMarkerServiceException(HttpStatus httpStatus, ErrorCodes errorCodes, String message) {
		this.httpStatus = httpStatus;
		this.errorCodes = errorCodes;
		this.message = message;
	}
	
	public FreeMarkerServiceException(HttpStatus httpStatus, ErrorCodes errorCodes) {
		this.httpStatus = httpStatus;
		this.errorCodes = errorCodes;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ErrorCodes getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(ErrorCodes errorCodes) {
		this.errorCodes = errorCodes;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "FreeMarkerServiceException [httpStatus=" + httpStatus + ", errorCodes=" + errorCodes + ", message="
				+ message + "]";
	}
	
	
}
