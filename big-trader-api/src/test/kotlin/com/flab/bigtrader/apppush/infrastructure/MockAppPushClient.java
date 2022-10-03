package com.flab.bigtrader.apppush.infrastructure;

import java.util.concurrent.atomic.AtomicLong;

import com.flab.bigtrader.common.exception.AppPushServerParameterException;

public class MockAppPushClient implements AppPushClient {

	private final AtomicLong atomicLong;

	public MockAppPushClient() {
		this.atomicLong = new AtomicLong();
	}

	@Override
	public AppPushSendResult sendAppPush(AppPushSendEvent appPushSendEvent) {
		String message = appPushSendEvent.getMessage();

		if (message.isBlank()) {
			throw new AppPushServerParameterException();
		}

		return new AppPushSendResult(atomicLong.incrementAndGet());
	}
}
