package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.docs.ServiceTermsApiDocumentation;
import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.ServiceTerms;
import com.project.hiuni.admin.domain.terms.service.ServiceTermsManager;
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

@RequiredArgsConstructor
@RequestMapping("/admin/service-terms")
@RestController
public class ServiceTermsController implements ServiceTermsApiDocumentation {

	private final ServiceTermsManager serviceTermsManager;

	@GetMapping("/{version}")
	public ResponseDto<TermsResponseDto> findByVersion(@PathVariable String version) {

		ServiceTerms serviceTerms = serviceTermsManager.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			serviceTerms.getContents(),
			serviceTerms.getVersion(),
			serviceTerms.getEffectiveDate()
		);

		return ResponseDto.response(termsResponseDto);
	}

	@GetMapping
	public ResponseDto<TermsResponseDto> findLatest() {

		ServiceTerms serviceTerms = serviceTermsManager.getByLastest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			serviceTerms.getContents(),
			serviceTerms.getVersion(),
			serviceTerms.getEffectiveDate()
		);

		return ResponseDto.response(termsResponseDto);
	}

	@PostMapping
	public void create(@RequestBody TermsRequestDto termsRequestDto) {

		serviceTermsManager.create(
			termsRequestDto.content(),
			termsRequestDto.version(),
			termsRequestDto.effectiveDate()
		);
	}
}
