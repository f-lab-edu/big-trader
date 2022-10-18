package com.flab.bigtrader.apppush.application;

import com.flab.bigtrader.apppush.infrastructure.AppPushClient;
import com.flab.bigtrader.apppush.infrastructure.AppPushSendEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppPushService {

    private final AppPushClient appPushClient;

    @Async
    public void sendAppPush(AppPushSendEvent appPushSendEvent) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(450_000);

        long start = System.currentTimeMillis();
        log.info("시작시간: {}", start);
        for (int i = 0; i < 450_000; i++) {
            appPushClient.sendAppPush(appPushSendEvent, countDownLatch);
        }

        long end = System.currentTimeMillis();

        countDownLatch.await();
        log.info("종료시간: {}", end);
        log.info("총 시간: {}초", end - start);
    }
}
