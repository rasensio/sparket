package com.sparket.core.util.logger;

import org.slf4j.LoggerFactory;

public class LoggerDSL {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("sparket");

	public static LoggerDSL logger() {
		return new LoggerDSL();
	}

	private LoggerDSL() {

	}

	public void debug(String value) {
		LOG.debug(value);
	}

	public void info(String value) {
		LOG.info(value);
	}

	public void error(String message, Throwable exception) {
		LOG.error(message, exception);
	}

	public void error(String message) {
		LOG.error(message);
	}
}
