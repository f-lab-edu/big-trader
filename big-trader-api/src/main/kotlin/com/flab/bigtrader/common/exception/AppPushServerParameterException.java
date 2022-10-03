package com.flab.bigtrader.common.exception;

public class AppPushServerParameterException extends BusinessException {

	private static final String MESSAGE = "잘못된 요청 파라미터입니다.";

	public AppPushServerParameterException() {
		super(MESSAGE);
	}
}
