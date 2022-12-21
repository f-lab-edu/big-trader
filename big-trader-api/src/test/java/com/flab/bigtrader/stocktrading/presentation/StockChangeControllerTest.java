package com.flab.bigtrader.stocktrading.presentation;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.flab.bigtrader.common.AcceptanceTest;
import com.flab.bigtrader.common.response.CommonApiResponse;
import com.flab.bigtrader.stocktrading.presentation.dto.StockTradingRequest;
import com.flab.bigtrader.stocktrading.presentation.dto.TradingType;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class StockChangeControllerTest extends AcceptanceTest {

	@DisplayName("주식 매수 요청에 성공한다")
	@Test
	void 주식_매수_요청에_성공한다() {
		// given
		StockTradingRequest stockTradingRequest = new StockTradingRequest(
			"SAMSUNG",
			50000L,
			TradingType.BUY
		);

		// when
		ExtractableResponse<Response> response = RestAssured.given()
			.log()
			.all()
			.contentType(ContentType.JSON)
			.body(stockTradingRequest)
			.when()
			.post("/api/v1/stock-trading")
			.then()
			.log()
			.all()
			.extract();

		CommonApiResponse<String> apiResponse = response.as(new TypeRef<>() {
		});

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(apiResponse.getBody()).isNotNull()
		);

	}
}
