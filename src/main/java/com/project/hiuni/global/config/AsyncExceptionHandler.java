package com.project.hiuni.global.config;

import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		log.warn("비동기 메서드 호출 중 처리되지 않은 예외 발생 :: 메서드 = {}, 메시지 = {}, 파라미터 = {}", method.getName(), ex.getMessage(), Arrays.toString(params));
	}
}
