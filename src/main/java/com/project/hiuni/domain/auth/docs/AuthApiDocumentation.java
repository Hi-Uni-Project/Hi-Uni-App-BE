package com.project.hiuni.domain.auth.docs;

import com.project.hiuni.domain.auth.dto.request.AuthSocialRequest;
import com.project.hiuni.domain.auth.dto.response.AuthSocialResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "인증 관련 api 입니다.", description = "소셜 로그인/회원가입 처리를 위한 API입니다.")
public interface AuthApiDocumentation {

  @Operation(summary = "소셜 로그인/회원가입 API", description = "소셜 로그인을 통해 회원가입 또는 로그인을 처리합니다.")
  AuthSocialResponse authSocial(AuthSocialRequest authSocialRequest);
}
