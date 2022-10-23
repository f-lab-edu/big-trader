package com.flab.bigtrader.common.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import com.flab.bigtrader.apppush.application.AppPushAsyncBlockingService;
import com.flab.bigtrader.apppush.application.AppPushAsyncNonBlockingService;
import com.flab.bigtrader.apppush.application.AppPushOperation;
import com.flab.bigtrader.apppush.application.AppPushService;

@Configuration
public class AppPushConfig {

	@Bean
	public Map<AppPushOperation, AppPushService> appPushServiceMap(
		AppPushAsyncBlockingService appPushAsyncBlockingService,
		AppPushAsyncNonBlockingService appPushAsyncNonBlockingService
	) {
		Map<AppPushOperation, AppPushService> appPushServiceMap = new ConcurrentHashMap<>();
		appPushServiceMap.put(AppPushOperation.BLOCKING, appPushAsyncBlockingService);
		appPushServiceMap.put(AppPushOperation.NON_BLOCKING, appPushAsyncNonBlockingService);

		return appPushServiceMap;
	}

	public static class AppPushOperatorConverter implements Converter<String, AppPushOperation> {

		@Override
		public AppPushOperation convert(String operation) {
			String targetOperation = operation.toUpperCase();
			if (AppPushOperation.isPossibleOperation(targetOperation)) {
				return AppPushOperation.valueOf(targetOperation);
			}

			return AppPushOperation.BLOCKING;
		}
	}
}
