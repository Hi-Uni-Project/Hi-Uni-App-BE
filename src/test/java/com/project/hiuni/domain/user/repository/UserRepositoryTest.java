package com.project.hiuni.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.v1.service.SocialProvider;
import com.project.hiuni.global.security.core.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		userRepository.deleteAll();
	}

	@DisplayName("소셜이메일과 소셜프로바이더로 유저를 조회할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String email = "test@gmail.com";
		User testUser = User.builder()
			.marketingConsent(true)
			.nickname("test-image")
			.socialEmail(email)
			.socialProvider(SocialProvider.KAKAO)
			.univName("testUni")
			.imageUrl("testImageUrl")
			.majorName("testMajor")
			.role(Role.ROLE_USER)
			.univEmail("test@unive.com")
			.build();
		
		userRepository.save(testUser);

		//when
		boolean result1 = userRepository.existsUserBySocialEmailAndSocialProvider(
			email,
			SocialProvider.KAKAO
		);

		boolean result2 = userRepository.existsUserBySocialEmailAndSocialProvider(
			email,
			SocialProvider.GOOGLE
		);

		//then
		assertThat(result1).isTrue();
		assertThat(result2).isFalse();
	}

	@DisplayName("닉네임으로 유저 조회를 할 수 있다.")
	@Test
	void test2() throws Exception {
		//given
		String nickname = "test-image";
		User testUser = User.builder()
			.marketingConsent(true)
			.nickname(nickname)
			.socialEmail("test@gmail.com")
			.socialProvider(SocialProvider.KAKAO)
			.univName("testUni")
			.imageUrl("testImageUrl")
			.majorName("testMajor")
			.role(Role.ROLE_USER)
			.univEmail("test@unive.com")
			.build();

		userRepository.save(testUser);

		//when
		boolean result1 = userRepository.existsUserByNickname(nickname);
		boolean result2 = userRepository.existsUserByNickname("test2");

		//then
		assertThat(result1).isTrue();
		assertThat(result2).isFalse();
	}
}