package com.flab.bigtrader.stockexchange.infrastructure.redis;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.bigtrader.stockexchange.domain.StockExchangeEvent;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockExchangeRedis {

	private final StringRedisTemplate redisTemplate;

	private final ObjectMapper objectMapper;

	/*
	 * TODO
	 * popEventOnLeft 로직 작성
	 * */

	public void pushEventOnRight(StockExchangeEvent stockExchangeEvent) {
		ListOperations<String, String> operations = redisTemplate.opsForList();
		operations.rightPush(stockExchangeEvent.generateKey(), convertEventToJson(stockExchangeEvent));
	}

	private String convertEventToJson(StockExchangeEvent stockExchangeEvent) {
		try {
			return objectMapper.writeValueAsString(stockExchangeEvent);
		} catch (JsonProcessingException exception) {
			throw new RuntimeException("parse error");
		}
	}
}
