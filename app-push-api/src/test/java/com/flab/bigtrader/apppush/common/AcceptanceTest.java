package com.flab.bigtrader.apppush.common;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;

@EmbeddedKafka(ports = {9092})
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

	@LocalServerPort
	protected int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = this.port;
	}
}
