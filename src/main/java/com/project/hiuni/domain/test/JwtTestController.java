package com.project.hiuni.domain.test;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.global.security.core.CustomUserDetails;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class JwtTestController {

  private final JwtTokenProvider jwtTokenProvider;

  @GetMapping
  public String test() {
    return "test";
  }

  @GetMapping("/create-token")
  public String createToken() {
    User user = User.createTestUserOf(1L);

    UserDetails userDetails = new CustomUserDetails(user);

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    String token = jwtTokenProvider.createToken(authentication, 100000L);
    return token;
  }

}
