package com.flab.bigtrader.apppush.application;

import com.flab.bigtrader.apppush.infrastructure.AppPushSendEvent;

public interface AppPushService {

    void sendAppPush(AppPushSendEvent appPushSendEvent);
}
