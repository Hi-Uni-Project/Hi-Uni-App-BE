package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.docs.MarketingTermsApiDocumentation;
import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.MarketingInfoTerms;
import com.project.hiuni.admin.domain.terms.service.MarketingInfoTermsService;
import com.project.hiuni.global.common.dto.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/marketing-terms")
@RestController
public class MarketingInfoTermsController implements MarketingTermsApiDocumentation {

	private final MarketingInfoTermsService marketingInfoTermsService;

	@GetMapping("/{version}")
	public ResponseEntity<TermsResponseDto> findByVersion(@PathVariable String version) {

		MarketingInfoTerms marketingInfoTerms = marketingInfoTermsService.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			marketingInfoTerms.getContents(),
			marketingInfoTerms.getVersion(),
			marketingInfoTerms.getEffectiveDate()
		);

		return ResponseEntity.ok(termsResponseDto);
	}

	@GetMapping
	public ResponseEntity<TermsResponseDto> findLatest() {

		MarketingInfoTerms marketingInfoTerms = marketingInfoTermsService.getByLastest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			marketingInfoTerms.getContents(),
			marketingInfoTerms.getVersion(),
			marketingInfoTerms.getEffectiveDate()
		);

		return ResponseEntity.ok(termsResponseDto);
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
