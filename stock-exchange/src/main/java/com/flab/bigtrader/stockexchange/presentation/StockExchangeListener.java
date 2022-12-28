package com.flab.bigtrader.stockexchange.presentation;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.bigtrader.stockexchange.application.StockExchangeService;
import com.flab.bigtrader.stockexchange.presentation.dto.StockExchangeMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockExchangeListener {

	private final StockExchangeService stockExchangeService;

	private final ObjectMapper objectMapper;

	@KafkaListener(topics = "${spring.kafka.topic}")
	public void stockExchange(String stock) {
		stockExchangeService.stockExchange(convertJsonToMessage(stock));
	}

	private StockExchangeMessage convertJsonToMessage(String stock) {
		try {
			return objectMapper.readValue(stock, StockExchangeMessage.class);
		} catch (JsonProcessingException exception) {
			throw new RuntimeException("failed to convert json to object");
		}
	}
}
