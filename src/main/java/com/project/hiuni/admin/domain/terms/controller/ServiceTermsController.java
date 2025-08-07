package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.docs.ServiceTermsApiDocumentation;
import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.ServiceTerms;
import com.project.hiuni.admin.domain.terms.service.ServiceTermsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<TermsResponseDto> findByVersion(@PathVariable String version) {

		ServiceTerms serviceTerms = serviceTermsManager.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			serviceTerms.getContents(),
			serviceTerms.getVersion(),
			serviceTerms.getEffectiveDate()
		);

		return ResponseEntity.ok(termsResponseDto);
	}

	@GetMapping
	public ResponseEntity<TermsResponseDto> findLatest() {

		ServiceTerms serviceTerms = serviceTermsManager.getByLastest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			serviceTerms.getContents(),
			serviceTerms.getVersion(),
			serviceTerms.getEffectiveDate()
		);

		return ResponseEntity.ok(termsResponseDto);
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
