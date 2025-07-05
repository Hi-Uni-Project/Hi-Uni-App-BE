package com.project.hiuni.domain.user.v1.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomNicknameDuplicationException;
import com.project.hiuni.domain.user.exception.CustomUserDuplicationException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.security.core.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserVerificationServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserVerificationService userVerificationService;

	@BeforeEach
	void setUp() {
		userRepository.deleteAll();
	}

	@DisplayName("중복된 이메일이 있으면 예외를 던진다.")
	@Test
	void test1() throws Exception {
		//given
		String email = "test@gmail.com";
		SocialProvider provider = SocialProvider.KAKAO;

		User testUser = getTestUser(email, "test", provider);
		userRepository.save(testUser);

		//when then
		assertThatThrownBy(() -> userVerificationService.checkEmailDuplication(email, provider))
			.isInstanceOf(CustomUserDuplicationException.class);
	}

	@DisplayName("중복된 이메일이 없으면 예외를 던지지 않는다.")
	@Test
	void test2() throws Exception {
		//given
		String email = "test@gmail.com";
		SocialProvider provider = SocialProvider.KAKAO;

		User testUser = getTestUser(email, "test", provider);
		userRepository.save(testUser);

		//when then
		assertThatNoException()
			.isThrownBy(() -> userVerificationService.checkEmailDuplication("test3@gmail.com", provider));
	}


	@DisplayName("중복된 닉네임이 있으면 예외를 던진다.")
	@Test
	void test3() throws Exception {
		//given
		String email = "test@gmail.com";
		SocialProvider provider = SocialProvider.KAKAO;
		String nickname = "test";

		User testUser = getTestUser(email, nickname, provider);
		userRepository.save(testUser);

		//when then
		assertThatThrownBy(() -> userVerificationService.checkNicknameDuplication(nickname))
			.isInstanceOf(CustomNicknameDuplicationException.class);
	}

	@DisplayName("중복된 닉네임이 없으면 예외를 던지지 않는다.")
	@Test
	void test4() throws Exception {
		//given
		String email = "test@gmail.com";
		SocialProvider provider = SocialProvider.KAKAO;
		String nickname = "test";

		User testUser = getTestUser(email, nickname, provider);
		userRepository.save(testUser);

		//when then
		assertThatNoException()
			.isThrownBy(() -> userVerificationService.checkNicknameDuplication("nickname"));
	}

	private User getTestUser(String mail, String nickname, SocialProvider socialProvider) {
		return User.builder()
			.marketingConsent(true)
			.nickname(nickname)
			.socialEmail(mail)
			.socialProvider(socialProvider)
			.univName("testUni")
			.imageUrl("testImageUrl")
			.majorName("testMajor")
			.role(Role.ROLE_USER)
			.univEmail("test@unive.com")
			.build();
	}
}

