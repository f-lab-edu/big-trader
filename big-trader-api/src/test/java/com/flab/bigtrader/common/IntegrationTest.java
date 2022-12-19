package com.flab.bigtrader.common;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import com.flab.bigtrader.config.EmbeddedRedisConfig;

@EmbeddedKafka(ports = {9092})
@ActiveProfiles("test")
@Import(EmbeddedRedisConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
}
