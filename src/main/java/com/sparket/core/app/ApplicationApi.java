package com.sparket.core.app;

import com.sparket.core.util.json.Json;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static com.sparket.core.app.ApplicationResponse.build;
import static com.sparket.core.util.json.JsonDSL.json;
import static com.sparket.core.util.logger.LoggerDSL.logger;

public abstract class ApplicationApi {

	protected static final String USER = "user";

	public abstract void initRoutes() throws Exception;


	public void debug(String value) {
		logger().debug(value);
	}

	public void info(String value) {
		logger().info(value);
	}

	/**
	 * shortcut to read read body as json
	 *
	 * @param req
	 * @return
	 */
	protected Json readJson(Request req) {
		return json().fromJson(req.body());
	}

	/***************************************
	 *     response methods
	 ***************************************/

	protected String ok(Response res, Optional optional) {
		Object payload = optional.isPresent() ? optional.get() : null;
		return build(res).ok(payload);
	}

	protected String notFound(Response res, String message) {
		return build(res).notFound(message);
	}

	protected String ok(Response res, Object payload) {
		return build(res).ok(payload);
	}

	protected String ok(Response res) {
		return build(res).ok("");
	}

	protected String bad(Response res, String message) {
		return build(res).bad(message);
	}

	protected String error(Response res, String message) {
		return build(res).error(message);
	}

	protected String forbidden(Response res, String message) {
		return build(res).forbidden(message);
	}

	/**
	 * return the attribute set during decodification of the JWT
	 * @param req
	 * @return
	 */
	protected UserToken user(Request req) {
		return req.attribute(USER);
	}

}
