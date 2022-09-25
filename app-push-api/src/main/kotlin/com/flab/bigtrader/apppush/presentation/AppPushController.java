package com.flab.bigtrader.apppush.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class AppPushController {

    @GetMapping("/app-push")
    @ResponseStatus(HttpStatus.OK)
    public void dummyApi() {

    }
}
