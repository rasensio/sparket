package com.sparket.core.util.error;

import com.sparket.core.util.FrameworkDSL;

import java.util.ResourceBundle;

public class ErrorDSL extends FrameworkDSL {

	/**
	 * Returns the full message for the exception
	 */
	public static String getMessageForException(Throwable e) {
		ErrorCode errorCode = null;
		String message = null;
		if (e instanceof FrameworkException) {
			FrameworkException ex = (FrameworkException) e;
			errorCode = ex.getErrorCode();
			message = ex.getCustomMessage();
		} else {
			errorCode = SystemErrorCode.SYSTEM_ERROR;
		}
		String key = errorCode.toString();
		String clazz = errorCode.getClass().getName() + "Bundle";
		ResourceBundle bundle = ResourceBundle.getBundle(clazz);
		return message != null ? message : bundle.getString(key);
	}

	/**
	 * will throw an application exception
	 *
	 * @param errorCode
	 * @author Rodrigo Asensio - @rasensio
	 * @date Feb 8, 2017
	 */
	public static void fail(ErrorCode errorCode) {
		ApplicationException.throwException(errorCode);
	}

	/**
	 * will throw an application exception with a message
	 *
	 * @param errorCode
	 * @author Rodrigo Asensio - @rasensio
	 * @date Feb 8, 2017
	 */
	public static void fail(ErrorCode errorCode, String message) {
		ApplicationException.throwException(errorCode, message);
	}

	/**
	 * will throw an application exception with var args
	 *
	 * @param errorCode
	 * @param message
	 * @param values
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 25, 2017
	 */
	public static void fail(ErrorCode errorCode, String message, Object... values) {
		ApplicationException.throwException(errorCode, String.format(message, values));
	}

	/**
	 * failed with a controlled exception
	 *
	 * @param errorCode
	 * @param exception
	 * @author Rodrigo Asensio - @rasensio
	 * @date Mar 18, 2017
	 */
	public static void fail(ErrorCode errorCode, Exception exception) {
		ApplicationException.throwException(errorCode, exception);
	}

	/**
	 * will throw a system exception
	 *
	 * @param errorCode
	 * @author Rodrigo Asensio - @rasensio
	 * @date Feb 8, 2017
	 */
	public static void fatal(ErrorCode errorCode) {
		SystemException.throwException(errorCode);
	}

	/**
	 * will throw a system exception with a message
	 *
	 * @param errorCode
	 * @author Rodrigo Asensio - @rasensio
	 * @date Feb 8, 2017
	 */
	public static void fatal(ErrorCode errorCode, String message) {
		SystemException.throwException(errorCode, message);
	}

	public static void fatal(ErrorCode errorCode, Throwable exception, String message) {
		SystemException.throwException(errorCode, exception, message);
	}

	/**
	 * error code plus exception
	 *
	 * @param errorCode
	 * @param exception
	 * @author Rodrigo Asensio - @rasensio
	 * @date Apr 7, 2017
	 */
	public static void fatal(ErrorCode errorCode, Exception exception) {
		SystemException.throwException(errorCode, exception);
	}

	/**
	 * throw error with controlled exception
	 *
	 * @param errorCode
	 * @param exception
	 * @author Rodrigo Asensio - @rasensio
	 * @date Mar 18, 2017
	 */
	public static void throwError(ErrorCode errorCode, Exception exception) {
		SystemException.throwException(errorCode, exception);
	}
}
