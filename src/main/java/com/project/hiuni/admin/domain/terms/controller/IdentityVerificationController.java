package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.IdentityVerification;
import com.project.hiuni.admin.domain.terms.service.IdentityVerificationService;
import com.project.hiuni.global.common.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/identity-Verificaitons")
@RestController
public class IdentityVerificationController {

	private final IdentityVerificationService identityVerificationService;

	@GetMapping("/versions")
	public ResponseDto<TermsResponseDto> findByVersion(@RequestParam String version) {

		IdentityVerification identityVerification = identityVerificationService.findByVersion(
			version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			identityVerification.getContents(),
			identityVerification.getTermsInfo().getAgreeVersion(),
			null
		);

		return ResponseDto.response(termsResponseDto);
	}

	@PostMapping
	public void create(@RequestBody TermsRequestDto termsRequestDto) {
		identityVerificationService.create(termsRequestDto.content(), termsRequestDto.version());
	}

}
