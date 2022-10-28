package com.flab.bigtrader.common.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.EnableAsync;

import com.flab.bigtrader.apppush.application.AppPushOperation;
import com.flab.bigtrader.apppush.application.AppPushService;
import com.flab.bigtrader.common.exception.UnSupportedAppPushOperationException;

@EnableAsync
@Configuration
public class AppPushConfig {

	@Bean
	public Map<AppPushOperation, AppPushService> appPushServiceMap(
		AppPushService appPushAsyncBlockingService,
		AppPushService appPushAsyncNonBlockingService) {
		Map<AppPushOperation, AppPushService> appPushServiceMap = new ConcurrentHashMap<>();
		appPushServiceMap.put(AppPushOperation.BLOCKING, appPushAsyncBlockingService);
		appPushServiceMap.put(AppPushOperation.NON_BLOCKING, appPushAsyncNonBlockingService);

		return appPushServiceMap;
	}

	public static class AppPushOperatorConverter implements Converter<String, AppPushOperation> {

		@Override
		public AppPushOperation convert(@NotNull String operation) {
			if (AppPushOperation.isPossibleOperation(operation)) {
				return AppPushOperation.getOperation(operation);
			}

			throw new UnSupportedAppPushOperationException();
		}
	}
}
