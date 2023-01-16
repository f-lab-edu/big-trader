package com.flab.bigtrader.stockexchange.infrastructure.redis;

import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.bigtrader.stockexchange.common.exception.JsonConvertException;
import com.flab.bigtrader.stockexchange.domain.StockExchangeEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StockExchangeRedis {

	private final RedisScript<String> sellFindRedisScript;

	private final RedisTemplate<String, String> redisTemplate;

	private final ObjectMapper objectMapper;

	public void pushEventOnRight(StockExchangeEvent stockExchangeEvent) {
		ListOperations<String, String> operations = redisTemplate.opsForList();
		operations.rightPush(stockExchangeEvent.generateKey(), convertEventToJson(stockExchangeEvent));
	}

	public Optional<StockExchangeEvent> findStockEvent(String stockKey) {
		String findStockEvent = redisTemplate.execute(sellFindRedisScript, List.of(stockKey));

		if (findStockEvent == null) {
			return Optional.empty();
		}

		return Optional.of(convertJsonToEvent(findStockEvent));
	}

	private String convertEventToJson(StockExchangeEvent stockExchangeEvent) {
		try {
			return objectMapper.writeValueAsString(stockExchangeEvent);
		} catch (JsonProcessingException exception) {
			log.error("json parse error", exception);
			throw new JsonConvertException("parse error");
		}
	}

	private StockExchangeEvent convertJsonToEvent(String stockExchangeEvent) {
		try {
			return objectMapper.readValue(stockExchangeEvent, StockExchangeEvent.class);
		} catch (JsonProcessingException exception) {
			log.error("json to object convert error", exception);
			throw new JsonConvertException("json to object convert error");
		}
	}
}
