package com.flab.bigtrader.stockexchange.domain;

import java.util.Optional;

import com.flab.bigtrader.stockexchange.presentation.dto.TradingType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockExchangeEvent {

	private String id;

	private String name;

	private Long price;

	private Long count;

	private TradingType tradingType;

	public StockExchangeEvent(String id, String name, Long price, Long count, TradingType tradingType) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.count = count;
		this.tradingType = tradingType;
	}

	public String generateKey() {
		return this.name + "-" + this.price + "-" + this.getTradingType();
	}

	public String generateReverseKey() {
		return this.name + "-" + this.price + "-" + this.tradingType.getReverseType();
	}

	public boolean isSameCount(Long count) {
		return this.count.equals(count);
	}

	public boolean isGoe(Long count) {
		return this.count >= count;
	}

	//TODO : 자기기준으로 클때
	public Optional<StockExchangeEvent> exchangeGoe(Long count) {
		long resultExchangeCount = this.count - count;

		if (resultExchangeCount == 0L) {
			return Optional.empty();
		}

		return Optional.of(
			new StockExchangeEvent(
				this.id,
				this.name,
				this.price,
				resultExchangeCount,
				this.tradingType
			)
		);
	}
}
