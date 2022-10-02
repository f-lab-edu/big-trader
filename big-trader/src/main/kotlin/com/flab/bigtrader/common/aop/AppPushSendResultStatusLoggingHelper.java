package com.flab.bigtrader.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.flab.bigtrader.apppush.infrastructure.AppPushSendResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AppPushSendResultStatusLoggingHelper {

	@Around("execution(* com.flab.bigtrader.apppush.infrastructure.DefaultAppPushClient.sendAppPush(..))")
	public Object logSendCount(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		AppPushSendResult result = (AppPushSendResult)proceedingJoinPoint.proceed();
		if (log.isDebugEnabled()) {
			log.debug("앱 푸쉬 전달 개수 : {}", result.getCount());
		}
		return result;
	}
}
