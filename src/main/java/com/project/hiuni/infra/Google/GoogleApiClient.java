package com.project.hiuni.infra.Google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.project.hiuni.domain.auth.dto.DefaultOAuthUserInfo;
import com.project.hiuni.domain.auth.dto.OAuthUserInfo;
import com.project.hiuni.domain.auth.exception.GoogleInvalidTokenException;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import java.util.Collections;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleApiClient {

  private final GoogleIdTokenVerifier verifier;

  @Value("${oauth.google.client-id}")
  private String GoogleClientId;

  public GoogleApiClient() {
    this.verifier = new GoogleIdTokenVerifier.Builder(
        new NetHttpTransport(),
        new GsonFactory())
        .setAudience(Collections.singletonList(GoogleClientId))
        .build();
  }

  public OAuthUserInfo getUserInfo(String idTokenString) {
    try {

      GoogleIdToken idToken = verifier.verify(idTokenString);

      if(idToken == null) {
        log.error("구글 id 토큰이 유효하지 않음");
        throw new GoogleInvalidTokenException(ErrorCode.GOOGLE_INVALID_TOKEN);
      }

      GoogleIdToken.Payload payload = idToken.getPayload();

      String socialId = payload.getSubject();
      String email = payload.getEmail();
      String name = (String) payload.get("name");

      log.info("구글 로그인 사용자 아이디 : " + socialId);
      log.info("구글 로그인 사용자 이메일 : " + email);
      log.info("구글 로그인 사용자 이름 : " + name);

      return DefaultOAuthUserInfo.of(socialId, name, email);

      } catch (Exception e) {
      log.error("서버 오류로 id 토큰 검증 실패: " + e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }








}
