package com.flab.bigtrader.apppush.application;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.bigtrader.apppush.infrastructure.AppPushKafkaConsumer;
import com.flab.bigtrader.apppush.presentation.dto.AppPushSendRequest;

@ExtendWith(MockitoExtension.class)
class AppPushServiceTest {

	@InjectMocks
	private AppPushService appPushService;

	@Mock
	private AppPushKafkaConsumer appPushKafkaConsumer;

	@Test
	void 컨슈머가_한번_호출되는지_테스트한다() {

		AppPushSendRequest appPushSendRequest = new AppPushSendRequest("send message");

		appPushService.sendMessage(appPushSendRequest);

		then(appPushKafkaConsumer).should(times(1)).sendMessage(appPushSendRequest);
	}
}
