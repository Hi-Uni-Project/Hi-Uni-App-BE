package com.project.hiuni.domain.user.v1.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.domain.user.entity.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImageServiceTest {

	@Autowired
	private ImageService imageService;

	@DisplayName("이미지 엔티티를 생성할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		byte[] fakeJpeg = new byte[] {(byte)0xFF, (byte)0xD8, (byte)0xFF, (byte)0xE0};

		String originalFilename = "originTitle.jpg";
		MockMultipartFile imageFile = new MockMultipartFile(
			"file",
			originalFilename,
			"image/jpeg",
			fakeJpeg
		);

		//when
		Image image = imageService.create(imageFile);

		//then
		assertThat(image.getUploadImageName()).isEqualTo(originalFilename);
		assertThat(image.getStoredImageName()).isNotEmpty();
	}

}