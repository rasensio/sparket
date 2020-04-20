package com.sparket.core.data.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.Optional;

public class Dynamo {

	private static AmazonDynamoDB client;

	static {
		client = AmazonDynamoDBClientBuilder.standard()
				.build();
	}

	/**
	 * gets an object by key
	 * @param table
	 * @param value
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> Optional<T> get(String table, Object value, Class<T> clazz) {
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		T type = mapper.load(clazz, value);
		return Optional.ofNullable(type);
	}

	/**
	 * inserts an object into Dynamo
	 * @param table
	 * @param obj
	 */
	public void insert(String table, Object obj) {
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		mapper.save(obj);
	}
}
