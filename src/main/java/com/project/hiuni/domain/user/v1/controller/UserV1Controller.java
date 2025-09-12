package com.project.hiuni.domain.user.v1.controller;

import com.project.hiuni.domain.auth.entity.SocialProvider;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserV1Controller {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  @GetMapping("/token-test")
  @Transactional
  public ResponseDTO<String> tokenTest() {

    User user = User.createStandardUserForSocial("social123", null, "social123@social123", SocialProvider.GOOGLE);

    if(userRepository.findBySocialId("social123").isPresent()) {
      userRepository.deleteBySocialId("social123");
      userRepository.flush();
    }

    userRepository.save(user);

    String token = jwtTokenProvider.createAccessToken("social123", "social123");

    return ResponseDTO.of(token, "토큰 발급 성공");
  }



}
