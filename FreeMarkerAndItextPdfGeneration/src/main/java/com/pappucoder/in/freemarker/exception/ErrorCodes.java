package com.pappucoder.in.freemarker.exception;

public enum ErrorCodes {
	TEMPLATE_URL_NOT_FOUND("FMS-001", "please check template url"),
	PLEASE_CHECK_MAP_PARAMETER_AGAINST_THE_HTML("FMS-002",""),
	PLEASE_CHECK_YOUR_URL("FMS-003",""),
	EXCEPTION_OCCURED_WHILE_FETCHING_TEMPLATE("FMS-004","exception occured while fecthing template"),
	EXCEPTION_OCCURED_WHILE_FREEMARKER("FMS-004","exception occured while freemarker process"),
	BYTEARRAY_OBJECT_IS_NOT_VALID("FMS-005","byte[] object of filled template is not valid"),
	FILE_NOT_FOUND("FMS-006","file not found"),
	EXPECTED_IS_OUTPUT_STREAM_AS_OBJECT("FMS-007","expected is output stream as object"),
	EXCEPTION_OCCURED_WHILE_GENERATING_PDF("FMS-008","exception occured while generating pdf"),
	FREEMARKER_REQUEST_BEAN_IS_EMPTY("FMS-009","please provide proper request"),
	
	;

	private String errorCode;
	private String message;

	private ErrorCodes(String errorcode, String message) {
		this.errorCode = errorcode;
		this.message = message;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getMessage() {
		return this.message;
	}

}
