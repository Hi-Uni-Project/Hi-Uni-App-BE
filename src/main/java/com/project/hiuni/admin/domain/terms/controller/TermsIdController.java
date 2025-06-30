package com.project.hiuni.admin.domain.terms.controller;

import com.project.hiuni.admin.domain.terms.service.TermsIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/termsIds/cache")
@RestController
public class TermsIdController {

	private final TermsIdService termsIdService;

	@GetMapping
	public void termsIdToCache() {
		termsIdService.cacheTermsIds();
	}

}
