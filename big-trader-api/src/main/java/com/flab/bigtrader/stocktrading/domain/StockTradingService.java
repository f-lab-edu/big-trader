package com.flab.bigtrader.stocktrading.domain;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.flab.bigtrader.stocktrading.application.dto.StockTradingCommand;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockTradingService {

	public StockTradingEvent requestStockTrading(StockTradingCommand stockTradingCommand) {
		//TODO: UUID 추후 식별할 수 있는 유저 id로 변경예정
		// 주식 거래 이력을 저장 할 수 있도록 변경 예정
		return stockTradingCommand.toEvent(UUID.randomUUID().toString());
	}
}
