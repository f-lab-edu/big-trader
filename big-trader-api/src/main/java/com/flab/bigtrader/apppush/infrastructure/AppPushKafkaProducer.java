package com.flab.bigtrader.apppush.infrastructure;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.flab.bigtrader.KafkaProperties;
import com.flab.bigtrader.apppush.infrastructure.dto.AppPushSendEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppPushKafkaProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	private final KafkaProperties kafkaProperties;

	public AppPushKafkaProducer(
		KafkaProperties kafkaProperties,
		KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
		this.kafkaProperties = kafkaProperties;
	}

	public void sendAppPush(AppPushSendEvent appPushSendEvent) {
		log.info("kafka send message");
		kafkaTemplate.send(kafkaProperties.getTopic(), appPushSendEvent);
	}
}
