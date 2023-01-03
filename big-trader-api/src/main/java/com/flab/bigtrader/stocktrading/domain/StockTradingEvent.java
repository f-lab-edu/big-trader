package com.flab.bigtrader.stocktrading.domain;

import com.flab.bigtrader.stocktrading.presentation.dto.TradingType;

import lombok.Getter;

@Getter
public class StockTradingEvent {

	private final String id;

	private final String name;

	private final Long price;

	private final Long count;

	private final TradingType tradingType;

	private final TradingStatus tradingStatus;

	public StockTradingEvent(
		String id,
		String name,
		Long price,
		Long count,
		TradingType tradingType) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.count = count;
		this.tradingType = tradingType;
		this.tradingStatus = TradingStatus.WAITING;
	}
}
