package com.flab.bigtrader.apppush.presentation;

import com.flab.bigtrader.apppush.application.AppPushService;
import com.flab.bigtrader.apppush.presentation.dto.AppPushRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class AppPushController {

    private final AppPushService appPushService;

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public void sendAppPush(@RequestBody AppPushRequest appPushRequest) throws InterruptedException {
        appPushService.sendAppPush(appPushRequest.toAppPushSendEvent());
    }
}
