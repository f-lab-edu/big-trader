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
	
	public void stockExchange(final StockExchangeEvent requestStockExchangeEvent) {
		//TODO: RUNTIME EXCEPTION 추후 변경 예정
		Optional<StockExchangeEvent> optionalStockExchangeEvent = stockExchangeRedis.findStockEvent(
			requestStockExchangeEvent.generateReverseKey());

		if (optionalStockExchangeEvent.isEmpty()) {
			stockExchangeRedis.pushEventOnRight(requestStockExchangeEvent);
			return;
		}

		StockExchangeEvent findStockExchangeEvent = optionalStockExchangeEvent.get();

		if (findStockExchangeEvent.isGoe(requestStockExchangeEvent.getCount())) {
			findStockExchangeEvent.exchange(requestStockExchangeEvent.getCount())
				.ifPresent(stockExchangeRedis::pushEventOnRight);

			return;
		}

		requestStockExchangeEvent.exchange(findStockExchangeEvent.getCount())
			.ifPresent(this::executeExchangeLoop);

	}

	private void executeExchangeLoop(StockExchangeEvent requestStockExchangeEvent) {
		while (true) {
			Optional<StockExchangeEvent> optionalStockExchangeEvent = stockExchangeRedis.findStockEvent(
				requestStockExchangeEvent.generateReverseKey());

			if (optionalStockExchangeEvent.isEmpty()) {
				stockExchangeRedis.pushEventOnRight(requestStockExchangeEvent);
				return;
			}

			Optional<StockExchangeEvent> resultStockExchange = requestStockExchangeEvent.exchange(
				optionalStockExchangeEvent.get().getCount());

			if (resultStockExchange.isEmpty()) {
				return;
			}

			requestStockExchangeEvent = resultStockExchange.get();
		}
	}
}
