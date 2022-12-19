package com.flab.bigtrader.stocktrading.domain;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flab.bigtrader.stocktrading.application.dto.StockTradingCommand;
import com.flab.bigtrader.stocktrading.infrastructure.StockRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockTradingService {

	private final StockRedisRepository stockRedisRepository;

	public StockTradingBuyEvent requestStockTrading(StockTradingCommand stockTradingCommand) {
		//TODO: UUID 추후 식별할 수 있는 유저 id로 변경예정
		StockTradingBuyEvent stockTradingBuyEvent = stockTradingCommand.toEvent(UUID.randomUUID().toString());
		stockRedisRepository.saveStockBuyEvent(stockTradingBuyEvent);

		return stockTradingBuyEvent;
	}
}
