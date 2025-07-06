package com.project.hiuni.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "profile_image")
@Entity
public class ProfileImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String uploadImageName;

	private String storedImageName;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] imageData;

	@Builder
	private ProfileImage(Long id, String uploadImageName, String storedImageName, byte[] imageData) {
		this.id = id;
		this.uploadImageName = uploadImageName;
		this.storedImageName = storedImageName;
		this.imageData = imageData;
	}

	public static ProfileImage of(String uploadImageName, String storedImageName, byte[] imageData) {
		return ProfileImage.builder()
			.imageData(imageData)
			.uploadImageName(uploadImageName)
			.storedImageName(storedImageName)
			.build();
	}
}
