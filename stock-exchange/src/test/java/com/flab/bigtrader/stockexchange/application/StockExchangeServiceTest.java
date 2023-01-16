package com.flab.bigtrader.stockexchange.application;

import static com.flab.bigtrader.fixture.StockExchangeFixture.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.bigtrader.stockexchange.domain.StockExchangeEvent;
import com.flab.bigtrader.stockexchange.infrastructure.kafka.StockKafkaProducer;
import com.flab.bigtrader.stockexchange.infrastructure.redis.StockExchangeRedis;
import com.flab.bigtrader.stockexchange.presentation.dto.TradingType;

@ExtendWith(MockitoExtension.class)
class StockExchangeServiceTest {

	@Mock
	private StockExchangeRedis stockExchangeRedis;

	@Mock
	private StockKafkaProducer stockKafkaProducer;

	@InjectMocks
	private StockExchangeService stockExchangeService;

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

		verify(stockExchangeRedis, times(1)).pushEventOnRight(buyStockExchangeEvent);
	}

	@DisplayName("매수요청일때 체결할 수 있는 매도요청 이벤트의 개수가 같을 경우 체결에 성공한다")
	@Test
	void 매수요청일때_체결할_수_있는_매도요청_이벤트의_개수가_같을_경우_체결에_성공한다() {
		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			SAMSUNG_STOCK,
			SAMSUNG_STOCK_PRICE,
			BUY_STOCK_COUNT,
			TradingType.BUY
		);

		when(stockExchangeRedis.findStockEvent(buyStockExchangeEvent.generateReverseKey()))
			.thenReturn(Optional.of(
					new StockExchangeEvent(
						createUniqueId(),
						SAMSUNG_STOCK,
						SAMSUNG_STOCK_PRICE,
						BUY_STOCK_COUNT,
						TradingType.SELL
					)
				)
			);

		stockExchangeService.stockExchange(buyStockExchangeEvent);

		verify(stockExchangeRedis, times(0)).pushEventOnRight(buyStockExchangeEvent);
	}

	@DisplayName("매수요청일때 체결할 수 있는 매도요청의 개수가 남을경우 남은 개수의 매도요청은 카프카에 전달된다.")
	@Test
	void 매수요청일때_체결할_수_있는_매도요청의_개수가_남을경우_남은_개수의_매도요청은_카프카에_전달된다() {
		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			SAMSUNG_STOCK,
			SAMSUNG_STOCK_PRICE,
			BUY_STOCK_COUNT,
			TradingType.BUY
		);

		when(stockExchangeRedis.findStockEvent(buyStockExchangeEvent.generateReverseKey()))
			.thenReturn(Optional.of(
					new StockExchangeEvent(
						createUniqueId(),
						SAMSUNG_STOCK,
						SAMSUNG_STOCK_PRICE,
						SELL_STOCK_COUNT,
						TradingType.SELL
					)
				)
			);

		stockExchangeService.stockExchange(buyStockExchangeEvent);

		verify(stockKafkaProducer, times(1)).sendStockTradingEvent(any());

	}

	@DisplayName("매수요청일때 체결할 수 있는 매도 요청 개수가 적을 경우 남은 개수는 카프카에 전달된다.")
	@Test
	void 매수요청일때_체결할_수_있는_매도_요청_개수가_적을_경우_다음_체결_가능한개수를_가진_매도요청으로_모든개수가_체결된다() {
		StockExchangeEvent buyStockExchangeEvent = new StockExchangeEvent(
			createUniqueId(),
			SAMSUNG_STOCK,
			SAMSUNG_STOCK_PRICE,
			THREE_STOCK,
			TradingType.BUY
		);

		when(stockExchangeRedis.findStockEvent(buyStockExchangeEvent.generateReverseKey()))
			.thenReturn(Optional.of(
					new StockExchangeEvent(
						createUniqueId(),
						SAMSUNG_STOCK,
						SAMSUNG_STOCK_PRICE,
						ONE_STOCK,
						TradingType.SELL
					)
				)
			);

		stockExchangeService.stockExchange(buyStockExchangeEvent);

		verify(stockKafkaProducer, times(1)).sendStockTradingEvent(any());
	}

	private String createUniqueId() {
		return UUID.randomUUID().toString();
	}
}
