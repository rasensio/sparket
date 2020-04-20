package com.sparket.core.util.string;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Base64;

/**
 * @author rasensio
 */
public class StringDSL {

	public static final Map<String, String> DIACRITICS = new HashMap<>();

	private Random RANDOM = new Random();
	private MessageDigest messageDigest;

	static {
		DIACRITICS.put("á", "a");
		DIACRITICS.put("é", "e");
		DIACRITICS.put("í", "i");
		DIACRITICS.put("ó", "o");
		DIACRITICS.put("ú", "u");
		DIACRITICS.put("ñ", "n");
		DIACRITICS.put("Á", "A");
		DIACRITICS.put("É", "E");
		DIACRITICS.put("Í", "I");
		DIACRITICS.put("Ó", "O");
		DIACRITICS.put("Ú", "U");
		DIACRITICS.put("ü", "u");
		DIACRITICS.put("Ü", "U");
		DIACRITICS.put("Ñ", "N");
		DIACRITICS.put("à", "a");
		DIACRITICS.put("è", "e");
		DIACRITICS.put("ì", "i");
		DIACRITICS.put("ò", "o");
		DIACRITICS.put("ù", "u");
	}

	/**
	 * static constructor
	 * 
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Sep 15, 2017
	 */
	public static StringDSL strings() {
		return new StringDSL();
	}

	/**
	 * only allowed internally
	 */
	private StringDSL() {}

	public String asString(int value) {
		return String.valueOf(value);
	}

	public String asString(double value) {
		return String.valueOf(value);
	}

	public String asString(boolean value) {
		return String.valueOf(value);
	}

	public String escapeQuote(String value) {
		StringBuffer sAux = new StringBuffer(0);
		int iLen = value.length();
		for (int iIdx = 0; iIdx < iLen; iIdx++) {
			char c = value.charAt(iIdx);
			if (c == '\'') {
				sAux.append('\'');
			}
			sAux.append(c);
		}
		return sAux.toString();
	}

	public String singleQuoted(String value) {
		int iLen = value.length();
		StringBuffer sAux = new StringBuffer(iLen);
		char quote = '\'';
		sAux.append(quote);
		for (int iIdx = 0; iIdx < iLen; iIdx++) {
			char c = value.charAt(iIdx);
			if (c == quote) {
				sAux.append(quote);
			}
			sAux.append(c);
		}
		sAux.append(quote);
		return sAux.toString();
	}

	public String rightPad(String value, long number, String fill) {
		return rightPad(value, Integer.parseInt(String.valueOf(number)), fill);
	}

	public String rightPad(String zStr, int zLon, String zRelleno) {
		if (zStr == null) zStr = "";
		int iLong = zLon - zStr.length();
		int iLongR = zRelleno.length();
		int i;
		int iRest;

		if (iLong < 0) return zStr;
		StringBuffer aux = new StringBuffer(iLong);
		StringBuffer dest = new StringBuffer(zStr);

		for (i = 0; i < iLong; i++) {
			iRest = i % iLongR;
			aux.append(zRelleno.charAt(iRest));
		}
		dest.append(aux);
		return dest.toString();
	}

	public String zeroLeftPad(String input, int length) {
		return this.leftPad(input, length, "0");
	}

	public String leftPad(String input, int length, String fill) {
		input = input == null ? "" : input;
		int iLong = length - input.length();
		int iLongR = fill.length();
		int i;
		int iRest;

		StringBuffer aux = new StringBuffer(length);

		if (iLong > 0) {
			for (i = 0; i < iLong; i++) {
				iRest = i % iLongR;
				aux.append(fill.charAt(iRest));
			}
		}
		aux.append(input);
		return aux.toString();
	}

	public void memCopy(char zDest[], char zOrig[], int zLen) {
		int iIdx;
		for (iIdx = 0; iIdx < zLen; iIdx++) {
			zDest[iIdx] = zOrig[iIdx];
		}
	}

