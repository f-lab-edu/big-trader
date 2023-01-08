package com.flab.bigtrader.stockexchange.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.flab.bigtrader.stockexchange.domain.StockExchangeEvent;
import com.flab.bigtrader.stockexchange.infrastructure.redis.StockExchangeRedis;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockExchangeService {

	private final StockExchangeRedis stockExchangeRedis;

	public void stockExchange(StockExchangeEvent stockExchangeEvent) {
		// RUNTIME EXCEPTION 추후 변경 예정
		Optional<StockExchangeEvent> optionalStockEvent = stockExchangeRedis.findStockEvent(
			stockExchangeEvent.generateReverseKey());

		if (optionalStockEvent.isEmpty()) {
			stockExchangeRedis.pushEventOnRight(stockExchangeEvent);
			return;
		}

		StockExchangeEvent findStockEvent = optionalStockEvent.get();

		if (findStockEvent.isSameCount(stockExchangeEvent.getCount())) {
			return;
		}
	}
}
