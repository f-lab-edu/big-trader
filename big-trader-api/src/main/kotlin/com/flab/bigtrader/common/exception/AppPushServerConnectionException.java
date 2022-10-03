package com.flab.bigtrader.common.exception;

public class AppPushServerConnectionException extends BusinessException {

	private static final String MESSAGE = "앱 푸쉬 서버 에러입니다.";

	public AppPushServerConnectionException() {
		super(MESSAGE);
	}

	public AppPushServerConnectionException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
