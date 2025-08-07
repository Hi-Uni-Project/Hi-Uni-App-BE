package com.project.hiuni.domain.user.v1.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.TestUtils;
import com.project.hiuni.domain.user.entity.ProfileImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileImageServiceTest {

	@Autowired
	private ImageService imageService;

	@DisplayName("이미지(jpg) 엔티티를 생성할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String originalFilename = "test_image.jpg";
		MockMultipartFile mockImageFile = TestUtils.getMockImageFile(
			"jpg",
			originalFilename,
			"image/jpg"
		);

		//when
		ProfileImage profileImage = imageService.create(mockImageFile);

		//then
		assertThat(profileImage.getUploadImageName()).isEqualTo(originalFilename);
		assertThat(profileImage.getStoredImageName()).isNotEmpty();
	}

	@DisplayName("이미지(jpeg) 엔티티를 생성할 수 있다.")
	@Test
	void test3() throws Exception {
		//given
		String originalFilename = "test_image.jpeg";
		MockMultipartFile mockImageFile = TestUtils.getMockImageFile(
			"jpeg",
			originalFilename,
			"image/jpeg"
		);
		//when
		ProfileImage profileImage = imageService.create(mockImageFile);

		//then
		assertThat(profileImage.getUploadImageName()).isEqualTo(originalFilename);
		assertThat(profileImage.getStoredImageName()).isNotEmpty();
	}

	@DisplayName("이미지(png) 엔티티를 생성할 수 있다.")
	@Test
	void test4() throws Exception {
		//given
		String originalFilename = "test_image.png";
		MockMultipartFile mockImageFile = TestUtils.getMockImageFile(
			"png",
			originalFilename,
			"image/png"
		);
		//when
		ProfileImage profileImage = imageService.create(mockImageFile);

		//then
		assertThat(profileImage.getUploadImageName()).isEqualTo(originalFilename);
		assertThat(profileImage.getStoredImageName()).isNotEmpty();
	}


	@DisplayName("이미지 파일이 null이면 빈 이미지 엔티티가 생성된다.")
	@Test
	void test2() throws Exception {
		//given
		//when
		ProfileImage profileImage = imageService.create(null);
		//then
		assertThat(profileImage.getImageData()).isNull();
		assertThat(profileImage.getStoredImageName()).isNull();
		assertThat(profileImage.getUploadImageName()).isNull();
	}
}