package com.flab.bigtrader.stockexchange.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.bigtrader.KafkaProperties;
import com.flab.bigtrader.stockexchange.common.exception.JsonConvertException;
import com.flab.bigtrader.stockexchange.domain.StockExchangeEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockKafkaProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	private final KafkaProperties kafkaProperties;

	private final ObjectMapper objectMapper;

	public void sendStockTradingEvent(StockExchangeEvent stockExchangeEvent) {
		String event = convertJsonOfEvent(stockExchangeEvent);
		kafkaTemplate.send(
			kafkaProperties.getTopic(),
			event
		);
	}

	private String convertJsonOfEvent(StockExchangeEvent stockTradingEvent) {
		try {
			return objectMapper.writeValueAsString(stockTradingEvent);
		} catch (JsonProcessingException exception) {
			log.error("json parse error", exception);
			throw new JsonConvertException("Json parse error");
		}
	}
}
