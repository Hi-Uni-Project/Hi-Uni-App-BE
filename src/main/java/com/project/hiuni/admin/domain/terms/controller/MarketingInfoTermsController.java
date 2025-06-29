package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.MarketingInfoTerms;
import com.project.hiuni.admin.domain.terms.service.MarketingInfoTermsService;
import com.project.hiuni.global.common.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/marketing-terms")
@RestController
public class MarketingInfoTermsController {

	private final MarketingInfoTermsService marketingInfoTermsService;

	@GetMapping("/{version}")
	public ResponseDto<TermsResponseDto> findByVersion(@PathVariable String version) {

		MarketingInfoTerms marketingInfoTerms = marketingInfoTermsService.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			marketingInfoTerms.getContents(),
			marketingInfoTerms.getTermsInfo().getAgreeVersion(),
			null
		);

		return ResponseDto.response(termsResponseDto);
	}

	@GetMapping
	public ResponseDto<TermsResponseDto> findLatest() {

		MarketingInfoTerms marketingInfoTerms = marketingInfoTermsService.getByLastest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			marketingInfoTerms.getContents(),
			marketingInfoTerms.getTermsInfo().getAgreeVersion(),
			null
		);

		return ResponseDto.response(termsResponseDto);
	}

	@PostMapping
	public void create(@RequestBody TermsRequestDto termsRequestDto) {
		marketingInfoTermsService.create(
			termsRequestDto.content(),
			termsRequestDto.version(),
			termsRequestDto.effectiveDate()
		);
	}
}
