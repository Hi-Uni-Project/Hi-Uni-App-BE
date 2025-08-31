package com.project.hiuni.global.security.jwt;

import static org.junit.jupiter.api.Assertions.*;

import com.project.hiuni.domain.user.entity.ProfileImage;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.entity.UserStatus;
import com.project.hiuni.domain.user.v1.service.SocialProvider;
import com.project.hiuni.global.security.core.CustomUserDetails;
import com.project.hiuni.global.security.core.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class JwtTokenProviderTest {

  @Autowired
  private SecretKey secretKey;

  @Test
  @DisplayName("JWT 엑세스 토큰을 생성한다.")
  void createAccessToken() {
    //given
    User testUser = getTestUser();
    Authentication authentication = getTestAuthentication(testUser);

    Long accessTokenExpirationTime = 900000L; // 15 minutes in milliseconds

    //when
    String accessToken = createToken(authentication, accessTokenExpirationTime);

    //then
    assertNotNull(accessToken);
  }

  @Test
  @DisplayName("JWT 리프레시 토큰을 생성한다.")
  void createRefreshToken() {
    //given
    User testUser = getTestUser();
    Authentication authentication = getTestAuthentication(testUser);

    Long refreshTokenExpirationTime = 2592000000L; // 30 days in milliseconds

    //when
    String refreshToken = createToken(authentication, refreshTokenExpirationTime);

    //then
    assertNotNull(refreshToken);
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

  /**
   * 테스트용 Authentication 객체를 생성하는 메서드입니다.
   *
   * @return Authentication 객체
   */
  public Authentication getTestAuthentication(User user) {
    UserDetails userTestDetails = new CustomUserDetails(user);
    return new UsernamePasswordAuthenticationToken(
        userTestDetails,
        null,
        userTestDetails.getAuthorities()
    );

  }

  /**
   * 테스트용 User 객체를 생성하는 메서드입니다.
   *
   * @return User 객체
   */
  public User getTestUser() {
    return User.builder()
        .id(1L)
        .socialEmail("test@kakao.com")
        .socialProvider(SocialProvider.KAKAO)
        .univName("Test University")
        .majorName("Computer Science")
        .univEmail("test@test.ac.kr")
        .nickname("test")
        .imageUrl("testImageUrl")
        .role(Role.ROLE_USER)
        .status(UserStatus.ACTIVE)
        .marketingConsent(true)
        .improvementConsent(true)
        .profileImage(ProfileImage.of("testImageUrl", "testImageUrl", null))
        .build();
  }


}