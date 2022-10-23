package com.flab.bigtrader.apppush.infrastructure;

import java.util.concurrent.CountDownLatch;

public interface AppPushClient {

	void sendAppPush(AppPushSendEvent appPushSendEvent, CountDownLatch countDownLatch);
}
