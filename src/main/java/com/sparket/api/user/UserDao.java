package com.sparket.api.user;


import com.sparket.core.app.Application;
import com.sparket.core.data.dynamodb.Dynamo;

import javax.inject.Inject;
import java.util.Optional;

public class UserDao {

	private static String USER_TABLE;

	static {
		USER_TABLE = Application.app().prop("user.table");
	}
	@Inject
	private Dynamo dynamo;

	public Optional<User> getUser(String email) {
		return this.dynamo.get(USER_TABLE, email, User.class);
	}

	/**
	 * updates the user
	 * @param user
	 */
	public void updateUser(User user) {
		this.dynamo.insert(USER_TABLE, user);
	}

	public void insertUser(User user) {
		this.dynamo.insert(USER_TABLE, user);
	}
}
