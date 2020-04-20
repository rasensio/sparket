package com.sparket.core.util.http;

import com.sparket.core.util.error.ApplicationException;
import com.sparket.core.util.json.Json;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static com.sparket.core.util.error.ErrorDSL.fail;
import static com.sparket.core.util.json.JsonDSL.json;

public class HttpDSL {

	public static HttpDSL http() {
		return new HttpDSL();
	}

	private HttpDSL() {
	}

	/**
	 * makes an http request and return json
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Json getJson(String url) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.timeout(Duration.ofMinutes(1))
					.header("Content-Type", "application/json")
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				fail(HttpErrorCode.SERVER_RESPONSE);
			}
			return json().fromJson(response.body());
		} catch (Exception e) {
			e.printStackTrace();
			ApplicationException.throwException(HttpErrorCode.SERVER_RESPONSE);
			return null;
		}
}

//	public Json postJson(String url) {
//
//	}
}
