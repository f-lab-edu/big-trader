package com.flab.bigtrader.stocktrading.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.bigtrader.KafkaProperties;
import com.flab.bigtrader.common.exception.BigTraderBusinessException;
import com.flab.bigtrader.stocktrading.domain.StockTradingEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockKafkaProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	private final KafkaProperties kafkaProperties;

	private final ObjectMapper objectMapper;

	public void sendStockTradingEvent(StockTradingEvent stockTradingEvent) {
		String event = convertJsonOfEvent(stockTradingEvent);
		kafkaTemplate.send(
			kafkaProperties.getTopic(),
			event
		);
	}

	private String convertJsonOfEvent(StockTradingEvent stockTradingEvent) {
		try {
			return objectMapper.writeValueAsString(stockTradingEvent);
		} catch (JsonProcessingException e) {
			throw new BigTraderBusinessException("Json parse error");
		}
	}
}
