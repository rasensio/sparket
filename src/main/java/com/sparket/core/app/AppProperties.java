package com.sparket.core.app;

import spark.resource.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for simple work with app.properties file and system properties or environment variables.
 * Notes:
 * - Environment variables defined and controlled by the operating system. Usually naming style is UPPERCASE_WITH_UNDERSCORES.
 * - System properties are defined by the JVM and can be specified on the command line when Java application starts. Usually naming style is lowercase.with.dots
 */
public final class AppProperties {

	private Properties properties;

	public AppProperties() {
		this.loadProperties();
	}

	private void loadProperties() {
		ClassPathResource classPathResource = new ClassPathResource("app.properties");
		this.properties = new Properties();
		InputStream input = null;
		try {
			input = classPathResource.getInputStream();
			this.properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String prop(String propertyName, String defaultValue) {
		if (properties == null) loadProperties();
		String property = properties.getProperty(propertyName);
		return (property != null && !property.isEmpty()) ? property.trim() : defaultValue;
	}

	public static String system(String propertyName, String defaultValue) {
		String property;
		try {
			property = System.getProperty(propertyName);
		} catch (Exception e) {
			property = null;
		}
		return (property != null && !property.isEmpty()) ? property.trim() : defaultValue;
	}

	public static String env(String variableName, String defaultValue) {
		String variable;
		try {
			variable = System.getenv(variableName);
		} catch (Exception e) {
			variable = null;
		}
		return (variable != null && !variable.isEmpty()) ? variable.trim() : defaultValue;
	}


}
