package com.sparket.core.app;

import com.sparket.core.service.injector.InjectorService;
import com.sparket.core.service.route.RouteService;
import com.sparket.core.util.error.ApplicationException;
import com.sparket.core.util.error.SystemException;

import static com.sparket.core.util.error.ErrorDSL.getMessageForException;
import static com.sparket.core.util.logger.LoggerDSL.logger;
import static spark.Spark.*;

public class Application {

	private static Application app;
	// services
	private InjectorService injectorService;
	private RouteService routeService;

	// config
	private AppProperties appProperties;

	/**
	 * main method
	 *
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		app = new Application();
		app.start();
	}

	/**
	 * will start the application configuring all needed
	 *
	 * @throws Exception
	 */
	private void start() throws Throwable {
		logger().info("Initializing application...");
		logger().info("Loading properties...");
		this.appProperties = new AppProperties();

		logger().info("Setting port...");
		port(5000);
		this.startServices();

		// logs te start, verify the JWT
		before(Filters.cors, Filters.logStart, Filters.validateJwt, Filters.headers);

		options("*", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.header("Access-Control-Allow-Methods", "OPTIONS,GET,POST,PUT,DELETE");
			res.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
			res.header("Access-Control-Allow-Credentials", "true");
			return "";
		});

		// the routes
		this.initRoutes();

		// end request, enablr cors
		after(Filters.logEnd);

		this.handleExceptions();
		logger().info("Application initialized OK");
	}

	private void handleExceptions() {

		// exception management
		exception(ApplicationException.class, (exception, request, response) -> {
			logger().error("application exception", exception);
			String message = getMessageForException(exception);
			String body = ApplicationResponse.build(response)
					.bad(message);
			response.body(body);
		});

		// exception management
		exception(SystemException.class, (exception, request, response) -> {
			logger().error("system exception", exception);
			String message = getMessageForException(exception);
			String body = ApplicationResponse.build(response)
					.error(message);
			response.body(body);
		});

		// exception management
		exception(RuntimeException.class, (exception, request, response) -> {
			logger().error("unhandled exception", exception);
			String message = getMessageForException(exception);
			String body = ApplicationResponse.build(response)
					.error(message);
			response.body(body);
		});
	}

	/**
	 * init the routing service
	 *
	 * @throws Throwable
	 */
	private void initRoutes() throws Throwable {
		this.routeService = new RouteService();
		this.routeService.start();
	}

	/**
	 * start the services
	 */
	private void startServices() throws Throwable {
		this.injectorService = new InjectorService();
		this.injectorService.start();
	}

	public InjectorService injection() {
		return this.injectorService;
	}

	/**
	 * return an environment variable
	 *
	 * @param key
	 * @return
	 */
	public String env(String key) {
		return this.appProperties.env(key, "");

	}

	/**
	 * return a configured property
	 *
	 * @param key
	 * @return
	 */
	public String prop(String key) {
		return this.appProperties.prop(key, "");

	}


	/**
	 * return the application object instance
	 *
	 * @return
	 */
	public static Application app() {
		return app;
	}


}
