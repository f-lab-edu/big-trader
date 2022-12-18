package com.flab.bigtrader.apppush.infrastructure;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.flab.bigtrader.apppush.infrastructure.dto.AppPushSendEvent;
import com.flab.bigtrader.common.exception.AppPushServerConnectionException;
import com.flab.bigtrader.common.exception.AppPushServerParameterException;

import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Slf4j
@Component
public class NonBlockingAppPushClient implements AppPushClient {

	private final String appPushServerBaseUrl;

	private WebClient webClient;

	public NonBlockingAppPushClient(
		@Value("${app.push.base-url}")
		String appPushServerBaseUrl) {
		this.appPushServerBaseUrl = appPushServerBaseUrl;
	}

	@PostConstruct
	private void setUpWebClient() {
		ConnectionProvider connectionProvider = ConnectionProvider.builder("non-blocking-connection-provider")
			.maxIdleTime(Duration.ofSeconds(20))
			.evictInBackground(Duration.ofSeconds(30))
			.pendingAcquireMaxCount(-1)
			.maxIdleTime(Duration.ofSeconds(80))
			.lifo()
			.build();

		ReactorClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(
			HttpClient.create(connectionProvider));

		this.webClient = WebClient.builder()
			.baseUrl(this.appPushServerBaseUrl)
			.clientConnector(clientHttpConnector)
			.build();
	}

	@Async
	@Override
	public void sendAppPush(AppPushSendEvent appPushSendEvent, CountDownLatch countDownLatch) {
		webClient.post()
			.uri("/api/v1/app-push")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(appPushSendEvent)
			.retrieve()
			.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(AppPushServerParameterException::new))
			.onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(AppPushServerConnectionException::new))
			.toBodilessEntity()
			.timeout(Duration.ofSeconds(80))
			.onErrorMap(ReadTimeoutException.class, AppPushServerConnectionException::new)
			.subscribe(result -> progressSuccess(countDownLatch), e -> log.error("실패", e.getCause()));
	}

	private static void progressSuccess(CountDownLatch countDownLatch) {
		countDownLatch.countDown();
		log.info("성공");
	}
}
