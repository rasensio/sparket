/**
 * @author Rodrigo Asensio - @rasensio
 * @date 9/1/2016
 */
package com.sparket.core.util.error;

public class ApplicationException extends FrameworkException {

	private static final long serialVersionUID = 1L;

	public ApplicationException(ErrorCode errorCode) {
		super(errorCode);
	}

	public ApplicationException(ErrorCode errorCode, Throwable e) {
		super(errorCode, e);
	}

	public ApplicationException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	public ApplicationException(ErrorCode errorCode, Throwable error, String message) {
		super(errorCode, error, message);
	}

	public static void throwException(ErrorCode errorCode) throws ApplicationException {
		throw new ApplicationException(errorCode);
	}

	public static void throwException(ErrorCode errorCode, Throwable e) throws ApplicationException {
		throw new ApplicationException(errorCode, e);
	}

	public static void throwException(ErrorCode errorCode, Throwable error, String message) throws SystemException {
		throw new ApplicationException(errorCode, error, message);
	}

	public static void throwException(ErrorCode errorCode, String message) throws ApplicationException {
		throw new ApplicationException(errorCode, message);
	}

	public static void throwException(ErrorCode errorCode, String message, Object... args) {
		throw new ApplicationException(errorCode, String.format(message, args));
	}

}