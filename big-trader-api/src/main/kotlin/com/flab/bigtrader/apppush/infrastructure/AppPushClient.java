package com.flab.bigtrader.apppush.infrastructure;

public interface AppPushClient {

	AppPushSendResult sendAppPush(AppPushSendEvent appPushSendEvent);
}
