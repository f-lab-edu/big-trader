package com.flab.bigtrader.apppush.infrastructure;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.flab.bigtrader.common.exception.AppPushServerConnectionException;
import com.flab.bigtrader.common.exception.AppPushServerParameterException;

import io.netty.handler.timeout.ReadTimeoutException;
import reactor.core.publisher.Mono;

@Component
public class DefaultAppPushClient implements AppPushClient {

	private final String appPushServerBaseUrl;

	private WebClient webClient;

	public DefaultAppPushClient(
		@Value("${app.push.base-url}")
		String appPushServerBaseUrl) {
		this.appPushServerBaseUrl = appPushServerBaseUrl;
	}

	@PostConstruct
	private void setUpWebClient() {
		this.webClient = WebClient.create(this.appPushServerBaseUrl);
	}

	public AppPushSendResult sendAppPush(AppPushSendEvent appPushSendEvent) {
		Mono<AppPushSendResult> resultMono = webClient.post()
			.uri("/api/v1/app-push")
			.body(appPushSendEvent, AppPushSendEvent.class)
			.retrieve()
			.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(AppPushServerParameterException::new))
			.onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(AppPushServerConnectionException::new))
			.bodyToMono(AppPushSendResult.class)
			.timeout(Duration.ofSeconds(60))
			.onErrorMap(ReadTimeoutException.class, AppPushServerConnectionException::new);

		return resultMono.block();
	}
}
