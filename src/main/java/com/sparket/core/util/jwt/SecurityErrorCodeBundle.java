package com.sparket.core.util.jwt;

import java.util.ListResourceBundle;

public class SecurityErrorCodeBundle extends ListResourceBundle {

	static final Object[][] CONTENTS = { 
		{ "INVALID_JWT", "Error validating jwt token" },
	};
	
	@Override
	protected Object[][] getContents() {
		return CONTENTS;
	}

}
