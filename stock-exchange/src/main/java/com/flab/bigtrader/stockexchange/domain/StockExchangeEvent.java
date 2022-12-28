package com.flab.bigtrader.stockexchange.domain;

import com.flab.bigtrader.stockexchange.presentation.dto.TradingType;

import lombok.Getter;

@Getter
public class StockExchangeEvent {

	private final String id;

	private final String name;

	private final Long price;

	private final TradingType tradingType;

	private final ExchangeStatus exchangeStatus;

	public StockExchangeEvent(String id, String name, Long price, TradingType tradingType) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.tradingType = tradingType;
		this.exchangeStatus = ExchangeStatus.WAITING;
	}

	public StockExchangeEvent(String id, String name, Long price, TradingType tradingType,
		ExchangeStatus exchangeStatus) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.tradingType = tradingType;
		this.exchangeStatus = exchangeStatus;
	}

	public String generateKey() {
		return this.getName() + "-" + this.getPrice() + "-" + this.getTradingType();
	}
}
