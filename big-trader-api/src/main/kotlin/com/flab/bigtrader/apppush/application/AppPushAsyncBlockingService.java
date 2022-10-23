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
public class AppPushAsyncBlockingService implements AppPushService {

	private static final int TOTAL_LOOP_COUNT = 300_000;

	private final AppPushClient blockingAppPushClient;

	@Async
	@Override
	public void sendAppPush(AppPushSendEvent appPushSendEvent) {
		CountDownLatch countDownLatch = new CountDownLatch(TOTAL_LOOP_COUNT);

		long start = System.currentTimeMillis();

		for (int i = 0; i < TOTAL_LOOP_COUNT; i++) {
			blockingAppPushClient.sendAppPush(appPushSendEvent, countDownLatch);
		}

		awaitTotalLoopEnd(countDownLatch);

		long end = System.currentTimeMillis();
		log.info("소요시간: {} 초", toSecond(start, end));
	}

	private void awaitTotalLoopEnd(CountDownLatch countDownLatch) {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			log.info("전제 루프 종료");
		}
	}

	private static long toSecond(long start, long end) {
		return (end - start) / 1000;
	}
}
