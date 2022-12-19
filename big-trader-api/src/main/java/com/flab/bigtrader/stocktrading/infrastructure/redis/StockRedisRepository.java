package com.flab.bigtrader.stocktrading.infrastructure.redis;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.bigtrader.common.exception.BusinessException;
import com.flab.bigtrader.stocktrading.domain.StockTradingEvent;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockRedisRepository {

	private final RedisTemplate<String, String> redisTemplate;

	private final ObjectMapper objectMapper;

	public void saveStockEvent(StockTradingEvent stockTradingEvent) {
		String event = convertJsonOfEvent(stockTradingEvent);

		ListOperations<String, String> listOperations = redisTemplate.opsForList();

		//TODO: redis 저장 못했을 경우 retry, backup data 저장소 필요
		listOperations.rightPush(createKey(stockTradingEvent), event);
	}

	private String createKey(StockTradingEvent stockTradingEvent) {
		return stockTradingEvent.getName()
			+ "-"
			+ stockTradingEvent.getPrice()
			+ "-"
			+ stockTradingEvent.getTradingType().name();
	}

	private String convertJsonOfEvent(StockTradingEvent stockTradingEvent) {
		try {
			return objectMapper.writeValueAsString(stockTradingEvent);
		} catch (JsonProcessingException e) {
			throw new BusinessException("Json parse error");
		}
	}

}
