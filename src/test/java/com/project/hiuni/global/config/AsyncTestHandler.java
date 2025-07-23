package com.project.hiuni.global.config;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.scheduling.annotation.Async;

@TestComponent
public class AsyncTestHandler {

	@Async
	public void throwException(long userId, boolean marketingConsent, boolean improveConsent) throws Exception {
		throw new RuntimeException("테스트 예외");
	}
}
