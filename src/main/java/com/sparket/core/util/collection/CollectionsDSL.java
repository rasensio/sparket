package com.sparket.core.util.collection;

import com.sparket.core.util.FrameworkDSL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CollectionsDSL extends FrameworkDSL {

	/**
	 * static constructor
	 * 
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Sep 15, 2017
	 */
	public static CollectionsDSL collections() {
		return new CollectionsDSL();
	}

	/**
	 * only allowed internally
	 */
	private CollectionsDSL() {}

	public Map<String, String> map(String key, String value) {
		Map<String, String> map = new HashMap<>();
		map.put(key, value);
		return map;
	}

	/**
	 * create a generic map
	 * 
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Apr 15, 2017
	 */
	public <K, V> Map<K, V> map() {
		return new HashMap<>();
	}

	/**
	 * creates a generic list of items
	 * 
	 * @param items
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Apr 15, 2017
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> list(T... items) {
		return Arrays.asList(items);
	}

	/**
	 * return a lisst from comma separated values
	 * 
	 * @param branches
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @return
	 * @date Sep 15, 2017
	 */
	public ArrayList<String> list(String values) {
		if (values == null || values.isEmpty()) {
			return new ArrayList<String>();
		}
		return new ArrayList<String>(Arrays.asList(values.split(",")));

	}

	/**
	 * creates a generic list
	 * 
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Apr 15, 2017
	 */
	public <T> List<T> list() {
		return new ArrayList<>();
	}

	/**
	 * convert a generic list to String list
	 * 
	 * @param toConvert
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Mar 14, 2017
	 */
	public <T> List<String> toStringList(List<T> toConvert) {
		return toConvert.stream()
				.map(Object::toString)
				.collect(Collectors.toList());
	}

	/**
	 * conver the list to a list of integers
	 * 
	 * @param toConvert
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Apr 15, 2017
	 */
	public <T> List<Integer> toIntList(List<T> toConvert) {
		return toConvert.stream()
				.map(Object::toString)
				.map(Integer::parseInt)
				.collect(Collectors.toList());
	}

	/**
	 * create batches of lists
	 * 
	 * @param source
	 * @param length
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Apr 20, 2017
	 */
	public <T> Stream<List<T>> batches(List<T> source, int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("length = " + length);
		}
		int size = source.size();
		if (size <= 0) {
			return Stream.empty();
		}
		int fullChunks = (size - 1) / length;
		return IntStream.range(0, fullChunks + 1)
				.mapToObj(n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
	}

	/**
	 * transform list of values into array
	 * 
	 * @param values
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Sep 15, 2017
	 */
	public String[] arrayString(String... values) {
		return values;
	}
	
	/**
	 * answer if a value is declared into the enumeration
	 * 
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Jan 24, 2014
	 * @param e
	 * @param value
	 * @return
	 */
	public <E extends Enum<E>> boolean contains(Class<E> e, Object value) {
		return EnumSet.allOf(e).contains(value);
	}

	/**
	 * return true if the toFind parameter is found in the array
	 * 
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Feb 24, 2014
	 * @param array
	 * @param toFind
	 * @return
	 */
//	public boolean contains(String[] array, String toFind) {
//		return ArrayUtils.contains(array, toFind);
//	}

	/**
	 * returns true if any item is the same
	 * 
	 * @author Rodrigo Asensio - @rasensio
	 * @date Dec 20, 2014
	 */
	public boolean hasAnyMatch(List<String> source, List<String> toCompare) {
		for (String each : source) {
			for (String compare : toCompare) {
				if (each.equals(compare)) return true;
			}
		}
		return false;
	}

	/**
	 * returns the intersection of both lists
	 * @param pivot
	 * @param toCompare
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 6, 2016
	 */
	public List<Integer> intersect(List<Integer> pivot, List<Integer> toCompare) {
		return pivot.stream()
				.filter(toCompare::contains)
				.collect(Collectors.toList());
	
	}

	/**
	 * returns true if all sources values match with all values
	 * 
	 * @author Rodrigo Asensio - rasensio@gmail.com
	 * @date Aug 30, 2013
	 * @param sources
	 * @param values
	 * @return
	 */
	public boolean matchAllValues(List<Integer> sources, int... values) {
		for (int source : sources) {
			for (int value : values) {
				if (source != value) return false;
			}
		}
		return true;
	}

	public List<String> newStringList(String... items) {
		List<String> list = new ArrayList<String>();
		for (String each : items) {
			list.add(each);
		}
		return list;
	}	
	
	/**
	 * convert the array to a list
	 * 
	 * @param data
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Oct 13, 2016
	 */
	public <T> List<T> toList(T data[]) {
		return Arrays.asList(data);
	}

}
