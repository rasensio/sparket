/**
 * @author Rodrigo Asensio - @rasensio
 * @date Mar 11, 2016
 */
package com.sparket.core.service.injector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sparket.core.service.Service;
import com.sparket.core.service.logger.LoggerEnum;

public class InjectorService extends Service {

	private Injector injector;

	@Override
	public void start() throws Throwable {
		this.injector = Guice.createInjector(new InjectionModule());
	}

	@Override
	public void stop() throws Throwable {
		this.injector = null;
	}

	/**
	 * will build the implementation of the class thru the injection engine
	 * 
	 * @author Rodrigo Asensio - @rasensio
	 * @date Mar 11, 2016
	 * @param clazz
	 * @return
	 */
	public <T> T build(Class<T> clazz) {
		return this.injector.getInstance(clazz);
	}

	@Override
	public LoggerEnum getLogger() {
		return LoggerEnum.INJECTION;
	}



}
