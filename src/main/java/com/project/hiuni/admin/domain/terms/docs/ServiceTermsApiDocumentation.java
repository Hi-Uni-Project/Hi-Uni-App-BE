package com.project.hiuni.admin.domain.terms.docs;

import com.project.hiuni.admin.domain.terms.dto.TermsRequestDto;
import com.project.hiuni.admin.domain.terms.dto.TermsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "서비스 이용 약관 추가 및 조회 api", description = "서비스 이용 약관을 추가 및 조회 할 수 있습니다. admin 권한이 필요합니다.")
public interface ServiceTermsApiDocumentation {

  @Operation(summary = "버전 별로 서비스 이용 약관을 조회할 수 있습니다.")
  ResponseEntity<TermsResponseDto> findByVersion(@PathVariable String version);

  @Operation(summary = "가장 최근 버전의 서비스 이용 약관을 조회할 수 있습니다.")
  ResponseEntity<TermsResponseDto> findLatest();

  @Operation(summary = "서비스 이용 약관을 추가할 수 있습니다.")
  void create(@RequestBody TermsRequestDto termsRequestDto);


}
