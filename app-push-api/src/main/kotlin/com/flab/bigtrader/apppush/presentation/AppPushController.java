package com.flab.bigtrader.apppush.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flab.bigtrader.apppush.common.AppPushRequestCounter;
import com.flab.bigtrader.apppush.presentation.dto.AppPushSendResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class AppPushController {

	private final AppPushRequestCounter appPushRequestCounter;

	@PostMapping("/app-push")
	@ResponseStatus(HttpStatus.OK)
	public AppPushSendResponse sendMessage() {
		long count = appPushRequestCounter.increaseAndGet();

		return new AppPushSendResponse(count);
	}
}
