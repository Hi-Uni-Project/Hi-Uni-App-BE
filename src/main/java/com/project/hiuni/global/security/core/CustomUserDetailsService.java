package com.project.hiuni.global.security.core;

import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * 사용자 인증 객체를 생성하는 메서드입니다.
   *
   * @param username 사용자 식별자인 소셜 이메일 값이 들어갑니다.
   * @return CustomUserDetails 객체
   * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findBySocialEmail(username).orElseThrow(
        () -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND));
    return new CustomUserDetails(user);
  }
}
