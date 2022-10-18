package com.flab.bigtrader.apppush.presentation;

import com.flab.bigtrader.apppush.presentation.dto.AppPushSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1")
@RestController
public class AppPushController {

    @PostMapping("/app-push")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(@RequestBody AppPushSendRequest appPushSendRequest) {
    }
}
