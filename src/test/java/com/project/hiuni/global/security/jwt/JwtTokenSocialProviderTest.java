package com.project.hiuni.global.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.v1.service.SocialProvider;
import com.project.hiuni.global.security.core.CustomUserDetails;
import com.project.hiuni.global.security.core.CustomUserDetailsService;
import com.project.hiuni.global.security.core.Role;
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
class JwtTokenSocialProviderTest {

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  CustomUserDetailsService customUserDetailsService;

  @Test
  @DisplayName("JWT 토큰을 생성한다.")
  void createToken() {
    //given
    User user = this.createTestUserOf(1L);

    UserDetails userDetails = new CustomUserDetails(user);

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    //when
    String token = jwtTokenProvider.createToken(authentication, 1000L);

    //then
    assertThat(token).isNotNull(); // 토큰은 null이 아니어야 함.
    assertThat(token.split("\\.")).hasSize(3); // JWT 토큰은 헤더, 페이로드, 시그니쳐로 구성
  }

  @Test
  @DisplayName("JWT 토큰에서 사용자 ID를 추출한다.")
  void getUserIdFromToken() {
    //given
    User user = this.createTestUserOf(1L);

    UserDetails userDetails = new CustomUserDetails(user);

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    String token = jwtTokenProvider.createToken(authentication, 100000L);

    //when
    Long userId = jwtTokenProvider.getUserIdFromToken(token);

    //then
    assertThat(userId).isNotNull(); // 토큰에서 추출한 userId는 null이 아니어야 함.
    assertThat(userId).isEqualTo(1L); // 토큰에서 추출한 userId가 같아야 함.
  }

  @Test
  @DisplayName("JWT 토큰의 유효성을 검증한다.")
  void validateToken() {

    //given
    User user = this.createTestUserOf(1L);

    UserDetails userDetails = new CustomUserDetails(user);

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    //when
    String validToken = jwtTokenProvider.createToken(authentication, 1000L);
    String expiredToken = jwtTokenProvider.createToken(authentication, -1000L);
    String malformedToken = "unsupported.token.format";
    String illegalArgumentToken = " ";
    String invalidSignatureToken =
        validToken.substring(0, validToken.lastIndexOf('.') + 1) + "invalid";

    //then
    assertThat(jwtTokenProvider.validateToken(validToken)).isTrue(); // 토큰이 유효해야 함.
    assertThat(jwtTokenProvider.validateToken(expiredToken)).isFalse(); // 만료된 토큰은 유효하지 않아야 함.
    assertThat(
        jwtTokenProvider.validateToken(malformedToken)).isFalse(); // 형식이 잘못된 토큰은 유효하지 않아야 함.
    assertThat(
        jwtTokenProvider.validateToken(illegalArgumentToken)).isFalse(); // 빈 문자열 토큰은 유효하지 않아야 함.
    assertThat(
        jwtTokenProvider.validateToken(invalidSignatureToken)).isFalse(); // 서명이 잘못된 토큰은 유효하지 않아야 함


  }


  /**
   * 테스트용 mock 사용자를 생성하는 메서드 입니다. 생성된 User 객체는 다음과 같은 속성을 가집니다: socialEmail => id + "@gmail.com"
   * socialProvider => "kakao" univName => "익명대학교" majorName => "컴퓨터공학과" univEmail => id +
   * "@anonymous.ac.kr" nickname => "anonymous" + id imageUrl => null role => ROLE_USER
   *
   * @param id 사용자 식별을 위한 ID 값(pk)
   * @return 생성된 User 객체
   */
  private User createTestUserOf(Long id) {
    return User.builder()
        .id(id)
        .socialEmail(id + "@gmail.com")
        .socialProvider(SocialProvider.KAKAO)
        .univName("익명대학교")
        .majorName("컴퓨터공학과")
        .univEmail(id + "@anonymous.ac.kr")
        .nickname("anonymous" + id)
        .imageUrl(null)
        .role(Role.ROLE_USER)
        .build();
  }

}