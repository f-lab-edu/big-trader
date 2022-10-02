package com.flab.bigtrader.apppush.common;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

@Component
public class AppPushRequestCounter {

	private final AtomicLong atomicLong;

	public AppPushRequestCounter() {
		atomicLong = new AtomicLong();
	}

	public long increaseAndGet() {
		return atomicLong.incrementAndGet();
	}
}
