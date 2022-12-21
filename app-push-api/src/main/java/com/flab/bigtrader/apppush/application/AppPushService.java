package com.flab.bigtrader.apppush.application;

import org.springframework.stereotype.Service;

import com.flab.bigtrader.apppush.infrastructure.AppPushKafkaConsumer;
import com.flab.bigtrader.apppush.presentation.dto.AppPushSendRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppPushService {

	private final AppPushKafkaConsumer appPushKafkaConsumer;

	public void sendMessage(AppPushSendRequest appPushSendRequest) {
		appPushKafkaConsumer.sendMessage(appPushSendRequest);
	}
}
