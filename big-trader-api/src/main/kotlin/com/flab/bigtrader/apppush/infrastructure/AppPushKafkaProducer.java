package com.flab.bigtrader.apppush.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppPushKafkaProducer {

	private final String topic;

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public AppPushKafkaProducer(
		@Value("${app.push.topic}") String topic,
		KafkaTemplate<String, Object> kafkaTemplate) {
		this.topic = topic;
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendAppPush(AppPushSendEvent appPushSendEvent) {
		log.info("kafka send message");
		kafkaTemplate.send(topic, appPushSendEvent);
	}
}
