package com.project.hiuni.infra.kakao;


import com.project.hiuni.domain.auth.dto.DefaultOAuthUserInfo;
import com.project.hiuni.domain.auth.dto.OAuthUserInfo;
import com.project.hiuni.domain.auth.exception.KakaoInvalidTokenException;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.infra.kakao.dto.KakaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.webmvc.core.service.RequestService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoApiClient {

  private final RestClient restClient;
  private final RequestService requestService;

  public OAuthUserInfo getUserInfo(String authToken) {
    try {
      log.info("카카오 엑세스 토큰 검증 시도 : " + authToken);

      KakaoResponse response = restClient.get()
          .uri("https://kapi.kakao.com/v2/user/me")
          .header("Authorization", "Bearer " + authToken)
          .retrieve()
          .body(KakaoResponse.class);

      String socialId = response.getId().toString();
      String email = response.getKakaoAccount().getEmail();
      String name = response.getKakaoAccount().getProfile().getNickname();


      return DefaultOAuthUserInfo
          .builder()
          .socialId(socialId)
          .name(name)
          .email(email)
          .build();

    } catch (Exception e) {
      log.error("서버 오류로 id 토큰 검증 실패: " + e.getMessage());
      throw new KakaoInvalidTokenException(ErrorCode.KAKAO_INVALID_TOKEN);
    }
  }



}
