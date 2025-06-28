package com.project.hiuni.admin.domain.terms.service;

import com.project.hiuni.admin.domain.terms.entity.ServiceImprovementTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.ServiceImprovementTermsRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ServiceImprovementTermsManager {

	private final ServiceImprovementTermsRepository serviceImprovementTermsRepository;

	public void create(String content, String version){

		ServiceImprovementTerms serviceImprovementTerms = ServiceImprovementTerms.of(
			TermsInfo.of(content, version)
		);

		serviceImprovementTermsRepository.save(serviceImprovementTerms);
	}

}
