package com.flab.bigtrader.apppush.presentation;

import com.flab.bigtrader.apppush.application.AppPushOperation;
import com.flab.bigtrader.apppush.application.AppPushService;
import com.flab.bigtrader.apppush.presentation.dto.AppPushRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            @RequestBody AppPushRequest appPushRequest)  {
        appPushServiceMap.get(operator)
                .sendAppPush(appPushRequest.toAppPushSendEvent());
    }
}
