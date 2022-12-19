package com.flab.bigtrader.stocktrading.domain;

import com.flab.bigtrader.stocktrading.presentation.dto.TradingType;

import lombok.Getter;

@Getter
public class StockTradingBuyEvent {

	private final String id;

	private final String name;

	private final Long price;

	private final TradingType tradingType;

	private final TradingStatus tradingStatus;

	public StockTradingBuyEvent(
		String id,
		String name,
		Long price,
		TradingType tradingType) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.tradingType = tradingType;
		this.tradingStatus = TradingStatus.WAITING;
	}
}
