package com.flab.bigtrader.apppush.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flab.bigtrader.apppush.application.AppPushService;
import com.flab.bigtrader.apppush.presentation.dto.AppPushRequest;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v2")
@RestController
@RequiredArgsConstructor
public class KafkaAppPushController {

	private final AppPushService appPushKafkaService;

	@PostMapping("/messages")
	@ResponseStatus(HttpStatus.OK)
	public void sendAppPush(@RequestBody AppPushRequest appPushRequest) {
		appPushKafkaService.sendAppPush(appPushRequest.toAppPushSendEvent());
	}
}
