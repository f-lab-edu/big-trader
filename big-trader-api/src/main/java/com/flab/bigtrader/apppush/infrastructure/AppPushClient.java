package com.flab.bigtrader.apppush.infrastructure;

import java.util.concurrent.CountDownLatch;

import com.flab.bigtrader.apppush.infrastructure.dto.AppPushSendEvent;

public interface AppPushClient {

	void sendAppPush(AppPushSendEvent appPushSendEvent, CountDownLatch countDownLatch);
}