	/**
	 * Clear all spaces inside or outside the string
	 * 
	 * @param input
	 * @return Author: rasensio Date: Apr 21, 2004
	 */
	public String clearSpaces(String input) {
		String output = "";
		input = input.trim();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ') {
				output += c;
			}
		}
		return output;
	}

	/**
	 * Clear all digits that are not number
	 */
	public String clearNonNumbers(String input) {
		String output = "";
		input = input.trim();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c >= 48 && c <= 57) {
				output += c;
			}
		}
		return output;
	}

	/**
	 * Deletes a string region
	 */
	public String deleteString(String source, String toDelete) {
		return source.replaceAll(toDelete, "");
	}

	/**
	 * Split a string into 2 values
	 */
	public String[] split(String toSplit, String splitter) {
		StringTokenizer token = new StringTokenizer(toSplit, splitter);
		String array[] = new String[2];
		array[0] = token.nextToken();
		array[1] = token.nextToken();
		return array;
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of characters whose ASCII value is
	 * between <code>32</code> and <code>126</code> (inclusive).
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @return the random string
	 */
	public String randomAscii(int count) {
		return random(count, 32, 127, false, false);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of alphabetic characters.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @return the random string
	 */
	public String randomAlphabetic(int count) {
		return random(count, true, false);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of alpha-numeric characters.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @return the random string
	 */
	public String randomAlphanumeric(int count) {
		return random(count, true, true);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of numeric characters.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @return the random string
	 */
	public String randomNumeric(int count) {
		return random(count, false, true);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of alpha-numeric characters as
	 * indicated by the arguments.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @param letters
	 *          if <code>true</code>, generated string will include alphabetic
	 *          characters
	 * @param numbers
	 *          if <code>true</code>, generated string will include numeric
	 *          characters
	 * @return the random string
	 */
	public String random(int count, boolean letters, boolean numbers) {
		return random(count, 0, 0, letters, numbers);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of alpha-numeric characters as
	 * indicated by the arguments.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @param start
	 *          the position in set of chars to start at
	 * @param end
	 *          the position in set of chars to end before
	 * @param letters
	 *          if <code>true</code>, generated string will include alphabetic
	 *          characters
	 * @param numbers
	 *          if <code>true</code>, generated string will include numeric
	 *          characters
	 * @return the random string
	 */
	public String random(int count, int start, int end, boolean letters, boolean numbers) {
		return random(count, start, end, letters, numbers, null, RANDOM);
	}

	/**
	 * <p>
	 * Creates a random string based on a variety of options, using default source
	 * of randomness.
	 * </p>
	 * 
	 * <p>
	 * This method has exactly the same semantics as
	 * {@link #random(int,int,int,boolean,boolean,char[],Random)}, but instead of
	 * using an externally supplied source of randomness, it uses the internal
	 * {@link Random} instance.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @param start
	 *          the position in set of chars to start at
	 * @param end
	 *          the position in set of chars to end before
	 * @param letters
	 *          only allow letters?
	 * @param numbers
	 *          only allow numbers?
	 * @param chars
	 *          the set of chars to choose randoms from. If <code>null</code>,
	 *          then it will use the set of all chars.
	 * @return the random string
	 * @throws ArrayIndexOutOfBoundsException
	 *           if there are not <code>(end - start) + 1</code> characters in the
	 *           set array.
	 */
	public String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars) {
		return random(count, start, end, letters, numbers, chars, RANDOM);
	}

	public String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		if ((start == 0) && (end == 0)) {
			end = 'z' + 1;
			start = ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}

		StringBuffer buffer = new StringBuffer();
		int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if ((letters && numbers && Character.isLetterOrDigit(ch)) || (letters && Character.isLetter(ch)) || (numbers && Character.isDigit(ch)) || (!letters && !numbers)) {
				buffer.append(ch);
			} else {
				count++;
			}
		}
		return buffer.toString();
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of characters specified.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @param chars
	 *          the String containing the set of characters to use, may be null
	 * @return the random string
	 * @throws IllegalArgumentException
	 *           if <code>count</code> &lt; 0.
	 */
	public String random(int count, String chars) {
		if (chars == null) {
			return random(count, 0, 0, false, false, null, RANDOM);
		}
		return random(count, chars.toCharArray());
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of characters specified.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @param chars
	 *          the character array containing the set of characters to use, may
	 *          be null
	 * @return the random string
	 * @throws IllegalArgumentException
	 *           if <code>count</code> &lt; 0.
	 */
	public String random(int count, char[] chars) {
		if (chars == null) {
			return random(count, 0, 0, false, false, null, RANDOM);
		}
		return random(count, 0, chars.length, false, false, chars, RANDOM);
	}

	public List<String> toList(String input, String token) {
		StringTokenizer tokenizer = new StringTokenizer(input, token);
		List<String> toAdd = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			String next = tokenizer.nextToken();
			toAdd.add(next);
		}
		return toAdd;
	}

	public boolean hasUpperCase(String input) {
		Pattern pattern = Pattern.compile("[A-Z]");
		Matcher matcher = pattern.matcher(input);
		boolean found = false;
		while (matcher.find()) {
			found = true;
		}
		return found;
	}

	public boolean hasLowerCase(String input) {
		Pattern pattern = Pattern.compile("[a-z]");
		Matcher matcher = pattern.matcher(input);
		boolean found = false;
		while (matcher.find()) {
			found = true;
		}
		return found;
	}

	public boolean hasAlphaNumberic(String input) {
		Pattern pattern = Pattern.compile("[A-Za-z]");
		Matcher matcher = pattern.matcher(input);
		boolean alphaFound = false;
		while (matcher.find()) {
			alphaFound = true;
		}
		pattern = Pattern.compile("[0-9]");
		matcher = pattern.matcher(input);
		boolean numericFound = false;
		while (matcher.find()) {
			numericFound = true;
		}
		return alphaFound & numericFound;
	}

	public boolean hasSpecialCharacters(String input) {
		Pattern pattern = Pattern.compile("[A-Za-z]");
		Matcher matcher = pattern.matcher(input);
		boolean found = false;
		while (matcher.find()) {
			found = true;
		}
		return found;
	}

	/**
	 * answer if any of the string values is empty or not nulll
	 * 
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Feb 18, 2013
	 * @param values
	 * @return
	 */
	public boolean hasValues(String... values) {
		for (String value : values) {
			if (value == null || value.isEmpty()) return false;
		}
		return true;
	}

	/**
	 * checks if any of the strings has a value
	 * 
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Feb 18, 2013
	 * @param values
	 * @return
	 */
	public boolean hasAtLeastOneValue(String... values) {
		for (String value : values) {
			if (value != null && !value.isEmpty()) return true;
		}
		return false;
	}

	/**
	 * Hash generator
	 * 
	 * @param key
	 * @return
	 */
	public String generateHash(String token, String key) {
		key += token;
		// start fresh
		this.getMessageDigestMD5().reset();
		this.getMessageDigestMD5().update(key.getBytes());
		byte[] bytes = this.getMessageDigestMD5().digest();
		// buffer to write the md5 hash to
		StringBuffer buff = new StringBuffer();
		for (int l = 0; l < bytes.length; l++) {
			String hx = Integer.toHexString(0xFF & bytes[l]);
			// make sure the hex string is correct if 1 character
			if (hx.length() == 1) buff.append("0");
			buff.append(hx);
		}
		return buff.toString().trim();
	}

	/**
	 * executes a MD5 of a string
	 * 
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Dec 20, 2013
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String md5(String key) {
		this.getMessageDigestMD5().reset();
		this.getMessageDigestMD5().update(key.getBytes());
		byte[] bytes = this.getMessageDigestMD5().digest();
		// buffer to write the md5 hash to
		StringBuffer buff = new StringBuffer();
		for (int l = 0; l < bytes.length; l++) {
			String hx = Integer.toHexString(0xFF & bytes[l]);
			// make sure the hex string is correct if 1 character
			if (hx.length() == 1) buff.append("0");
			buff.append(hx);
		}
		return buff.toString().trim();
	}

	/**
	 * Lazy initialization of message digest for MD5
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private MessageDigest getMessageDigestMD5() {
		if (this.messageDigest == null) {
			try {
				this.messageDigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
		return this.messageDigest;
	}

	/**
	 * Converts a <code>String</code> to a <code>boolean</code> value.
	 * 
	 * @param value
	 *          The <code>String</code> to convert.
	 * @return The corresponding <code>boolean</code> value.
	 * @since 1.0
	 */
	public boolean convertToBoolean(String value) {
		if (null == value) {
			return false;
		}

		value = value.trim();
		if (value.equals("1") || value.equalsIgnoreCase("t") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("y") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on")) {
			return true;
		}
		return false;
	}

	/**
	 * Converts all tabs on a line to spaces according to the provided tab width.
	 * 
	 * @param line
	 *          The line whose tabs have to be converted.
	 * @param tabWidth
	 *          The tab width.
	 * @return A new <code>String</code> object containing the line with the
	 *         replaced tabs.
	 * @since 1.0
	 */
	public String convertTabsToSpaces(String line, int tabWidth) {
		StringBuilder result = new StringBuilder();
		int tab_index = -1;
		int last_tab_index = 0;
		int added_chars = 0;
		int tab_size;
		while ((tab_index = line.indexOf("\t", last_tab_index)) != -1) {
			tab_size = tabWidth - ((tab_index + added_chars) % tabWidth);
			if (0 == tab_size) {
				tab_size = tabWidth;
			}
			added_chars += tab_size - 1;
			result.append(line.substring(last_tab_index, tab_index));
			result.append(this.repeat(" ", tab_size));
			last_tab_index = tab_index + 1;
		}
		if (0 == last_tab_index) {
			return line;
		}
		result.append(line.substring(last_tab_index));
		return result.toString();
	}

	/**
	 * Creates a new string that contains the provided string a number of times.
	 * 
	 * @param source
	 *          The string that will be repeated.
	 * @param count
	 *          The number of times that the string will be repeated.
	 * @return A new <code>String</code> object containing the repeated
	 *         concatenation result.
	 * @since 1.0
	 */
	public String repeat(String source, int count) {
		if (null == source) {
			return null;
		}

		StringBuilder new_string = new StringBuilder();
		while (count > 0) {
			new_string.append(source);
			count--;
		}

		return new_string.toString();
	}

	/**
	 * Creates a new array of <code>String</code> objects, containing the elements
	 * of a supplied <code>Iterator</code>.
	 * 
	 * @param iterator
	 *          The iterator containing the elements to create the array with.
	 * @return The new <code>String</code> array.
	 * @since 1.0
	 */
	public String[] toStringArray(Iterator<String> iterator) {
		if (null == iterator) {
			return new String[0];
		}

		ArrayList<String> strings = new ArrayList<String>();

		while (iterator.hasNext()) {
			strings.add(iterator.next());
		}

		String[] string_array = new String[strings.size()];
		strings.toArray(string_array);

		return string_array;
	}

	/**
	 * Creates a new <code>ArrayList</code>, containing the elements of a supplied
	 * array of <code>String</code> objects.
	 * 
	 * @param stringArray
	 *          The array of <code>String</code> objects that have to be
	 *          converted.
	 * @return The new <code>ArrayList</code> with the elements of the
	 *         <code>String</code> array.
	 * @since 1.0
	 */
	public ArrayList<String> toArrayList(String[] stringArray) {
		ArrayList<String> strings = new ArrayList<String>();

		if (null == stringArray) {
			return strings;
		}

		for (String element : stringArray) {
			strings.add(element);
		}

		return strings;
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied <code>Collection</code> of <code>String</code> objects joined by a
	 * given seperator.
	 * 
	 * @param collection
	 *          The <code>Collection</code> containing the elements to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(Collection collection, String seperator) {
		if (null == collection) {
			return null;
		}

		if (null == seperator) {
			seperator = "";
		}

		if (0 == collection.size()) return "";
		StringBuilder result = new StringBuilder();
		for (Object element : collection) {
			result.append(String.valueOf(element));
			result.append(seperator);
		}

		result.setLength(result.length() - seperator.length());
		return result.toString();
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied array, joined by a given seperator.
	 * 
	 * @param array
	 *          The boolean array containing the values to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(boolean[] array, String seperator) {
		if (null == array) {
			return null;
		}

		if (null == seperator) {
			seperator = "";
		}

		if (0 == array.length) return "";
		int current_index = 0;
		String result = "";
		while (current_index < array.length - 1) {
			result = result + array[current_index] + seperator;
			current_index++;
		}

		result = result + array[current_index];
		return result;
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied array, joined by a given seperator.
	 * 
	 * @param array
	 *          The byte array containing the values to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(byte[] array, String seperator) {
		if (null == array) {
			return null;
		}

		if (null == seperator) {
			seperator = "";
		}

		if (0 == array.length) {
			return "";
		} else {
			int current_index = 0;
			String result = "";
			while (current_index < array.length - 1) {
				result = result + array[current_index] + seperator;
				current_index++;
			}

			result = result + array[current_index];
			return result;
		}
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied array, joined by a given seperator.
	 * 
	 * @param array
	 *          The double array containing the values to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(double[] array, String seperator) {
		if (null == array) {
			return null;
		}

		if (null == seperator) {
			seperator = "";
		}

		if (0 == array.length) {
			return "";
		} else {
			int current_index = 0;
			String result = "";
			while (current_index < array.length - 1) {
				result = result + array[current_index] + seperator;
				current_index++;
			}

			result = result + array[current_index];
			return result;
		}
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied array, joined by a given seperator.
	 * 
	 * @param array
	 *          The float array containing the values to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(float[] array, String seperator) {
		if (null == array) {
			return null;
		}

		if (null == seperator) {
			seperator = "";
		}

		if (0 == array.length) {
			return "";
		}
		int current_index = 0;
		String result = "";
		while (current_index < array.length - 1) {
			result = result + array[current_index] + seperator;
			current_index++;
		}

		result = result + array[current_index];
		return result;
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied array, joined by a given seperator.
	 * 
	 * @param array
	 *          The integer array containing the values to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(int[] array, String seperator) {
		if (null == array) {
			return null;
		}

		if (null == seperator) {
			seperator = "";
		}

		if (0 == array.length) {
			return "";
		} else {
			int current_index = 0;
			String result = "";
			while (current_index < array.length - 1) {
				result = result + array[current_index] + seperator;
				current_index++;
			}

			result = result + array[current_index];
			return result;
		}
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied array, joined by a given seperator.
	 * 
	 * @param array
	 *          The long array containing the values to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(long[] array, String seperator) {
		if (null == array) {
			return null;
		}

		if (null == seperator) {
			seperator = "";
		}

		if (0 == array.length) {
			return "";
		} else {
			int current_index = 0;
			String result = "";
			while (current_index < array.length - 1) {
				result = result + array[current_index] + seperator;
				current_index++;
			}

			result = result + array[current_index];
			return result;
		}
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied array, joined by a given seperator.
	 * 
	 * @param array
	 *          The short array containing the values to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(short[] array, String seperator) {
		if (null == array) {
			return null;
		}

		if (null == seperator) {
			seperator = "";
		}

		if (0 == array.length) {
			return "";
		} else {
			int current_index = 0;
			String result = "";
			while (current_index < array.length - 1) {
				result = result + array[current_index] + seperator;
				current_index++;
			}

			result = result + array[current_index];
			return result;
		}
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied array, joined by a given seperator.
	 * 
	 * @param array
	 *          The char array containing the values to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(char[] array, String seperator) {
		return join(array, seperator, null);
	}

	/**
	 * Creates a new <code>String</code> object, containing the elements of a
	 * supplied array, joined by a given seperator.
	 * 
	 * @param array
	 *          The char array containing the values to join.
	 * @param seperator
	 *          The seperator used to join the string elements.
	 * @param delimiter
	 *          The delimiter used to surround the string elements.
	 * @return A new <code>String</code> with the join result.
	 * @since 1.0
	 */
	public String join(char[] array, String seperator, String delimiter) {
		if (null == array) {
			return null;
		}

		if (null == seperator) {
			seperator = "";
		}

		if (null == delimiter) {
			delimiter = "";
		}

		if (0 == array.length) {
			return "";
		}
		int current_index = 0;
		StringBuilder result = new StringBuilder();
		while (current_index < array.length - 1) {
			result.append(delimiter);
			result.append(array[current_index]);
			result.append(delimiter);
			result.append(seperator);
			current_index++;
		}

		result.append(delimiter);
		result.append(String.valueOf(array[current_index]));
		result.append(delimiter);
		return result.toString();
	}

	/**
	 * Returns an array that contains all the occurances of a substring in a
	 * string in the correct order. The search will be performed in a
	 * case-sensitive manner.
	 * 
	 * @param source
	 *          The <code>String</code> object that will be searched in.
	 * @param substring
	 *          The string whose occurances will we counted.
	 * @return An <code>int[]</code> array containing the indices of the
	 *         substring.
	 * @since 1.0
	 */
	public int[] indicesOf(String source, String substring) {
		return indicesOf(source, substring, true);
	}

	/**
	 * Returns an array that contains all the occurances of a substring in a
	 * string in the correct order.
	 * 
	 * @param source
	 *          The <code>String</code> object that will be searched in.
	 * @param substring
	 *          The string whose occurances will we counted.
	 * @param matchCase
	 *          A <code>boolean</code> indicating if the match is going to be
	 *          performed in a case-sensitive manner or not.
	 * @return An <code>int[]</code> array containing the indices of the
	 *         substring.
	 * @since 1.0
	 */
	public int[] indicesOf(String source, String substring, boolean matchCase) {
		if (null == source || null == substring) {
			return new int[0];
		}

		String source_lookup_reference = null;
		if (!matchCase) {
			source_lookup_reference = source.toLowerCase();
			substring = substring.toLowerCase();
		} else {
			source_lookup_reference = source;
		}

		int current_index = 0;
		int substring_index = 0;
		int count = count(source_lookup_reference, substring);
		int[] indices = new int[count];
		int counter = 0;

		while (current_index < source.length() - 1) {
			substring_index = source_lookup_reference.indexOf(substring, current_index);

			if (-1 == substring_index) {
				break;
			} else {
				current_index = substring_index + substring.length();
				indices[counter] = substring_index;
				counter++;
			}
		}

		return indices;
	}

	/**
	 * Matches a collection of regular expressions against a string.
	 * 
	 * @param value
	 *          The <code>String</code> that will be checked.
	 * @param regexps
	 *          The collection of regular expressions against which the match will
	 *          be performed.
	 * @return The <code>Matcher</code> instance that corresponds to the
	 *         <code>String</code> that returned a successful match; or
	 *         <p>
	 *         <code>null</code> if no match could be found.
	 * @since 1.0
	 */
	public Matcher getMatchingRegexp(String value, Collection<Pattern> regexps) {
		if (value != null && value.length() > 0 && regexps != null && regexps.size() > 0) {
			Matcher matcher = null;
			for (Pattern regexp : regexps) {
				matcher = regexp.matcher(value);
				if (matcher.matches()) {
					return matcher;
				}
			}
		}

		return null;
	}

	/**
	 * Matches a collection of strings against a regular expression.
	 * 
	 * @param values
	 *          The <code>Collection</code> of <code>String</code> objects that
	 *          will be checked.
	 * @param regexp
	 *          The regular expression <code>Pattern</code> against which the
	 *          matches will be performed.
	 * @return The <code>Matcher</code> instance that corresponds to the
	 *         <code>String</code> that returned a successful match; or
	 *         <p>
	 *         <code>null</code> if no match could be found.
	 * @since 1.0
	 */
	public Matcher getRegexpMatch(Collection<String> values, Pattern regexp) {
		if (values != null && values.size() > 0 && regexp != null) {
			Matcher matcher = null;
			for (String value : values) {
				matcher = regexp.matcher(value);
				if (matcher.matches()) {
					return matcher;
				}
			}
		}

		return null;
	}

	/**
	 * Checks if the name filters through an including and an excluding regular
	 * expression.
	 * 
	 * @param name
	 *          The <code>String</code> that will be filtered.
	 * @param included
	 *          The regular expressions that needs to succeed
	 * @param excluded
	 *          The regular expressions that needs to fail
	 * @return <code>true</code> if the name filtered through correctly; or
	 *         <p>
	 *         <code>false</code> otherwise.
	 * @since 1.0
	 */
	public boolean filter(String name, Pattern included, Pattern excluded) {
		Pattern[] included_array = null;
		if (included != null) {
			included_array = new Pattern[] { included };
		}

		Pattern[] excluded_array = null;
		if (excluded != null) {
			excluded_array = new Pattern[] { excluded };
		}

		return filter(name, included_array, excluded_array);
	}

	/**
	 * Checks if the name filters through a series of including and excluding
	 * regular expressions.
	 * 
	 * @param name
	 *          The <code>String</code> that will be filtered.
	 * @param included
	 *          An array of regular expressions that need to succeed
	 * @param excluded
	 *          An array of regular expressions that need to fail
	 * @return <code>true</code> if the name filtered through correctly; or
	 *         <p>
	 *         <code>false</code> otherwise.
	 * @since 1.0
	 */
	public boolean filter(String name, Pattern[] included, Pattern[] excluded) {
		if (null == name) {
			return false;
		}

		boolean accepted = false;

		// retain only the includes
		if (null == included) {
			accepted = true;
		} else {
			for (Pattern pattern : included) {
				if (pattern != null && pattern.matcher(name).matches()) {
					accepted = true;
					break;
				}
			}
		}

		// remove the excludes
		if (accepted && excluded != null) {
			for (Pattern pattern : excluded) {
				if (pattern != null && pattern.matcher(name).matches()) {
					accepted = false;
					break;
				}
			}
		}

		return accepted;
	}

	/**
	 * Ensure that the first character of the provided string is upper case.
	 * 
	 * @param source
	 *          The <code>String</code> to capitalize.
	 * @return The capitalized <code>String</code>.
	 * @since 1.0
	 */
	public String capitalize(String source) {
		if (source == null || source.length() == 0) {
			return source;
		}

		if (source.length() > 1 && Character.isUpperCase(source.charAt(0))) {
			return source;
		}

		char chars[] = source.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	/**
	 * Ensure that the first character of the provided string lower case.
	 * 
	 * @param source
	 *          The <code>String</code> to uncapitalize.
	 * @return The uncapitalized <code>String</code>.
	 * @since 1.5
	 */
	public String uncapitalize(String source) {
		if (source == null || source.length() == 0) {
			return source;
		}

		if (source.length() > 1 && Character.isLowerCase(source.charAt(0))) {
			return source;
		}

		char chars[] = source.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}

	/**
	 * Searches for a string within a specified string in a case-sensitive manner
	 * and replaces every match with another string.
	 * 
	 * @param source
	 *          The string in which the matching parts will be replaced.
	 * @param stringToReplace
	 *          The string that will be searched for.
	 * @param replacementString
	 *          The string that will replace each matching part.
	 * @return A new <code>String</code> object containing the replacement result.
	 * @since 1.0
	 */
	public String replace(String source, String stringToReplace, String replacementString) {
		return replace(source, stringToReplace, replacementString, true);
	}

	/**
	 * Searches for a string within a specified string and replaces every match
	 * with another string.
	 * 
	 * @param source
	 *          The string in which the matching parts will be replaced.
	 * @param stringToReplace
	 *          The string that will be searched for.
	 * @param replacementString
	 *          The string that will replace each matching part.
	 * @param matchCase
	 *          A <code>boolean</code> indicating if the match is going to be
	 *          performed in a case-sensitive manner or not.
	 * @return A new <code>String</code> object containing the replacement result.
	 * @since 1.0
	 */
	public String replace(String source, String stringToReplace, String replacementString, boolean matchCase) {
		if (null == source) {
			return null;
		}

		if (null == stringToReplace) {
			return source;
		}

		if (null == replacementString) {
			return source;
		}

		Iterator<String> string_parts = split(source, stringToReplace, matchCase).iterator();
		StringBuilder new_string = new StringBuilder();

		while (string_parts.hasNext()) {
			String string_part = string_parts.next();
			new_string.append(string_part);
			if (string_parts.hasNext()) {
				new_string.append(replacementString);
			}
		}

		return new_string.toString();
	}

	/**
	 * Splits a string into different parts, using a seperator string to detect
	 * the seperation boundaries. The seperator will not be included in the list
	 * of parts.
	 * 
	 * @param source
	 *          The string that will be split into parts.
	 * @param seperator
	 *          The seperator string that will be used to determine the parts.
	 * @param matchCase
	 *          A <code>boolean</code> indicating if the match is going to be
	 *          performed in a case-sensitive manner or not.
	 * @return An <code>ArrayList</code> containing the parts as
	 *         <code>String</code> objects.
	 * @since 1.0
	 */
	public ArrayList<String> split(String source, String seperator, boolean matchCase) {
		ArrayList<String> substrings = new ArrayList<String>();

		if (null == source) {
			return substrings;
		}

		if (null == seperator) {
			substrings.add(source);
			return substrings;
		}

		int current_index = 0;
		int delimiter_index = 0;
		String element = null;

		String source_lookup_reference = null;
		if (!matchCase) {
			source_lookup_reference = source.toLowerCase();
			seperator = seperator.toLowerCase();
		} else {
			source_lookup_reference = source;
		}

		while (current_index <= source_lookup_reference.length()) {
			delimiter_index = source_lookup_reference.indexOf(seperator, current_index);

			if (-1 == delimiter_index) {
				element = new String(source.substring(current_index, source.length()));
				substrings.add(element);
				current_index = source.length() + 1;
			} else {
				element = new String(source.substring(current_index, delimiter_index));
				substrings.add(element);
				current_index = delimiter_index + seperator.length();
			}
		}

		return substrings;
	}

	/**
	 * Splits a string into different parts, using a seperator string to detect
	 * the seperation boundaries in a case-sensitive manner. The seperator will
	 * not be included in the parts array.
	 * 
	 * @param source
	 *          The string that will be split into parts.
	 * @param seperator
	 *          The seperator string that will be used to determine the parts.
	 * @return A <code>String[]</code> array containing the seperated parts.
	 * @since 1.0
	 */
	public String[] splitToArray(String source, String seperator) {
		return splitToArray(source, seperator, true);
	}

	/**
	 * Splits a string into different parts, using a seperator string to detect
	 * the seperation boundaries. The seperator will not be included in the parts
	 * array.
	 * 
	 * @param source
	 *          The string that will be split into parts.
	 * @param seperator
	 *          The seperator string that will be used to determine the parts.
	 * @param matchCase
	 *          A <code>boolean</code> indicating if the match is going to be
	 *          performed in a case-sensitive manner or not.
	 * @return A <code>String[]</code> array containing the seperated parts.
	 * @since 1.0
	 */
	public String[] splitToArray(String source, String seperator, boolean matchCase) {
		ArrayList<String> substrings = split(source, seperator, matchCase);
		String[] substrings_array = new String[substrings.size()];
		substrings_array = substrings.toArray(substrings_array);

		return substrings_array;
	}

	/**
	 * Splits a string into integers, using a seperator string to detect the
	 * seperation boundaries in a case-sensitive manner. If a part couldn't be
	 * converted to an integer, it will be omitted from the resulting array.
	 * 
	 * @param source
	 *          The string that will be split into integers.
	 * @param seperator
	 *          The seperator string that will be used to determine the parts.
	 * @return An <code>int[]</code> array containing the seperated parts.
	 * @since 1.0
	 */
	public int[] splitToIntArray(String source, String seperator) {
		return splitToIntArray(source, seperator, true);
	}

	/**
	 * Splits a string into integers, using a seperator string to detect the
	 * seperation boundaries. If a part couldn't be converted to an integer, it
	 * will be omitted from the resulting array.
	 * 
	 * @param source
	 *          The string that will be split into integers.
	 * @param seperator
	 *          The seperator string that will be used to determine the parts.
	 * @param matchCase
	 *          A <code>boolean</code> indicating if the match is going to be
	 *          performed in a case-sensitive manner or not.
	 * @return An <code>int[]</code> array containing the seperated parts.
	 * @since 1.0
	 */
	public int[] splitToIntArray(String source, String seperator, boolean matchCase) {
		ArrayList<String> string_parts = split(source, seperator, matchCase);
		int number_of_valid_parts = 0;

		for (String string_part : string_parts) {
			try {
				Integer.parseInt(string_part);
				number_of_valid_parts++;
			} catch (NumberFormatException e) {
				// just continue
			}
		}

		int[] string_parts_int = (int[]) Array.newInstance(int.class, number_of_valid_parts);
		int added_parts = 0;

		for (String string_part : string_parts) {
			try {
				string_parts_int[added_parts] = Integer.parseInt(string_part);
				added_parts++;
			} catch (NumberFormatException e) {
				// just continue
			}
		}

		return string_parts_int;
	}

	/**
	 * Splits a string into bytes, using a seperator string to detect the
	 * seperation boundaries in a case-sensitive manner. If a part couldn't be
	 * converted to a <code>byte</code>, it will be omitted from the resulting
	 * array.
	 * 
	 * @param source
	 *          The string that will be split into bytes.
	 * @param seperator
	 *          The seperator string that will be used to determine the parts.
	 * @return A <code>byte[]</code> array containing the bytes.
	 * @since 1.0
	 */
	public byte[] splitToByteArray(String source, String seperator) {
		return splitToByteArray(source, seperator, true);
	}

	/**
	 * Splits a string into bytes, using a seperator string to detect the
	 * seperation boundaries. If a part couldn't be converted to a
	 * <code>byte</code>, it will be omitted from the resulting array.
	 * 
	 * @param source
	 *          The string that will be split into bytes.
	 * @param seperator
	 *          The seperator string that will be used to determine the parts.
	 * @param matchCase
	 *          A <code>boolean</code> indicating if the match is going to be
	 *          performed in a case-sensitive manner or not.
	 * @return A <code>byte[]</code> array containing the bytes.
	 * @since 1.0
	 */
	public byte[] splitToByteArray(String source, String seperator, boolean matchCase) {
		ArrayList<String> string_parts = split(source, seperator, matchCase);
		int number_of_valid_parts = 0;
		for (String string_part : string_parts) {
			try {
				Byte.parseByte(string_part);
				number_of_valid_parts++;
			} catch (NumberFormatException e) {
				// just continue
			}
		}

		byte[] string_parts_byte = (byte[]) Array.newInstance(byte.class, number_of_valid_parts);
		int added_parts = 0;
		for (String string_part : string_parts) {
			try {
				string_parts_byte[added_parts] = Byte.parseByte(string_part);
				added_parts++;
			} catch (NumberFormatException e) {
				// just continue
			}
		}

		return string_parts_byte;
	}

	/**
	 * Removes all occurances of a string from the front of another string in a
	 * case-sensitive manner.
	 * 
	 * @param source
	 *          The string in which the matching will be done.
	 * @param stringToStrip
	 *          The string that will be stripped from the front.
	 * @return A new <code>String</code> containing the stripped result.
	 * @since 1.0
	 */
	public String stripFromFront(String source, String stringToStrip) {
		return stripFromFront(source, stringToStrip, true);
	}

	/**
	 * Removes all occurances of a string from the front of another string.
	 * 
	 * @param source
	 *          The string in which the matching will be done.
	 * @param stringToStrip
	 *          The string that will be stripped from the front.
	 * @param matchCase
	 *          A <code>boolean</code> indicating if the match is going to be
	 *          performed in a case-sensitive manner or not.
	 * @return A new <code>String</code> containing the stripping result.
	 * @since 1.0
	 */
	public String stripFromFront(String source, String stringToStrip, boolean matchCase) {
		if (null == source) {
			return null;
		}

		if (null == stringToStrip) {
			return source;
		}

		int strip_length = stringToStrip.length();
		int new_index = 0;
		int last_index = 0;

		String source_lookup_reference = null;
		if (!matchCase) {
			source_lookup_reference = source.toLowerCase();
			stringToStrip = stringToStrip.toLowerCase();
		} else {
			source_lookup_reference = source;
		}

		new_index = source_lookup_reference.indexOf(stringToStrip);
		if (0 == new_index) {
			do {
				last_index = new_index;
				new_index = source_lookup_reference.indexOf(stringToStrip, new_index + strip_length);
			}
			while (new_index != -1 && new_index == last_index + strip_length);

			return source.substring(last_index + strip_length);
		} else {
			return source;
		}
	}

	/**
	 * Removes all occurances of a string from the end of another string in a
	 * case-sensitive manner.
	 * 
	 * @param source
	 *          The string in which the matching will be done.
	 * @param stringToStrip
	 *          The string that will be stripped from the end.
	 * @return A new <code>String</code> containing the stripped result.
	 * @since 1.0
	 */
	public String stripFromEnd(String source, String stringToStrip) {
		return stripFromEnd(source, stringToStrip, true);
	}

	/**
	 * Removes all occurances of a string from the end of another string.
	 * 
	 * @param source
	 *          The string in which the matching will be done.
	 * @param stringToStrip
	 *          The string that will be stripped from the end.
	 * @param matchCase
	 *          A <code>boolean</code> indicating if the match is going to be
	 *          performed in a case-sensitive manner or not.
	 * @return A new <code>String</code> containing the stripped result.
	 * @since 1.0
	 */
	public String stripFromEnd(String source, String stringToStrip, boolean matchCase) {
		if (null == source) {
			return null;
		}

		if (null == stringToStrip) {
			return source;
		}

		int strip_length = stringToStrip.length();
		int new_index = 0;
		int last_index = 0;

		String source_lookup_reference = null;
		if (!matchCase) {
			source_lookup_reference = source.toLowerCase();
			stringToStrip = stringToStrip.toLowerCase();
		} else {
			source_lookup_reference = source;
		}

		new_index = source_lookup_reference.lastIndexOf(stringToStrip);
		if (new_index != -1 && source.length() == new_index + strip_length) {
			do {
				last_index = new_index;
				new_index = source_lookup_reference.lastIndexOf(stringToStrip, last_index - 1);
			}
			while (new_index != -1 && new_index == last_index - strip_length);

			return source.substring(0, last_index);
		} else {
			return source;
		}
	}

	/**
	 * Ensures that all whitespace is removed from a <code>String</code>.
	 * <p>
	 * It also works with a <code>null</code> argument.
	 * 
	 * @param source
	 *          The <code>String</code> to trim.
	 * @return The trimmed <code>String</code>.
	 * @since 1.0
	 */
	public String trim(String source) {
		if (source == null || source.length() == 0) {
			return source;
		}
		return source.trim();
	}

	/**
	 * Reformats a string where lines that are longer than <tt>width</tt> are
	 * split apart at the earliest wordbreak or at maxLength, whichever is sooner.
	 * If the width specified is less than 5 or greater than the input Strings
	 * length the string will be returned as is.
	 * <p>
	 * Please note that this method can be lossy - trailing spaces on wrapped
	 * lines may be trimmed.
	 * 
	 * @param input
	 *          the String to reformat.
	 * @param width
	 *          the maximum length of any one line.
	 * @return a new String with reformatted as needed.
	 */
	public String wordWrap(String input, int width, Locale locale) {
		// handle invalid input
		if (input == null) {
			return "";
		} else if (width < 5) {
			return input;
		} else if (width >= input.length()) {
			return input;
		}

		// default locale
		if (locale == null) {
			locale = Locale.US;
		}

		StringBuilder buffer = new StringBuilder(input.length());
		int current_index = 0;
		int delimiter_index = 0;
		String seperator = "\n";
		String line;

		// go over the input string and jump from line to line
		while (current_index <= input.length()) {
			// look for the next linebreak
			delimiter_index = input.indexOf(seperator, current_index);

			// get the line that corresponds to it
			if (-1 == delimiter_index) {
				line = new String(input.substring(current_index, input.length()));
				current_index = input.length() + 1;
			} else {
				line = new String(input.substring(current_index, delimiter_index));
				current_index = delimiter_index + seperator.length();
			}

			// handle the wrapping of the line
			BreakIterator breaks = BreakIterator.getLineInstance(locale);
			breaks.setText(line);

			int line_start = 0;
			int start = breaks.first();
			int end = breaks.next();
			while (end != BreakIterator.DONE) {
				// check if the width has been exceeded
				if (end - 1 - line_start >= width) {
					boolean break_line = true;

					// first check if the last characters were spaces,
					// if they were and by removing them the width is not
					// exceeded, just continue
					if (Character.isWhitespace(line.charAt(end - 1))) {
						for (int j = end - 1; j >= 0; j--) {
							if (!Character.isWhitespace(line.charAt(j))) {
								if (j - line_start < width) {
									break_line = false;
								}

								break;
							}
						}
					}

					if (break_line) {
						String line_breaked = line.substring(line_start, start);
						// this can happen with trailing whitespace
						if (line_breaked.length() > width) {
							line_breaked = line_breaked.substring(0, width);
						}
						buffer.append(line_breaked);

						buffer.append("\n");

						line_start = start;
					}
				}

				start = end;
				end = breaks.next();
			}

			if (line_start < line.length()) {
				buffer.append(line.substring(line_start));
			}

			if (delimiter_index != -1) {
				buffer.append("\n");
			}
		}

		return buffer.toString();
	}

	/**
	 * Counts the number of times a substring occures in a provided string in a
	 * case-sensitive manner.
	 * 
	 * @param source
	 *          The <code>String</code> object that will be searched in.
	 * @param substring
	 *          The string whose occurances will we counted.
	 * @return An <code>int</code> value containing the number of occurances of
	 *         the substring.
	 * @since 1.0
	 */
	public int count(String source, String substring) {
		return count(source, substring, true);
	}

	/**
	 * Counts the number of times a substring occures in a provided string.
	 * 
	 * @param source
	 *          The <code>String</code> object that will be searched in.
	 * @param substring
	 *          The string whose occurances will we counted.
	 * @param matchCase
	 *          A <code>boolean</code> indicating if the match is going to be
	 *          performed in a case-sensitive manner or not.
	 * @return An <code>int</code> value containing the number of occurances of
	 *         the substring.
	 * @since 1.0
	 */
	public int count(String source, String substring, boolean matchCase) {
		if (null == source) {
			return 0;
		}

		if (null == substring) {
			return 0;
		}

		int current_index = 0;
		int substring_index = 0;
		int count = 0;

		if (!matchCase) {
			source = source.toLowerCase();
			substring = substring.toLowerCase();
		}

		while (current_index < source.length() - 1) {
			substring_index = source.indexOf(substring, current_index);

			if (-1 == substring_index) {
				break;
			}
			current_index = substring_index + substring.length();
			count++;
		}
		return count;
	}

	/**
	 * converts the title to a seo url
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 *           public String convertSEOURL(String title) throws Exception {
	 *           title = Toolkit.strings().clearHTML(title); title =
	 *           Toolkit.strings().capitalize(title); title =
	 *           Toolkit.sql().cleanSingleQuotes(title); title = title.trim();
	 *           title = title.replaceAll("-", ""); title =
	 *           title.replaceAll("\\s", "99xxxx99"); title =
	 *           title.replaceAll("\\W", ""); title = title.replaceAll("99xxxx99",
	 *           "-"); title = title.replaceAll("--", "-"); return title; }
	 */

	public String clearNewLines(String value) {
		value = value.replaceAll("\\n", "");
		return value.replaceAll("\\r", "");
	}

	/**
	 * replace diacritics for latin language
	 * 
	 * @param input
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 17, 2016
	 */
	public static String replaceDiacritics(String input) {
		for (String key : DIACRITICS.keySet()) {
			input = input.replaceAll(key, DIACRITICS.get(key));
		}
		return input;
	}

	/**
	 * @param input
	 * @return
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Nov 2, 2010
	 */
	public String deleteFirstCharacter(String input) {
		if (input.length() < 2) return input;
		return input.substring(1, input.length());
	}

	/**
	 * @param input
	 * @return
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Nov 2, 2010
	 */
	public String deleteLastCharacter(String input) {
		if (input.length() < 2) return input;
		return input.substring(0, input.length() - 1);
	}

	public String removeRightComma(String input) {
		input = input.trim();
		if (input.endsWith(",")) {
			input = this.deleteLastCharacter(input);
		}
		return input;
	}

	public String toCamelCase(String input) {
		String result = "";
		for (int i = 0; i < input.length(); i++) {
			String next = input.substring(i, i + 1);
			if (i == 0) {
				result += next.toUpperCase();
			} else {
				result += next.toLowerCase();
			}
		}
		return result;
	}

	/**
	 * will extract the string up to the search string
	 * for example, 1-3 if you extractUpTo("1-3", "-")
	 * it will return "1"
	 * @author Rodrigo Asensio
	 * @date May 31, 2011
	 * @param input
	 * @param search
	 * @return
	 */
	public String extractUpTo(String input, String search) {
		int index = input.indexOf(search);
		if (index < 0) return input;
		return input.substring(0, index);
	}

	/**
	 * @author Rodrigo Asensio
	 * @date Jun 1, 2011
	 * @param input
	 * @param search
	 * @return
	 */
	public String extractUpToLast(String input, String search) {
		int index = input.lastIndexOf(search);
		if (index < 0) return input;
		return input.substring(0, index);
	}

	/**
	 * @author Rodrigo Asensio
	 * @date Jun 10, 2011
	 * @param input
	 * @param search
	 * @return
	 */
	public String extractFrom(String input, String search) {
		int index = input.indexOf(search);
		if (index < 0) return input;
		return input.substring(index + 1, input.length());
	}

	public String extractFromLast(String input, String search) {
		int index = input.lastIndexOf(search);
		if (index < 0) return input;
		return input.substring(index + 1, input.length());
	}

	/**
	 * @author Rodrigo Asensio
	 * @date Aug 4, 2011
	 * @param input
	 * @param compare
	 * @return
	 */
	public boolean contains(String input, String compare) {
		return input.indexOf(compare) > -1;
	}

	/**
	 * @author Rodrigo Asensio
	 * @date Mar 4, 2012
	 * @param desc
	 * @param max
	 * @return
	 */
	public String truncate(String desc, int max) {
		if (desc == null) {
			return null;
		} else if (desc.length() <= max) {
			return desc;
		} else {
			return desc.substring(0, max);
		}
	}

	/**
	 * return the first value non null or empty
	 * 
	 * @param values
	 * @return
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Oct 14, 2012
	 */
	public String returnNonEmpty(String... values) {
		for (String value : values) {
			if (value != null && !value.trim().isEmpty()) {
				return value;
			}
		}
		return null;
	}

	/**
	 * creates a unique UUID randomly
	 * 
	 * @return
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Oct 15, 2012
	 */
	public String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * encode base 64.
	 * 
	 * @depends commons-codec
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Dec 25, 2012
	 * @param input
	 * @return
	 */
	public String encodeBase64(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes());
	}

	/**
	 * Decodes base 64
	 * 
	 * @depends commons-codec
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Dec 25, 2012
	 * @param input
	 * @return
	 */
	public String decodeBase64(String input) {
		return new String(Base64.getDecoder().decode(input));
	}

	/**
	 * removes all non alphanumeric characters
	 * 
	 * @author Rodrigo Asensio - @rasensio
	 * @date Jul 7, 2014
	 */
	public static String removeNonAlpha(String input) {
		return input.replaceAll("[\\W]|_", "");
	}

	/**
	 * removes all non alphanumeric chars
	 * 
	 * @param input
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Oct 29, 2016
	 */
	public static String removeNonAlphaNumberic(String input) {
		return input.replaceAll("[^A-Za-z0-9]", "");
	}

	public String removeHyphens(String input) {
		return input.replaceAll("[-_\\+]", "");
	}

	public int countNumbers(String input) {
		return input.replaceAll("\\D", "").length();
	}

	public int countLetters(String input) {
		return input.replaceAll("[^a-zA-Z]", "").length();
	}

	public int countSymbols(String input) {
		return input.replaceAll("[a-zA-Z0-9]", "").length();
	}

	public int countUppercase(String input) {
		return input.replaceAll("[^A-Z]", "").length();
	}

	/**
	 * will remove the part of the string that starts with the match after
	 * 
	 * @param source
	 * @param after
	 * @return
	 */
	public static String removeAfter(String source, String after) {
		if (source == null || after == null) return source;
		int position = source.indexOf(after);
		return source.substring(0, position);
	}

	/**
	 * format a string
	 * @author Rodrigo Asensio - @rasensio
	 * @date Jan 13, 2018
	 * @param template
	 * @param values
	 * @return
	 */
	public String format(String template, Object... values) {
		return String.format(template, values);
	}

}
