package com.flab.bigtrader.stockexchange.presentation;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.flab.bigtrader.stockexchange.application.StockExchangeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockExchangeListener {

	private final StockExchangeService stockExchangeService;

	@KafkaListener(topics = "${spring.kafka.topic}")
	public void stockExchange(String stock) {
		log.info("{}", stock);
	}
}
