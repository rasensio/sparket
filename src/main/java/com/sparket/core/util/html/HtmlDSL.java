package com.sparket.core.util.html;

import com.sparket.core.util.FrameworkDSL;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class HtmlDSL extends FrameworkDSL {

	public static HtmlDSL html() {
		return new HtmlDSL();
	}

	private HtmlDSL() {
	}

	/**
	 * sanitize html input from the user
	 * @param input
	 * @return
	 */
	public String clean(String input) {
		return Jsoup.clean(input, Whitelist.basic());
	}
}
