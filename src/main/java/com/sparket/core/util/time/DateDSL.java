package com.sparket.core.util.time;

import com.sparket.core.util.FrameworkDSL;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class DateDSL extends FrameworkDSL {

	private LocalDate localDate;

	private DateDSL() {
		this.localDate = LocalDate.now();
	}

	/**
	 * expects iso instant format such as '2011-12-03'.
	 *
	 * @param input
	 */
	private DateDSL(String input) {
		this.localDate = LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
	}

	/**
	 * default constructor
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Nov 30, 2017
	 */
	public static DateDSL date() {
		return new DateDSL();
	}

	/**
	 * transform to basic iso 8601
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Mar 27, 2017
	 */
	public String asIso() {
		return this.localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	/**
	 * convert the time to epoch with millis
	 *
	 * @return
	 * @author Rodrigo Asensio - @rasensio
	 * @date Apr 19, 2017
	 */
	public long asEpoch() {
		ZoneId zoneId = ZoneId.systemDefault();
		return this.localDate.atStartOfDay(zoneId).toInstant().toEpochMilli();
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

	public DateDSL yesterday() {
		this.localDate = this.localDate.minusDays(1);
		return this;
	}

	public DateDSL tomorrow() {
		this.localDate = this.localDate.plusDays(1);
		return this;
	}

	public DateDSL addDays(int days) {
		this.localDate = this.localDate.plusDays(days);
		return this;
	}

	public DateDSL minusDays(int days) {
		this.localDate = this.localDate.minusDays(days);
		return this;
	}

	public LocalDate get() {
		return this.localDate;
	}

	public DateDSL daysAgo(int days) {
		this.addDays(-days);
		return this;
	}

	public DateDSL addYears(int years) {
		this.localDate = this.localDate.plusYears(years);
		return this;
	}
}
