package com.flab.bigtrader.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flab.bigtrader.common.response.CommonErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonErrorResponse handleBusinessException(BusinessException ex) {
		return CommonErrorResponse.of(ex.getMessage());
	}

	@ExceptionHandler(AppPushServerParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonErrorResponse handleAppPushServerException(AppPushServerParameterException ex) {
		return CommonErrorResponse.of(ex.getMessage());
	}

	@ExceptionHandler(AppPushServerConnectionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handleAppPushServerConnectionException(
		AppPushServerConnectionException ex) {
		log.error(ex.getMessage());
	}

	@ExceptionHandler(UnSupportedAppPushOperationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonErrorResponse handleUnSupportedAppPushOperationException(UnSupportedAppPushOperationException ex) {
		return CommonErrorResponse.of(ex.getMessage());
	}
}
