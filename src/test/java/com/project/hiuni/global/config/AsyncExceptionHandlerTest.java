package com.project.hiuni.global.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(OutputCaptureExtension.class)
@Import({AsyncTestHandler.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AsyncExceptionHandlerTest {

	@Autowired
	private AsyncTestHandler asyncTest;

	@DisplayName("비동기 예외 발생 시 로그를 남긴다.")
	@Test
	void test1(CapturedOutput output) throws Exception {
		//given
		//when
		asyncTest.throwException(34L, false, false);
		Thread.sleep(500);
		//then
		assertThat(output).contains("비동기 메서드 호출 중 처리되지 않은 예외 발생");
	}
}