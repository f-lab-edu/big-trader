package com.flab.bigtrader.stockexchange.application;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flab.bigtrader.common.IntegrationTest;
import com.flab.bigtrader.stockexchange.domain.StockExchangeEvent;
import com.flab.bigtrader.stockexchange.infrastructure.redis.StockExchangeRedis;
import com.flab.bigtrader.stockexchange.presentation.dto.TradingType;

class StockExchangeServiceTest extends IntegrationTest {

	@Autowired
	private StockExchangeRedis stockExchangeRedis;

	@Autowired
	private StockExchangeService stockExchangeService;

	@AfterEach
	void clearData() {
		flushAllInRedis();
	}

	@DisplayName("매수요청일때_체결할 수 있는 매도요청 이벤트가 없을경우 체결에 실패하고 저장된다")
	@Test
	void 매수요청일때_체결할_수_있는_매도요청_이벤트가_없을경우_체결에_실패하고_저장된다() {
		String stockName = "SANSUNG";

		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			stockName,
			52000L,
			3L,
			TradingType.BUY
		);

		stockExchangeService.stockExchange(buyStockExchangeEvent);

		Optional<StockExchangeEvent> stockEvent = stockExchangeRedis.findStockEvent(
			buyStockExchangeEvent.generateKey());

		assertThat(stockEvent.isPresent()).isTrue();
	}

	@DisplayName("매수요청일때 체결할 수 있는 매도요청 이벤트의 개수가 같을 경우 체결에 성공한다")
	@Test
	void 매수요청일때_체결할_수_있는_매도요청_이벤트의_개수가_같을_경우_체결에_성공한다() {
		String stockName = "SANSUNG";
		Long count = 3L;
		매도_요청_저장(stockName, count);

		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			stockName,
			52000L,
			count,
			TradingType.BUY
		);

		stockExchangeService.stockExchange(buyStockExchangeEvent);

		Optional<StockExchangeEvent> stockEvent = stockExchangeRedis.findStockEvent(
			buyStockExchangeEvent.generateKey());

		assertThat(stockEvent.isPresent()).isFalse();
	}

	@DisplayName("매수요청일때 체결할 수 있는 매도요청의 개수가 많을 경우 남은 개수의 매도요청은 저장된다")
	@Test
	void 매수요청일때_체결할_수_있는_매도요청의_개수가_많을_경우_남은_개수의_매도요청은_저장된다() {
		String stockName = "SANSUNG";
		Long buyCount = 3L;
		Long sellCount = 5L;
		StockExchangeEvent sellStockExchangeEvent = 매도_요청_저장(stockName, sellCount);

		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			stockName,
			52000L,
			buyCount,
			TradingType.BUY
		);

		stockExchangeService.stockExchange(buyStockExchangeEvent);

		Optional<StockExchangeEvent> stockEvent = stockExchangeRedis.findStockEvent(
			sellStockExchangeEvent.generateKey());

		Assertions.assertAll(
			() -> assertThat(stockEvent.isPresent()).isTrue(),
			() -> assertThat(stockEvent.get().getCount()).isEqualTo(sellCount - buyCount)
		);
	}

	private String createUniqueId() {
		return UUID.randomUUID().toString();
	}

	private StockExchangeEvent 매도_요청_저장(String stockName, Long count) {
		StockExchangeEvent sellStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			stockName,
			52000L,
			count,
			TradingType.SELL
		);

		stockExchangeRedis.pushEventOnRight(sellStockExchangeEvent);
		return sellStockExchangeEvent;
	}

}
