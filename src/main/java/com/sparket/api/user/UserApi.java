package com.sparket.api.user;

import com.sparket.core.app.ApplicationApi;
import com.sparket.core.app.Filters;
import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.path;

public class UserApi extends ApplicationApi {

	@Override
	public void initRoutes() throws Exception {
		path("/users", () -> {
			get("/validate", this::validateJwt);
		});
	}

	private String validateJwt(Request req, Response res) {
		String token = req.headers("Authorization");
		Filters.jwtValidator().decode(token);
		return ok(res);
	}

}
