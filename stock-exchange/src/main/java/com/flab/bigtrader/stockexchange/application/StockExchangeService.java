package com.flab.bigtrader.stockexchange.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flab.bigtrader.stockexchange.infrastructure.redis.StockExchangeRedis;
import com.flab.bigtrader.stockexchange.presentation.dto.StockExchangeMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockExchangeService {

	private final StockExchangeRedis stockExchangeRedis;

	public void stockExchange(StockExchangeMessage stockExchangeMessage) {
		String exchangeId = UUID.randomUUID().toString();
		stockExchangeRedis.pushEventOnRight(stockExchangeMessage.toEvent(exchangeId));
	}
}
