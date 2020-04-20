package com.sparket.core.util.http;

import spark.Request;
import spark.Response;

import static com.sparket.core.util.error.ErrorDSL.fail;

public class WebDSL {

	public static WebDSL web(Request req, Response res) {
		return new WebDSL();
	}

	private WebDSL() {}



}
