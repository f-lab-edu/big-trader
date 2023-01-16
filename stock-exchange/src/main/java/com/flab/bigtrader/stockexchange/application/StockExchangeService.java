package com.flab.bigtrader.stockexchange.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.flab.bigtrader.stockexchange.domain.StockExchangeEvent;
import com.flab.bigtrader.stockexchange.infrastructure.kafka.StockKafkaProducer;
import com.flab.bigtrader.stockexchange.infrastructure.redis.StockExchangeRedis;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockExchangeService {

	private final StockExchangeRedis stockExchangeRedis;

	private final StockKafkaProducer stockKafkaProducer;

	public void stockExchange(final StockExchangeEvent requestStockExchangeEvent) {
		Optional<StockExchangeEvent> optionalStockExchangeEvent = stockExchangeRedis.findStockEvent(
			requestStockExchangeEvent.generateReverseKey());

		if (optionalStockExchangeEvent.isEmpty()) {
			stockExchangeRedis.pushEventOnRight(requestStockExchangeEvent);
			return;
		}

		StockExchangeEvent findStockExchangeEvent = optionalStockExchangeEvent.get();

		findStockExchangeEvent.exchange(requestStockExchangeEvent.getCount())
			.ifPresent(stockKafkaProducer::sendStockTradingEvent);
	}
}
