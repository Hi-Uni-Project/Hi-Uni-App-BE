package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.MarketingInfoTerms;
import com.project.hiuni.admin.domain.terms.service.MarketingInfoTermsService;
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

@Tag(name = "마케팅 정보 수신 약관을 추가 및 조회 api", description = "마케팅 정보 수신 약관을 추가 및 조회 할 수 있습니다. admin 권한이 필요합니다.")
@RequiredArgsConstructor
@RequestMapping("/admin/marketing-terms")
@RestController
public class MarketingInfoTermsController {

	private final MarketingInfoTermsService marketingInfoTermsService;

	@Operation(summary = "버전 별로 마케팅 정보 수신 약관을 조회할 수 있습니다.")
	@GetMapping("/{version}")
	public ResponseDto<TermsResponseDto> findByVersion(@PathVariable String version) {

		MarketingInfoTerms marketingInfoTerms = marketingInfoTermsService.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			marketingInfoTerms.getContents(),
			marketingInfoTerms.getVersion(),
			marketingInfoTerms.getEffectiveDate()
		);

		return ResponseDto.response(termsResponseDto);
	}

	@Operation(summary = "버전 별로 마케팅 정보 수신 약관을 조회할 수 있습니다.")
	@GetMapping
	public ResponseDto<TermsResponseDto> findLatest() {

		MarketingInfoTerms marketingInfoTerms = marketingInfoTermsService.getByLastest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			marketingInfoTerms.getContents(),
			marketingInfoTerms.getVersion(),
			marketingInfoTerms.getEffectiveDate()
		);

		return ResponseDto.response(termsResponseDto);
	}

	@Operation(summary = "버전 별로 마케팅 정보 수신 약관 조회할 수 있습니다.")
	@PostMapping
	public void create(@RequestBody TermsRequestDto termsRequestDto) {

		marketingInfoTermsService.create(
			termsRequestDto.content(),
			termsRequestDto.version(),
			termsRequestDto.effectiveDate()
		);
	}
}
