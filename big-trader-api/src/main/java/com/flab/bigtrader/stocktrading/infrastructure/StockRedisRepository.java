package com.flab.bigtrader.stocktrading.infrastructure;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.bigtrader.common.exception.BusinessException;
import com.flab.bigtrader.stocktrading.domain.StockTradingBuyEvent;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockRedisRepository {

	private final RedisTemplate<String, String> redisTemplate;

	private final ObjectMapper objectMapper;

	public void saveStockBuyEvent(StockTradingBuyEvent stockTradingBuyEvent) {
		String buyEvent = convertJsonOfBuyEvent(stockTradingBuyEvent);

		ListOperations<String, String> listOperations = redisTemplate.opsForList();

		//TODO: redis 저장 못했을 경우 retry, backup data 저장소 필요
		listOperations.rightPush(createKey(stockTradingBuyEvent), buyEvent);
	}

	private String createKey(StockTradingBuyEvent stockTradingBuyEvent) {
		return stockTradingBuyEvent.getName()
			+ "-"
			+ stockTradingBuyEvent.getPrice()
			+ "-"
			+ stockTradingBuyEvent.getTradingType().name();
	}

	private String convertJsonOfBuyEvent(StockTradingBuyEvent stockTradingBuyEvent) {
		try {
			return objectMapper.writeValueAsString(stockTradingBuyEvent);
		} catch (JsonProcessingException e) {
			throw new BusinessException("Json parse error");
		}
	}

}
