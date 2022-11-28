package com.flab.bigtrader.apppush.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.flab.bigtrader.apppush.presentation.dto.AppPushSendRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppPushService {

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void sendMessage(AppPushSendRequest appPushSendRequest) {
		log.info("consumer send message {}", appPushSendRequest.getMessage());
	}
}
