package com.sparket.api.user;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import static com.sparket.core.util.string.StringDSL.strings;

@DynamoDBTable(tableName = "RasensioUser")
public class User {

	private String email;
	private String userId;
	private String name;
	private String avatar;
	private String provider;
	private String providerToken;
	private String role;
	private String lastLogin;

	@DynamoDBAttribute(attributeName = "userId")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@DynamoDBAttribute(attributeName = "name")
	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	@DynamoDBAttribute(attributeName = "provider")
	public String getProvider() {
		return provider;
	}

	public User setProvider(String provider) {
		this.provider = provider;
		return this;
	}

	@DynamoDBAttribute(attributeName = "providerToken")
	public String getProviderToken() {
		return providerToken;
	}

	public User setProviderToken(String providerToken) {
		this.providerToken = providerToken;
		return this;
	}

	@DynamoDBHashKey(attributeName = "email")
	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	@DynamoDBAttribute(attributeName = "role")
	public String getRole() {
		return this.role;
	}

	public User setRole(String role) {
		this.role = role;
		return this;
	}

	@DynamoDBAttribute(attributeName = "lastLogin")
	public String getLastLogin() {
		return this.lastLogin;
	}

	public User setLastLogin(String isoDate) {
		this.lastLogin = isoDate;
		return this;
	}

	public User newId() {
		this.userId = strings().randomAlphanumeric(10);
		return this;
	}

	@DynamoDBAttribute(attributeName = "avatar")
	public String getAvatar() {
		return avatar;
	}

	public User setAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}
}
