package com.flab.bigtrader.stockexchange.common.exception;

public class StockExchangeBusinessException extends RuntimeException {

	public StockExchangeBusinessException(String message) {
		super(message);
	}

	public StockExchangeBusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
