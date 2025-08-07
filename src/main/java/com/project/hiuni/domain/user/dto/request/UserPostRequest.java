package com.project.hiuni.domain.user.dto.request;

import com.project.hiuni.domain.user.v1.service.SocialProvider;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record UserPostRequest(

	String socialEmail,
	SocialProvider socialProvider,
	String univName,
	String majorName,
	String univEmail,
	@Nullable String nickname,
	String imageUrl,
	MultipartFile imageFile,
	boolean marketingConsent,
	boolean improvementConsent
) {
//TODO: 닉네임, 이메일 정규식, 이미지 관리
}
