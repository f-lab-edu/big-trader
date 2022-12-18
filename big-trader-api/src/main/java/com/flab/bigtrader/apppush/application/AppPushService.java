package com.flab.bigtrader.apppush.application;

import com.flab.bigtrader.apppush.infrastructure.dto.AppPushSendEvent;

public interface AppPushService {

	void sendAppPush(AppPushSendEvent appPushSendEvent);
}
