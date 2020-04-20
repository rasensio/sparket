package com.sparket.core.service.route;

import com.sparket.core.app.ApplicationApi;
import com.sparket.core.app.Application;
import com.sparket.core.service.Service;
import com.sparket.core.service.logger.LoggerEnum;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.util.List;

public class RouteService extends Service {

	/**
	 * will discover all childs of ApplicationApi an start their routes
	 * @throws Throwable
	 */
	@Override
	public void start() throws Throwable {
		String routes = Application.app().prop("routes");
		this.info("loading routes from " + routes);
		List<Class<?>> controlClassRefs;
		try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo()
				.whitelistPackages(routes).scan()) {

			ClassInfoList controlClasses = result.getSubclasses(ApplicationApi.class.getName());
			controlClassRefs = controlClasses.loadClasses();
		}
		for (Class<?> clazz : controlClassRefs) {
			ApplicationApi api = (ApplicationApi) Application.app().injection().build(clazz);
			this.info("starting routes for " + api.toString());
			api.initRoutes();
		}
	}

	@Override
	public void stop() throws Throwable {

	}

	@Override
	public LoggerEnum getLogger() {
		return LoggerEnum.ROUTES;
	}
}
