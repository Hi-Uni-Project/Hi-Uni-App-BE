package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.user.entity.ProfileImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

	@Value("${file.dir}")
	private String fileDir;

	public ProfileImage create(MultipartFile image) throws IOException {

		if (image == null) {
			return ProfileImage.builder().build();
		}

		String originalFilename = image.getOriginalFilename();
		String storedImageName = generateImageName(originalFilename);
		byte[] compressImage = compressImage(image.getBytes());

		String fullPath = getFullPath(storedImageName);

		try (FileOutputStream fos = new FileOutputStream(fullPath)) {
			fos.write(compressImage);
		}

		return ProfileImage.of(originalFilename, storedImageName, fullPath);
	}

	private String getFullPath(String storedImageName) {
		return fileDir + storedImageName;
	}

	private String generateImageName(String originalFilename) {

		if (originalFilename == null) {
			return UUID.randomUUID().toString();
		}

		String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		return UUID.randomUUID() + "." + extension;
	}

	private byte[] compressImage(byte[] originalImageData) throws IOException {
		try (
			ByteArrayInputStream imageDataBytes = new ByteArrayInputStream(originalImageData);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
		) {

			Thumbnails.of(imageDataBytes)
				.scale(1.0)
				.outputQuality(0.75)
				.toOutputStream(outputStream);

			return outputStream.toByteArray();
		}
	}
}
