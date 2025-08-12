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
public class ProfileImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String uploadImageName;

	private String storedImageName;

	private String fileDir;


	@Builder
	private ProfileImage(Long id, String uploadImageName, String storedImageName, String fileDir) {
		this.id = id;
		this.uploadImageName = uploadImageName;
		this.storedImageName = storedImageName;
		this.fileDir = fileDir;
	}

	public static ProfileImage of(String uploadImageName, String storedImageName, String fileDir) {
		return ProfileImage.builder()
			.uploadImageName(uploadImageName)
			.storedImageName(storedImageName)
			.fileDir(fileDir)
			.build();
	}
}
