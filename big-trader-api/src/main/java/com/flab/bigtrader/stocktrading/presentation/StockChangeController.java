package com.flab.bigtrader.stocktrading.presentation;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flab.bigtrader.common.response.CommonApiResponse;
import com.flab.bigtrader.stocktrading.presentation.dto.StockTradingRequest;

@RequestMapping("/api/v1")
@RestController
public class StockChangeController {

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/stock-trading")
	public CommonApiResponse<String> requestStockTrading(@RequestBody StockTradingRequest stockTradingRequest) {

		return CommonApiResponse.of(UUID.randomUUID().toString());
	}
}
