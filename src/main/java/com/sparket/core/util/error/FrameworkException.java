package com.sparket.core.util.error;

import java.util.HashMap;
import java.util.Map;

public abstract class FrameworkException extends RuntimeException {

	private static final long serialVersionUID = -4387113273635828489L;
	
	private ErrorCode errorCode;
	private Map<String, Object> properties = new HashMap<>();
	private String customMessage;
	private int status = 400;

	public FrameworkException(ErrorCode errorCode) {
		super(errorCode.toString());
		this.errorCode = errorCode;
	}

	public FrameworkException(ErrorCode errorCode, Throwable e) {
		super(e);
		this.errorCode = errorCode;
	}

	public FrameworkException(ErrorCode errorCode, String message) {
		this(errorCode);
		this.customMessage = message;
	}

	public FrameworkException(ErrorCode errorCode, Throwable error, String message) {
		super(message, error);
		this.errorCode = errorCode;
		this.customMessage = message;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) this.properties.get(key);
	}

	public void set(String key, Object value) {
		this.properties.put(key, value);
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCustomMessage() {
		return this.customMessage;
	}
}
