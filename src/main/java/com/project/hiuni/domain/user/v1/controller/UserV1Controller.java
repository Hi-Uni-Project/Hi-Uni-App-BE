package com.project.hiuni.domain.user.v1.controller;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.v1.service.UserAgreementService;
import com.project.hiuni.domain.user.v1.service.UserV1Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user")
public class UserV1Controller {

	private UserV1Service  userV1Service;
	private UserAgreementService userAgreementService;

	@PostMapping
	public ResponseEntity<?> createUser() {

		User user = userV1Service.create();

		//TODO: 마케팅 정보 동의, 서비스 개선 동의 boolean값이 필요합니다.
		//userAgreementService.add(user.getId(),);

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{userId}/marketing/cancel")
	public ResponseEntity<?> rejectMarketing(@PathVariable long userId) {
		userV1Service.cancelMarketingSubs(userId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{userId}/marketing/agree")
	public ResponseEntity<?> updateUser(@PathVariable long userId) {
		userV1Service.agreeMarketingSubs(userId);
		return ResponseEntity.ok().build();
	}
}
