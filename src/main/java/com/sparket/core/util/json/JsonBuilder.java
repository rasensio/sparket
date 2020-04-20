package com.sparket.core.util.json;

public class JsonBuilder {

	private Json json;

	public JsonBuilder(String key, String value) {
		this.json = new Json(key, value);
	}

	public JsonBuilder(String key, int value) {
		this.json = new Json(key, value);
	}

	public JsonBuilder(String key, long value) {
		this.json = new Json(key, value);
	}

	public JsonBuilder(String key, double value) {
		this.json = new Json(key, value);
	}

	public JsonBuilder(String key, boolean value) {
		this.json = new Json(key, value);
	}

	public JsonBuilder(String key, Json value) {
		this.json = new Json(key, value);
	}


	public JsonBuilder put(String key, Object value) {
		this.json.put(key, value);
		return this;
	}

	public JsonBuilder put(String key, int value) {
		this.json.put(key, value);
		return this;
	}

	public Json get() {
		return this.json;
	}

}