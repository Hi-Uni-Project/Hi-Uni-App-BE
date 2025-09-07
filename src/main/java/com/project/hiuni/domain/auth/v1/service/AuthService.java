package com.project.hiuni.domain.auth.v1.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.project.hiuni.domain.auth.dto.request.AuthSocialRequest;
import com.project.hiuni.domain.auth.dto.response.AuthSocialResponse;
import com.project.hiuni.domain.auth.entity.SocialProvider;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

  private final JwtTokenProvider jwtTokenProvider;
  private final RestTemplate restTemplate;


  public AuthSocialResponse socialLogin(AuthSocialRequest authSocialRequest) {
    SocialProvider userProvider = authSocialRequest.getProvider();

    // 소셜 플랫폼이 구글일 경우
    if(userProvider == SocialProvider.GOOGLE) {
      log.info("Google social login");

      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
          new NetHttpTransport(),
          new GsonFactory())
          .build();



    }

    return null;

  }

}
