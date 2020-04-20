package com.sparket.core.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sparket.core.util.error.ApplicationException;

import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static com.sparket.core.util.json.JsonDSL.json;
import static com.sparket.core.util.string.StringDSL.strings;

public class JwtDSL {


	public static final String UPGOAL = "sparket";
	private JWTVerifier verifier;

	private JwtDSL() {
	}

	public static JwtDSL jwt() {
		return new JwtDSL();
	}

	public JwtDSL build(String secret) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		this.verifier = JWT.require(algorithm)
				.withIssuer(UPGOAL)
				.build();
		return this;

	}

	/**
	 * encode a jwt token
	 *
	 * @param claims
	 * @param secret
	 * @param expirationDays
	 * @return
	 */
	public String encode(Map<String, String> claims, String secret, int expirationDays) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		java.util.Date expirationDate = Date.from(Instant.now().plus(Duration.ofDays(expirationDays)));
		JWTCreator.Builder builder = JWT.create()
				.withIssuer(UPGOAL)
				.withExpiresAt(expirationDate);
		claims.forEach(builder::withClaim);
		return builder.sign(algorithm);
	}

	/**
	 * verify the token and returns the payload
	 *
	 * @param token
	 * @return
	 */
	public String decode(String token) {
		try {
			DecodedJWT jwt = this.verifier.verify(token);
			String base64 = jwt.getPayload();
			return strings().decodeBase64(base64);
		} catch (JWTDecodeException e) {
			ApplicationException.throwException(SecurityErrorCode.INVALID_JWT, e);
			return null;
		}
	}

	/**
	 * decode a token and converts it to object
	 * @param token
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public <T> T decode(String token, Class<T> clazz) {
		String json = this.decode(token);
		return json().fromJson(json, clazz);
	}
}
