package com.flab.bigtrader.apppush.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flab.bigtrader.apppush.presentation.dto.AppPushSendRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1")
@RestController
public class AppPushController {

	@PostMapping("/app-push")
	@ResponseStatus(HttpStatus.OK)
	public void sendMessage(@RequestBody AppPushSendRequest appPushSendRequest) {
	}
}
