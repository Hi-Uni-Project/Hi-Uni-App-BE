package com.project.hiuni.domain.user.v1.controller;

import com.project.hiuni.domain.user.dto.request.UserPostRequest;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.v1.service.UserAgreementService;
import com.project.hiuni.domain.user.v1.service.UserV1Service;
import com.project.hiuni.domain.user.v1.service.UserVerificationService;
import com.project.hiuni.global.common.dto.response.ResponseDto;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserV1Controller {

	private final UserV1Service userV1Service;
	private final UserAgreementService userAgreementService;
	private final UserVerificationService userVerificationService;
	private final JwtTokenProvider jwtTokenProvider;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseDto<Long> createUser(@RequestBody UserPostRequest request) throws IOException {

		User user = userV1Service.create(request);
		userAgreementService.addAgreements(user.getId(), request.marketingConsent(),
			request.improvementConsent());

		//TODO: 리프레시 토큰, 액세스 토큰 발급 필요
		return ResponseDto.response(user.getId());
	}

	@GetMapping("/nickname")
	public ResponseEntity<?> getUserNickname(@RequestParam String nickname) {
		userVerificationService.checkNicknameDuplication(nickname);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{userId}/marketing/cancel")
	public ResponseEntity<?> rejectMarketing(@PathVariable long userId) {
		userV1Service.cancelMarketingSubs(userId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{userId}/marketing/agree")
	public ResponseEntity<?> agreeMarketing(@PathVariable long userId) {
		userV1Service.agreeMarketingSubs(userId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{userId}/nickname")
	public ResponseEntity<?> changeNickname(@PathVariable long userId,
		@RequestParam String newNickname) {
		userV1Service.changeNickname(userId, newNickname);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@RequestParam long userId) {
		userV1Service.withdrawUser(userId);
		//TODO: 로그아웃 필요
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{userId}/image")
	public ResponseEntity<?> deleteUserImage(@PathVariable long userId) {
		userV1Service.deleteProfileImage(userId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{userId}/image")
	public ResponseEntity<?> changeUserImage(
		@PathVariable long userId,
		@RequestParam MultipartFile newImage
	) throws IOException {

		userV1Service.changeProfileImage(userId, newImage);
		return ResponseEntity.ok().build();
	}
}
