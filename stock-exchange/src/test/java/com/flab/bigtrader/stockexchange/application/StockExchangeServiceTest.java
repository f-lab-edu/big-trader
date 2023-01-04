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

	@DisplayName("체결할 수 있는 매도요청 이벤트가 없을경우 저장된다")
	@Test
	void 체결할_수_있는_매도요청_이벤트가_없을경우_저장된다() {
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

	private String createUniqueId() {
		return UUID.randomUUID().toString();
	}

}
