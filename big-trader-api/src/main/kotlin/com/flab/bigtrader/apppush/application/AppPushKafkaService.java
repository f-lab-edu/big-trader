package com.flab.bigtrader.apppush.application;

import org.springframework.stereotype.Service;

import com.flab.bigtrader.apppush.infrastructure.AppPushKafkaProducer;
import com.flab.bigtrader.apppush.infrastructure.AppPushSendEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppPushKafkaService implements AppPushService {

	private final AppPushKafkaProducer appPushKafkaProducer;

	@Override
	public void sendAppPush(AppPushSendEvent appPushSendEvent) {
		appPushKafkaProducer.sendAppPush(appPushSendEvent);
	}
}
