package com.sparket.core.util.input;


import com.sparket.core.util.FrameworkDSL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;


import static com.sparket.core.util.error.ErrorDSL.fail;
import static com.sparket.core.util.html.HtmlDSL.html;
import static com.sparket.core.util.string.StringDSL.strings;

public class InputDSL extends FrameworkDSL {

	private Optional<Object> input;
	private String exceptionMessage = "validation failed";
	private boolean hasValue;
	private String stringValue;

	public static InputDSL input(Object input) {
		return new InputDSL(input);
	}

	private InputDSL() {}

	private InputDSL(Object input) {
		this.build(input);
	}

	/**
	 * build the input object
	 * @param input
	 */
	private void build(Object input) {
		this.input = Optional.ofNullable(input);
		this.hasValue = this.hasValue();
		this.stringValue = this.hasValue ? html().clean(this.input.get().toString()) : "";
	}

	public String asString() {
		return this.stringValue;
	}

	public int asInt() {
		if (this.hasValue) {
			return Double.valueOf(this.stringValue).intValue();
		}
		return -1;
	}

	public boolean asBoolean() {
		if (this.hasValue) {
			return Boolean.parseBoolean(this.stringValue);
		}
		return false;
	}

	private boolean hasValue() {
		if (!input.isPresent()) return false;
		Object value = this.input.get();
		return !value.toString().isEmpty();
	}

