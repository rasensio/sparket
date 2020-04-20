package com.sparket.core.app;

import com.sparket.core.util.http.HttpConstants;
import spark.Response;

import static com.sparket.core.util.json.JsonDSL.json;

public class ApplicationResponse {

	private Response response;
	private ResponseBean bean;


	public static ApplicationResponse build(Response response) {
		return new ApplicationResponse(response);
	}

	/**
	 * creates the instance and set defaults
	 *
	 * @param response
	 */
	private ApplicationResponse(Response response) {
		this.bean = new ResponseBean();
		this.response = response;
		this.response.type(HttpConstants.CONTENT_APPLICATION_JSON_UTF8);
	}

	public ApplicationResponse bad() {
		this.response.status(400);
		return this;
	}

	public ApplicationResponse ok() {
		this.response.status(200);
		return this;
	}

	public ApplicationResponse error() {
		this.response.status(500);
		return this;
	}

	private ApplicationResponse forbidden() {
		this.response.status(401);
		return this;
	}

	private ApplicationResponse notFound() {
		this.response.status(404);
		return this;
	}

	public ApplicationResponse data(Object payload) {
		this.bean.setData(payload);
		return this;
	}

	public ApplicationResponse message(String message) {
		this.bean.setMessage(message);
		return this;
	}

	public String ok(Object payload) {
		return this.ok().data(payload).toJson();
	}

	/**
	 * converts the current object to json string
	 *
	 * @return
	 */
	private String toJson() {
		return this.bean.toJson();
	}

	public String bad(String message) {
		return this.bad().message(message).toJson();
	}

	public String error(String message) {
		return this.error().message(message).toJson();
	}

	public String forbidden(String message) {
		return this.forbidden().message(message).toJson();
	}

	public String notFound(String message) {
		return this.notFound().message(message).toJson();
	}



	static class ResponseBean {
		private String message;
		private Object data;

		public String getMessage() {
			return message;
		}

		public Object getData() {
			return data;
		}

		/**
		 * converts the current object to json string
		 *
		 * @return
		 */
		private String toJson() {
			return json().toJson(this);
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public void setData(Object data) {
			this.data = data;
		}
	}
}
