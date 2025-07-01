package com.project.hiuni.admin.domain.terms.entity.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TermsInfoTest {

	@DisplayName("약관 동의 공통 필드를 생성할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String content = "test_content";
		String version = "1";
		LocalDateTime effectiveDate = LocalDateTime.now();

		//when
		TermsInfo result = TermsInfo.of(content, version, effectiveDate);

		//then
		assertThat(result).isEqualTo(new TermsInfo(content, version, effectiveDate));
	}

	@DisplayName("effectiveDate가 null이어도 약관 동의 공통 필드를 생성할 수 있다.")
	@Test
	void test2() throws Exception {
		//given
		String content = "test_content";
		String version = "1";
		LocalDateTime effectiveDate = null;

		//when
		TermsInfo result = TermsInfo.of(content, version, effectiveDate);

		//then
		assertThat(result).isEqualTo(new TermsInfo(content, version, null));
	}


}