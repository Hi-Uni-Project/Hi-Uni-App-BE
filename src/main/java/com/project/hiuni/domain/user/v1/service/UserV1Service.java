package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserV1Service {

	private final UserRepository userRepository;

	public User create() {
		return null;
	}

	public User cancelMarketingSubs(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.NOT_FOUND));
		user.cancelMarketingConsent();

		return user;
	}

	public User agreeMarketingSubs(long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomUserNotFoundException(ErrorCode.NOT_FOUND));
		user.agreeMarketingConsent();

		return user;
	}
}
