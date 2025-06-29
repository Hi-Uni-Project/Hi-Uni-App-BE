package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.PersonalInfoTerms;
import com.project.hiuni.admin.domain.terms.service.PersonalInfoTermsService;
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

@Tag(name = "개인 정보 수집 약관 추가 및 조회 api", description = "개인 정보 수집 약관을 추가 및 조회 할 수 있습니다. admin 권한이 필요합니다.")
@RequiredArgsConstructor
@RequestMapping("/admin/personal-terms")
@RestController
public class PersonalInfoTermsController {

	private final PersonalInfoTermsService personalInfoTermsService;

	@Operation(summary = "버전 별로 개인 정보 수집 약관을 조회할 수 있습니다.")
	@GetMapping("/{version}")
	public ResponseDto<TermsResponseDto> findByVersion(@PathVariable String version) {

		PersonalInfoTerms personalInfoTerms = personalInfoTermsService.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			personalInfoTerms.getContents(),
			personalInfoTerms.getVersion(),
			personalInfoTerms.getEffectiveDate()
		);

		return ResponseDto.response(termsResponseDto);
	}

	@Operation(summary = "버전 별로 개인 정보 수집 약관을 조회할 수 있습니다.")
	@GetMapping
	public ResponseDto<TermsResponseDto> findLatest() {

		PersonalInfoTerms personalInfoTerms = personalInfoTermsService.getByLastest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			personalInfoTerms.getContents(),
			personalInfoTerms.getVersion(),
			personalInfoTerms.getEffectiveDate()
		);

		return ResponseDto.response(termsResponseDto);
	}

	@Operation(summary = "버전 별로 개인 정보 수집 약관을 조회할 수 있습니다.")
	@PostMapping
	public void create(@RequestBody TermsRequestDto termsRequestDto) {

		personalInfoTermsService.create(
			termsRequestDto.content(),
			termsRequestDto.version(),
			termsRequestDto.effectiveDate()
		);
	}
}
