package com.sparket.core.util.json;

import java.util.ListResourceBundle;

public class JsonErrorCodeBundle extends ListResourceBundle {

	static final Object[][] CONTENTS = { 
		{ "PARSING_ERROR", "Error parsing Json" },
	};
	
	@Override
	protected Object[][] getContents() {
		return CONTENTS;
	}

}
