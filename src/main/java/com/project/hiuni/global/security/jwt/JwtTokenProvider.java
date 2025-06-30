package com.project.hiuni.global.security.jwt;

import com.project.hiuni.global.security.core.CustomUserDetails;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * JWT 토큰 생성 및 추출, 검증하는 클래스 입니다.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final SecretKey secretKey;

  public String createToken(Authentication authentication, Long expirationMillis) {
    CustomUserDetails cu

  }

}
