package com.project.hiuni.global.security.jwt;

import com.project.hiuni.global.security.core.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
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
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    Date expiryDate = new Date(new Date().getTime() + expirationMillis);

    return Jwts.builder()
        .subject(customUserDetails.getUsername().toString())
        .claim("userEmail", customUserDetails.getUsername())
        .issuedAt(new Date())
        .expiration(expiryDate)
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }

  public Long getUserIdFromToken()

}
