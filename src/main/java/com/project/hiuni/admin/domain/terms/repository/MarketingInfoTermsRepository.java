package com.project.hiuni.admin.domain.terms.repository;

import com.project.hiuni.admin.domain.terms.entity.MarketingInfoTerms;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketingInfoTermsRepository extends JpaRepository<MarketingInfoTerms, Long> {

	Optional<MarketingInfoTerms> findMarketingInfoTermsByTermsInfo_AgreeVersion(String version);
	Optional<MarketingInfoTerms> findTopByOrderByCreatedAtDesc();
}