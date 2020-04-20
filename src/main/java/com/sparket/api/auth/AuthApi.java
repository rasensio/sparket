package com.sparket.api.auth;

import com.sparket.api.user.User;
import com.sparket.api.user.UserDao;
import com.sparket.core.app.Application;
import com.sparket.core.app.ApplicationApi;
import com.sparket.core.app.Filters;
import com.sparket.core.util.json.Json;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

import static com.sparket.core.util.collection.CollectionsDSL.collections;
import static com.sparket.core.util.http.HttpDSL.http;
import static com.sparket.core.util.input.InputDSL.input;
import static com.sparket.core.util.input.ValidationDSL.notEquals;
import static com.sparket.core.util.jwt.JwtDSL.jwt;
import static com.sparket.core.util.time.DateDSL.date;
import static spark.Spark.path;
import static spark.Spark.post;

public class AuthApi extends ApplicationApi {

	private static final String PROVIDER_FACEBOOK = "facebook";
	private static final String ROLE_USER = "user";
	private static final String ROLE_ADMIN = "admin";
	private static String SECRET;

	static {
		SECRET = Application.app().env("JWT_SECRET");
	}

	@Inject
	private UserDao userDao;


	@Override
	public void initRoutes() throws Exception {
		path("/auth", () -> {
			post("/facebook", this::facebooLogin);
			post("/returning", this::returningUser);
		});
	}

	/**
	 * the user is returning, will be checked in the
	 * database and generated a new token
	 * @param req
	 * @param res
	 * @return
	 */
	private String returningUser(Request req, Response res) {
		Json json = this.readJson(req);
		String token = input(json.getString("token")).notEmpty().minLength(5).asString();
		User decoded = Filters.jwtValidator().decode(token, User.class);
		Optional<User> user = this.userDao.getUser(decoded.getEmail());
		if (user.isEmpty()) {
			return this.forbidden(res, "Invalid token");
		} else {
			return this.createJWTAndPayload(res, user.get());
		}
	}

	/**
	 * creates the user payload and the token
	 * @param res
	 * @param user
	 * @return
	 */
	private String createJWTAndPayload(Response res, User user) {
		String newToken = this.generateJWT(user);
		User userPayload = new User()
			.setEmail(user.getEmail())
			.setName(user.getName())
			.setAvatar(user.getAvatar());
		Json payload = Json.of("token", newToken)
				.put("user", userPayload)
				.get();
		return this.ok(res, payload);
	}

	private String facebooLogin(Request req, Response res) {
		Json json = this.readJson(req);

		// input validation
		String name = input(json.getString("name")).notEmpty().minLength(5).asString();
		String email = input(json.getString("email")).notEmpty().isEmail().asString();
		String avatar = input(json.getString("avatar")).asString();
		String id = input(json.getString("id")).asString();
		String providerToken = input(json.getString("providerToken")).notEmpty().minLength(10).asString();

		// validates against facebook the user identity
		Json facebookResponse = http().getJson("https://graph.facebook.com/v6.0/me?fields=id,name,email&access_token=" + providerToken);
		String facebookName = facebookResponse.getString("name");
		String facebookEmail = facebookResponse.getString("email");
		String facebookId = facebookResponse.getString("id");
		if (notEquals(email, facebookEmail) || notEquals(facebookName, name) || notEquals(id, facebookId)) {
			return bad(res, "Facebook validation failed");
		}

		// get the user from the DB
		Optional<User> user = this.userDao.getUser(email);
		if (user.isPresent()) {
			user.get()
					.setLastLogin(date().asIso())
					.setProviderToken(providerToken);

			this.userDao.updateUser(user.get());
		} else {
			// no user exists with this email
			info("creating user " + email);
			User newUser = new User()
				.newId()
				.setEmail(email)
				.setProviderToken(providerToken)
				.setProvider(PROVIDER_FACEBOOK)
				.setRole(ROLE_USER)
				.setAvatar(avatar)
				.setLastLogin(date().asIso())
				.setName(name);
			this.userDao.insertUser(newUser);
			info("user created ok " + email);
			user = Optional.of(newUser);
		}

		return this.createJWTAndPayload(res, user.get());
		// create the payload and the jwt token
//		String token = this.generateJWT(user.get());
//		User userPayload = new User()
//				.setEmail(user.get().getEmail())
//				.setName(user.get().getName())
//				.setAvatar(user.get().getAvatar());
//		return ok(res, Json.of("token", token).get());


	}

	/**
	 * will generate and return the signed JWT token
	 *
	 * @param user
	 * @return
	 */
	private String generateJWT(User user) {
		Map<String, String> claims = collections().map("email", user.getEmail());
		claims.put("name", user.getName());
		claims.put("userId", user.getUserId());
		claims.put("role", user.getRole());
		return jwt().encode(claims, SECRET, 30);
	}

}