	public InputDSL withMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
		return this;
	}

	public InputDSL isNumber() {
		if (this.hasValue) {
			if (!Pattern.matches("[-+]?\\d*\\.?\\d+", this.stringValue)) {
				fail(ValidationErrorCode.INVALID_NUMBER, this.exceptionMessage);
			}
		}
		return this;
	}

	public InputDSL isInteger() {
		if (this.hasValue) {
			if (!Pattern.matches("^-?\\d+$", this.stringValue)) {
				fail(ValidationErrorCode.INVALID_NUMBER, this.exceptionMessage);
			}
		}
		return this;
	}

	public InputDSL isFloat() {
		if (this.hasValue) {
			if (!Pattern.matches("[-+]?\\d*\\.\\d+", this.stringValue)) {
				fail(ValidationErrorCode.INVALID_NUMBER, this.exceptionMessage);
			}
		}
		return this;
	}

	public InputDSL isBoolean() {
		if (this.hasValue) {
			if (!Pattern.matches("true|false", this.stringValue)) {
				fail(ValidationErrorCode.INVALID_INPUT, this.exceptionMessage);
			}
		}
		return this;
	}

	public InputDSL notEmpty() {
		if (!this.hasValue) {
			fail(ValidationErrorCode.REQUIRED_INPUT, this.exceptionMessage);
		}
		return this;
	}

	public InputDSL notEmpty(String message) {
		if (!this.hasValue) {
			fail(ValidationErrorCode.REQUIRED_INPUT, message);
		}
		return this;
	}

	/**
	 * check the max length of the string to be X
	 * @param length
	 * @return
	 */
	public InputDSL maxLength(int length) {
		if (this.hasValue) {
			if (this.stringValue.length() > length) {
				fail(ValidationErrorCode.INPUT_TOO_LONG, this.exceptionMessage);
			}
		}
		return this;
	}

	/**
	 * check the input es not larger than
	 * @param value
	 * @return
	 */
	public InputDSL max(int value) {
		if (this.hasValue) {
			if (this.asInt() > value) {
				fail(ValidationErrorCode.INPUT_TOO_LONG, this.exceptionMessage);
			}
		}
		return this;
	}

	public InputDSL minLength(int length) {
		if (this.hasValue) {
			if (this.stringValue.length() < length) {
				fail(ValidationErrorCode.INPUT_TOO_SHORT, this.exceptionMessage);
			}
		}
		return this;
	}

	/**
	 * sets a default if not present
	 * @param value
	 * @return
	 */
	public InputDSL withDefault(int value) {
		if (!this.hasValue) {
			this.build(value);
		}
		return this;
	}

	/**
	 * sets a default if not present
	 * @param value
	 * @return
	 */
	public InputDSL withDefault(String value) {
		if (!this.hasValue) {
			this.build(value);
		}
		return this;
	}

	public InputDSL isEmail() {
		if (this.hasValue) {
			if (!this.isValidEmail(this.stringValue)) {
				fail(ValidationErrorCode.INVALID_EMAIL, this.exceptionMessage);
			}
		}
		return this;
	}

	public InputDSL trimFront(String strip) {
		if (this.hasValue) {
			this.stringValue = strings().stripFromFront(this.stringValue, strip);
			this.input = Optional.of(this.stringValue);
		}
		return this;
	}

	/**
	 * trim front and back
	 *
	 * @return
	 */
	public InputDSL trim() {
		if (this.hasValue) {
			this.stringValue = this.stringValue.trim();
			this.input = Optional.of(this.stringValue);
		}
		return this;
	}

	public InputDSL trimBack(String strip) {
		if (this.hasValue) {
			this.stringValue = strings().stripFromEnd(this.stringValue, strip);
			this.input = Optional.of(this.stringValue);
		}
		return this;
	}

	/**
	 * lowercase the input
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Mar 22, 2017
	 */
	public InputDSL lowercase() {
		if (this.hasValue) {
			this.stringValue = this.stringValue.toLowerCase();
			this.input = Optional.of(this.stringValue);
		}
		return this;
	}

	/**
	 * uppercase the input
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Mar 22, 2017
	 */
	public InputDSL uppercase() {
		if (this.hasValue) {
			this.stringValue = this.stringValue.toUpperCase();
			this.input = Optional.of(this.stringValue);
		}
		return this;
	}

	/**
	 * input must be greater than
	 *
	 * @param toCompare
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Sep 9, 2017
	 */
	public InputDSL greaterThan(int toCompare) {
		if (this.hasValue) {
			if (this.asInt() <= toCompare) {
				fail(ValidationErrorCode.INVALID_INPUT, this.exceptionMessage);
			}
		}
		return this;
	}

	public InputDSL lessThan(int toCompare) {
		if (this.hasValue) {
			if (this.asInt() > toCompare) {
				fail(ValidationErrorCode.INVALID_INPUT, this.exceptionMessage);
			}
		}
		return this;
	}

	public InputDSL isDate(String format, String message) {
		if (this.hasValue) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
				LocalDate.parse(this.stringValue, formatter);
			} catch (Exception e) {
				fail(ValidationErrorCode.INVALID_DATE, message);
			}
		}
		return this;

	}

	/**
	 * ask if the date is valid
	 *
	 * @param format
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Oct 28, 2017
	 */
	public InputDSL isDate(String format) {
		return this.isDate(format, this.exceptionMessage);
	}

	/**
	 * get the int value in optional
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 2, 2017
	 */
	public Optional<Integer> getInt() {
		if (this.hasValue) {
			return Optional.of(this.asInt());
		}
		return Optional.empty();
	}

	/**
	 * get the string value in optional
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 2, 2017
	 */
	public Optional<String> getString() {
		if (this.hasValue) {
			return Optional.of(this.asString());
		}
		return Optional.empty();
	}

	/**
	 * check that the original input has a value or the new input has value
	 *
	 * @param value
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 4, 2017
	 */
	public InputDSL or(String value) {
		if (this.hasValue) {
			return this;
		} else {
			if (Optional.ofNullable(value).isEmpty()) {
				fail(ValidationErrorCode.REQUIRED_INPUT, this.exceptionMessage);
			}
		}
		return null;
	}

	/**
	 * will apply HTML cleanup securely
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Dec 3, 2017
	 */
	public InputDSL secure() {
		if (this.hasValue) {
			this.stringValue = html().clean(this.stringValue);
		}
		return this;
	}

	/**
	 * fails if the value is not contained in the source
	 *
	 * @param set
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Dec 25, 2017
	 */
	public InputDSL contains(Set<String> set) {
		if (this.hasValue) {
			if (!set.contains(this.stringValue)) {
				fail(ValidationErrorCode.REQUIRED_INPUT, this.exceptionMessage);
			}
		}
		return this;
	}

	private boolean isValidEmail(String email) {
		email = email.toLowerCase();
		String pattern = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		return email.matches(pattern);
	}

	/**
	 * Validates the ip using a regular expression, also checks the values of each
	 * section (must be between 0 and 255)
	 *
	 * @param ip
	 * @return
	 */
	private boolean isValidIP(String ip) {
		return ip.matches("^((\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
	}

}
