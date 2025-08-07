package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.user.dto.request.UserPostRequest;
import com.project.hiuni.domain.user.entity.ProfileImage;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class UserV1Service {

	private final UserRepository userRepository;
	private final UserVerificationService userVerificationService;
	private final ImageService imageService;

	public User create(UserPostRequest request) throws IOException {

		userVerificationService.checkEmailDuplication(
			request.socialEmail(),
			request.socialProvider()
		);

		ProfileImage profileImage = imageService.create(request.imageFile());
		User user = User.createStandardUserOf(request, profileImage);

		return userRepository.save(user);
	}

	public User cancelMarketingSubs(long userId) {
		User user = findUser(userId);
		user.cancelMarketingConsent();

		return user;
	}

	public User agreeMarketingSubs(long userId) {
		User user = findUser(userId);
		user.agreeMarketingConsent();

		return user;
	}

	public void changeNickname(long userId, String newNickname) {
		User user = findUser(userId);

		userVerificationService.checkNicknameDuplication(newNickname);
		user.changeNickname(newNickname);
	}

	public void withdrawUser(long userId) {
		User user = findUser(userId);
		user.withdraw();
	}


	public void deleteProfileImage(long userId) {
		User user = findUser(userId);
		if (user.getProfileImage() != null) {
			user.deleteImage();
		}
	}

	public void changeProfileImage(long userId, MultipartFile newImage) throws IOException {
		User user = findUser(userId);
		ProfileImage profileImage = imageService.create(newImage);
		user.changeProfileImage(profileImage);
	}

	public User findUser(long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.NOT_FOUND));
	}
}
