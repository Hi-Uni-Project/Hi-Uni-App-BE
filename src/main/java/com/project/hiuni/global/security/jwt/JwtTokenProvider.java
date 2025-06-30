package com.project.hiuni.global.security.jwt;

import com.project.hiuni.global.security.core.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
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

  /**
   * JWT 토큰을 생성하는 메서드입니다.
   *
   * @param authentication   인증 정보
   * @param expirationMillis 토큰 만료 시간 (밀리초 단위)
   * @return 생성된 JWT 토큰
   */
  public String createToken(Authentication authentication, Long expirationMillis) {
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    Date expiryDate = new Date(new Date().getTime() + expirationMillis);

    return Jwts.builder()
        .subject(customUserDetails.getUsername())
        .claim("userId", customUserDetails.getId())
        .issuedAt(new Date())
        .expiration(expiryDate)
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }

  /**
   * JWT 토큰에서 사용자 ID를 추출하는 메서드입니다.
   *
   * @param token JWT 토큰
   * @return 사용자 ID
   */
  public Long getUserIdFromToken(String token) {
    return Jwts
        .parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get("userId", Long.class);
  }

  /**
   * JWT 토큰의 유효성을 검증하는 메서드입니다.
   *
   * @param token JWT 토큰
   * @return 유효성 검사 결과 (true: 유효, false: 무효)
   */
  public Boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token);
      return true;

    } catch (MalformedJwtException e) {
      //토큰 형식이 잘못됨
      return false;

    } catch (ExpiredJwtException e) {
      //토큰이 만료됨
      return false;

    } catch (UnsupportedJwtException e) {
      //지원하지 않는 토큰 형식
      return false;

    } catch (IllegalArgumentException e) {
      //토큰이 비어있거나 잘못된 형식
      return false;

    } catch (SignatureException e) {
      //시그니처 검증 실패
      return false;

    } catch (JwtException e) {
      //기타 JWT 관련 예외
      return false;
    }

  }

}
