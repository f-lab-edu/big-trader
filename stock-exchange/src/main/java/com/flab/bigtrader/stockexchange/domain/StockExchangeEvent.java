package com.flab.bigtrader.stockexchange.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flab.bigtrader.stockexchange.presentation.dto.TradingType;

import lombok.Getter;

@Getter
public class StockExchangeEvent {

	private final String id;

	private final String name;

	private final Long price;

	private final Long count;

	private final TradingType tradingType;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private final TradingStatus tradingStatus;

	public StockExchangeEvent(
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

	public String generateKey() {
		return this.getName() + "-" + this.getPrice() + "-" + this.getTradingType();
	}
}
