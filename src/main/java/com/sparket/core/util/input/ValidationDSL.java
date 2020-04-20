package com.sparket.core.util.input;

public class ValidationDSL {

	/**
	 * straightforward notequal
	 * @param input1
	 * @param input2
	 * @return
	 */
	public static boolean notEquals(String input1, String input2) {
		return !input1.equals(input2);
	}
}
