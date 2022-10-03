package com.flab.bigtrader.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flab.bigtrader.common.CommonErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<CommonErrorResponse> handleBusinessException(BusinessException ex) {
		return ResponseEntity.badRequest()
			.body(CommonErrorResponse.of(ex.getMessage()));
	}

	@ExceptionHandler(AppPushServerParameterException.class)
	public ResponseEntity<CommonErrorResponse> handleAppPushServerException(AppPushServerParameterException ex) {
		return ResponseEntity.badRequest()
			.body(CommonErrorResponse.of(ex.getMessage()));
	}

	@ExceptionHandler(AppPushServerConnectionException.class)
	public ResponseEntity<Void> handleAppPushServerConnectionException(
		AppPushServerConnectionException ex) {
		log.error(ex.getMessage());
		ex.printStackTrace();

		return ResponseEntity.ok()
			.build();
	}
}
