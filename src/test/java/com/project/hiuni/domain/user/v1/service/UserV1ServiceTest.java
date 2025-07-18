package com.project.hiuni.domain.user.v1.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.security.core.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserV1ServiceTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserV1Service userV1Service;

	@BeforeEach
	void setUp() {
		userRepository.deleteAll();
	}

	@DisplayName("마케팅 수신을 거절할 수 있다.")
	@Test
	void test1() {
		//given
		User testUser = getTestUser(true, "test@gmail.com", "test");

		User user = userRepository.save(testUser);

		//when
		User result = userV1Service.cancelMarketingSubs(user.getId());

		//then
		assertThat(result.isMarketingConsent()).isFalse();
	}

	@DisplayName("마케팅 수신을 동의할 수 있다.")
	@Test
	void test2() {
		//given
		User testUser = getTestUser(false, "test1@gmail.com", "test1");

		User user = userRepository.save(testUser);

		//when
		User result = userV1Service.agreeMarketingSubs(user.getId());

		//then
		assertThat(result.isMarketingConsent()).isTrue();
	}

	private User getTestUser(boolean marketingConsent, String mail, String test) {
		return User.builder()
			.marketingConsent(marketingConsent)
			.nickname(test)
			.socialEmail(mail)
			.socialProvider("kakao")
			.univName("testUni")
			.imageUrl("testImageUrl")
			.majorName("testMajor")
			.role(Role.ROLE_USER)
			.univEmail("test@unive.com")
			.build();
	}
}