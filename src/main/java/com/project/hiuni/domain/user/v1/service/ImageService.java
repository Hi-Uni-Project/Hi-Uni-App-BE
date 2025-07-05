package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.user.entity.Image;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

	public Image create(MultipartFile image) {
		
		if(image == null) return Image.builder().build();
		String storedImageName = generateImageName(image.getOriginalFilename());

		return Image.of(image.getOriginalFilename(), storedImageName);
	}

	private String generateImageName(String originalFilename) {
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		return UUID.randomUUID() + "." + extension;
	}
}
