package com.flab.bigtrader.stockexchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class StockExchangeRedisConfig {

	@Bean
	public RedisScript<String> findRedisScript() {
		Resource script = new ClassPathResource("scripts/lua/findstock.lua");
		return RedisScript.of(script, String.class);
	}
}
