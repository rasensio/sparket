package com.sparket.core.util.time;

import com.sparket.core.util.FrameworkDSL;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeDSL extends FrameworkDSL {

	private ZonedDateTime localDate;

	/**
	 * hidden so nobody can call the constructor.
	 * must use dateTime()
	 *
	 * @author Rodrigo Asensio - @rasensio
	 * @date Dec 4, 2017
	 */
	private DateTimeDSL() {
		this.localDate = ZonedDateTime.now();
	}

	/**
	 * default constructor
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 30, 2017
	 */
	public static DateTimeDSL dateTime() {
		return new DateTimeDSL();
	}

	/**
	 * expects iso instant format such as '2011-12-03'.
	 *
	 * @param input
	 */
	public DateTimeDSL(String input) {
		this.localDate = ZonedDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	/**
	 * create the date from an input
	 *
	 * @param input
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 30, 2017
	 */
	public DateTimeDSL fromOffset(String input) {
		this.localDate = ZonedDateTime.parse(input, DateTimeFormatter.ISO_DATE_TIME);
		return this;
	}

	/**
	 * transform to basic iso 8601
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Mar 27, 2017
	 */
	public String asIso() {
		return this.localDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	/**
	 * will transform into iso 8601 offset only for zoned
	 *
	 * @return
	 */
	public String asIsoOffset() {
		return this.localDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	/**
	 * convert the time to epoch with millis
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Apr 19, 2017
	 */
	public long asEpoch() {
		return this.localDate.toInstant().toEpochMilli();
	}


	/**
	 * transform the date to a pattern
	 *
	 * @param pattern
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Mar 27, 2017
	 */
	public String asPattern(String pattern) {
		return this.localDate.format(DateTimeFormatter.ofPattern(pattern));
	}

	public static DateTimeDSL today() {
		return new DateTimeDSL();
	}

	public DateTimeDSL yesterday() {
		this.localDate = this.localDate.minusDays(1);
		return this;
	}

	public DateTimeDSL tomorrow() {
		this.localDate = this.localDate.plusDays(1);
		return this;
	}

	public DateTimeDSL addDays(int days) {
		this.localDate = this.localDate.plusDays(days);
		return this;
	}

	public DateTimeDSL addHours(int hours) {
		this.localDate = this.localDate.plusHours(hours);
		return this;
	}

	public DateTimeDSL addMinutes(int minutes) {
		this.localDate = this.localDate.plusMinutes(minutes);
		return this;
	}

	public DateTimeDSL addMinutes(long minutes) {
		this.localDate = this.localDate.plusMinutes(minutes);
		return this;
	}

	public ZonedDateTime get() {
		return this.localDate;
	}

	/**
	 * convert to local time
	 *
	 * @return
	 */
	public LocalDateTime toLocal() {
		return this.localDate.toLocalDateTime();
	}

	public DateTimeDSL addYears(int years) {
		this.localDate = this.localDate.plusYears(years);
		return this;
	}
}
