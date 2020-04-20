package com.sparket.core.util.time;

import java.time.Duration;
import java.time.Instant;

public class StopWatch {

	private Instant finish;
	private Instant start;

	public static StopWatch start() {
		var stopWatch = new StopWatch();
		stopWatch.startTimer();
		return stopWatch;
	}

	public void startTimer() {
		this.start = Instant.now();
	}

	public StopWatch stop() {
		this.finish = Instant.now();
		return this;
	}

	public long millis() {
		return Duration.between(this.start, this.finish).toMillis();
	}

	public long seconds() {
		return Duration.between(this.start, this.finish).toSeconds();
	}

}
