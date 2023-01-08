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

	public void stockExchange(StockExchangeEvent requestStockExchangeEvent) {
		// RUNTIME EXCEPTION 추후 변경 예정
		Optional<StockExchangeEvent> optionalStockExchangeEvent = stockExchangeRedis.findStockEvent(
			requestStockExchangeEvent.generateReverseKey());

		if (optionalStockExchangeEvent.isEmpty()) {
			stockExchangeRedis.pushEventOnRight(requestStockExchangeEvent);
			return;
		}

		//TODO: optional get 사용안하도록 변경해야함
		StockExchangeEvent findStockExchangeEvent = optionalStockExchangeEvent.get();

		if (findStockExchangeEvent.isGoe(requestStockExchangeEvent.getCount())) {
			findStockExchangeEvent.exchangeGoe(requestStockExchangeEvent.getCount())
				.ifPresent(stockExchangeRedis::pushEventOnRight);
			return;
		}

	}
}
