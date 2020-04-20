package com.sparket.core.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparket.core.util.error.ApplicationException;
import com.sparket.core.util.error.SystemException;

import java.io.IOException;

public class JsonDSL {

	private ObjectMapper mapper = new ObjectMapper();

	public static JsonDSL json() {
		return new JsonDSL();
	}

	private JsonDSL() {
		this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public static Json json(String key, String value) {
		return Json.of(key, value).get();
	}

	public static Json json(String key, Json value) {
		return Json.of(key, value).get();
	}

	public static Json json(String key, boolean value) {
		return Json.of(key, value).get();
	}

	public static Json json(String key, int value) {
		return Json.of(key, value).get();
	}

	public static Json json(String key, double value) {
		return Json.of(key, value).get();
	}

	/**
	 * convert a json string to object
	 *
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return the object parsed
	 * @throws IOException
	 */
	public <T> T fromJson(String json, Class<T> clazz) {
		try {
			this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return this.mapper.readValue(json, clazz);
		} catch (Exception e) {
			SystemException.throwException(JsonErrorCode.PARSING_ERROR);
			return null;
		}
	}

	/**
	 * converts a plain
	 *
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Json fromJson(String json) {
		try {
			TypeReference<Json> typeRef = new TypeReference<Json>() {
			};
			Json map = mapper.readValue(json, typeRef);
			return map;
		} catch (Exception e) {
			ApplicationException.throwException(JsonErrorCode.PARSING_ERROR, e);
			return null;
		}
	}

	/**
	 * convert an object to json string
	 * @param object
	 * @return
	 */
	public String toJson(Object object) {
		try {
			return mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(object);
		} catch (Exception e) {
			ApplicationException.throwException(JsonErrorCode.PARSING_ERROR, e);
			return null;
		}
	}
}
