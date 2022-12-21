package com.flab.bigtrader.stocktrading.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flab.bigtrader.common.IntegrationTest;
import com.flab.bigtrader.stocktrading.application.dto.StockTradingCommand;
import com.flab.bigtrader.stocktrading.presentation.dto.TradingType;

class StockTradingFacadeTest extends IntegrationTest {

	@Autowired
	private StockTradingFacade stockTradingFacade;

	@DisplayName("주식 매수 메도 이벤트를 카프카 메시지 전송에 성공한다")
	@Test
	void 주식_매수_메도_이벤트를_카프카_메시지_전송에_성공한다() {
		StockTradingCommand stockTradingCommand = new StockTradingCommand(
			"SAMSUNG",
			50000L,
			TradingType.BUY
		);

		String identifiedId = stockTradingFacade.requestStockTrading(stockTradingCommand);

		assertThat(identifiedId).isNotNull();
	}
}
