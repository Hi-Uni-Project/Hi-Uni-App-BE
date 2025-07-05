package com.project.hiuni.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "profile_image")
@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String uploadImageName;

	private String storedImageName;

	@Builder
	private Image(Long id, String uploadImageName, String storedImageName) {
		this.id = id;
		this.uploadImageName = uploadImageName;
		this.storedImageName = storedImageName;
	}

	public static Image of(String uploadImageName, String storedImageName) {
		return Image.builder()
			.uploadImageName(uploadImageName)
			.storedImageName(storedImageName)
			.build();
	}
}
