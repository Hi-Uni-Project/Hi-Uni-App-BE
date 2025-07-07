package com.project.hiuni.domain.user.v1.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.TestUtils;
import com.project.hiuni.domain.user.dto.request.UserPostRequest;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.security.core.Role;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class UserV1ServiceTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserV1Service userV1Service;



	@DisplayName("회원을 생성하여 저장할 수 있다.")
	@Test
	void test3() throws Exception {
		//given
		var imageFile = TestUtils.getMockImageFile("jpg","testImage.jpg","image/jpg");
		var userPostRequest = getUserPostRequest(imageFile);

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
		assertThat(user.getProfileImage().getStoredImageName()).isNotNull();
		assertThat(user.getProfileImage().getUploadImageName()).isEqualTo(imageFile.getOriginalFilename());
		assertThat(user.getProfileImage().getImageData()).isNotEqualTo(imageFile.getBytes());
	}

	@DisplayName("이미지 파일이 null일 경우 이미지 엔티티의 필드도 null이다.")
	@Test
	void test5() throws Exception {
		//given
		UserPostRequest userPostRequest = getUserPostRequest(null);
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
		assertThat(user.getProfileImage().getStoredImageName()).isNull();
		assertThat(user.getProfileImage().getUploadImageName()).isNull();
		assertThat(user.getProfileImage().getImageData()).isNull();
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

	@DisplayName("닉네임을 변경할 수 있다.")
	@Test
	void test6() throws Exception {
		//given
		String nickname = "test";
		User testUser = getTestUser(false, "test@gmail.com", nickname);
		User user = userRepository.save(testUser);

		//when
		String newNickname = "test2";
		userV1Service.changeNickname(user.getId(), newNickname);

		//then
		assertThat(user.getNickname()).isEqualTo(newNickname);
	}

	@DisplayName("프로필 이미지를 삭제할 수 있다.")
	@Test
	void test7() throws Exception {
		//given
		var imageFile = TestUtils.getMockImageFile("jpg","testImage.jpg","image/jpg");
		UserPostRequest userPostRequest = getUserPostRequest(imageFile);
		User user = userV1Service.create(userPostRequest);

		//when
		userV1Service.deleteProfileImage(user.getId());

		//then
		User result = userRepository.findById(user.getId()).get();
		assertThat(result.getProfileImage()).isNull();
	}

	@Rollback(false)
	@DisplayName("프로필 이미지를 변경할 수 있다.")
	@Test
	void test8() throws Exception {
		//given
		var imageFile = TestUtils.getMockImageFile("jpg","testImage.jpg","image/jpg");
		UserPostRequest userPostRequest = getUserPostRequest(imageFile);
		User user = userV1Service.create(userPostRequest);
		String storedImageName = user.getProfileImage().getStoredImageName();

		var newImage = TestUtils.getMockImageFile("jpeg","testImage2.jpg","image/jpeg");

		//when
		userV1Service.changeProfileImage(user.getId(), newImage);

		//then
		User result = userRepository.findById(user.getId()).get();
		assertThat(result.getProfileImage().getUploadImageName()).isEqualTo(newImage.getOriginalFilename());
		assertThat(result.getProfileImage().getStoredImageName()).isNotEqualTo(storedImageName);
	}

	private User getTestUser(boolean marketingConsent, String mail, String nickname) {
		return User.builder()
			.marketingConsent(marketingConsent)
			.nickname(nickname)
			.socialEmail(mail)
			.socialProvider(SocialProvider.KAKAO)
			.univName("testUni")
			.imageUrl("testImageUrl")
			.majorName("testMajor")
			.role(Role.ROLE_USER)
			.univEmail("test@unive.com")
			.build();
	}

	private UserPostRequest getUserPostRequest(MockMultipartFile imageFile) {
		return new UserPostRequest(
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
	}

	private MockMultipartFile getImageFile(String originFilename) throws IOException {
		byte[] fakeJpeg = new byte[] {(byte)0xFF, (byte)0xD8, (byte)0xFF, (byte)0xE0};

		return new MockMultipartFile(
			"file",
			originFilename,
			"image/jpeg",
			fakeJpeg
		);
	}
}