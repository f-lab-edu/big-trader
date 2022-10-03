package com.flab.bigtrader.apppush.presentation;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.flab.bigtrader.apppush.presentation.dto.AppPushRequest;
import com.flab.bigtrader.common.AcceptanceTest;
import com.flab.bigtrader.common.CommonErrorResponse;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class AppPushControllerTest extends AcceptanceTest {

	@DisplayName("앱푸시 메시지 요청 시 App Push Api 전달에 성공한다.")
	@Test
	void 앱푸시_메시지_요청시_App_Push_Api전달에_성공한다() {
		// given
		AppPushRequest appPushRequest = new AppPushRequest("요청 메시지");

		// when
		ExtractableResponse<Response> response = 앱푸쉬요청(appPushRequest);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@DisplayName("공백 메시지 요청시 App Push Api 메시지 요청에 실패한다")
	@Test
	void 공백_메시지_요청시_앱푸시_메시지_요청에_실패한다() {
		// given
		AppPushRequest appPushRequest = new AppPushRequest("");

		// when
		ExtractableResponse<Response> response = 앱푸쉬요청(appPushRequest);

		CommonErrorResponse errorResponse = response.as(CommonErrorResponse.class);

		// then
		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
			() -> assertThat(errorResponse.getMessage()).isEqualTo("잘못된 요청 파라미터입니다.")
		);
	}

	private ExtractableResponse<Response> 앱푸쉬요청(AppPushRequest appPushRequest) {
		return RestAssured.given()
			.log()
			.all()
			.body(appPushRequest)
			.contentType(ContentType.JSON)
			.when()
			.post("/api/v1/messages")
			.then()
			.log()
			.all()
			.extract();
	}
}
