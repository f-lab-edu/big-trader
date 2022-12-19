package com.flab.bigtrader.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.test.context.TestConfiguration;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@Slf4j
@TestConfiguration
public class EmbeddedRedisConfig {

	private static final int EMBEDDED_REDIS_PORT = 6300;

	private RedisServer redisServer;

	@PostConstruct
	public void startRedisServer() {
		redisServer = new RedisServer(EMBEDDED_REDIS_PORT);

		log.info("start redis server");

		redisServer.start();
	}

	@PreDestroy
	public void stopRedisServer() {
		log.info("stop redis server");
		redisServer.stop();
		log.info("redis stop complete");
	}
}
