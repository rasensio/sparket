package com.sparket.core.util.input;

import com.sparket.core.util.error.ErrorCode;

public enum ValidationErrorCode implements ErrorCode {

	READING_INPUT,
	VALIDATION,
	REQUIRED_INPUT,
	INPUT_MUST_BE_CERTAIN_VALUE,
	INPUT_TOO_SHORT,
	INPUT_TOO_LONG,
	INVALID_NUMBER,
	INVALID_FLOAT,
	INVALID_INPUT,
	INVALID_DATE,
	INVALID_EMAIL, 
	INVALID_JSONP_CALLBACK, 
	INPUT_MUST_BE_EQUAL;

}
