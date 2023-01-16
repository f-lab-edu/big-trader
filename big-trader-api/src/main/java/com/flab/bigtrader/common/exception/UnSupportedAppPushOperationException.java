package com.flab.bigtrader.common.exception;

public class UnSupportedAppPushOperationException extends BigTraderBusinessException {

	private static final String MESSAGE = "지원하지 않은 작업입니다.";

	public UnSupportedAppPushOperationException() {
		super(MESSAGE);
	}
}
