package com.flab.bigtrader.apppush.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<AppPushSendResponse> sendMessage() {
		long count = appPushRequestCounter.increaseAndGet();

		return ResponseEntity.ok()
			.body(new AppPushSendResponse(count));
	}
}
