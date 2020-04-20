package com.sparket.core.service;

import com.sparket.core.service.logger.LoggerEnum;

import static com.sparket.core.util.logger.LoggerDSL.logger;

public abstract class Service {

	public abstract void start() throws Throwable;

	public abstract void stop() throws Throwable;

	public abstract LoggerEnum getLogger();

	protected String getConfigFile() {
		return null;
	}

	protected void info(String value) {
		logger().info(value);
	}
}
