package com.sparket.core.app;

import com.sparket.core.util.http.HttpConstants;
import com.sparket.core.util.jwt.JwtDSL;
import com.sparket.core.util.time.StopWatch;
import spark.Filter;

import static com.sparket.core.util.jwt.JwtDSL.jwt;
import static com.sparket.core.util.logger.LoggerDSL.logger;
import static com.sparket.core.util.string.StringDSL.strings;
import static spark.Spark.halt;

public class Filters {


	private static final String ATTRIBUTE_STOP_WATCH = "ATTRIBUTE_STOP_WATCH";
	private static final String ATTRIBUTE_REQUEST_ID = "ATTRIBUTE_REQUEST_ID";
	private static String SECRET;
	private static JwtDSL JWT;


	static {
		SECRET = Application.app().env("JWT_SECRET");
		JWT = jwt().build(SECRET);
	}

	/**
	 * return the validator
	 *
	 * @return
	 */
	public static JwtDSL jwtValidator() {
		return JWT;
	}


	/**
	 * filter to validate the JWT token
	 */
	public static Filter validateJwt = (req, res) -> {
		if (req.requestMethod().equals(HttpConstants.METHOD_OPTIONS)) return;
		String PUBLIC_PATH = "/auth";
		if (!req.pathInfo().startsWith(PUBLIC_PATH)) {
			String token = req.headers("Authorization");

			// check if token exists
			if (token == null || token.trim().length() == 0) {
				logger().error("unauthorized " + req.requestMethod() + " to " + req.pathInfo());
				halt(401, "Invalid token");
				return;
			}
			UserToken userToken = JWT.decode(token, UserToken.class);
			req.attribute("user", userToken);
		}
	};

	/**
	 * the cors filter
	 */
	public static Filter cors = (req, res) -> {
		// wont set anything on options since is being set after this
		if (req.requestMethod().equalsIgnoreCase(HttpConstants.OPTIONS)) return;
		res.header("Access-Control-Allow-Origin", "*");
		res.header("Access-Control-Allow-Methods", "OPTIONS,GET,POST,PUT,DELETE");
		res.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
		res.header("Access-Control-Allow-Credentials", "true");
	};

	/**
	 * list of standard headers unless overridne
	 */
	public static Filter headers = (req, res) -> {
		res.type("application/json");
	};

	/**
	 * filter to log the start of the operation
	 */
	public static Filter logStart = (req, res) -> {
		StopWatch stopWatch = StopWatch.start();
		req.attribute(ATTRIBUTE_STOP_WATCH, stopWatch);
		String requestId = strings().randomAlphabetic(5);
		req.attribute(ATTRIBUTE_REQUEST_ID, requestId);
		logger().info(requestId + ": " + req.requestMethod() + " to " + req.pathInfo());
	};

	/**
	 * filter to log the end of the request
	 */
	public static Filter logEnd = (req, res) -> {
		StopWatch stopWatch = req.attribute(ATTRIBUTE_STOP_WATCH);
		long millis = stopWatch.stop().millis();
		String requestId = req.attribute(ATTRIBUTE_REQUEST_ID);
		String method = req.requestMethod();
		String path = req.pathInfo();
		int status = res.status();
		logger().info(requestId + ": " + method + " status " + status + " to " + path + " took " + millis + "ms");
	};

}
