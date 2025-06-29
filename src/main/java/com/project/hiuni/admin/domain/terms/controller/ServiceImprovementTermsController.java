package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.ServiceImprovementTerms;
import com.project.hiuni.admin.domain.terms.service.ServiceImprovementTermsManager;
import com.project.hiuni.global.common.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/service-improvements-terms")
@RestController
public class ServiceImprovementTermsController {

	private final ServiceImprovementTermsManager ServiceImprovementTermsManager;

	@GetMapping("/{version}")
	public ResponseDto<TermsResponseDto> findByVersion(@PathVariable String version) {

		ServiceImprovementTerms serviceImprovementTerms = ServiceImprovementTermsManager.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			serviceImprovementTerms.getContents(),
			serviceImprovementTerms.getVersion(),
			null
		);

		return ResponseDto.response(termsResponseDto);
	}

	@GetMapping
	public ResponseDto<TermsResponseDto> findLatest() {

		ServiceImprovementTerms serviceImprovementTerms = ServiceImprovementTermsManager.getByLastest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			serviceImprovementTerms.getContents(),
			serviceImprovementTerms.getVersion(),
			null
		);

		return ResponseDto.response(termsResponseDto);
	}

	@PostMapping
	public void create(@RequestBody TermsRequestDto termsRequestDto) {

		ServiceImprovementTermsManager.create(
			termsRequestDto.content(),
			termsRequestDto.version(),
			termsRequestDto.effectiveDate()
		);
	}
}
