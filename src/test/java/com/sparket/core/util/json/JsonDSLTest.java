package com.sparket.core.util.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.sparket.core.util.json.JsonDSL.json;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonDSLTest {

	/**
	 * test the marshaling from json to object
	 * @throws IOException
	 */
	@Test
	void fromJson() throws IOException {
		PersonPojo emp = json().fromJson("{\n" +
				"  \"name\": \"David\",\n" +
				"  \"role\": \"Manager\",\n" +
				"  \"city\": \"Los Angeles\",\n" +
				"  \"address\" : {\n" +
				"    \"street\": \"34 Sanderling Pl\",\n" +
				"    \"zip\": 26747\n" +
				"  }\n" +
				"}", PersonPojo.class);
		assertEquals(emp.getName(), "David");
		assertEquals(emp.getRole(), "Manager");
		assertEquals(emp.getCity(), "Los Angeles");
		assertEquals(emp.getAddress().getZip(), 26747);
		assertEquals(emp.getAddress().getStreet(), "34 Sanderling Pl");
	}
}