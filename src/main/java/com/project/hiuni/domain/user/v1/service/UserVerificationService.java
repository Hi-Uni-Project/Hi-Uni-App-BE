package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.user.exception.CustomNicknameDuplicationException;
import com.project.hiuni.domain.user.exception.CustomUserDuplicationException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserVerificationService {

	private final UserRepository userRepository;

	public void checkEmailDuplication(String socialEmail, SocialProvider socialProvider) {

		boolean exist = userRepository.existsUserBySocialEmailAndSocialProvider(
			socialEmail,
			socialProvider
		);

		if (exist) {
			throw new CustomUserDuplicationException(ErrorCode.DUPLICATED_USER);
		}
	}

	public void checkNicknameDuplication(String nickname) {

		boolean exist = userRepository.existsUserByNickname(nickname);

		if (exist) {
			throw new CustomNicknameDuplicationException(ErrorCode.DUPLICATED_NICKNAME);
		}
	}

}
