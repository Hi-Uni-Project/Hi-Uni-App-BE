package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.IdentityVerification;
import com.project.hiuni.admin.domain.terms.service.IdentityVerificationService;
import com.project.hiuni.global.common.dto.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 가입 동의 약관 추가 및 조회 api", description = "회원 가입 동의 약관을 추가 및 조회 할 수 있습니다. admin 권한이 필요합니다.")
@RequiredArgsConstructor
@RequestMapping("/admin/identity-terms")
@RestController
public class IdentityVerificationController {

	private final IdentityVerificationService identityVerificationService;

	@Operation(summary = "버전 별로 회원 가입 동의 약관을 조회할 수 있습니다.")
	@GetMapping("/{version}")
	public ResponseDto<TermsResponseDto> findByVersion(@PathVariable String version) {

		IdentityVerification identityVerification =
			identityVerificationService.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			identityVerification.getContents(),
			identityVerification.getVersion(),
			identityVerification.getEffectiveDate()
		);

		return ResponseDto.response(termsResponseDto);
	}

	@Operation(summary = "가장 최근 버전의 회원 가입 동의 약관을 조회할 수 있습니다.")
	@GetMapping
	public ResponseDto<TermsResponseDto> findLatest() {

		IdentityVerification identityVerification =
			identityVerificationService.getByLatest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			identityVerification.getContents(),
			identityVerification.getVersion(),
			identityVerification.getEffectiveDate()
		);

		return ResponseDto.response(termsResponseDto);
	}

	@Operation(summary = "회원 가입 동의 약관을 추가할 수 있습니다.")
	@PostMapping
	public void create(@RequestBody TermsRequestDto termsRequestDto) {
		identityVerificationService.create(
			termsRequestDto.content(),
			termsRequestDto.version(),
			termsRequestDto.effectiveDate()
		);
	}

}
