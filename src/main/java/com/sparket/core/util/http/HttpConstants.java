/*
 * WebConstants.java
 * Created on August 16, 2004, 3:38 PM
 */

package com.sparket.core.util.http;

/**
 * @author rasensio
 */
public interface HttpConstants {

	String CONTENT_APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";

	// methods
	String POST = "POST";
	String GET = "GET";
	String PUT = "PUT";
	String DELETE = "DELETE";
	String OPTIONS = "OPTIONS";

	// headers
	String HEADER_USER_AGENT = "User-Agent";
	String HEADER_REFERER = "Referer";
	String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
	String HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	String HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	String HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	String HEADER_ORIGIN = "Origin";
	String HEADER_ACCEPT = "Accept";
	String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
	String HEADER_AUTHORIZATION = "Authorization";
	String HEADER_CACHE_CONTROL = "Cache-control";
	String HEADER_EXPIRES = "Expires";
	String HEADER_CONTENT_TYPE = "Content-Type";

	int STATUS_OK = 200;

	int STATUS_BAD_ARGUMENTS = 400;
	int STATUS_UNAUTHORIZED = 401;
	int STATUS_FORBIDDEN = 403;
	int STATUS_NOT_FOUND = 404;
	int STATUS_MEDIA_NOT_SUPPORTED = 415;

	int STATUS_ERROR = 500;
	int STATUS_SERVICE_UNAVAILABLE = 503;

	// cookies
	int COOKIE_24_HS = 24 * 60 * 60;
	int COOKIE_1_Y = 24 * 60 * 60 * 365;
	int COOKIE_1_M = 24 * 60 * 60 * 30;
	String COOKIE_X_XSRF_TOKEN = "X-XSRF-TOKEN";

	// browsers
	String BROWSER_PHONE = "phone";
	String BROWSER_TABLET = "tablet";
	String BROWSER_DESKTOP = "desktop";
	String BROWSER_UNKNOWN = "unknown";

	// enctypes
	String ENCTYPE_APPLICATION_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	String ENCTYPE_MULTIPART_FORM_DATA = "multipart/form-data";
	String ENCTYPE_TEXT_PLAIN = "text/plain";

	// cache
	String NO_CACHE = "max-age=0, private, no-store, no-cache, must-revalidate";


	String METHOD_OPTIONS = "OPTIONS";
	String METHOD_GET = "GET";
	String METHOD_POST = "POST";
}
