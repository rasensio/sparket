package com.sparket.core.util.http;

import java.util.ListResourceBundle;

public class HttpErrorCodeBundle extends ListResourceBundle {

	static final Object[][] CONTENTS = { 
		{ "SERVER_RESPONSE", "The server came back with an error" },
	};
	
	@Override
	protected Object[][] getContents() {
		return CONTENTS;
	}

}
