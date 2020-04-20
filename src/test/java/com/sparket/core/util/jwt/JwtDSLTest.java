package com.sparket.core.util.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.sparket.core.util.jwt.JwtDSL.jwt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtDSLTest {


	/**
	 * test the positive signature
	 * @throws IOException
	 */
	@Test
	void testEncodeAndVerification() throws IOException {
		String secret = "abc";
		Map<String, String> claims = new HashMap<>();
		claims.put("name", "David");
		claims.put("role", "admin");
		claims.put("scopes", "a,b,c");
		String token = jwt().encode(claims, secret, 1);

		// verify the token
		JwtDSL verifier = jwt().build(secret);
		UserPojo user = verifier.decode(token, UserPojo.class);
		assertEquals(user.getName(), "David");
		assertEquals(user.getRole(), "admin");
		assertEquals(user.getScopes(), "a,b,c");
	}

	@Test
	void testHijackedToken() {
		String secret = "abc";
		Map<String, String> claims = new HashMap<>();
		claims.put("name", "David");
		claims.put("role", "admin");
		claims.put("scopes", "a,b,c");
		String token = jwt().encode(claims, secret, 1);
		System.out.println(token);
		// verify the token
		JwtDSL verifier = jwt().build(secret);

		// verify wrong format
		assertThrows(JWTDecodeException.class, () -> {
			verifier.decode("abasflkajsdks.salfdkajsdlfka.fhasdlkjashdlf");
		});
		assertThrows(JWTDecodeException.class, () -> {
			verifier.decode(token.replaceAll("a", "b").replaceAll("e", "c").replaceAll("i","d"));
		});
	}
}