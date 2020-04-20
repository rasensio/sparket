package com.sparket.core.util.http;

import com.sparket.core.util.json.Json;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.sparket.core.util.http.HttpDSL.http;

class HttpDSLTest {

	@Test
	void getJson() throws IOException, InterruptedException {
		Json json = http().getJson("https://jsonplaceholder.typicode.com/todos/1");
		System.out.println(json);
	}
}