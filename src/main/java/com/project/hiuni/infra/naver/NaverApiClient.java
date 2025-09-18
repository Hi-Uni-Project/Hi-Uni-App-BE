package com.project.hiuni.infra.naver;

import com.project.hiuni.domain.auth.dto.DefaultOAuthUserInfo;
import com.project.hiuni.domain.auth.dto.OAuthUserInfo;
import com.project.hiuni.domain.auth.exception.NaverInvalidTokenException;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.infra.naver.dto.NaverResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class NaverApiClient {

  private final RestClient restClient;

  public OAuthUserInfo getUserInfo(String authToken) {
    try {
      log.info("네이버 엑세스 토큰 검증 시도 : " + authToken);

      NaverResponse response = restClient.get()
          .uri("https://openapi.naver.com/v1/nid/me")
          .header("Authorization", "Bearer " + authToken)
          .retrieve()
          .body(NaverResponse.class);

      String socialId = response.getResponse().getId();
      String email = response.getResponse().getEmail();
      String name = response.getResponse().getEmail();

      return DefaultOAuthUserInfo
          .builder()
          .socialId(socialId)
          .name(name)
          .email(email)
          .build();

    } catch (Exception e) {
      log.error("서버 오류로 id 토큰 검증 실패: " + e.getMessage());
      throw new NaverInvalidTokenException(ErrorCode.NAVER_INVALID_TOKEN);
    }
  }

}
