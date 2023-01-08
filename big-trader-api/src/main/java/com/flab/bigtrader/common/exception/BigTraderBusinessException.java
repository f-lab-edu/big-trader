package com.flab.bigtrader.common.exception;

public class BigTraderBusinessException extends RuntimeException {

	public BigTraderBusinessException(String message) {
		super(message);
	}

	public BigTraderBusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
