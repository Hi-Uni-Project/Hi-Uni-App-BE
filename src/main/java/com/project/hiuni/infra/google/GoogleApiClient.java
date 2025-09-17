package com.project.hiuni.infra.google;

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
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoogleApiClient {

  private final GoogleIdTokenVerifier verifier;

  public GoogleApiClient(
      @Value("${oauth.google.client-id-android}") String clientId1,
      @Value("${oauth.google.client-id-apple}") String clientId2) {

    List<String> clientIds = List.of(clientId1, clientId2);

    this.verifier = new GoogleIdTokenVerifier.Builder(
        new NetHttpTransport(),
        new GsonFactory())
        .setAudience(clientIds)
        .build();
  }

  public OAuthUserInfo getUserInfo(String authToken) {
    try {

      log.info("구글 id 토큰 검증 시도 : " + authToken);

      GoogleIdToken idToken = verifier.verify(authToken);

      if(idToken == null) {
        log.error("구글 id 토큰이 비어있음");
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
      throw new GoogleInvalidTokenException(ErrorCode.GOOGLE_INVALID_TOKEN);
    }

  }

}
