package com.project.hiuni.domain.user.v1.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.domain.user.dto.request.UserPostRequest;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.security.core.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@Transactional
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

	@DisplayName("회원을 생성하여 저장할 수 있다.")
	@Test
	void test3() throws Exception {
		//given
		byte[] fakeJpeg = new byte[] {(byte)0xFF, (byte)0xD8, (byte)0xFF, (byte)0xE0};

		String originalFilename = "originTitle.jpg";
		MockMultipartFile imageFile = new MockMultipartFile(
			"file",
			originalFilename,
			"image/jpeg",
			fakeJpeg
		);
		UserPostRequest userPostRequest = new UserPostRequest(
			"test@gmail.com",
			SocialProvider.KAKAO,
			"testUniv",
			"major",
			"test@univ.com",
			"nickname",
			"imageUrl",
			imageFile,
			false,
			false
		);
		//when
		User user = userV1Service.create(userPostRequest);

		//then
		assertThat(user).isNotNull();
		assertThat(user.getNickname()).isEqualTo("nickname");
		assertThat(user.getSocialEmail()).isEqualTo("test@gmail.com");
		assertThat(user.getUnivName()).isEqualTo("testUniv");
		assertThat(user.getUnivEmail()).isEqualTo("test@univ.com");
		assertThat(user.getSocialProvider()).isEqualTo(SocialProvider.KAKAO);
		assertThat(user.isMarketingConsent()).isFalse();
		assertThat(user.isImprovementConsent()).isFalse();
		assertThat(user.getImage().getStoredImageName()).isNotNull();
		assertThat(user.getImage().getUploadImageName()).isEqualTo(originalFilename);
		assertThat(user.getImage().getImageData()).isEqualTo(new byte[] {(byte)0xFF, (byte)0xD8, (byte)0xFF, (byte)0xE0});
	}

	@DisplayName("이미지 파일이 null일 경우 이미지 엔티티의 필드도 null이다.")
	@Test
	void test5() throws Exception {
		//given
		UserPostRequest userPostRequest = new UserPostRequest(
			"test@gmail.com",
			SocialProvider.KAKAO,
			"testUniv",
			"major",
			"test@univ.com",
			"nickname",
			"imageUrl",
			null,
			false,
			false
		);
		//when
		User user = userV1Service.create(userPostRequest);

		//then
		assertThat(user).isNotNull();
		assertThat(user.getNickname()).isEqualTo("nickname");
		assertThat(user.getSocialEmail()).isEqualTo("test@gmail.com");
		assertThat(user.getUnivName()).isEqualTo("testUniv");
		assertThat(user.getUnivEmail()).isEqualTo("test@univ.com");
		assertThat(user.getSocialProvider()).isEqualTo(SocialProvider.KAKAO);
		assertThat(user.isMarketingConsent()).isFalse();
		assertThat(user.isImprovementConsent()).isFalse();
		assertThat(user.getImage().getStoredImageName()).isNull();
		assertThat(user.getImage().getUploadImageName()).isNull();
		assertThat(user.getImage().getImageData()).isNull();
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
			.socialProvider(SocialProvider.KAKAO)
			.univName("testUni")
			.imageUrl("testImageUrl")
			.majorName("testMajor")
			.role(Role.ROLE_USER)
			.univEmail("test@unive.com")
			.build();
	}
}