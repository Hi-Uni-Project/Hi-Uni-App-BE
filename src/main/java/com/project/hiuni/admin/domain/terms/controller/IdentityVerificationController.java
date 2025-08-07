package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.docs.IdentityTermsApiDocumentation;
import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.IdentityVerification;
import com.project.hiuni.admin.domain.terms.service.IdentityVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/admin/identity-terms")
@RestController
public class IdentityVerificationController implements IdentityTermsApiDocumentation {

	private final IdentityVerificationService identityVerificationService;

	@GetMapping("/{version}")
	public ResponseEntity<TermsResponseDto> findByVersion(@PathVariable String version) {

		IdentityVerification identityVerification =
			identityVerificationService.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			identityVerification.getContents(),
			identityVerification.getVersion(),
			identityVerification.getEffectiveDate()
		);

		return ResponseEntity.ok(termsResponseDto);
	}

	@GetMapping
	public ResponseEntity<TermsResponseDto> findLatest() {

		IdentityVerification identityVerification =
			identityVerificationService.getByLatest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			identityVerification.getContents(),
			identityVerification.getVersion(),
			identityVerification.getEffectiveDate()
		);

		return ResponseEntity.ok().body(termsResponseDto);
	}

	@PostMapping
	public void create(@RequestBody TermsRequestDto termsRequestDto) {
		identityVerificationService.create(
			termsRequestDto.content(),
			termsRequestDto.version(),
			termsRequestDto.effectiveDate()
		);
	}

}
