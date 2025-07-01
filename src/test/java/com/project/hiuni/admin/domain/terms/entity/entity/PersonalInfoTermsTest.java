package com.project.hiuni.admin.domain.terms.entity.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.PersonalInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PersonalInfoTermsTest {

	@DisplayName("PersonalInfoTerms 객체를 생성할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String content = "test_content";
		String version = "1";
		LocalDateTime effectiveDate = LocalDateTime.now();

		var termsInfo = TermsInfo.builder()
			.agreeContents(content)
			.agreeVersion(version)
			.effectiveDate(effectiveDate)
			.build();

		//when
		var result = PersonalInfoTerms.of(termsInfo);

		//then
		assertThat(result.getTermsInfo()).isEqualTo(TermsInfo.of(content, version, effectiveDate));
	}

}