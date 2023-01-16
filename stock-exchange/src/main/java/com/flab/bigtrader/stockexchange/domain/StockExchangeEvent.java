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

	public Optional<StockExchangeEvent> exchange(Long count) {
		if (isGreaterThan(count)) {
			long resultExchangeCount = this.count - count;

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

		if (isLessThan(count)) {
			long resultExchangeCount = count - this.count;

			return Optional.of(
				new StockExchangeEvent(
					this.id,
					this.name,
					this.price,
					resultExchangeCount,
					this.tradingType.getReverseType()
				)
			);
		}

		return Optional.empty();
	}

	private boolean isGreaterThan(Long count) {
		return this.count > count;
	}

	private boolean isLessThan(Long count) {
		return this.count < count;
	}
}
