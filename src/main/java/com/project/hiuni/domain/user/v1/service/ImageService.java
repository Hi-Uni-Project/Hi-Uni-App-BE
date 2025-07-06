package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.user.entity.ProfileImage;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

	public ProfileImage create(MultipartFile image) throws IOException {

		if (image == null) {
			return ProfileImage.builder().build();
		}

		String storedImageName = generateImageName(image.getOriginalFilename());

		return ProfileImage.of(image.getOriginalFilename(), storedImageName, image.getBytes());
	}

	private String generateImageName(String originalFilename) {
		String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		return UUID.randomUUID() + "." + extension;
	}
}
