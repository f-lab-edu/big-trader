package com.flab.bigtrader.stocktrading.application;

import org.springframework.stereotype.Service;

import com.flab.bigtrader.stocktrading.application.dto.StockTradingCommand;
import com.flab.bigtrader.stocktrading.domain.StockTradingEvent;
import com.flab.bigtrader.stocktrading.domain.StockTradingService;
import com.flab.bigtrader.stocktrading.infrastructure.kafka.StockKafkaProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockTradingFacade {

	private final StockTradingService stockTradingService;

	private final StockKafkaProducer stockKafkaProducer;

	public String requestStockTrading(StockTradingCommand stockTradingCommand) {
		StockTradingEvent stockTradingEvent = stockTradingService.requestStockTrading(stockTradingCommand);
		stockKafkaProducer.sendStockTradingEvent(stockTradingEvent);

		return stockTradingEvent.getId();
	}
}
