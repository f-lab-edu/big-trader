package com.flab.bigtrader.apppush.infrastructure;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.flab.bigtrader.apppush.presentation.dto.AppPushSendRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppPushKafkaConsumer {

	@KafkaListener(topics = "${spring.kafka.topic}")
	public void sendMessage(AppPushSendRequest appPushSendRequest) {
		log.info("consumer send message {}", appPushSendRequest.getMessage());
	}
}
