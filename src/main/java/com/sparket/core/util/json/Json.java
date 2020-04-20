package com.sparket.core.util.json;

import java.util.HashMap;

public class Json extends HashMap<String, Object> {

	public Json() {
	}

	public Json(String key, String value) {
		this.put(key, value);
	}

	public Json(String key, double value) {
		this.put(key, value);
	}

	public Json(String key, int value) {
		this.put(key, value);
	}

	public Json(String key, boolean value) {
		this.put(key, value);
	}

	public Json(String key, Json child) {
		this.put(key, child);
	}

	public String getString(String key) {
		return (String) this.get(key);
	}

	/**
	 * static constructor
	 *
	 * @param key
	 * @param value
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 25, 2016
	 */
	public static JsonBuilder of(String key, String value) {
		return new JsonBuilder(key, value);
	}

	/**
	 * static constructor for long
	 *
	 * @param key
	 * @param value
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date May 8, 2017
	 */
	public static JsonBuilder of(String key, long value) {
		return new JsonBuilder(key, value);
	}

	public static JsonBuilder of(String key, double value) {
		return new JsonBuilder(key, value);
	}

	/**
	 * static builder for json child
	 *
	 * @param key
	 * @param child
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Jan 2, 2017
	 */
	public static JsonBuilder of(String key, Json child) {
		return new JsonBuilder(key, child);
	}

	/**
	 * static builder with integer
	 *
	 * @param key
	 * @param value
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Jan 3, 2017
	 */
	public static JsonBuilder of(String key, int value) {
		return new JsonBuilder(key, value);
	}

	public static JsonBuilder of(String key, boolean value) {
		return new JsonBuilder(key, value);
	}


}
