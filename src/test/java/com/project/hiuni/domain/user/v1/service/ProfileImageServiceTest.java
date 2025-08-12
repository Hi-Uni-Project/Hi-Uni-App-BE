package com.project.hiuni.domain.user.v1.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.TestUtils;
import com.project.hiuni.domain.user.entity.ProfileImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileSystemUtils;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileImageServiceTest {

	@Autowired
	private ImageService imageService;

	@Value("${file.dir}")
	private String fileDir;

	@BeforeEach
	void setup() throws Exception {
		// 테스트 시작 전, 파일 저장 디렉터리가 없으면 생성합니다.
		Path testDirPath = Paths.get(fileDir);
		if (Files.notExists(testDirPath)) {
			Files.createDirectories(testDirPath);
		}
	}

	@AfterEach
	void cleanup() throws Exception {
		Path testDirPath = Paths.get(fileDir);
		if (Files.exists(testDirPath)) {
			// Files.deleteRecursively()는 내부의 모든 파일과 폴더를 삭제하고,
			// 최종적으로 디렉토리 자체를 삭제합니다.
			FileSystemUtils.deleteRecursively(testDirPath);
		}
	}

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
//		assertThat(profileImage.getUploadImageName()).isEqualTo(originalFilename);
//		assertThat(profileImage.getStoredImageName()).isNotEmpty();

		assertThat(profileImage.getUploadImageName()).isEqualTo(originalFilename);
		assertThat(profileImage.getStoredImageName()).matches("[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}\\.jpg");
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

		File storedFile = new File(fileDir + profileImage.getStoredImageName());
		assertThat(storedFile).exists();
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

		File storedFile = new File(fileDir + profileImage.getStoredImageName());
		assertThat(storedFile).exists();
	}


	@DisplayName("이미지 파일이 null이면 빈 이미지 엔티티가 생성된다.")
	@Test
	void test2() throws Exception {
		//given
		//when
		ProfileImage profileImage = imageService.create(null);
		//then
		assertThat(profileImage.getStoredImageName()).isNull();
		assertThat(profileImage.getUploadImageName()).isNull();
		File storedFile = new File(fileDir + profileImage.getStoredImageName());
		assertThat(storedFile).doesNotExist();
	}
}