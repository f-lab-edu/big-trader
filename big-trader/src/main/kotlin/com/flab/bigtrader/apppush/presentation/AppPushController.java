package com.flab.bigtrader.apppush.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flab.bigtrader.apppush.infrastructure.AppPushClient;
import com.flab.bigtrader.apppush.infrastructure.AppPushSendResult;
import com.flab.bigtrader.apppush.presentation.dto.AppPushRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class AppPushController {

	private final AppPushClient appPushClient;

	@PostMapping("/messages")
	@ResponseStatus(HttpStatus.OK)
	public void sendAppPush(@RequestBody AppPushRequest appPushRequest) {
		AppPushSendResult appPushSendResult = appPushClient.sendAppPush(appPushRequest.toAppPushSendEvent());

		if (log.isDebugEnabled()) {
			log.debug("현재 앱푸시 전달 개수: {}", appPushSendResult.getCount());
		}
	}
}
