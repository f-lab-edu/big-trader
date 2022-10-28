package com.flab.bigtrader.apppush.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class AppPushOperationTest {

	@DisplayName("앱푸쉬 operation 파싱에 성공한다.")
	@ParameterizedTest
	@ValueSource(strings = {"BLOCKING", "NON_BLOCKING", "blocking", "non_blocking"})
	void 앱푸쉬_operation_파싱에_성공한다(String targetOperation) {
		// given when
		boolean result = AppPushOperation.isPossibleOperation(targetOperation);

		// then
		assertThat(result).isTrue();
	}

	@DisplayName("null 또는 공백문자일 경우 앱푸쉬 operation 파싱에 실패한다.")
	@ParameterizedTest
	@NullAndEmptySource
	void null_또는_공백문자일경우_앱푸쉬_operation_파싱에_실패한다(String targetOperation) {
		// given when
		boolean result = AppPushOperation.isPossibleOperation(targetOperation);

		// then
		assertThat(result).isFalse();
	}

	@ParameterizedTest
	@ValueSource(strings = {"blok", "nonblock"})
	void 파싱하지못하는_문자가_올경우_앱푸쉬_operation_파싱에_실패한다(String targetOperation) {
		// given when
		boolean result = AppPushOperation.isPossibleOperation(targetOperation);

		// then
		assertThat(result).isFalse();
	}

}