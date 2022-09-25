package com.flab.bigtrader.apppush.presentation;

import com.flab.bigtrader.common.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

class AppPushControllerTest extends AcceptanceTest {

    @Test
    void 메시지를_전달한다() {
        ExtractableResponse<Response> response = when()
                .post("/api/v1/app-push")
                .then()
                .log()
                .all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}