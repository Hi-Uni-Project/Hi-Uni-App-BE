package com.project.hiuni.domain.test;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.domain.user.v1.service.SocialProvider;
import com.project.hiuni.global.security.core.CustomUserDetails;
import com.project.hiuni.global.security.core.Role;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  @GetMapping("/test")
  public String test() {
    User user = createTestUserOf(1L);
    UserDetails userDetails = new CustomUserDetails(user);
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    String token = jwtTokenProvider.createToken(authentication, 10000000L);
    return token;
  }

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
