/*
 * ProgrammingException.java Created on August 17, 2004, 9:47 AM
 */
package com.sparket.core.util.error;

/**
 * @author rasensio
 */
public class SystemException extends FrameworkException {

	public SystemException(ErrorCode errorCode) {
		super(errorCode);
	}

	public SystemException(ErrorCode errorCode, Throwable e) {
		super(errorCode, e);
	}

	public SystemException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	public SystemException(ErrorCode errorCode, Throwable error, String message) {
		super(errorCode, message);
	}

	public static void throwException(ErrorCode errorCode) throws SystemException {
		throw new SystemException(errorCode);
	}

	public static void throwException(ErrorCode errorCode, Throwable e) throws SystemException {
		throw new SystemException(errorCode, e);
	}

	public static void throwException(ErrorCode errorCode, String message) throws SystemException {
		throw new SystemException(errorCode, message);
	}

	public static void throwException(ErrorCode errorCode, Throwable error, String message) throws SystemException {
		throw new SystemException(errorCode, error, message);
	}

	public static void throwException(ErrorCode errorCode, String message, Object... args) {
		throw new SystemException(errorCode, String.format(message, args));
	}

}