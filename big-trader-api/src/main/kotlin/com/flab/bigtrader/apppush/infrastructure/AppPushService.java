package com.flab.bigtrader.apppush.infrastructure;

import java.util.concurrent.CountDownLatch;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.flab.bigtrader.apppush.presentation.dto.AppPushRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppPushService {

	private final AppPushClient appPushClient;

	private final static int loopCount = 300_000;

	@Async
	public void sendMessages(AppPushRequest appPushRequest) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(loopCount);

		long start = System.currentTimeMillis();

		for (int i = 0; i < loopCount; i++) {
			appPushClient.sendAppPush(appPushRequest.toAppPushSendEvent(),
				countDownLatch);
		}
		countDownLatch.await();

		long end = System.currentTimeMillis();
		log.info("소요시간: {} 초", (end - start) / 1000);
	}
}
