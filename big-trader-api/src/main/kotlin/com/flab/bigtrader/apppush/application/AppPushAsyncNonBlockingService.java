package com.flab.bigtrader.apppush.application;

import java.util.concurrent.CountDownLatch;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.flab.bigtrader.apppush.infrastructure.AppPushClient;
import com.flab.bigtrader.apppush.infrastructure.AppPushSendEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppPushAsyncNonBlockingService implements AppPushService {

	private static final int TOTAL_LOOP_COUNT = 450_000;

	private final AppPushClient nonBlockingAppPushClient;

	@Async
	@Override
	public void sendAppPush(AppPushSendEvent appPushSendEvent) {
		CountDownLatch countDownLatch = new CountDownLatch(TOTAL_LOOP_COUNT);

		long start = System.currentTimeMillis();
		log.info("시작시간: {}", start);

		for (int i = 0; i < TOTAL_LOOP_COUNT; i++) {
			nonBlockingAppPushClient.sendAppPush(appPushSendEvent, countDownLatch);
		}

		long end = System.currentTimeMillis();

		awaitTotalLoopEnd(countDownLatch);

		log.info("종료시간: {}", end);
		log.info("총 시간: {}초", end - start);
	}

	private void awaitTotalLoopEnd(CountDownLatch countDownLatch) {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			log.info("전제 루프 종료");
		}
	}
}
