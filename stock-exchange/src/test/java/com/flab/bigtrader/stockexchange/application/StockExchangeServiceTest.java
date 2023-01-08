package com.flab.bigtrader.stockexchange.application;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

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
		매도_요청_저장(stockName);

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

		assertThat(stockEvent.isPresent()).isFalse();
	}

	private String createUniqueId() {
		return UUID.randomUUID().toString();
	}

	private void 매도_요청_저장(String stockName) {
		StockExchangeEvent sellStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			stockName,
			52000L,
			3L,
			TradingType.SELL
		);

		stockExchangeRedis.pushEventOnRight(sellStockExchangeEvent);
	}

}
