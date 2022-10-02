package com.flab.bigtrader.common;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.flab.bigtrader.apppush.infrastructure.AppPushClient;
import com.flab.bigtrader.apppush.infrastructure.MockAppPushClient;

@TestConfiguration
public class IntegrationTestConfiguration {

	@Primary
	@Bean
	public AppPushClient appPushClient() {
		return new MockAppPushClient();
	}
}
