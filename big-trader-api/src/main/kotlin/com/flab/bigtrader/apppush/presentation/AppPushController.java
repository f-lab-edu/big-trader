package com.flab.bigtrader.apppush.presentation;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flab.bigtrader.apppush.application.AppPushOperation;
import com.flab.bigtrader.apppush.application.AppPushService;
import com.flab.bigtrader.apppush.presentation.dto.AppPushRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class AppPushController {

	private final Map<AppPushOperation, AppPushService> appPushServiceMap;

	@PostMapping("/messages/{operator}")
	@ResponseStatus(HttpStatus.OK)
	public void sendAppPush(
		@PathVariable AppPushOperation operator,
		@RequestBody AppPushRequest appPushRequest) {
		appPushServiceMap.get(operator)
			.sendAppPush(appPushRequest.toAppPushSendEvent());
	}
}
