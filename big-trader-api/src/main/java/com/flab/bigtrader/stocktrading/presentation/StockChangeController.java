package com.flab.bigtrader.stocktrading.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flab.bigtrader.common.response.CommonApiResponse;
import com.flab.bigtrader.stocktrading.application.StockTradingFacade;
import com.flab.bigtrader.stocktrading.presentation.dto.StockTradingRequest;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class StockChangeController {

	private final StockTradingFacade stockTradingFacade;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/stock-trading")
	public CommonApiResponse<String> requestStockTrading(@RequestBody StockTradingRequest stockTradingRequest) {
		String identifiedId = stockTradingFacade.requestStockTrading(stockTradingRequest.toCommand());
		return CommonApiResponse.of(identifiedId);
	}
}
