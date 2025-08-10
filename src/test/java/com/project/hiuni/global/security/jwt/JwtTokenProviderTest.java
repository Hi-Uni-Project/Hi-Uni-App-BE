package com.project.hiuni.global.security.jwt;

import static org.junit.jupiter.api.Assertions.*;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.global.security.core.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class JwtTokenProviderTest {

  @Autowired
  private SecretKey secretKey;

  @Test
  void createAccessToken() {
    //given
    //when
    //then
  }

  @Test
  void createRefreshToken() {
    //given
    //when
    //then
  }


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


}