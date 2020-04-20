package com.sparket.core.app;

public class UserToken {

	private String userId;
	private String name;
	private String email;
	private String providerToken;
	private String provider;
	private String scopes;

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getProviderToken() {
		return providerToken;
	}

	public String getProvider() {
		return provider;
	}

	public String getScopes() {
		return scopes;
	}
}