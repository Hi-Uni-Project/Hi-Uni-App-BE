package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserV1Service {

	private final UserRepository userRepository;
	private final UserAgreementService userAgreementService;

	public User create(){
		return null;
	}

}
