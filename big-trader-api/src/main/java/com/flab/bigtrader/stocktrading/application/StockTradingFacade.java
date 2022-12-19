package com.flab.bigtrader.stocktrading.application;

import org.springframework.stereotype.Service;

import com.flab.bigtrader.stocktrading.application.dto.StockTradingCommand;
import com.flab.bigtrader.stocktrading.domain.StockTradingBuyEvent;
import com.flab.bigtrader.stocktrading.domain.StockTradingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockTradingFacade {

	private final StockTradingService stockTradingService;

	public String requestStockTrading(StockTradingCommand stockTradingCommand) {
		StockTradingBuyEvent stockTradingBuyEvent = stockTradingService.requestStockTrading(stockTradingCommand);

		return stockTradingBuyEvent.getId();
	}
}
