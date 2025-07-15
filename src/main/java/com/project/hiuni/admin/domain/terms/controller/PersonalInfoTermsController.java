package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.docs.PersonalTermsApiDocumentation;
import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import com.project.hiuni.admin.domain.terms.entity.PersonalInfoTerms;
import com.project.hiuni.admin.domain.terms.service.PersonalInfoTermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/personal-terms")
@RestController
public class PersonalInfoTermsController implements PersonalTermsApiDocumentation {

	private final PersonalInfoTermsService personalInfoTermsService;

	@GetMapping("/{version}")
	public ResponseEntity<TermsResponseDto> findByVersion(@PathVariable String version) {

		PersonalInfoTerms personalInfoTerms = personalInfoTermsService.getByVersion(version);

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			personalInfoTerms.getContents(),
			personalInfoTerms.getVersion(),
			personalInfoTerms.getEffectiveDate()
		);

		return ResponseEntity.ok(termsResponseDto);
	}

	@GetMapping
	public ResponseEntity<TermsResponseDto> findLatest() {

		PersonalInfoTerms personalInfoTerms = personalInfoTermsService.getByLastest();

		TermsResponseDto termsResponseDto = new TermsResponseDto(
			personalInfoTerms.getContents(),
			personalInfoTerms.getVersion(),
			personalInfoTerms.getEffectiveDate()
		);

		return ResponseEntity.ok(termsResponseDto);
	}

	@PostMapping
	public void create(@RequestBody TermsRequestDto termsRequestDto) {

		personalInfoTermsService.create(
			termsRequestDto.content(),
			termsRequestDto.version(),
			termsRequestDto.effectiveDate()
		);
	}
}
