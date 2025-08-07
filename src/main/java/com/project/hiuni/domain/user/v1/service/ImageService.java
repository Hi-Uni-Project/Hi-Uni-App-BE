package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.user.entity.ProfileImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

	public ProfileImage create(MultipartFile image) throws IOException {

		if (image == null) {
			return ProfileImage.builder().build();
		}

		String originalFilename = image.getOriginalFilename();

		String storedImageName = generateImageName(originalFilename);
		return ProfileImage.of(originalFilename, storedImageName, compressImage(image.getBytes()));
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
