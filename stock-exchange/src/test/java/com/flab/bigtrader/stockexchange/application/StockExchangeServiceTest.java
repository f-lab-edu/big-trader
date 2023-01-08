package com.flab.bigtrader.stockexchange.application;

import static com.flab.bigtrader.fixture.StockExchangeFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
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
		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			SAMSUNG_STOCK,
			SAMSUNG_STOCK_PRICE,
			BUY_STOCK_COUNT,
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
		매도_요청_저장(SAMSUNG_STOCK, BUY_STOCK_COUNT);

		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			SAMSUNG_STOCK,
			SAMSUNG_STOCK_PRICE,
			BUY_STOCK_COUNT,
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
		StockExchangeEvent sellStockExchangeEvent = 매도_요청_저장(SAMSUNG_STOCK, SELL_STOCK_COUNT);

		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			SAMSUNG_STOCK,
			SAMSUNG_STOCK_PRICE,
			BUY_STOCK_COUNT,
			TradingType.BUY
		);

		stockExchangeService.stockExchange(buyStockExchangeEvent);

		Optional<StockExchangeEvent> stockEvent = stockExchangeRedis.findStockEvent(
			sellStockExchangeEvent.generateKey());

		assertAll(
			() -> assertThat(stockEvent.isPresent()).isTrue(),
			() -> assertThat(stockEvent.get().getCount()).isEqualTo(SELL_STOCK_COUNT - BUY_STOCK_COUNT)
		);
	}

	@DisplayName("매수요청일때 체결할 수 있는 매도 요청 개수가 적을 경우 다음 체결 가능한개수를 가진 매도요청으로 모든개수가 체결된다")
	@Test
	void 매수요청일때_체결할_수_있는_매도_요청_개수가_적을_경우_다음_체결_가능한개수를_가진_매도요청으로_모든개수가_체결된다() {
		매도_요청_저장(SAMSUNG_STOCK, ONE_STOCK);
		매도_요청_저장(SAMSUNG_STOCK, TWO_STOCK);

		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			SAMSUNG_STOCK,
			SAMSUNG_STOCK_PRICE,
			THREE_STOCK,
			TradingType.BUY
		);

		stockExchangeService.stockExchange(buyStockExchangeEvent);

		Optional<StockExchangeEvent> stockEvent = stockExchangeRedis.findStockEvent(
			buyStockExchangeEvent.generateKey());

		assertThat(stockEvent.isPresent()).isFalse();
	}

	@DisplayName("매수요청일때 체결할 수 있는 매도 요청 개수가 적을 경우 다음 매도 요청이 체결 가능하지 않을때 남은 개수만큼 매수요청이 저장된다")
	@Test
	void 매수요청일때_체결할_수_있는_매도_요청_개수가_적을_경우_다음_매도_요청이_체결_가능하지_않을때_남은_개수만큼_매수요청이_저장된다() {
		매도_요청_저장(SAMSUNG_STOCK, ONE_STOCK);
		매도_요청_저장(SAMSUNG_STOCK, ONE_STOCK);

		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			SAMSUNG_STOCK,
			SAMSUNG_STOCK_PRICE,
			THREE_STOCK,
			TradingType.BUY
		);

		stockExchangeService.stockExchange(buyStockExchangeEvent);

		Optional<StockExchangeEvent> stockEvent = stockExchangeRedis.findStockEvent(
			buyStockExchangeEvent.generateKey());

		assertAll(
			() -> assertThat(stockEvent.isPresent()).isTrue(),
			() -> assertThat(stockEvent.get().getCount()).isEqualTo(ONE_STOCK)
		);

	}

	private String createUniqueId() {
		return UUID.randomUUID().toString();
	}

	private StockExchangeEvent 매도_요청_저장(String stockName, Long count) {
		StockExchangeEvent sellStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			stockName,
			SAMSUNG_STOCK_PRICE,
			count,
			TradingType.SELL
		);

		stockExchangeRedis.pushEventOnRight(sellStockExchangeEvent);
		return sellStockExchangeEvent;
	}

}
